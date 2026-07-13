/*@lineinfo:filename=OLCSC_MENU*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_MENU
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
  public OLCSC_MENU() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_MENU(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_MENU(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_MENU(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal GET_MENU (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_MENU_USER,
    String P_IP_ADDRESS,
    OLC_MENU_ITEM_ARR P_MENU_ITEMS[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[],
    String P_BASE_LANG_ID,
    String P_LANG_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:82^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_MENU(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0],
//        :P_BASE_LANG_ID,
//        :P_LANG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_MENU(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(9,P_BASE_LANG_ID);
   __sJT_st.setString(10,P_LANG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:91^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:97^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_MENU(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0],
//        :P_BASE_LANG_ID,
//        :P_LANG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_MENU(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(9,P_BASE_LANG_ID);
   __sJT_st.setString(10,P_LANG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:106^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_MENU (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_MENU_USER,
    String P_IP_ADDRESS,
    OLC_MENU_ITEM_ARR P_MENU_ITEMS[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:127^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_MENU(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_MENU(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"2com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:134^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:140^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_MENU(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_MENU(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"3com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:147^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public boolean PLAN_TAG_COUNT (
    java.math.BigDecimal I_ACCT_ID)
  throws java.sql.SQLException
  {
    boolean __jPt_result=false;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:162^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_MENU.PLAN_TAG_COUNT(
//        :I_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_MENU.PLAN_TAG_COUNT(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"4com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:163^21*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:169^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_MENU.PLAN_TAG_COUNT(
//        :I_ACCT_ID)))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := SYS.SQLJUTL.BOOL2INT(OL_OWNER.OLCSC_MENU.PLAN_TAG_COUNT(\n       :2  ))  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"5com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.BIT);
   }
   // set IN parameters
   __sJT_st.setBigDecimal(2,I_ACCT_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBoolean(1); if (__sJT_st.wasNull()) throw new sqlj.runtime.SQLNullException();
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:170^21*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_ITEM_MENU (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_MENU_USER,
    String P_IP_ADDRESS,
    java.math.BigDecimal P_ITEM_ID,
    String P_ITEM_TYPE,
    OLC_MENU_ITEM_ARR P_MENU_ITEMS[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[],
    String P_BASE_LANG_ID,
    String P_LANG_ID)
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:195^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_ITEM_MENU(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_ITEM_ID,
//        :P_ITEM_TYPE,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0],
//        :P_BASE_LANG_ID,
//        :P_LANG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_ITEM_MENU(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"6com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setBigDecimal(7,P_ITEM_ID);
   __sJT_st.setString(8,P_ITEM_TYPE);
   __sJT_st.setString(11,P_BASE_LANG_ID);
   __sJT_st.setString(12,P_LANG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:206^20*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:212^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_ITEM_MENU(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_ITEM_ID,
//        :P_ITEM_TYPE,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0],
//        :P_BASE_LANG_ID,
//        :P_LANG_ID))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_ITEM_MENU(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  ,\n       :10  ,\n       :11  ,\n       :12  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"7com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(10,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setBigDecimal(7,P_ITEM_ID);
   __sJT_st.setString(8,P_ITEM_TYPE);
   __sJT_st.setString(11,P_BASE_LANG_ID);
   __sJT_st.setString(12,P_LANG_ID);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(10,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:223^20*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }

  public java.math.BigDecimal GET_MENU_TYPE (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_MENU_USER,
    String P_IP_ADDRESS,
    String P_ITEM_TYPE,
    OLC_MENU_ITEM_ARR P_MENU_ITEMS[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:245^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_MENU_TYPE(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_ITEM_TYPE,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_MENU_TYPE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"8com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_ITEM_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:253^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:259^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_MENU.GET_MENU_TYPE(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_MENU_USER,
//        :P_IP_ADDRESS,
//        :P_ITEM_TYPE,
//        :P_MENU_ITEMS[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_MENU.GET_MENU_TYPE(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  ,\n       :9  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"9com.etcc.csc.plsql.OLCSC_MENU",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_MENU_ITEM_ARR");
      __sJT_st.registerOutParameter(9,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_MENU_USER);
   __sJT_st.setString(6,P_IP_ADDRESS);
   __sJT_st.setString(7,P_ITEM_TYPE);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_MENU_ITEMS[0] = (com.etcc.csc.plsql.OLC_MENU_ITEM_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_MENU_ITEM_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(9,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:267^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/