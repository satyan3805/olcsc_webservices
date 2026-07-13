package com.etcc.csc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.EtccSysException;
import com.etcc.csc.service.EjbServiceFactory;
import com.etcc.csc.service.ServiceFactory;

/**
 * Gets, sets, and closes the Database Connection for the DAOs.  This Aspect works by monitoring the classes in the
 * Service & DAO package.  The "Flow" starts with the first call to the service package, and ends when that first
 * call is finished.  As a side effect, forces multiple DAOs in the same call to share a Connection and a Transaction.
 *
 * @author Stephen Davidson
 */
//Note: Aspects are normally "Singletons".
public aspect ConnectionController {

    //Should Transactions be managed by this controller.
    //HACK: Fast check for EJBs.
    //TODO: Check for if a Global Transaction Manager will be in use.
    private final static boolean manageTransactions = !(ServiceFactory.getDefaultServiceFactory() instanceof EjbServiceFactory);
    /**
     * "Container" to hold a connection in a Threadsafe manner.
     */
    private ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    declare soft : EtccException : 
        execution(public * com.etcc.csc.service.MenuInterface.*(..))
        ;
    
    protected pointcut topLevelTransactedOperation():
        transactedOperation() && !cflowbelow(transactedOperation());

    protected pointcut transactedOperation():
        execution(public * com.etcc.csc.service.*.*(..))
        && target(com.etcc.csc.service.ServiceInterface+)
        //HACK: The above line is acting like an 'OR', not an 'AND'
        && target(com.etcc.csc.dao.BaseDAO+)
        //Hack: Does not implement ServiceInterface, so these should not be getting picked up.
        && !target(com.etcc.csc.service.ServiceFactory+)
        && !execution(* com.etcc.csc.service.MenuTestImpl.createObject(..))
        && !execution(* com.etcc.csc.service.*Test.*(..))
        ;
    
    protected pointcut obtainConnection():
        call(* com.etcc.csc.dao.ConnectionUtil.getConnection(String));

    pointcut illegalConnectionManagement():
        (call(void BaseDAO.closeConnection())
        ||call(void Connection.close())
        || call(void Connection.commit())
        || call(void Connection.rollback())
        || call(void Connection.setAutoCommit(boolean)))
        && !within(ConnectionController);

    declare error : illegalConnectionManagement() :
        "Do not call close(), commit() rollback() or setAutoCommit() on Connection objects from here";

    /**
     * If necessary, commits or rollbacks the transaction.  Then closes the Database connection.
     * @return the object returned by the operation, if any.
     */
    @SuppressWarnings("null")
    Object around() : topLevelTransactedOperation(){

        final Logger logger = Logger.getLogger(getClass());
        final boolean traceEnabled = logger.isTraceEnabled();
        
		logger.info("ConnectionController.topLevelTransactedOperation Starting toplevel transaction for: [" + thisJoinPoint.getSignature().getDeclaringTypeName() + '.'
				+ thisJoinPoint.getSignature().getName() +"]");

        Connection connection = null;
        try {
            Object operationResult = proceed();
            if (traceEnabled){
                logger.trace("Have result for: " + thisJoinPoint.getSignature().getDeclaringTypeName());
            }
            connection = this.connectionHolder.get();
            logger.info("ConnectionController.topLevelTransactedOperation connection retrived from connectionHolder [" + connection +"]");
            if (manageTransactions && connection != null && !connection.isClosed() && !connection.isReadOnly()){
                connection.commit();
            }
            return operationResult;
        } catch (EtccSecurityException e){
            //Nothing to rollback;
            throw new EtccConnectionException(e);
        } catch (EtccException e){
            rollbackTransaction(connection, logger);
            throw new EtccConnectionException(e);
        } catch (RuntimeException e){
            rollbackTransaction(connection, logger);
            throw e;
        } catch (SQLException e) {
            rollbackTransaction(connection, logger);
            throw new EtccConnectionException(new EtccException("Transaction failed: " + e.getMessage(), e));
        } finally {
            logger.debug("Closing connection.");
            if ((connection != null || (connection = this.connectionHolder.get()) != null)){
                try {
                    if (!connection.isClosed()){
                        connection.close();
                    	logger.info("ConnectionController.topLevelTransactedOperation Connection Closed Successfully");
                    } else {
                        logger.info("ConnectionController.topLevelTransactedOperation Connection is already closed.");
                    }
                } catch (SQLException e){
                    handleSQLException(logger, e,
                            "Closing Connection failed: " + e.getMessage(), Level.WARN);
                }
            } else {
                logger.info("ConnectionController.topLevelTransactedOperation No Connection to close.");
            }
            this.connectionHolder.remove();
        }
    }

    private void rollbackTransaction(Connection connection, Logger logger){
        //BUG: If running under EJB Container, need to set the Transaction Context to rollbackOnly.

        if (manageTransactions && (connection != null || (connection = this.connectionHolder.get()) != null)){
            try {
                connection.rollback();
            } catch (SQLException se) {
                handleSQLException(logger, se,
                        "Transaction Rollback failed: "+ se.getMessage(), Level.ERROR);
            }
        }
    }

    private void handleSQLException(Logger logger, SQLException e, String logMessage, Priority priority){
        SQLException current = e;
        logger.log(priority, logMessage);
        do {
            logger.log(priority, current.getMessage(), current);
        } while ((current = e.getNextException()) != null);

    }

    /**
     * Captures the Connection, and for any additional caller in this flow, will return the original Connection
     * rather than getting a new one.
     * @return a new Database Connection if one has not yet been opened, or the current DB Connection otherwise.
     * @throws EtccSysException thrown if the Datasource can not be found.
     */
    Connection around() throws EtccSysException : obtainConnection() {
        final Logger logger = Logger.getLogger(getClass());
        final boolean traceEnabled = logger.isTraceEnabled();
        if (traceEnabled){
            logger.trace("Getting connection for: " + thisJoinPoint.getSignature().getDeclaringTypeName());
        }
        Connection connection = this.connectionHolder.get();
        logger.info("ConnectionController.obtainConnection connection in connectionHolder [" + connection +"]");
        try {
			if (connection == null || connection.isClosed()) {

				logger.info("ConnectionController.obtainConnection Connection not available, loading from ConnectionUtil");

				connection = proceed();
				this.connectionHolder.set(connection);
				logger.info("ConnectionController.obtainConnection connection set in connectionHolder [" + connection + "]");
			}
        } catch (SQLException e){
            //Only thrown by connection.isClosed() check, so connection is toast.
            handleSQLException(Logger.getLogger(thisJoinPoint.getSignature().getDeclaringType().getClass()), e,
                    "Exception checking connection status: " + e.getMessage(), Level.WARN);
            logger.info("Getting fresh connection");
            connection = proceed();
            this.connectionHolder.set(connection);
        }
        if (traceEnabled){
            logger.trace("Have connection for: " + thisJoinPoint.getSignature().getDeclaringTypeName());
        }
        return connection;
    }

}
