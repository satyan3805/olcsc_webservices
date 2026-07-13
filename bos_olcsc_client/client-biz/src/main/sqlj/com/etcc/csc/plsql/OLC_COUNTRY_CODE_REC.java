package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_COUNTRY_CODE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_COUNTRY_CODE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLC_COUNTRY_CODE_REC _OLC_COUNTRY_CODE_RECFactory = new OLC_COUNTRY_CODE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_COUNTRY_CODE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLC_COUNTRY_CODE_REC()
  { _init_struct(true); }
  public OLC_COUNTRY_CODE_REC(String COUNTRY_CODE, String COUNTRY_NAME) throws SQLException
  { _init_struct(true);
    setCOUNTRY_CODE(COUNTRY_CODE);
    setCOUNTRY_NAME(COUNTRY_NAME);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_COUNTRY_CODE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_COUNTRY_CODE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getCOUNTRY_CODE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setCOUNTRY_CODE(String COUNTRY_CODE) throws SQLException
  { _struct.setAttribute(0, COUNTRY_CODE); }


  public String getCOUNTRY_NAME() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setCOUNTRY_NAME(String COUNTRY_NAME) throws SQLException
  { _struct.setAttribute(1, COUNTRY_NAME); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_COUNTRY_CODE_REC" + "(" +
       ((getCOUNTRY_CODE()==null)?"null": "'" + getCOUNTRY_CODE()+"'" ) + "," +
       ((getCOUNTRY_NAME()==null)?"null": "'" + getCOUNTRY_NAME()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
