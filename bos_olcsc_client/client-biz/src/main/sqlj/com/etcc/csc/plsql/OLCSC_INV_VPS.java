/*@lineinfo:filename=OLCSC_INV_VPS*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_INV_VPS
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
  public OLCSC_INV_VPS() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_INV_VPS(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_INV_VPS(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_INV_VPS(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal GET_NAMES (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    String P_FIRST_NAME[],
    String P_LAST_NAME[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:78^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_NAMES(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_FIRST_NAME[0],
//        :P_LAST_NAME[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_NAMES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FIRST_NAME[0] = (String)__sJT_st.getString(5);
   P_LAST_NAME[0] = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:83^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:89^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_NAMES(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_FIRST_NAME[0],
//        :P_LAST_NAME[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_NAMES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FIRST_NAME[0] = (String)__sJT_st.getString(5);
   P_LAST_NAME[0] = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:94^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public OLC_VPS_INV_ARR_D GET_LINE_ITEMS (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO)
  throws java.sql.SQLException
  {
    OLC_VPS_INV_ARR_D __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:111^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_LINE_ITEMS(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_LINE_ITEMS(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLC_VPS_INV_ARR_D");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_D)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_VPS_INV_ARR_D.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:114^23*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:120^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_LINE_ITEMS(
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_LINE_ITEMS(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLC_VPS_INV_ARR_D");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_D)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_VPS_INV_ARR_D.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:123^23*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_LOCATION (
    String P_LANE_ID)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:138^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_LOCATION(
//        :P_LANE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_LOCATION(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_LANE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:139^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:145^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_LOCATION(
//        :P_LANE_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_LOCATION(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_LANE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:146^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public OLCSC_INV_VPS_INVOICE_TBL GET_INVOICES (
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    String P_SOLO_INVOICE_ID)
  throws java.sql.SQLException
  {
    OLCSC_INV_VPS_INVOICE_TBL __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:164^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL2(OL_OWNER.OLCSC_INV_VPS.GET_INVOICES(
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
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL2(OL_OWNER.OLCSC_INV_VPS.GET_INVOICES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_INV_VPS_INVOICE_TBL");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
   __sJT_st.setString(5,P_SOLO_INVOICE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_INV_VPS_INVOICE_TBL)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_INV_VPS_INVOICE_TBL.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:168^29*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:174^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL2(OL_OWNER.OLCSC_INV_VPS.GET_INVOICES(
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
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL2(OL_OWNER.OLCSC_INV_VPS.GET_INVOICES(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_INV_VPS_INVOICE_TBL");
   }
   // set IN parameters
   __sJT_st.setString(2,P_LIC_PLATE);
   __sJT_st.setString(3,P_LIC_STATE);
   __sJT_st.setString(4,P_INVOICE_NO);
   __sJT_st.setString(5,P_SOLO_INVOICE_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_INV_VPS_INVOICE_TBL)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_INV_VPS_INVOICE_TBL.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:178^29*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_INVOICE_DOC (
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    String P_INVOICE_DOC[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    boolean P_CALLED_FROM_GUI,
    String P_AGENCY)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:204^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_INVOICE_DOC(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_INVOICE_DOC[0],
//        :O_ERROR_MSG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_CALLED_FROM_GUI),
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_INVOICE_DOC(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n      SYS.SQLJUTL.INT2BOOL( :12  ),\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setString(9,P_INVOICE_NO);
   __sJT_st.setBoolean(12,P_CALLED_FROM_GUI);
   __sJT_st.setString(13,P_AGENCY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_INVOICE_DOC[0] = (String)__sJT_st.getString(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:216^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:222^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_INVOICE_DOC(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_INVOICE_DOC[0],
//        :O_ERROR_MSG_ARR[0],
//        SYS.SQLJUTL.INT2BOOL(:P_CALLED_FROM_GUI),
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_INVOICE_DOC(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n      SYS.SQLJUTL.INT2BOOL( :12  ),\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setString(9,P_INVOICE_NO);
   __sJT_st.setBoolean(12,P_CALLED_FROM_GUI);
   __sJT_st.setString(13,P_AGENCY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_INVOICE_DOC[0] = (String)__sJT_st.getString(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:234^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_PAYMENT_MADE_ONLINE (
    String P_INVOICE_NO,
    String P_AGENCY)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:250^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_PAYMENT_MADE_ONLINE(
//        :P_INVOICE_NO,
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_PAYMENT_MADE_ONLINE(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:252^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:258^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_PAYMENT_MADE_ONLINE(
//        :P_INVOICE_NO,
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_PAYMENT_MADE_ONLINE(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:260^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_VPS_INVOICE (
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_USER_ID,
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_LIC_PLATE,
    String P_LIC_STATE,
    String P_INVOICE_NO,
    String P_FIRST_NAME[],
    String P_LAST_NAME[],
    java.sql.Timestamp P_LAST_UPD_DATE[],
    OLC_VPS_INV_ARR_H O_VPS_INVOICES_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    String P_PAY_PLAN_EXISTS[],
    boolean P_CALLED_FROM_GUI,
    String P_AGENCY)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:290^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_VPS_INVOICE(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_FIRST_NAME[0],
//        :P_LAST_NAME[0],
//        :P_LAST_UPD_DATE[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_PAY_PLAN_EXISTS[0],
//        SYS.SQLJUTL.INT2BOOL(:P_CALLED_FROM_GUI),
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_VPS_INVOICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n      SYS.SQLJUTL.INT2BOOL( :16  ),\n       :17  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_VPS_INV_ARR_H");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setString(9,P_INVOICE_NO);
   __sJT_st.setBoolean(16,P_CALLED_FROM_GUI);
   __sJT_st.setString(17,P_AGENCY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FIRST_NAME[0] = (String)__sJT_st.getString(10);
   P_LAST_NAME[0] = (String)__sJT_st.getString(11);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(12);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_H)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_VPS_INV_ARR_H.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   P_PAY_PLAN_EXISTS[0] = (String)__sJT_st.getString(15);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:306^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:312^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.GET_VPS_INVOICE(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_LIC_PLATE,
//        :P_LIC_STATE,
//        :P_INVOICE_NO,
//        :P_FIRST_NAME[0],
//        :P_LAST_NAME[0],
//        :P_LAST_UPD_DATE[0],
//        :O_VPS_INVOICES_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :P_PAY_PLAN_EXISTS[0],
//        SYS.SQLJUTL.INT2BOOL(:P_CALLED_FROM_GUI),
//        :P_AGENCY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.GET_VPS_INVOICE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n      SYS.SQLJUTL.INT2BOOL( :16  ),\n       :17  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_VPS_INV_ARR_H");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setBigDecimal(5,P_DOC_ID);
   __sJT_st.setString(6,P_DOC_TYPE);
   __sJT_st.setString(7,P_LIC_PLATE);
   __sJT_st.setString(8,P_LIC_STATE);
   __sJT_st.setString(9,P_INVOICE_NO);
   __sJT_st.setBoolean(16,P_CALLED_FROM_GUI);
   __sJT_st.setString(17,P_AGENCY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FIRST_NAME[0] = (String)__sJT_st.getString(10);
   P_LAST_NAME[0] = (String)__sJT_st.getString(11);
   P_LAST_UPD_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(12);
   O_VPS_INVOICES_ARR[0] = (com.etcc.csc.plsql.OLC_VPS_INV_ARR_H)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_VPS_INV_ARR_H.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   P_PAY_PLAN_EXISTS[0] = (String)__sJT_st.getString(15);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:328^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal PAYMENT_PLAN (
    String P_SESSION,
    String P_IP_ADDRESS,
    String P_LIC_KEY,
    String P_INVOICE,
    String P_LIC_PLATE,
    java.sql.Timestamp P_LAST_UPDATED[],
    java.sql.Timestamp P_FIRST_PAYMENT_DATE[],
    java.math.BigDecimal P_FIRST_PAYMENT_AMT[],
    String P_PARTY_NAME[],
    String P_PARTY_ADDRESS[],
    String P_PHONE_NUMBER[],
    java.math.BigDecimal P_TOTAL_PLAN_AMT[],
    java.math.BigDecimal P_TOTAL_AMT_PAID[],
    java.math.BigDecimal P_TOTAL_AMT_DUE[],
    java.math.BigDecimal P_TOTAL_DELIQUENT[],
    OLC_VPS_PAY_PLAN_ARR P_PAY_DETAILS[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:359^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.PAYMENT_PLAN(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_LIC_KEY,
//        :P_INVOICE,
//        :P_LIC_PLATE,
//        :P_LAST_UPDATED[0],
//        :P_FIRST_PAYMENT_DATE[0],
//        :P_FIRST_PAYMENT_AMT[0],
//        :P_PARTY_NAME[0],
//        :P_PARTY_ADDRESS[0],
//        :P_PHONE_NUMBER[0],
//        :P_TOTAL_PLAN_AMT[0],
//        :P_TOTAL_AMT_PAID[0],
//        :P_TOTAL_AMT_DUE[0],
//        :P_TOTAL_DELIQUENT[0],
//        :P_PAY_DETAILS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.PAYMENT_PLAN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(17,2003,"OL_OWNER.OLC_VPS_PAY_PLAN_ARR");
      __sJT_st.registerOutParameter(18,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LIC_KEY);
   __sJT_st.setString(5,P_INVOICE);
   __sJT_st.setString(6,P_LIC_PLATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_LAST_UPDATED[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(7);
   P_FIRST_PAYMENT_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(8);
   P_FIRST_PAYMENT_AMT[0] = __sJT_st.getBigDecimal(9);
   P_PARTY_NAME[0] = (String)__sJT_st.getString(10);
   P_PARTY_ADDRESS[0] = (String)__sJT_st.getString(11);
   P_PHONE_NUMBER[0] = (String)__sJT_st.getString(12);
   P_TOTAL_PLAN_AMT[0] = __sJT_st.getBigDecimal(13);
   P_TOTAL_AMT_PAID[0] = __sJT_st.getBigDecimal(14);
   P_TOTAL_AMT_DUE[0] = __sJT_st.getBigDecimal(15);
   P_TOTAL_DELIQUENT[0] = __sJT_st.getBigDecimal(16);
   P_PAY_DETAILS[0] = (com.etcc.csc.plsql.OLC_VPS_PAY_PLAN_ARR)__sJT_st.getORAData(17,com.etcc.csc.plsql.OLC_VPS_PAY_PLAN_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(18,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:376^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:382^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.PAYMENT_PLAN(
//        :P_SESSION,
//        :P_IP_ADDRESS,
//        :P_LIC_KEY,
//        :P_INVOICE,
//        :P_LIC_PLATE,
//        :P_LAST_UPDATED[0],
//        :P_FIRST_PAYMENT_DATE[0],
//        :P_FIRST_PAYMENT_AMT[0],
//        :P_PARTY_NAME[0],
//        :P_PARTY_ADDRESS[0],
//        :P_PHONE_NUMBER[0],
//        :P_TOTAL_PLAN_AMT[0],
//        :P_TOTAL_AMT_PAID[0],
//        :P_TOTAL_AMT_DUE[0],
//        :P_TOTAL_DELIQUENT[0],
//        :P_PAY_DETAILS[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.PAYMENT_PLAN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  ,\n       :17  ,\n       :18  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(8,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(16,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(17,2003,"OL_OWNER.OLC_VPS_PAY_PLAN_ARR");
      __sJT_st.registerOutParameter(18,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LIC_KEY);
   __sJT_st.setString(5,P_INVOICE);
   __sJT_st.setString(6,P_LIC_PLATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_LAST_UPDATED[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(7);
   P_FIRST_PAYMENT_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(8);
   P_FIRST_PAYMENT_AMT[0] = __sJT_st.getBigDecimal(9);
   P_PARTY_NAME[0] = (String)__sJT_st.getString(10);
   P_PARTY_ADDRESS[0] = (String)__sJT_st.getString(11);
   P_PHONE_NUMBER[0] = (String)__sJT_st.getString(12);
   P_TOTAL_PLAN_AMT[0] = __sJT_st.getBigDecimal(13);
   P_TOTAL_AMT_PAID[0] = __sJT_st.getBigDecimal(14);
   P_TOTAL_AMT_DUE[0] = __sJT_st.getBigDecimal(15);
   P_TOTAL_DELIQUENT[0] = __sJT_st.getBigDecimal(16);
   P_PAY_DETAILS[0] = (com.etcc.csc.plsql.OLC_VPS_PAY_PLAN_ARR)__sJT_st.getORAData(17,com.etcc.csc.plsql.OLC_VPS_PAY_PLAN_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(18,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:399^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String CHECK_PAYMENT_PLAN (
    String P_INVOICE_NO)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:414^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.CHECK_PAYMENT_PLAN(
//        :P_INVOICE_NO))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.CHECK_PAYMENT_PLAN(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:415^23*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:421^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_INV_VPS.CHECK_PAYMENT_PLAN(
//        :P_INVOICE_NO))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_INV_VPS.CHECK_PAYMENT_PLAN(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_INV_VPS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_INVOICE_NO);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:422^23*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/