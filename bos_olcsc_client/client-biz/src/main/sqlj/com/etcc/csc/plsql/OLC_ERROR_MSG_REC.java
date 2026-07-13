package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ERROR_MSG_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ERROR_MSG_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[1];
  protected static final OLC_ERROR_MSG_REC _OLC_ERROR_MSG_RECFactory = new OLC_ERROR_MSG_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ERROR_MSG_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[1], _sqlType, _factory); }
  public OLC_ERROR_MSG_REC()
  { _init_struct(true); }
  public OLC_ERROR_MSG_REC(String ERROR_MSG) throws SQLException
  { _init_struct(true);
    setERROR_MSG(ERROR_MSG);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ERROR_MSG_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ERROR_MSG_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getERROR_MSG() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setERROR_MSG(String ERROR_MSG) throws SQLException
  { _struct.setAttribute(0, ERROR_MSG); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ERROR_MSG_REC" + "(" +
       ((getERROR_MSG()==null)?"null": "'" + getERROR_MSG()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
