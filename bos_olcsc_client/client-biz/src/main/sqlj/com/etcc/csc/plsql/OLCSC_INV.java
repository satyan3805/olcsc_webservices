/*@lineinfo:filename=OLCSC_INV*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_INV
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
  public OLCSC_INV() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_INV(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_INV(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_INV(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal GET_PMT_INFO (
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    OLC_ACCOUNT_TAG_ARR O_ACCOUNT_TAG_ARR[],
    java.math.BigDecimal P_RTL_TRXN_ID[],
    OLC_VPS_INVOICES_ARR O_VPS_INVOICES_ARR[],
    OLC_UNINVOICED_VIOLS_ARR O_UNINVOICED_VIOLS_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    java.math.BigDecimal P_EVENT_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:86^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV.GET_PMT_INFO(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :O_ACCOUNT_TAG_ARR[0],
//        :P_RTL_TRXN_ID[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_EVENT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV.GET_PMT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_VPS_INVOICES_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setBigDecimal(10,P_RTL_TRXN_ID[0]);
   __sJT_st.setBigDecimal(14,P_EVENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCOUNT_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR.getORADataFactory());
   P_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(10);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:99^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:105^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV.GET_PMT_INFO(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :O_ACCOUNT_TAG_ARR[0],
//        :P_RTL_TRXN_ID[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_EVENT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV.GET_PMT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ACCOUNT_TAG_ARR");
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_VPS_INVOICES_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setBigDecimal(10,P_RTL_TRXN_ID[0]);
   __sJT_st.setBigDecimal(14,P_EVENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ACCOUNT_TAG_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ACCOUNT_TAG_ARR.getORADataFactory());
   P_RTL_TRXN_ID[0] = __sJT_st.getBigDecimal(10);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:118^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_OPEN_INVOICES (
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    java.math.BigDecimal P_RETAIL_TRANS_ID,
    OLC_VPS_INVOICES_ARR O_VPS_INVOICES_ARR[],
    OLC_UNINVOICED_VIOLS_ARR O_UNINVOICED_VIOLS_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    boolean P_CALLED_FROM_GUI,
    java.math.BigDecimal P_EVENT_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:145^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV.GET_OPEN_INVOICES(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_RETAIL_TRANS_ID,
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_CALLED_FROM_GUI),
//        :P_EVENT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV.GET_OPEN_INVOICES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n      SYS.SQLJUTL.INT2BOOL( :13  ),\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_VPS_INVOICES_ARR");
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setBigDecimal(9,P_RETAIL_TRANS_ID);
   __sJT_st.setBoolean(13,P_CALLED_FROM_GUI);
   __sJT_st.setBigDecimal(14,P_EVENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:158^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:164^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV.GET_OPEN_INVOICES(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_RETAIL_TRANS_ID,
//        :O_VPS_INVOICES_ARR[0],
//        :O_UNINVOICED_VIOLS_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_CALLED_FROM_GUI),
//        :P_EVENT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV.GET_OPEN_INVOICES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n      SYS.SQLJUTL.INT2BOOL( :13  ),\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_VPS_INVOICES_ARR");
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_UNINVOICED_VIOLS_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setBigDecimal(9,P_RETAIL_TRANS_ID);
   __sJT_st.setBoolean(13,P_CALLED_FROM_GUI);
   __sJT_st.setBigDecimal(14,P_EVENT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_VPS_INVOICES_ARR.getORADataFactory());
   O_UNINVOICED_VIOLS_ARR[0] = (com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_UNINVOICED_VIOLS_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:177^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal POPULATE_LPS (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    java.math.BigDecimal P_RETAIL_TRANS_ID,
    OLCSC_INV_T_VIOLATOR_LPS_ARR P_VIOLATOR_LPS[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:197^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_INV$POPULATE_LPS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_RETAIL_TRANS_ID,
//        :P_VIOLATOR_LPS[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_INV$POPULATE_LPS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLCSC_INV_T_VIOLATOR_LPS_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_LIC_PLATE);
   __sJT_st.setString(5,P_LIC_STATE);
   __sJT_st.setBigDecimal(6,P_RETAIL_TRANS_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_VIOLATOR_LPS[0] = (com.etcc.csc.plsql.OLCSC_INV_T_VIOLATOR_LPS_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLCSC_INV_T_VIOLATOR_LPS_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:203^34*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:209^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_INV$POPULATE_LPS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_RETAIL_TRANS_ID,
//        :P_VIOLATOR_LPS[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_INV$POPULATE_LPS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLCSC_INV_T_VIOLATOR_LPS_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_LIC_PLATE);
   __sJT_st.setString(5,P_LIC_STATE);
   __sJT_st.setBigDecimal(6,P_RETAIL_TRANS_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_VIOLATOR_LPS[0] = (com.etcc.csc.plsql.OLCSC_INV_T_VIOLATOR_LPS_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLCSC_INV_T_VIOLATOR_LPS_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:215^34*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean CHARGEBACKS_EXIST (
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:230^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_INV.CHARGEBACKS_EXIST(
//        :P_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_INV.CHARGEBACKS_EXIST(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:231^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:237^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_INV.CHARGEBACKS_EXIST(
//        :P_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_INV.CHARGEBACKS_EXIST(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_INV",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:238^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/