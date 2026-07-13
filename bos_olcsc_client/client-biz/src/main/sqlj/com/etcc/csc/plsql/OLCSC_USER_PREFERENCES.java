/*@lineinfo:filename=OLCSC_USER_PREFERENCES*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_USER_PREFERENCES
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
  public OLCSC_USER_PREFERENCES() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_USER_PREFERENCES(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_USER_PREFERENCES(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_USER_PREFERENCES(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal OLCSC_UPDATE_OPTIN (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_LOGIN_PWD,
    String P_PUSH_NOTIFY_OTPIN,
    String O_PUSH_NOTIFY_OTPIN[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:82^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_UPDATE_OPTIN(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_LOGIN_PWD,
//        :P_PUSH_NOTIFY_OTPIN,
//        :O_PUSH_NOTIFY_OTPIN[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_UPDATE_OPTIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_LOGIN_PWD);
   __sJT_st.setString(8,P_PUSH_NOTIFY_OTPIN);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PUSH_NOTIFY_OTPIN[0] = (String)__sJT_st.getString(9);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:91^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:97^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_UPDATE_OPTIN(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_LOGIN_PWD,
//        :P_PUSH_NOTIFY_OTPIN,
//        :O_PUSH_NOTIFY_OTPIN[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_UPDATE_OPTIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_LOGIN_PWD);
   __sJT_st.setString(8,P_PUSH_NOTIFY_OTPIN);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PUSH_NOTIFY_OTPIN[0] = (String)__sJT_st.getString(9);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:106^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_PREFERENCES (
    String P_USER_ID,
    java.math.BigDecimal P_ACCT_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    OLC_IOP_ACCT_INFO_ARR P_IOP_ACCT_INFO_ARR[],
    OLC_ACCT_NOTIFICATION_ARR P_ACCT_NOTIFICATION_ARR[],
    String P_POSTAL_FLAG[],
    String P_EMAIL_FLAG[],
    String P_SMS_FLAG[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:130^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.GET_PREFERENCES(
//        :P_USER_ID,
//        :P_ACCT_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_IOP_ACCT_INFO_ARR[0],
//        :P_ACCT_NOTIFICATION_ARR[0],
//        :P_POSTAL_FLAG[0],
//        :P_EMAIL_FLAG[0],
//        :P_SMS_FLAG[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.GET_PREFERENCES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_IOP_ACCT_INFO_ARR");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCT_NOTIFICATION_ARR");
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_IOP_ACCT_INFO_ARR[0] = (com.etcc.csc.plsql.OLC_IOP_ACCT_INFO_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_IOP_ACCT_INFO_ARR.getORADataFactory());
   P_ACCT_NOTIFICATION_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_NOTIFICATION_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCT_NOTIFICATION_ARR.getORADataFactory());
   P_POSTAL_FLAG[0] = (String)__sJT_st.getString(8);
   P_EMAIL_FLAG[0] = (String)__sJT_st.getString(9);
   P_SMS_FLAG[0] = (String)__sJT_st.getString(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:140^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:146^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.GET_PREFERENCES(
//        :P_USER_ID,
//        :P_ACCT_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_IOP_ACCT_INFO_ARR[0],
//        :P_ACCT_NOTIFICATION_ARR[0],
//        :P_POSTAL_FLAG[0],
//        :P_EMAIL_FLAG[0],
//        :P_SMS_FLAG[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.GET_PREFERENCES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_IOP_ACCT_INFO_ARR");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCT_NOTIFICATION_ARR");
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_IOP_ACCT_INFO_ARR[0] = (com.etcc.csc.plsql.OLC_IOP_ACCT_INFO_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_IOP_ACCT_INFO_ARR.getORADataFactory());
   P_ACCT_NOTIFICATION_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_NOTIFICATION_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCT_NOTIFICATION_ARR.getORADataFactory());
   P_POSTAL_FLAG[0] = (String)__sJT_st.getString(8);
   P_EMAIL_FLAG[0] = (String)__sJT_st.getString(9);
   P_SMS_FLAG[0] = (String)__sJT_st.getString(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:156^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal OLCSC_GET_OPTIN (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_LOGIN_PWD,
    String O_PUSH_NOTIFY_OTPIN[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:178^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_GET_OPTIN(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_LOGIN_PWD,
//        :O_PUSH_NOTIFY_OTPIN[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_GET_OPTIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_LOGIN_PWD);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PUSH_NOTIFY_OTPIN[0] = (String)__sJT_st.getString(8);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:186^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:192^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_GET_OPTIN(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_LOGIN_PWD,
//        :O_PUSH_NOTIFY_OTPIN[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.OLCSC_GET_OPTIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_LOGIN_PWD);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PUSH_NOTIFY_OTPIN[0] = (String)__sJT_st.getString(8);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:200^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_PREFERENCES (
    String P_USER_ID,
    java.math.BigDecimal P_ACCT_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    OLC_IOP_ACCT_INFO_ARR P_IOP_ACCT_INFO_ARR,
    OLC_ACCT_NOTIFICATION_ARR P_ACCT_NOTIFICATION_ARR,
    String P_NOTIFICATION_FORMAT,
    String P_POSTAL_FLAG,
    String P_EMAIL_FLAG,
    String P_SMS_FLAG,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:225^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.SET_PREFERENCES(
//        :P_USER_ID,
//        :P_ACCT_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_IOP_ACCT_INFO_ARR,
//        :P_ACCT_NOTIFICATION_ARR,
//        :P_NOTIFICATION_FORMAT,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_SMS_FLAG,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.SET_PREFERENCES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   if (P_IOP_ACCT_INFO_ARR==null) __sJT_st.setNull(6,2003,"OL_OWNER.OLC_IOP_ACCT_INFO_ARR"); else __sJT_st.setORAData(6,P_IOP_ACCT_INFO_ARR);
   if (P_ACCT_NOTIFICATION_ARR==null) __sJT_st.setNull(7,2003,"OL_OWNER.OLC_ACCT_NOTIFICATION_ARR"); else __sJT_st.setORAData(7,P_ACCT_NOTIFICATION_ARR);
   __sJT_st.setString(8,P_NOTIFICATION_FORMAT);
   __sJT_st.setString(9,P_POSTAL_FLAG);
   __sJT_st.setString(10,P_EMAIL_FLAG);
   __sJT_st.setString(11,P_SMS_FLAG);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:236^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:242^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_USER_PREFERENCES.SET_PREFERENCES(
//        :P_USER_ID,
//        :P_ACCT_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_IOP_ACCT_INFO_ARR,
//        :P_ACCT_NOTIFICATION_ARR,
//        :P_NOTIFICATION_FORMAT,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_SMS_FLAG,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_USER_PREFERENCES.SET_PREFERENCES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_USER_PREFERENCES",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   if (P_IOP_ACCT_INFO_ARR==null) __sJT_st.setNull(6,2003,"OL_OWNER.OLC_IOP_ACCT_INFO_ARR"); else __sJT_st.setORAData(6,P_IOP_ACCT_INFO_ARR);
   if (P_ACCT_NOTIFICATION_ARR==null) __sJT_st.setNull(7,2003,"OL_OWNER.OLC_ACCT_NOTIFICATION_ARR"); else __sJT_st.setORAData(7,P_ACCT_NOTIFICATION_ARR);
   __sJT_st.setString(8,P_NOTIFICATION_FORMAT);
   __sJT_st.setString(9,P_POSTAL_FLAG);
   __sJT_st.setString(10,P_EMAIL_FLAG);
   __sJT_st.setString(11,P_SMS_FLAG);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:253^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/