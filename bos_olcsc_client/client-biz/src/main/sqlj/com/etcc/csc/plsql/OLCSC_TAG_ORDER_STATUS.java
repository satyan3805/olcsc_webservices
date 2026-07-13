/*@lineinfo:filename=OLCSC_TAG_ORDER_STATUS*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;
import java.sql.Connection;

public class OLCSC_TAG_ORDER_STATUS
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
  public OLCSC_TAG_ORDER_STATUS() throws SQLException
  { __tx = DefaultContext.getDefaultContext();
 }
  public OLCSC_TAG_ORDER_STATUS(DefaultContext c) throws SQLException
  { __tx = c; }
  public OLCSC_TAG_ORDER_STATUS(Connection c) throws SQLException
  {__onn = c; __tx = new DefaultContext(c);  }
  public OLCSC_TAG_ORDER_STATUS(javax.sql.DataSource ds) throws SQLException { __dataSource = ds; }

  public java.math.BigDecimal GET_FULFILLMENT_STAT (
    String P_SESSION,
    java.math.BigDecimal P_ACCT_ID,
    String P_USER,
    String P_IP_ADDRESS,
    String P_PENDING_FLAG,
    OLC_FULFILLMENT_STAT_ARR P_FULFILLMENT_STAT_ARR[],
    OLC_ERROR_MSG_ARR P_ERROR_ARR[])
  throws java.sql.SQLException
  {
    java.math.BigDecimal __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:80^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_TAG_ORDER_STATUS.GET_FULFILLMENT_STAT(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_PENDING_FLAG,
//        :P_FULFILLMENT_STAT_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_TAG_ORDER_STATUS.GET_FULFILLMENT_STAT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLCSC_TAG_ORDER_STATUS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_FULFILLMENT_STAT_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_PENDING_FLAG);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FULFILLMENT_STAT_ARR[0] = (com.etcc.csc.plsql.OLC_FULFILLMENT_STAT_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_FULFILLMENT_STAT_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:87^31*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:93^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLCSC_TAG_ORDER_STATUS.GET_FULFILLMENT_STAT(
//        :P_SESSION,
//        :P_ACCT_ID,
//        :P_USER,
//        :P_IP_ADDRESS,
//        :P_PENDING_FLAG,
//        :P_FULFILLMENT_STAT_ARR[0],
//        :P_ERROR_ARR[0]))  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLCSC_TAG_ORDER_STATUS.GET_FULFILLMENT_STAT(\n       :2  ,\n       :3  ,\n       :4  ,\n       :5  ,\n       :6  ,\n       :7  ,\n       :8  )  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLCSC_TAG_ORDER_STATUS",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,oracle.jdbc.OracleTypes.NUMERIC);
      __sJT_st.registerOutParameter(7,2003,"OL_OWNER.OLC_FULFILLMENT_STAT_ARR");
      __sJT_st.registerOutParameter(8,2003,"OL_OWNER.OLC_ERROR_MSG_ARR");
   }
   // set IN parameters
   __sJT_st.setString(2,P_SESSION);
   __sJT_st.setBigDecimal(3,P_ACCT_ID);
   __sJT_st.setString(4,P_USER);
   __sJT_st.setString(5,P_IP_ADDRESS);
   __sJT_st.setString(6,P_PENDING_FLAG);
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = __sJT_st.getBigDecimal(1);
   P_FULFILLMENT_STAT_ARR[0] = (com.etcc.csc.plsql.OLC_FULFILLMENT_STAT_ARR)__sJT_st.getORAData(7,com.etcc.csc.plsql.OLC_FULFILLMENT_STAT_ARR.getORADataFactory());
   P_ERROR_ARR[0] = (com.etcc.csc.plsql.OLC_ERROR_MSG_ARR)__sJT_st.getORAData(8,com.etcc.csc.plsql.OLC_ERROR_MSG_ARR.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:100^31*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/