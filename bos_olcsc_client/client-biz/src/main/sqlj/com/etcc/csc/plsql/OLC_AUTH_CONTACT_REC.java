/*@lineinfo:filename=OLC_AUTH_CONTACT_REC*//*@lineinfo:user-code*//*@lineinfo:1^1*/package com.etcc.csc.plsql;

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

public class OLC_AUTH_CONTACT_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_AUTH_CONTACT_REC";
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

  protected static int[] _sqlType =  { 2,12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  protected static final OLC_AUTH_CONTACT_REC _OLC_AUTH_CONTACT_RECFactory = new OLC_AUTH_CONTACT_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_AUTH_CONTACT_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public OLC_AUTH_CONTACT_REC()
  { _init_struct(true); __tx = DefaultContext.getDefaultContext(); }
  public OLC_AUTH_CONTACT_REC(DefaultContext c) /*throws SQLException*/
  { _init_struct(true); __tx = c; }
  public OLC_AUTH_CONTACT_REC(Connection c) /*throws SQLException*/
  { _init_struct(true); __onn = c; }
  public OLC_AUTH_CONTACT_REC(java.math.BigDecimal AUTH_CONTACT_ID, String ACTION_FLAG, String LAST_NAME, String FIRST_NAME, String AUTH_CONTACT_PASSWORD) throws SQLException
  {
    _init_struct(true);
    setAUTH_CONTACT_ID(AUTH_CONTACT_ID);
    setACTION_FLAG(ACTION_FLAG);
    setLAST_NAME(LAST_NAME);
    setFIRST_NAME(FIRST_NAME);
    setAUTH_CONTACT_PASSWORD(AUTH_CONTACT_PASSWORD);
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
  public void setFrom(OLC_AUTH_CONTACT_REC o) throws SQLException
  { setContextFrom(o); setValueFrom(o); }
  protected void setContextFrom(OLC_AUTH_CONTACT_REC o) throws SQLException
  { release(); __tx = o.__tx; __onn = o.__onn; }
  protected void setValueFrom(OLC_AUTH_CONTACT_REC o) { _struct = o._struct; }
  protected ORAData create(OLC_AUTH_CONTACT_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) { if (o!=null) { o.release(); }; return null; }
    if (o == null) o = new OLC_AUTH_CONTACT_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    o.__onn = ((STRUCT) d).getJavaSqlConnection();
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getAUTH_CONTACT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setAUTH_CONTACT_ID(java.math.BigDecimal AUTH_CONTACT_ID) throws SQLException
  { _struct.setAttribute(0, AUTH_CONTACT_ID); }


  public String getACTION_FLAG() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setACTION_FLAG(String ACTION_FLAG) throws SQLException
  { _struct.setAttribute(1, ACTION_FLAG); }


  public String getLAST_NAME() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLAST_NAME(String LAST_NAME) throws SQLException
  { _struct.setAttribute(2, LAST_NAME); }


  public String getFIRST_NAME() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setFIRST_NAME(String FIRST_NAME) throws SQLException
  { _struct.setAttribute(3, FIRST_NAME); }


  public String getAUTH_CONTACT_PASSWORD() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setAUTH_CONTACT_PASSWORD(String AUTH_CONTACT_PASSWORD) throws SQLException
  { _struct.setAttribute(4, AUTH_CONTACT_PASSWORD); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_AUTH_CONTACT_REC" + "(" +
       getAUTH_CONTACT_ID() + "," +
       ((getACTION_FLAG()==null)?"null": "'" + getACTION_FLAG()+"'" ) + "," +
       ((getLAST_NAME()==null)?"null": "'" + getLAST_NAME()+"'" ) + "," +
       ((getFIRST_NAME()==null)?"null": "'" + getFIRST_NAME()+"'" ) + "," +
       ((getAUTH_CONTACT_PASSWORD()==null)?"null": "'" + getAUTH_CONTACT_PASSWORD()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }


  public OLC_AUTH_CONTACT_REC INIT ()
  throws java.sql.SQLException
  {
    OLC_AUTH_CONTACT_REC __jPt_result=null;
 try {
    /*@lineinfo:generated-code*//*@lineinfo:170^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLC_AUTH_CONTACT_REC.INIT())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLC_AUTH_CONTACT_REC.INIT()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"0com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"OL_OWNER.OLC_AUTH_CONTACT_REC");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:170^97*/
 } catch(java.sql.SQLException _err) {
   try {
      getConnectionContext().getExecutionContext().close();
      closeConnection();
      if (__dataSource==null) throw _err;
    /*@lineinfo:generated-code*//*@lineinfo:176^5*/

//  ************************************************************
//  #sql [getConnectionContext()] __jPt_result = { VALUES(OL_OWNER.OLC_AUTH_CONTACT_REC.INIT())  };
//  ************************************************************

{
  // declare temps
  oracle.jdbc.OracleCallableStatement __sJT_st = null;
  sqlj.runtime.ref.DefaultContext __sJT_cc = getConnectionContext(); if (__sJT_cc==null) sqlj.runtime.error.RuntimeRefErrors.raise_NULL_CONN_CTX();
  sqlj.runtime.ExecutionContext.OracleContext __sJT_ec = ((__sJT_cc.getExecutionContext()==null) ? sqlj.runtime.ExecutionContext.raiseNullExecCtx() : __sJT_cc.getExecutionContext().getOracleContext());
  try {
   String theSqlTS = "BEGIN :1 := OL_OWNER.OLC_AUTH_CONTACT_REC.INIT()  \n; END;";
   __sJT_st = __sJT_ec.prepareOracleCall(__sJT_cc,"1com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC",theSqlTS);
   if (__sJT_ec.isNew())
   {
      __sJT_st.registerOutParameter(1,2002,"OL_OWNER.OLC_AUTH_CONTACT_REC");
   }
  // execute statement
   __sJT_ec.oracleExecuteUpdate();
   // retrieve OUT parameters
   __jPt_result = (com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC)__sJT_st.getORAData(1,com.etcc.csc.plsql.OLC_AUTH_CONTACT_REC.getORADataFactory());
  } finally { __sJT_ec.oracleClose(); }
}


//  ************************************************************

/*@lineinfo:user-code*//*@lineinfo:176^97*/
   } catch (java.sql.SQLException _err2) { 
     try { getConnectionContext().getExecutionContext().close(); } catch (java.sql.SQLException _sqle) {}
     throw _err; 
  }
 }
    return __jPt_result;
  }
}/*@lineinfo:generated-code*/