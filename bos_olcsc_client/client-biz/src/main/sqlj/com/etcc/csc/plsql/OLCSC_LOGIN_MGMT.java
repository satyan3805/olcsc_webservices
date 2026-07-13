/*@lineinfo:filename=OLCSC_LOGIN_MGMT*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_LOGIN_MGMT
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
  public OLCSC_LOGIN_MGMT() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_LOGIN_MGMT(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_LOGIN_MGMT(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_LOGIN_MGMT(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public void CLEAR_FAILED_LOGINS (
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:73^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_LOGIN_MGMT.CLEAR_FAILED_LOGINS(
//        :P_ACCT_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_LOGIN_MGMT.CLEAR_FAILED_LOGINS(\n       :1  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:74^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:80^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_LOGIN_MGMT.CLEAR_FAILED_LOGINS(
//        :P_ACCT_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_LOGIN_MGMT.CLEAR_FAILED_LOGINS(\n       :1  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:81^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public String GENERATE_TEMP_PASSWORD ()
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:94^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.GENERATE_TEMP_PASSWORD())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.GENERATE_TEMP_PASSWORD()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:94^111*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:100^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.GENERATE_TEMP_PASSWORD())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.GENERATE_TEMP_PASSWORD()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:100^111*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHANGE_LOGIN_ID (
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_LOGIN_ID,
    String P_OLD_LOGIN_ID,
    String P_LOGIN_PW,
    String P_ACCT_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:121^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_LOGIN_ID(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_OLD_LOGIN_ID,
//        :P_LOGIN_PW,
//        :P_ACCT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_LOGIN_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LOGIN_ID);
   __sJT_st.setString(5,P_OLD_LOGIN_ID);
   __sJT_st.setString(6,P_LOGIN_PW);
   __sJT_st.setString(7,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:128^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:134^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_LOGIN_ID(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_OLD_LOGIN_ID,
//        :P_LOGIN_PW,
//        :P_ACCT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_LOGIN_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LOGIN_ID);
   __sJT_st.setString(5,P_OLD_LOGIN_ID);
   __sJT_st.setString(6,P_LOGIN_PW);
   __sJT_st.setString(7,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:141^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHANGE_PASSWORD (
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_LOGIN_ID,
    String P_OLD_LOGIN_PW,
    String P_LOGIN_PW,
    String P_ACCT_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:162^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_PASSWORD(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_OLD_LOGIN_PW,
//        :P_LOGIN_PW,
//        :P_ACCT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_PASSWORD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LOGIN_ID);
   __sJT_st.setString(5,P_OLD_LOGIN_PW);
   __sJT_st.setString(6,P_LOGIN_PW);
   __sJT_st.setString(7,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:169^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:175^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_PASSWORD(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_OLD_LOGIN_PW,
//        :P_LOGIN_PW,
//        :P_ACCT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_PASSWORD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LOGIN_ID);
   __sJT_st.setString(5,P_OLD_LOGIN_PW);
   __sJT_st.setString(6,P_LOGIN_PW);
   __sJT_st.setString(7,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:182^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHANGE_EMAIL_SECQ_PW (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_LOGIN_PW,
    String P_OLD_LOGIN_PW,
    String P_EMAIL_ADDRESS,
    java.math.BigDecimal P_SECURITY_QUESTION_ID,
    String P_SECURITY_ANSWER,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:207^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_EMAIL_SECQ_PW(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_LOGIN_PW,
//        :P_OLD_LOGIN_PW,
//        :P_EMAIL_ADDRESS,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_EMAIL_SECQ_PW(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_LOGIN_PW);
   __sJT_st.setString(8,P_OLD_LOGIN_PW);
   __sJT_st.setString(9,P_EMAIL_ADDRESS);
   __sJT_st.setBigDecimal(10,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(11,P_SECURITY_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:218^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:224^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_EMAIL_SECQ_PW(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_LOGIN_PW,
//        :P_OLD_LOGIN_PW,
//        :P_EMAIL_ADDRESS,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_EMAIL_SECQ_PW(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_LOGIN_PW);
   __sJT_st.setString(8,P_OLD_LOGIN_PW);
   __sJT_st.setString(9,P_EMAIL_ADDRESS);
   __sJT_st.setBigDecimal(10,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(11,P_SECURITY_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:235^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHANGE_SECURITY_QUE (
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_LOGIN_ID,
    String P_LOGIN_PW,
    String P_ACCT_ID,
    java.math.BigDecimal P_SECURITY_QUESTION_ID,
    String P_SECURITY_ANSWER,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:257^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_SECURITY_QUE(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_LOGIN_PW,
//        :P_ACCT_ID,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_SECURITY_QUE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LOGIN_ID);
   __sJT_st.setString(5,P_LOGIN_PW);
   __sJT_st.setString(6,P_ACCT_ID);
   __sJT_st.setBigDecimal(7,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(8,P_SECURITY_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:265^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:271^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_SECURITY_QUE(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_LOGIN_PW,
//        :P_ACCT_ID,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.CHANGE_SECURITY_QUE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LOGIN_ID);
   __sJT_st.setString(5,P_LOGIN_PW);
   __sJT_st.setString(6,P_ACCT_ID);
   __sJT_st.setBigDecimal(7,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(8,P_SECURITY_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:279^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal LOGIN_CREATION (
    java.math.BigDecimal O_ACCT_ID[],
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_PLAN,
    String P_FIRST_NAME,
    String P_MIDDLE_INITIAL,
    String P_LAST_NAME,
    String P_EMAIL_ADDRESS,
    String P_ALT_EMAIL,
    String P_USER_ID,
    String P_LOGIN_PW,
    java.math.BigDecimal P_SECURITY_QUESTION_ID,
    String P_SECURITY_ANSWER,
    String P_SOURCE_USER_NAME,
    String O_SESSION_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:310^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_CREATION(
//        :O_ACCT_ID[0],
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_PLAN,
//        :P_FIRST_NAME,
//        :P_MIDDLE_INITIAL,
//        :P_LAST_NAME,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_USER_ID,
//        :P_LOGIN_PW,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
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
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_CREATION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,O_ACCT_ID[0]);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_PLAN);
   __sJT_st.setString(7,P_FIRST_NAME);
   __sJT_st.setString(8,P_MIDDLE_INITIAL);
   __sJT_st.setString(9,P_LAST_NAME);
   __sJT_st.setString(10,P_EMAIL_ADDRESS);
   __sJT_st.setString(11,P_ALT_EMAIL);
   __sJT_st.setString(12,P_USER_ID);
   __sJT_st.setString(13,P_LOGIN_PW);
   __sJT_st.setBigDecimal(14,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(15,P_SECURITY_ANSWER);
   __sJT_st.setString(16,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_ID[0] = __sJT_st.getBigDecimal(2);
   O_SESSION_ID[0] = (String)__sJT_st.getString(17);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(18,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:327^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:333^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_CREATION(
//        :O_ACCT_ID[0],
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_PLAN,
//        :P_FIRST_NAME,
//        :P_MIDDLE_INITIAL,
//        :P_LAST_NAME,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_USER_ID,
//        :P_LOGIN_PW,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
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
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_CREATION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(17,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(18,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,O_ACCT_ID[0]);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_PLAN);
   __sJT_st.setString(7,P_FIRST_NAME);
   __sJT_st.setString(8,P_MIDDLE_INITIAL);
   __sJT_st.setString(9,P_LAST_NAME);
   __sJT_st.setString(10,P_EMAIL_ADDRESS);
   __sJT_st.setString(11,P_ALT_EMAIL);
   __sJT_st.setString(12,P_USER_ID);
   __sJT_st.setString(13,P_LOGIN_PW);
   __sJT_st.setBigDecimal(14,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(15,P_SECURITY_ANSWER);
   __sJT_st.setString(16,P_SOURCE_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_ID[0] = __sJT_st.getBigDecimal(2);
   O_SESSION_ID[0] = (String)__sJT_st.getString(17);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(18,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:350^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void LOG_FAILED_LOGIN (
    java.math.BigDecimal P_FAILURE_TYPE,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER_NAME)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:366^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_LOGIN_MGMT.LOG_FAILED_LOGIN(
//        :P_FAILURE_TYPE,
//        :P_ACCT_ID,
//        :P_USER_NAME)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_LOGIN_MGMT.LOG_FAILED_LOGIN(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_FAILURE_TYPE);
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:369^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:375^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_LOGIN_MGMT.LOG_FAILED_LOGIN(
//        :P_FAILURE_TYPE,
//        :P_ACCT_ID,
//        :P_USER_NAME)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_LOGIN_MGMT.LOG_FAILED_LOGIN(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_FAILURE_TYPE);
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_USER_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:378^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal LOGIN_UPDATE (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_FIRST_NAME,
    String P_MIDDLE_INITIAL,
    String P_LAST_NAME,
    String P_EMAIL_ADDRESS,
    String P_ALT_EMAIL,
    String P_USER_ID,
    String P_LOGIN_PW,
    String P_OLD_USER_ID,
    java.math.BigDecimal P_SECURITY_QUESTION_ID,
    String P_SECURITY_ANSWER,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:406^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_UPDATE(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_FIRST_NAME,
//        :P_MIDDLE_INITIAL,
//        :P_LAST_NAME,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_USER_ID,
//        :P_LOGIN_PW,
//        :P_OLD_USER_ID,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_UPDATE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_FIRST_NAME);
   __sJT_st.setString(7,P_MIDDLE_INITIAL);
   __sJT_st.setString(8,P_LAST_NAME);
   __sJT_st.setString(9,P_EMAIL_ADDRESS);
   __sJT_st.setString(10,P_ALT_EMAIL);
   __sJT_st.setString(11,P_USER_ID);
   __sJT_st.setString(12,P_LOGIN_PW);
   __sJT_st.setString(13,P_OLD_USER_ID);
   __sJT_st.setBigDecimal(14,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(15,P_SECURITY_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:421^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:427^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_UPDATE(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_FIRST_NAME,
//        :P_MIDDLE_INITIAL,
//        :P_LAST_NAME,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_USER_ID,
//        :P_LOGIN_PW,
//        :P_OLD_USER_ID,
//        :P_SECURITY_QUESTION_ID,
//        :P_SECURITY_ANSWER,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_LOGIN_MGMT.LOGIN_UPDATE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_LOGIN_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_FIRST_NAME);
   __sJT_st.setString(7,P_MIDDLE_INITIAL);
   __sJT_st.setString(8,P_LAST_NAME);
   __sJT_st.setString(9,P_EMAIL_ADDRESS);
   __sJT_st.setString(10,P_ALT_EMAIL);
   __sJT_st.setString(11,P_USER_ID);
   __sJT_st.setString(12,P_LOGIN_PW);
   __sJT_st.setString(13,P_OLD_USER_ID);
   __sJT_st.setBigDecimal(14,P_SECURITY_QUESTION_ID);
   __sJT_st.setString(15,P_SECURITY_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:442^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/