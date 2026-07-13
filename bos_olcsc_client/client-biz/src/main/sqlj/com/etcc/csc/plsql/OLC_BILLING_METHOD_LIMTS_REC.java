/*@lineinfo:filename=OLC_BILLING_METHOD_LIMTS_REC*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;
import sqlj.runtime.ref.DefaultContext;
import sqlj.runtime.ConnectionContext;

public class OLC_BILLING_METHOD_LIMTS_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

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
  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,2,2,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[8];
  protected static final OLC_BILLING_METHOD_LIMTS_REC _OLC_BILLING_METHOD_LIMTS_RECFactory = new OLC_BILLING_METHOD_LIMTS_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_BILLING_METHOD_LIMTS_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[8], _sqlType, _factory); }
  public OLC_BILLING_METHOD_LIMTS_REC()
  { _init_struct(true); __tx = DefaultContext.getDefaultContext(); }
  public OLC_BILLING_METHOD_LIMTS_REC(DefaultContext c) /*throws SQLException*/
  { _init_struct(true); __tx = c; }
  public OLC_BILLING_METHOD_LIMTS_REC(Connection c) /*throws SQLException*/
  { _init_struct(true); __onn = c; }
  public OLC_BILLING_METHOD_LIMTS_REC(java.math.BigDecimal ACCOUNT_ID, String PLAN, java.math.BigDecimal MIN_BILLING_METHOD, java.math.BigDecimal MAX_BILLING_METHOD, java.math.BigDecimal MIN_CREDIT_CARD, java.math.BigDecimal MAX_CREDIT_CARD, java.math.BigDecimal MIN_ACH_ACCOUNT, java.math.BigDecimal MAX_ACH_ACCOUNT) throws SQLException
  {
    _init_struct(true);
    setACCOUNT_ID(ACCOUNT_ID);
    setPLAN(PLAN);
    setMIN_BILLING_METHOD(MIN_BILLING_METHOD);
    setMAX_BILLING_METHOD(MAX_BILLING_METHOD);
    setMIN_CREDIT_CARD(MIN_CREDIT_CARD);
    setMAX_CREDIT_CARD(MAX_CREDIT_CARD);
    setMIN_ACH_ACCOUNT(MIN_ACH_ACCOUNT);
    setMAX_ACH_ACCOUNT(MAX_ACH_ACCOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    if (__tx!=null && __onn!=c) release();
    __onn = c;
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  public void setFrom(OLC_BILLING_METHOD_LIMTS_REC o) throws SQLException
  { setContextFrom(o); setValueFrom(o); }
  protected void setContextFrom(OLC_BILLING_METHOD_LIMTS_REC o) throws SQLException
  { release(); __tx = o.__tx; __onn = o.__onn; }
  protected void setValueFrom(OLC_BILLING_METHOD_LIMTS_REC o) { _struct = o._struct; }
  protected ORAData create(OLC_BILLING_METHOD_LIMTS_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) { if (o!=null) { o.release(); }; return null; }
    if (o == null) o = new OLC_BILLING_METHOD_LIMTS_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    o.__onn = ((STRUCT) d).getJavaSqlConnection();
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(0, ACCOUNT_ID); }


  public String getPLAN() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setPLAN(String PLAN) throws SQLException
  { _struct.setAttribute(1, PLAN); }


  public java.math.BigDecimal getMIN_BILLING_METHOD() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setMIN_BILLING_METHOD(java.math.BigDecimal MIN_BILLING_METHOD) throws SQLException
  { _struct.setAttribute(2, MIN_BILLING_METHOD); }


  public java.math.BigDecimal getMAX_BILLING_METHOD() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setMAX_BILLING_METHOD(java.math.BigDecimal MAX_BILLING_METHOD) throws SQLException
  { _struct.setAttribute(3, MAX_BILLING_METHOD); }


  public java.math.BigDecimal getMIN_CREDIT_CARD() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setMIN_CREDIT_CARD(java.math.BigDecimal MIN_CREDIT_CARD) throws SQLException
  { _struct.setAttribute(4, MIN_CREDIT_CARD); }


  public java.math.BigDecimal getMAX_CREDIT_CARD() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setMAX_CREDIT_CARD(java.math.BigDecimal MAX_CREDIT_CARD) throws SQLException
  { _struct.setAttribute(5, MAX_CREDIT_CARD); }


  public java.math.BigDecimal getMIN_ACH_ACCOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setMIN_ACH_ACCOUNT(java.math.BigDecimal MIN_ACH_ACCOUNT) throws SQLException
  { _struct.setAttribute(6, MIN_ACH_ACCOUNT); }


  public java.math.BigDecimal getMAX_ACH_ACCOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setMAX_ACH_ACCOUNT(java.math.BigDecimal MAX_ACH_ACCOUNT) throws SQLException
  { _struct.setAttribute(7, MAX_ACH_ACCOUNT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC" + "(" +
       getACCOUNT_ID() + "," +
       ((getPLAN()==null)?"null": "'" + getPLAN()+"'" ) + "," +
       getMIN_BILLING_METHOD() + "," +
       getMAX_BILLING_METHOD() + "," +
       getMIN_CREDIT_CARD() + "," +
       getMAX_CREDIT_CARD() + "," +
       getMIN_ACH_ACCOUNT() + "," +
       getMAX_ACH_ACCOUNT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }


  public OLC_BILLING_METHOD_LIMTS_REC INIT ()
  throws java.sql.SQLException
  {
    OLC_BILLING_METHOD_LIMTS_REC __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:197^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC.INIT())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC.INIT()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:197^105*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:203^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC.INIT())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC.INIT()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"OL_OWNER.OLC_BILLING_METHOD_LIMTS_REC");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_BILLING_METHOD_LIMTS_REC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:203^105*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/