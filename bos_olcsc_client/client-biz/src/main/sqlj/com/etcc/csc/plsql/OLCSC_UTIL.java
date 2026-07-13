/*@lineinfo:filename=OLCSC_UTIL*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_UTIL
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
  public OLCSC_UTIL() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_UTIL(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_UTIL(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_UTIL(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public void SET_TRANS_VALUE (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_IP_ADDRESS,
    String P_LANG_ID)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:76^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_UTIL.SET_TRANS_VALUE(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_LANG_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_UTIL.SET_TRANS_VALUE(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_SESSION);
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LANG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:80^19*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:86^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_UTIL.SET_TRANS_VALUE(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_IP_ADDRESS,
//        :P_LANG_ID)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_UTIL.SET_TRANS_VALUE(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_SESSION);
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
   __sJT_st.setString(3,P_IP_ADDRESS);
   __sJT_st.setString(4,P_LANG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:90^19*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public java.math.BigDecimal GET_STATES (
    OLC_STATE_CODE_ARR P_STATES[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:105^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_STATES(
//        :P_STATES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_STATES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_STATE_CODE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_STATES[0] = (com.etcc.csc.plsql.OLC_STATE_CODE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_STATE_CODE_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:107^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:113^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_STATES(
//        :P_STATES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_STATES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_STATE_CODE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_STATES[0] = (com.etcc.csc.plsql.OLC_STATE_CODE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_STATE_CODE_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:115^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_COUNTRY_DPDN (
    OLC_COUNTRY_CODE_ARR O_OLC_COUNTRY_ARR[],
    OLC_ERROR_MSG_ARR O_OLC_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:131^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_COUNTRY_DPDN(
//        :O_OLC_COUNTRY_ARR[0],
//        :O_OLC_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_COUNTRY_DPDN(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_COUNTRY_CODE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_COUNTRY_ARR[0] = (com.etcc.csc.plsql.OLC_COUNTRY_CODE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_COUNTRY_CODE_ARR.getORADataFactory());
   O_OLC_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:133^39*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:139^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_COUNTRY_DPDN(
//        :O_OLC_COUNTRY_ARR[0],
//        :O_OLC_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_COUNTRY_DPDN(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_COUNTRY_CODE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_COUNTRY_ARR[0] = (com.etcc.csc.plsql.OLC_COUNTRY_CODE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_COUNTRY_CODE_ARR.getORADataFactory());
   O_OLC_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:141^39*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_TAG_AUTHORITIES (
    OLC_TAG_AUTHORITIES_ARR P_TAG_AUTHORITIES[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:157^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_TAG_AUTHORITIES(
//        :P_TAG_AUTHORITIES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_TAG_AUTHORITIES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_TAG_AUTHORITIES_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TAG_AUTHORITIES[0] = (com.etcc.csc.plsql.OLC_TAG_AUTHORITIES_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_TAG_AUTHORITIES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:159^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:165^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_TAG_AUTHORITIES(
//        :P_TAG_AUTHORITIES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_TAG_AUTHORITIES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_TAG_AUTHORITIES_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_TAG_AUTHORITIES[0] = (com.etcc.csc.plsql.OLC_TAG_AUTHORITIES_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_TAG_AUTHORITIES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:167^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean ZIP_CODE_VALIDATION (
    String P_PMT_TYPE_CODE,
    String P_ACCT_TYPE_CODE,
    String P_ZIP_CODE,
    String O_DISPLAY_MSG[])
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:185^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_UTIL$ZIP_CODE_VALIDATIO(
//        :P_PMT_TYPE_CODE,
//        :P_ACCT_TYPE_CODE,
//        :P_ZIP_CODE,
//        :O_DISPLAY_MSG[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_UTIL$ZIP_CODE_VALIDATIO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PMT_TYPE_CODE);
   __sJT_st.setString(3,P_ACCT_TYPE_CODE);
   __sJT_st.setString(4,P_ZIP_CODE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_DISPLAY_MSG[0] = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:189^33*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:195^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.OLCSC_UTIL$ZIP_CODE_VALIDATIO(
//        :P_PMT_TYPE_CODE,
//        :P_ACCT_TYPE_CODE,
//        :P_ZIP_CODE,
//        :O_DISPLAY_MSG[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.OLCSC_UTIL$ZIP_CODE_VALIDATIO(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
      __sJT_st.registerOutParameter(5,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PMT_TYPE_CODE);
   __sJT_st.setString(3,P_ACCT_TYPE_CODE);
   __sJT_st.setString(4,P_ZIP_CODE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
   O_DISPLAY_MSG[0] = (String)__sJT_st.getString(5);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:199^33*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_VEHICLE_CLASSES (
    OLC_VEHICLE_CLASSES_ARR P_VEHICLE_CLASSES[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:215^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_VEHICLE_CLASSES(
//        :P_VEHICLE_CLASSES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_VEHICLE_CLASSES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"10com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VEHICLE_CLASSES_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_VEHICLE_CLASSES[0] = (com.etcc.csc.plsql.OLC_VEHICLE_CLASSES_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VEHICLE_CLASSES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:217^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:223^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_VEHICLE_CLASSES(
//        :P_VEHICLE_CLASSES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_VEHICLE_CLASSES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"11com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VEHICLE_CLASSES_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_VEHICLE_CLASSES[0] = (com.etcc.csc.plsql.OLC_VEHICLE_CLASSES_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VEHICLE_CLASSES_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:225^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_API_ID (
    String P_API_NAME)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:240^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_API_ID(
//        :P_API_NAME))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_API_ID(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"12com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_API_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:241^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:247^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_API_ID(
//        :P_API_NAME))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_API_ID(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"13com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
   }
   // set IN parameters
   __sJT_st.setString(2,P_API_NAME);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:248^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void SEND_EMAIL (
    String P_FROM,
    String P_TO,
    String P_SUBJECT,
    String P_BODY,
    String P_FORMAT)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:266^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_UTIL.SEND_EMAIL(
//        :P_FROM,
//        :P_TO,
//        :P_SUBJECT,
//        :P_BODY,
//        :P_FORMAT)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_UTIL.SEND_EMAIL(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"14com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_FROM);
   __sJT_st.setString(2,P_TO);
   __sJT_st.setString(3,P_SUBJECT);
   __sJT_st.setString(4,P_BODY);
   __sJT_st.setString(5,P_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:271^18*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:277^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_UTIL.SEND_EMAIL(
//        :P_FROM,
//        :P_TO,
//        :P_SUBJECT,
//        :P_BODY,
//        :P_FORMAT)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_UTIL.SEND_EMAIL(\n       :1  ,\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"15com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   // set IN parameters
   __sJT_st.setString(1,P_FROM);
   __sJT_st.setString(2,P_TO);
   __sJT_st.setString(3,P_SUBJECT);
   __sJT_st.setString(4,P_BODY);
   __sJT_st.setString(5,P_FORMAT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:282^18*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }

  public OLC_S_QUESTIONS_ARR GET_SECURITY_QUESTIONS (
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    OLC_S_QUESTIONS_ARR __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:296^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_SECURITY_QUESTIONS(
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_SECURITY_QUESTIONS(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"16com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLC_S_QUESTIONS_ARR");
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_S_QUESTIONS_ARR)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_S_QUESTIONS_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:297^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:303^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_SECURITY_QUESTIONS(
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_SECURITY_QUESTIONS(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"17com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLC_S_QUESTIONS_ARR");
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_S_QUESTIONS_ARR)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_S_QUESTIONS_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:304^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_VEHICLE_MAKE_DPDN (
    OLC_VEH_MAKE_TYPE_ARR O_OLC_VEHICLE_MAKE_ARR[],
    OLC_ERROR_MSG_ARR O_OLC_ERROR_MSG_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:320^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_VEHICLE_MAKE_DPDN(
//        :O_OLC_VEHICLE_MAKE_ARR[0],
//        :O_OLC_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_VEHICLE_MAKE_DPDN(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"18com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VEH_MAKE_TYPE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_VEHICLE_MAKE_ARR[0] = (com.etcc.csc.plsql.OLC_VEH_MAKE_TYPE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VEH_MAKE_TYPE_ARR.getORADataFactory());
   O_OLC_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:322^39*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:328^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_VEHICLE_MAKE_DPDN(
//        :O_OLC_VEHICLE_MAKE_ARR[0],
//        :O_OLC_ERROR_MSG_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_VEHICLE_MAKE_DPDN(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"19com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_VEH_MAKE_TYPE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   O_OLC_VEHICLE_MAKE_ARR[0] = (com.etcc.csc.plsql.OLC_VEH_MAKE_TYPE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_VEH_MAKE_TYPE_ARR.getORADataFactory());
   O_OLC_ERROR_MSG_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:330^39*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_CR_CARD_TYPES (
    OLC_CR_CARD_ARR P_CC_TYPES[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:346^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_CR_CARD_TYPES(
//        :P_CC_TYPES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_CR_CARD_TYPES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"20com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_CR_CARD_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_CC_TYPES[0] = (com.etcc.csc.plsql.OLC_CR_CARD_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_CR_CARD_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:348^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:354^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_CR_CARD_TYPES(
//        :P_CC_TYPES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_CR_CARD_TYPES(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"21com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_CR_CARD_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_CC_TYPES[0] = (com.etcc.csc.plsql.OLC_CR_CARD_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_CR_CARD_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:356^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String STRIP_DASHES (
    String P_PHONE_NBR)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:371^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.STRIP_DASHES(
//        :P_PHONE_NBR))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.STRIP_DASHES(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"22com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PHONE_NBR);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:372^22*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:378^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.STRIP_DASHES(
//        :P_PHONE_NBR))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.STRIP_DASHES(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"23com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,P_PHONE_NBR);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:379^22*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GET_TRANS_VALUE (
    String A_BASE_LANG_ID,
    String A_TARGET_LANG,
    String A_TEXT)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:396^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_TRANS_VALUE(
//        :A_BASE_LANG_ID,
//        :A_TARGET_LANG,
//        :A_TEXT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_TRANS_VALUE(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"24com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,A_BASE_LANG_ID);
   __sJT_st.setString(3,A_TARGET_LANG);
   __sJT_st.setString(4,A_TEXT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:399^17*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:405^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_TRANS_VALUE(
//        :A_BASE_LANG_ID,
//        :A_TARGET_LANG,
//        :A_TEXT))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_TRANS_VALUE(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"25com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,A_BASE_LANG_ID);
   __sJT_st.setString(3,A_TARGET_LANG);
   __sJT_st.setString(4,A_TEXT);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:408^17*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public oracle.sql.CLOB REPLACE_TEXT (
    oracle.sql.CLOB P_TEMPLATE,
    oracle.sql.CLOB P_EMAIL_BODY)
  throws java.sql.SQLException
  {
    oracle.sql.CLOB __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:424^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.REPLACE_TEXT(
//        :P_TEMPLATE,
//        :P_EMAIL_BODY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.REPLACE_TEXT(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"26com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.CLOB);
   }
   // set IN parameters
   __sJT_st.setCLOB(2,P_TEMPLATE);
   __sJT_st.setCLOB(3,P_EMAIL_BODY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.CLOB)__sJT_st.getCLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:426^23*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:432^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.REPLACE_TEXT(
//        :P_TEMPLATE,
//        :P_EMAIL_BODY))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.REPLACE_TEXT(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"27com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.CLOB);
   }
   // set IN parameters
   __sJT_st.setCLOB(2,P_TEMPLATE);
   __sJT_st.setCLOB(3,P_EMAIL_BODY);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.CLOB)__sJT_st.getCLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:434^23*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public oracle.sql.CLOB GET_OLC_TEXT (
    String P_TEXT_ID)
  throws java.sql.SQLException
  {
    oracle.sql.CLOB __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:449^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_OLC_TEXT(
//        :P_TEXT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_OLC_TEXT(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"28com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.CLOB);
   }
   // set IN parameters
   __sJT_st.setString(2,P_TEXT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.CLOB)__sJT_st.getCLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:450^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:456^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_OLC_TEXT(
//        :P_TEXT_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_OLC_TEXT(\n       :2  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"29com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.CLOB);
   }
   // set IN parameters
   __sJT_st.setString(2,P_TEXT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (oracle.sql.CLOB)__sJT_st.getCLOB(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:457^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_MARK_SOURCE (
    OLC_MARKET_SOURCE_ARR P_MARK_SOURCES[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:473^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_MARK_SOURCE(
//        :P_MARK_SOURCES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_MARK_SOURCE(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"30com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_MARKET_SOURCE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MARK_SOURCES[0] = (com.etcc.csc.plsql.OLC_MARKET_SOURCE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_MARKET_SOURCE_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:475^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:481^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GET_MARK_SOURCE(
//        :P_MARK_SOURCES[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GET_MARK_SOURCE(\n       :2  ,\n       :3  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"31com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(2,2003,"OL_OWNER.OLC_MARKET_SOURCE_ARR");
      __sJT_st.registerOutParameter(3,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MARK_SOURCES[0] = (com.etcc.csc.plsql.OLC_MARKET_SOURCE_ARR)__sJT_st.getORAData(2,com.etcc.csc.plsql.OLC_MARKET_SOURCE_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(3,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:483^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public String GETSYSPARM (
    String A_NAME,
    String A_DEFAULT,
    java.sql.Timestamp A_DATE)
  throws java.sql.SQLException
  {
    String __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:500^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GETSYSPARM(
//        :A_NAME,
//        :A_DEFAULT,
//        :A_DATE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GETSYSPARM(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"32com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,A_NAME);
   __sJT_st.setString(3,A_DEFAULT);
   __sJT_st.setTimestamp(4,A_DATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:503^17*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:509^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_UTIL.GETSYSPARM(
//        :A_NAME,
//        :A_DEFAULT,
//        :A_DATE))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_UTIL.GETSYSPARM(\n       :2  ,\n       :3  ,\n       :4  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"33com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.VARCHAR);
   }
   // set IN parameters
   __sJT_st.setString(2,A_NAME);
   __sJT_st.setString(3,A_DEFAULT);
   __sJT_st.setTimestamp(4,A_DATE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (String)__sJT_st.getString(1);
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:512^17*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public OLCSC_UTIL_ACCOUNT_INFO_TBL POPULATE_ACCT_INFO_TBL (
    java.math.BigDecimal P_ACCT_ID)
  throws java.sql.SQLException
  {
    OLCSC_UTIL_ACCOUNT_INFO_TBL __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:527^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL0(OL_OWNER.OLCSC_UTIL.POPULATE_ACCT_INFO_TBL(
//        :P_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL0(OL_OWNER.OLCSC_UTIL.POPULATE_ACCT_INFO_TBL(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"34com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_UTIL_ACCOUNT_INFO_TBL");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_UTIL_ACCOUNT_INFO_TBL)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_UTIL_ACCOUNT_INFO_TBL.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:528^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:534^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(JPUB_PLSQL_WRAPPER.PL_TO_SQL0(OL_OWNER.OLCSC_UTIL.POPULATE_ACCT_INFO_TBL(
//        :P_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := JPUB_PLSQL_WRAPPER.PL_TO_SQL0(OL_OWNER.OLCSC_UTIL.POPULATE_ACCT_INFO_TBL(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"35com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2003,"OL_OWNER.OLCSC_UTIL_ACCOUNT_INFO_TBL");
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,P_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLCSC_UTIL_ACCOUNT_INFO_TBL)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLCSC_UTIL_ACCOUNT_INFO_TBL.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:535^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public void SHOW_ERRORS (
    OLC_ERROR_MSG_ARR P_ERROR_ARR)
  throws java.sql.SQLException
  {
 try {
    /*@lineinfo:generated-code*//*@lineinfo:549^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_UTIL.SHOW_ERRORS(
//        :P_ERROR_ARR)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_UTIL.SHOW_ERRORS(\n       :1  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"36com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   // set IN parameters
   if (P_ERROR_ARR==null) __sJT_st.setNull(1,2003,"OL_OWNER.OLC_ERROR_MSG_ARR"); else __sJT_st.setORAData(1,P_ERROR_ARR);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:550^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:556^5*/

//  ************************************************************
//  #sql [getConnectionContext()] { CALL OL_OWNER.OLCSC_UTIL.SHOW_ERRORS(
//        :P_ERROR_ARR)  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OraclePreparedStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN OL_OWNER.OLCSC_UTIL.SHOW_ERRORS(\n       :1  ) \n; END;";
   __sJT_st = __sJT_ec.prepareOracleStatement(__sJT_cc,"37com.etcc.csc.plsql.OLCSC_UTIL",theSqlTS);
   // set IN parameters
   if (P_ERROR_ARR==null) __sJT_st.setNull(1,2003,"OL_OWNER.OLC_ERROR_MSG_ARR"); else __sJT_st.setORAData(1,P_ERROR_ARR);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:557^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
  }
}/*@lineinfo:generated-code*/