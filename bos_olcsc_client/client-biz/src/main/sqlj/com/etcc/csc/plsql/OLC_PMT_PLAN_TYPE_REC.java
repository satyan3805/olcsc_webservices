package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_PMT_PLAN_TYPE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_PMT_PLAN_TYPE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,12,12,12,2,2,2,2,2,91,2,2,2,91,2,2,12,12,2,12,2003,2003,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[24];
  static
  {
    _factory[21] = OLC_PAYMENT_INFO_ARR.getORADataFactory();
    _factory[22] = OLC_PMT_PLAN_INST_PMT_HST_ARR.getORADataFactory();
    _factory[23] = OLC_PMT_PLAN_INST_TYPE_ARR.getORADataFactory();
  }
  protected static final OLC_PMT_PLAN_TYPE_REC _OLC_PMT_PLAN_TYPE_RECFactory = new OLC_PMT_PLAN_TYPE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_PMT_PLAN_TYPE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[24], _sqlType, _factory); }
  public OLC_PMT_PLAN_TYPE_REC()
  { _init_struct(true); }
  public OLC_PMT_PLAN_TYPE_REC(java.math.BigDecimal PAYMENT_PLAN_ID, java.math.BigDecimal ACCOUNT_ID, String PLAN_STATUS_CODE, String INSTALLMENT_FREQ_TYPE_CODE, String PAYMENT_PLAN_NUMBER, java.math.BigDecimal PLAN_DISCOUNT_AMOUNT, java.math.BigDecimal PLAN_SETTLEMENT_AMOUNT, java.math.BigDecimal PLAN_TOTAL_AMOUNT, java.math.BigDecimal UNCOLLECTABLE_AMOUNT, java.math.BigDecimal NUMBER_OF_INSTALLMENTS, java.sql.Timestamp FIRST_PAYMENT_DATE, java.math.BigDecimal REBILL_FAILED_COUNT, java.math.BigDecimal OPEN_AMOUNT, java.math.BigDecimal PAID_AMOUNT, java.sql.Timestamp STATUS_DATE, java.math.BigDecimal AGCY_ID, java.math.BigDecimal ACCOUNT_BILLING_METHOD_ID, String BILLING_FULL_NAME, String BILLING_PAYMENT_FORM, java.math.BigDecimal BILLING_EFT_ACT_TYPE_ID, String BILLING_CARD_TYPE, OLC_PAYMENT_INFO_ARR PMT_PLAN_PMT_TYPE_ARR, OLC_PMT_PLAN_INST_PMT_HST_ARR PMT_PLAN_HIST_TYPE_ARR, OLC_PMT_PLAN_INST_TYPE_ARR PMT_PLAN_INST_TYPE_ARR) throws SQLException
  { _init_struct(true);
    setPAYMENT_PLAN_ID(PAYMENT_PLAN_ID);
    setACCOUNT_ID(ACCOUNT_ID);
    setPLAN_STATUS_CODE(PLAN_STATUS_CODE);
    setINSTALLMENT_FREQ_TYPE_CODE(INSTALLMENT_FREQ_TYPE_CODE);
    setPAYMENT_PLAN_NUMBER(PAYMENT_PLAN_NUMBER);
    setPLAN_DISCOUNT_AMOUNT(PLAN_DISCOUNT_AMOUNT);
    setPLAN_SETTLEMENT_AMOUNT(PLAN_SETTLEMENT_AMOUNT);
    setPLAN_TOTAL_AMOUNT(PLAN_TOTAL_AMOUNT);
    setUNCOLLECTABLE_AMOUNT(UNCOLLECTABLE_AMOUNT);
    setNUMBER_OF_INSTALLMENTS(NUMBER_OF_INSTALLMENTS);
    setFIRST_PAYMENT_DATE(FIRST_PAYMENT_DATE);
    setREBILL_FAILED_COUNT(REBILL_FAILED_COUNT);
    setOPEN_AMOUNT(OPEN_AMOUNT);
    setPAID_AMOUNT(PAID_AMOUNT);
    setSTATUS_DATE(STATUS_DATE);
    setAGCY_ID(AGCY_ID);
    setACCOUNT_BILLING_METHOD_ID(ACCOUNT_BILLING_METHOD_ID);
    setBILLING_FULL_NAME(BILLING_FULL_NAME);
    setBILLING_PAYMENT_FORM(BILLING_PAYMENT_FORM);
    setBILLING_EFT_ACT_TYPE_ID(BILLING_EFT_ACT_TYPE_ID);
    setBILLING_CARD_TYPE(BILLING_CARD_TYPE);
    setPMT_PLAN_PMT_TYPE_ARR(PMT_PLAN_PMT_TYPE_ARR);
    setPMT_PLAN_HIST_TYPE_ARR(PMT_PLAN_HIST_TYPE_ARR);
    setPMT_PLAN_INST_TYPE_ARR(PMT_PLAN_INST_TYPE_ARR);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_PMT_PLAN_TYPE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_PMT_PLAN_TYPE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getPAYMENT_PLAN_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setPAYMENT_PLAN_ID(java.math.BigDecimal PAYMENT_PLAN_ID) throws SQLException
  { _struct.setAttribute(0, PAYMENT_PLAN_ID); }


  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(1, ACCOUNT_ID); }


  public String getPLAN_STATUS_CODE() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setPLAN_STATUS_CODE(String PLAN_STATUS_CODE) throws SQLException
  { _struct.setAttribute(2, PLAN_STATUS_CODE); }


  public String getINSTALLMENT_FREQ_TYPE_CODE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setINSTALLMENT_FREQ_TYPE_CODE(String INSTALLMENT_FREQ_TYPE_CODE) throws SQLException
  { _struct.setAttribute(3, INSTALLMENT_FREQ_TYPE_CODE); }


  public String getPAYMENT_PLAN_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setPAYMENT_PLAN_NUMBER(String PAYMENT_PLAN_NUMBER) throws SQLException
  { _struct.setAttribute(4, PAYMENT_PLAN_NUMBER); }


  public java.math.BigDecimal getPLAN_DISCOUNT_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setPLAN_DISCOUNT_AMOUNT(java.math.BigDecimal PLAN_DISCOUNT_AMOUNT) throws SQLException
  { _struct.setAttribute(5, PLAN_DISCOUNT_AMOUNT); }


  public java.math.BigDecimal getPLAN_SETTLEMENT_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setPLAN_SETTLEMENT_AMOUNT(java.math.BigDecimal PLAN_SETTLEMENT_AMOUNT) throws SQLException
  { _struct.setAttribute(6, PLAN_SETTLEMENT_AMOUNT); }


  public java.math.BigDecimal getPLAN_TOTAL_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setPLAN_TOTAL_AMOUNT(java.math.BigDecimal PLAN_TOTAL_AMOUNT) throws SQLException
  { _struct.setAttribute(7, PLAN_TOTAL_AMOUNT); }


  public java.math.BigDecimal getUNCOLLECTABLE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setUNCOLLECTABLE_AMOUNT(java.math.BigDecimal UNCOLLECTABLE_AMOUNT) throws SQLException
  { _struct.setAttribute(8, UNCOLLECTABLE_AMOUNT); }


  public java.math.BigDecimal getNUMBER_OF_INSTALLMENTS() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setNUMBER_OF_INSTALLMENTS(java.math.BigDecimal NUMBER_OF_INSTALLMENTS) throws SQLException
  { _struct.setAttribute(9, NUMBER_OF_INSTALLMENTS); }


  public java.sql.Timestamp getFIRST_PAYMENT_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(10); }

  public void setFIRST_PAYMENT_DATE(java.sql.Timestamp FIRST_PAYMENT_DATE) throws SQLException
  { _struct.setAttribute(10, FIRST_PAYMENT_DATE); }


  public java.math.BigDecimal getREBILL_FAILED_COUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setREBILL_FAILED_COUNT(java.math.BigDecimal REBILL_FAILED_COUNT) throws SQLException
  { _struct.setAttribute(11, REBILL_FAILED_COUNT); }


  public java.math.BigDecimal getOPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setOPEN_AMOUNT(java.math.BigDecimal OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(12, OPEN_AMOUNT); }


  public java.math.BigDecimal getPAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(13); }

  public void setPAID_AMOUNT(java.math.BigDecimal PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(13, PAID_AMOUNT); }


  public java.sql.Timestamp getSTATUS_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(14); }

  public void setSTATUS_DATE(java.sql.Timestamp STATUS_DATE) throws SQLException
  { _struct.setAttribute(14, STATUS_DATE); }


  public java.math.BigDecimal getAGCY_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(15); }

  public void setAGCY_ID(java.math.BigDecimal AGCY_ID) throws SQLException
  { _struct.setAttribute(15, AGCY_ID); }


  public java.math.BigDecimal getACCOUNT_BILLING_METHOD_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(16); }

  public void setACCOUNT_BILLING_METHOD_ID(java.math.BigDecimal ACCOUNT_BILLING_METHOD_ID) throws SQLException
  { _struct.setAttribute(16, ACCOUNT_BILLING_METHOD_ID); }


  public String getBILLING_FULL_NAME() throws SQLException
  { return (String) _struct.getAttribute(17); }

  public void setBILLING_FULL_NAME(String BILLING_FULL_NAME) throws SQLException
  { _struct.setAttribute(17, BILLING_FULL_NAME); }


  public String getBILLING_PAYMENT_FORM() throws SQLException
  { return (String) _struct.getAttribute(18); }

  public void setBILLING_PAYMENT_FORM(String BILLING_PAYMENT_FORM) throws SQLException
  { _struct.setAttribute(18, BILLING_PAYMENT_FORM); }


  public java.math.BigDecimal getBILLING_EFT_ACT_TYPE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(19); }

  public void setBILLING_EFT_ACT_TYPE_ID(java.math.BigDecimal BILLING_EFT_ACT_TYPE_ID) throws SQLException
  { _struct.setAttribute(19, BILLING_EFT_ACT_TYPE_ID); }


  public String getBILLING_CARD_TYPE() throws SQLException
  { return (String) _struct.getAttribute(20); }

  public void setBILLING_CARD_TYPE(String BILLING_CARD_TYPE) throws SQLException
  { _struct.setAttribute(20, BILLING_CARD_TYPE); }


  public OLC_PAYMENT_INFO_ARR getPMT_PLAN_PMT_TYPE_ARR() throws SQLException
  { return (OLC_PAYMENT_INFO_ARR) _struct.getAttribute(21); }

  public void setPMT_PLAN_PMT_TYPE_ARR(OLC_PAYMENT_INFO_ARR PMT_PLAN_PMT_TYPE_ARR) throws SQLException
  { _struct.setAttribute(21, PMT_PLAN_PMT_TYPE_ARR); }


  public OLC_PMT_PLAN_INST_PMT_HST_ARR getPMT_PLAN_HIST_TYPE_ARR() throws SQLException
  { return (OLC_PMT_PLAN_INST_PMT_HST_ARR) _struct.getAttribute(22); }

  public void setPMT_PLAN_HIST_TYPE_ARR(OLC_PMT_PLAN_INST_PMT_HST_ARR PMT_PLAN_HIST_TYPE_ARR) throws SQLException
  { _struct.setAttribute(22, PMT_PLAN_HIST_TYPE_ARR); }


  public OLC_PMT_PLAN_INST_TYPE_ARR getPMT_PLAN_INST_TYPE_ARR() throws SQLException
  { return (OLC_PMT_PLAN_INST_TYPE_ARR) _struct.getAttribute(23); }

  public void setPMT_PLAN_INST_TYPE_ARR(OLC_PMT_PLAN_INST_TYPE_ARR PMT_PLAN_INST_TYPE_ARR) throws SQLException
  { _struct.setAttribute(23, PMT_PLAN_INST_TYPE_ARR); }

}
