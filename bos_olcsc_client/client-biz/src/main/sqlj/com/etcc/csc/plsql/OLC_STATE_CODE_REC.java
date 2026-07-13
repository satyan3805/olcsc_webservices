package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_STATE_CODE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_STATE_CODE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  protected static final OLC_STATE_CODE_REC _OLC_STATE_CODE_RECFactory = new OLC_STATE_CODE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_STATE_CODE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public OLC_STATE_CODE_REC()
  { _init_struct(true); }
  public OLC_STATE_CODE_REC(String STATE_CODE, String STATE_NAME, String DEFAULT_VALUE, String DRIVER_LICENSE_PATTERN) throws SQLException
  { _init_struct(true);
    setSTATE_CODE(STATE_CODE);
    setSTATE_NAME(STATE_NAME);
    setDEFAULT_VALUE(DEFAULT_VALUE);
    setDRIVER_LICENSE_PATTERN(DRIVER_LICENSE_PATTERN);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_STATE_CODE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_STATE_CODE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getSTATE_CODE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setSTATE_CODE(String STATE_CODE) throws SQLException
  { _struct.setAttribute(0, STATE_CODE); }


  public String getSTATE_NAME() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setSTATE_NAME(String STATE_NAME) throws SQLException
  { _struct.setAttribute(1, STATE_NAME); }


  public String getDEFAULT_VALUE() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setDEFAULT_VALUE(String DEFAULT_VALUE) throws SQLException
  { _struct.setAttribute(2, DEFAULT_VALUE); }


  public String getDRIVER_LICENSE_PATTERN() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setDRIVER_LICENSE_PATTERN(String DRIVER_LICENSE_PATTERN) throws SQLException
  { _struct.setAttribute(3, DRIVER_LICENSE_PATTERN); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_STATE_CODE_REC" + "(" +
       ((getSTATE_CODE()==null)?"null": "'" + getSTATE_CODE()+"'" ) + "," +
       ((getSTATE_NAME()==null)?"null": "'" + getSTATE_NAME()+"'" ) + "," +
       ((getDEFAULT_VALUE()==null)?"null": "'" + getDEFAULT_VALUE()+"'" ) + "," +
       ((getDRIVER_LICENSE_PATTERN()==null)?"null": "'" + getDRIVER_LICENSE_PATTERN()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
