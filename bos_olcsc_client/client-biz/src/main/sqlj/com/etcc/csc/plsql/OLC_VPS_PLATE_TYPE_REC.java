package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_PLATE_TYPE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_PLATE_TYPE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2,2003,12,12,2,2,2,2,2,2,2,2,2,2,2,2003,2,2,2003,2,2,2,2002,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[25];
  static
  {
    _factory[2] = OLC_VPS_INV_TYPE_ARR.getORADataFactory();
    _factory[16] = OLC_VPS_UNINVOICED_VIOLS_ARR.getORADataFactory();
    _factory[19] = OLC_PMT_PLAN_TYPE_ARR.getORADataFactory();
    _factory[23] = OLC_VPS_ACCOUNT_CONV_REC.getORADataFactory();
    _factory[24] = OLC_VPS_PLATE_AGCY_ARR.getORADataFactory();
  }
  protected static final OLC_VPS_PLATE_TYPE_REC _OLC_VPS_PLATE_TYPE_RECFactory = new OLC_VPS_PLATE_TYPE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_PLATE_TYPE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[25], _sqlType, _factory); }
  public OLC_VPS_PLATE_TYPE_REC()
  { _init_struct(true); }
  public OLC_VPS_PLATE_TYPE_REC(String DISCOUNT_RULE, java.math.BigDecimal TTL_AMOUNT_DUE, OLC_VPS_INV_TYPE_ARR INV_TYPE_ARR, String LIC_PLATE, String LIC_STATE, java.math.BigDecimal ORIG_INV_FEE_AMOUNT, java.math.BigDecimal OPEN_INV_FEE_AMOUNT, java.math.BigDecimal ORIG_INV_AMOUNT, java.math.BigDecimal OPEN_INV_AMOUNT, java.math.BigDecimal ORIG_PLATE_AMOUNT, java.math.BigDecimal ORIG_UNINV_AMOUNT, java.math.BigDecimal OPEN_UNINV_AMOUNT, java.math.BigDecimal TOTAL_PAID_AMOUNT, java.math.BigDecimal TOTAL_EXC_AMOUNT, java.math.BigDecimal PLATE_DUE_AFTER_EXC, java.math.BigDecimal PLATE_DUE_BEFORE_EXC, OLC_VPS_UNINVOICED_VIOLS_ARR UNINV_TYPE_ARR, java.math.BigDecimal SERVICE_FEE, java.math.BigDecimal ACCOUNT_VEHICLE_ID, OLC_PMT_PLAN_TYPE_ARR PMT_PLAN_TYPE_ARR, java.math.BigDecimal INV_TOLL_OPEN_AMOUNT, java.math.BigDecimal UNIV_TOLL_OPEN_AMOUNT, java.math.BigDecimal TTL_TOLL_OPEN_AMOUNT, OLC_VPS_ACCOUNT_CONV_REC VPS_ACCOUNT_CONV_REC, OLC_VPS_PLATE_AGCY_ARR VPS_PLATE_AGCY_REC) throws SQLException
  { _init_struct(true);
    setDISCOUNT_RULE(DISCOUNT_RULE);
    setTTL_AMOUNT_DUE(TTL_AMOUNT_DUE);
    setINV_TYPE_ARR(INV_TYPE_ARR);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setORIG_INV_FEE_AMOUNT(ORIG_INV_FEE_AMOUNT);
    setOPEN_INV_FEE_AMOUNT(OPEN_INV_FEE_AMOUNT);
    setORIG_INV_AMOUNT(ORIG_INV_AMOUNT);
    setOPEN_INV_AMOUNT(OPEN_INV_AMOUNT);
    setORIG_PLATE_AMOUNT(ORIG_PLATE_AMOUNT);
    setORIG_UNINV_AMOUNT(ORIG_UNINV_AMOUNT);
    setOPEN_UNINV_AMOUNT(OPEN_UNINV_AMOUNT);
    setTOTAL_PAID_AMOUNT(TOTAL_PAID_AMOUNT);
    setTOTAL_EXC_AMOUNT(TOTAL_EXC_AMOUNT);
    setPLATE_DUE_AFTER_EXC(PLATE_DUE_AFTER_EXC);
    setPLATE_DUE_BEFORE_EXC(PLATE_DUE_BEFORE_EXC);
    setUNINV_TYPE_ARR(UNINV_TYPE_ARR);
    setSERVICE_FEE(SERVICE_FEE);
    setACCOUNT_VEHICLE_ID(ACCOUNT_VEHICLE_ID);
    setPMT_PLAN_TYPE_ARR(PMT_PLAN_TYPE_ARR);
    setINV_TOLL_OPEN_AMOUNT(INV_TOLL_OPEN_AMOUNT);
    setUNIV_TOLL_OPEN_AMOUNT(UNIV_TOLL_OPEN_AMOUNT);
    setTTL_TOLL_OPEN_AMOUNT(TTL_TOLL_OPEN_AMOUNT);
    setVPS_ACCOUNT_CONV_REC(VPS_ACCOUNT_CONV_REC);
    setVPS_PLATE_AGCY_REC(VPS_PLATE_AGCY_REC);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_PLATE_TYPE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_PLATE_TYPE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getDISCOUNT_RULE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setDISCOUNT_RULE(String DISCOUNT_RULE) throws SQLException
  { _struct.setAttribute(0, DISCOUNT_RULE); }


  public java.math.BigDecimal getTTL_AMOUNT_DUE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setTTL_AMOUNT_DUE(java.math.BigDecimal TTL_AMOUNT_DUE) throws SQLException
  { _struct.setAttribute(1, TTL_AMOUNT_DUE); }


  public OLC_VPS_INV_TYPE_ARR getINV_TYPE_ARR() throws SQLException
  { return (OLC_VPS_INV_TYPE_ARR) _struct.getAttribute(2); }

  public void setINV_TYPE_ARR(OLC_VPS_INV_TYPE_ARR INV_TYPE_ARR) throws SQLException
  { _struct.setAttribute(2, INV_TYPE_ARR); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(3, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(4, LIC_STATE); }


  public java.math.BigDecimal getORIG_INV_FEE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setORIG_INV_FEE_AMOUNT(java.math.BigDecimal ORIG_INV_FEE_AMOUNT) throws SQLException
  { _struct.setAttribute(5, ORIG_INV_FEE_AMOUNT); }


  public java.math.BigDecimal getOPEN_INV_FEE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setOPEN_INV_FEE_AMOUNT(java.math.BigDecimal OPEN_INV_FEE_AMOUNT) throws SQLException
  { _struct.setAttribute(6, OPEN_INV_FEE_AMOUNT); }


  public java.math.BigDecimal getORIG_INV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setORIG_INV_AMOUNT(java.math.BigDecimal ORIG_INV_AMOUNT) throws SQLException
  { _struct.setAttribute(7, ORIG_INV_AMOUNT); }


  public java.math.BigDecimal getOPEN_INV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setOPEN_INV_AMOUNT(java.math.BigDecimal OPEN_INV_AMOUNT) throws SQLException
  { _struct.setAttribute(8, OPEN_INV_AMOUNT); }


  public java.math.BigDecimal getORIG_PLATE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setORIG_PLATE_AMOUNT(java.math.BigDecimal ORIG_PLATE_AMOUNT) throws SQLException
  { _struct.setAttribute(9, ORIG_PLATE_AMOUNT); }


  public java.math.BigDecimal getORIG_UNINV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setORIG_UNINV_AMOUNT(java.math.BigDecimal ORIG_UNINV_AMOUNT) throws SQLException
  { _struct.setAttribute(10, ORIG_UNINV_AMOUNT); }


  public java.math.BigDecimal getOPEN_UNINV_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setOPEN_UNINV_AMOUNT(java.math.BigDecimal OPEN_UNINV_AMOUNT) throws SQLException
  { _struct.setAttribute(11, OPEN_UNINV_AMOUNT); }


  public java.math.BigDecimal getTOTAL_PAID_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setTOTAL_PAID_AMOUNT(java.math.BigDecimal TOTAL_PAID_AMOUNT) throws SQLException
  { _struct.setAttribute(12, TOTAL_PAID_AMOUNT); }


  public java.math.BigDecimal getTOTAL_EXC_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(13); }

  public void setTOTAL_EXC_AMOUNT(java.math.BigDecimal TOTAL_EXC_AMOUNT) throws SQLException
  { _struct.setAttribute(13, TOTAL_EXC_AMOUNT); }


  public java.math.BigDecimal getPLATE_DUE_AFTER_EXC() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(14); }

  public void setPLATE_DUE_AFTER_EXC(java.math.BigDecimal PLATE_DUE_AFTER_EXC) throws SQLException
  { _struct.setAttribute(14, PLATE_DUE_AFTER_EXC); }


  public java.math.BigDecimal getPLATE_DUE_BEFORE_EXC() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(15); }

  public void setPLATE_DUE_BEFORE_EXC(java.math.BigDecimal PLATE_DUE_BEFORE_EXC) throws SQLException
  { _struct.setAttribute(15, PLATE_DUE_BEFORE_EXC); }


  public OLC_VPS_UNINVOICED_VIOLS_ARR getUNINV_TYPE_ARR() throws SQLException
  { return (OLC_VPS_UNINVOICED_VIOLS_ARR) _struct.getAttribute(16); }

  public void setUNINV_TYPE_ARR(OLC_VPS_UNINVOICED_VIOLS_ARR UNINV_TYPE_ARR) throws SQLException
  { _struct.setAttribute(16, UNINV_TYPE_ARR); }


  public java.math.BigDecimal getSERVICE_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(17); }

  public void setSERVICE_FEE(java.math.BigDecimal SERVICE_FEE) throws SQLException
  { _struct.setAttribute(17, SERVICE_FEE); }


  public java.math.BigDecimal getACCOUNT_VEHICLE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(18); }

  public void setACCOUNT_VEHICLE_ID(java.math.BigDecimal ACCOUNT_VEHICLE_ID) throws SQLException
  { _struct.setAttribute(18, ACCOUNT_VEHICLE_ID); }


  public OLC_PMT_PLAN_TYPE_ARR getPMT_PLAN_TYPE_ARR() throws SQLException
  { return (OLC_PMT_PLAN_TYPE_ARR) _struct.getAttribute(19); }

  public void setPMT_PLAN_TYPE_ARR(OLC_PMT_PLAN_TYPE_ARR PMT_PLAN_TYPE_ARR) throws SQLException
  { _struct.setAttribute(19, PMT_PLAN_TYPE_ARR); }


  public java.math.BigDecimal getINV_TOLL_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(20); }

  public void setINV_TOLL_OPEN_AMOUNT(java.math.BigDecimal INV_TOLL_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(20, INV_TOLL_OPEN_AMOUNT); }


  public java.math.BigDecimal getUNIV_TOLL_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(21); }

  public void setUNIV_TOLL_OPEN_AMOUNT(java.math.BigDecimal UNIV_TOLL_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(21, UNIV_TOLL_OPEN_AMOUNT); }


  public java.math.BigDecimal getTTL_TOLL_OPEN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(22); }

  public void setTTL_TOLL_OPEN_AMOUNT(java.math.BigDecimal TTL_TOLL_OPEN_AMOUNT) throws SQLException
  { _struct.setAttribute(22, TTL_TOLL_OPEN_AMOUNT); }


  public OLC_VPS_ACCOUNT_CONV_REC getVPS_ACCOUNT_CONV_REC() throws SQLException
  { return (OLC_VPS_ACCOUNT_CONV_REC) _struct.getAttribute(23); }

  public void setVPS_ACCOUNT_CONV_REC(OLC_VPS_ACCOUNT_CONV_REC VPS_ACCOUNT_CONV_REC) throws SQLException
  { _struct.setAttribute(23, VPS_ACCOUNT_CONV_REC); }


  public OLC_VPS_PLATE_AGCY_ARR getVPS_PLATE_AGCY_REC() throws SQLException
  { return (OLC_VPS_PLATE_AGCY_ARR) _struct.getAttribute(24); }

  public void setVPS_PLATE_AGCY_REC(OLC_VPS_PLATE_AGCY_ARR VPS_PLATE_AGCY_REC) throws SQLException
  { _struct.setAttribute(24, VPS_PLATE_AGCY_REC); }

}
