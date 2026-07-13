package com.etcc.csc.datatype;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OlcUninvoicedViolsRec implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_UNINVOICED_VIOLS_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,91,12,2,2,2,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[11];
  protected static final OlcUninvoicedViolsRec _OlcUninvoicedViolsRecFactory = new OlcUninvoicedViolsRec();

  public static ORADataFactory getORADataFactory()
  { return _OlcUninvoicedViolsRecFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[11], _sqlType, _factory); }
  public OlcUninvoicedViolsRec()
  { _init_struct(true); }
  public OlcUninvoicedViolsRec(java.math.BigDecimal violationId, String violationLocation, String fullLocationName, java.sql.Timestamp violationDateTime, String status, java.math.BigDecimal cashAmt, java.math.BigDecimal aviAmt, java.math.BigDecimal violatorId, String licPlate, String licState, java.math.BigDecimal onlineFee) throws SQLException
  { _init_struct(true);
    setViolationId(violationId);
    setViolationLocation(violationLocation);
    setFullLocationName(fullLocationName);
    setViolationDateTime(violationDateTime);
    setStatus(status);
    setCashAmt(cashAmt);
    setAviAmt(aviAmt);
    setViolatorId(violatorId);
    setLicPlate(licPlate);
    setLicState(licState);
    setOnlineFee(onlineFee);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OlcUninvoicedViolsRec o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OlcUninvoicedViolsRec();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getViolationId() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setViolationId(java.math.BigDecimal violationId) throws SQLException
  { _struct.setAttribute(0, violationId); }


  public String getViolationLocation() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setViolationLocation(String violationLocation) throws SQLException
  { _struct.setAttribute(1, violationLocation); }


  public String getFullLocationName() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setFullLocationName(String fullLocationName) throws SQLException
  { _struct.setAttribute(2, fullLocationName); }


  public java.sql.Timestamp getViolationDateTime() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(3); }

  public void setViolationDateTime(java.sql.Timestamp violationDateTime) throws SQLException
  { _struct.setAttribute(3, violationDateTime); }


  public String getStatus() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setStatus(String status) throws SQLException
  { _struct.setAttribute(4, status); }


  public java.math.BigDecimal getCashAmt() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setCashAmt(java.math.BigDecimal cashAmt) throws SQLException
  { _struct.setAttribute(5, cashAmt); }


  public java.math.BigDecimal getAviAmt() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setAviAmt(java.math.BigDecimal aviAmt) throws SQLException
  { _struct.setAttribute(6, aviAmt); }


  public java.math.BigDecimal getViolatorId() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setViolatorId(java.math.BigDecimal violatorId) throws SQLException
  { _struct.setAttribute(7, violatorId); }


  public String getLicPlate() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLicPlate(String licPlate) throws SQLException
  { _struct.setAttribute(8, licPlate); }


  public String getLicState() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setLicState(String licState) throws SQLException
  { _struct.setAttribute(9, licState); }


  public java.math.BigDecimal getOnlineFee() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setOnlineFee(java.math.BigDecimal onlineFee) throws SQLException
  { _struct.setAttribute(10, onlineFee); }

}
