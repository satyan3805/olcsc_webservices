package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VEHICLE_CLASSES_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VEHICLE_CLASSES_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,12,2,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  protected static final OLC_VEHICLE_CLASSES_REC _OLC_VEHICLE_CLASSES_RECFactory = new OLC_VEHICLE_CLASSES_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VEHICLE_CLASSES_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public OLC_VEHICLE_CLASSES_REC()
  { _init_struct(true); }
  public OLC_VEHICLE_CLASSES_REC(String VEHICLE_CLASS_CODE, String VEHICLE_CLASS_DESCR, String VEHICLE_CLASS_LONG_DESCR, java.math.BigDecimal VEHICLE_CLASS_ORDER, String DEFAULT_VALUE_FLAG) throws SQLException
  { _init_struct(true);
    setVEHICLE_CLASS_CODE(VEHICLE_CLASS_CODE);
    setVEHICLE_CLASS_DESCR(VEHICLE_CLASS_DESCR);
    setVEHICLE_CLASS_LONG_DESCR(VEHICLE_CLASS_LONG_DESCR);
    setVEHICLE_CLASS_ORDER(VEHICLE_CLASS_ORDER);
    setDEFAULT_VALUE_FLAG(DEFAULT_VALUE_FLAG);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VEHICLE_CLASSES_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VEHICLE_CLASSES_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getVEHICLE_CLASS_CODE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setVEHICLE_CLASS_CODE(String VEHICLE_CLASS_CODE) throws SQLException
  { _struct.setAttribute(0, VEHICLE_CLASS_CODE); }


  public String getVEHICLE_CLASS_DESCR() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setVEHICLE_CLASS_DESCR(String VEHICLE_CLASS_DESCR) throws SQLException
  { _struct.setAttribute(1, VEHICLE_CLASS_DESCR); }


  public String getVEHICLE_CLASS_LONG_DESCR() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setVEHICLE_CLASS_LONG_DESCR(String VEHICLE_CLASS_LONG_DESCR) throws SQLException
  { _struct.setAttribute(2, VEHICLE_CLASS_LONG_DESCR); }


  public java.math.BigDecimal getVEHICLE_CLASS_ORDER() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setVEHICLE_CLASS_ORDER(java.math.BigDecimal VEHICLE_CLASS_ORDER) throws SQLException
  { _struct.setAttribute(3, VEHICLE_CLASS_ORDER); }


  public String getDEFAULT_VALUE_FLAG() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setDEFAULT_VALUE_FLAG(String DEFAULT_VALUE_FLAG) throws SQLException
  { _struct.setAttribute(4, DEFAULT_VALUE_FLAG); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VEHICLE_CLASSES_REC" + "(" +
       ((getVEHICLE_CLASS_CODE()==null)?"null": "'" + getVEHICLE_CLASS_CODE()+"'" ) + "," +
       ((getVEHICLE_CLASS_DESCR()==null)?"null": "'" + getVEHICLE_CLASS_DESCR()+"'" ) + "," +
       ((getVEHICLE_CLASS_LONG_DESCR()==null)?"null": "'" + getVEHICLE_CLASS_LONG_DESCR()+"'" ) + "," +
       getVEHICLE_CLASS_ORDER() + "," +
       ((getDEFAULT_VALUE_FLAG()==null)?"null": "'" + getDEFAULT_VALUE_FLAG()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
