package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_UNINVOICED_VIOLS_REC_N implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_UNINVOICED_VIOLS_REC_N";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,91,12,2,2,2,12,12,2,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[13];
  protected static final OLC_UNINVOICED_VIOLS_REC_N _OLC_UNINVOICED_VIOLS_REC_NFactory = new OLC_UNINVOICED_VIOLS_REC_N();

  public static ORADataFactory getORADataFactory()
  { return _OLC_UNINVOICED_VIOLS_REC_NFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[13], _sqlType, _factory); }
  public OLC_UNINVOICED_VIOLS_REC_N()
  { _init_struct(true); }
  public OLC_UNINVOICED_VIOLS_REC_N(java.math.BigDecimal VIOLATION_ID, String VIOLATION_LOCATION, String FULL_LOCATION_NAME, java.sql.Timestamp VIOLATION_DATE_TIME, String STATUS, java.math.BigDecimal CASH_AMT, java.math.BigDecimal AVI_AMT, java.math.BigDecimal VIOLATOR_ID, String LIC_PLATE, String LIC_STATE, java.math.BigDecimal ONLINE_FEE, String AGENCY_ID, String AGENCY_NAME) throws SQLException
  { _init_struct(true);
    setVIOLATION_ID(VIOLATION_ID);
    setVIOLATION_LOCATION(VIOLATION_LOCATION);
    setFULL_LOCATION_NAME(FULL_LOCATION_NAME);
    setVIOLATION_DATE_TIME(VIOLATION_DATE_TIME);
    setSTATUS(STATUS);
    setCASH_AMT(CASH_AMT);
    setAVI_AMT(AVI_AMT);
    setVIOLATOR_ID(VIOLATOR_ID);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setONLINE_FEE(ONLINE_FEE);
    setAGENCY_ID(AGENCY_ID);
    setAGENCY_NAME(AGENCY_NAME);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_UNINVOICED_VIOLS_REC_N o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_UNINVOICED_VIOLS_REC_N();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getVIOLATION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setVIOLATION_ID(java.math.BigDecimal VIOLATION_ID) throws SQLException
  { _struct.setAttribute(0, VIOLATION_ID); }


  public String getVIOLATION_LOCATION() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setVIOLATION_LOCATION(String VIOLATION_LOCATION) throws SQLException
  { _struct.setAttribute(1, VIOLATION_LOCATION); }


  public String getFULL_LOCATION_NAME() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setFULL_LOCATION_NAME(String FULL_LOCATION_NAME) throws SQLException
  { _struct.setAttribute(2, FULL_LOCATION_NAME); }


  public java.sql.Timestamp getVIOLATION_DATE_TIME() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(3); }

  public void setVIOLATION_DATE_TIME(java.sql.Timestamp VIOLATION_DATE_TIME) throws SQLException
  { _struct.setAttribute(3, VIOLATION_DATE_TIME); }


  public String getSTATUS() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setSTATUS(String STATUS) throws SQLException
  { _struct.setAttribute(4, STATUS); }


  public java.math.BigDecimal getCASH_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setCASH_AMT(java.math.BigDecimal CASH_AMT) throws SQLException
  { _struct.setAttribute(5, CASH_AMT); }


  public java.math.BigDecimal getAVI_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setAVI_AMT(java.math.BigDecimal AVI_AMT) throws SQLException
  { _struct.setAttribute(6, AVI_AMT); }


  public java.math.BigDecimal getVIOLATOR_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setVIOLATOR_ID(java.math.BigDecimal VIOLATOR_ID) throws SQLException
  { _struct.setAttribute(7, VIOLATOR_ID); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(8, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(9, LIC_STATE); }


  public java.math.BigDecimal getONLINE_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setONLINE_FEE(java.math.BigDecimal ONLINE_FEE) throws SQLException
  { _struct.setAttribute(10, ONLINE_FEE); }


  public String getAGENCY_ID() throws SQLException
  { return (String) _struct.getAttribute(11); }

  public void setAGENCY_ID(String AGENCY_ID) throws SQLException
  { _struct.setAttribute(11, AGENCY_ID); }


  public String getAGENCY_NAME() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setAGENCY_NAME(String AGENCY_NAME) throws SQLException
  { _struct.setAttribute(12, AGENCY_NAME); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_UNINVOICED_VIOLS_REC_N" + "(" +
       getVIOLATION_ID() + "," +
       ((getVIOLATION_LOCATION()==null)?"null": "'" + getVIOLATION_LOCATION()+"'" ) + "," +
       ((getFULL_LOCATION_NAME()==null)?"null": "'" + getFULL_LOCATION_NAME()+"'" ) + "," +
       getVIOLATION_DATE_TIME() + "," +
       ((getSTATUS()==null)?"null": "'" + getSTATUS()+"'" ) + "," +
       getCASH_AMT() + "," +
       getAVI_AMT() + "," +
       getVIOLATOR_ID() + "," +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       getONLINE_FEE() + "," +
       ((getAGENCY_ID()==null)?"null": "'" + getAGENCY_ID()+"'" ) + "," +
       ((getAGENCY_NAME()==null)?"null": "'" + getAGENCY_NAME()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
