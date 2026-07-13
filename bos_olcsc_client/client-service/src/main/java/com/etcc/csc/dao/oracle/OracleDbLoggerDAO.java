/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DbLoggerDAO;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.util.CoreDateUtil;

/**
 * Oracle implementation of the Logger DAO.
 */
public class OracleDbLoggerDAO extends DbLoggerDAO  {
    private static final Logger logger = Logger.getLogger(OracleDbLoggerDAO.class);

    public String logError(String message, String stack) throws EtccException {
/*
 FUNCTION error_handle(p_error_code     IN NUMBER,
                         p_error_message  IN VARCHAR2 ,
                         p_procedure_name IN VARCHAR2 ,
                         p_document_id         IN NUMBER DEFAULT   NULL,
                         p_u_doc_id       IN NUMBER DEFAULT   NULL,
                         p_doc_type       IN VARCHAR2 DEFAULT NULL,
                         p_session_id     IN VARCHAR2 DEFAULT NULL,
                         p_ip_address     IN VARCHAR2 DEFAULT NULL,
                         p_display_message IN VARCHAR2 DEFAULT NULL,
                         p_custom_error   IN VARCHAR2 DEFAULT NULL,
                         p_sec_custom1    IN VARCHAR2 DEFAULT NULL,
                         p_sec_custom2    IN VARCHAR2 DEFAULT NULL,
                         p_sec_custom3    IN VARCHAR2 DEFAULT NULL,
                         p_error_msg_arr IN OUT olc_error_msg_arr,
                         p_email    IN CHAR DEFAULT 'N' Send email to maint group)
 */
        
        String result = "";
        CallableStatement cstmt = null;
        try {
            Map<String, Class<?>> typeMap = setTypeMap();

            ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
                "OL_OWNER.OLC_ERROR_MSG_ARR", this.conn);            
            ARRAY array = new ARRAY(arraydesc, this.conn, 
                new OLC_ERROR_MSG_REC[]{new OLC_ERROR_MSG_REC()});            
        
            byte idx = 1;            
            cstmt = this.conn.prepareCall("{? = call OLCSC_ERROR_UTIL.error_handle("
                + "NULL,?,'JAVA',null,null,null,null,null,null,null,null,null,"
                + "null,?,'Y')}");
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, (CoreDateUtil.getISO8061DateTime(new Date()) + "-" + stack).substring(0,1500));
            cstmt.setArray(idx, array);
            cstmt.registerOutParameter(idx++, Types.ARRAY, 
                "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.executeUpdate();
            int exists = cstmt.getInt(1);
            if (exists == 1) {
                result = "123";
            }

        } catch (SQLException e) {
            //throw new EtccException("Error logging error: " + th, th);  
            logger.error("Error logging error: " + e.getMessage(), e);
        } finally {
            close(cstmt);
        }
        return result;    
    }
  
    public String logSecurityViolation(String message) 
        throws EtccException {
    
//        StringBuilder sb = new StringBuilder();
        
    /*
    FUNCTION LOGIN_VPS(P_USER VARCHAR2, P_PASSWORD VARCHAR2, P_ACCT_ID OUT NUMBER, P_INVOICE_ID NUMBER,
    P_CA_ACCT_ID NUMBER, P_LICENSE_PLATE VARCHAR2, P_LIC_PLATE_STATE VARCHAR2, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR)

    */
        
    /*        sb.append(" select inv.VIOL_INVOICE_ID ");
        sb.append(" from   viol_invoices inv, violators viol ");
        sb.append(" where  viol.VIOLATOR_ID = inv.VIOLATOR_ID ");
        sb.append(" and    viol.LIC_PLATE_NBR = ? ");
        sb.append(" and    viol.LIC_PLATE_STATE = ?  ");
        sb.append(" and    inv.viol_invoice_id = ? ");
    */
        String result = "";
        CallableStatement cstmt = null;
        try {
    /*
            cstmt = conn.prepareCall(
                "{? = call OLCSC_LOGIN.LOGIN_VPS(?, ?, ?, ?, ?, ?, ?, ?)}");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(4, Types.NUMERIC);
            cstmt.registerOutParameter(9, Types.ARRAY, "OLC_ERROR_MSG_ARR");
    //            cstmt.registerOutParameter(9, Types.ARRAY);
            cstmt.setString(2, invoiceId);
            cstmt.setString(3, invoiceId);
            cstmt.setString(5, invoiceId);
            cstmt.setString(6, collectionsId);
            cstmt.setString(7, licPlate);
            cstmt.setString(8, licState);
            cstmt.executeUpdate();
            int exists = cstmt.getInt(1);
            if (exists == 1) {
                result = true;
            }
    */
    /*            ps = conn.prepareStatement(sb.toString());
            ps.setString(1, licPlate);
            ps.setString(2, licState);
            ps.setString(3, invoiceId);
            //      ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
    */
        } catch (Throwable th) {
            throw new EtccException("Error logging security violation: " 
                + th, th);  
        } finally {
            close(cstmt);
        }
        return result;    
    }
}
