package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_INV_TYPE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_INV_TYPE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,91,12,12,91,12,12,12,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2003,12,2,2,2,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[30];
  static
  {
    _factory[24] = OLC_VPS_INV_ARR_D.getORADataFactory();
    _factory[29] = OLC_OPEN_FEE_ARR.getORADataFactory();
  }
  protected static final OLC_VPS_INV_TYPE_REC _OLC_VPS_INV_TYPE_RECFactory = new OLC_VPS_INV_TYPE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_INV_TYPE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[30], _sqlType, _factory); }
  public OLC_VPS_INV_TYPE_REC()
  { _init_struct(true); }
  public OLC_VPS_INV_TYPE_REC(java.math.BigDecimal INVOICE_ID, String INVOICE_NUMBER, java.sql.Timestamp INVOICE_DATE, String LIC_PLATE, String LIC_STATE, java.sql.Timestamp DUE_DATE, String AGENCY_ID, String AGENCY_NAME, String INV_ESC_ST_CD, java.math.BigDecimal OPEN_TOLL_AMOUNT, java.math.BigDecimal ORIG_TOLL_AMOUNT, java.math.BigDecimal OPEN_ADMIN_FEES, java.math.BigDecimal ORIG_ADMIN_FEES, java.math.BigDecimal OPEN_COLLECTION_FEES, java.math.BigDecimal ORIG_COLLECTION_FEES, java.math.BigDecimal OPEN_OTHER_FEES, java.math.BigDecimal ORIG_OTHER_FEES, java.math.BigDecimal INVOICE_AMT, java.math.BigDecimal INV_PAID_AMOUNT, java.math.BigDecimal INV_OPEN_AMOUNT, java.math.BigDecimal INV_EXC_AMOUNT, java.math.BigDecimal INV_FEE_OPEN_AMT, java.math.BigDecimal INV_FEE_EXC_AMT, java.math.BigDecimal INV_DUE_AFTER_EXC_AMT, OLC_VPS_INV_ARR_D LINE_ITEMS, String DISCOUNT_RULE, java.math.BigDecimal ACCT_CONV_EXC_AMT, java.math.BigDecimal INV_DUE_AFTER_ACCT_CONV_EX_AMT, java.math.BigDecimal INVOICE_FINE_AMOUNT, OLC_OPEN_FEE_ARR OPEN_FEE_ARR) throws SQLException
  { _init_struct(true);
    setINVOICE_ID(INVOICE_ID);
    setINVOICE_NUMBER(INVOICE_NUMBER);
    setINVOICE_DATE(INVOICE_DATE);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setDUE_DATE(DUE_DATE);
    setAGENCY_ID(AGENCY_ID);
    setAGENCY_NAME(AGENCY_NAME);
    setINV_ESC_ST_CD(INV_ESC_ST_CD);
    setOPEN_TOLL_AMOUNT(OPEN_TOLL_AMOUNT);
    setORIG_TOLL_AMOUNT(ORIG_TOLL_AMOUNT);
    setOPEN_ADMIN_FEES(OPEN_ADMIN_FEES);
    setORIG_ADMIN_FEES(ORIG_ADMIN_FEES);
    setOPEN_COLLECTION_FEES(OPEN_COLLECTION_FEES);
    setORIG_COLLECTION_FEES(ORIG_COLLECTION_FEES);
    setOPEN_OTHER_FEES(OPEN_OTHER_FEES);
    setORIG_OTHER_FEES(ORIG_OTHER_FEES);
    setINVOICE_AMT(INVOICE_AMT);
    setINV_PAID_AMOUNT(INV_PAID_AMOUNT);
    setINV_OPEN_AMOUNT(INV_OPEN_AMOUNT);
    setINV_EXC_AMOUNT(INV_EXC_AMOUNT);
    setINV_FEE_OPEN_AMT(INV_FEE_OPEN_AMT);
    setINV_FEE_EXC_AMT(INV_FEE_EXC_AMT);
    setINV_DUE_AFTER_EXC_AMT(INV_DUE_AFTER_EXC_AMT);
    setLINE_ITEMS(LINE_ITEMS);
    setDISCOUNT_RULE(DISCOUNT_RULE);
    setACCT_CONV_EXC_AMT(ACCT_CONV_EXC_AMT);
    setINV_DUE_AFTER_ACCT_CONV_EX_AMT(INV_DUE_AFTER_ACCT_CONV_EX_AMT);
    setINVOICE_FINE_AMOUNT(INVOICE_FINE_AMOUNT);
    setOPEN_FEE_ARR(OPEN_FEE_ARR);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_INV_TYPE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_INV_TYPE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getINVOICE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setINVOICE_ID(java.math.BigDecimal INVOICE_ID) throws SQLException
  { _struct.setAttribute(0, INVOICE_ID); }


  public String getINVOICE_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setINVOICE_NUMBER(String INVOICE_NUMBER) throws SQLException
  { _struct.setAttribute(1, INVOICE_NUMBER); }


  public java.sql.Timestamp getINVOICE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(2); }

  public void setINVOICE_DATE(java.sql.Timestamp INVOICE_DATE) throws SQLException
  { _struct.setAttribute(2, INVOICE_DATE); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(3, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(4, LIC_STATE); }


  public java.sql.Timestamp getDUE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(5); }

  public void setDUE_DATE(java.sql.Timestamp DUE_DATE) throws SQLException
  { _struct.setAttribute(5, DUE_DATE); }


  public String getAGENCY_ID() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setAGENCY_ID(String AGENCY_ID) throws SQLException
  { _struct.setAttribute(6, AGENCY_ID); }


  public String getAGENCY_NAME() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setAGENCY_NAME(String AGENCY_NAME) throws SQLException
  { _struct.setAttribute(7, AGENCY_NAME); }


  public String getINV_ESC_ST_CD() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setINV_ESC_ST_CD(String INV_ESC_ST_CD) throws SQLException
  { _struct.setAttribute(8, INV_ESC_ST_CD); }


  public java.math.BigDecimal getOPEN_TOLL_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setOPEN_TOLL_AMOUNT(java.math.BigDecimal OPEN_TOLL_AMOUNT) throws SQLException
  { _struct.setAttribute(9, OPEN_TOLL_AMOUNT); }


  public java.math.BigDecimal getORIG_TOLL_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setORIG_TOLL_AMOUNT(java.math.BigDecimal ORIG_TOLL_AMOUNT) throws SQLException
  { _struct.setAttribute(10, ORIG_TOLL_AMOUNT); }


  public java.math.BigDecimal getOPEN_ADMIN_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setOPEN_ADMIN_FEES(java.math.BigDecimal OPEN_ADMIN_FEES) throws SQLException
  { _struct.setAttribute(11, OPEN_ADMIN_FEES); }


  public java.math.BigDecimal getORIG_ADMIN_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setORIG_ADMIN_FEES(java.math.BigDecimal ORIG_ADMIN_FEES) throws SQLException
  { _struct.setAttribute(12, ORIG_ADMIN_FEES); }


  public java.math.BigDecimal getOPEN_COLLECTION_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(13); }

  public void setOPEN_COLLECTION_FEES(java.math.BigDecimal OPEN_COLLECTION_FEES) throws SQLException
  { _struct.setAttribute(13, OPEN_COLLECTION_FEES); }


  public java.math.BigDecimal getORIG_COLLECTION_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(14); }

  public void setORIG_COLLECTION_FEES(java.math.BigDecimal ORIG_COLLECTION_FEES) throws SQLException
  { _struct.setAttribute(14, ORIG_COLLECTION_FEES); }


  public java.math.BigDecimal getOPEN_OTHER_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(15); }

  public void setOPEN_OTHER_FEES(java.math.BigDecimal OPEN_OTHER_FEES) throws SQLException
  { _struct.setAttribute(15, OPEN_OTHER_FEES); }


  public java.math.BigDecimal getORIG_OTHER_FEES() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(16); }

  public void setORIG_OTHER_FEES(java.math.BigDecimal ORIG_OTHER_FEES) throws SQLException
  { _struct.setAttribute(16, ORIG_OTHER_FEES); }


  public java.math.BigDecimal getINVOICE_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(17); }

  public void setINVOICE_AMT(java.math.BigDecimal INVOICE_AMT) throws SQLException
  { _struct.setAttribute(17, INVOICE_AMT); }


  public java.math.BigDecimal getINV_PAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(18); }

  public void setINV_PAID_AMOUNT(java.math.BigDecimal INV_PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(18, INV_PAID_AMOUNT); }


  public java.math.BigDecimal getINV_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(19); }

  public void setINV_OPEN_AMOUNT(java.math.BigDecimal INV_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(19, INV_OPEN_AMOUNT); }


  public java.math.BigDecimal getINV_EXC_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(20); }

  public void setINV_EXC_AMOUNT(java.math.BigDecimal INV_EXC_AMOUNT) throws SQLException
  { _struct.setAttribute(20, INV_EXC_AMOUNT); }


  public java.math.BigDecimal getINV_FEE_OPEN_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(21); }

  public void setINV_FEE_OPEN_AMT(java.math.BigDecimal INV_FEE_OPEN_AMT) throws SQLException
  { _struct.setAttribute(21, INV_FEE_OPEN_AMT); }


  public java.math.BigDecimal getINV_FEE_EXC_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(22); }

  public void setINV_FEE_EXC_AMT(java.math.BigDecimal INV_FEE_EXC_AMT) throws SQLException
  { _struct.setAttribute(22, INV_FEE_EXC_AMT); }


  public java.math.BigDecimal getINV_DUE_AFTER_EXC_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(23); }

  public void setINV_DUE_AFTER_EXC_AMT(java.math.BigDecimal INV_DUE_AFTER_EXC_AMT) throws SQLException
  { _struct.setAttribute(23, INV_DUE_AFTER_EXC_AMT); }


  public OLC_VPS_INV_ARR_D getLINE_ITEMS() throws SQLException
  { return (OLC_VPS_INV_ARR_D) _struct.getAttribute(24); }

  public void setLINE_ITEMS(OLC_VPS_INV_ARR_D LINE_ITEMS) throws SQLException
  { _struct.setAttribute(24, LINE_ITEMS); }


  public String getDISCOUNT_RULE() throws SQLException
  { return (String) _struct.getAttribute(25); }

  public void setDISCOUNT_RULE(String DISCOUNT_RULE) throws SQLException
  { _struct.setAttribute(25, DISCOUNT_RULE); }


  public java.math.BigDecimal getACCT_CONV_EXC_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(26); }

  public void setACCT_CONV_EXC_AMT(java.math.BigDecimal ACCT_CONV_EXC_AMT) throws SQLException
  { _struct.setAttribute(26, ACCT_CONV_EXC_AMT); }


  public java.math.BigDecimal getINV_DUE_AFTER_ACCT_CONV_EX_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(27); }

  public void setINV_DUE_AFTER_ACCT_CONV_EX_AMT(java.math.BigDecimal INV_DUE_AFTER_ACCT_CONV_EX_AMT) throws SQLException
  { _struct.setAttribute(27, INV_DUE_AFTER_ACCT_CONV_EX_AMT); }


  public java.math.BigDecimal getINVOICE_FINE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(28); }

  public void setINVOICE_FINE_AMOUNT(java.math.BigDecimal INVOICE_FINE_AMOUNT) throws SQLException
  { _struct.setAttribute(28, INVOICE_FINE_AMOUNT); }


  public OLC_OPEN_FEE_ARR getOPEN_FEE_ARR() throws SQLException
  { return (OLC_OPEN_FEE_ARR) _struct.getAttribute(29); }

  public void setOPEN_FEE_ARR(OLC_OPEN_FEE_ARR OPEN_FEE_ARR) throws SQLException
  { _struct.setAttribute(29, OPEN_FEE_ARR); }

}
