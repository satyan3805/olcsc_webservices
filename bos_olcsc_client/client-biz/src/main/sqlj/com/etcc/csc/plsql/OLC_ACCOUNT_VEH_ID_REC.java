package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCOUNT_VEH_ID_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCOUNT_VEH_ID_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final OLC_ACCOUNT_VEH_ID_REC _OLC_ACCOUNT_VEH_ID_RECFactory = new OLC_ACCOUNT_VEH_ID_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCOUNT_VEH_ID_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public OLC_ACCOUNT_VEH_ID_REC()
  { _init_struct(true); }
  public OLC_ACCOUNT_VEH_ID_REC(java.math.BigDecimal ACCT_VEHICLE_ID, String MOTOCYCLE_FLAG, String NO_TAG_FLAG) throws SQLException
  { _init_struct(true);
    setACCT_VEHICLE_ID(ACCT_VEHICLE_ID);
    setMOTOCYCLE_FLAG(MOTOCYCLE_FLAG);
    setNO_TAG_FLAG(NO_TAG_FLAG);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCOUNT_VEH_ID_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCOUNT_VEH_ID_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCT_VEHICLE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCT_VEHICLE_ID(java.math.BigDecimal ACCT_VEHICLE_ID) throws SQLException
  { _struct.setAttribute(0, ACCT_VEHICLE_ID); }


  public String getMOTOCYCLE_FLAG() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setMOTOCYCLE_FLAG(String MOTOCYCLE_FLAG) throws SQLException
  { _struct.setAttribute(1, MOTOCYCLE_FLAG); }


  public String getNO_TAG_FLAG() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setNO_TAG_FLAG(String NO_TAG_FLAG) throws SQLException
  { _struct.setAttribute(2, NO_TAG_FLAG); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ACCOUNT_VEH_ID_REC" + "(" +
       getACCT_VEHICLE_ID() + "," +
       ((getMOTOCYCLE_FLAG()==null)?"null": "'" + getMOTOCYCLE_FLAG()+"'" ) + "," +
       ((getNO_TAG_FLAG()==null)?"null": "'" + getNO_TAG_FLAG()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
