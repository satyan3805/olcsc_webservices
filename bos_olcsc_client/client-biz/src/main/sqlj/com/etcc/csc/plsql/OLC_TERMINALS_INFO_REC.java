package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_TERMINALS_INFO_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_TERMINALS_INFO_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,91,91,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[7];
  protected static final OLC_TERMINALS_INFO_REC _OLC_TERMINALS_INFO_RECFactory = new OLC_TERMINALS_INFO_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_TERMINALS_INFO_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[7], _sqlType, _factory); }
  public OLC_TERMINALS_INFO_REC()
  { _init_struct(true); }
  public OLC_TERMINALS_INFO_REC(String TERMINAL_SERIAL_NUMBER, String WORKSTATION_NAME, java.sql.Timestamp DATE_CREATED, java.sql.Timestamp DATE_MODIFIED, String CREATED_BY, String MODIFIED_BY, String IS_ACTIVE) throws SQLException
  { _init_struct(true);
    setTERMINAL_SERIAL_NUMBER(TERMINAL_SERIAL_NUMBER);
    setWORKSTATION_NAME(WORKSTATION_NAME);
    setDATE_CREATED(DATE_CREATED);
    setDATE_MODIFIED(DATE_MODIFIED);
    setCREATED_BY(CREATED_BY);
    setMODIFIED_BY(MODIFIED_BY);
    setIS_ACTIVE(IS_ACTIVE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_TERMINALS_INFO_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_TERMINALS_INFO_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getTERMINAL_SERIAL_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setTERMINAL_SERIAL_NUMBER(String TERMINAL_SERIAL_NUMBER) throws SQLException
  { _struct.setAttribute(0, TERMINAL_SERIAL_NUMBER); }


  public String getWORKSTATION_NAME() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setWORKSTATION_NAME(String WORKSTATION_NAME) throws SQLException
  { _struct.setAttribute(1, WORKSTATION_NAME); }


  public java.sql.Timestamp getDATE_CREATED() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(2); }

  public void setDATE_CREATED(java.sql.Timestamp DATE_CREATED) throws SQLException
  { _struct.setAttribute(2, DATE_CREATED); }


  public java.sql.Timestamp getDATE_MODIFIED() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(3); }

  public void setDATE_MODIFIED(java.sql.Timestamp DATE_MODIFIED) throws SQLException
  { _struct.setAttribute(3, DATE_MODIFIED); }


  public String getCREATED_BY() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setCREATED_BY(String CREATED_BY) throws SQLException
  { _struct.setAttribute(4, CREATED_BY); }


  public String getMODIFIED_BY() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setMODIFIED_BY(String MODIFIED_BY) throws SQLException
  { _struct.setAttribute(5, MODIFIED_BY); }


  public String getIS_ACTIVE() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setIS_ACTIVE(String IS_ACTIVE) throws SQLException
  { _struct.setAttribute(6, IS_ACTIVE); }

}
