package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_CITATION_INFO_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_CITATION_INFO_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,12,12,12,12,12,12,12,12,91,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[13];
  protected static final OLC_CITATION_INFO_REC _OLC_CITATION_INFO_RECFactory = new OLC_CITATION_INFO_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_CITATION_INFO_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[13], _sqlType, _factory); }
  public OLC_CITATION_INFO_REC()
  { _init_struct(true); }
  public OLC_CITATION_INFO_REC(java.math.BigDecimal INVOICE_ID, java.math.BigDecimal CITATION_ID, java.math.BigDecimal COURT_ID, String COURT_NAME, String JUDGE, String ADDRESS1, String ADDRESS2, String CITY, String STATE, String ZIP_CODE, String PLUS4, java.sql.Timestamp COURT_DATE, String PHONE_NUMBER) throws SQLException
  { _init_struct(true);
    setINVOICE_ID(INVOICE_ID);
    setCITATION_ID(CITATION_ID);
    setCOURT_ID(COURT_ID);
    setCOURT_NAME(COURT_NAME);
    setJUDGE(JUDGE);
    setADDRESS1(ADDRESS1);
    setADDRESS2(ADDRESS2);
    setCITY(CITY);
    setSTATE(STATE);
    setZIP_CODE(ZIP_CODE);
    setPLUS4(PLUS4);
    setCOURT_DATE(COURT_DATE);
    setPHONE_NUMBER(PHONE_NUMBER);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_CITATION_INFO_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_CITATION_INFO_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getINVOICE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setINVOICE_ID(java.math.BigDecimal INVOICE_ID) throws SQLException
  { _struct.setAttribute(0, INVOICE_ID); }


  public java.math.BigDecimal getCITATION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setCITATION_ID(java.math.BigDecimal CITATION_ID) throws SQLException
  { _struct.setAttribute(1, CITATION_ID); }


  public java.math.BigDecimal getCOURT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setCOURT_ID(java.math.BigDecimal COURT_ID) throws SQLException
  { _struct.setAttribute(2, COURT_ID); }


  public String getCOURT_NAME() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setCOURT_NAME(String COURT_NAME) throws SQLException
  { _struct.setAttribute(3, COURT_NAME); }


  public String getJUDGE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setJUDGE(String JUDGE) throws SQLException
  { _struct.setAttribute(4, JUDGE); }


  public String getADDRESS1() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setADDRESS1(String ADDRESS1) throws SQLException
  { _struct.setAttribute(5, ADDRESS1); }


  public String getADDRESS2() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setADDRESS2(String ADDRESS2) throws SQLException
  { _struct.setAttribute(6, ADDRESS2); }


  public String getCITY() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setCITY(String CITY) throws SQLException
  { _struct.setAttribute(7, CITY); }


  public String getSTATE() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setSTATE(String STATE) throws SQLException
  { _struct.setAttribute(8, STATE); }


  public String getZIP_CODE() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setZIP_CODE(String ZIP_CODE) throws SQLException
  { _struct.setAttribute(9, ZIP_CODE); }


  public String getPLUS4() throws SQLException
  { return (String) _struct.getAttribute(10); }

  public void setPLUS4(String PLUS4) throws SQLException
  { _struct.setAttribute(10, PLUS4); }


  public java.sql.Timestamp getCOURT_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(11); }

  public void setCOURT_DATE(java.sql.Timestamp COURT_DATE) throws SQLException
  { _struct.setAttribute(11, COURT_DATE); }


  public String getPHONE_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setPHONE_NUMBER(String PHONE_NUMBER) throws SQLException
  { _struct.setAttribute(12, PHONE_NUMBER); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_CITATION_INFO_REC" + "(" +
       getINVOICE_ID() + "," +
       getCITATION_ID() + "," +
       getCOURT_ID() + "," +
       ((getCOURT_NAME()==null)?"null": "'" + getCOURT_NAME()+"'" ) + "," +
       ((getJUDGE()==null)?"null": "'" + getJUDGE()+"'" ) + "," +
       ((getADDRESS1()==null)?"null": "'" + getADDRESS1()+"'" ) + "," +
       ((getADDRESS2()==null)?"null": "'" + getADDRESS2()+"'" ) + "," +
       ((getCITY()==null)?"null": "'" + getCITY()+"'" ) + "," +
       ((getSTATE()==null)?"null": "'" + getSTATE()+"'" ) + "," +
       ((getZIP_CODE()==null)?"null": "'" + getZIP_CODE()+"'" ) + "," +
       ((getPLUS4()==null)?"null": "'" + getPLUS4()+"'" ) + "," +
       getCOURT_DATE() + "," +
       ((getPHONE_NUMBER()==null)?"null": "'" + getPHONE_NUMBER()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
