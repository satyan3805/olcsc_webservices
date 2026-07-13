package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLCSC_INV_T_LIC_PLATE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLCSC_INV_T_LIC_PLATE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final OLCSC_INV_T_LIC_PLATE_REC _OLCSC_INV_T_LIC_PLATE_RECFactory = new OLCSC_INV_T_LIC_PLATE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_INV_T_LIC_PLATE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public OLCSC_INV_T_LIC_PLATE_REC()
  { _init_struct(true); }
  public OLCSC_INV_T_LIC_PLATE_REC(String LIC_PLATE, String LIC_STATE, java.math.BigDecimal VIOLATOR_ID) throws SQLException
  { _init_struct(true);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setVIOLATOR_ID(VIOLATOR_ID);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLCSC_INV_T_LIC_PLATE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLCSC_INV_T_LIC_PLATE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(0, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(1, LIC_STATE); }


  public java.math.BigDecimal getVIOLATOR_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setVIOLATOR_ID(java.math.BigDecimal VIOLATOR_ID) throws SQLException
  { _struct.setAttribute(2, VIOLATOR_ID); }

  public String toString()
  { try {
     return "OL_OWNER.OLCSC_INV_T_LIC_PLATE_REC" + "(" +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       getVIOLATOR_ID() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
