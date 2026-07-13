package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_UNINVOICED_VIOLS_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_UNINVOICED_VIOLS_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,2,2,12,91,12,12,12,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[12];
  protected static final OLC_VPS_UNINVOICED_VIOLS_REC _OLC_VPS_UNINVOICED_VIOLS_RECFactory = new OLC_VPS_UNINVOICED_VIOLS_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_UNINVOICED_VIOLS_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[12], _sqlType, _factory); }
  public OLC_VPS_UNINVOICED_VIOLS_REC()
  { _init_struct(true); }
  public OLC_VPS_UNINVOICED_VIOLS_REC(java.math.BigDecimal AGENCY_ID, String AGENCY_NAME, java.math.BigDecimal VIOLATION_ID, java.math.BigDecimal VIOLATOR_ID, String FULL_LOCATION_NAME, java.sql.Timestamp VIOLATION_DATE_TIME, String STATUS, String LIC_PLATE, String LIC_STATE, java.math.BigDecimal ORIG_TOLL_AMT, java.math.BigDecimal OPEN_TOLL_AMT, java.math.BigDecimal PAID_TOLL_AMT) throws SQLException
  { _init_struct(true);
    setAGENCY_ID(AGENCY_ID);
    setAGENCY_NAME(AGENCY_NAME);
    setVIOLATION_ID(VIOLATION_ID);
    setVIOLATOR_ID(VIOLATOR_ID);
    setFULL_LOCATION_NAME(FULL_LOCATION_NAME);
    setVIOLATION_DATE_TIME(VIOLATION_DATE_TIME);
    setSTATUS(STATUS);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setORIG_TOLL_AMT(ORIG_TOLL_AMT);
    setOPEN_TOLL_AMT(OPEN_TOLL_AMT);
    setPAID_TOLL_AMT(PAID_TOLL_AMT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_UNINVOICED_VIOLS_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_UNINVOICED_VIOLS_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getAGENCY_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setAGENCY_ID(java.math.BigDecimal AGENCY_ID) throws SQLException
  { _struct.setAttribute(0, AGENCY_ID); }


  public String getAGENCY_NAME() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setAGENCY_NAME(String AGENCY_NAME) throws SQLException
  { _struct.setAttribute(1, AGENCY_NAME); }


  public java.math.BigDecimal getVIOLATION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setVIOLATION_ID(java.math.BigDecimal VIOLATION_ID) throws SQLException
  { _struct.setAttribute(2, VIOLATION_ID); }


  public java.math.BigDecimal getVIOLATOR_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setVIOLATOR_ID(java.math.BigDecimal VIOLATOR_ID) throws SQLException
  { _struct.setAttribute(3, VIOLATOR_ID); }


  public String getFULL_LOCATION_NAME() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setFULL_LOCATION_NAME(String FULL_LOCATION_NAME) throws SQLException
  { _struct.setAttribute(4, FULL_LOCATION_NAME); }


  public java.sql.Timestamp getVIOLATION_DATE_TIME() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(5); }

  public void setVIOLATION_DATE_TIME(java.sql.Timestamp VIOLATION_DATE_TIME) throws SQLException
  { _struct.setAttribute(5, VIOLATION_DATE_TIME); }


  public String getSTATUS() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setSTATUS(String STATUS) throws SQLException
  { _struct.setAttribute(6, STATUS); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(7, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(8, LIC_STATE); }


  public java.math.BigDecimal getORIG_TOLL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setORIG_TOLL_AMT(java.math.BigDecimal ORIG_TOLL_AMT) throws SQLException
  { _struct.setAttribute(9, ORIG_TOLL_AMT); }


  public java.math.BigDecimal getOPEN_TOLL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setOPEN_TOLL_AMT(java.math.BigDecimal OPEN_TOLL_AMT) throws SQLException
  { _struct.setAttribute(10, OPEN_TOLL_AMT); }


  public java.math.BigDecimal getPAID_TOLL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setPAID_TOLL_AMT(java.math.BigDecimal PAID_TOLL_AMT) throws SQLException
  { _struct.setAttribute(11, PAID_TOLL_AMT); }

}
