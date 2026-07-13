/*@lineinfo:filename=OLCSC_REP*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_REP
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
  public OLCSC_REP() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_REP(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_REP(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_REP(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public String GET_HCTRA_AGENCY_INFO ()
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:73^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_HCTRA_AGENCY_INFO())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_HCTRA_AGENCY_INFO()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:73^103*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:79^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_HCTRA_AGENCY_INFO())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_HCTRA_AGENCY_INFO()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:79^103*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_TXNS_MAIN (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_USER_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    java.sql.Timestamp P_BEGIN_DATE,
    java.sql.Timestamp P_END_DATE,
    String P_ACCT_VPN_TYPE,
    OLC_VIEWTXN_TXNTYPE_ARR O_OLC_VIEWTXN_TXNTYPE_ARR[],
    OLCSC_VEHICLE_NICKNAME_ARR O_VEHICLE_NICKNAME_ARR[],
    String O_PDF_LINK[],
    String O_XLS_LINK[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:106^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TXNS_MAIN(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_BEGIN_DATE,
//        :P_END_DATE,
//        :P_ACCT_VPN_TYPE,
//        :O_OLC_VIEWTXN_TXNTYPE_ARR[0],
//        :O_VEHICLE_NICKNAME_ARR[0],
//        :O_PDF_LINK[0],
//        :O_XLS_LINK[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TXNS_MAIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_VIEWTXN_TXNTYPE_ARR");
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLCSC_VEHICLE_NICKNAME_ARR");
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setTimestamp(7,P_BEGIN_DATE);
   __sJT_st.setTimestamp(8,P_END_DATE);
   __sJT_st.setString(9,P_ACCT_VPN_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_VIEWTXN_TXNTYPE_ARR[0] = (com.etcc.csc.plsql.OLC_VIEWTXN_TXNTYPE_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_VIEWTXN_TXNTYPE_ARR.getORADataFactory());
   O_VEHICLE_NICKNAME_ARR[0] = (com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR.getORADataFactory());
   O_PDF_LINK[0] = (String)__sJT_st.getString(12);
   O_XLS_LINK[0] = (String)__sJT_st.getString(13);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:119^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:125^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TXNS_MAIN(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_BEGIN_DATE,
//        :P_END_DATE,
//        :P_ACCT_VPN_TYPE,
//        :O_OLC_VIEWTXN_TXNTYPE_ARR[0],
//        :O_VEHICLE_NICKNAME_ARR[0],
//        :O_PDF_LINK[0],
//        :O_XLS_LINK[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TXNS_MAIN(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_VIEWTXN_TXNTYPE_ARR");
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLCSC_VEHICLE_NICKNAME_ARR");
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(13,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setTimestamp(7,P_BEGIN_DATE);
   __sJT_st.setTimestamp(8,P_END_DATE);
   __sJT_st.setString(9,P_ACCT_VPN_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_VIEWTXN_TXNTYPE_ARR[0] = (com.etcc.csc.plsql.OLC_VIEWTXN_TXNTYPE_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_VIEWTXN_TXNTYPE_ARR.getORADataFactory());
   O_VEHICLE_NICKNAME_ARR[0] = (com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR.getORADataFactory());
   O_PDF_LINK[0] = (String)__sJT_st.getString(12);
   O_XLS_LINK[0] = (String)__sJT_st.getString(13);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:138^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String ACCOUNT_INFO (
    String P_SESSION_ID,
    String P_USER,
    java.math.BigDecimal P_ACCT_ID,
    String P_IP_ADDRESS,
    String P_REPORT_FORMAT)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:157^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.ACCOUNT_INFO(
//        :P_SESSION_ID,
//        :P_USER,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_REPORT_FORMAT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.ACCOUNT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER);
   __sJT_st.setBigDecimal(4,P_ACCT_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_REPORT_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:162^26*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:168^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.ACCOUNT_INFO(
//        :P_SESSION_ID,
//        :P_USER,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_REPORT_FORMAT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.ACCOUNT_INFO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER);
   __sJT_st.setBigDecimal(4,P_ACCT_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_REPORT_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:173^26*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal IS_ACCOUNT_SUMMARIZED (
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:188^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.IS_ACCOUNT_SUMMARIZED(
//        :P_ACCT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.IS_ACCOUNT_SUMMARIZED(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:189^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:195^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.IS_ACCOUNT_SUMMARIZED(
//        :P_ACCT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.IS_ACCOUNT_SUMMARIZED(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:196^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_POS_LOCATION_BY_SHIFT (
    java.math.BigDecimal P_SHIFT_ID)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:211^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_POS_LOCATION_BY_SHIFT(
//        :P_SHIFT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_POS_LOCATION_BY_SHIFT(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_SHIFT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:212^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:218^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_POS_LOCATION_BY_SHIFT(
//        :P_SHIFT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_POS_LOCATION_BY_SHIFT(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_SHIFT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:219^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.sql.Timestamp GET_END_DATE ()
  throws java.sql.SQLException
  {
    java.sql.Timestamp __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:233^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_END_DATE())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_END_DATE()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.TIMESTAMP);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (java.sql.Timestamp)__sJT_st.getTimestamp(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:233^94*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:239^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_END_DATE())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_END_DATE()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.TIMESTAMP);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (java.sql.Timestamp)__sJT_st.getTimestamp(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:239^94*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_STATEMENTS_HTML (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_USER_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_MONTH,
    String P_REPORT_FORMAT,
    String P_ACCT_VPN_TYPE,
    String O_BAL_AMT[],
    String O_LAST_DAY[],
    OLC_VIEW_MONTHLY_ARR O_DETAIL_ARR[],
    OLC_TAG_SUMMARY_ARR O_TAG_SUMMARY_ARR[],
    OLC_ACCT_SUMMARY_ARR O_ACCT_SUMMARY_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_ALERT_DISPLAY_ARR O_ALERTS[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:268^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_STATEMENTS_HTML(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_MONTH,
//        :P_REPORT_FORMAT,
//        :P_ACCT_VPN_TYPE,
//        :O_BAL_AMT[0],
//        :O_LAST_DAY[0],
//        :O_DETAIL_ARR[0],
//        :O_TAG_SUMMARY_ARR[0],
//        :O_ACCT_SUMMARY_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :O_ALERTS[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_STATEMENTS_HTML(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_VIEW_MONTHLY_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_TAG_SUMMARY_ARR");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ACCT_SUMMARY_ARR");
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(16,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_MONTH);
   __sJT_st.setString(8,P_REPORT_FORMAT);
   __sJT_st.setString(9,P_ACCT_VPN_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_BAL_AMT[0] = (String)__sJT_st.getString(10);
   O_LAST_DAY[0] = (String)__sJT_st.getString(11);
   O_DETAIL_ARR[0] = (com.etcc.csc.plsql.OLC_VIEW_MONTHLY_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_VIEW_MONTHLY_ARR.getORADataFactory());
   O_TAG_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR.getORADataFactory());
   O_ACCT_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:283^28*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:289^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_STATEMENTS_HTML(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_MONTH,
//        :P_REPORT_FORMAT,
//        :P_ACCT_VPN_TYPE,
//        :O_BAL_AMT[0],
//        :O_LAST_DAY[0],
//        :O_DETAIL_ARR[0],
//        :O_TAG_SUMMARY_ARR[0],
//        :O_ACCT_SUMMARY_ARR[0],
//        :O_ERROR_MSG_ARR[0],
//        :O_ALERTS[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_STATEMENTS_HTML(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  ,\n       :16  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_VIEW_MONTHLY_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_TAG_SUMMARY_ARR");
      __sJT_st.registerOutParameter(14,2003,"OL_OWNER.OLC_ACCT_SUMMARY_ARR");
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(16,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_MONTH);
   __sJT_st.setString(8,P_REPORT_FORMAT);
   __sJT_st.setString(9,P_ACCT_VPN_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_BAL_AMT[0] = (String)__sJT_st.getString(10);
   O_LAST_DAY[0] = (String)__sJT_st.getString(11);
   O_DETAIL_ARR[0] = (com.etcc.csc.plsql.OLC_VIEW_MONTHLY_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_VIEW_MONTHLY_ARR.getORADataFactory());
   O_TAG_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR.getORADataFactory());
   O_ACCT_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR)__sJT_st.getORAData(14,com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_ALERTS[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(16,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:304^28*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_REPORT_NAME (
    java.math.BigDecimal P_ACCT_ID,
    String P_REPORT_NAME,
    String P_REPORT_FORMAT)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:321^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_REPORT_NAME(
//        :P_ACCT_ID,
//        :P_REPORT_NAME,
//        :P_REPORT_FORMAT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_REPORT_NAME(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_REPORT_NAME);
   __sJT_st.setString(4,P_REPORT_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:324^26*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:330^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_REPORT_NAME(
//        :P_ACCT_ID,
//        :P_REPORT_NAME,
//        :P_REPORT_FORMAT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_REPORT_NAME(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_REPORT_NAME);
   __sJT_st.setString(4,P_REPORT_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:333^26*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal TEST (
    String P_SESSION_ID,
    String P_USER,
    java.math.BigDecimal P_ACCT_ID,
    String P_IP_ADDRESS,
    String P_REP_NAME[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:352^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.TEST(
//        :P_SESSION_ID,
//        :P_USER,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_REP_NAME[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.TEST(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER);
   __sJT_st.setBigDecimal(4,P_ACCT_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_REP_NAME[0] = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:357^30*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:363^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.TEST(
//        :P_SESSION_ID,
//        :P_USER,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_REP_NAME[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.TEST(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(6,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setString(3,P_USER);
   __sJT_st.setBigDecimal(4,P_ACCT_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_REP_NAME[0] = (String)__sJT_st.getString(6);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:368^30*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal IS_BIG_ACCOUNT (
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:383^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.IS_BIG_ACCOUNT(
//        :P_ACCT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.IS_BIG_ACCOUNT(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"18com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:384^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:390^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.IS_BIG_ACCOUNT(
//        :P_ACCT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.IS_BIG_ACCOUNT(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"19com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:391^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VEHICLE_NICKNAME_DPDN (
    java.math.BigDecimal P_ACCT_ID,
    OLCSC_VEHICLE_NICKNAME_ARR O_VEHICLE_NICKNAME_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:408^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VEHICLE_NICKNAME_DPDN(
//        :P_ACCT_ID,
//        :O_VEHICLE_NICKNAME_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VEHICLE_NICKNAME_DPDN(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"20com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLCSC_VEHICLE_NICKNAME_ARR");
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VEHICLE_NICKNAME_ARR[0] = (com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:411^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:417^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VEHICLE_NICKNAME_DPDN(
//        :P_ACCT_ID,
//        :O_VEHICLE_NICKNAME_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VEHICLE_NICKNAME_DPDN(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"21com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLCSC_VEHICLE_NICKNAME_ARR");
      __sJT_st.registerOutParameter(4,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_VEHICLE_NICKNAME_ARR[0] = (com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLCSC_VEHICLE_NICKNAME_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(4,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:420^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_TXNS (
    String P_DOC_ID,
    String P_DATE_TYPE,
    java.sql.Timestamp P_BEGIN_DATE,
    java.sql.Timestamp P_END_DATE,
    String P_FILTER,
    String P_NICKNAME,
    String P_NICKNAME_TYPE,
    OLC_ACCOUNT_HISTORY_ARR O_OLC_ACC_HISTORY_ARR[],
    java.math.BigDecimal O_TOTAL[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:444^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TXNS(
//        :P_DOC_ID,
//        :P_DATE_TYPE,
//        :P_BEGIN_DATE,
//        :P_END_DATE,
//        :P_FILTER,
//        :P_NICKNAME,
//        :P_NICKNAME_TYPE,
//        :O_OLC_ACC_HISTORY_ARR[0],
//        :O_TOTAL[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TXNS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"22com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DATE_TYPE);
   __sJT_st.setTimestamp(4,P_BEGIN_DATE);
   __sJT_st.setTimestamp(5,P_END_DATE);
   __sJT_st.setString(6,P_FILTER);
   __sJT_st.setString(7,P_NICKNAME);
   __sJT_st.setString(8,P_NICKNAME_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_ACC_HISTORY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR.getORADataFactory());
   O_TOTAL[0] = __sJT_st.getBigDecimal(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:454^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:460^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TXNS(
//        :P_DOC_ID,
//        :P_DATE_TYPE,
//        :P_BEGIN_DATE,
//        :P_END_DATE,
//        :P_FILTER,
//        :P_NICKNAME,
//        :P_NICKNAME_TYPE,
//        :O_OLC_ACC_HISTORY_ARR[0],
//        :O_TOTAL[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TXNS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"23com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DATE_TYPE);
   __sJT_st.setTimestamp(4,P_BEGIN_DATE);
   __sJT_st.setTimestamp(5,P_END_DATE);
   __sJT_st.setString(6,P_FILTER);
   __sJT_st.setString(7,P_NICKNAME);
   __sJT_st.setString(8,P_NICKNAME_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_ACC_HISTORY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR.getORADataFactory());
   O_TOTAL[0] = __sJT_st.getBigDecimal(10);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:470^35*/
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
    String P_DATE_TYPE,
    java.sql.Timestamp P_BEGIN_DATE,
    java.sql.Timestamp P_END_DATE,
    String P_TRANS_FILTER,
    String P_NICKNAME,
    String P_NICKNAME_TYPE,
    OLC_ACCOUNT_HISTORY_ARR O_OLC_ACC_HISTORY_ARR[],
    java.math.BigDecimal O_TOTAL[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:498^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TXNS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_DATE_TYPE,
//        :P_BEGIN_DATE,
//        :P_END_DATE,
//        :P_TRANS_FILTER,
//        :P_NICKNAME,
//        :P_NICKNAME_TYPE,
//        :O_OLC_ACC_HISTORY_ARR[0],
//        :O_TOTAL[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TXNS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"24com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_DATE_TYPE);
   __sJT_st.setTimestamp(8,P_BEGIN_DATE);
   __sJT_st.setTimestamp(9,P_END_DATE);
   __sJT_st.setString(10,P_TRANS_FILTER);
   __sJT_st.setString(11,P_NICKNAME);
   __sJT_st.setString(12,P_NICKNAME_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_ACC_HISTORY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR.getORADataFactory());
   O_TOTAL[0] = __sJT_st.getBigDecimal(14);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:512^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:518^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TXNS(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_DATE_TYPE,
//        :P_BEGIN_DATE,
//        :P_END_DATE,
//        :P_TRANS_FILTER,
//        :P_NICKNAME,
//        :P_NICKNAME_TYPE,
//        :O_OLC_ACC_HISTORY_ARR[0],
//        :O_TOTAL[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TXNS(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  ,\n       :14  ,\n       :15  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"25com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ACCOUNT_HISTORY_ARR");
      __sJT_st.registerOutParameter(14,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(15,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_DATE_TYPE);
   __sJT_st.setTimestamp(8,P_BEGIN_DATE);
   __sJT_st.setTimestamp(9,P_END_DATE);
   __sJT_st.setString(10,P_TRANS_FILTER);
   __sJT_st.setString(11,P_NICKNAME);
   __sJT_st.setString(12,P_NICKNAME_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_ACC_HISTORY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ACCOUNT_HISTORY_ARR.getORADataFactory());
   O_TOTAL[0] = __sJT_st.getBigDecimal(14);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(15,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:532^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void SETACCOUNTHISTORYVIEWPARMS (
    java.math.BigDecimal P_DOC_ID,
    java.sql.Timestamp P_BEGIN_DATE,
    java.sql.Timestamp P_END_DATE)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:548^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_REP.SETACCOUNTHISTORYVIEWPARMS(
//        :P_DOC_ID,
//        :P_BEGIN_DATE,
//        :P_END_DATE)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_REP.SETACCOUNTHISTORYVIEWPARMS(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"26com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_DOC_ID);
   __sJT_st.setTimestamp(2,P_BEGIN_DATE);
   __sJT_st.setTimestamp(3,P_END_DATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:551^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:557^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_REP.SETACCOUNTHISTORYVIEWPARMS(
//        :P_DOC_ID,
//        :P_BEGIN_DATE,
//        :P_END_DATE)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_REP.SETACCOUNTHISTORYVIEWPARMS(\n       :1  ,\n       :2  ,\n       :3  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"27com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   // set IN parameters
   __sJT_st.setBigDecimal(1,P_DOC_ID);
   __sJT_st.setTimestamp(2,P_BEGIN_DATE);
   __sJT_st.setTimestamp(3,P_END_DATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:560^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal VIEW_TRANSACTIONS_HTML (
    String P_SESSION_ID,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    java.sql.Timestamp P_START,
    java.sql.Timestamp P_END,
    java.math.BigDecimal P_AGENCY_ID,
    String P_TAG_ID,
    String P_LICENSE_PLATE,
    String P_TXN_TYPE,
    OLC_TRANS_ARR P_TXN_INFO_ARR[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:585^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TRANSACTIONS_HTML(
//        :P_SESSION_ID,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_START,
//        :P_END,
//        :P_AGENCY_ID,
//        :P_TAG_ID,
//        :P_LICENSE_PLATE,
//        :P_TXN_TYPE,
//        :P_TXN_INFO_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TRANSACTIONS_HTML(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"28com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_TRANS_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setTimestamp(6,P_START);
   __sJT_st.setTimestamp(7,P_END);
   __sJT_st.setBigDecimal(8,P_AGENCY_ID);
   __sJT_st.setString(9,P_TAG_ID);
   __sJT_st.setString(10,P_LICENSE_PLATE);
   __sJT_st.setString(11,P_TXN_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TXN_INFO_ARR[0] = (com.etcc.csc.plsql.OLC_TRANS_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_TRANS_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:597^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:603^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_TRANSACTIONS_HTML(
//        :P_SESSION_ID,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_START,
//        :P_END,
//        :P_AGENCY_ID,
//        :P_TAG_ID,
//        :P_LICENSE_PLATE,
//        :P_TXN_TYPE,
//        :P_TXN_INFO_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_TRANSACTIONS_HTML(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"29com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_TRANS_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION_ID);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setTimestamp(6,P_START);
   __sJT_st.setTimestamp(7,P_END);
   __sJT_st.setBigDecimal(8,P_AGENCY_ID);
   __sJT_st.setString(9,P_TAG_ID);
   __sJT_st.setString(10,P_LICENSE_PLATE);
   __sJT_st.setString(11,P_TXN_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TXN_INFO_ARR[0] = (com.etcc.csc.plsql.OLC_TRANS_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_TRANS_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:615^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_ACCT_SUM_GRAPH (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_ALERT_DISPLAY_ARR O_OLC_ALERT_DISPLAY_ARR[],
    OLC_ACCT_SUM_ARR O_ACCT_SUM_ARR[],
    java.sql.Timestamp O_LAST_LOGIN_DATE[],
    java.sql.Timestamp O_REBILL_DATE[],
    String O_CC_EFT[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:640^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_ACCT_SUM_GRAPH(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_ALERT_DISPLAY_ARR[0],
//        :O_ACCT_SUM_ARR[0],
//        :O_LAST_LOGIN_DATE[0],
//        :O_REBILL_DATE[0],
//        :O_CC_EFT[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_ACCT_SUM_GRAPH(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"30com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ACCT_SUM_ARR");
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_ALERT_DISPLAY_ARR[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ACCT_SUM_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_SUM_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ACCT_SUM_ARR.getORADataFactory());
   O_LAST_LOGIN_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   O_REBILL_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(11);
   O_CC_EFT[0] = (String)__sJT_st.getString(12);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:651^28*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:657^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_ACCT_SUM_GRAPH(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_ALERT_DISPLAY_ARR[0],
//        :O_ACCT_SUM_ARR[0],
//        :O_LAST_LOGIN_DATE[0],
//        :O_REBILL_DATE[0],
//        :O_CC_EFT[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_ACCT_SUM_GRAPH(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"31com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ALERT_DISPLAY_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ACCT_SUM_ARR");
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(11,oracle.jdbc.OracleTypes.TIMESTAMP);
      __sJT_st.registerOutParameter(12,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_ALERT_DISPLAY_ARR[0] = (com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ALERT_DISPLAY_ARR.getORADataFactory());
   O_ACCT_SUM_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_SUM_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ACCT_SUM_ARR.getORADataFactory());
   O_LAST_LOGIN_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(10);
   O_REBILL_DATE[0] = (java.sql.Timestamp)__sJT_st.getTimestamp(11);
   O_CC_EFT[0] = (String)__sJT_st.getString(12);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:668^28*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_DOC_ID ()
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:682^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_DOC_ID())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_DOC_ID()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"32com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:682^92*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:688^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_DOC_ID())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_DOC_ID()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"33com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:688^92*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal VIEW_SUMMARY_HTML (
    String P_DOC_ID,
    String P_DOC_TYPE,
    String P_USER_ID,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_YEAR,
    String P_REPORT_FORMAT,
    String O_BAL_AMT[],
    String O_LAST_DAY[],
    OLC_TAG_SUMMARY_ARR O_TAG_SUMMARY_ARR[],
    OLC_ACCT_SUMMARY_ARR O_ACCT_SUMMARY_ARR[],
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:714^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_SUMMARY_HTML(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_YEAR,
//        :P_REPORT_FORMAT,
//        :O_BAL_AMT[0],
//        :O_LAST_DAY[0],
//        :O_TAG_SUMMARY_ARR[0],
//        :O_ACCT_SUMMARY_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_SUMMARY_HTML(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"34com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_TAG_SUMMARY_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ACCT_SUMMARY_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_YEAR);
   __sJT_st.setString(8,P_REPORT_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_BAL_AMT[0] = (String)__sJT_st.getString(9);
   O_LAST_DAY[0] = (String)__sJT_st.getString(10);
   O_TAG_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR.getORADataFactory());
   O_ACCT_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:726^35*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:732^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.VIEW_SUMMARY_HTML(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_USER_ID,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_YEAR,
//        :P_REPORT_FORMAT,
//        :O_BAL_AMT[0],
//        :O_LAST_DAY[0],
//        :O_TAG_SUMMARY_ARR[0],
//        :O_ACCT_SUMMARY_ARR[0],
//        :O_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.VIEW_SUMMARY_HTML(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  ,\n       :13  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"35com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(11,2003,"OL_OWNER.OLC_TAG_SUMMARY_ARR");
      __sJT_st.registerOutParameter(12,2003,"OL_OWNER.OLC_ACCT_SUMMARY_ARR");
      __sJT_st.registerOutParameter(13,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_USER_ID);
   __sJT_st.setString(5,P_SESSION_ID);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_YEAR);
   __sJT_st.setString(8,P_REPORT_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_BAL_AMT[0] = (String)__sJT_st.getString(9);
   O_LAST_DAY[0] = (String)__sJT_st.getString(10);
   O_TAG_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR)__sJT_st.getORAData(11,com.etcc.csc.plsql.OLC_TAG_SUMMARY_ARR.getORADataFactory());
   O_ACCT_SUMMARY_ARR[0] = (com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR)__sJT_st.getORAData(12,com.etcc.csc.plsql.OLC_ACCT_SUMMARY_ARR.getORADataFactory());
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(13,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:744^35*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.sql.Timestamp GET_BEGIN_DATE ()
  throws java.sql.SQLException
  {
    java.sql.Timestamp __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:758^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_BEGIN_DATE())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_BEGIN_DATE()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"36com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.TIMESTAMP);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (java.sql.Timestamp)__sJT_st.getTimestamp(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:758^96*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:764^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_BEGIN_DATE())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_BEGIN_DATE()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"37com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.TIMESTAMP);
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (java.sql.Timestamp)__sJT_st.getTimestamp(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:764^96*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_RANDOM_ID ()
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:778^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_RANDOM_ID())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_RANDOM_ID()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"38com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:778^95*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:784^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.GET_RANDOM_ID())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.GET_RANDOM_ID()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"39com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
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

/*@lineinfo:user-code*//*@lineinfo:784^95*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal CHECK_SUSPENSION_AND_VIOLATION (
    java.math.BigDecimal P_DOC_ID,
    String P_DOC_TYPE,
    String P_SESSION_ID,
    String P_IP_ADDRESS,
    String P_USER_ID,
    OLC_ERROR_MSG_ARR O_ERROR_MSG_ARR[],
    OLC_LIC_PLATE_ARR O_OLC_LIC_PLATE_ARR[],
    String O_OLC_SUSPENSION_FLAG[],
    String O_OLC_VIOLATION_FLAG[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:807^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.CHECK_SUSPENSION_AND_VIOLATION(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_LIC_PLATE_ARR[0],
//        :O_OLC_SUSPENSION_FLAG[0],
//        :O_OLC_VIOLATION_FLAG[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.CHECK_SUSPENSION_AND_VIOLATION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"40com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_LIC_PLATE_ARR");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_LIC_PLATE_ARR[0] = (com.etcc.csc.plsql.OLC_LIC_PLATE_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_LIC_PLATE_ARR.getORADataFactory());
   O_OLC_SUSPENSION_FLAG[0] = (String)__sJT_st.getString(9);
   O_OLC_VIOLATION_FLAG[0] = (String)__sJT_st.getString(10);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:816^40*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:822^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_REP.CHECK_SUSPENSION_AND_VIOLATION(
//        :P_DOC_ID,
//        :P_DOC_TYPE,
//        :P_SESSION_ID,
//        :P_IP_ADDRESS,
//        :P_USER_ID,
//        :O_ERROR_MSG_ARR[0],
//        :O_OLC_LIC_PLATE_ARR[0],
//        :O_OLC_SUSPENSION_FLAG[0],
//        :O_OLC_VIOLATION_FLAG[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_REP.CHECK_SUSPENSION_AND_VIOLATION(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"41com.etcc.csc.plsql.OLCSC_REP",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_LIC_PLATE_ARR");
      __sJT_st.registerOutParameter(9,oracle.jdbc.OracleTypes.VARCHAR);
      __sJT_st.registerOutParameter(10,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_DOC_ID);
   __sJT_st.setString(3,P_DOC_TYPE);
   __sJT_st.setString(4,P_SESSION_ID);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_USER_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
   O_OLC_LIC_PLATE_ARR[0] = (com.etcc.csc.plsql.OLC_LIC_PLATE_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_LIC_PLATE_ARR.getORADataFactory());
   O_OLC_SUSPENSION_FLAG[0] = (String)__sJT_st.getString(9);
   O_OLC_VIOLATION_FLAG[0] = (String)__sJT_st.getString(10);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:831^40*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/