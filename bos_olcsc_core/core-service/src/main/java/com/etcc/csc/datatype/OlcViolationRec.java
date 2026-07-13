package com.etcc.csc.datatype;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OlcViolationRec implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VIOLATION_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,91,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final OlcViolationRec _OlcViolationRecFactory = new OlcViolationRec();

  public static ORADataFactory getORADataFactory()
  { return _OlcViolationRecFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public OlcViolationRec()
  { _init_struct(true); }
  public OlcViolationRec(java.math.BigDecimal violationId, java.sql.Timestamp violationTime, String laneName) throws SQLException
  { _init_struct(true);
    setViolationId(violationId);
    setViolationTime(violationTime);
    setLaneName(laneName);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OlcViolationRec o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OlcViolationRec();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getViolationId() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setViolationId(java.math.BigDecimal violationId) throws SQLException
  { _struct.setAttribute(0, violationId); }


  public java.sql.Timestamp getViolationTime() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setViolationTime(java.sql.Timestamp violationTime) throws SQLException
  { _struct.setAttribute(1, violationTime); }


  public String getLaneName() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLaneName(String laneName) throws SQLException
  { _struct.setAttribute(2, laneName); }

}
