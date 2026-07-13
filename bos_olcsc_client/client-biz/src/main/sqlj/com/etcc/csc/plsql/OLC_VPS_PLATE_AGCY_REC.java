package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_PLATE_AGCY_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_PLATE_AGCY_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,2,2,2,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[7];
  protected static final OLC_VPS_PLATE_AGCY_REC _OLC_VPS_PLATE_AGCY_RECFactory = new OLC_VPS_PLATE_AGCY_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_PLATE_AGCY_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[7], _sqlType, _factory); }
  public OLC_VPS_PLATE_AGCY_REC()
  { _init_struct(true); }
  public OLC_VPS_PLATE_AGCY_REC(java.math.BigDecimal AGENCY_ID, String DISCOUNT_RULE, java.math.BigDecimal TTL_AMOUNT_DUE, java.math.BigDecimal TOTAL_EXC_AMOUNT, java.math.BigDecimal TTL_AMOUNT_DUE_AFTER_DISC, String STATUS_REASON, java.math.BigDecimal SERVICE_FEE) throws SQLException
  { _init_struct(true);
    setAGENCY_ID(AGENCY_ID);
    setDISCOUNT_RULE(DISCOUNT_RULE);
    setTTL_AMOUNT_DUE(TTL_AMOUNT_DUE);
    setTOTAL_EXC_AMOUNT(TOTAL_EXC_AMOUNT);
    setTTL_AMOUNT_DUE_AFTER_DISC(TTL_AMOUNT_DUE_AFTER_DISC);
    setSTATUS_REASON(STATUS_REASON);
    setSERVICE_FEE(SERVICE_FEE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_PLATE_AGCY_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_PLATE_AGCY_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getAGENCY_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setAGENCY_ID(java.math.BigDecimal AGENCY_ID) throws SQLException
  { _struct.setAttribute(0, AGENCY_ID); }


  public String getDISCOUNT_RULE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setDISCOUNT_RULE(String DISCOUNT_RULE) throws SQLException
  { _struct.setAttribute(1, DISCOUNT_RULE); }


  public java.math.BigDecimal getTTL_AMOUNT_DUE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setTTL_AMOUNT_DUE(java.math.BigDecimal TTL_AMOUNT_DUE) throws SQLException
  { _struct.setAttribute(2, TTL_AMOUNT_DUE); }


  public java.math.BigDecimal getTOTAL_EXC_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setTOTAL_EXC_AMOUNT(java.math.BigDecimal TOTAL_EXC_AMOUNT) throws SQLException
  { _struct.setAttribute(3, TOTAL_EXC_AMOUNT); }


  public java.math.BigDecimal getTTL_AMOUNT_DUE_AFTER_DISC() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setTTL_AMOUNT_DUE_AFTER_DISC(java.math.BigDecimal TTL_AMOUNT_DUE_AFTER_DISC) throws SQLException
  { _struct.setAttribute(4, TTL_AMOUNT_DUE_AFTER_DISC); }


  public String getSTATUS_REASON() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setSTATUS_REASON(String STATUS_REASON) throws SQLException
  { _struct.setAttribute(5, STATUS_REASON); }


  public java.math.BigDecimal getSERVICE_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setSERVICE_FEE(java.math.BigDecimal SERVICE_FEE) throws SQLException
  { _struct.setAttribute(6, SERVICE_FEE); }

}
