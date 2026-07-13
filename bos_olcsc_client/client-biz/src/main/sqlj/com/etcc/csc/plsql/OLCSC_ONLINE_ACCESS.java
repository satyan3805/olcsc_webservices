/*@lineinfo:filename=OLCSC_ONLINE_ACCESS*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_ONLINE_ACCESS
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
  public OLCSC_ONLINE_ACCESS() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_ONLINE_ACCESS(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_ONLINE_ACCESS(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_ONLINE_ACCESS(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal SET_EMAIL_VALIDATION_STATUS (
    String P_DOC_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:75^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.SET_EMAIL_VALIDATION_STATUS(
//        :P_DOC_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.SET_EMAIL_VALIDATION_STATUS(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:77^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:83^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.SET_EMAIL_VALIDATION_STATUS(
//        :P_DOC_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.SET_EMAIL_VALIDATION_STATUS(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:85^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PASSWORD_RETRIEVAL (
    String P_SESSION_ID,
    String P_USER_ID,
    String P_IP_ADDRESS,
    java.math.BigDecimal P_EMAIL_EXIST,
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_ANSWER,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:107^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.PASSWORD_RETRIEVAL(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_EMAIL_EXIST,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.PASSWORD_RETRIEVAL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setBigDecimal(5,P_EMAIL_EXIST);
   __sJT_st.setString(6,P_DOC_ID);
   __sJT_st.setString(7,P_DOC_TYPE);
   __sJT_st.setString(8,P_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:115^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:121^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.PASSWORD_RETRIEVAL(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_EMAIL_EXIST,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.PASSWORD_RETRIEVAL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setBigDecimal(5,P_EMAIL_EXIST);
   __sJT_st.setString(6,P_DOC_ID);
   __sJT_st.setString(7,P_DOC_TYPE);
   __sJT_st.setString(8,P_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:129^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CONTACT_US (
    String P_SESSION_ID,
    java.math.BigDecimal P_DOCUMENT_ID,
    String P_DOCUMENT_TYPE,
    String P_LIC_STATE,
    String P_LIC_PLATE,
    String P_REPLY_ADDR,
    String P_COMMENT)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:150^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.CONTACT_US(
//        :P_SESSION_ID,
//        :P_DOCUMENT_ID,
//        :P_DOCUMENT_TYPE,
//        :P_LIC_STATE,
//        :P_LIC_PLATE,
//        :P_REPLY_ADDR,
//        :P_COMMENT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.CONTACT_US(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setBigDecimal(3,P_DOCUMENT_ID);
   __sJT_st.setString(4,P_DOCUMENT_TYPE);
   __sJT_st.setString(5,P_LIC_STATE);
   __sJT_st.setString(6,P_LIC_PLATE);
   __sJT_st.setString(7,P_REPLY_ADDR);
   __sJT_st.setString(8,P_COMMENT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:157^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:163^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.CONTACT_US(
//        :P_SESSION_ID,
//        :P_DOCUMENT_ID,
//        :P_DOCUMENT_TYPE,
//        :P_LIC_STATE,
//        :P_LIC_PLATE,
//        :P_REPLY_ADDR,
//        :P_COMMENT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.CONTACT_US(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setBigDecimal(3,P_DOCUMENT_ID);
   __sJT_st.setString(4,P_DOCUMENT_TYPE);
   __sJT_st.setString(5,P_LIC_STATE);
   __sJT_st.setString(6,P_LIC_PLATE);
   __sJT_st.setString(7,P_REPLY_ADDR);
   __sJT_st.setString(8,P_COMMENT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:170^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_EMAIL_VALIDATION_DATA (
    String P_VALIDATION_ID,
    String P_SESSION_ID,
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
    java.math.BigDecimal O_VALIDATION_STATUS[],
    java.math.BigDecimal O_ACCT_ID[],
    String O_EMAIL_ADDRESS[],
    String O_SECURITY_QUESTION[],
    java.math.BigDecimal O_SECURITY_Q_ID[],
    String O_LOGIN_ID[],
    String O_SESSION_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:204^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.GET_EMAIL_VALIDATION_DATA(
//        :P_VALIDATION_ID,
//        :P_SESSION_ID,
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
//        :O_VALIDATION_STATUS[0],
//        :O_ACCT_ID[0],
//        :O_EMAIL_ADDRESS[0],
//        :O_SECURITY_QUESTION[0],
//        :O_SECURITY_Q_ID[0],
//        :O_LOGIN_ID[0],
//        :O_SESSION_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.GET_EMAIL_VALIDATION_DATA(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_VALIDATION_ID);
   __sJT_st.setString(3,P_SESSION_ID);
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
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VALIDATION_STATUS[0] = __sJT_st.getBigDecimal(14);
   O_ACCT_ID[0] = __sJT_st.getBigDecimal(15);
   O_EMAIL_ADDRESS[0] = (String)__sJT_st.getString(16);
   O_SECURITY_QUESTION[0] = (String)__sJT_st.getString(17);
   O_SECURITY_Q_ID[0] = __sJT_st.getBigDecimal(18);
   O_LOGIN_ID[0] = (String)__sJT_st.getString(19);
   O_SESSION_ID[0] = (String)__sJT_st.getString(20);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(21,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:224^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:230^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.GET_EMAIL_VALIDATION_DATA(
//        :P_VALIDATION_ID,
//        :P_SESSION_ID,
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
//        :O_VALIDATION_STATUS[0],
//        :O_ACCT_ID[0],
//        :O_EMAIL_ADDRESS[0],
//        :O_SECURITY_QUESTION[0],
//        :O_SECURITY_Q_ID[0],
//        :O_LOGIN_ID[0],
//        :O_SESSION_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.GET_EMAIL_VALIDATION_DATA(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(19,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(20,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_VALIDATION_ID);
   __sJT_st.setString(3,P_SESSION_ID);
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
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VALIDATION_STATUS[0] = __sJT_st.getBigDecimal(14);
   O_ACCT_ID[0] = __sJT_st.getBigDecimal(15);
   O_EMAIL_ADDRESS[0] = (String)__sJT_st.getString(16);
   O_SECURITY_QUESTION[0] = (String)__sJT_st.getString(17);
   O_SECURITY_Q_ID[0] = __sJT_st.getBigDecimal(18);
   O_LOGIN_ID[0] = (String)__sJT_st.getString(19);
   O_SESSION_ID[0] = (String)__sJT_st.getString(20);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(21,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:250^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHGPASSWORD_URL (
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
    String P_EMAIL_ADDRESS[],
    String P_SOURCE_USER_NAME,
    String O_SESSION_ID[],
    String O_SECURITY_QUESTION[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:282^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.CHGPASSWORD_URL(
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
//        :P_EMAIL_ADDRESS[0],
//        :P_SOURCE_USER_NAME,
//        :O_SESSION_ID[0],
//        :O_SECURITY_QUESTION[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.CHGPASSWORD_URL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(19,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
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
   __sJT_st.setString(15,P_EMAIL_ADDRESS[0]);
   __sJT_st.setString(16,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = (String)__sJT_st.getString(2);
   P_EMAIL_ADDRESS[0] = (String)__sJT_st.getString(15);
   O_SESSION_ID[0] = (String)__sJT_st.getString(17);
   O_SECURITY_QUESTION[0] = (String)__sJT_st.getString(18);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(19,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:300^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:306^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.CHGPASSWORD_URL(
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
//        :P_EMAIL_ADDRESS[0],
//        :P_SOURCE_USER_NAME,
//        :O_SESSION_ID[0],
//        :O_SECURITY_QUESTION[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.CHGPASSWORD_URL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(19,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
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
   __sJT_st.setString(15,P_EMAIL_ADDRESS[0]);
   __sJT_st.setString(16,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = (String)__sJT_st.getString(2);
   P_EMAIL_ADDRESS[0] = (String)__sJT_st.getString(15);
   O_SESSION_ID[0] = (String)__sJT_st.getString(17);
   O_SECURITY_QUESTION[0] = (String)__sJT_st.getString(18);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(19,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:324^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VALIDATE_EMAIL_ADDR (
    String P_SESSION_ID,
    String P_USER_ID,
    String P_IP_ADDRESS,
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_EMAIL_ADDRESS,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:345^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.VALIDATE_EMAIL_ADDR(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_EMAIL_ADDRESS,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.VALIDATE_EMAIL_ADDR(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:352^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:358^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.VALIDATE_EMAIL_ADDR(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_EMAIL_ADDRESS,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.VALIDATE_EMAIL_ADDR(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:365^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal ADD_SEC_QUESTION (
    String P_SESSION_ID,
    String P_USER_ID,
    String P_IP_ADDRESS,
    String P_DOC_ID,
    String P_DOC_TYPE,
    java.math.BigDecimal P_EMAIL_EXIST,
    String P_SEC_QUESTION,
    String P_SEC_ANSWER,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:388^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.ADD_SEC_QUESTION(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_EMAIL_EXIST,
//        :P_SEC_QUESTION,
//        :P_SEC_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.ADD_SEC_QUESTION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setBigDecimal(7,P_EMAIL_EXIST);
   __sJT_st.setString(8,P_SEC_QUESTION);
   __sJT_st.setString(9,P_SEC_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:397^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:403^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.ADD_SEC_QUESTION(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_EMAIL_EXIST,
//        :P_SEC_QUESTION,
//        :P_SEC_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.ADD_SEC_QUESTION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setBigDecimal(7,P_EMAIL_EXIST);
   __sJT_st.setString(8,P_SEC_QUESTION);
   __sJT_st.setString(9,P_SEC_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:412^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal FORGOT_PWD_STEP1 (
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
    String O_SECURITY_QUESTION[],
    String O_EMAIL_ADDR[],
    String O_SESSION_ID[],
    java.math.BigDecimal O_SECURITY_QUESTION_ID[],
    String O_LOGIN_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:451^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.FORGOT_PWD_STEP1(
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
//        :O_SECURITY_QUESTION[0],
//        :O_EMAIL_ADDR[0],
//        :O_SESSION_ID[0],
//        :O_SECURITY_QUESTION_ID[0],
//        :O_LOGIN_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.FORGOT_PWD_STEP1(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(26,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
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
   O_SECURITY_QUESTION[0] = (String)__sJT_st.getString(21);
   O_EMAIL_ADDR[0] = (String)__sJT_st.getString(22);
   O_SESSION_ID[0] = (String)__sJT_st.getString(23);
   O_SECURITY_QUESTION_ID[0] = __sJT_st.getBigDecimal(24);
   O_LOGIN_ID[0] = (String)__sJT_st.getString(25);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(26,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:476^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:482^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ONLINE_ACCESS.FORGOT_PWD_STEP1(
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
//        :O_SECURITY_QUESTION[0],
//        :O_EMAIL_ADDR[0],
//        :O_SESSION_ID[0],
//        :O_SECURITY_QUESTION_ID[0],
//        :O_LOGIN_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ONLINE_ACCESS.FORGOT_PWD_STEP1(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_ONLINE_ACCESS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(21,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(22,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(26,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
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
   O_SECURITY_QUESTION[0] = (String)__sJT_st.getString(21);
   O_EMAIL_ADDR[0] = (String)__sJT_st.getString(22);
   O_SESSION_ID[0] = (String)__sJT_st.getString(23);
   O_SECURITY_QUESTION_ID[0] = __sJT_st.getBigDecimal(24);
   O_LOGIN_ID[0] = (String)__sJT_st.getString(25);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(26,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:507^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/