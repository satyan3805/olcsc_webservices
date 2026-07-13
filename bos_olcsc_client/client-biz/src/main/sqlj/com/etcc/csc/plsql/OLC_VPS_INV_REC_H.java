package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_INV_REC_H implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_INV_REC_H";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,91,91,12,2,2,2,2,2,2,2,2,2,2,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[15];
  static
  {
    _factory[14] = OLC_VPS_INV_ARR_D.getORADataFactory();
  }
  protected static final OLC_VPS_INV_REC_H _OLC_VPS_INV_REC_HFactory = new OLC_VPS_INV_REC_H();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_INV_REC_HFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[15], _sqlType, _factory); }
  public OLC_VPS_INV_REC_H()
  { _init_struct(true); }
  public OLC_VPS_INV_REC_H(String INVOICE_ID, java.sql.Timestamp INVOICE_DATE, java.sql.Timestamp DUE_DATE, String AGENCY_ID, java.math.BigDecimal INVOICE_AMOUNT, java.math.BigDecimal TOLL_AMT, java.math.BigDecimal ADMIN_FEES, java.math.BigDecimal COLLECTION_FEES, java.math.BigDecimal OTHER_FEES, java.math.BigDecimal AMOUNT_PAID, java.math.BigDecimal AMOUNT_PENDING, java.math.BigDecimal AMOUNT_OWED, java.math.BigDecimal AMOUNT_FOR_NEW_PMT, java.math.BigDecimal PAYMENT_MADE_ONLINE, OLC_VPS_INV_ARR_D LINE_ITEMS) throws SQLException
  { _init_struct(true);
    setINVOICE_ID(INVOICE_ID);
    setINVOICE_DATE(INVOICE_DATE);
    setDUE_DATE(DUE_DATE);
    setAGENCY_ID(AGENCY_ID);
    setINVOICE_AMOUNT(INVOICE_AMOUNT);
    setTOLL_AMT(TOLL_AMT);
    setADMIN_FEES(ADMIN_FEES);
    setCOLLECTION_FEES(COLLECTION_FEES);
    setOTHER_FEES(OTHER_FEES);
    setAMOUNT_PAID(AMOUNT_PAID);
    setAMOUNT_PENDING(AMOUNT_PENDING);
    setAMOUNT_OWED(AMOUNT_OWED);
    setAMOUNT_FOR_NEW_PMT(AMOUNT_FOR_NEW_PMT);
    setPAYMENT_MADE_ONLINE(PAYMENT_MADE_ONLINE);
    setLINE_ITEMS(LINE_ITEMS);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_INV_REC_H o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_INV_REC_H();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getINVOICE_ID() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setINVOICE_ID(String INVOICE_ID) throws SQLException
  { _struct.setAttribute(0, INVOICE_ID); }


  public java.sql.Timestamp getINVOICE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setINVOICE_DATE(java.sql.Timestamp INVOICE_DATE) throws SQLException
  { _struct.setAttribute(1, INVOICE_DATE); }


  public java.sql.Timestamp getDUE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(2); }

  public void setDUE_DATE(java.sql.Timestamp DUE_DATE) throws SQLException
  { _struct.setAttribute(2, DUE_DATE); }


  public String getAGENCY_ID() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setAGENCY_ID(String AGENCY_ID) throws SQLException
  { _struct.setAttribute(3, AGENCY_ID); }


  public java.math.BigDecimal getINVOICE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setINVOICE_AMOUNT(java.math.BigDecimal INVOICE_AMOUNT) throws SQLException
  { _struct.setAttribute(4, INVOICE_AMOUNT); }


  public java.math.BigDecimal getTOLL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setTOLL_AMT(java.math.BigDecimal TOLL_AMT) throws SQLException
  { _struct.setAttribute(5, TOLL_AMT); }


  public java.math.BigDecimal getADMIN_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setADMIN_FEES(java.math.BigDecimal ADMIN_FEES) throws SQLException
  { _struct.setAttribute(6, ADMIN_FEES); }


  public java.math.BigDecimal getCOLLECTION_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setCOLLECTION_FEES(java.math.BigDecimal COLLECTION_FEES) throws SQLException
  { _struct.setAttribute(7, COLLECTION_FEES); }


  public java.math.BigDecimal getOTHER_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setOTHER_FEES(java.math.BigDecimal OTHER_FEES) throws SQLException
  { _struct.setAttribute(8, OTHER_FEES); }


  public java.math.BigDecimal getAMOUNT_PAID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setAMOUNT_PAID(java.math.BigDecimal AMOUNT_PAID) throws SQLException
  { _struct.setAttribute(9, AMOUNT_PAID); }


  public java.math.BigDecimal getAMOUNT_PENDING() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setAMOUNT_PENDING(java.math.BigDecimal AMOUNT_PENDING) throws SQLException
  { _struct.setAttribute(10, AMOUNT_PENDING); }


  public java.math.BigDecimal getAMOUNT_OWED() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setAMOUNT_OWED(java.math.BigDecimal AMOUNT_OWED) throws SQLException
  { _struct.setAttribute(11, AMOUNT_OWED); }


  public java.math.BigDecimal getAMOUNT_FOR_NEW_PMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setAMOUNT_FOR_NEW_PMT(java.math.BigDecimal AMOUNT_FOR_NEW_PMT) throws SQLException
  { _struct.setAttribute(12, AMOUNT_FOR_NEW_PMT); }


  public java.math.BigDecimal getPAYMENT_MADE_ONLINE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(13); }

  public void setPAYMENT_MADE_ONLINE(java.math.BigDecimal PAYMENT_MADE_ONLINE) throws SQLException
  { _struct.setAttribute(13, PAYMENT_MADE_ONLINE); }


  public OLC_VPS_INV_ARR_D getLINE_ITEMS() throws SQLException
  { return (OLC_VPS_INV_ARR_D) _struct.getAttribute(14); }

  public void setLINE_ITEMS(OLC_VPS_INV_ARR_D LINE_ITEMS) throws SQLException
  { _struct.setAttribute(14, LINE_ITEMS); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VPS_INV_REC_H" + "(" +
       ((getINVOICE_ID()==null)?"null": "'" + getINVOICE_ID()+"'" ) + "," +
       getINVOICE_DATE() + "," +
       getDUE_DATE() + "," +
       ((getAGENCY_ID()==null)?"null": "'" + getAGENCY_ID()+"'" ) + "," +
       getINVOICE_AMOUNT() + "," +
       getTOLL_AMT() + "," +
       getADMIN_FEES() + "," +
       getCOLLECTION_FEES() + "," +
       getOTHER_FEES() + "," +
       getAMOUNT_PAID() + "," +
       getAMOUNT_PENDING() + "," +
       getAMOUNT_OWED() + "," +
       getAMOUNT_FOR_NEW_PMT() + "," +
       getPAYMENT_MADE_ONLINE() + "," +
       getLINE_ITEMS() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
