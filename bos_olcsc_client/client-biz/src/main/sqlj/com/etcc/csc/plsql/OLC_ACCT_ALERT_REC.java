package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCT_ALERT_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCT_ALERT_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  static
  {
    _factory[2] = OLC_CITATION_INFO_ARR.getORADataFactory();
  }
  protected static final OLC_ACCT_ALERT_REC _OLC_ACCT_ALERT_RECFactory = new OLC_ACCT_ALERT_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCT_ALERT_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public OLC_ACCT_ALERT_REC()
  { _init_struct(true); }
  public OLC_ACCT_ALERT_REC(String ALERT_MSG, java.math.BigDecimal INVOICE_ID, OLC_CITATION_INFO_ARR INFO_CITATION_ITEMS_ARR) throws SQLException
  { _init_struct(true);
    setALERT_MSG(ALERT_MSG);
    setINVOICE_ID(INVOICE_ID);
    setINFO_CITATION_ITEMS_ARR(INFO_CITATION_ITEMS_ARR);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCT_ALERT_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCT_ALERT_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getALERT_MSG() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setALERT_MSG(String ALERT_MSG) throws SQLException
  { _struct.setAttribute(0, ALERT_MSG); }


  public java.math.BigDecimal getINVOICE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setINVOICE_ID(java.math.BigDecimal INVOICE_ID) throws SQLException
  { _struct.setAttribute(1, INVOICE_ID); }


  public OLC_CITATION_INFO_ARR getINFO_CITATION_ITEMS_ARR() throws SQLException
  { return (OLC_CITATION_INFO_ARR) _struct.getAttribute(2); }

  public void setINFO_CITATION_ITEMS_ARR(OLC_CITATION_INFO_ARR INFO_CITATION_ITEMS_ARR) throws SQLException
  { _struct.setAttribute(2, INFO_CITATION_ITEMS_ARR); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ACCT_ALERT_REC" + "(" +
       ((getALERT_MSG()==null)?"null": "'" + getALERT_MSG()+"'" ) + "," +
       getINVOICE_ID() + "," +
       getINFO_CITATION_ITEMS_ARR() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
