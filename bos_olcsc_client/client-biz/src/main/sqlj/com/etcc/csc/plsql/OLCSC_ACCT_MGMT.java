/*@lineinfo:filename=OLCSC_ACCT_MGMT*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_ACCT_MGMT
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
  public OLCSC_ACCT_MGMT() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_ACCT_MGMT(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_ACCT_MGMT(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_ACCT_MGMT(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public void INSERT_OLCSC_ACCOUNT_COMMENT (
    String P_CUSTOM_COMMENT,
    java.math.BigDecimal P_ACT_TYPE,
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:75^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_ACCOUNT_COMMENT(
//        :P_CUSTOM_COMMENT,
//        :P_ACT_TYPE,
//        :P_ACCT_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_ACCOUNT_COMMENT(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_CUSTOM_COMMENT);
   __sJT_st.setBigDecimal(2,P_ACT_TYPE);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:78^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:84^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_ACCOUNT_COMMENT(
//        :P_CUSTOM_COMMENT,
//        :P_ACT_TYPE,
//        :P_ACCT_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_ACCOUNT_COMMENT(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_CUSTOM_COMMENT);
   __sJT_st.setBigDecimal(2,P_ACT_TYPE);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:87^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal GET_ALERTS (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ACCT_ALERT_ARR O_ACCT_ALERT_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:107^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ALERTS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_ALERT_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ALERTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCT_ALERT_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_ALERT_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_ALERT_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCT_ALERT_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:114^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:120^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ALERTS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_ALERT_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ALERTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCT_ALERT_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_ALERT_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_ALERT_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCT_ALERT_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:127^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_STATEMENT_DATES (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    OLC_STTMT_DATES_ARR P_DATE_ARR[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:147^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_DATES(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_DATE_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_DATES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_STTMT_DATES_ARR");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DATE_ARR[0] = (com.etcc.csc.plsql.OLC_STTMT_DATES_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_STTMT_DATES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:153^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:159^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_DATES(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_DATE_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_DATES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_STTMT_DATES_ARR");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DATE_ARR[0] = (com.etcc.csc.plsql.OLC_STTMT_DATES_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_STTMT_DATES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:165^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PERSONAL_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_ACCT_TYPE,
    String P_POSTAL_FLAG,
    String P_EMAIL_FLAG,
    String P_PHONE_FLAG,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_FIRST_NAME,
    String P_LAST_NAME,
    String P_COMPANY_NAME,
    String P_ADDRESS1,
    String P_ADDRESS2,
    String P_ADDRESS3,
    String P_ADDRESS4,
    String P_CITY,
    String P_STATE,
    String P_COUNTRY,
    String P_ZIP_CODE,
    String P_PLUS4,
    String P_HOME_PHO_NBR,
    String P_WORK_PHO_NBR,
    String P_WORK_PHO_EXT,
    String P_MO_STMT_FLAG,
    java.math.BigDecimal P_MS_ID,
    java.math.BigDecimal P_POS_ID,
    java.math.BigDecimal O_RTL_TRXN_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_HOME_PHO_EXT,
    String P_SMS_ALERTS_OPT_IN)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:210^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_ACCT_TYPE,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_PHONE_FLAG,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FIRST_NAME,
//        :P_LAST_NAME,
//        :P_COMPANY_NAME,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :P_MO_STMT_FLAG,
//        :P_MS_ID,
//        :P_POS_ID,
//        :O_RTL_TRXN_ID[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_HOME_PHO_EXT,
//        :P_SMS_ALERTS_OPT_IN))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_ACCT_TYPE);
   __sJT_st.setString(5,P_POSTAL_FLAG);
   __sJT_st.setString(6,P_EMAIL_FLAG);
   __sJT_st.setString(7,P_PHONE_FLAG);
   __sJT_st.setString(8,P_SESSION_ID);
   __sJT_st.setString(9,P_IP_ADDRESS);
   __sJT_st.setString(10,P_USER_ID);
   __sJT_st.setString(11,P_FIRST_NAME);
   __sJT_st.setString(12,P_LAST_NAME);
   __sJT_st.setString(13,P_COMPANY_NAME);
   __sJT_st.setString(14,P_ADDRESS1);
   __sJT_st.setString(15,P_ADDRESS2);
   __sJT_st.setString(16,P_ADDRESS3);
   __sJT_st.setString(17,P_ADDRESS4);
   __sJT_st.setString(18,P_CITY);
   __sJT_st.setString(19,P_STATE);
   __sJT_st.setString(20,P_COUNTRY);
   __sJT_st.setString(21,P_ZIP_CODE);
   __sJT_st.setString(22,P_PLUS4);
   __sJT_st.setString(23,P_HOME_PHO_NBR);
   __sJT_st.setString(24,P_WORK_PHO_NBR);
   __sJT_st.setString(25,P_WORK_PHO_EXT);
   __sJT_st.setString(26,P_MO_STMT_FLAG);
   __sJT_st.setBigDecimal(27,P_MS_ID);
   __sJT_st.setBigDecimal(28,P_POS_ID);
   __sJT_st.setBigDecimal(29,O_RTL_TRXN_ID[0]);
   __sJT_st.setString(31,P_HOME_PHO_EXT);
   __sJT_st.setString(32,P_SMS_ALERTS_OPT_IN);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(29);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:241^30*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:247^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_ACCT_TYPE,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_PHONE_FLAG,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FIRST_NAME,
//        :P_LAST_NAME,
//        :P_COMPANY_NAME,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :P_MO_STMT_FLAG,
//        :P_MS_ID,
//        :P_POS_ID,
//        :O_RTL_TRXN_ID[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_HOME_PHO_EXT,
//        :P_SMS_ALERTS_OPT_IN))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_ACCT_TYPE);
   __sJT_st.setString(5,P_POSTAL_FLAG);
   __sJT_st.setString(6,P_EMAIL_FLAG);
   __sJT_st.setString(7,P_PHONE_FLAG);
   __sJT_st.setString(8,P_SESSION_ID);
   __sJT_st.setString(9,P_IP_ADDRESS);
   __sJT_st.setString(10,P_USER_ID);
   __sJT_st.setString(11,P_FIRST_NAME);
   __sJT_st.setString(12,P_LAST_NAME);
   __sJT_st.setString(13,P_COMPANY_NAME);
   __sJT_st.setString(14,P_ADDRESS1);
   __sJT_st.setString(15,P_ADDRESS2);
   __sJT_st.setString(16,P_ADDRESS3);
   __sJT_st.setString(17,P_ADDRESS4);
   __sJT_st.setString(18,P_CITY);
   __sJT_st.setString(19,P_STATE);
   __sJT_st.setString(20,P_COUNTRY);
   __sJT_st.setString(21,P_ZIP_CODE);
   __sJT_st.setString(22,P_PLUS4);
   __sJT_st.setString(23,P_HOME_PHO_NBR);
   __sJT_st.setString(24,P_WORK_PHO_NBR);
   __sJT_st.setString(25,P_WORK_PHO_EXT);
   __sJT_st.setString(26,P_MO_STMT_FLAG);
   __sJT_st.setBigDecimal(27,P_MS_ID);
   __sJT_st.setBigDecimal(28,P_POS_ID);
   __sJT_st.setBigDecimal(29,O_RTL_TRXN_ID[0]);
   __sJT_st.setString(31,P_HOME_PHO_EXT);
   __sJT_st.setString(32,P_SMS_ALERTS_OPT_IN);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(29);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:278^30*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PERSONAL_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_ACCT_TYPE,
    String P_POSTAL_FLAG,
    String P_EMAIL_FLAG,
    String P_PHONE_FLAG,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_FIRST_NAME,
    String P_LAST_NAME,
    String P_COMPANY_NAME,
    String P_ADDRESS1,
    String P_ADDRESS2,
    String P_ADDRESS3,
    String P_ADDRESS4,
    String P_CITY,
    String P_STATE,
    String P_COUNTRY,
    String P_ZIP_CODE,
    String P_PLUS4,
    String P_HOME_PHO_NBR,
    String P_WORK_PHO_NBR,
    String P_WORK_PHO_EXT,
    String P_MO_STMT_FLAG,
    java.math.BigDecimal P_MS_ID,
    java.math.BigDecimal P_POS_ID,
    java.math.BigDecimal O_RTL_TRXN_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:321^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_ACCT_TYPE,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_PHONE_FLAG,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FIRST_NAME,
//        :P_LAST_NAME,
//        :P_COMPANY_NAME,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :P_MO_STMT_FLAG,
//        :P_MS_ID,
//        :P_POS_ID,
//        :O_RTL_TRXN_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_ACCT_TYPE);
   __sJT_st.setString(5,P_POSTAL_FLAG);
   __sJT_st.setString(6,P_EMAIL_FLAG);
   __sJT_st.setString(7,P_PHONE_FLAG);
   __sJT_st.setString(8,P_SESSION_ID);
   __sJT_st.setString(9,P_IP_ADDRESS);
   __sJT_st.setString(10,P_USER_ID);
   __sJT_st.setString(11,P_FIRST_NAME);
   __sJT_st.setString(12,P_LAST_NAME);
   __sJT_st.setString(13,P_COMPANY_NAME);
   __sJT_st.setString(14,P_ADDRESS1);
   __sJT_st.setString(15,P_ADDRESS2);
   __sJT_st.setString(16,P_ADDRESS3);
   __sJT_st.setString(17,P_ADDRESS4);
   __sJT_st.setString(18,P_CITY);
   __sJT_st.setString(19,P_STATE);
   __sJT_st.setString(20,P_COUNTRY);
   __sJT_st.setString(21,P_ZIP_CODE);
   __sJT_st.setString(22,P_PLUS4);
   __sJT_st.setString(23,P_HOME_PHO_NBR);
   __sJT_st.setString(24,P_WORK_PHO_NBR);
   __sJT_st.setString(25,P_WORK_PHO_EXT);
   __sJT_st.setString(26,P_MO_STMT_FLAG);
   __sJT_st.setBigDecimal(27,P_MS_ID);
   __sJT_st.setBigDecimal(28,P_POS_ID);
   __sJT_st.setBigDecimal(29,O_RTL_TRXN_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(29);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:350^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:356^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_ACCT_TYPE,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_PHONE_FLAG,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FIRST_NAME,
//        :P_LAST_NAME,
//        :P_COMPANY_NAME,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :P_MO_STMT_FLAG,
//        :P_MS_ID,
//        :P_POS_ID,
//        :O_RTL_TRXN_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_ACCT_TYPE);
   __sJT_st.setString(5,P_POSTAL_FLAG);
   __sJT_st.setString(6,P_EMAIL_FLAG);
   __sJT_st.setString(7,P_PHONE_FLAG);
   __sJT_st.setString(8,P_SESSION_ID);
   __sJT_st.setString(9,P_IP_ADDRESS);
   __sJT_st.setString(10,P_USER_ID);
   __sJT_st.setString(11,P_FIRST_NAME);
   __sJT_st.setString(12,P_LAST_NAME);
   __sJT_st.setString(13,P_COMPANY_NAME);
   __sJT_st.setString(14,P_ADDRESS1);
   __sJT_st.setString(15,P_ADDRESS2);
   __sJT_st.setString(16,P_ADDRESS3);
   __sJT_st.setString(17,P_ADDRESS4);
   __sJT_st.setString(18,P_CITY);
   __sJT_st.setString(19,P_STATE);
   __sJT_st.setString(20,P_COUNTRY);
   __sJT_st.setString(21,P_ZIP_CODE);
   __sJT_st.setString(22,P_PLUS4);
   __sJT_st.setString(23,P_HOME_PHO_NBR);
   __sJT_st.setString(24,P_WORK_PHO_NBR);
   __sJT_st.setString(25,P_WORK_PHO_EXT);
   __sJT_st.setString(26,P_MO_STMT_FLAG);
   __sJT_st.setBigDecimal(27,P_MS_ID);
   __sJT_st.setBigDecimal(28,P_POS_ID);
   __sJT_st.setBigDecimal(29,O_RTL_TRXN_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(29);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:385^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PERSONAL_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_ACCT_TYPE,
    String P_POSTAL_FLAG,
    String P_EMAIL_FLAG,
    String P_PHONE_FLAG,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_FIRST_NAME,
    String P_LAST_NAME,
    String P_COMPANY_NAME,
    String P_COMPANY_TAX_ID,
    String P_ADDRESS1,
    String P_ADDRESS2,
    String P_ADDRESS3,
    String P_ADDRESS4,
    String P_CITY,
    String P_STATE,
    String P_COUNTRY,
    String P_ZIP_CODE,
    String P_PLUS4,
    String P_HOME_PHO_NBR,
    String P_WORK_PHO_NBR,
    String P_WORK_PHO_EXT,
    String P_DRIVER_LIC_NBR,
    String P_DRIVER_LIC_STATE,
    String P_MO_STMT_FLAG,
    java.math.BigDecimal P_MS_ID,
    java.math.BigDecimal P_POS_ID,
    String O_DUPE_DL_FLAG[],
    java.math.BigDecimal O_RTL_TRXN_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:432^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_ACCT_TYPE,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_PHONE_FLAG,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FIRST_NAME,
//        :P_LAST_NAME,
//        :P_COMPANY_NAME,
//        :P_COMPANY_TAX_ID,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :P_DRIVER_LIC_NBR,
//        :P_DRIVER_LIC_STATE,
//        :P_MO_STMT_FLAG,
//        :P_MS_ID,
//        :P_POS_ID,
//        :O_DUPE_DL_FLAG[0],
//        :O_RTL_TRXN_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  ,\n       :34  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(34,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_ACCT_TYPE);
   __sJT_st.setString(5,P_POSTAL_FLAG);
   __sJT_st.setString(6,P_EMAIL_FLAG);
   __sJT_st.setString(7,P_PHONE_FLAG);
   __sJT_st.setString(8,P_SESSION_ID);
   __sJT_st.setString(9,P_IP_ADDRESS);
   __sJT_st.setString(10,P_USER_ID);
   __sJT_st.setString(11,P_FIRST_NAME);
   __sJT_st.setString(12,P_LAST_NAME);
   __sJT_st.setString(13,P_COMPANY_NAME);
   __sJT_st.setString(14,P_COMPANY_TAX_ID);
   __sJT_st.setString(15,P_ADDRESS1);
   __sJT_st.setString(16,P_ADDRESS2);
   __sJT_st.setString(17,P_ADDRESS3);
   __sJT_st.setString(18,P_ADDRESS4);
   __sJT_st.setString(19,P_CITY);
   __sJT_st.setString(20,P_STATE);
   __sJT_st.setString(21,P_COUNTRY);
   __sJT_st.setString(22,P_ZIP_CODE);
   __sJT_st.setString(23,P_PLUS4);
   __sJT_st.setString(24,P_HOME_PHO_NBR);
   __sJT_st.setString(25,P_WORK_PHO_NBR);
   __sJT_st.setString(26,P_WORK_PHO_EXT);
   __sJT_st.setString(27,P_DRIVER_LIC_NBR);
   __sJT_st.setString(28,P_DRIVER_LIC_STATE);
   __sJT_st.setString(29,P_MO_STMT_FLAG);
   __sJT_st.setBigDecimal(30,P_MS_ID);
   __sJT_st.setBigDecimal(31,P_POS_ID);
   __sJT_st.setString(32,O_DUPE_DL_FLAG[0]);
   __sJT_st.setBigDecimal(33,O_RTL_TRXN_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_DUPE_DL_FLAG[0] = (String)__sJT_st.getString(32);
   O_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(33);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(34,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:465^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:471^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_ACCT_TYPE,
//        :P_POSTAL_FLAG,
//        :P_EMAIL_FLAG,
//        :P_PHONE_FLAG,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FIRST_NAME,
//        :P_LAST_NAME,
//        :P_COMPANY_NAME,
//        :P_COMPANY_TAX_ID,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :P_DRIVER_LIC_NBR,
//        :P_DRIVER_LIC_STATE,
//        :P_MO_STMT_FLAG,
//        :P_MS_ID,
//        :P_POS_ID,
//        :O_DUPE_DL_FLAG[0],
//        :O_RTL_TRXN_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.PERSONAL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  ,\n       :34  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(34,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_ACCT_TYPE);
   __sJT_st.setString(5,P_POSTAL_FLAG);
   __sJT_st.setString(6,P_EMAIL_FLAG);
   __sJT_st.setString(7,P_PHONE_FLAG);
   __sJT_st.setString(8,P_SESSION_ID);
   __sJT_st.setString(9,P_IP_ADDRESS);
   __sJT_st.setString(10,P_USER_ID);
   __sJT_st.setString(11,P_FIRST_NAME);
   __sJT_st.setString(12,P_LAST_NAME);
   __sJT_st.setString(13,P_COMPANY_NAME);
   __sJT_st.setString(14,P_COMPANY_TAX_ID);
   __sJT_st.setString(15,P_ADDRESS1);
   __sJT_st.setString(16,P_ADDRESS2);
   __sJT_st.setString(17,P_ADDRESS3);
   __sJT_st.setString(18,P_ADDRESS4);
   __sJT_st.setString(19,P_CITY);
   __sJT_st.setString(20,P_STATE);
   __sJT_st.setString(21,P_COUNTRY);
   __sJT_st.setString(22,P_ZIP_CODE);
   __sJT_st.setString(23,P_PLUS4);
   __sJT_st.setString(24,P_HOME_PHO_NBR);
   __sJT_st.setString(25,P_WORK_PHO_NBR);
   __sJT_st.setString(26,P_WORK_PHO_EXT);
   __sJT_st.setString(27,P_DRIVER_LIC_NBR);
   __sJT_st.setString(28,P_DRIVER_LIC_STATE);
   __sJT_st.setString(29,P_MO_STMT_FLAG);
   __sJT_st.setBigDecimal(30,P_MS_ID);
   __sJT_st.setBigDecimal(31,P_POS_ID);
   __sJT_st.setString(32,O_DUPE_DL_FLAG[0]);
   __sJT_st.setBigDecimal(33,O_RTL_TRXN_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_DUPE_DL_FLAG[0] = (String)__sJT_st.getString(32);
   O_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(33);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(34,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:504^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_ACCOUNT_INFO (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_POSTAL_FLAG[],
    String P_EMAIL_FLAG[],
    String P_PHONE_FLAG[],
    String P_REVENUE_ACCT[],
    OLC_ACCOUNT_INFO_REC O_ACCT_INFO[],
    String O_ACCT_VPN_TYPE[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    boolean P_UI_CALL,
    String O_PMT_PLAN_FLAG[],
    java.math.BigDecimal O_PMT_PLAN_TOTAL_DUE[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:533^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_INFO(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_POSTAL_FLAG[0],
//        :P_EMAIL_FLAG[0],
//        :P_PHONE_FLAG[0],
//        :P_REVENUE_ACCT[0],
//        :O_ACCT_INFO[0],
//        :O_ACCT_VPN_TYPE[0],
//        :O_ERROR_MSG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_PMT_PLAN_FLAG[0],
//        :O_PMT_PLAN_TOTAL_DUE[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n      SYS.SQLJUTL.INT2BOOL( :14  ),\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2002,"OL_OWNER.OLC_ACCOUNT_INFO_REC");
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBoolean(14,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_POSTAL_FLAG[0] = (String)__sJT_st.getString(7);
   P_EMAIL_FLAG[0] = (String)__sJT_st.getString(8);
   P_PHONE_FLAG[0] = (String)__sJT_st.getString(9);
   P_REVENUE_ACCT[0] = (String)__sJT_st.getString(10);
   O_ACCT_INFO[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC.getORADataFactory());
   O_ACCT_VPN_TYPE[0] = (String)__sJT_st.getString(12);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_PMT_PLAN_FLAG[0] = (String)__sJT_st.getString(15);
   O_PMT_PLAN_TOTAL_DUE[0] = __sJT_st.getBigDecimal(16);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:548^40*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:554^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_INFO(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_POSTAL_FLAG[0],
//        :P_EMAIL_FLAG[0],
//        :P_PHONE_FLAG[0],
//        :P_REVENUE_ACCT[0],
//        :O_ACCT_INFO[0],
//        :O_ACCT_VPN_TYPE[0],
//        :O_ERROR_MSG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_PMT_PLAN_FLAG[0],
//        :O_PMT_PLAN_TOTAL_DUE[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n      SYS.SQLJUTL.INT2BOOL( :14  ),\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2002,"OL_OWNER.OLC_ACCOUNT_INFO_REC");
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBoolean(14,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_POSTAL_FLAG[0] = (String)__sJT_st.getString(7);
   P_EMAIL_FLAG[0] = (String)__sJT_st.getString(8);
   P_PHONE_FLAG[0] = (String)__sJT_st.getString(9);
   P_REVENUE_ACCT[0] = (String)__sJT_st.getString(10);
   O_ACCT_INFO[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC.getORADataFactory());
   O_ACCT_VPN_TYPE[0] = (String)__sJT_st.getString(12);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_PMT_PLAN_FLAG[0] = (String)__sJT_st.getString(15);
   O_PMT_PLAN_TOTAL_DUE[0] = __sJT_st.getBigDecimal(16);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:569^40*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_TAG_INFO_BYOT (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    String P_SEARCH_VALUE,
    OLC_ACCOUNT_TAG_LICPLATE_ARR P_ACCOUNT_TAG_ARR[],
    OLC_ACCOUNT_TAG_LICPLATE_ARR P_LIC_PLATE_TAG_ARR[],
    boolean P_UI_CALL,
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:592^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO_BYOT(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SEARCH_VALUE,
//        :P_ACCOUNT_TAG_ARR[0],
//        :P_LIC_PLATE_TAG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n      SYS.SQLJUTL.INT2BOOL( :9  ),\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCOUNT_TAG_LICPLATE_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_TAG_LICPLATE_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_SEARCH_VALUE);
   __sJT_st.setBoolean(9,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCOUNT_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR.getORADataFactory());
   P_LIC_PLATE_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:601^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:607^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO_BYOT(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SEARCH_VALUE,
//        :P_ACCOUNT_TAG_ARR[0],
//        :P_LIC_PLATE_TAG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n      SYS.SQLJUTL.INT2BOOL( :9  ),\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCOUNT_TAG_LICPLATE_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_TAG_LICPLATE_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_SEARCH_VALUE);
   __sJT_st.setBoolean(9,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCOUNT_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR.getORADataFactory());
   P_LIC_PLATE_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_LICPLATE_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:616^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_ACCT_DOCUMENTS (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.sql.ResultSet O_ACCT_DOCUMENTS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:637^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DOCUMENTS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_DOCUMENTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DOCUMENTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_DOCUMENTS[0] = (java.sql.ResultSet)__sJT_st.getCursor(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:644^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:650^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DOCUMENTS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_DOCUMENTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DOCUMENTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_DOCUMENTS[0] = (java.sql.ResultSet)__sJT_st.getCursor(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:657^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_ACCT_STATUS (
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:672^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_STATUS(
//        :P_ACCT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_STATUS(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"18com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:673^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:679^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_STATUS(
//        :P_ACCT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_STATUS(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"19com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:680^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_ACCOUNT_DEVICE_STATUS (
    String P_SESSION_ID,
    String P_USER_ID,
    String P_IP_ADDRESS,
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_FINGERPRINTID,
    String O_MFA_REQUIRED[],
    java.sql.Timestamp O_DEVICE_DATE[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:703^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_DEVICE_STATUS(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_FINGERPRINTID,
//        :O_MFA_REQUIRED[0],
//        :O_DEVICE_DATE[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_DEVICE_STATUS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"20com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_FINGERPRINTID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_MFA_REQUIRED[0] = (String)__sJT_st.getString(8);
   O_DEVICE_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(9);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:712^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:718^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_DEVICE_STATUS(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_FINGERPRINTID,
//        :O_MFA_REQUIRED[0],
//        :O_DEVICE_DATE[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCOUNT_DEVICE_STATUS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"21com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER_ID);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_FINGERPRINTID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_MFA_REQUIRED[0] = (String)__sJT_st.getString(8);
   O_DEVICE_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(9);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:727^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CONFIRM_ADD_TAGS (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_FULFILLMENT_ID[],
    OLC_ACCOUNT_VEH_ID_ARR P_OLC_ACCOUNT_VEH_ID_ARR[],
    String P_DELIVERY_METHOD,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:750^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FULFILLMENT_ID[0],
//        :P_OLC_ACCOUNT_VEH_ID_ARR[0],
//        :P_DELIVERY_METHOD,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"22com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_VEH_ID_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_OLC_ACCOUNT_VEH_ID_ARR[0]==null) __sJT_st.setNull(8,2003,"OL_OWNER.OLC_ACCOUNT_VEH_ID_ARR"); else __sJT_st.setORAData(8,P_OLC_ACCOUNT_VEH_ID_ARR[0]);
   __sJT_st.setString(9,P_DELIVERY_METHOD);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FULFILLMENT_ID[0] = __sJT_st.getBigDecimal(7);
   P_OLC_ACCOUNT_VEH_ID_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_VEH_ID_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_VEH_ID_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:759^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:765^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FULFILLMENT_ID[0],
//        :P_OLC_ACCOUNT_VEH_ID_ARR[0],
//        :P_DELIVERY_METHOD,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"23com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_VEH_ID_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_OLC_ACCOUNT_VEH_ID_ARR[0]==null) __sJT_st.setNull(8,2003,"OL_OWNER.OLC_ACCOUNT_VEH_ID_ARR"); else __sJT_st.setORAData(8,P_OLC_ACCOUNT_VEH_ID_ARR[0]);
   __sJT_st.setString(9,P_DELIVERY_METHOD);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FULFILLMENT_ID[0] = __sJT_st.getBigDecimal(7);
   P_OLC_ACCOUNT_VEH_ID_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_VEH_ID_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_VEH_ID_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:774^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_ACCT_DOCUMENTS (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_FILE_LOCATION,
    String P_FILE_NAME,
    String P_ACTION,
    String P_DOCUMENT_TYPE,
    String P_DESCRIPTION,
    java.math.BigDecimal P_ACCOUNT_DOCUMENT_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:800^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_ACCT_DOCUMENTS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FILE_LOCATION,
//        :P_FILE_NAME,
//        :P_ACTION,
//        :P_DOCUMENT_TYPE,
//        :P_DESCRIPTION,
//        :P_ACCOUNT_DOCUMENT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_ACCT_DOCUMENTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"24com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_FILE_LOCATION);
   __sJT_st.setString(8,P_FILE_NAME);
   __sJT_st.setString(9,P_ACTION);
   __sJT_st.setString(10,P_DOCUMENT_TYPE);
   __sJT_st.setString(11,P_DESCRIPTION);
   __sJT_st.setBigDecimal(12,P_ACCOUNT_DOCUMENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:812^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:818^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_ACCT_DOCUMENTS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FILE_LOCATION,
//        :P_FILE_NAME,
//        :P_ACTION,
//        :P_DOCUMENT_TYPE,
//        :P_DESCRIPTION,
//        :P_ACCOUNT_DOCUMENT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_ACCT_DOCUMENTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"25com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_FILE_LOCATION);
   __sJT_st.setString(8,P_FILE_NAME);
   __sJT_st.setString(9,P_ACTION);
   __sJT_st.setString(10,P_DOCUMENT_TYPE);
   __sJT_st.setString(11,P_DESCRIPTION);
   __sJT_st.setBigDecimal(12,P_ACCOUNT_DOCUMENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:830^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_ACCT_DETAILS_2FA (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_TEMP_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ACCOUNT_INFO_REC O_ACCT_INFO[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:851^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DETAILS_2FA(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_TEMP_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_INFO[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DETAILS_2FA(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"26com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2002,"OL_OWNER.OLC_ACCOUNT_INFO_REC");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_TEMP_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_INFO[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:858^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:864^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DETAILS_2FA(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_TEMP_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_INFO[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_DETAILS_2FA(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"27com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2002,"OL_OWNER.OLC_ACCOUNT_INFO_REC");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_TEMP_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_INFO[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCOUNT_INFO_REC.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:871^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_CONTACT_INFO_BY_CONTACT_ID (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_EMAIL_ADDRESS,
    String P_ALT_EMAIL,
    String P_HOME_PHO_NBR,
    String P_WORK_PHO_NBR,
    String P_WORK_PHO_EXT,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_HOME_PHO_EXT,
    String P_MOBILEPHONE,
    String P_MOBILE_PHO_EXT,
    String P_SMS_ALERTS_OPT_IN,
    java.math.BigDecimal P_HOME_PHONE_ID,
    java.math.BigDecimal P_WORK_PHONE_ID,
    java.math.BigDecimal P_MOBILE_PHONE_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:903^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO_BY_CONTACT_ID(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :O_ERROR_MSG_ARR[0],
//        :P_HOME_PHO_EXT,
//        :P_MOBILEPHONE,
//        :P_MOBILE_PHO_EXT,
//        :P_SMS_ALERTS_OPT_IN,
//        :P_HOME_PHONE_ID,
//        :P_WORK_PHONE_ID,
//        :P_MOBILE_PHONE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO_BY_CONTACT_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"28com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_ALT_EMAIL);
   __sJT_st.setString(9,P_HOME_PHO_NBR);
   __sJT_st.setString(10,P_WORK_PHO_NBR);
   __sJT_st.setString(11,P_WORK_PHO_EXT);
   __sJT_st.setString(13,P_HOME_PHO_EXT);
   __sJT_st.setString(14,P_MOBILEPHONE);
   __sJT_st.setString(15,P_MOBILE_PHO_EXT);
   __sJT_st.setString(16,P_SMS_ALERTS_OPT_IN);
   __sJT_st.setBigDecimal(17,P_HOME_PHONE_ID);
   __sJT_st.setBigDecimal(18,P_WORK_PHONE_ID);
   __sJT_st.setBigDecimal(19,P_MOBILE_PHONE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:921^28*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:927^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO_BY_CONTACT_ID(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :O_ERROR_MSG_ARR[0],
//        :P_HOME_PHO_EXT,
//        :P_MOBILEPHONE,
//        :P_MOBILE_PHO_EXT,
//        :P_SMS_ALERTS_OPT_IN,
//        :P_HOME_PHONE_ID,
//        :P_WORK_PHONE_ID,
//        :P_MOBILE_PHONE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO_BY_CONTACT_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"29com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_ALT_EMAIL);
   __sJT_st.setString(9,P_HOME_PHO_NBR);
   __sJT_st.setString(10,P_WORK_PHO_NBR);
   __sJT_st.setString(11,P_WORK_PHO_EXT);
   __sJT_st.setString(13,P_HOME_PHO_EXT);
   __sJT_st.setString(14,P_MOBILEPHONE);
   __sJT_st.setString(15,P_MOBILE_PHO_EXT);
   __sJT_st.setString(16,P_SMS_ALERTS_OPT_IN);
   __sJT_st.setBigDecimal(17,P_HOME_PHONE_ID);
   __sJT_st.setBigDecimal(18,P_WORK_PHONE_ID);
   __sJT_st.setBigDecimal(19,P_MOBILE_PHONE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:945^28*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void INSERT_OLCSC_FACTOR_AUTH_LOG (
    java.math.BigDecimal I_ACCOUNT_ID,
    String I_FACTOR_AUTHENTICATION_OPERATION,
    String I_EMAIL_ADDRESS,
    String I_PHONE_NUMBER,
    String I_FACTOR_AUTHENTICATION_STATUS,
    String I_COMMENTS)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:964^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_FACTOR_AUTH_LOG(
//        :I_ACCOUNT_ID,
//        :I_FACTOR_AUTHENTICATION_OPERATION,
//        :I_EMAIL_ADDRESS,
//        :I_PHONE_NUMBER,
//        :I_FACTOR_AUTHENTICATION_STATUS,
//        :I_COMMENTS)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_FACTOR_AUTH_LOG(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"30com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,I_ACCOUNT_ID);
   __sJT_st.setString(2,I_FACTOR_AUTHENTICATION_OPERATION);
   __sJT_st.setString(3,I_EMAIL_ADDRESS);
   __sJT_st.setString(4,I_PHONE_NUMBER);
   __sJT_st.setString(5,I_FACTOR_AUTHENTICATION_STATUS);
   __sJT_st.setString(6,I_COMMENTS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:970^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:976^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_FACTOR_AUTH_LOG(
//        :I_ACCOUNT_ID,
//        :I_FACTOR_AUTHENTICATION_OPERATION,
//        :I_EMAIL_ADDRESS,
//        :I_PHONE_NUMBER,
//        :I_FACTOR_AUTHENTICATION_STATUS,
//        :I_COMMENTS)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_ACCT_MGMT.INSERT_OLCSC_FACTOR_AUTH_LOG(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"31com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,I_ACCOUNT_ID);
   __sJT_st.setString(2,I_FACTOR_AUTHENTICATION_OPERATION);
   __sJT_st.setString(3,I_EMAIL_ADDRESS);
   __sJT_st.setString(4,I_PHONE_NUMBER);
   __sJT_st.setString(5,I_FACTOR_AUTHENTICATION_STATUS);
   __sJT_st.setString(6,I_COMMENTS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:982^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal GET_ACCT_NOTIFICATIONS (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.sql.ResultSet O_ACCT_NOTIFICATION[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1002^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_NOTIFICATIONS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_NOTIFICATION[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_NOTIFICATIONS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"32com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_NOTIFICATION[0] = (java.sql.ResultSet)__sJT_st.getCursor(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1009^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1015^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_NOTIFICATIONS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_NOTIFICATION[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_NOTIFICATIONS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"33com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_NOTIFICATION[0] = (java.sql.ResultSet)__sJT_st.getCursor(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1022^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CONFIRM_ADD_TAGS_BYOT (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_FULFILLMENT_ID[],
    OLC_ACCOUNT_VEHID_TAG_ARR P_OLC_ACCOUNT_VEHID_TAG_ARR[],
    String P_DELIVERY_METHOD,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1045^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS_BYOT(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FULFILLMENT_ID[0],
//        :P_OLC_ACCOUNT_VEHID_TAG_ARR[0],
//        :P_DELIVERY_METHOD,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"34com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_VEHID_TAG_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_OLC_ACCOUNT_VEHID_TAG_ARR[0]==null) __sJT_st.setNull(8,2003,"OL_OWNER.OLC_ACCOUNT_VEHID_TAG_ARR"); else __sJT_st.setORAData(8,P_OLC_ACCOUNT_VEHID_TAG_ARR[0]);
   __sJT_st.setString(9,P_DELIVERY_METHOD);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FULFILLMENT_ID[0] = __sJT_st.getBigDecimal(7);
   P_OLC_ACCOUNT_VEHID_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_VEHID_TAG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_VEHID_TAG_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1054^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1060^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS_BYOT(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_FULFILLMENT_ID[0],
//        :P_OLC_ACCOUNT_VEHID_TAG_ARR[0],
//        :P_DELIVERY_METHOD,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.CONFIRM_ADD_TAGS_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"35com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_VEHID_TAG_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_OLC_ACCOUNT_VEHID_TAG_ARR[0]==null) __sJT_st.setNull(8,2003,"OL_OWNER.OLC_ACCOUNT_VEHID_TAG_ARR"); else __sJT_st.setORAData(8,P_OLC_ACCOUNT_VEHID_TAG_ARR[0]);
   __sJT_st.setString(9,P_DELIVERY_METHOD);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FULFILLMENT_ID[0] = __sJT_st.getBigDecimal(7);
   P_OLC_ACCOUNT_VEHID_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_VEHID_TAG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_VEHID_TAG_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1069^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SAVE_ACCOUNT_DEVICE (
    String P_SESSION_ID,
    String P_USER_ID,
    String P_IP_ADDRESS,
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_FINGERPRINTID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1090^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SAVE_ACCOUNT_DEVICE(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_FINGERPRINTID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SAVE_ACCOUNT_DEVICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"36com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_FINGERPRINTID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1097^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1103^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SAVE_ACCOUNT_DEVICE(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_FINGERPRINTID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SAVE_ACCOUNT_DEVICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"37com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_FINGERPRINTID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1110^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_PAYMENT_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_DEFAULT_BLNG_METHD,
    String P_COMING_FROM_PAYMENT,
    java.math.BigDecimal P_MIN_BAL_AMT[],
    java.math.BigDecimal P_REBILL_AMT[],
    java.math.BigDecimal P_PMT_AMT[],
    java.math.BigDecimal P_PREPAID_AMT[],
    boolean P_UI_CALL,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1138^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_PAYMENT_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        :P_DEFAULT_BLNG_METHD,
//        :P_COMING_FROM_PAYMENT,
//        :P_MIN_BAL_AMT[0],
//        :P_REBILL_AMT[0],
//        :P_PMT_AMT[0],
//        :P_PREPAID_AMT[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_PAYMENT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n      SYS.SQLJUTL.INT2BOOL( :14  ),\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"38com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setString(8,P_DEFAULT_BLNG_METHD);
   __sJT_st.setString(9,P_COMING_FROM_PAYMENT);
   __sJT_st.setBigDecimal(12,P_PMT_AMT[0]);
   __sJT_st.setBoolean(14,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(10);
   P_REBILL_AMT[0] = __sJT_st.getBigDecimal(11);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(12);
   P_PREPAID_AMT[0] = __sJT_st.getBigDecimal(13);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1152^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1158^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_PAYMENT_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        :P_DEFAULT_BLNG_METHD,
//        :P_COMING_FROM_PAYMENT,
//        :P_MIN_BAL_AMT[0],
//        :P_REBILL_AMT[0],
//        :P_PMT_AMT[0],
//        :P_PREPAID_AMT[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_PAYMENT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n      SYS.SQLJUTL.INT2BOOL( :14  ),\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"39com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setString(8,P_DEFAULT_BLNG_METHD);
   __sJT_st.setString(9,P_COMING_FROM_PAYMENT);
   __sJT_st.setBigDecimal(12,P_PMT_AMT[0]);
   __sJT_st.setBoolean(14,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(10);
   P_REBILL_AMT[0] = __sJT_st.getBigDecimal(11);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(12);
   P_PREPAID_AMT[0] = __sJT_st.getBigDecimal(13);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1172^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_DEFAULT_STATE (
    String P_COUNTRY)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1187^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_STATE(
//        :P_COUNTRY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_STATE(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"40com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_COUNTRY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1188^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1194^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_STATE(
//        :P_COUNTRY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_STATE(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"41com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_COUNTRY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1195^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VALIDATE_ROUTING_NUMBER (
    String P_ROUTING_NUMBER,
    OLC_ALERT_DISPLAY_ARR O_ALERTS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1212^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_ROUTING_NUMBER(
//        :P_ROUTING_NUMBER,
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_ROUTING_NUMBER(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"42com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_ROUTING_NUMBER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1215^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1221^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_ROUTING_NUMBER(
//        :P_ROUTING_NUMBER,
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_ROUTING_NUMBER(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"43com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_ROUTING_NUMBER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1224^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_CONTACT_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_EMAIL_ADDRESS,
    String P_ALT_EMAIL,
    String P_HOME_PHO_NBR,
    String P_WORK_PHO_NBR,
    String P_WORK_PHO_EXT,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_HOME_PHO_EXT,
    String P_MOBILEPHONE,
    String P_MOBILE_PHO_EXT,
    String P_SMS_ALERTS_OPT_IN)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1253^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :O_ERROR_MSG_ARR[0],
//        :P_HOME_PHO_EXT,
//        :P_MOBILEPHONE,
//        :P_MOBILE_PHO_EXT,
//        :P_SMS_ALERTS_OPT_IN))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"44com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_ALT_EMAIL);
   __sJT_st.setString(9,P_HOME_PHO_NBR);
   __sJT_st.setString(10,P_WORK_PHO_NBR);
   __sJT_st.setString(11,P_WORK_PHO_EXT);
   __sJT_st.setString(13,P_HOME_PHO_EXT);
   __sJT_st.setString(14,P_MOBILEPHONE);
   __sJT_st.setString(15,P_MOBILE_PHO_EXT);
   __sJT_st.setString(16,P_SMS_ALERTS_OPT_IN);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1268^30*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1274^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :O_ERROR_MSG_ARR[0],
//        :P_HOME_PHO_EXT,
//        :P_MOBILEPHONE,
//        :P_MOBILE_PHO_EXT,
//        :P_SMS_ALERTS_OPT_IN))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"45com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_ALT_EMAIL);
   __sJT_st.setString(9,P_HOME_PHO_NBR);
   __sJT_st.setString(10,P_WORK_PHO_NBR);
   __sJT_st.setString(11,P_WORK_PHO_EXT);
   __sJT_st.setString(13,P_HOME_PHO_EXT);
   __sJT_st.setString(14,P_MOBILEPHONE);
   __sJT_st.setString(15,P_MOBILE_PHO_EXT);
   __sJT_st.setString(16,P_SMS_ALERTS_OPT_IN);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1289^30*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_CONTACT_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_EMAIL_ADDRESS,
    String P_ALT_EMAIL,
    String P_HOME_PHO_NBR,
    String P_WORK_PHO_NBR,
    String P_WORK_PHO_EXT,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1314^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"46com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_ALT_EMAIL);
   __sJT_st.setString(9,P_HOME_PHO_NBR);
   __sJT_st.setString(10,P_WORK_PHO_NBR);
   __sJT_st.setString(11,P_WORK_PHO_EXT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1325^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1331^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL,
//        :P_HOME_PHO_NBR,
//        :P_WORK_PHO_NBR,
//        :P_WORK_PHO_EXT,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_CONTACT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"47com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_ALT_EMAIL);
   __sJT_st.setString(9,P_HOME_PHO_NBR);
   __sJT_st.setString(10,P_WORK_PHO_NBR);
   __sJT_st.setString(11,P_WORK_PHO_EXT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1342^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_BILLING_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_DEFAULT_BLNG_METHD,
    boolean P_UI_CALL,
    java.math.BigDecimal O_INIT_PREPAID_BAL[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1366^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        :P_DEFAULT_BLNG_METHD,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_INIT_PREPAID_BAL[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n      SYS.SQLJUTL.INT2BOOL( :9  ),\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"48com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setString(8,P_DEFAULT_BLNG_METHD);
   __sJT_st.setBoolean(9,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_INIT_PREPAID_BAL[0] = __sJT_st.getBigDecimal(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1376^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1382^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        :P_DEFAULT_BLNG_METHD,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_INIT_PREPAID_BAL[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n      SYS.SQLJUTL.INT2BOOL( :9  ),\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"49com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setString(8,P_DEFAULT_BLNG_METHD);
   __sJT_st.setBoolean(9,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_INIT_PREPAID_BAL[0] = __sJT_st.getBigDecimal(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1392^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void INSERT_BAD_PLATES (
    String P_LIC_PLATE,
    String P_LIC_STATE)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1407^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_ACCT_MGMT.INSERT_BAD_PLATES(
//        :P_LIC_PLATE,
//        :P_LIC_STATE)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_ACCT_MGMT.INSERT_BAD_PLATES(\n       :1  ,\n       :2  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"50com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1409^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1415^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_ACCT_MGMT.INSERT_BAD_PLATES(
//        :P_LIC_PLATE,
//        :P_LIC_STATE)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_ACCT_MGMT.INSERT_BAD_PLATES(\n       :1  ,\n       :2  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"51com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1417^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal REPLACE_REACTIVE_TAG (
    String P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_TAG_ID,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID,
    String P_MOTORCYCLE_FLAG,
    String P_DELIVERY_METHOD,
    String P_ACTION,
    String P_REASON,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1444^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.REPLACE_REACTIVE_TAG(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_TAG_ID,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_ACCOUNT_VEHICLE_ID,
//        :P_MOTORCYCLE_FLAG,
//        :P_DELIVERY_METHOD,
//        :P_ACTION,
//        :P_REASON,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.REPLACE_REACTIVE_TAG(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"52com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_TAG_ID);
   __sJT_st.setString(8,P_LIC_PLATE);
   __sJT_st.setString(9,P_LIC_STATE);
   __sJT_st.setBigDecimal(10,P_ACCOUNT_VEHICLE_ID);
   __sJT_st.setString(11,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(12,P_DELIVERY_METHOD);
   __sJT_st.setString(13,P_ACTION);
   __sJT_st.setString(14,P_REASON);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1458^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1464^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.REPLACE_REACTIVE_TAG(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_TAG_ID,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_ACCOUNT_VEHICLE_ID,
//        :P_MOTORCYCLE_FLAG,
//        :P_DELIVERY_METHOD,
//        :P_ACTION,
//        :P_REASON,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.REPLACE_REACTIVE_TAG(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"53com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_TAG_ID);
   __sJT_st.setString(8,P_LIC_PLATE);
   __sJT_st.setString(9,P_LIC_STATE);
   __sJT_st.setBigDecimal(10,P_ACCOUNT_VEHICLE_ID);
   __sJT_st.setString(11,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(12,P_DELIVERY_METHOD);
   __sJT_st.setString(13,P_ACTION);
   __sJT_st.setString(14,P_REASON);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1478^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_MAILING_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_ADDRESS1,
    String P_ADDRESS2,
    String P_ADDRESS3,
    String P_ADDRESS4,
    String P_CITY,
    String P_STATE,
    String P_COUNTRY,
    String P_ZIP_CODE,
    String P_PLUS4,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1507^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_MAILING_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_MAILING_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"54com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_ADDRESS1);
   __sJT_st.setString(8,P_ADDRESS2);
   __sJT_st.setString(9,P_ADDRESS3);
   __sJT_st.setString(10,P_ADDRESS4);
   __sJT_st.setString(11,P_CITY);
   __sJT_st.setString(12,P_STATE);
   __sJT_st.setString(13,P_COUNTRY);
   __sJT_st.setString(14,P_ZIP_CODE);
   __sJT_st.setString(15,P_PLUS4);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1522^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1528^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_MAILING_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_MAILING_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"55com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_ADDRESS1);
   __sJT_st.setString(8,P_ADDRESS2);
   __sJT_st.setString(9,P_ADDRESS3);
   __sJT_st.setString(10,P_ADDRESS4);
   __sJT_st.setString(11,P_CITY);
   __sJT_st.setString(12,P_STATE);
   __sJT_st.setString(13,P_COUNTRY);
   __sJT_st.setString(14,P_ZIP_CODE);
   __sJT_st.setString(15,P_PLUS4);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1543^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_DEFAULT_PLATE_TYPE (
    String P_PLATE_STATE)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1558^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_PLATE_TYPE(
//        :P_PLATE_STATE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_PLATE_TYPE(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"56com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PLATE_STATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1559^24*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1565^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_PLATE_TYPE(
//        :P_PLATE_STATE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_DEFAULT_PLATE_TYPE(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"57com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PLATE_STATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1566^24*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_MATCH (
    java.math.BigDecimal P_ACCT_ID,
    String P_SEARCH_VALUE)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1582^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_MATCH(
//        :P_ACCT_ID,
//        :P_SEARCH_VALUE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_MATCH(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"58com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_SEARCH_VALUE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1584^25*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1590^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_MATCH(
//        :P_ACCT_ID,
//        :P_SEARCH_VALUE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_MATCH(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"59com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_SEARCH_VALUE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1592^25*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_STATEMENT_YEARS (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    OLC_STTMT_DATES_ARR P_DATE_ARR[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1612^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_YEARS(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_DATE_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_YEARS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"60com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_STTMT_DATES_ARR");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DATE_ARR[0] = (com.etcc.csc.plsql.OLC_STTMT_DATES_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_STTMT_DATES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1618^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1624^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_YEARS(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_DATE_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_STATEMENT_YEARS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"61com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_STTMT_DATES_ARR");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DATE_ARR[0] = (com.etcc.csc.plsql.OLC_STTMT_DATES_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_STTMT_DATES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1630^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_TXNS (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_USER_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_FILTER,
    java.sql.Timestamp P_BEGIN_DATE[],
    java.sql.Timestamp P_END_DATE[],
    OLC_ACCOUNT_HISTORY_ARR O_OLC_ACC_HISTORY_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1654^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VIEW_TXNS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_FILTER,
//        :P_BEGIN_DATE[0],
//        :P_END_DATE[0],
//        :O_OLC_ACC_HISTORY_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VIEW_TXNS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"62com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_FILTER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_BEGIN_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(8);
   P_END_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(9);
   O_OLC_ACC_HISTORY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1664^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1670^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VIEW_TXNS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_FILTER,
//        :P_BEGIN_DATE[0],
//        :P_END_DATE[0],
//        :O_OLC_ACC_HISTORY_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VIEW_TXNS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"63com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_FILTER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_BEGIN_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(8);
   P_END_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(9);
   O_OLC_ACC_HISTORY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1680^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal IS_TXDOT_ACCOUNT (
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_ACCT_ID,
    java.math.BigDecimal O_MIGRATION_STATUS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1699^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.IS_TXDOT_ACCOUNT(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_ACCT_ID,
//        :O_MIGRATION_STATUS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.IS_TXDOT_ACCOUNT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"64com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_MIGRATION_STATUS[0] = __sJT_st.getBigDecimal(5);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1704^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1710^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.IS_TXDOT_ACCOUNT(
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_ACCT_ID,
//        :O_MIGRATION_STATUS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.IS_TXDOT_ACCOUNT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"65com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_MIGRATION_STATUS[0] = __sJT_st.getBigDecimal(5);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1715^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VALIDATION_DOC_DOWNLOAD (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1735^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATION_DOC_DOWNLOAD(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATION_DOC_DOWNLOAD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"66com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1741^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1747^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATION_DOC_DOWNLOAD(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATION_DOC_DOWNLOAD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"67com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1753^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_ACCT_PHONE_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.sql.ResultSet O_ACCT_PHONES[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1774^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_PHONE_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_PHONES[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_PHONE_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"68com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_PHONES[0] = (java.sql.ResultSet)__sJT_st.getCursor(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1781^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1787^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_PHONE_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ACCT_PHONES[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_ACCT_PHONE_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"69com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.CURSOR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCT_PHONES[0] = (java.sql.ResultSet)__sJT_st.getCursor(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1794^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_BILLING_PREFERENCES (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_PAYMENT_INFO_ARR P_PAYMENT_INFO,
    boolean P_UI_CALL,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1816^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_PREFERENCES(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_PREFERENCES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n      SYS.SQLJUTL.INT2BOOL( :8  ),\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"70com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2003,"OL_OWNER.OLC_PAYMENT_INFO_ARR"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setBoolean(8,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1824^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1830^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_PREFERENCES(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_PREFERENCES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n      SYS.SQLJUTL.INT2BOOL( :8  ),\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"71com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2003,"OL_OWNER.OLC_PAYMENT_INFO_ARR"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setBoolean(8,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1838^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean IS_EXISTING_ACCOUNT (
    java.math.BigDecimal P_DOC_ID,
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1854^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.IS_EXISTING_ACCOUNT(
//        :P_DOC_ID,
//        :P_SESSION_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.IS_EXISTING_ACCOUNT(\n       :2  ,\n       :3  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"72com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1856^24*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1862^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.IS_EXISTING_ACCOUNT(
//        :P_DOC_ID,
//        :P_SESSION_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.IS_EXISTING_ACCOUNT(\n       :2  ,\n       :3  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"73com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1864^24*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal DOCUMENT_EXISTS (
    String P_SESSION,
    String P_USER,
    String P_IP_ADDRESS,
    java.math.BigDecimal P_DOC_ID[],
    String P_DOC_TYPE,
    String P_DOC_STATUS,
    String P_TAG_ID,
    String P_AGENCY_ID,
    String P_DRIVERS_LIC_STATE,
    String P_DRIVERS_LIC_NBR,
    String P_COMPANY_TAX_ID,
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1890^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.DOCUMENT_EXISTS(
//        :P_SESSION,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_DOC_ID[0],
//        :P_DOC_TYPE,
//        :P_DOC_STATUS,
//        :P_TAG_ID,
//        :P_AGENCY_ID,
//        :P_DRIVERS_LIC_STATE,
//        :P_DRIVERS_LIC_NBR,
//        :P_COMPANY_TAX_ID,
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.DOCUMENT_EXISTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"74com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_USER);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setBigDecimal(5,P_DOC_ID[0]);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_DOC_STATUS);
   __sJT_st.setString(8,P_TAG_ID);
   __sJT_st.setString(9,P_AGENCY_ID);
   __sJT_st.setString(10,P_DRIVERS_LIC_STATE);
   __sJT_st.setString(11,P_DRIVERS_LIC_NBR);
   __sJT_st.setString(12,P_COMPANY_TAX_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = __sJT_st.getBigDecimal(5);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1902^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1908^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.DOCUMENT_EXISTS(
//        :P_SESSION,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_DOC_ID[0],
//        :P_DOC_TYPE,
//        :P_DOC_STATUS,
//        :P_TAG_ID,
//        :P_AGENCY_ID,
//        :P_DRIVERS_LIC_STATE,
//        :P_DRIVERS_LIC_NBR,
//        :P_COMPANY_TAX_ID,
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.DOCUMENT_EXISTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"75com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_USER);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setBigDecimal(5,P_DOC_ID[0]);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_DOC_STATUS);
   __sJT_st.setString(8,P_TAG_ID);
   __sJT_st.setString(9,P_AGENCY_ID);
   __sJT_st.setString(10,P_DRIVERS_LIC_STATE);
   __sJT_st.setString(11,P_DRIVERS_LIC_NBR);
   __sJT_st.setString(12,P_COMPANY_TAX_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = __sJT_st.getBigDecimal(5);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1920^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_PAYMENT_INFO (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_PAYMENT_INFO_ARR O_PAYMENT_INFO[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_BILLING_METHOD_LIMTS_REC O_BILLING_METHOD_LIMITS[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1942^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_PAYMENT_INFO(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_PAYMENT_INFO[0],
//        :O_ERROR_MSG_ARR[0],
//        :O_BILLING_METHOD_LIMITS[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_PAYMENT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"76com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_PAYMENT_INFO_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,2002,"OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PAYMENT_INFO[0] = (com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_BILLING_METHOD_LIMITS[0] = (com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1950^43*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:1956^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_PAYMENT_INFO(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_PAYMENT_INFO[0],
//        :O_ERROR_MSG_ARR[0],
//        :O_BILLING_METHOD_LIMITS[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_PAYMENT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"77com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_PAYMENT_INFO_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,2002,"OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PAYMENT_INFO[0] = (com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_PAYMENT_INFO_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_BILLING_METHOD_LIMITS[0] = (com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1964^43*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_REBILL_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_REBILL_AMT,
    java.math.BigDecimal P_LOW_BAL_LEVEL,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:1986^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_REBILL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_REBILL_AMT,
//        :P_LOW_BAL_LEVEL,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_REBILL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"78com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBigDecimal(7,P_REBILL_AMT);
   __sJT_st.setBigDecimal(8,P_LOW_BAL_LEVEL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:1994^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2000^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_REBILL_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_REBILL_AMT,
//        :P_LOW_BAL_LEVEL,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_REBILL_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"79com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBigDecimal(7,P_REBILL_AMT);
   __sJT_st.setBigDecimal(8,P_LOW_BAL_LEVEL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2008^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal MODIFY_TAG_BYOT (
    java.math.BigDecimal P_ACCT_ID,
    String P_TAG_SEQ,
    java.math.BigDecimal P_ACCT_VEHICLE_ID[],
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_MOTORCYCLE_FLAG,
    String P_YEAR,
    String P_COLOR,
    String P_VEHICLE_MAKE,
    String P_VEHICLE_MODEL,
    String P_VEHICLE_CLASS,
    String P_CHECK_DUP,
    String P_TEMP_PLATE,
    java.sql.Timestamp P_TEMP_DATE,
    String P_TRANTYPE_FLAG,
    java.math.BigDecimal P_POS_ID,
    java.math.BigDecimal P_RETAIL_TRANS_ID,
    java.sql.Timestamp P_PBP_START_DATE,
    java.sql.Timestamp P_PBP_END_DATE,
    String P_TAG_TYPE,
    String P_NICKNAME,
    String SHOWCONTINUEMSG,
    String P_ACCT_TAG_STATUS,
    String P_ACTIVE_TAGS_IND[],
    java.math.BigDecimal P_TAG_SALES_AMT[],
    java.math.BigDecimal P_MIN_BAL_AMT[],
    java.math.BigDecimal P_PMT_AMT[],
    String O_DUP_FLAG[],
    String O_HAS_VIOLATIONS[],
    OLC_ALERT_DISPLAY_ARR O_ALERTS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_TAG_PREFIX,
    String P_TAG_ID,
    java.math.BigDecimal P_ACCOUNT_TAG_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2059^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG_BYOT(
//        :P_ACCT_ID,
//        :P_TAG_SEQ,
//        :P_ACCT_VEHICLE_ID[0],
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_MOTORCYCLE_FLAG,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_TRANTYPE_FLAG,
//        :P_POS_ID,
//        :P_RETAIL_TRANS_ID,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_TAG_TYPE,
//        :P_NICKNAME,
//        :SHOWCONTINUEMSG,
//        :P_ACCT_TAG_STATUS,
//        :P_ACTIVE_TAGS_IND[0],
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_TAG_PREFIX,
//        :P_TAG_ID,
//        :P_ACCOUNT_TAG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  ,\n       :34  ,\n       :35  ,\n       :36  ,\n       :37  ,\n       :38  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"80com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(31,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(34,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(35,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_TAG_SEQ);
   __sJT_st.setBigDecimal(4,P_ACCT_VEHICLE_ID[0]);
   __sJT_st.setString(5,P_SESSION);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER);
   __sJT_st.setString(8,P_LIC_PLATE);
   __sJT_st.setString(9,P_LIC_STATE);
   __sJT_st.setString(10,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(11,P_YEAR);
   __sJT_st.setString(12,P_COLOR);
   __sJT_st.setString(13,P_VEHICLE_MAKE);
   __sJT_st.setString(14,P_VEHICLE_MODEL);
   __sJT_st.setString(15,P_VEHICLE_CLASS);
   __sJT_st.setString(16,P_CHECK_DUP);
   __sJT_st.setString(17,P_TEMP_PLATE);
   __sJT_st.setTimestamp(18,P_TEMP_DATE);
   __sJT_st.setString(19,P_TRANTYPE_FLAG);
   __sJT_st.setBigDecimal(20,P_POS_ID);
   __sJT_st.setBigDecimal(21,P_RETAIL_TRANS_ID);
   __sJT_st.setTimestamp(22,P_PBP_START_DATE);
   __sJT_st.setTimestamp(23,P_PBP_END_DATE);
   __sJT_st.setString(24,P_TAG_TYPE);
   __sJT_st.setString(25,P_NICKNAME);
   __sJT_st.setString(26,SHOWCONTINUEMSG);
   __sJT_st.setString(27,P_ACCT_TAG_STATUS);
   __sJT_st.setString(36,P_TAG_PREFIX);
   __sJT_st.setString(37,P_TAG_ID);
   __sJT_st.setBigDecimal(38,P_ACCOUNT_TAG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(4);
   P_ACTIVE_TAGS_IND[0] = (String)__sJT_st.getString(28);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(29);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(30);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(31);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(32);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(33);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(34,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(35,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2096^27*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2102^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG_BYOT(
//        :P_ACCT_ID,
//        :P_TAG_SEQ,
//        :P_ACCT_VEHICLE_ID[0],
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_MOTORCYCLE_FLAG,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_TRANTYPE_FLAG,
//        :P_POS_ID,
//        :P_RETAIL_TRANS_ID,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_TAG_TYPE,
//        :P_NICKNAME,
//        :SHOWCONTINUEMSG,
//        :P_ACCT_TAG_STATUS,
//        :P_ACTIVE_TAGS_IND[0],
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_TAG_PREFIX,
//        :P_TAG_ID,
//        :P_ACCOUNT_TAG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  ,\n       :34  ,\n       :35  ,\n       :36  ,\n       :37  ,\n       :38  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"81com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(31,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(34,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(35,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_TAG_SEQ);
   __sJT_st.setBigDecimal(4,P_ACCT_VEHICLE_ID[0]);
   __sJT_st.setString(5,P_SESSION);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER);
   __sJT_st.setString(8,P_LIC_PLATE);
   __sJT_st.setString(9,P_LIC_STATE);
   __sJT_st.setString(10,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(11,P_YEAR);
   __sJT_st.setString(12,P_COLOR);
   __sJT_st.setString(13,P_VEHICLE_MAKE);
   __sJT_st.setString(14,P_VEHICLE_MODEL);
   __sJT_st.setString(15,P_VEHICLE_CLASS);
   __sJT_st.setString(16,P_CHECK_DUP);
   __sJT_st.setString(17,P_TEMP_PLATE);
   __sJT_st.setTimestamp(18,P_TEMP_DATE);
   __sJT_st.setString(19,P_TRANTYPE_FLAG);
   __sJT_st.setBigDecimal(20,P_POS_ID);
   __sJT_st.setBigDecimal(21,P_RETAIL_TRANS_ID);
   __sJT_st.setTimestamp(22,P_PBP_START_DATE);
   __sJT_st.setTimestamp(23,P_PBP_END_DATE);
   __sJT_st.setString(24,P_TAG_TYPE);
   __sJT_st.setString(25,P_NICKNAME);
   __sJT_st.setString(26,SHOWCONTINUEMSG);
   __sJT_st.setString(27,P_ACCT_TAG_STATUS);
   __sJT_st.setString(36,P_TAG_PREFIX);
   __sJT_st.setString(37,P_TAG_ID);
   __sJT_st.setBigDecimal(38,P_ACCOUNT_TAG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(4);
   P_ACTIVE_TAGS_IND[0] = (String)__sJT_st.getString(28);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(29);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(30);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(31);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(32);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(33);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(34,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(35,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2139^27*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal MODIFY_TAG (
    java.math.BigDecimal P_ACCT_ID,
    String P_TAG_SEQ,
    java.math.BigDecimal P_ACCT_VEHICLE_ID[],
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_MOTORCYCLE_FLAG,
    String P_YEAR,
    String P_COLOR,
    String P_VEHICLE_MAKE,
    String P_VEHICLE_MODEL,
    String P_VEHICLE_CLASS,
    String P_CHECK_DUP,
    String P_TEMP_PLATE,
    java.sql.Timestamp P_TEMP_DATE,
    String P_TRANTYPE_FLAG,
    java.math.BigDecimal P_POS_ID,
    java.math.BigDecimal P_RETAIL_TRANS_ID,
    java.sql.Timestamp P_PBP_START_DATE,
    java.sql.Timestamp P_PBP_END_DATE,
    String P_TAG_TYPE,
    String P_NICKNAME,
    String SHOWCONTINUEMSG,
    String P_ACCT_TAG_STATUS,
    String P_ACTIVE_TAGS_IND[],
    java.math.BigDecimal P_TAG_SALES_AMT[],
    java.math.BigDecimal P_MIN_BAL_AMT[],
    java.math.BigDecimal P_PMT_AMT[],
    String O_DUP_FLAG[],
    String O_HAS_VIOLATIONS[],
    OLC_ALERT_DISPLAY_ARR O_ALERTS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2187^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG(
//        :P_ACCT_ID,
//        :P_TAG_SEQ,
//        :P_ACCT_VEHICLE_ID[0],
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_MOTORCYCLE_FLAG,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_TRANTYPE_FLAG,
//        :P_POS_ID,
//        :P_RETAIL_TRANS_ID,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_TAG_TYPE,
//        :P_NICKNAME,
//        :SHOWCONTINUEMSG,
//        :P_ACCT_TAG_STATUS,
//        :P_ACTIVE_TAGS_IND[0],
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  ,\n       :34  ,\n       :35  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"82com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(31,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(34,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(35,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_TAG_SEQ);
   __sJT_st.setBigDecimal(4,P_ACCT_VEHICLE_ID[0]);
   __sJT_st.setString(5,P_SESSION);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER);
   __sJT_st.setString(8,P_LIC_PLATE);
   __sJT_st.setString(9,P_LIC_STATE);
   __sJT_st.setString(10,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(11,P_YEAR);
   __sJT_st.setString(12,P_COLOR);
   __sJT_st.setString(13,P_VEHICLE_MAKE);
   __sJT_st.setString(14,P_VEHICLE_MODEL);
   __sJT_st.setString(15,P_VEHICLE_CLASS);
   __sJT_st.setString(16,P_CHECK_DUP);
   __sJT_st.setString(17,P_TEMP_PLATE);
   __sJT_st.setTimestamp(18,P_TEMP_DATE);
   __sJT_st.setString(19,P_TRANTYPE_FLAG);
   __sJT_st.setBigDecimal(20,P_POS_ID);
   __sJT_st.setBigDecimal(21,P_RETAIL_TRANS_ID);
   __sJT_st.setTimestamp(22,P_PBP_START_DATE);
   __sJT_st.setTimestamp(23,P_PBP_END_DATE);
   __sJT_st.setString(24,P_TAG_TYPE);
   __sJT_st.setString(25,P_NICKNAME);
   __sJT_st.setString(26,SHOWCONTINUEMSG);
   __sJT_st.setString(27,P_ACCT_TAG_STATUS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(4);
   P_ACTIVE_TAGS_IND[0] = (String)__sJT_st.getString(28);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(29);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(30);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(31);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(32);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(33);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(34,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(35,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2221^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2227^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG(
//        :P_ACCT_ID,
//        :P_TAG_SEQ,
//        :P_ACCT_VEHICLE_ID[0],
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_MOTORCYCLE_FLAG,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_TRANTYPE_FLAG,
//        :P_POS_ID,
//        :P_RETAIL_TRANS_ID,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_TAG_TYPE,
//        :P_NICKNAME,
//        :SHOWCONTINUEMSG,
//        :P_ACCT_TAG_STATUS,
//        :P_ACTIVE_TAGS_IND[0],
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.MODIFY_TAG(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  ,\n       :34  ,\n       :35  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"83com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(30,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(31,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(32,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(33,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(34,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(35,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_TAG_SEQ);
   __sJT_st.setBigDecimal(4,P_ACCT_VEHICLE_ID[0]);
   __sJT_st.setString(5,P_SESSION);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER);
   __sJT_st.setString(8,P_LIC_PLATE);
   __sJT_st.setString(9,P_LIC_STATE);
   __sJT_st.setString(10,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(11,P_YEAR);
   __sJT_st.setString(12,P_COLOR);
   __sJT_st.setString(13,P_VEHICLE_MAKE);
   __sJT_st.setString(14,P_VEHICLE_MODEL);
   __sJT_st.setString(15,P_VEHICLE_CLASS);
   __sJT_st.setString(16,P_CHECK_DUP);
   __sJT_st.setString(17,P_TEMP_PLATE);
   __sJT_st.setTimestamp(18,P_TEMP_DATE);
   __sJT_st.setString(19,P_TRANTYPE_FLAG);
   __sJT_st.setBigDecimal(20,P_POS_ID);
   __sJT_st.setBigDecimal(21,P_RETAIL_TRANS_ID);
   __sJT_st.setTimestamp(22,P_PBP_START_DATE);
   __sJT_st.setTimestamp(23,P_PBP_END_DATE);
   __sJT_st.setString(24,P_TAG_TYPE);
   __sJT_st.setString(25,P_NICKNAME);
   __sJT_st.setString(26,SHOWCONTINUEMSG);
   __sJT_st.setString(27,P_ACCT_TAG_STATUS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(4);
   P_ACTIVE_TAGS_IND[0] = (String)__sJT_st.getString(28);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(29);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(30);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(31);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(32);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(33);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(34,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(35,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2261^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal UNBLOCK_BILLING_METHOD (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_ACCOUNT_BILLING_METHOD_ID,
    boolean P_UI_CALL,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2283^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.UNBLOCK_BILLING_METHOD(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ACCOUNT_BILLING_METHOD_ID,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.UNBLOCK_BILLING_METHOD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n      SYS.SQLJUTL.INT2BOOL( :8  ),\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"84com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBigDecimal(7,P_ACCOUNT_BILLING_METHOD_ID);
   __sJT_st.setBoolean(8,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2291^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2297^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.UNBLOCK_BILLING_METHOD(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ACCOUNT_BILLING_METHOD_ID,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.UNBLOCK_BILLING_METHOD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n      SYS.SQLJUTL.INT2BOOL( :8  ),\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"85com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBigDecimal(7,P_ACCOUNT_BILLING_METHOD_ID);
   __sJT_st.setBoolean(8,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2305^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal ADD_TAG (
    java.math.BigDecimal P_ACCT_ID,
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_YEAR,
    String P_COLOR,
    String P_VEHICLE_MAKE,
    String P_VEHICLE_MODEL,
    String P_VEHICLE_CLASS,
    String P_MOTORCYCLE_FLAG,
    String P_CHECK_DUP,
    String P_TEMP_PLATE,
    java.sql.Timestamp P_TEMP_DATE,
    java.math.BigDecimal P_POS_ID,
    java.math.BigDecimal P_TRXN[],
    String P_TAG_TYPE,
    java.sql.Timestamp P_PBP_START_DATE,
    java.sql.Timestamp P_PBP_END_DATE,
    String P_NICKNAME,
    java.math.BigDecimal P_TAG_SALES_AMT[],
    java.math.BigDecimal P_MIN_BAL_AMT[],
    java.math.BigDecimal P_PMT_AMT[],
    java.math.BigDecimal P_TAG_CHARGE[],
    String O_TAG_SEQ[],
    String O_DUP_FLAG[],
    String O_HAS_VIOLATIONS[],
    OLC_ALERT_DISPLAY_ARR O_ALERTS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2349^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG(
//        :P_ACCT_ID,
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_MOTORCYCLE_FLAG,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_POS_ID,
//        :P_TRXN[0],
//        :P_TAG_TYPE,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_NICKNAME,
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :P_TAG_CHARGE[0],
//        :O_TAG_SEQ[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"86com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(26,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(27,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(31,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_SESSION);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_LIC_PLATE);
   __sJT_st.setString(7,P_LIC_STATE);
   __sJT_st.setString(8,P_YEAR);
   __sJT_st.setString(9,P_COLOR);
   __sJT_st.setString(10,P_VEHICLE_MAKE);
   __sJT_st.setString(11,P_VEHICLE_MODEL);
   __sJT_st.setString(12,P_VEHICLE_CLASS);
   __sJT_st.setString(13,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(14,P_CHECK_DUP);
   __sJT_st.setString(15,P_TEMP_PLATE);
   __sJT_st.setTimestamp(16,P_TEMP_DATE);
   __sJT_st.setBigDecimal(17,P_POS_ID);
   __sJT_st.setBigDecimal(18,P_TRXN[0]);
   __sJT_st.setString(19,P_TAG_TYPE);
   __sJT_st.setTimestamp(20,P_PBP_START_DATE);
   __sJT_st.setTimestamp(21,P_PBP_END_DATE);
   __sJT_st.setString(22,P_NICKNAME);
   __sJT_st.setString(27,O_TAG_SEQ[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TRXN[0] = __sJT_st.getBigDecimal(18);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(23);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(24);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(25);
   P_TAG_CHARGE[0] = __sJT_st.getBigDecimal(26);
   O_TAG_SEQ[0] = (String)__sJT_st.getString(27);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(28);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(29);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(31,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2379^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2385^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG(
//        :P_ACCT_ID,
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_MOTORCYCLE_FLAG,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_POS_ID,
//        :P_TRXN[0],
//        :P_TAG_TYPE,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_NICKNAME,
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :P_TAG_CHARGE[0],
//        :O_TAG_SEQ[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"87com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(26,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(27,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(31,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_SESSION);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_LIC_PLATE);
   __sJT_st.setString(7,P_LIC_STATE);
   __sJT_st.setString(8,P_YEAR);
   __sJT_st.setString(9,P_COLOR);
   __sJT_st.setString(10,P_VEHICLE_MAKE);
   __sJT_st.setString(11,P_VEHICLE_MODEL);
   __sJT_st.setString(12,P_VEHICLE_CLASS);
   __sJT_st.setString(13,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(14,P_CHECK_DUP);
   __sJT_st.setString(15,P_TEMP_PLATE);
   __sJT_st.setTimestamp(16,P_TEMP_DATE);
   __sJT_st.setBigDecimal(17,P_POS_ID);
   __sJT_st.setBigDecimal(18,P_TRXN[0]);
   __sJT_st.setString(19,P_TAG_TYPE);
   __sJT_st.setTimestamp(20,P_PBP_START_DATE);
   __sJT_st.setTimestamp(21,P_PBP_END_DATE);
   __sJT_st.setString(22,P_NICKNAME);
   __sJT_st.setString(27,O_TAG_SEQ[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TRXN[0] = __sJT_st.getBigDecimal(18);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(23);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(24);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(25);
   P_TAG_CHARGE[0] = __sJT_st.getBigDecimal(26);
   O_TAG_SEQ[0] = (String)__sJT_st.getString(27);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(28);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(29);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(31,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2415^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VALIDATE_DL (
    String P_STATE_CODE,
    String P_VALUE)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2431^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_DL(
//        :P_STATE_CODE,
//        :P_VALUE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_DL(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"88com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_STATE_CODE);
   __sJT_st.setString(3,P_VALUE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2433^18*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2439^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_DL(
//        :P_STATE_CODE,
//        :P_VALUE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_DL(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"89com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_STATE_CODE);
   __sJT_st.setString(3,P_VALUE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2441^18*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal ADD_TAG_BYOT (
    java.math.BigDecimal P_ACCT_ID,
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_YEAR,
    String P_COLOR,
    String P_VEHICLE_MAKE,
    String P_VEHICLE_MODEL,
    String P_VEHICLE_CLASS,
    String P_MOTORCYCLE_FLAG,
    String P_CHECK_DUP,
    String P_TEMP_PLATE,
    java.sql.Timestamp P_TEMP_DATE,
    java.math.BigDecimal P_POS_ID,
    java.math.BigDecimal P_TRXN[],
    String P_TAG_TYPE,
    java.sql.Timestamp P_PBP_START_DATE,
    java.sql.Timestamp P_PBP_END_DATE,
    String P_NICKNAME,
    java.math.BigDecimal P_TAG_SALES_AMT[],
    java.math.BigDecimal P_MIN_BAL_AMT[],
    java.math.BigDecimal P_PMT_AMT[],
    java.math.BigDecimal P_TAG_CHARGE[],
    String O_TAG_SEQ[],
    String O_DUP_FLAG[],
    String O_HAS_VIOLATIONS[],
    OLC_ALERT_DISPLAY_ARR O_ALERTS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_TAG_PREFIX,
    String P_TAG_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2487^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG_BYOT(
//        :P_ACCT_ID,
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_MOTORCYCLE_FLAG,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_POS_ID,
//        :P_TRXN[0],
//        :P_TAG_TYPE,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_NICKNAME,
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :P_TAG_CHARGE[0],
//        :O_TAG_SEQ[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_TAG_PREFIX,
//        :P_TAG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"90com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(26,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(27,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(31,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_SESSION);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_LIC_PLATE);
   __sJT_st.setString(7,P_LIC_STATE);
   __sJT_st.setString(8,P_YEAR);
   __sJT_st.setString(9,P_COLOR);
   __sJT_st.setString(10,P_VEHICLE_MAKE);
   __sJT_st.setString(11,P_VEHICLE_MODEL);
   __sJT_st.setString(12,P_VEHICLE_CLASS);
   __sJT_st.setString(13,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(14,P_CHECK_DUP);
   __sJT_st.setString(15,P_TEMP_PLATE);
   __sJT_st.setTimestamp(16,P_TEMP_DATE);
   __sJT_st.setBigDecimal(17,P_POS_ID);
   __sJT_st.setBigDecimal(18,P_TRXN[0]);
   __sJT_st.setString(19,P_TAG_TYPE);
   __sJT_st.setTimestamp(20,P_PBP_START_DATE);
   __sJT_st.setTimestamp(21,P_PBP_END_DATE);
   __sJT_st.setString(22,P_NICKNAME);
   __sJT_st.setString(27,O_TAG_SEQ[0]);
   __sJT_st.setString(32,P_TAG_PREFIX);
   __sJT_st.setString(33,P_TAG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TRXN[0] = __sJT_st.getBigDecimal(18);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(23);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(24);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(25);
   P_TAG_CHARGE[0] = __sJT_st.getBigDecimal(26);
   O_TAG_SEQ[0] = (String)__sJT_st.getString(27);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(28);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(29);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(31,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2519^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2525^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG_BYOT(
//        :P_ACCT_ID,
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_YEAR,
//        :P_COLOR,
//        :P_VEHICLE_MAKE,
//        :P_VEHICLE_MODEL,
//        :P_VEHICLE_CLASS,
//        :P_MOTORCYCLE_FLAG,
//        :P_CHECK_DUP,
//        :P_TEMP_PLATE,
//        :P_TEMP_DATE,
//        :P_POS_ID,
//        :P_TRXN[0],
//        :P_TAG_TYPE,
//        :P_PBP_START_DATE,
//        :P_PBP_END_DATE,
//        :P_NICKNAME,
//        :P_TAG_SALES_AMT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0],
//        :P_TAG_CHARGE[0],
//        :O_TAG_SEQ[0],
//        :O_DUP_FLAG[0],
//        :O_HAS_VIOLATIONS[0],
//        :O_ALERTS[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_TAG_PREFIX,
//        :P_TAG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.ADD_TAG_BYOT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n       :19  ,\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  ,\n       :29  ,\n       :30  ,\n       :31  ,\n       :32  ,\n       :33  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"91com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(18,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(23,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(24,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(25,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(26,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(27,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(28,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(29,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(30,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(31,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_SESSION);
   __sJT_st.setString(4,P_IP_ADDRESS);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_LIC_PLATE);
   __sJT_st.setString(7,P_LIC_STATE);
   __sJT_st.setString(8,P_YEAR);
   __sJT_st.setString(9,P_COLOR);
   __sJT_st.setString(10,P_VEHICLE_MAKE);
   __sJT_st.setString(11,P_VEHICLE_MODEL);
   __sJT_st.setString(12,P_VEHICLE_CLASS);
   __sJT_st.setString(13,P_MOTORCYCLE_FLAG);
   __sJT_st.setString(14,P_CHECK_DUP);
   __sJT_st.setString(15,P_TEMP_PLATE);
   __sJT_st.setTimestamp(16,P_TEMP_DATE);
   __sJT_st.setBigDecimal(17,P_POS_ID);
   __sJT_st.setBigDecimal(18,P_TRXN[0]);
   __sJT_st.setString(19,P_TAG_TYPE);
   __sJT_st.setTimestamp(20,P_PBP_START_DATE);
   __sJT_st.setTimestamp(21,P_PBP_END_DATE);
   __sJT_st.setString(22,P_NICKNAME);
   __sJT_st.setString(27,O_TAG_SEQ[0]);
   __sJT_st.setString(32,P_TAG_PREFIX);
   __sJT_st.setString(33,P_TAG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TRXN[0] = __sJT_st.getBigDecimal(18);
   P_TAG_SALES_AMT[0] = __sJT_st.getBigDecimal(23);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(24);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(25);
   P_TAG_CHARGE[0] = __sJT_st.getBigDecimal(26);
   O_TAG_SEQ[0] = (String)__sJT_st.getString(27);
   O_DUP_FLAG[0] = (String)__sJT_st.getString(28);
   O_HAS_VIOLATIONS[0] = (String)__sJT_st.getString(29);
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(30,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(31,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2557^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SET_BILLING_ADDRESS (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String P_ADDRESS1,
    String P_ADDRESS2,
    String P_ADDRESS3,
    String P_ADDRESS4,
    String P_CITY,
    String P_STATE,
    String P_COUNTRY,
    String P_ZIP_CODE,
    String P_PLUS4,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2586^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_ADDRESS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_ADDRESS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"92com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_ADDRESS1);
   __sJT_st.setString(8,P_ADDRESS2);
   __sJT_st.setString(9,P_ADDRESS3);
   __sJT_st.setString(10,P_ADDRESS4);
   __sJT_st.setString(11,P_CITY);
   __sJT_st.setString(12,P_STATE);
   __sJT_st.setString(13,P_COUNTRY);
   __sJT_st.setString(14,P_ZIP_CODE);
   __sJT_st.setString(15,P_PLUS4);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2601^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2607^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_ADDRESS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ADDRESS1,
//        :P_ADDRESS2,
//        :P_ADDRESS3,
//        :P_ADDRESS4,
//        :P_CITY,
//        :P_STATE,
//        :P_COUNTRY,
//        :P_ZIP_CODE,
//        :P_PLUS4,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.SET_BILLING_ADDRESS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"93com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setString(7,P_ADDRESS1);
   __sJT_st.setString(8,P_ADDRESS2);
   __sJT_st.setString(9,P_ADDRESS3);
   __sJT_st.setString(10,P_ADDRESS4);
   __sJT_st.setString(11,P_CITY);
   __sJT_st.setString(12,P_STATE);
   __sJT_st.setString(13,P_COUNTRY);
   __sJT_st.setString(14,P_ZIP_CODE);
   __sJT_st.setString(15,P_PLUS4);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2622^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHECK_EMAILADDRESS_PHONE (
    String P_ACTION,
    String P_EMAIL_ADDRESS,
    String P_PHONE_NUMBER,
    String P_DOC_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2641^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.CHECK_EMAILADDRESS_PHONE(
//        :P_ACTION,
//        :P_EMAIL_ADDRESS,
//        :P_PHONE_NUMBER,
//        :P_DOC_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.CHECK_EMAILADDRESS_PHONE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"94com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_ACTION);
   __sJT_st.setString(3,P_EMAIL_ADDRESS);
   __sJT_st.setString(4,P_PHONE_NUMBER);
   __sJT_st.setString(5,P_DOC_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = (String)__sJT_st.getString(5);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2646^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2652^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.CHECK_EMAILADDRESS_PHONE(
//        :P_ACTION,
//        :P_EMAIL_ADDRESS,
//        :P_PHONE_NUMBER,
//        :P_DOC_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.CHECK_EMAILADDRESS_PHONE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"95com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_ACTION);
   __sJT_st.setString(3,P_EMAIL_ADDRESS);
   __sJT_st.setString(4,P_PHONE_NUMBER);
   __sJT_st.setString(5,P_DOC_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_DOC_ID[0] = (String)__sJT_st.getString(5);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2657^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_TAG_INFO (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    String P_SEARCH_VALUE,
    OLC_ACCOUNT_TAG_ARR P_ACCOUNT_TAG_ARR[],
    OLC_ACCOUNT_TAG_ARR P_LIC_PLATE_TAG_ARR[],
    boolean P_UI_CALL,
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2680^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SEARCH_VALUE,
//        :P_ACCOUNT_TAG_ARR[0],
//        :P_LIC_PLATE_TAG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n      SYS.SQLJUTL.INT2BOOL( :9  ),\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"96com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_SEARCH_VALUE);
   __sJT_st.setBoolean(9,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCOUNT_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR.getORADataFactory());
   P_LIC_PLATE_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2689^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2695^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SEARCH_VALUE,
//        :P_ACCOUNT_TAG_ARR[0],
//        :P_LIC_PLATE_TAG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_TAG_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n      SYS.SQLJUTL.INT2BOOL( :9  ),\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"97com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_SEARCH_VALUE);
   __sJT_st.setBoolean(9,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_ACCOUNT_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR.getORADataFactory());
   P_LIC_PLATE_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2704^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VALIDATE_SECURITY_ANSWER (
    String P_SESSION_ID,
    String P_USER_ID,
    String P_IP_ADDRESS,
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_ANSWER,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2725^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_SECURITY_ANSWER(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
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
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_SECURITY_ANSWER(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"98com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2732^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2738^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_SECURITY_ANSWER(
//        :P_SESSION_ID,
//        :P_USER_ID,
//        :P_IP_ADDRESS,
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
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.VALIDATE_SECURITY_ANSWER(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"99com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
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
   __sJT_st.setString(7,P_ANSWER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2745^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean SUSPEND_ACCT_EMAIL (
    String P_EMAIL_ADDRESS,
    String P_ALT_EMAIL_ADDRESS)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2761^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.SUSPEND_ACCT_EMAIL(
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL_ADDRESS)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.SUSPEND_ACCT_EMAIL(\n       :2  ,\n       :3  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"100com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setString(2,P_EMAIL_ADDRESS);
   __sJT_st.setString(3,P_ALT_EMAIL_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2763^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2769^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.SUSPEND_ACCT_EMAIL(
//        :P_EMAIL_ADDRESS,
//        :P_ALT_EMAIL_ADDRESS)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_ACCT_MGMT.SUSPEND_ACCT_EMAIL(\n       :2  ,\n       :3  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"101com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setString(2,P_EMAIL_ADDRESS);
   __sJT_st.setString(3,P_ALT_EMAIL_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2771^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_AUTHENTICATED_SESSIONID (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_TEMP_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    String O_SESSION_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2792^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_AUTHENTICATED_SESSIONID(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_TEMP_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_SESSION_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_AUTHENTICATED_SESSIONID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"102com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_TEMP_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_SESSION_ID[0] = (String)__sJT_st.getString(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2799^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2805^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_AUTHENTICATED_SESSIONID(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_TEMP_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_SESSION_ID[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_AUTHENTICATED_SESSIONID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"103com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_TEMP_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_SESSION_ID[0] = (String)__sJT_st.getString(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2812^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_INITIAL_AUTOCHARGE_AMOUNTS (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_ACCT_TYPE,
    java.math.BigDecimal P_ACCT_PLAN,
    java.math.BigDecimal P_PLAN_DETAIL,
    java.math.BigDecimal P_PAYMENT_FORM,
    java.math.BigDecimal P_VEHICLE_COUNT,
    String O_VALUES_OUT[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2838^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_INITIAL_AUTOCHARGE_AMOUNTS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ACCT_TYPE,
//        :P_ACCT_PLAN,
//        :P_PLAN_DETAIL,
//        :P_PAYMENT_FORM,
//        :P_VEHICLE_COUNT,
//        :O_VALUES_OUT[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_INITIAL_AUTOCHARGE_AMOUNTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"104com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBigDecimal(7,P_ACCT_TYPE);
   __sJT_st.setBigDecimal(8,P_ACCT_PLAN);
   __sJT_st.setBigDecimal(9,P_PLAN_DETAIL);
   __sJT_st.setBigDecimal(10,P_PAYMENT_FORM);
   __sJT_st.setBigDecimal(11,P_VEHICLE_COUNT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VALUES_OUT[0] = (String)__sJT_st.getString(12);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2850^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2856^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.GET_INITIAL_AUTOCHARGE_AMOUNTS(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_ACCT_TYPE,
//        :P_ACCT_PLAN,
//        :P_PLAN_DETAIL,
//        :P_PAYMENT_FORM,
//        :P_VEHICLE_COUNT,
//        :O_VALUES_OUT[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.GET_INITIAL_AUTOCHARGE_AMOUNTS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"105com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBigDecimal(7,P_ACCT_TYPE);
   __sJT_st.setBigDecimal(8,P_ACCT_PLAN);
   __sJT_st.setBigDecimal(9,P_PLAN_DETAIL);
   __sJT_st.setBigDecimal(10,P_PAYMENT_FORM);
   __sJT_st.setBigDecimal(11,P_VEHICLE_COUNT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VALUES_OUT[0] = (String)__sJT_st.getString(12);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2868^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal HAS_LAST_YEAR_STMT (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    String P_LAST_YEAR_AVAILABLE[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2888^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.HAS_LAST_YEAR_STMT(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_LAST_YEAR_AVAILABLE[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.HAS_LAST_YEAR_STMT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"106com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_LAST_YEAR_AVAILABLE[0] = (String)__sJT_st.getString(6);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2894^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2900^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.HAS_LAST_YEAR_STMT(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_LAST_YEAR_AVAILABLE[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.HAS_LAST_YEAR_STMT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"107com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_LAST_YEAR_AVAILABLE[0] = (String)__sJT_st.getString(6);
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2906^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal REMOVE_BILLING_INFO (
    java.math.BigDecimal P_ACCT_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    boolean P_UI_CALL,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:2928^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.REMOVE_BILLING_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.REMOVE_BILLING_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n      SYS.SQLJUTL.INT2BOOL( :8  ),\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"108com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setBoolean(8,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2936^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:2942^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_ACCT_MGMT.REMOVE_BILLING_INFO(
//        :P_ACCT_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_PAYMENT_INFO,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_ACCT_MGMT.REMOVE_BILLING_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n      SYS.SQLJUTL.INT2BOOL( :8  ),\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"109com.etcc.csc.plsql.OLCSC_ACCT_MGMT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(7,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(7,P_PAYMENT_INFO);
   __sJT_st.setBoolean(8,P_UI_CALL);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:2950^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/