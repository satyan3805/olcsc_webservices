package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class MULTIPLE_VEHICLE_UPLOAD_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  protected static final MULTIPLE_VEHICLE_UPLOAD_REC _MULTIPLE_VEHICLE_UPLOAD_RECFactory = new MULTIPLE_VEHICLE_UPLOAD_REC();

  public static ORADataFactory getORADataFactory()
  { return _MULTIPLE_VEHICLE_UPLOAD_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public MULTIPLE_VEHICLE_UPLOAD_REC()
  { _init_struct(true); }
  public MULTIPLE_VEHICLE_UPLOAD_REC(String REASON, String LIC_STATE, String LIC_PLATE, String RAW_RECORD, String ERROR_MSG) throws SQLException
  { _init_struct(true);
    setREASON(REASON);
    setLIC_STATE(LIC_STATE);
    setLIC_PLATE(LIC_PLATE);
    setRAW_RECORD(RAW_RECORD);
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
  protected ORAData create(MULTIPLE_VEHICLE_UPLOAD_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new MULTIPLE_VEHICLE_UPLOAD_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getREASON() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setREASON(String REASON) throws SQLException
  { _struct.setAttribute(0, REASON); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(1, LIC_STATE); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(2, LIC_PLATE); }


  public String getRAW_RECORD() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setRAW_RECORD(String RAW_RECORD) throws SQLException
  { _struct.setAttribute(3, RAW_RECORD); }


  public String getERROR_MSG() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setERROR_MSG(String ERROR_MSG) throws SQLException
  { _struct.setAttribute(4, ERROR_MSG); }

  public String toString()
  { try {
     return "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_REC" + "(" +
       ((getREASON()==null)?"null": "'" + getREASON()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       ((getRAW_RECORD()==null)?"null": "'" + getRAW_RECORD()+"'" ) + "," +
       ((getERROR_MSG()==null)?"null": "'" + getERROR_MSG()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
