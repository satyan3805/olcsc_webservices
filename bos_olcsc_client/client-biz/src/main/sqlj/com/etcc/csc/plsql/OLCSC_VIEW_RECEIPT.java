/*@lineinfo:filename=OLCSC_VIEW_RECEIPT*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_VIEW_RECEIPT
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
  public OLCSC_VIEW_RECEIPT() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_VIEW_RECEIPT(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_VIEW_RECEIPT(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_VIEW_RECEIPT(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal VIEW_ACCT_TRANS (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_RETAIL_TRANS_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    java.math.BigDecimal O_AP_USER_ID[],
    java.sql.Timestamp O_TRANS_DATE[],
    OLC_ACCOUNT_TRANSACTION_ARR O_OLC_ACCOUNT_TRANSACTION_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:83^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_ACCT_TRANS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_AP_USER_ID[0],
//        :O_TRANS_DATE[0],
//        :O_OLC_ACCOUNT_TRANSACTION_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_ACCT_TRANS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ACCOUNT_TRANSACTION_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_AP_USER_ID[0] = __sJT_st.getBigDecimal(9);
   O_TRANS_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   O_OLC_ACCOUNT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:93^49*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:99^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_ACCT_TRANS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_AP_USER_ID[0],
//        :O_TRANS_DATE[0],
//        :O_OLC_ACCOUNT_TRANSACTION_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_ACCT_TRANS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ACCOUNT_TRANSACTION_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_AP_USER_ID[0] = __sJT_st.getBigDecimal(9);
   O_TRANS_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   O_OLC_ACCOUNT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:109^49*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_PMT_DETAIL (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_RETAIL_TRANS_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_PAYMENT_DETAIL_ARR O_OLC_PAYMENT_DETAIL_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:131^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_PMT_DETAIL(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_PAYMENT_DETAIL_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_PMT_DETAIL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_PAYMENT_DETAIL_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_PAYMENT_DETAIL_ARR[0] = (com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:139^44*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:145^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_PMT_DETAIL(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_PAYMENT_DETAIL_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_PMT_DETAIL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_PAYMENT_DETAIL_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_PAYMENT_DETAIL_ARR[0] = (com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:153^44*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal OLCSC_VIEW_RECEIPT_DETAIL (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_RETAIL_TRANS_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    java.math.BigDecimal O_AP_USER_ID[],
    java.sql.Timestamp O_TRANS_DATE[],
    OLC_ACCOUNT_TRANSACTION_ARR O_OLC_ACCOUNT_TRANSACTION_ARR[],
    OLC_PAYMENT_DETAIL_ARR O_OLC_PAYMENT_DETAIL_ARR[],
    OLC_DEPOSIT_TRANSACTION_ARR O_OLC_DEPOSIT_TRANSACTION_ARR[],
    OLC_HAS_PARK_RECEIPT_ARR O_OLC_HAS_PARK_RECEIPT_ARR[],
    String HAS_FLAG)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:181^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_DETAIL(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_AP_USER_ID[0],
//        :O_TRANS_DATE[0],
//        :O_OLC_ACCOUNT_TRANSACTION_ARR[0],
//        :O_OLC_PAYMENT_DETAIL_ARR[0],
//        :O_OLC_DEPOSIT_TRANSACTION_ARR[0],
//        :O_OLC_HAS_PARK_RECEIPT_ARR[0],
//        :HAS_FLAG))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_DETAIL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ACCOUNT_TRANSACTION_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_PAYMENT_DETAIL_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_DEPOSIT_TRANSACTION_ARR");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_HAS_PARK_RECEIPT_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
   __sJT_st.setString(15,HAS_FLAG);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_AP_USER_ID[0] = __sJT_st.getBigDecimal(9);
   O_TRANS_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   O_OLC_ACCOUNT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR.getORADataFactory());
   O_OLC_PAYMENT_DETAIL_ARR[0] = (com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR.getORADataFactory());
   O_OLC_DEPOSIT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR.getORADataFactory());
   O_OLC_HAS_PARK_RECEIPT_ARR[0] = (com.etcc.csc.plsql.OLC_HAS_PARK_RECEIPT_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_HAS_PARK_RECEIPT_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:195^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:201^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_DETAIL(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_AP_USER_ID[0],
//        :O_TRANS_DATE[0],
//        :O_OLC_ACCOUNT_TRANSACTION_ARR[0],
//        :O_OLC_PAYMENT_DETAIL_ARR[0],
//        :O_OLC_DEPOSIT_TRANSACTION_ARR[0],
//        :O_OLC_HAS_PARK_RECEIPT_ARR[0],
//        :HAS_FLAG))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_DETAIL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ACCOUNT_TRANSACTION_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_PAYMENT_DETAIL_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_DEPOSIT_TRANSACTION_ARR");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_HAS_PARK_RECEIPT_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
   __sJT_st.setString(15,HAS_FLAG);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_AP_USER_ID[0] = __sJT_st.getBigDecimal(9);
   O_TRANS_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   O_OLC_ACCOUNT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ACCOUNT_TRANSACTION_ARR.getORADataFactory());
   O_OLC_PAYMENT_DETAIL_ARR[0] = (com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_PAYMENT_DETAIL_ARR.getORADataFactory());
   O_OLC_DEPOSIT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR.getORADataFactory());
   O_OLC_HAS_PARK_RECEIPT_ARR[0] = (com.etcc.csc.plsql.OLC_HAS_PARK_RECEIPT_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_HAS_PARK_RECEIPT_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:215^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_DPST_TRANS (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_RETAIL_TRANS_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_DEPOSIT_TRANSACTION_ARR O_OLC_DEPOSIT_TRANSACTION_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:237^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_DPST_TRANS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_DEPOSIT_TRANSACTION_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_DPST_TRANS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_DEPOSIT_TRANSACTION_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_DEPOSIT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:245^49*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:251^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_DPST_TRANS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_RETAIL_TRANS_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_DEPOSIT_TRANSACTION_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.VIEW_DPST_TRANS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_DEPOSIT_TRANSACTION_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_RETAIL_TRANS_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_DEPOSIT_TRANSACTION_ARR[0] = (com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_DEPOSIT_TRANSACTION_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:259^49*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal OLCSC_VIEW_RECEIPT_HEAD (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.sql.Timestamp P_START_DATE,
    java.sql.Timestamp P_END_DATE,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_VIEW_RECEIPT_HEAD_ARR O_VIEW_RECEIPT_HEAD_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:282^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_HEAD(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_START_DATE,
//        :P_END_DATE,
//        :O_ERROR_MSG_ARR[0],
//        :O_VIEW_RECEIPT_HEAD_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_HEAD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_VIEW_RECEIPT_HEAD_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setTimestamp(7,P_START_DATE);
   __sJT_st.setTimestamp(8,P_END_DATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_VIEW_RECEIPT_HEAD_ARR[0] = (com.etcc.csc.plsql.OLC_VIEW_RECEIPT_HEAD_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_VIEW_RECEIPT_HEAD_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:291^43*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:297^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_HEAD(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_START_DATE,
//        :P_END_DATE,
//        :O_ERROR_MSG_ARR[0],
//        :O_VIEW_RECEIPT_HEAD_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_VIEW_RECEIPT.OLCSC_VIEW_RECEIPT_HEAD(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_VIEW_RECEIPT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_VIEW_RECEIPT_HEAD_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setTimestamp(7,P_START_DATE);
   __sJT_st.setTimestamp(8,P_END_DATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_VIEW_RECEIPT_HEAD_ARR[0] = (com.etcc.csc.plsql.OLC_VIEW_RECEIPT_HEAD_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_VIEW_RECEIPT_HEAD_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:306^43*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/