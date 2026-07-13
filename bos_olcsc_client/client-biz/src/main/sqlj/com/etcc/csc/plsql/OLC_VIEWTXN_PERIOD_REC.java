package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VIEWTXN_PERIOD_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VIEWTXN_PERIOD_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLC_VIEWTXN_PERIOD_REC _OLC_VIEWTXN_PERIOD_RECFactory = new OLC_VIEWTXN_PERIOD_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VIEWTXN_PERIOD_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLC_VIEWTXN_PERIOD_REC()
  { _init_struct(true); }
  public OLC_VIEWTXN_PERIOD_REC(java.math.BigDecimal PERIOD, String DESCRIPTION) throws SQLException
  { _init_struct(true);
    setPERIOD(PERIOD);
    setDESCRIPTION(DESCRIPTION);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VIEWTXN_PERIOD_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VIEWTXN_PERIOD_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getPERIOD() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setPERIOD(java.math.BigDecimal PERIOD) throws SQLException
  { _struct.setAttribute(0, PERIOD); }


  public String getDESCRIPTION() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setDESCRIPTION(String DESCRIPTION) throws SQLException
  { _struct.setAttribute(1, DESCRIPTION); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VIEWTXN_PERIOD_REC" + "(" +
       getPERIOD() + "," +
       ((getDESCRIPTION()==null)?"null": "'" + getDESCRIPTION()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
