package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCOUNT_PLATE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCOUNT_PLATE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2,12,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2003,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[20];
  static
  {
    _factory[18] = OLC_VPS_PLATE_TYPE_ARR.getORADataFactory();
  }
  protected static final OLC_ACCOUNT_PLATE_REC _OLC_ACCOUNT_PLATE_RECFactory = new OLC_ACCOUNT_PLATE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCOUNT_PLATE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[20], _sqlType, _factory); }
  public OLC_ACCOUNT_PLATE_REC()
  { _init_struct(true); }
  public OLC_ACCOUNT_PLATE_REC(String DISCOUNT_RULE, java.math.BigDecimal ACCOUNT_ID, String ACCOUNT_PLAN, java.math.BigDecimal TTL_AMOUNT_DUE, java.math.BigDecimal ORIG_INV_FEE_AMOUNT, java.math.BigDecimal OPEN_INV_FEE_AMOUNT, java.math.BigDecimal ORIG_INV_AMOUNT, java.math.BigDecimal OPEN_INV_AMOUNT, java.math.BigDecimal ORIG_UNINV_AMOUNT, java.math.BigDecimal OPEN_UNINV_AMOUNT, java.math.BigDecimal TOTAL_PAID_AMOUNT, java.math.BigDecimal TOTAL_EXC_AMOUNT, java.math.BigDecimal ACCOUNT_DUE_AFTER_EXC, java.math.BigDecimal ACCOUNT_DUE_BEFORE_EXC, java.math.BigDecimal SERVICE_FEE, java.math.BigDecimal INV_TOLL_OPEN_AMOUNT, java.math.BigDecimal UNIV_TOLL_OPEN_AMOUNT, java.math.BigDecimal TTL_TOLL_OPEN_AMOUNT, OLC_VPS_PLATE_TYPE_ARR OLC_PLATE_TYPE_ARR, String SESSION_ID) throws SQLException
  { _init_struct(true);
    setDISCOUNT_RULE(DISCOUNT_RULE);
    setACCOUNT_ID(ACCOUNT_ID);
    setACCOUNT_PLAN(ACCOUNT_PLAN);
    setTTL_AMOUNT_DUE(TTL_AMOUNT_DUE);
    setORIG_INV_FEE_AMOUNT(ORIG_INV_FEE_AMOUNT);
    setOPEN_INV_FEE_AMOUNT(OPEN_INV_FEE_AMOUNT);
    setORIG_INV_AMOUNT(ORIG_INV_AMOUNT);
    setOPEN_INV_AMOUNT(OPEN_INV_AMOUNT);
    setORIG_UNINV_AMOUNT(ORIG_UNINV_AMOUNT);
    setOPEN_UNINV_AMOUNT(OPEN_UNINV_AMOUNT);
    setTOTAL_PAID_AMOUNT(TOTAL_PAID_AMOUNT);
    setTOTAL_EXC_AMOUNT(TOTAL_EXC_AMOUNT);
    setACCOUNT_DUE_AFTER_EXC(ACCOUNT_DUE_AFTER_EXC);
    setACCOUNT_DUE_BEFORE_EXC(ACCOUNT_DUE_BEFORE_EXC);
    setSERVICE_FEE(SERVICE_FEE);
    setINV_TOLL_OPEN_AMOUNT(INV_TOLL_OPEN_AMOUNT);
    setUNIV_TOLL_OPEN_AMOUNT(UNIV_TOLL_OPEN_AMOUNT);
    setTTL_TOLL_OPEN_AMOUNT(TTL_TOLL_OPEN_AMOUNT);
    setOLC_PLATE_TYPE_ARR(OLC_PLATE_TYPE_ARR);
    setSESSION_ID(SESSION_ID);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCOUNT_PLATE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCOUNT_PLATE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getDISCOUNT_RULE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setDISCOUNT_RULE(String DISCOUNT_RULE) throws SQLException
  { _struct.setAttribute(0, DISCOUNT_RULE); }


  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(1, ACCOUNT_ID); }


  public String getACCOUNT_PLAN() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setACCOUNT_PLAN(String ACCOUNT_PLAN) throws SQLException
  { _struct.setAttribute(2, ACCOUNT_PLAN); }


  public java.math.BigDecimal getTTL_AMOUNT_DUE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setTTL_AMOUNT_DUE(java.math.BigDecimal TTL_AMOUNT_DUE) throws SQLException
  { _struct.setAttribute(3, TTL_AMOUNT_DUE); }


  public java.math.BigDecimal getORIG_INV_FEE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setORIG_INV_FEE_AMOUNT(java.math.BigDecimal ORIG_INV_FEE_AMOUNT) throws SQLException
  { _struct.setAttribute(4, ORIG_INV_FEE_AMOUNT); }


  public java.math.BigDecimal getOPEN_INV_FEE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setOPEN_INV_FEE_AMOUNT(java.math.BigDecimal OPEN_INV_FEE_AMOUNT) throws SQLException
  { _struct.setAttribute(5, OPEN_INV_FEE_AMOUNT); }


  public java.math.BigDecimal getORIG_INV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setORIG_INV_AMOUNT(java.math.BigDecimal ORIG_INV_AMOUNT) throws SQLException
  { _struct.setAttribute(6, ORIG_INV_AMOUNT); }


  public java.math.BigDecimal getOPEN_INV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setOPEN_INV_AMOUNT(java.math.BigDecimal OPEN_INV_AMOUNT) throws SQLException
  { _struct.setAttribute(7, OPEN_INV_AMOUNT); }


  public java.math.BigDecimal getORIG_UNINV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setORIG_UNINV_AMOUNT(java.math.BigDecimal ORIG_UNINV_AMOUNT) throws SQLException
  { _struct.setAttribute(8, ORIG_UNINV_AMOUNT); }


  public java.math.BigDecimal getOPEN_UNINV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setOPEN_UNINV_AMOUNT(java.math.BigDecimal OPEN_UNINV_AMOUNT) throws SQLException
  { _struct.setAttribute(9, OPEN_UNINV_AMOUNT); }


  public java.math.BigDecimal getTOTAL_PAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setTOTAL_PAID_AMOUNT(java.math.BigDecimal TOTAL_PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(10, TOTAL_PAID_AMOUNT); }


  public java.math.BigDecimal getTOTAL_EXC_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setTOTAL_EXC_AMOUNT(java.math.BigDecimal TOTAL_EXC_AMOUNT) throws SQLException
  { _struct.setAttribute(11, TOTAL_EXC_AMOUNT); }


  public java.math.BigDecimal getACCOUNT_DUE_AFTER_EXC() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setACCOUNT_DUE_AFTER_EXC(java.math.BigDecimal ACCOUNT_DUE_AFTER_EXC) throws SQLException
  { _struct.setAttribute(12, ACCOUNT_DUE_AFTER_EXC); }


  public java.math.BigDecimal getACCOUNT_DUE_BEFORE_EXC() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(13); }

  public void setACCOUNT_DUE_BEFORE_EXC(java.math.BigDecimal ACCOUNT_DUE_BEFORE_EXC) throws SQLException
  { _struct.setAttribute(13, ACCOUNT_DUE_BEFORE_EXC); }


  public java.math.BigDecimal getSERVICE_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(14); }

  public void setSERVICE_FEE(java.math.BigDecimal SERVICE_FEE) throws SQLException
  { _struct.setAttribute(14, SERVICE_FEE); }


  public java.math.BigDecimal getINV_TOLL_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(15); }

  public void setINV_TOLL_OPEN_AMOUNT(java.math.BigDecimal INV_TOLL_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(15, INV_TOLL_OPEN_AMOUNT); }


  public java.math.BigDecimal getUNIV_TOLL_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(16); }

  public void setUNIV_TOLL_OPEN_AMOUNT(java.math.BigDecimal UNIV_TOLL_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(16, UNIV_TOLL_OPEN_AMOUNT); }


  public java.math.BigDecimal getTTL_TOLL_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(17); }

  public void setTTL_TOLL_OPEN_AMOUNT(java.math.BigDecimal TTL_TOLL_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(17, TTL_TOLL_OPEN_AMOUNT); }


  public OLC_VPS_PLATE_TYPE_ARR getOLC_PLATE_TYPE_ARR() throws SQLException
  { return (OLC_VPS_PLATE_TYPE_ARR) _struct.getAttribute(18); }

  public void setOLC_PLATE_TYPE_ARR(OLC_VPS_PLATE_TYPE_ARR OLC_PLATE_TYPE_ARR) throws SQLException
  { _struct.setAttribute(18, OLC_PLATE_TYPE_ARR); }


  public String getSESSION_ID() throws SQLException
  { return (String) _struct.getAttribute(19); }

  public void setSESSION_ID(String SESSION_ID) throws SQLException
  { _struct.setAttribute(19, SESSION_ID); }

}
