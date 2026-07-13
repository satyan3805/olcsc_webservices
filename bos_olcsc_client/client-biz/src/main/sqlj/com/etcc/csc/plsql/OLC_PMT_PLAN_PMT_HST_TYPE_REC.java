package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_PMT_PLAN_PMT_HST_TYPE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_PMT_PLAN_PMT_HST_TYPE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,12,91,91,2,12,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[12];
  protected static final OLC_PMT_PLAN_PMT_HST_TYPE_REC _OLC_PMT_PLAN_PMT_HST_TYPE_RECFactory = new OLC_PMT_PLAN_PMT_HST_TYPE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_PMT_PLAN_PMT_HST_TYPE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[12], _sqlType, _factory); }
  public OLC_PMT_PLAN_PMT_HST_TYPE_REC()
  { _init_struct(true); }
  public OLC_PMT_PLAN_PMT_HST_TYPE_REC(java.math.BigDecimal PAYMENT_ID, String PAYMENT_FORM, String PAYMENT_TYPE, String LAST4, java.sql.Timestamp PAYMENT_DATE, java.sql.Timestamp DUE_DATE, java.math.BigDecimal INSTALLMENT_NUMBER, String INSTALLMENT_STATUS, java.math.BigDecimal PAYMENT_AMOUNT, java.math.BigDecimal INSTALLMENT_AMOUNT, java.math.BigDecimal PAID_AMOUNT, java.math.BigDecimal OPEN_AMOUNT) throws SQLException
  { _init_struct(true);
    setPAYMENT_ID(PAYMENT_ID);
    setPAYMENT_FORM(PAYMENT_FORM);
    setPAYMENT_TYPE(PAYMENT_TYPE);
    setLAST4(LAST4);
    setPAYMENT_DATE(PAYMENT_DATE);
    setDUE_DATE(DUE_DATE);
    setINSTALLMENT_NUMBER(INSTALLMENT_NUMBER);
    setINSTALLMENT_STATUS(INSTALLMENT_STATUS);
    setPAYMENT_AMOUNT(PAYMENT_AMOUNT);
    setINSTALLMENT_AMOUNT(INSTALLMENT_AMOUNT);
    setPAID_AMOUNT(PAID_AMOUNT);
    setOPEN_AMOUNT(OPEN_AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_PMT_PLAN_PMT_HST_TYPE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_PMT_PLAN_PMT_HST_TYPE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getPAYMENT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setPAYMENT_ID(java.math.BigDecimal PAYMENT_ID) throws SQLException
  { _struct.setAttribute(0, PAYMENT_ID); }


  public String getPAYMENT_FORM() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setPAYMENT_FORM(String PAYMENT_FORM) throws SQLException
  { _struct.setAttribute(1, PAYMENT_FORM); }


  public String getPAYMENT_TYPE() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setPAYMENT_TYPE(String PAYMENT_TYPE) throws SQLException
  { _struct.setAttribute(2, PAYMENT_TYPE); }


  public String getLAST4() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setLAST4(String LAST4) throws SQLException
  { _struct.setAttribute(3, LAST4); }


  public java.sql.Timestamp getPAYMENT_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(4); }

  public void setPAYMENT_DATE(java.sql.Timestamp PAYMENT_DATE) throws SQLException
  { _struct.setAttribute(4, PAYMENT_DATE); }


  public java.sql.Timestamp getDUE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(5); }

  public void setDUE_DATE(java.sql.Timestamp DUE_DATE) throws SQLException
  { _struct.setAttribute(5, DUE_DATE); }


  public java.math.BigDecimal getINSTALLMENT_NUMBER() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setINSTALLMENT_NUMBER(java.math.BigDecimal INSTALLMENT_NUMBER) throws SQLException
  { _struct.setAttribute(6, INSTALLMENT_NUMBER); }


  public String getINSTALLMENT_STATUS() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setINSTALLMENT_STATUS(String INSTALLMENT_STATUS) throws SQLException
  { _struct.setAttribute(7, INSTALLMENT_STATUS); }


  public java.math.BigDecimal getPAYMENT_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setPAYMENT_AMOUNT(java.math.BigDecimal PAYMENT_AMOUNT) throws SQLException
  { _struct.setAttribute(8, PAYMENT_AMOUNT); }


  public java.math.BigDecimal getINSTALLMENT_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setINSTALLMENT_AMOUNT(java.math.BigDecimal INSTALLMENT_AMOUNT) throws SQLException
  { _struct.setAttribute(9, INSTALLMENT_AMOUNT); }


  public java.math.BigDecimal getPAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setPAID_AMOUNT(java.math.BigDecimal PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(10, PAID_AMOUNT); }


  public java.math.BigDecimal getOPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setOPEN_AMOUNT(java.math.BigDecimal OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(11, OPEN_AMOUNT); }

}
