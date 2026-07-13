package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_BILLING_INFO_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_BILLING_INFO_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,12,12,12,12,12,12,2,2,2,2,12,12,12,12,12,12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[22];
  protected static final OLC_VPS_BILLING_INFO_REC _OLC_VPS_BILLING_INFO_RECFactory = new OLC_VPS_BILLING_INFO_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_BILLING_INFO_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[22], _sqlType, _factory); }
  public OLC_VPS_BILLING_INFO_REC()
  { _init_struct(true); }
  public OLC_VPS_BILLING_INFO_REC(java.math.BigDecimal ACCOUNT_ID, java.math.BigDecimal ACCOUNT_BILLING_METHOD_ID, java.math.BigDecimal PERSON_ID, String PMT_TYPE, String CREDIT_CARD_TYPE_ID, String NAME_ON_BANK_ACCOUNT, String NAME_ON_CARD, String CC_NUMBER, String LAST4, java.math.BigDecimal CC_EXP_MO, java.math.BigDecimal CC_EXP_YEAR, java.math.BigDecimal TOKEN, java.math.BigDecimal BANK_ACCOUNT_TYPE_ID, String ROUTING_NUMBER, String BANK_ACCOUNT_NUMBER, String ADDRESS_1, String ADDRESS_2, String CITY, String STATE, String COUNTRY, String ZIPCODE, String PLUS4) throws SQLException
  { _init_struct(true);
    setACCOUNT_ID(ACCOUNT_ID);
    setACCOUNT_BILLING_METHOD_ID(ACCOUNT_BILLING_METHOD_ID);
    setPERSON_ID(PERSON_ID);
    setPMT_TYPE(PMT_TYPE);
    setCREDIT_CARD_TYPE_ID(CREDIT_CARD_TYPE_ID);
    setNAME_ON_BANK_ACCOUNT(NAME_ON_BANK_ACCOUNT);
    setNAME_ON_CARD(NAME_ON_CARD);
    setCC_NUMBER(CC_NUMBER);
    setLAST4(LAST4);
    setCC_EXP_MO(CC_EXP_MO);
    setCC_EXP_YEAR(CC_EXP_YEAR);
    setTOKEN(TOKEN);
    setBANK_ACCOUNT_TYPE_ID(BANK_ACCOUNT_TYPE_ID);
    setROUTING_NUMBER(ROUTING_NUMBER);
    setBANK_ACCOUNT_NUMBER(BANK_ACCOUNT_NUMBER);
    setADDRESS_1(ADDRESS_1);
    setADDRESS_2(ADDRESS_2);
    setCITY(CITY);
    setSTATE(STATE);
    setCOUNTRY(COUNTRY);
    setZIPCODE(ZIPCODE);
    setPLUS4(PLUS4);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_BILLING_INFO_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_BILLING_INFO_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(0, ACCOUNT_ID); }


  public java.math.BigDecimal getACCOUNT_BILLING_METHOD_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setACCOUNT_BILLING_METHOD_ID(java.math.BigDecimal ACCOUNT_BILLING_METHOD_ID) throws SQLException
  { _struct.setAttribute(1, ACCOUNT_BILLING_METHOD_ID); }


  public java.math.BigDecimal getPERSON_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setPERSON_ID(java.math.BigDecimal PERSON_ID) throws SQLException
  { _struct.setAttribute(2, PERSON_ID); }


  public String getPMT_TYPE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setPMT_TYPE(String PMT_TYPE) throws SQLException
  { _struct.setAttribute(3, PMT_TYPE); }


  public String getCREDIT_CARD_TYPE_ID() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setCREDIT_CARD_TYPE_ID(String CREDIT_CARD_TYPE_ID) throws SQLException
  { _struct.setAttribute(4, CREDIT_CARD_TYPE_ID); }


  public String getNAME_ON_BANK_ACCOUNT() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setNAME_ON_BANK_ACCOUNT(String NAME_ON_BANK_ACCOUNT) throws SQLException
  { _struct.setAttribute(5, NAME_ON_BANK_ACCOUNT); }


  public String getNAME_ON_CARD() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setNAME_ON_CARD(String NAME_ON_CARD) throws SQLException
  { _struct.setAttribute(6, NAME_ON_CARD); }


  public String getCC_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setCC_NUMBER(String CC_NUMBER) throws SQLException
  { _struct.setAttribute(7, CC_NUMBER); }


  public String getLAST4() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLAST4(String LAST4) throws SQLException
  { _struct.setAttribute(8, LAST4); }


  public java.math.BigDecimal getCC_EXP_MO() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setCC_EXP_MO(java.math.BigDecimal CC_EXP_MO) throws SQLException
  { _struct.setAttribute(9, CC_EXP_MO); }


  public java.math.BigDecimal getCC_EXP_YEAR() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setCC_EXP_YEAR(java.math.BigDecimal CC_EXP_YEAR) throws SQLException
  { _struct.setAttribute(10, CC_EXP_YEAR); }


  public java.math.BigDecimal getTOKEN() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setTOKEN(java.math.BigDecimal TOKEN) throws SQLException
  { _struct.setAttribute(11, TOKEN); }


  public java.math.BigDecimal getBANK_ACCOUNT_TYPE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setBANK_ACCOUNT_TYPE_ID(java.math.BigDecimal BANK_ACCOUNT_TYPE_ID) throws SQLException
  { _struct.setAttribute(12, BANK_ACCOUNT_TYPE_ID); }


  public String getROUTING_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setROUTING_NUMBER(String ROUTING_NUMBER) throws SQLException
  { _struct.setAttribute(13, ROUTING_NUMBER); }


  public String getBANK_ACCOUNT_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setBANK_ACCOUNT_NUMBER(String BANK_ACCOUNT_NUMBER) throws SQLException
  { _struct.setAttribute(14, BANK_ACCOUNT_NUMBER); }


  public String getADDRESS_1() throws SQLException
  { return (String) _struct.getAttribute(15); }

  public void setADDRESS_1(String ADDRESS_1) throws SQLException
  { _struct.setAttribute(15, ADDRESS_1); }


  public String getADDRESS_2() throws SQLException
  { return (String) _struct.getAttribute(16); }

  public void setADDRESS_2(String ADDRESS_2) throws SQLException
  { _struct.setAttribute(16, ADDRESS_2); }


  public String getCITY() throws SQLException
  { return (String) _struct.getAttribute(17); }

  public void setCITY(String CITY) throws SQLException
  { _struct.setAttribute(17, CITY); }


  public String getSTATE() throws SQLException
  { return (String) _struct.getAttribute(18); }

  public void setSTATE(String STATE) throws SQLException
  { _struct.setAttribute(18, STATE); }


  public String getCOUNTRY() throws SQLException
  { return (String) _struct.getAttribute(19); }

  public void setCOUNTRY(String COUNTRY) throws SQLException
  { _struct.setAttribute(19, COUNTRY); }


  public String getZIPCODE() throws SQLException
  { return (String) _struct.getAttribute(20); }

  public void setZIPCODE(String ZIPCODE) throws SQLException
  { _struct.setAttribute(20, ZIPCODE); }


  public String getPLUS4() throws SQLException
  { return (String) _struct.getAttribute(21); }

  public void setPLUS4(String PLUS4) throws SQLException
  { _struct.setAttribute(21, PLUS4); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VPS_BILLING_INFO_REC" + "(" +
       getACCOUNT_ID() + "," +
       getACCOUNT_BILLING_METHOD_ID() + "," +
       getPERSON_ID() + "," +
       ((getPMT_TYPE()==null)?"null": "'" + getPMT_TYPE()+"'" ) + "," +
       ((getCREDIT_CARD_TYPE_ID()==null)?"null": "'" + getCREDIT_CARD_TYPE_ID()+"'" ) + "," +
       ((getNAME_ON_BANK_ACCOUNT()==null)?"null": "'" + getNAME_ON_BANK_ACCOUNT()+"'" ) + "," +
       ((getNAME_ON_CARD()==null)?"null": "'" + getNAME_ON_CARD()+"'" ) + "," +
       ((getCC_NUMBER()==null)?"null": "'" + getCC_NUMBER()+"'" ) + "," +
       ((getLAST4()==null)?"null": "'" + getLAST4()+"'" ) + "," +
       getCC_EXP_MO() + "," +
       getCC_EXP_YEAR() + "," +
       getTOKEN() + "," +
       getBANK_ACCOUNT_TYPE_ID() + "," +
       ((getROUTING_NUMBER()==null)?"null": "'" + getROUTING_NUMBER()+"'" ) + "," +
       ((getBANK_ACCOUNT_NUMBER()==null)?"null": "'" + getBANK_ACCOUNT_NUMBER()+"'" ) + "," +
       ((getADDRESS_1()==null)?"null": "'" + getADDRESS_1()+"'" ) + "," +
       ((getADDRESS_2()==null)?"null": "'" + getADDRESS_2()+"'" ) + "," +
       ((getCITY()==null)?"null": "'" + getCITY()+"'" ) + "," +
       ((getSTATE()==null)?"null": "'" + getSTATE()+"'" ) + "," +
       ((getCOUNTRY()==null)?"null": "'" + getCOUNTRY()+"'" ) + "," +
       ((getZIPCODE()==null)?"null": "'" + getZIPCODE()+"'" ) + "," +
       ((getPLUS4()==null)?"null": "'" + getPLUS4()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
