package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VIOLATION_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VIOLATION_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,91,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  protected static final OLC_VIOLATION_REC _OLC_VIOLATION_RECFactory = new OLC_VIOLATION_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VIOLATION_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public OLC_VIOLATION_REC()
  { _init_struct(true); }
  public OLC_VIOLATION_REC(java.math.BigDecimal VIOLATION_ID, java.sql.Timestamp VIOLATION_TIME, String LANE_NAME, String FULL_LANE_NAME, String CITATION_NBR) throws SQLException
  { _init_struct(true);
    setVIOLATION_ID(VIOLATION_ID);
    setVIOLATION_TIME(VIOLATION_TIME);
    setLANE_NAME(LANE_NAME);
    setFULL_LANE_NAME(FULL_LANE_NAME);
    setCITATION_NBR(CITATION_NBR);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VIOLATION_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VIOLATION_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getVIOLATION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setVIOLATION_ID(java.math.BigDecimal VIOLATION_ID) throws SQLException
  { _struct.setAttribute(0, VIOLATION_ID); }


  public java.sql.Timestamp getVIOLATION_TIME() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setVIOLATION_TIME(java.sql.Timestamp VIOLATION_TIME) throws SQLException
  { _struct.setAttribute(1, VIOLATION_TIME); }


  public String getLANE_NAME() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLANE_NAME(String LANE_NAME) throws SQLException
  { _struct.setAttribute(2, LANE_NAME); }


  public String getFULL_LANE_NAME() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setFULL_LANE_NAME(String FULL_LANE_NAME) throws SQLException
  { _struct.setAttribute(3, FULL_LANE_NAME); }


  public String getCITATION_NBR() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setCITATION_NBR(String CITATION_NBR) throws SQLException
  { _struct.setAttribute(4, CITATION_NBR); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VIOLATION_REC" + "(" +
       getVIOLATION_ID() + "," +
       getVIOLATION_TIME() + "," +
       ((getLANE_NAME()==null)?"null": "'" + getLANE_NAME()+"'" ) + "," +
       ((getFULL_LANE_NAME()==null)?"null": "'" + getFULL_LANE_NAME()+"'" ) + "," +
       ((getCITATION_NBR()==null)?"null": "'" + getCITATION_NBR()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
