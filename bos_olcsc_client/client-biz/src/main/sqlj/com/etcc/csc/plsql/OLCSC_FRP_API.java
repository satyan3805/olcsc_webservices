/*@lineinfo:filename=OLCSC_FRP_API*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_FRP_API
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
  public OLCSC_FRP_API() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_FRP_API(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_FRP_API(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_FRP_API(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal PAY_TOLL (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    OLC_UNINVOICED_VIOLS_ARR_N P_UNINVOICED_VIOLS_ARR[],
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_EMAIL_ADDRESS,
    java.math.BigDecimal O_PMT_AMOUNT[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:81^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.PAY_TOLL(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_UNINVOICED_VIOLS_ARR[0],
//        :P_PAYMENT_INFO,
//        :P_EMAIL_ADDRESS,
//        :O_PMT_AMOUNT[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.PAY_TOLL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   if (P_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(4,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(4,P_UNINVOICED_VIOLS_ARR[0]);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(5,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(5,P_PAYMENT_INFO);
   __sJT_st.setString(6,P_EMAIL_ADDRESS);
   __sJT_st.setString(9,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_PMT_AMOUNT[0] = __sJT_st.getBigDecimal(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:89^23*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:95^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.PAY_TOLL(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_UNINVOICED_VIOLS_ARR[0],
//        :P_PAYMENT_INFO,
//        :P_EMAIL_ADDRESS,
//        :O_PMT_AMOUNT[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.PAY_TOLL(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   if (P_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(4,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(4,P_UNINVOICED_VIOLS_ARR[0]);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(5,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(5,P_PAYMENT_INFO);
   __sJT_st.setString(6,P_EMAIL_ADDRESS);
   __sJT_st.setString(9,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_PMT_AMOUNT[0] = __sJT_st.getBigDecimal(7);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:103^23*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void CALCULATE_SAL_PAYMENT_AMOUNT (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NRB,
    OLC_VPS_INV_ARR_N O_VPS_INVOICES_ARR[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR[],
    java.math.BigDecimal O_WAVED_FEES[],
    java.math.BigDecimal O_SAL_PAY_AMOUNT[],
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:124^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CALCULATE_SAL_PAYMENT_AMOUNT(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NRB,
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_WAVED_FEES[0],
//        :O_SAL_PAY_AMOUNT[0],
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CALCULATE_SAL_PAYMENT_AMOUNT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_INVOICE_NRB);
   if (O_VPS_INVOICES_ARR[0]==null) __sJT_st.setNull(4,2003,"OL_OWNER.OLC_VPS_INV_ARR_N"); else __sJT_st.setORAData(4,O_VPS_INVOICES_ARR[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(5,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(5,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(8,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_WAVED_FEES[0] = __sJT_st.getBigDecimal(6);
   O_SAL_PAY_AMOUNT[0] = __sJT_st.getBigDecimal(7);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:132^22*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:138^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CALCULATE_SAL_PAYMENT_AMOUNT(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NRB,
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_WAVED_FEES[0],
//        :O_SAL_PAY_AMOUNT[0],
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CALCULATE_SAL_PAYMENT_AMOUNT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_INVOICE_NRB);
   if (O_VPS_INVOICES_ARR[0]==null) __sJT_st.setNull(4,2003,"OL_OWNER.OLC_VPS_INV_ARR_N"); else __sJT_st.setORAData(4,O_VPS_INVOICES_ARR[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(5,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(5,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(8,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_WAVED_FEES[0] = __sJT_st.getBigDecimal(6);
   O_SAL_PAY_AMOUNT[0] = __sJT_st.getBigDecimal(7);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:146^22*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public boolean CHECK_LP_WAIVER_COUNT_EXCEEDED (
    java.math.BigDecimal I_ACCOUNT_VEHICLE_ID,
    String I_ABT_CODE,
    java.sql.Timestamp O_LAST_UPD[])
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:162^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$CHECK_LP_WAIVER(
//        :I_ACCOUNT_VEHICLE_ID,
//        :I_ABT_CODE,
//        :O_LAST_UPD[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$CHECK_LP_WAIVER(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.TIMESTAMP);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCOUNT_VEHICLE_ID);
   __sJT_st.setString(3,I_ABT_CODE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_LAST_UPD[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:165^30*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:171^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$CHECK_LP_WAIVER(
//        :I_ACCOUNT_VEHICLE_ID,
//        :I_ABT_CODE,
//        :O_LAST_UPD[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$CHECK_LP_WAIVER(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.TIMESTAMP);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCOUNT_VEHICLE_ID);
   __sJT_st.setString(3,I_ABT_CODE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_LAST_UPD[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(4);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:174^30*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean GET_ACCOUNT_VEHICLE_ID (
    String P_PLATE,
    String P_JURISDICTION,
    java.math.BigDecimal O_ACCOUNT_ID[],
    java.math.BigDecimal O_ACCT_VEHICLE_ID[])
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:192^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_ACCOUNT_VEH(
//        :P_PLATE,
//        :P_JURISDICTION,
//        :O_ACCOUNT_ID[0],
//        :O_ACCT_VEHICLE_ID[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_ACCOUNT_VEH(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PLATE);
   __sJT_st.setString(3,P_JURISDICTION);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_ACCOUNT_ID[0] = __sJT_st.getBigDecimal(4);
   O_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:196^37*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:202^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_ACCOUNT_VEH(
//        :P_PLATE,
//        :P_JURISDICTION,
//        :O_ACCOUNT_ID[0],
//        :O_ACCT_VEHICLE_ID[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_ACCOUNT_VEH(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PLATE);
   __sJT_st.setString(3,P_JURISDICTION);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_ACCOUNT_ID[0] = __sJT_st.getBigDecimal(4);
   O_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:206^37*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void CHECK_SAL_ELIGIBILITY_ACCT (
    java.math.BigDecimal P_ACCOUNT_ID,
    String P_IS_ELIGIBLE[],
    java.sql.Timestamp P_LAST_UPD_DATE[],
    java.math.BigDecimal P_SERVICE_FEE[],
    OLC_VPS_INV_ARR_N_ACC O_VPS_INVOICES_ARR_ACC[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_AGENCY,
    String P_DOC_TYPE,
    String P_IP_ADDRESS,
    String P_LOGIN_ID,
    String P_JSESSION_ID,
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:232^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY_ACCT(
//        :P_ACCOUNT_ID,
//        :P_IS_ELIGIBLE[0],
//        :P_LAST_UPD_DATE[0],
//        :P_SERVICE_FEE[0],
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_AGENCY,
//        :P_DOC_TYPE,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_JSESSION_ID,
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY_ACCT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_ID);
   if (O_VPS_INVOICES_ARR_ACC[0]==null) __sJT_st.setNull(5,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC"); else __sJT_st.setORAData(5,O_VPS_INVOICES_ARR_ACC[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(6,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(6,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(8,P_AGENCY);
   __sJT_st.setString(9,P_DOC_TYPE);
   __sJT_st.setString(10,P_IP_ADDRESS);
   __sJT_st.setString(11,P_LOGIN_ID);
   __sJT_st.setString(12,P_JSESSION_ID);
   __sJT_st.setString(13,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   P_IS_ELIGIBLE[0] = (String)__sJT_st.getString(2);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(3);
   P_SERVICE_FEE[0] = __sJT_st.getBigDecimal(4);
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:245^22*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:251^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY_ACCT(
//        :P_ACCOUNT_ID,
//        :P_IS_ELIGIBLE[0],
//        :P_LAST_UPD_DATE[0],
//        :P_SERVICE_FEE[0],
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_AGENCY,
//        :P_DOC_TYPE,
//        :P_IP_ADDRESS,
//        :P_LOGIN_ID,
//        :P_JSESSION_ID,
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY_ACCT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_ID);
   if (O_VPS_INVOICES_ARR_ACC[0]==null) __sJT_st.setNull(5,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC"); else __sJT_st.setORAData(5,O_VPS_INVOICES_ARR_ACC[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(6,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(6,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(8,P_AGENCY);
   __sJT_st.setString(9,P_DOC_TYPE);
   __sJT_st.setString(10,P_IP_ADDRESS);
   __sJT_st.setString(11,P_LOGIN_ID);
   __sJT_st.setString(12,P_JSESSION_ID);
   __sJT_st.setString(13,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   P_IS_ELIGIBLE[0] = (String)__sJT_st.getString(2);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(3);
   P_SERVICE_FEE[0] = __sJT_st.getBigDecimal(4);
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:264^22*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal GET_PAYMENT_MADE_ONLINE (
    String P_INVOICE_NO,
    String P_AGENCY)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:279^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.GET_PAYMENT_MADE_ONLINE(
//        :P_INVOICE_NO,
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.GET_PAYMENT_MADE_ONLINE(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_INVOICE_NO);
   __sJT_st.setString(3,P_AGENCY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:281^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:287^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.GET_PAYMENT_MADE_ONLINE(
//        :P_INVOICE_NO,
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.GET_PAYMENT_MADE_ONLINE(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_INVOICE_NO);
   __sJT_st.setString(3,P_AGENCY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:289^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public OLCSC_FRP_API_INVOICE_TBL GET_INVOICES (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    String P_SOLO_INVOICE_ID)
  throws java.sql.SQLException
  {
    OLCSC_FRP_API_INVOICE_TBL __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:307^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL3(OL_OWNER.OLCSC_FRP_API.GET_INVOICES(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_SOLO_INVOICE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL3(OL_OWNER.OLCSC_FRP_API.GET_INVOICES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_FRP_API_INVOICE_TBL");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
   __sJT_st.setString(5,P_SOLO_INVOICE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:311^29*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:317^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL3(OL_OWNER.OLCSC_FRP_API.GET_INVOICES(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_SOLO_INVOICE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL3(OL_OWNER.OLCSC_FRP_API.GET_INVOICES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_FRP_API_INVOICE_TBL");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
   __sJT_st.setString(5,P_SOLO_INVOICE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:321^29*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void GET_UNINVOICED_VIOLATION_INV (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    java.math.BigDecimal P_ACCOUNT_ID,
    java.math.BigDecimal O_TOTAL_AMOUNT[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR_N[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:340^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION_INV(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_ACCOUNT_ID,
//        :O_TOTAL_AMOUNT[0],
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION_INV(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setBigDecimal(3,P_ACCOUNT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(4);
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:346^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:352^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION_INV(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_ACCOUNT_ID,
//        :O_TOTAL_AMOUNT[0],
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION_INV(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setBigDecimal(3,P_ACCOUNT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(4);
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:358^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal PAY_INVOICE (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NRB,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_EMAIL_ADDRESS,
    java.math.BigDecimal O_PMT_AMOUNT[],
    OLC_VPS_INV_ARR_N O_VPS_INVOICES_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:380^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.PAY_INVOICE(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NRB,
//        :P_PAYMENT_INFO,
//        :P_EMAIL_ADDRESS,
//        :O_PMT_AMOUNT[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.PAY_INVOICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NRB);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(5,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(5,P_PAYMENT_INFO);
   __sJT_st.setString(6,P_EMAIL_ADDRESS);
   __sJT_st.setString(10,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PMT_AMOUNT[0] = __sJT_st.getBigDecimal(7);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:389^23*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:395^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.PAY_INVOICE(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NRB,
//        :P_PAYMENT_INFO,
//        :P_EMAIL_ADDRESS,
//        :O_PMT_AMOUNT[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.PAY_INVOICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NRB);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(5,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(5,P_PAYMENT_INFO);
   __sJT_st.setString(6,P_EMAIL_ADDRESS);
   __sJT_st.setString(10,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PMT_AMOUNT[0] = __sJT_st.getBigDecimal(7);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:404^23*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void CHECK_SAL_ELIGIBILITY (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    String P_DOC_TYPE,
    String P_USER,
    String P_IP_ADDRESS,
    String P_SOURCE_USER_NAME,
    String P_JSESSION_ID,
    String P_IS_ELIGIBLE[],
    java.sql.Timestamp P_LAST_UPD_DATE[],
    java.math.BigDecimal P_SERVICE_FEE[],
    OLC_VPS_INV_ARR_N O_VPS_INVOICES_ARR[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_AGENCY,
    String O_SESSION_ID[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:433^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_DOC_TYPE,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SOURCE_USER_NAME,
//        :P_JSESSION_ID,
//        :P_IS_ELIGIBLE[0],
//        :P_LAST_UPD_DATE[0],
//        :P_SERVICE_FEE[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_AGENCY,
//        :O_SESSION_ID[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"18com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_INVOICE_NO);
   __sJT_st.setString(4,P_DOC_TYPE);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_SOURCE_USER_NAME);
   __sJT_st.setString(8,P_JSESSION_ID);
   if (O_VPS_INVOICES_ARR[0]==null) __sJT_st.setNull(12,2003,"OL_OWNER.OLC_VPS_INV_ARR_N"); else __sJT_st.setORAData(12,O_VPS_INVOICES_ARR[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(13,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(13,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(15,P_AGENCY);
   __sJT_st.setString(16,O_SESSION_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   P_IS_ELIGIBLE[0] = (String)__sJT_st.getString(9);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   P_SERVICE_FEE[0] = __sJT_st.getBigDecimal(11);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_SESSION_ID[0] = (String)__sJT_st.getString(16);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:449^33*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:455^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_DOC_TYPE,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SOURCE_USER_NAME,
//        :P_JSESSION_ID,
//        :P_IS_ELIGIBLE[0],
//        :P_LAST_UPD_DATE[0],
//        :P_SERVICE_FEE[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_AGENCY,
//        :O_SESSION_ID[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CHECK_SAL_ELIGIBILITY(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"19com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_INVOICE_NO);
   __sJT_st.setString(4,P_DOC_TYPE);
   __sJT_st.setString(5,P_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_SOURCE_USER_NAME);
   __sJT_st.setString(8,P_JSESSION_ID);
   if (O_VPS_INVOICES_ARR[0]==null) __sJT_st.setNull(12,2003,"OL_OWNER.OLC_VPS_INV_ARR_N"); else __sJT_st.setORAData(12,O_VPS_INVOICES_ARR[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(13,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(13,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(15,P_AGENCY);
   __sJT_st.setString(16,O_SESSION_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   P_IS_ELIGIBLE[0] = (String)__sJT_st.getString(9);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   P_SERVICE_FEE[0] = __sJT_st.getBigDecimal(11);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_SESSION_ID[0] = (String)__sJT_st.getString(16);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:471^33*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public OLCSC_FRP_API_INVOICE_TBL_ACC GET_INVOICES_BY_ACC_VEH_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID)
  throws java.sql.SQLException
  {
    OLCSC_FRP_API_INVOICE_TBL_ACC __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:485^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL4(OL_OWNER.OLCSC_FRP_API.GET_INVOICES_BY_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL4(OL_OWNER.OLCSC_FRP_API.GET_INVOICES_BY_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"20com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_FRP_API_INVOICE_TBL_ACC");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL_ACC)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL_ACC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:486^32*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:492^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL4(OL_OWNER.OLCSC_FRP_API.GET_INVOICES_BY_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL4(OL_OWNER.OLCSC_FRP_API.GET_INVOICES_BY_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"21com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_FRP_API_INVOICE_TBL_ACC");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL_ACC)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_FRP_API_INVOICE_TBL_ACC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:493^32*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean GET_INV_ACC_VEH_ID (
    java.math.BigDecimal I_ACCOUNT_VEHICLE_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:508^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.GET_INV_ACC_VEH_ID(
//        :I_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.GET_INV_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"22com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:509^32*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:515^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.GET_INV_ACC_VEH_ID(
//        :I_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.GET_INV_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"23com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:516^32*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void CHECK_MAT_ELIGIBILITY (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_DOC_TYPE,
    String P_USER,
    String P_IP_ADDRESS,
    String P_SOURCE_USER_NAME,
    String P_JSESSION_ID,
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR_N[],
    String P_IS_ELIGIBLE[],
    java.sql.Timestamp P_LAST_UPD_DATE[],
    java.math.BigDecimal P_TOTAL_AMOUNT[],
    java.math.BigDecimal P_SERVICE_FEE[],
    java.math.BigDecimal P_MAT_PAY_AMOUNT[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_AGENCY,
    String O_SESSION_ID[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:545^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CHECK_MAT_ELIGIBILITY(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_DOC_TYPE,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SOURCE_USER_NAME,
//        :P_JSESSION_ID,
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :P_IS_ELIGIBLE[0],
//        :P_LAST_UPD_DATE[0],
//        :P_TOTAL_AMOUNT[0],
//        :P_SERVICE_FEE[0],
//        :P_MAT_PAY_AMOUNT[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_AGENCY,
//        :O_SESSION_ID[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CHECK_MAT_ELIGIBILITY(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"24com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_SOURCE_USER_NAME);
   __sJT_st.setString(7,P_JSESSION_ID);
   if (O_UNINVOICED_VIOLS_ARR_N[0]==null) __sJT_st.setNull(8,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(8,O_UNINVOICED_VIOLS_ARR_N[0]);
   __sJT_st.setString(15,P_AGENCY);
   __sJT_st.setString(16,O_SESSION_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   P_IS_ELIGIBLE[0] = (String)__sJT_st.getString(9);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   P_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(11);
   P_SERVICE_FEE[0] = __sJT_st.getBigDecimal(12);
   P_MAT_PAY_AMOUNT[0] = __sJT_st.getBigDecimal(13);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_SESSION_ID[0] = (String)__sJT_st.getString(16);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:561^33*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:567^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CHECK_MAT_ELIGIBILITY(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_DOC_TYPE,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_SOURCE_USER_NAME,
//        :P_JSESSION_ID,
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :P_IS_ELIGIBLE[0],
//        :P_LAST_UPD_DATE[0],
//        :P_TOTAL_AMOUNT[0],
//        :P_SERVICE_FEE[0],
//        :P_MAT_PAY_AMOUNT[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_AGENCY,
//        :O_SESSION_ID[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CHECK_MAT_ELIGIBILITY(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"25com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_SOURCE_USER_NAME);
   __sJT_st.setString(7,P_JSESSION_ID);
   if (O_UNINVOICED_VIOLS_ARR_N[0]==null) __sJT_st.setNull(8,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(8,O_UNINVOICED_VIOLS_ARR_N[0]);
   __sJT_st.setString(15,P_AGENCY);
   __sJT_st.setString(16,O_SESSION_ID[0]);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   P_IS_ELIGIBLE[0] = (String)__sJT_st.getString(9);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   P_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(11);
   P_SERVICE_FEE[0] = __sJT_st.getBigDecimal(12);
   P_MAT_PAY_AMOUNT[0] = __sJT_st.getBigDecimal(13);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_SESSION_ID[0] = (String)__sJT_st.getString(16);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:583^33*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public void GET_UNINVOICED_VIOLATION (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    java.math.BigDecimal O_TOTAL_AMOUNT[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR_N[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:600^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :O_TOTAL_AMOUNT[0],
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"26com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(3);
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:605^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:611^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :O_TOTAL_AMOUNT[0],
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINVOICED_VIOLATION(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"27com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(3,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(3);
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:616^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public void GET_INV_VIOLATION_BY_INV_NBR (
    String P_INVOICE_NO,
    OLC_VPS_INV_ARR_N O_VPS_INVOICES_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:632^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATION_BY_INV_NBR(
//        :P_INVOICE_NO,
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATION_BY_INV_NBR(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"28com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_INVOICE_NO);
   __sJT_st.setString(4,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:636^22*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:642^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATION_BY_INV_NBR(
//        :P_INVOICE_NO,
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATION_BY_INV_NBR(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"29com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_INVOICE_NO);
   __sJT_st.setString(4,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:646^22*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public void GET_INVOICE_VIOLATION (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    OLC_VPS_INV_ARR_N O_VPS_INVOICES_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:663^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INVOICE_VIOLATION(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INVOICE_VIOLATION(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"30com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:668^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:674^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INVOICE_VIOLATION(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INVOICE_VIOLATION(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"31com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(5,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_LIC_PLATE);
   __sJT_st.setString(2,P_LIC_STATE);
   __sJT_st.setString(3,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(5,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:679^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public void GET_INV_VIO_ACC_VEH_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID,
    OLC_VPS_INV_ARR_N_ACC O_VPS_INVOICES_ARR_ACC[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:694^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INV_VIO_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID,
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INV_VIO_ACC_VEH_ID(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"32com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:697^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:703^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INV_VIO_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID,
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INV_VIO_ACC_VEH_ID(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"33com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:706^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public boolean CHK_NOT_EXIST_BY_ACC_VEH_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:720^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_NOT_EXIST_BY_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_NOT_EXIST_BY_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"34com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:721^32*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:727^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_NOT_EXIST_BY_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_NOT_EXIST_BY_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"35com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:728^32*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PAY_INVOICE_BY_ACC_ID (
    java.math.BigDecimal P_ACCOUNT_ID,
    OLC_PAYMENT_INFO_REC P_PAYMENT_INFO,
    String P_EMAIL_ADDRESS,
    java.math.BigDecimal O_PMT_AMOUNT[],
    OLC_VPS_INV_ARR_N_ACC O_VPS_INVOICES_ARR_ACC[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:749^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.PAY_INVOICE_BY_ACC_ID(
//        :P_ACCOUNT_ID,
//        :P_PAYMENT_INFO,
//        :P_EMAIL_ADDRESS,
//        :O_PMT_AMOUNT[0],
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.PAY_INVOICE_BY_ACC_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"36com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(3,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(3,P_PAYMENT_INFO);
   __sJT_st.setString(4,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PMT_AMOUNT[0] = __sJT_st.getBigDecimal(5);
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:756^23*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:762^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.PAY_INVOICE_BY_ACC_ID(
//        :P_ACCOUNT_ID,
//        :P_PAYMENT_INFO,
//        :P_EMAIL_ADDRESS,
//        :O_PMT_AMOUNT[0],
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_SESSION_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.PAY_INVOICE_BY_ACC_ID(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"37com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_ID);
   if (P_PAYMENT_INFO==null) __sJT_st.setNull(3,2002,"OL_OWNER.OLC_PAYMENT_INFO_REC"); else __sJT_st.setORAData(3,P_PAYMENT_INFO);
   __sJT_st.setString(4,P_EMAIL_ADDRESS);
   __sJT_st.setString(8,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_PMT_AMOUNT[0] = __sJT_st.getBigDecimal(5);
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(6,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:769^23*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean CHK_INV_EXIST_ACC_VEH_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:784^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_INV_EXIST_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_INV_EXIST_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"38com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:785^32*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:791^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_INV_EXIST_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_FRP_API.CHK_INV_EXIST_ACC_VEH_ID(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"39com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:792^32*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void GET_UNINV_VIOLS_BY_ACCT_VEH_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID,
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR_N[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:808^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIOLS_BY_ACCT_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID,
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIOLS_BY_ACCT_VEH_ID(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"40com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:811^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:817^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIOLS_BY_ACCT_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID,
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIOLS_BY_ACCT_VEH_ID(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"41com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:820^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public OLC_VPS_INV_ARR_D GET_LINE_ITEMS_ACC_VEH_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID)
  throws java.sql.SQLException
  {
    OLC_VPS_INV_ARR_D __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:834^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.GET_LINE_ITEMS_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.GET_LINE_ITEMS_ACC_VEH_ID(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"42com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLC_VPS_INV_ARR_D");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_D)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_VPS_INV_ARR_D.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:835^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:841^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.GET_LINE_ITEMS_ACC_VEH_ID(
//        :P_ACCOUNT_VEHICLE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.GET_LINE_ITEMS_ACC_VEH_ID(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"43com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLC_VPS_INV_ARR_D");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_D)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_VPS_INV_ARR_D.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:842^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void CAL_SAL_PMT_AMT_BY_ACCT (
    java.math.BigDecimal P_ACCOUNT_ID,
    OLC_VPS_INV_ARR_N_ACC O_VPS_INVOICES_ARR_ACC[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR[],
    java.math.BigDecimal O_WAVED_FEES[],
    java.math.BigDecimal O_SAL_PAY_AMOUNT[],
    String P_SESSION_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:861^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CAL_SAL_PMT_AMT_BY_ACCT(
//        :P_ACCOUNT_ID,
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_WAVED_FEES[0],
//        :O_SAL_PAY_AMOUNT[0],
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CAL_SAL_PMT_AMT_BY_ACCT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"44com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_ID);
   if (O_VPS_INVOICES_ARR_ACC[0]==null) __sJT_st.setNull(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC"); else __sJT_st.setORAData(2,O_VPS_INVOICES_ARR_ACC[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(3,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(3,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(6,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_WAVED_FEES[0] = __sJT_st.getBigDecimal(4);
   O_SAL_PAY_AMOUNT[0] = __sJT_st.getBigDecimal(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:867^22*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:873^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.CAL_SAL_PMT_AMT_BY_ACCT(
//        :P_ACCOUNT_ID,
//        :O_VPS_INVOICES_ARR_ACC[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_WAVED_FEES[0],
//        :O_SAL_PAY_AMOUNT[0],
//        :P_SESSION_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.CAL_SAL_PMT_AMT_BY_ACCT(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"45com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(4,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACCOUNT_ID);
   if (O_VPS_INVOICES_ARR_ACC[0]==null) __sJT_st.setNull(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N_ACC"); else __sJT_st.setORAData(2,O_VPS_INVOICES_ARR_ACC[0]);
   if (O_UNINVOICED_VIOLS_ARR[0]==null) __sJT_st.setNull(3,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N"); else __sJT_st.setORAData(3,O_UNINVOICED_VIOLS_ARR[0]);
   __sJT_st.setString(6,P_SESSION_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR_ACC[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N_ACC.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_WAVED_FEES[0] = __sJT_st.getBigDecimal(4);
   O_SAL_PAY_AMOUNT[0] = __sJT_st.getBigDecimal(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:879^22*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public void GET_UNINV_VIO_INV_ACC_VEH_ID (
    java.math.BigDecimal P_ACC_VEH_ID,
    java.math.BigDecimal O_TOTAL_AMOUNT[],
    OLC_UNINVOICED_VIOLS_ARR_N O_UNINVOICED_VIOLS_ARR_N[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:895^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIO_INV_ACC_VEH_ID(
//        :P_ACC_VEH_ID,
//        :O_TOTAL_AMOUNT[0],
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIO_INV_ACC_VEH_ID(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"46com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACC_VEH_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(2);
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:899^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:905^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIO_INV_ACC_VEH_ID(
//        :P_ACC_VEH_ID,
//        :O_TOTAL_AMOUNT[0],
//        :O_UNINVOICED_VIOLS_ARR_N[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_UNINV_VIO_INV_ACC_VEH_ID(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"47com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N");
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_ACC_VEH_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_TOTAL_AMOUNT[0] = __sJT_st.getBigDecimal(2);
   O_UNINVOICED_VIOLS_ARR_N[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:909^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public void GET_INV_VIOLATIONS_BY_INV_NBR (
    String P_INVOICE_NO,
    OLC_VPS_INV_ARR_N O_VPS_INVOICES_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:924^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATIONS_BY_INV_NBR(
//        :P_INVOICE_NO,
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATIONS_BY_INV_NBR(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"48com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:927^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:933^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATIONS_BY_INV_NBR(
//        :P_INVOICE_NO,
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0])  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_FRP_API.GET_INV_VIOLATIONS_BY_INV_NBR(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"49com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VPS_INV_ARR_N");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(1,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_N)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VPS_INV_ARR_N.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:936^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal GET_PMT_MADE_BY_AV_ID (
    java.math.BigDecimal P_ACCOUNT_VEHICLE_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:950^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.GET_PMT_MADE_BY_AV_ID(
//        :P_ACCOUNT_VEHICLE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.GET_PMT_MADE_BY_AV_ID(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"50com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:951^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:957^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_FRP_API.GET_PMT_MADE_BY_AV_ID(
//        :P_ACCOUNT_VEHICLE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_FRP_API.GET_PMT_MADE_BY_AV_ID(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"51com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCOUNT_VEHICLE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:958^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean GET_INVOICE_ACCOUNT_VEHICLE_ID (
    String P_PLATE,
    String P_JURISDICTION,
    String P_INVOICE_NUMBER,
    java.math.BigDecimal O_ACCOUNT_ID[],
    java.math.BigDecimal O_ACCT_VEHICLE_ID[])
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:977^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_INVOICE_ACC(
//        :P_PLATE,
//        :P_JURISDICTION,
//        :P_INVOICE_NUMBER,
//        :O_ACCOUNT_ID[0],
//        :O_ACCT_VEHICLE_ID[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_INVOICE_ACC(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"52com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PLATE);
   __sJT_st.setString(3,P_JURISDICTION);
   __sJT_st.setString(4,P_INVOICE_NUMBER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_ACCOUNT_ID[0] = __sJT_st.getBigDecimal(5);
   O_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:982^37*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:988^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_INVOICE_ACC(
//        :P_PLATE,
//        :P_JURISDICTION,
//        :P_INVOICE_NUMBER,
//        :O_ACCOUNT_ID[0],
//        :O_ACCT_VEHICLE_ID[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_FRP_API$GET_INVOICE_ACC(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"53com.etcc.csc.plsql.OLCSC_FRP_API",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PLATE);
   __sJT_st.setString(3,P_JURISDICTION);
   __sJT_st.setString(4,P_INVOICE_NUMBER);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_ACCOUNT_ID[0] = __sJT_st.getBigDecimal(5);
   O_ACCT_VEHICLE_ID[0] = __sJT_st.getBigDecimal(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:993^37*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/