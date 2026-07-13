/*@lineinfo:filename=OLCSC_LOGIN*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_LOGIN
{

  /* connection management */
  protected Connection __onn = null;
  protected javax.sql.DataSource __dataSource = null;
  public void setDataSource(javax.sql.DataSource dataSource) throws SQLException
  { release(); __dataSource = dataSource; }
  public void setDataSourceLocation(String dataSourceLocation) throws SQLException {
    javax.sql.DataSource dataSource;
    try {
      Class cls = Class.forName("javax.naming.InitialContext");
      Object ctx = cls.newInstance();
      java.lang.reflect.Method meth = cls.getMethod("lookup", new Class[]{String.class});
      dataSource = (javax.sql.DataSource) meth.invoke(ctx, new Object[]{"java:comp/env/" + dataSourceLocation});
      setDataSource(dataSource);
    } catch (Exception e) {
      throw new java.sql.SQLException("Error initializing DataSource at " + dataSourceLocation + ": " + e.getMessage());
    }
  }
  public Connection getConnection() throws SQLException
  { 
    if (__onn!=null) return __onn;
     else if (__tx!=null) return __tx.getConnection(); 
     else if (__dataSource!=null) __onn= __dataSource.getConnection(); 
     return __onn; 
   } 
  public void release() throws SQLException
  { if (__tx!=null && __onn!=null) __tx.close(ConnectionContext.KEEP_CONNECTION);
    __onn = null; __tx = null;
    __dataSource = null;
  }

  public void closeConnection(){
    if (__dataSource!=null) {
      try { if (__onn!=null) { __onn.close(); } } catch (java.sql.SQLException e) {}
      try { if (__tx!=null) {__tx.close(); } } catch (java.sql.SQLException e) {}
      __onn=null;
      __tx=null;
    }
  }
  protected DefaultContext __tx = null;
  public void setConnectionContext(DefaultContext ctx) throws SQLException
  { release(); __tx = ctx; }
  public DefaultContext getConnectionContext() throws SQLException
  { if (__tx==null)
    { __tx = (getConnection()==null) ? DefaultContext.getDefaultContext() : new DefaultContext(getConnection()); }
    return __tx;
  };

  /* constructors */
  public OLCSC_LOGIN() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_LOGIN(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_LOGIN(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_LOGIN(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal LOGIN_VPS (
    String P_USER,
    String P_JSESSION_ID,
    String P_IP_ADDRESS,
    String P_BROWSER_TYPE,
    String P_BROWSER_VERSION,
    String P_OS_TYPE,
    String P_OS_VERSION,
    String P_USER_ENV_ATTRIBUTE1,
    String P_USER_ENV_ATTRIBUTE2,
    String P_USER_ENV_ATTRIBUTE3,
    String P_USER_ENV_ATTRIBUTE4,
    String P_USER_ENV_ATTRIBUTE5,
    String P_INVOICE_NBR,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    java.math.BigDecimal P_PMT_PLAN_ID,
    String P_SOURCE_USER_NAME,
    java.math.BigDecimal O_VIOLATOR_ID[],
    String O_SESSION_ID[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:93^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN.LOGIN_VPS(
//        :P_USER,
//        :P_JSESSION_ID,
//        :P_IP_ADDRESS,
//        :P_BROWSER_TYPE,
//        :P_BROWSER_VERSION,
//        :P_OS_TYPE,
//        :P_OS_VERSION,
//        :P_USER_ENV_ATTRIBUTE1,
//        :P_USER_ENV_ATTRIBUTE2,
//        :P_USER_ENV_ATTRIBUTE3,
//        :P_USER_ENV_ATTRIBUTE4,
//        :P_USER_ENV_ATTRIBUTE5,
//        :P_INVOICE_NBR,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_PMT_PLAN_ID,
//        :P_SOURCE_USER_NAME,
//        :O_VIOLATOR_ID[0],
//        :O_SESSION_ID[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN.LOGIN_VPS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_LOGIN",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER);
   __sJT_st.setString(3,P_JSESSION_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_BROWSER_TYPE);
   __sJT_st.setString(6,P_BROWSER_VERSION);
   __sJT_st.setString(7,P_OS_TYPE);
   __sJT_st.setString(8,P_OS_VERSION);
   __sJT_st.setString(9,P_USER_ENV_ATTRIBUTE1);
   __sJT_st.setString(10,P_USER_ENV_ATTRIBUTE2);
   __sJT_st.setString(11,P_USER_ENV_ATTRIBUTE3);
   __sJT_st.setString(12,P_USER_ENV_ATTRIBUTE4);
   __sJT_st.setString(13,P_USER_ENV_ATTRIBUTE5);
   __sJT_st.setString(14,P_INVOICE_NBR);
   __sJT_st.setString(15,P_LIC_PLATE);
   __sJT_st.setString(16,P_LIC_STATE);
   __sJT_st.setBigDecimal(17,P_PMT_PLAN_ID);
   __sJT_st.setString(18,P_SOURCE_USER_NAME);
   __sJT_st.setString(20,O_SESSION_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VIOLATOR_ID[0] = __sJT_st.getBigDecimal(19);
   O_SESSION_ID[0] = (String)__sJT_st.getString(20);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(21,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:113^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:119^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN.LOGIN_VPS(
//        :P_USER,
//        :P_JSESSION_ID,
//        :P_IP_ADDRESS,
//        :P_BROWSER_TYPE,
//        :P_BROWSER_VERSION,
//        :P_OS_TYPE,
//        :P_OS_VERSION,
//        :P_USER_ENV_ATTRIBUTE1,
//        :P_USER_ENV_ATTRIBUTE2,
//        :P_USER_ENV_ATTRIBUTE3,
//        :P_USER_ENV_ATTRIBUTE4,
//        :P_USER_ENV_ATTRIBUTE5,
//        :P_INVOICE_NBR,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_PMT_PLAN_ID,
//        :P_SOURCE_USER_NAME,
//        :O_VIOLATOR_ID[0],
//        :O_SESSION_ID[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN.LOGIN_VPS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_LOGIN",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER);
   __sJT_st.setString(3,P_JSESSION_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_BROWSER_TYPE);
   __sJT_st.setString(6,P_BROWSER_VERSION);
   __sJT_st.setString(7,P_OS_TYPE);
   __sJT_st.setString(8,P_OS_VERSION);
   __sJT_st.setString(9,P_USER_ENV_ATTRIBUTE1);
   __sJT_st.setString(10,P_USER_ENV_ATTRIBUTE2);
   __sJT_st.setString(11,P_USER_ENV_ATTRIBUTE3);
   __sJT_st.setString(12,P_USER_ENV_ATTRIBUTE4);
   __sJT_st.setString(13,P_USER_ENV_ATTRIBUTE5);
   __sJT_st.setString(14,P_INVOICE_NBR);
   __sJT_st.setString(15,P_LIC_PLATE);
   __sJT_st.setString(16,P_LIC_STATE);
   __sJT_st.setBigDecimal(17,P_PMT_PLAN_ID);
   __sJT_st.setString(18,P_SOURCE_USER_NAME);
   __sJT_st.setString(20,O_SESSION_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VIOLATOR_ID[0] = __sJT_st.getBigDecimal(19);
   O_SESSION_ID[0] = (String)__sJT_st.getString(20);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(21,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:139^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal LOGIN (
    String P_USER,
    String P_PASSWORD,
    String P_JSESSION_ID,
    String P_IP_ADDRESS,
    String P_BROWSER_TYPE,
    String P_BROWSER_VERSION,
    String P_OS_TYPE,
    String P_OS_VERSION,
    String P_USER_ENV_ATTRIBUTE1,
    String P_USER_ENV_ATTRIBUTE2,
    String P_USER_ENV_ATTRIBUTE3,
    String P_USER_ENV_ATTRIBUTE4,
    String P_USER_ENV_ATTRIBUTE5,
    String P_SOURCE_USER_NAME,
    java.math.BigDecimal P_ACCT_ID[],
    String O_SESSION_ID[],
    String P_ACCT_LOGIN_STATUS_CODE[],
    String P_ACCOUNT_STATUS_CODE[],
    String P_PWD_UPDATE_FLAG[],
    String P_IS_LOCKED[],
    java.sql.Timestamp P_LAST_UPDATE_DATE[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_LARGE_ACCOUNT_FLAG[],
    String P_EXTRA_LARGE_ACCOUNT_FLAG[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:177^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN.LOGIN(
//        :P_USER,
//        :P_PASSWORD,
//        :P_JSESSION_ID,
//        :P_IP_ADDRESS,
//        :P_BROWSER_TYPE,
//        :P_BROWSER_VERSION,
//        :P_OS_TYPE,
//        :P_OS_VERSION,
//        :P_USER_ENV_ATTRIBUTE1,
//        :P_USER_ENV_ATTRIBUTE2,
//        :P_USER_ENV_ATTRIBUTE3,
//        :P_USER_ENV_ATTRIBUTE4,
//        :P_USER_ENV_ATTRIBUTE5,
//        :P_SOURCE_USER_NAME,
//        :P_ACCT_ID[0],
//        :O_SESSION_ID[0],
//        :P_ACCT_LOGIN_STATUS_CODE[0],
//        :P_ACCOUNT_STATUS_CODE[0],
//        :P_PWD_UPDATE_FLAG[0],
//        :P_IS_LOCKED[0],
//        :P_LAST_UPDATE_DATE[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_LARGE_ACCOUNT_FLAG[0],
//        :P_EXTRA_LARGE_ACCOUNT_FLAG[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN.LOGIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_LOGIN",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(23,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER);
   __sJT_st.setString(3,P_PASSWORD);
   __sJT_st.setString(4,P_JSESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_BROWSER_TYPE);
   __sJT_st.setString(7,P_BROWSER_VERSION);
   __sJT_st.setString(8,P_OS_TYPE);
   __sJT_st.setString(9,P_OS_VERSION);
   __sJT_st.setString(10,P_USER_ENV_ATTRIBUTE1);
   __sJT_st.setString(11,P_USER_ENV_ATTRIBUTE2);
   __sJT_st.setString(12,P_USER_ENV_ATTRIBUTE3);
   __sJT_st.setString(13,P_USER_ENV_ATTRIBUTE4);
   __sJT_st.setString(14,P_USER_ENV_ATTRIBUTE5);
   __sJT_st.setString(15,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCT_ID[0] = __sJT_st.getBigDecimal(16);
   O_SESSION_ID[0] = (String)__sJT_st.getString(17);
   P_ACCT_LOGIN_STATUS_CODE[0] = (String)__sJT_st.getString(18);
   P_ACCOUNT_STATUS_CODE[0] = (String)__sJT_st.getString(19);
   P_PWD_UPDATE_FLAG[0] = (String)__sJT_st.getString(20);
   P_IS_LOCKED[0] = (String)__sJT_st.getString(21);
   P_LAST_UPDATE_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(22);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(23,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   P_LARGE_ACCOUNT_FLAG[0] = (String)__sJT_st.getString(24);
   P_EXTRA_LARGE_ACCOUNT_FLAG[0] = (String)__sJT_st.getString(25);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:201^46*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:207^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN.LOGIN(
//        :P_USER,
//        :P_PASSWORD,
//        :P_JSESSION_ID,
//        :P_IP_ADDRESS,
//        :P_BROWSER_TYPE,
//        :P_BROWSER_VERSION,
//        :P_OS_TYPE,
//        :P_OS_VERSION,
//        :P_USER_ENV_ATTRIBUTE1,
//        :P_USER_ENV_ATTRIBUTE2,
//        :P_USER_ENV_ATTRIBUTE3,
//        :P_USER_ENV_ATTRIBUTE4,
//        :P_USER_ENV_ATTRIBUTE5,
//        :P_SOURCE_USER_NAME,
//        :P_ACCT_ID[0],
//        :O_SESSION_ID[0],
//        :P_ACCT_LOGIN_STATUS_CODE[0],
//        :P_ACCOUNT_STATUS_CODE[0],
//        :P_PWD_UPDATE_FLAG[0],
//        :P_IS_LOCKED[0],
//        :P_LAST_UPDATE_DATE[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_LARGE_ACCOUNT_FLAG[0],
//        :P_EXTRA_LARGE_ACCOUNT_FLAG[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN.LOGIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_LOGIN",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(23,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER);
   __sJT_st.setString(3,P_PASSWORD);
   __sJT_st.setString(4,P_JSESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_BROWSER_TYPE);
   __sJT_st.setString(7,P_BROWSER_VERSION);
   __sJT_st.setString(8,P_OS_TYPE);
   __sJT_st.setString(9,P_OS_VERSION);
   __sJT_st.setString(10,P_USER_ENV_ATTRIBUTE1);
   __sJT_st.setString(11,P_USER_ENV_ATTRIBUTE2);
   __sJT_st.setString(12,P_USER_ENV_ATTRIBUTE3);
   __sJT_st.setString(13,P_USER_ENV_ATTRIBUTE4);
   __sJT_st.setString(14,P_USER_ENV_ATTRIBUTE5);
   __sJT_st.setString(15,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCT_ID[0] = __sJT_st.getBigDecimal(16);
   O_SESSION_ID[0] = (String)__sJT_st.getString(17);
   P_ACCT_LOGIN_STATUS_CODE[0] = (String)__sJT_st.getString(18);
   P_ACCOUNT_STATUS_CODE[0] = (String)__sJT_st.getString(19);
   P_PWD_UPDATE_FLAG[0] = (String)__sJT_st.getString(20);
   P_IS_LOCKED[0] = (String)__sJT_st.getString(21);
   P_LAST_UPDATE_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(22);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(23,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   P_LARGE_ACCOUNT_FLAG[0] = (String)__sJT_st.getString(24);
   P_EXTRA_LARGE_ACCOUNT_FLAG[0] = (String)__sJT_st.getString(25);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:231^46*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/