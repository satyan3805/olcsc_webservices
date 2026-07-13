package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLCSC_VEHICLE_NICKNAME_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLCSC_VEHICLE_NICKNAME_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLCSC_VEHICLE_NICKNAME_REC _OLCSC_VEHICLE_NICKNAME_RECFactory = new OLCSC_VEHICLE_NICKNAME_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_VEHICLE_NICKNAME_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLCSC_VEHICLE_NICKNAME_REC()
  { _init_struct(true); }
  public OLCSC_VEHICLE_NICKNAME_REC(String VEHICLE_NICKNAME, String VEHICLE_NICKNAME_TYPE) throws SQLException
  { _init_struct(true);
    setVEHICLE_NICKNAME(VEHICLE_NICKNAME);
    setVEHICLE_NICKNAME_TYPE(VEHICLE_NICKNAME_TYPE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLCSC_VEHICLE_NICKNAME_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLCSC_VEHICLE_NICKNAME_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getVEHICLE_NICKNAME() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setVEHICLE_NICKNAME(String VEHICLE_NICKNAME) throws SQLException
  { _struct.setAttribute(0, VEHICLE_NICKNAME); }


  public String getVEHICLE_NICKNAME_TYPE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setVEHICLE_NICKNAME_TYPE(String VEHICLE_NICKNAME_TYPE) throws SQLException
  { _struct.setAttribute(1, VEHICLE_NICKNAME_TYPE); }

  public String toString()
  { try {
     return "OL_OWNER.OLCSC_VEHICLE_NICKNAME_REC" + "(" +
       ((getVEHICLE_NICKNAME()==null)?"null": "'" + getVEHICLE_NICKNAME()+"'" ) + "," +
       ((getVEHICLE_NICKNAME_TYPE()==null)?"null": "'" + getVEHICLE_NICKNAME_TYPE()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
