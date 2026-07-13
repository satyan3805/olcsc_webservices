package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_UNINV_PMT_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_UNINV_PMT_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final OLC_UNINV_PMT_REC _OLC_UNINV_PMT_RECFactory = new OLC_UNINV_PMT_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_UNINV_PMT_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public OLC_UNINV_PMT_REC()
  { _init_struct(true); }
  public OLC_UNINV_PMT_REC(java.math.BigDecimal ACCOUNT_ID, java.math.BigDecimal VIOLATION_ID, java.math.BigDecimal TOLL_PAID_AMOUNT) throws SQLException
  { _init_struct(true);
    setACCOUNT_ID(ACCOUNT_ID);
    setVIOLATION_ID(VIOLATION_ID);
    setTOLL_PAID_AMOUNT(TOLL_PAID_AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_UNINV_PMT_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_UNINV_PMT_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(0, ACCOUNT_ID); }


  public java.math.BigDecimal getVIOLATION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setVIOLATION_ID(java.math.BigDecimal VIOLATION_ID) throws SQLException
  { _struct.setAttribute(1, VIOLATION_ID); }


  public java.math.BigDecimal getTOLL_PAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setTOLL_PAID_AMOUNT(java.math.BigDecimal TOLL_PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(2, TOLL_PAID_AMOUNT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_UNINV_PMT_REC" + "(" +
       getACCOUNT_ID() + "," +
       getVIOLATION_ID() + "," +
       getTOLL_PAID_AMOUNT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
