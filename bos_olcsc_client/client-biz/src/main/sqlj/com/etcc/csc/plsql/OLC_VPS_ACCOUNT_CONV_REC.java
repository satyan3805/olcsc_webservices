package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_ACCOUNT_CONV_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_ACCOUNT_CONV_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,12,2,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[8];
  protected static final OLC_VPS_ACCOUNT_CONV_REC _OLC_VPS_ACCOUNT_CONV_RECFactory = new OLC_VPS_ACCOUNT_CONV_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_ACCOUNT_CONV_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[8], _sqlType, _factory); }
  public OLC_VPS_ACCOUNT_CONV_REC()
  { _init_struct(true); }
  public OLC_VPS_ACCOUNT_CONV_REC(java.math.BigDecimal PERSON_ID, java.math.BigDecimal ADDRESS_ID, String ACCT_CONV_ELIG, java.math.BigDecimal TTL_AMT_DUE_FOR_CONVERSION, java.math.BigDecimal INITIAL_PRE_PAID_BALANCE, java.math.BigDecimal TAG_ACTIVATION_FEE, java.math.BigDecimal ACCT_CONV_DISC_PCT, java.math.BigDecimal TOTAL_DISCOUNTED_AMOUNT) throws SQLException
  { _init_struct(true);
    setPERSON_ID(PERSON_ID);
    setADDRESS_ID(ADDRESS_ID);
    setACCT_CONV_ELIG(ACCT_CONV_ELIG);
    setTTL_AMT_DUE_FOR_CONVERSION(TTL_AMT_DUE_FOR_CONVERSION);
    setINITIAL_PRE_PAID_BALANCE(INITIAL_PRE_PAID_BALANCE);
    setTAG_ACTIVATION_FEE(TAG_ACTIVATION_FEE);
    setACCT_CONV_DISC_PCT(ACCT_CONV_DISC_PCT);
    setTOTAL_DISCOUNTED_AMOUNT(TOTAL_DISCOUNTED_AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_ACCOUNT_CONV_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_ACCOUNT_CONV_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getPERSON_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setPERSON_ID(java.math.BigDecimal PERSON_ID) throws SQLException
  { _struct.setAttribute(0, PERSON_ID); }


  public java.math.BigDecimal getADDRESS_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setADDRESS_ID(java.math.BigDecimal ADDRESS_ID) throws SQLException
  { _struct.setAttribute(1, ADDRESS_ID); }


  public String getACCT_CONV_ELIG() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setACCT_CONV_ELIG(String ACCT_CONV_ELIG) throws SQLException
  { _struct.setAttribute(2, ACCT_CONV_ELIG); }


  public java.math.BigDecimal getTTL_AMT_DUE_FOR_CONVERSION() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setTTL_AMT_DUE_FOR_CONVERSION(java.math.BigDecimal TTL_AMT_DUE_FOR_CONVERSION) throws SQLException
  { _struct.setAttribute(3, TTL_AMT_DUE_FOR_CONVERSION); }


  public java.math.BigDecimal getINITIAL_PRE_PAID_BALANCE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setINITIAL_PRE_PAID_BALANCE(java.math.BigDecimal INITIAL_PRE_PAID_BALANCE) throws SQLException
  { _struct.setAttribute(4, INITIAL_PRE_PAID_BALANCE); }


  public java.math.BigDecimal getTAG_ACTIVATION_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setTAG_ACTIVATION_FEE(java.math.BigDecimal TAG_ACTIVATION_FEE) throws SQLException
  { _struct.setAttribute(5, TAG_ACTIVATION_FEE); }


  public java.math.BigDecimal getACCT_CONV_DISC_PCT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setACCT_CONV_DISC_PCT(java.math.BigDecimal ACCT_CONV_DISC_PCT) throws SQLException
  { _struct.setAttribute(6, ACCT_CONV_DISC_PCT); }


  public java.math.BigDecimal getTOTAL_DISCOUNTED_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setTOTAL_DISCOUNTED_AMOUNT(java.math.BigDecimal TOTAL_DISCOUNTED_AMOUNT) throws SQLException
  { _struct.setAttribute(7, TOTAL_DISCOUNTED_AMOUNT); }

}
