/*@lineinfo:filename=OLCSC_PAYMENT*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_PAYMENT
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
  public OLCSC_PAYMENT() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_PAYMENT(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_PAYMENT(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_PAYMENT(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public void FIGURE_PAYMENT (
    java.math.BigDecimal P_ACCT_ID,
    java.math.BigDecimal P_REBILL_AMOUNT[],
    java.math.BigDecimal P_MIN_BAL_AMT[],
    java.math.BigDecimal P_PMT_AMT[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:76^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_PAYMENT.FIGURE_PAYMENT(
//        :P_ACCT_ID,
//        :P_REBILL_AMOUNT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_PAYMENT.FIGURE_PAYMENT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   P_REBILL_AMOUNT[0] = __sJT_st.getBigDecimal(2);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(3);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:80^28*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:86^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_PAYMENT.FIGURE_PAYMENT(
//        :P_ACCT_ID,
//        :P_REBILL_AMOUNT[0],
//        :P_MIN_BAL_AMT[0],
//        :P_PMT_AMT[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_PAYMENT.FIGURE_PAYMENT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   P_REBILL_AMOUNT[0] = __sJT_st.getBigDecimal(2);
   P_MIN_BAL_AMT[0] = __sJT_st.getBigDecimal(3);
   P_PMT_AMT[0] = __sJT_st.getBigDecimal(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:90^28*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal GET_ACCT_POSTING_ID_SHIFT_ID (
    java.math.BigDecimal I_ACCT_ID,
    String I_SESSION,
    String I_IP_ADDRESS,
    String I_USER,
    java.math.BigDecimal I_EVENT_ID,
    java.math.BigDecimal O_ACCOUNT_POSTING_ID[],
    java.math.BigDecimal O_SHIFT_ID[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String I_DOC_TYPE)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:112^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.GET_ACCT_POSTING_ID_SHIFT_ID(
//        :I_ACCT_ID,
//        :I_SESSION,
//        :I_IP_ADDRESS,
//        :I_USER,
//        :I_EVENT_ID,
//        :O_ACCOUNT_POSTING_ID[0],
//        :O_SHIFT_ID[0],
//        :O_ERROR_MSG_ARR[0],
//        :I_DOC_TYPE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.GET_ACCT_POSTING_ID_SHIFT_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCT_ID);
   __sJT_st.setString(3,I_SESSION);
   __sJT_st.setString(4,I_IP_ADDRESS);
   __sJT_st.setString(5,I_USER);
   __sJT_st.setBigDecimal(6,I_EVENT_ID);
   __sJT_st.setString(10,I_DOC_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCOUNT_POSTING_ID[0] = __sJT_st.getBigDecimal(7);
   O_SHIFT_ID[0] = __sJT_st.getBigDecimal(8);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:121^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:127^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.GET_ACCT_POSTING_ID_SHIFT_ID(
//        :I_ACCT_ID,
//        :I_SESSION,
//        :I_IP_ADDRESS,
//        :I_USER,
//        :I_EVENT_ID,
//        :O_ACCOUNT_POSTING_ID[0],
//        :O_SHIFT_ID[0],
//        :O_ERROR_MSG_ARR[0],
//        :I_DOC_TYPE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.GET_ACCT_POSTING_ID_SHIFT_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCT_ID);
   __sJT_st.setString(3,I_SESSION);
   __sJT_st.setString(4,I_IP_ADDRESS);
   __sJT_st.setString(5,I_USER);
   __sJT_st.setBigDecimal(6,I_EVENT_ID);
   __sJT_st.setString(10,I_DOC_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCOUNT_POSTING_ID[0] = __sJT_st.getBigDecimal(7);
   O_SHIFT_ID[0] = __sJT_st.getBigDecimal(8);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:136^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PAYMENT_CART (
    java.math.BigDecimal I_ACCOUNT_ID,
    java.math.BigDecimal I_SHIFT_ID,
    java.math.BigDecimal I_ACCT_VEH_ID,
    java.math.BigDecimal I_ACCT_TAG_ID,
    java.math.BigDecimal I_ACCT_INVENT_ID,
    String I_CART_ITEM,
    java.math.BigDecimal I_AMOUNT,
    String I_ITEM_COMMENT,
    String I_POST_ACTION,
    String I_ABT,
    String I_FEE_TYPE,
    java.math.BigDecimal I_EVENT_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:163^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.PAYMENT_CART(
//        :I_ACCOUNT_ID,
//        :I_SHIFT_ID,
//        :I_ACCT_VEH_ID,
//        :I_ACCT_TAG_ID,
//        :I_ACCT_INVENT_ID,
//        :I_CART_ITEM,
//        :I_AMOUNT,
//        :I_ITEM_COMMENT,
//        :I_POST_ACTION,
//        :I_ABT,
//        :I_FEE_TYPE,
//        :I_EVENT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.PAYMENT_CART(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCOUNT_ID);
   __sJT_st.setBigDecimal(3,I_SHIFT_ID);
   __sJT_st.setBigDecimal(4,I_ACCT_VEH_ID);
   __sJT_st.setBigDecimal(5,I_ACCT_TAG_ID);
   __sJT_st.setBigDecimal(6,I_ACCT_INVENT_ID);
   __sJT_st.setString(7,I_CART_ITEM);
   __sJT_st.setBigDecimal(8,I_AMOUNT);
   __sJT_st.setString(9,I_ITEM_COMMENT);
   __sJT_st.setString(10,I_POST_ACTION);
   __sJT_st.setString(11,I_ABT);
   __sJT_st.setString(12,I_FEE_TYPE);
   __sJT_st.setBigDecimal(13,I_EVENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:176^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:182^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.PAYMENT_CART(
//        :I_ACCOUNT_ID,
//        :I_SHIFT_ID,
//        :I_ACCT_VEH_ID,
//        :I_ACCT_TAG_ID,
//        :I_ACCT_INVENT_ID,
//        :I_CART_ITEM,
//        :I_AMOUNT,
//        :I_ITEM_COMMENT,
//        :I_POST_ACTION,
//        :I_ABT,
//        :I_FEE_TYPE,
//        :I_EVENT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.PAYMENT_CART(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCOUNT_ID);
   __sJT_st.setBigDecimal(3,I_SHIFT_ID);
   __sJT_st.setBigDecimal(4,I_ACCT_VEH_ID);
   __sJT_st.setBigDecimal(5,I_ACCT_TAG_ID);
   __sJT_st.setBigDecimal(6,I_ACCT_INVENT_ID);
   __sJT_st.setString(7,I_CART_ITEM);
   __sJT_st.setBigDecimal(8,I_AMOUNT);
   __sJT_st.setString(9,I_ITEM_COMMENT);
   __sJT_st.setString(10,I_POST_ACTION);
   __sJT_st.setString(11,I_ABT);
   __sJT_st.setString(12,I_FEE_TYPE);
   __sJT_st.setBigDecimal(13,I_EVENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:195^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal UPDATE_SESSION_ID (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    boolean P_UI_CALL,
    java.math.BigDecimal P_OLC_PMT_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:217^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.UPDATE_SESSION_ID(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :P_OLC_PMT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.UPDATE_SESSION_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n      SYS.SQLJUTL.INT2BOOL( :7  ),\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBoolean(7,P_UI_CALL);
   __sJT_st.setBigDecimal(8,P_OLC_PMT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:225^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:231^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.UPDATE_SESSION_ID(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        SYS.SQLJUTL.INT2BOOL(:P_UI_CALL),
//        :P_OLC_PMT_ID,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.UPDATE_SESSION_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n      SYS.SQLJUTL.INT2BOOL( :7  ),\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   __sJT_st.setBoolean(7,P_UI_CALL);
   __sJT_st.setBigDecimal(8,P_OLC_PMT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:239^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal MAKE_PAYMENT (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    oracle.xdb.XMLType XS,
    String P_EMAIL_ADDRESS,
    java.math.BigDecimal P_EVENT_ID,
    String P_UI_CALL,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_UPDATE_PMT_INFO,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:265^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :XS,
//        :P_EMAIL_ADDRESS,
//        :P_EVENT_ID,
//        :P_UI_CALL,
//        :P_PAYMENT_INFO,
//        :P_UPDATE_PMT_INFO,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (XS==null) __sJT_st.setNull(7,2007,"SYS.XMLTYPE"); else __sJT_st.setORAData(7,XS);
   __sJT_st.setString(8,P_EMAIL_ADDRESS);
   __sJT_st.setBigDecimal(9,P_EVENT_ID);
   __sJT_st.setString(10,P_UI_CALL);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(11,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(11,P_PAYMENT_INFO);
   __sJT_st.setString(12,P_UPDATE_PMT_INFO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:277^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:283^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :XS,
//        :P_EMAIL_ADDRESS,
//        :P_EVENT_ID,
//        :P_UI_CALL,
//        :P_PAYMENT_INFO,
//        :P_UPDATE_PMT_INFO,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (XS==null) __sJT_st.setNull(7,2007,"SYS.XMLTYPE"); else __sJT_st.setORAData(7,XS);
   __sJT_st.setString(8,P_EMAIL_ADDRESS);
   __sJT_st.setBigDecimal(9,P_EVENT_ID);
   __sJT_st.setString(10,P_UI_CALL);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(11,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(11,P_PAYMENT_INFO);
   __sJT_st.setString(12,P_UPDATE_PMT_INFO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:295^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal MAKE_PAYMENT_FOR_INVOICE (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    oracle.xdb.XMLType XS,
    String P_EMAIL_ADDRESS,
    java.math.BigDecimal P_EVENT_ID,
    String P_UI_CALL,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_UPDATE_PMT_INFO,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:321^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT_FOR_INVOICE(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :XS,
//        :P_EMAIL_ADDRESS,
//        :P_EVENT_ID,
//        :P_UI_CALL,
//        :P_PAYMENT_INFO,
//        :P_UPDATE_PMT_INFO,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT_FOR_INVOICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (XS==null) __sJT_st.setNull(7,2007,"SYS.XMLTYPE"); else __sJT_st.setORAData(7,XS);
   __sJT_st.setString(8,P_EMAIL_ADDRESS);
   __sJT_st.setBigDecimal(9,P_EVENT_ID);
   __sJT_st.setString(10,P_UI_CALL);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(11,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(11,P_PAYMENT_INFO);
   __sJT_st.setString(12,P_UPDATE_PMT_INFO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:333^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:339^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT_FOR_INVOICE(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :XS,
//        :P_EMAIL_ADDRESS,
//        :P_EVENT_ID,
//        :P_UI_CALL,
//        :P_PAYMENT_INFO,
//        :P_UPDATE_PMT_INFO,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.MAKE_PAYMENT_FOR_INVOICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
   if (XS==null) __sJT_st.setNull(7,2007,"SYS.XMLTYPE"); else __sJT_st.setORAData(7,XS);
   __sJT_st.setString(8,P_EMAIL_ADDRESS);
   __sJT_st.setBigDecimal(9,P_EVENT_ID);
   __sJT_st.setString(10,P_UI_CALL);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(11,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(11,P_PAYMENT_INFO);
   __sJT_st.setString(12,P_UPDATE_PMT_INFO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:351^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal SEND_WELCOME_NOTIFICATION (
    java.math.BigDecimal P_DOC_ID,
    java.math.BigDecimal TAG_ACTIVATION_FEE,
    java.math.BigDecimal TAG_COUNT,
    String P_PLATE_EXPIRY_DATE,
    String P_LIC_PLATE,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:371^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.SEND_WELCOME_NOTIFICATION(
//        :P_DOC_ID,
//        :TAG_ACTIVATION_FEE,
//        :TAG_COUNT,
//        :P_PLATE_EXPIRY_DATE,
//        :P_LIC_PLATE,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.SEND_WELCOME_NOTIFICATION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setBigDecimal(3,TAG_ACTIVATION_FEE);
   __sJT_st.setBigDecimal(4,TAG_COUNT);
   __sJT_st.setString(5,P_PLATE_EXPIRY_DATE);
   __sJT_st.setString(6,P_LIC_PLATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:377^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:383^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.SEND_WELCOME_NOTIFICATION(
//        :P_DOC_ID,
//        :TAG_ACTIVATION_FEE,
//        :TAG_COUNT,
//        :P_PLATE_EXPIRY_DATE,
//        :P_LIC_PLATE,
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.SEND_WELCOME_NOTIFICATION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setBigDecimal(3,TAG_ACTIVATION_FEE);
   __sJT_st.setBigDecimal(4,TAG_COUNT);
   __sJT_st.setString(5,P_PLATE_EXPIRY_DATE);
   __sJT_st.setString(6,P_LIC_PLATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:389^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
  
  public java.math.BigDecimal MAKE_VIOLATION_PAYMENT (
		    java.math.BigDecimal P_ACCOUNT_ID,
		    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID,
		    String P_EMAIL_ADDRESS,
		    java.math.BigDecimal P_EVENT_ID,
		    String P_IP_ADDRESS,
		    String P_SESSION_ID[],
		    String P_UI_CALL,
		    String P_USER_ID,
		    java.math.BigDecimal P_ADDRESS_ID,
		    String P_DELIVERY_METHOD,
		    String P_IS_MOTOR_CYCLE,
		    String P_LIC_PLATE,
		    String P_LIC_STATE,
		    OLC_VPS_BILLING_INFO_REC P_PAYMENT_INFO,
		    String P_SOURCE_USER_NAME,
		    java.math.BigDecimal P_TAG_ACTIVATION_FEE,
		    java.math.BigDecimal P_INITIAL_PREPAID_BAL,
		    boolean P_CONVERT_VIOLATOR,
		    java.math.BigDecimal P_DELIVERY_MODE,
		    java.math.BigDecimal P_TOTAL_PAID_AMOUNT,
		    java.math.BigDecimal P_TOTAL_DISCOUNT_AMOUNT,
		    String P_STATUS_REASON,
		    java.math.BigDecimal P_SERVICE_FEE_PAID,
		    OLC_INV_PMT_ARR P_INVOICE_PAYMENT_ARRAY,
		    OLC_UNINV_PMT_ARR P_UNINVOICE_PAYMENT_ARRAY,
		    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
		    String P_NEW_LOGIN)
		  throws java.sql.SQLException
		  {
		    java.math.BigDecimal __jPt_result=null;
		 try {
		    /*@lineinfo:generated-code*//*@lineinfo:477^5*/

		//  ************************************************************
		//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.MAKE_VIOLATION_PAYMENT(
//		        :P_ACCOUNT_ID,
//		        :P_ACCOUNT_VEHICLE_ID,
//		        :P_EMAIL_ADDRESS,
//		        :P_EVENT_ID,
//		        :P_IP_ADDRESS,
//		        :P_SESSION_ID[0],
//		        :P_UI_CALL,
//		        :P_USER_ID,
//		        :P_ADDRESS_ID,
//		        :P_DELIVERY_METHOD,
//		        :P_IS_MOTOR_CYCLE,
//		        :P_LIC_PLATE,
//		        :P_LIC_STATE,
//		        :P_PAYMENT_INFO,
//		        :P_SOURCE_USER_NAME,
//		        :P_TAG_ACTIVATION_FEE,
//		        :P_INITIAL_PREPAID_BAL,
//		        SYS.SQLJUTL.INT2BOOL(:P_CONVERT_VIOLATOR),
//		        :P_DELIVERY_MODE,
//		        :P_TOTAL_PAID_AMOUNT,
//		        :P_TOTAL_DISCOUNT_AMOUNT,
//		        :P_STATUS_REASON,
//		        :P_SERVICE_FEE_PAID,
//		        :P_INVOICE_PAYMENT_ARRAY,
//		        :P_UNINVOICE_PAYMENT_ARRAY,
//		        :O_ERROR_MSG_ARR[0],
//		        :P_NEW_LOGIN))  };
		//  ************************************************************

		{
		  // declare temps
		  oracle.jdbc.OracleCallableStatement __sJT_st = null;
		  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
		  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
		  try {
		   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.MAKE_VIOLATION_PAYMENT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n      SYS.SQLJUTL.INT2BOOL( :19  ),\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  )  \n; END;";
		   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
		   if (__sJT_ec.isNew())
		   {
		      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
		      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
		      __sJT_st.registerOutParameter(27,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
		   }
		   // set IN parameters
		   __sJT_st.setBigDecimal(2,P_ACCOUNT_ID);
		   __sJT_st.setBigDecimal(3,P_ACCOUNT_VEHICLE_ID);
		   __sJT_st.setString(4,P_EMAIL_ADDRESS);
		   __sJT_st.setBigDecimal(5,P_EVENT_ID);
		   __sJT_st.setString(6,P_IP_ADDRESS);
		   __sJT_st.setString(7,P_SESSION_ID[0]);
		   __sJT_st.setString(8,P_UI_CALL);
		   __sJT_st.setString(9,P_USER_ID);
		   __sJT_st.setBigDecimal(10,P_ADDRESS_ID);
		   __sJT_st.setString(11,P_DELIVERY_METHOD);
		   __sJT_st.setString(12,P_IS_MOTOR_CYCLE);
		   __sJT_st.setString(13,P_LIC_PLATE);
		   __sJT_st.setString(14,P_LIC_STATE);
		   if (P_PAYMENT_INFO==null) __sJT_st.setNull(15,2002,"OL_OWNER.OLC_VPS_BILLING_INFO_REC"); else __sJT_st.setORAData(15,P_PAYMENT_INFO);
		   __sJT_st.setString(16,P_SOURCE_USER_NAME);
		   __sJT_st.setBigDecimal(17,P_TAG_ACTIVATION_FEE);
		   __sJT_st.setBigDecimal(18,P_INITIAL_PREPAID_BAL);
		   __sJT_st.setBoolean(19,P_CONVERT_VIOLATOR);
		   __sJT_st.setBigDecimal(20,P_DELIVERY_MODE);
		   __sJT_st.setBigDecimal(21,P_TOTAL_PAID_AMOUNT);
		   __sJT_st.setBigDecimal(22,P_TOTAL_DISCOUNT_AMOUNT);
		   __sJT_st.setString(23,P_STATUS_REASON);
		   __sJT_st.setBigDecimal(24,P_SERVICE_FEE_PAID);
		   if (P_INVOICE_PAYMENT_ARRAY==null) __sJT_st.setNull(25,2003,"OL_OWNER.OLC_INV_PMT_ARR"); else __sJT_st.setORAData(25,P_INVOICE_PAYMENT_ARRAY);
		   if (P_UNINVOICE_PAYMENT_ARRAY==null) __sJT_st.setNull(26,2003,"OL_OWNER.OLC_UNINV_PMT_ARR"); else __sJT_st.setORAData(26,P_UNINVOICE_PAYMENT_ARRAY);
		   __sJT_st.setString(28,P_NEW_LOGIN);
		  // execute statement
		   __sJT_ec.oracleExecuteUpdate();
		   // retrieve OUT parameters
		   __jPt_result = __sJT_st.getBigDecimal(1);
		   P_SESSION_ID[0] = (String)__sJT_st.getString(7);
		   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(27,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
		  } finally { __sJT_ec.oracleClose(); }
		}


		//  ************************************************************

		/*@lineinfo:user-code*//*@lineinfo:504^22*/
		 } catch(java.sql.SQLException _err) {
		   try {
		      getConnectionContext().getExecutionContext().close();
		      closeConnection();
		      if (__dataSource==null) throw _err;
		    /*@lineinfo:generated-code*//*@lineinfo:510^5*/

		//  ************************************************************
		//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_PAYMENT.MAKE_VIOLATION_PAYMENT(
//		        :P_ACCOUNT_ID,
//		        :P_ACCOUNT_VEHICLE_ID,
//		        :P_EMAIL_ADDRESS,
//		        :P_EVENT_ID,
//		        :P_IP_ADDRESS,
//		        :P_SESSION_ID[0],
//		        :P_UI_CALL,
//		        :P_USER_ID,
//		        :P_ADDRESS_ID,
//		        :P_DELIVERY_METHOD,
//		        :P_IS_MOTOR_CYCLE,
//		        :P_LIC_PLATE,
//		        :P_LIC_STATE,
//		        :P_PAYMENT_INFO,
//		        :P_SOURCE_USER_NAME,
//		        :P_TAG_ACTIVATION_FEE,
//		        :P_INITIAL_PREPAID_BAL,
//		        SYS.SQLJUTL.INT2BOOL(:P_CONVERT_VIOLATOR),
//		        :P_DELIVERY_MODE,
//		        :P_TOTAL_PAID_AMOUNT,
//		        :P_TOTAL_DISCOUNT_AMOUNT,
//		        :P_STATUS_REASON,
//		        :P_SERVICE_FEE_PAID,
//		        :P_INVOICE_PAYMENT_ARRAY,
//		        :P_UNINVOICE_PAYMENT_ARRAY,
//		        :O_ERROR_MSG_ARR[0],
//		        :P_NEW_LOGIN))  };
		//  ************************************************************

		{
		  // declare temps
		  oracle.jdbc.OracleCallableStatement __sJT_st = null;
		  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
		  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
		  try {
		   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_PAYMENT.MAKE_VIOLATION_PAYMENT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  ,\n      SYS.SQLJUTL.INT2BOOL( :19  ),\n       :20  ,\n       :21  ,\n       :22  ,\n       :23  ,\n       :24  ,\n       :25  ,\n       :26  ,\n       :27  ,\n       :28  )  \n; END;";
		   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_PAYMENT",theSqlTS);
		   if (__sJT_ec.isNew())
		   {
		      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
		      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.VARCHAR);
		      __sJT_st.registerOutParameter(27,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
		   }
		   // set IN parameters
		   __sJT_st.setBigDecimal(2,P_ACCOUNT_ID);
		   __sJT_st.setBigDecimal(3,P_ACCOUNT_VEHICLE_ID);
		   __sJT_st.setString(4,P_EMAIL_ADDRESS);
		   __sJT_st.setBigDecimal(5,P_EVENT_ID);
		   __sJT_st.setString(6,P_IP_ADDRESS);
		   __sJT_st.setString(7,P_SESSION_ID[0]);
		   __sJT_st.setString(8,P_UI_CALL);
		   __sJT_st.setString(9,P_USER_ID);
		   __sJT_st.setBigDecimal(10,P_ADDRESS_ID);
		   __sJT_st.setString(11,P_DELIVERY_METHOD);
		   __sJT_st.setString(12,P_IS_MOTOR_CYCLE);
		   __sJT_st.setString(13,P_LIC_PLATE);
		   __sJT_st.setString(14,P_LIC_STATE);
		   if (P_PAYMENT_INFO==null) __sJT_st.setNull(15,2002,"OL_OWNER.OLC_VPS_BILLING_INFO_REC"); else __sJT_st.setORAData(15,P_PAYMENT_INFO);
		   __sJT_st.setString(16,P_SOURCE_USER_NAME);
		   __sJT_st.setBigDecimal(17,P_TAG_ACTIVATION_FEE);
		   __sJT_st.setBigDecimal(18,P_INITIAL_PREPAID_BAL);
		   __sJT_st.setBoolean(19,P_CONVERT_VIOLATOR);
		   __sJT_st.setBigDecimal(20,P_DELIVERY_MODE);
		   __sJT_st.setBigDecimal(21,P_TOTAL_PAID_AMOUNT);
		   __sJT_st.setBigDecimal(22,P_TOTAL_DISCOUNT_AMOUNT);
		   __sJT_st.setString(23,P_STATUS_REASON);
		   __sJT_st.setBigDecimal(24,P_SERVICE_FEE_PAID);
		   if (P_INVOICE_PAYMENT_ARRAY==null) __sJT_st.setNull(25,2003,"OL_OWNER.OLC_INV_PMT_ARR"); else __sJT_st.setORAData(25,P_INVOICE_PAYMENT_ARRAY);
		   if (P_UNINVOICE_PAYMENT_ARRAY==null) __sJT_st.setNull(26,2003,"OL_OWNER.OLC_UNINV_PMT_ARR"); else __sJT_st.setORAData(26,P_UNINVOICE_PAYMENT_ARRAY);
		   __sJT_st.setString(28,P_NEW_LOGIN);
		  // execute statement
		   __sJT_ec.oracleExecuteUpdate();
		   // retrieve OUT parameters
		   __jPt_result = __sJT_st.getBigDecimal(1);
		   P_SESSION_ID[0] = (String)__sJT_st.getString(7);
		   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(27,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
		  } finally { __sJT_ec.oracleClose(); }
		}


		//  ************************************************************

		/*@lineinfo:user-code*//*@lineinfo:537^22*/
		   } catch (java.sql.SQLException _err2) { 
		     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
		     throw _err; 
		  }
		 }
		    return __jPt_result;
		  }
}/*@lineinfo:generated-code*/