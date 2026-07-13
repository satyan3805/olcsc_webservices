package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_STTMT_DATE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_STTMT_DATE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[1];
  protected static final OLC_STTMT_DATE_REC _OLC_STTMT_DATE_RECFactory = new OLC_STTMT_DATE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_STTMT_DATE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[1], _sqlType, _factory); }
  public OLC_STTMT_DATE_REC()
  { _init_struct(true); }
  public OLC_STTMT_DATE_REC(String MONTH_DESC) throws SQLException
  { _init_struct(true);
    setMONTH_DESC(MONTH_DESC);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_STTMT_DATE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_STTMT_DATE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getMONTH_DESC() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setMONTH_DESC(String MONTH_DESC) throws SQLException
  { _struct.setAttribute(0, MONTH_DESC); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_STTMT_DATE_REC" + "(" +
       ((getMONTH_DESC()==null)?"null": "'" + getMONTH_DESC()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
