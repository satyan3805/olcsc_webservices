/*@lineinfo:filename=OLCSC_OLCSETUP*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_OLCSETUP
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
  public OLCSC_OLCSETUP() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_OLCSETUP(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_OLCSETUP(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_OLCSETUP(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal SET_ACCOUNT_LOGIN_INFO (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_IP_ADDRESS,
    String P_USER,
    String P_USER_ID,
    String P_PASSWORD,
    java.math.BigDecimal P_SECURITY_QUESTION_ID,
    String P_SQ_ANSWER,
    String P_EMAIL,
    String P_ALT_EMAIL,
    OLC_ERROR_MSG_ARR P_ERROR_ARR[],
    boolean P_UPDATE_ACCT,
    boolean P_UI_CALL,
    String O_SESSION_ID[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:87^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_OLCSETUP.SET_ACCOUNT_LOGIN_INFO(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_USER_ID,
//        :P_PASSWORD,
//        :P_SECURITY_QUESTION_ID,
//        :P_SQ_ANSWER,
//        :P_EMAIL,
//        :P_ALT_EMAIL,
//        :P_ERROR_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UPDATE_ACCT),
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_SESSION_ID[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_OLCSETUP.SET_ACCOUNT_LOGIN_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n      SYS.SQLJUTL.INT2BOOL( :13  ),\n      SYS.SQLJUTL.INT2BOOL( :14  ),\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_OLCSETUP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_PASSWORD);
   __sJT_st.setBigDecimal(8,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(9,P_SQ_ANSWER);
   __sJT_st.setString(10,P_EMAIL);
   __sJT_st.setString(11,P_ALT_EMAIL);
   __sJT_st.setBoolean(13,P_UPDATE_ACCT);
   __sJT_st.setBoolean(14,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_SESSION_ID[0] = (String)__sJT_st.getString(15);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:101^32*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:107^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_OLCSETUP.SET_ACCOUNT_LOGIN_INFO(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_USER_ID,
//        :P_PASSWORD,
//        :P_SECURITY_QUESTION_ID,
//        :P_SQ_ANSWER,
//        :P_EMAIL,
//        :P_ALT_EMAIL,
//        :P_ERROR_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UPDATE_ACCT),
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_SESSION_ID[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_OLCSETUP.SET_ACCOUNT_LOGIN_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n      SYS.SQLJUTL.INT2BOOL( :13  ),\n      SYS.SQLJUTL.INT2BOOL( :14  ),\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_OLCSETUP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_PASSWORD);
   __sJT_st.setBigDecimal(8,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(9,P_SQ_ANSWER);
   __sJT_st.setString(10,P_EMAIL);
   __sJT_st.setString(11,P_ALT_EMAIL);
   __sJT_st.setBoolean(13,P_UPDATE_ACCT);
   __sJT_st.setBoolean(14,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_SESSION_ID[0] = (String)__sJT_st.getString(15);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:121^32*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean USERIDALREADYTAKEN (
    String P_USER_ID,
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:137^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_OLCSETUP.USERIDALREADYTAKEN(
//        :P_USER_ID,
//        :P_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_OLCSETUP.USERIDALREADYTAKEN(\n       :2  ,\n       :3  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_OLCSETUP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:139^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:145^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_OLCSETUP.USERIDALREADYTAKEN(
//        :P_USER_ID,
//        :P_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_OLCSETUP.USERIDALREADYTAKEN(\n       :2  ,\n       :3  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_OLCSETUP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setString(2,P_USER_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:147^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_ONLINE_ACCESS_STEP1 (
    String P_DOC_ID[],
    String P_DOC_TYPE,
    String P_IP_ADDRESS,
    String P_JSESSION_ID,
    String P_BROWSER_TYPE,
    String P_BROWSER_VERSION,
    String P_OS_TYPE,
    String P_OS_VERSION,
    String P_USER_ENV_ATTRIBUTE1,
    String P_USER_ENV_ATTRIBUTE2,
    String P_USER_ENV_ATTRIBUTE3,
    String P_USER_ENV_ATTRIBUTE4,
    String P_USER_ENV_ATTRIBUTE5,
    String P_TAG_PREFIX,
    String P_TAG_ID,
    String P_DRIVER_LIC_STATE,
    String P_DRIVER_LIC_NBR,
    String P_TAX_ID,
    String P_SOURCE_USER_NAME,
    String O_SESSION_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:182^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_OLCSETUP.SET_ONLINE_ACCESS_STEP1(
//        :P_DOC_ID[0],
//        :P_DOC_TYPE,
//        :P_IP_ADDRESS,
//        :P_JSESSION_ID,
//        :P_BROWSER_TYPE,
//        :P_BROWSER_VERSION,
//        :P_OS_TYPE,
//        :P_OS_VERSION,
//        :P_USER_ENV_ATTRIBUTE1,
//        :P_USER_ENV_ATTRIBUTE2,
//        :P_USER_ENV_ATTRIBUTE3,
//        :P_USER_ENV_ATTRIBUTE4,
//        :P_USER_ENV_ATTRIBUTE5,
//        :P_TAG_PREFIX,
//        :P_TAG_ID,
//        :P_DRIVER_LIC_STATE,
//        :P_DRIVER_LIC_NBR,
//        :P_TAX_ID,
//        :P_SOURCE_USER_NAME,
//        :O_SESSION_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_OLCSETUP.SET_ONLINE_ACCESS_STEP1(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_OLCSETUP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(22,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID[0]);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_JSESSION_ID);
   __sJT_st.setString(6,P_BROWSER_TYPE);
   __sJT_st.setString(7,P_BROWSER_VERSION);
   __sJT_st.setString(8,P_OS_TYPE);
   __sJT_st.setString(9,P_OS_VERSION);
   __sJT_st.setString(10,P_USER_ENV_ATTRIBUTE1);
   __sJT_st.setString(11,P_USER_ENV_ATTRIBUTE2);
   __sJT_st.setString(12,P_USER_ENV_ATTRIBUTE3);
   __sJT_st.setString(13,P_USER_ENV_ATTRIBUTE4);
   __sJT_st.setString(14,P_USER_ENV_ATTRIBUTE5);
   __sJT_st.setString(15,P_TAG_PREFIX);
   __sJT_st.setString(16,P_TAG_ID);
   __sJT_st.setString(17,P_DRIVER_LIC_STATE);
   __sJT_st.setString(18,P_DRIVER_LIC_NBR);
   __sJT_st.setString(19,P_TAX_ID);
   __sJT_st.setString(20,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = (String)__sJT_st.getString(2);
   O_SESSION_ID[0] = (String)__sJT_st.getString(21);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(22,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:203^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:209^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_OLCSETUP.SET_ONLINE_ACCESS_STEP1(
//        :P_DOC_ID[0],
//        :P_DOC_TYPE,
//        :P_IP_ADDRESS,
//        :P_JSESSION_ID,
//        :P_BROWSER_TYPE,
//        :P_BROWSER_VERSION,
//        :P_OS_TYPE,
//        :P_OS_VERSION,
//        :P_USER_ENV_ATTRIBUTE1,
//        :P_USER_ENV_ATTRIBUTE2,
//        :P_USER_ENV_ATTRIBUTE3,
//        :P_USER_ENV_ATTRIBUTE4,
//        :P_USER_ENV_ATTRIBUTE5,
//        :P_TAG_PREFIX,
//        :P_TAG_ID,
//        :P_DRIVER_LIC_STATE,
//        :P_DRIVER_LIC_NBR,
//        :P_TAX_ID,
//        :P_SOURCE_USER_NAME,
//        :O_SESSION_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_OLCSETUP.SET_ONLINE_ACCESS_STEP1(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_OLCSETUP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(22,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID[0]);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_JSESSION_ID);
   __sJT_st.setString(6,P_BROWSER_TYPE);
   __sJT_st.setString(7,P_BROWSER_VERSION);
   __sJT_st.setString(8,P_OS_TYPE);
   __sJT_st.setString(9,P_OS_VERSION);
   __sJT_st.setString(10,P_USER_ENV_ATTRIBUTE1);
   __sJT_st.setString(11,P_USER_ENV_ATTRIBUTE2);
   __sJT_st.setString(12,P_USER_ENV_ATTRIBUTE3);
   __sJT_st.setString(13,P_USER_ENV_ATTRIBUTE4);
   __sJT_st.setString(14,P_USER_ENV_ATTRIBUTE5);
   __sJT_st.setString(15,P_TAG_PREFIX);
   __sJT_st.setString(16,P_TAG_ID);
   __sJT_st.setString(17,P_DRIVER_LIC_STATE);
   __sJT_st.setString(18,P_DRIVER_LIC_NBR);
   __sJT_st.setString(19,P_TAX_ID);
   __sJT_st.setString(20,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = (String)__sJT_st.getString(2);
   O_SESSION_ID[0] = (String)__sJT_st.getString(21);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(22,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:230^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/