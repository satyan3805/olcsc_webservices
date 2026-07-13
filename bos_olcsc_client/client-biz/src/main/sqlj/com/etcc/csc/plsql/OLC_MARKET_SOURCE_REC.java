package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_MARKET_SOURCE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_MARKET_SOURCE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLC_MARKET_SOURCE_REC _OLC_MARKET_SOURCE_RECFactory = new OLC_MARKET_SOURCE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_MARKET_SOURCE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLC_MARKET_SOURCE_REC()
  { _init_struct(true); }
  public OLC_MARKET_SOURCE_REC(java.math.BigDecimal MS_ID, String MS_DESCR) throws SQLException
  { _init_struct(true);
    setMS_ID(MS_ID);
    setMS_DESCR(MS_DESCR);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_MARKET_SOURCE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_MARKET_SOURCE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getMS_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setMS_ID(java.math.BigDecimal MS_ID) throws SQLException
  { _struct.setAttribute(0, MS_ID); }


  public String getMS_DESCR() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setMS_DESCR(String MS_DESCR) throws SQLException
  { _struct.setAttribute(1, MS_DESCR); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_MARKET_SOURCE_REC" + "(" +
       getMS_ID() + "," +
       ((getMS_DESCR()==null)?"null": "'" + getMS_DESCR()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
