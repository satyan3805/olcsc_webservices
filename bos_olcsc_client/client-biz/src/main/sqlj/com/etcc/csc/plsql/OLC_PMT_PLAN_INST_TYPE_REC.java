package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_PMT_PLAN_INST_TYPE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_PMT_PLAN_INST_TYPE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,2,2,12,2,91,2,2,2,2,91,2,2,12,91,12,91,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[22];
  protected static final OLC_PMT_PLAN_INST_TYPE_REC _OLC_PMT_PLAN_INST_TYPE_RECFactory = new OLC_PMT_PLAN_INST_TYPE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_PMT_PLAN_INST_TYPE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[22], _sqlType, _factory); }
  public OLC_PMT_PLAN_INST_TYPE_REC()
  { _init_struct(true); }
  public OLC_PMT_PLAN_INST_TYPE_REC(java.math.BigDecimal PAYMENT_PLAN_INSTALLMENT_ID, String PLAN_INSTALLMENT_STATUS_CODE, java.math.BigDecimal PAYMENT_PLAN_ID, java.math.BigDecimal ITEMS_PAYMENT_ID, String IS_FUTURE, java.math.BigDecimal INSTALLMENT_NUMBER, java.sql.Timestamp DUE_DATE, java.math.BigDecimal INSTALLMENT_AMOUNT, java.math.BigDecimal OPEN_AMOUNT, java.math.BigDecimal PAID_AMOUNT, java.math.BigDecimal RECORD_VERSION, java.sql.Timestamp STATUS_DATE, java.math.BigDecimal TOLL_AMOUNT, java.math.BigDecimal FEE_AMOUNT, String CREATED_BY, java.sql.Timestamp DATE_CREATED, String MODIFIED_BY, java.sql.Timestamp DATE_MODIFIED, java.math.BigDecimal PLAN_INSTALLMENT_TYPE_ID, java.math.BigDecimal LAST_CART_ID, java.math.BigDecimal UNCOLLECTABLE_AMOUNT, java.math.BigDecimal CANCELLED_AMOUNT) throws SQLException
  { _init_struct(true);
    setPAYMENT_PLAN_INSTALLMENT_ID(PAYMENT_PLAN_INSTALLMENT_ID);
    setPLAN_INSTALLMENT_STATUS_CODE(PLAN_INSTALLMENT_STATUS_CODE);
    setPAYMENT_PLAN_ID(PAYMENT_PLAN_ID);
    setITEMS_PAYMENT_ID(ITEMS_PAYMENT_ID);
    setIS_FUTURE(IS_FUTURE);
    setINSTALLMENT_NUMBER(INSTALLMENT_NUMBER);
    setDUE_DATE(DUE_DATE);
    setINSTALLMENT_AMOUNT(INSTALLMENT_AMOUNT);
    setOPEN_AMOUNT(OPEN_AMOUNT);
    setPAID_AMOUNT(PAID_AMOUNT);
    setRECORD_VERSION(RECORD_VERSION);
    setSTATUS_DATE(STATUS_DATE);
    setTOLL_AMOUNT(TOLL_AMOUNT);
    setFEE_AMOUNT(FEE_AMOUNT);
    setCREATED_BY(CREATED_BY);
    setDATE_CREATED(DATE_CREATED);
    setMODIFIED_BY(MODIFIED_BY);
    setDATE_MODIFIED(DATE_MODIFIED);
    setPLAN_INSTALLMENT_TYPE_ID(PLAN_INSTALLMENT_TYPE_ID);
    setLAST_CART_ID(LAST_CART_ID);
    setUNCOLLECTABLE_AMOUNT(UNCOLLECTABLE_AMOUNT);
    setCANCELLED_AMOUNT(CANCELLED_AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_PMT_PLAN_INST_TYPE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_PMT_PLAN_INST_TYPE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getPAYMENT_PLAN_INSTALLMENT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setPAYMENT_PLAN_INSTALLMENT_ID(java.math.BigDecimal PAYMENT_PLAN_INSTALLMENT_ID) throws SQLException
  { _struct.setAttribute(0, PAYMENT_PLAN_INSTALLMENT_ID); }


  public String getPLAN_INSTALLMENT_STATUS_CODE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setPLAN_INSTALLMENT_STATUS_CODE(String PLAN_INSTALLMENT_STATUS_CODE) throws SQLException
  { _struct.setAttribute(1, PLAN_INSTALLMENT_STATUS_CODE); }


  public java.math.BigDecimal getPAYMENT_PLAN_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setPAYMENT_PLAN_ID(java.math.BigDecimal PAYMENT_PLAN_ID) throws SQLException
  { _struct.setAttribute(2, PAYMENT_PLAN_ID); }


  public java.math.BigDecimal getITEMS_PAYMENT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setITEMS_PAYMENT_ID(java.math.BigDecimal ITEMS_PAYMENT_ID) throws SQLException
  { _struct.setAttribute(3, ITEMS_PAYMENT_ID); }


  public String getIS_FUTURE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setIS_FUTURE(String IS_FUTURE) throws SQLException
  { _struct.setAttribute(4, IS_FUTURE); }


  public java.math.BigDecimal getINSTALLMENT_NUMBER() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setINSTALLMENT_NUMBER(java.math.BigDecimal INSTALLMENT_NUMBER) throws SQLException
  { _struct.setAttribute(5, INSTALLMENT_NUMBER); }


  public java.sql.Timestamp getDUE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(6); }

  public void setDUE_DATE(java.sql.Timestamp DUE_DATE) throws SQLException
  { _struct.setAttribute(6, DUE_DATE); }


  public java.math.BigDecimal getINSTALLMENT_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setINSTALLMENT_AMOUNT(java.math.BigDecimal INSTALLMENT_AMOUNT) throws SQLException
  { _struct.setAttribute(7, INSTALLMENT_AMOUNT); }


  public java.math.BigDecimal getOPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setOPEN_AMOUNT(java.math.BigDecimal OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(8, OPEN_AMOUNT); }


  public java.math.BigDecimal getPAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setPAID_AMOUNT(java.math.BigDecimal PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(9, PAID_AMOUNT); }


  public java.math.BigDecimal getRECORD_VERSION() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setRECORD_VERSION(java.math.BigDecimal RECORD_VERSION) throws SQLException
  { _struct.setAttribute(10, RECORD_VERSION); }


  public java.sql.Timestamp getSTATUS_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(11); }

  public void setSTATUS_DATE(java.sql.Timestamp STATUS_DATE) throws SQLException
  { _struct.setAttribute(11, STATUS_DATE); }


  public java.math.BigDecimal getTOLL_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setTOLL_AMOUNT(java.math.BigDecimal TOLL_AMOUNT) throws SQLException
  { _struct.setAttribute(12, TOLL_AMOUNT); }


  public java.math.BigDecimal getFEE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(13); }

  public void setFEE_AMOUNT(java.math.BigDecimal FEE_AMOUNT) throws SQLException
  { _struct.setAttribute(13, FEE_AMOUNT); }


  public String getCREATED_BY() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setCREATED_BY(String CREATED_BY) throws SQLException
  { _struct.setAttribute(14, CREATED_BY); }


  public java.sql.Timestamp getDATE_CREATED() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(15); }

  public void setDATE_CREATED(java.sql.Timestamp DATE_CREATED) throws SQLException
  { _struct.setAttribute(15, DATE_CREATED); }


  public String getMODIFIED_BY() throws SQLException
  { return (String) _struct.getAttribute(16); }

  public void setMODIFIED_BY(String MODIFIED_BY) throws SQLException
  { _struct.setAttribute(16, MODIFIED_BY); }


  public java.sql.Timestamp getDATE_MODIFIED() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(17); }

  public void setDATE_MODIFIED(java.sql.Timestamp DATE_MODIFIED) throws SQLException
  { _struct.setAttribute(17, DATE_MODIFIED); }


  public java.math.BigDecimal getPLAN_INSTALLMENT_TYPE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(18); }

  public void setPLAN_INSTALLMENT_TYPE_ID(java.math.BigDecimal PLAN_INSTALLMENT_TYPE_ID) throws SQLException
  { _struct.setAttribute(18, PLAN_INSTALLMENT_TYPE_ID); }


  public java.math.BigDecimal getLAST_CART_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(19); }

  public void setLAST_CART_ID(java.math.BigDecimal LAST_CART_ID) throws SQLException
  { _struct.setAttribute(19, LAST_CART_ID); }


  public java.math.BigDecimal getUNCOLLECTABLE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(20); }

  public void setUNCOLLECTABLE_AMOUNT(java.math.BigDecimal UNCOLLECTABLE_AMOUNT) throws SQLException
  { _struct.setAttribute(20, UNCOLLECTABLE_AMOUNT); }


  public java.math.BigDecimal getCANCELLED_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(21); }

  public void setCANCELLED_AMOUNT(java.math.BigDecimal CANCELLED_AMOUNT) throws SQLException
  { _struct.setAttribute(21, CANCELLED_AMOUNT); }

}
