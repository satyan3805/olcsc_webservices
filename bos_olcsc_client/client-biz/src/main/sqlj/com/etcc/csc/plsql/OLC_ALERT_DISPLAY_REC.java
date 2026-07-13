package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ALERT_DISPLAY_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ALERT_DISPLAY_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 4,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLC_ALERT_DISPLAY_REC _OLC_ALERT_DISPLAY_RECFactory = new OLC_ALERT_DISPLAY_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ALERT_DISPLAY_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLC_ALERT_DISPLAY_REC()
  { _init_struct(true); }
  public OLC_ALERT_DISPLAY_REC(int ALERT_ID, String DISPLAY) throws SQLException
  { _init_struct(true);
    setALERT_ID(ALERT_ID);
    setDISPLAY(DISPLAY);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ALERT_DISPLAY_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ALERT_DISPLAY_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public int getALERT_ID() throws SQLException
  { return ((Integer) _struct.getAttribute(0)).intValue(); }

  public void setALERT_ID(int ALERT_ID) throws SQLException
  { _struct.setAttribute(0, new Integer(ALERT_ID)); }


  public String getDISPLAY() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setDISPLAY(String DISPLAY) throws SQLException
  { _struct.setAttribute(1, DISPLAY); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ALERT_DISPLAY_REC" + "(" +
       getALERT_ID() + "," +
       ((getDISPLAY()==null)?"null": "'" + getDISPLAY()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
