package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_PAY_PLAN_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_PAY_PLAN_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 91,2,91,12,2,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final OLC_VPS_PAY_PLAN_REC _OLC_VPS_PAY_PLAN_RECFactory = new OLC_VPS_PAY_PLAN_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_PAY_PLAN_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public OLC_VPS_PAY_PLAN_REC()
  { _init_struct(true); }
  public OLC_VPS_PAY_PLAN_REC(java.sql.Timestamp DUE_DATE, java.math.BigDecimal AMOUNT_DUE, java.sql.Timestamp PAY_DATE, String PAY_METHOD, java.math.BigDecimal AMOUNT, String CLERK) throws SQLException
  { _init_struct(true);
    setDUE_DATE(DUE_DATE);
    setAMOUNT_DUE(AMOUNT_DUE);
    setPAY_DATE(PAY_DATE);
    setPAY_METHOD(PAY_METHOD);
    setAMOUNT(AMOUNT);
    setCLERK(CLERK);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_PAY_PLAN_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_PAY_PLAN_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.sql.Timestamp getDUE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(0); }

  public void setDUE_DATE(java.sql.Timestamp DUE_DATE) throws SQLException
  { _struct.setAttribute(0, DUE_DATE); }


  public java.math.BigDecimal getAMOUNT_DUE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setAMOUNT_DUE(java.math.BigDecimal AMOUNT_DUE) throws SQLException
  { _struct.setAttribute(1, AMOUNT_DUE); }


  public java.sql.Timestamp getPAY_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(2); }

  public void setPAY_DATE(java.sql.Timestamp PAY_DATE) throws SQLException
  { _struct.setAttribute(2, PAY_DATE); }


  public String getPAY_METHOD() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setPAY_METHOD(String PAY_METHOD) throws SQLException
  { _struct.setAttribute(3, PAY_METHOD); }


  public java.math.BigDecimal getAMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setAMOUNT(java.math.BigDecimal AMOUNT) throws SQLException
  { _struct.setAttribute(4, AMOUNT); }


  public String getCLERK() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setCLERK(String CLERK) throws SQLException
  { _struct.setAttribute(5, CLERK); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VPS_PAY_PLAN_REC" + "(" +
       getDUE_DATE() + "," +
       getAMOUNT_DUE() + "," +
       getPAY_DATE() + "," +
       ((getPAY_METHOD()==null)?"null": "'" + getPAY_METHOD()+"'" ) + "," +
       getAMOUNT() + "," +
       ((getCLERK()==null)?"null": "'" + getCLERK()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
