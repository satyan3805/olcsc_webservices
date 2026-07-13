package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_PAYMENT_INFO_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_PAYMENT_INFO_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2,12,91,2,12,2,12,12,2,2,2,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[29];
  protected static final OLC_PAYMENT_INFO_REC _OLC_PAYMENT_INFO_RECFactory = new OLC_PAYMENT_INFO_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_PAYMENT_INFO_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[29], _sqlType, _factory); }
  public OLC_PAYMENT_INFO_REC()
  { _init_struct(true); }
  public OLC_PAYMENT_INFO_REC(String PMT_TYPE, java.math.BigDecimal PERSON_ID, String CARD_NBR, java.sql.Timestamp CARD_EXPIRES, java.math.BigDecimal CARD_TYPE, String CARD_NAME, java.math.BigDecimal ACCOUNT_BILLING_METHOD_ID, String IS_DEFAULT_BILLING_METHOD, String IS_ACTIVE, java.math.BigDecimal TOKEN, java.math.BigDecimal ADDRESS_ID, java.math.BigDecimal PHONE_ID, String FIRST_NAME, String LAST_NAME, String ADDRESS_1, String ADDRESS_2, String CITY, String STATE, String COUNTRY, String ZIPCODE, String PLUS4, String PHONE_NBR, String PHONE_EXTN, String BANK_ACCT_TYPE, String BANK_ACCT_NUMBER, String ROUTING_NBR, String IS_BLOCKED, String BILLING_TYPE, java.math.BigDecimal BILLING_PRIORITY) throws SQLException
  { _init_struct(true);
    setPMT_TYPE(PMT_TYPE);
    setPERSON_ID(PERSON_ID);
    setCARD_NBR(CARD_NBR);
    setCARD_EXPIRES(CARD_EXPIRES);
    setCARD_TYPE(CARD_TYPE);
    setCARD_NAME(CARD_NAME);
    setACCOUNT_BILLING_METHOD_ID(ACCOUNT_BILLING_METHOD_ID);
    setIS_DEFAULT_BILLING_METHOD(IS_DEFAULT_BILLING_METHOD);
    setIS_ACTIVE(IS_ACTIVE);
    setTOKEN(TOKEN);
    setADDRESS_ID(ADDRESS_ID);
    setPHONE_ID(PHONE_ID);
    setFIRST_NAME(FIRST_NAME);
    setLAST_NAME(LAST_NAME);
    setADDRESS_1(ADDRESS_1);
    setADDRESS_2(ADDRESS_2);
    setCITY(CITY);
    setSTATE(STATE);
    setCOUNTRY(COUNTRY);
    setZIPCODE(ZIPCODE);
    setPLUS4(PLUS4);
    setPHONE_NBR(PHONE_NBR);
    setPHONE_EXTN(PHONE_EXTN);
    setBANK_ACCT_TYPE(BANK_ACCT_TYPE);
    setBANK_ACCT_NUMBER(BANK_ACCT_NUMBER);
    setROUTING_NBR(ROUTING_NBR);
    setIS_BLOCKED(IS_BLOCKED);
    setBILLING_TYPE(BILLING_TYPE);
    setBILLING_PRIORITY(BILLING_PRIORITY);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_PAYMENT_INFO_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_PAYMENT_INFO_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getPMT_TYPE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setPMT_TYPE(String PMT_TYPE) throws SQLException
  { _struct.setAttribute(0, PMT_TYPE); }


  public java.math.BigDecimal getPERSON_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setPERSON_ID(java.math.BigDecimal PERSON_ID) throws SQLException
  { _struct.setAttribute(1, PERSON_ID); }


  public String getCARD_NBR() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setCARD_NBR(String CARD_NBR) throws SQLException
  { _struct.setAttribute(2, CARD_NBR); }


  public java.sql.Timestamp getCARD_EXPIRES() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(3); }

  public void setCARD_EXPIRES(java.sql.Timestamp CARD_EXPIRES) throws SQLException
  { _struct.setAttribute(3, CARD_EXPIRES); }


  public java.math.BigDecimal getCARD_TYPE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setCARD_TYPE(java.math.BigDecimal CARD_TYPE) throws SQLException
  { _struct.setAttribute(4, CARD_TYPE); }


  public String getCARD_NAME() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setCARD_NAME(String CARD_NAME) throws SQLException
  { _struct.setAttribute(5, CARD_NAME); }


  public java.math.BigDecimal getACCOUNT_BILLING_METHOD_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setACCOUNT_BILLING_METHOD_ID(java.math.BigDecimal ACCOUNT_BILLING_METHOD_ID) throws SQLException
  { _struct.setAttribute(6, ACCOUNT_BILLING_METHOD_ID); }


  public String getIS_DEFAULT_BILLING_METHOD() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setIS_DEFAULT_BILLING_METHOD(String IS_DEFAULT_BILLING_METHOD) throws SQLException
  { _struct.setAttribute(7, IS_DEFAULT_BILLING_METHOD); }


  public String getIS_ACTIVE() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setIS_ACTIVE(String IS_ACTIVE) throws SQLException
  { _struct.setAttribute(8, IS_ACTIVE); }


  public java.math.BigDecimal getTOKEN() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setTOKEN(java.math.BigDecimal TOKEN) throws SQLException
  { _struct.setAttribute(9, TOKEN); }


  public java.math.BigDecimal getADDRESS_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setADDRESS_ID(java.math.BigDecimal ADDRESS_ID) throws SQLException
  { _struct.setAttribute(10, ADDRESS_ID); }


  public java.math.BigDecimal getPHONE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setPHONE_ID(java.math.BigDecimal PHONE_ID) throws SQLException
  { _struct.setAttribute(11, PHONE_ID); }


  public String getFIRST_NAME() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setFIRST_NAME(String FIRST_NAME) throws SQLException
  { _struct.setAttribute(12, FIRST_NAME); }


  public String getLAST_NAME() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setLAST_NAME(String LAST_NAME) throws SQLException
  { _struct.setAttribute(13, LAST_NAME); }


  public String getADDRESS_1() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setADDRESS_1(String ADDRESS_1) throws SQLException
  { _struct.setAttribute(14, ADDRESS_1); }


  public String getADDRESS_2() throws SQLException
  { return (String) _struct.getAttribute(15); }

  public void setADDRESS_2(String ADDRESS_2) throws SQLException
  { _struct.setAttribute(15, ADDRESS_2); }


  public String getCITY() throws SQLException
  { return (String) _struct.getAttribute(16); }

  public void setCITY(String CITY) throws SQLException
  { _struct.setAttribute(16, CITY); }


  public String getSTATE() throws SQLException
  { return (String) _struct.getAttribute(17); }

  public void setSTATE(String STATE) throws SQLException
  { _struct.setAttribute(17, STATE); }


  public String getCOUNTRY() throws SQLException
  { return (String) _struct.getAttribute(18); }

  public void setCOUNTRY(String COUNTRY) throws SQLException
  { _struct.setAttribute(18, COUNTRY); }


  public String getZIPCODE() throws SQLException
  { return (String) _struct.getAttribute(19); }

  public void setZIPCODE(String ZIPCODE) throws SQLException
  { _struct.setAttribute(19, ZIPCODE); }


  public String getPLUS4() throws SQLException
  { return (String) _struct.getAttribute(20); }

  public void setPLUS4(String PLUS4) throws SQLException
  { _struct.setAttribute(20, PLUS4); }


  public String getPHONE_NBR() throws SQLException
  { return (String) _struct.getAttribute(21); }

  public void setPHONE_NBR(String PHONE_NBR) throws SQLException
  { _struct.setAttribute(21, PHONE_NBR); }


  public String getPHONE_EXTN() throws SQLException
  { return (String) _struct.getAttribute(22); }

  public void setPHONE_EXTN(String PHONE_EXTN) throws SQLException
  { _struct.setAttribute(22, PHONE_EXTN); }


  public String getBANK_ACCT_TYPE() throws SQLException
  { return (String) _struct.getAttribute(23); }

  public void setBANK_ACCT_TYPE(String BANK_ACCT_TYPE) throws SQLException
  { _struct.setAttribute(23, BANK_ACCT_TYPE); }


  public String getBANK_ACCT_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(24); }

  public void setBANK_ACCT_NUMBER(String BANK_ACCT_NUMBER) throws SQLException
  { _struct.setAttribute(24, BANK_ACCT_NUMBER); }


  public String getROUTING_NBR() throws SQLException
  { return (String) _struct.getAttribute(25); }

  public void setROUTING_NBR(String ROUTING_NBR) throws SQLException
  { _struct.setAttribute(25, ROUTING_NBR); }


  public String getIS_BLOCKED() throws SQLException
  { return (String) _struct.getAttribute(26); }

  public void setIS_BLOCKED(String IS_BLOCKED) throws SQLException
  { _struct.setAttribute(26, IS_BLOCKED); }


  public String getBILLING_TYPE() throws SQLException
  { return (String) _struct.getAttribute(27); }

  public void setBILLING_TYPE(String BILLING_TYPE) throws SQLException
  { _struct.setAttribute(27, BILLING_TYPE); }


  public java.math.BigDecimal getBILLING_PRIORITY() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(28); }

  public void setBILLING_PRIORITY(java.math.BigDecimal BILLING_PRIORITY) throws SQLException
  { _struct.setAttribute(28, BILLING_PRIORITY); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_PAYMENT_INFO_REC" + "(" +
       ((getPMT_TYPE()==null)?"null": "'" + getPMT_TYPE()+"'" ) + "," +
       getPERSON_ID() + "," +
       ((getCARD_NBR()==null)?"null": "'" + getCARD_NBR()+"'" ) + "," +
       getCARD_EXPIRES() + "," +
       getCARD_TYPE() + "," +
       ((getCARD_NAME()==null)?"null": "'" + getCARD_NAME()+"'" ) + "," +
       getACCOUNT_BILLING_METHOD_ID() + "," +
       ((getIS_DEFAULT_BILLING_METHOD()==null)?"null": "'" + getIS_DEFAULT_BILLING_METHOD()+"'" ) + "," +
       ((getIS_ACTIVE()==null)?"null": "'" + getIS_ACTIVE()+"'" ) + "," +
       getTOKEN() + "," +
       getADDRESS_ID() + "," +
       getPHONE_ID() + "," +
       ((getFIRST_NAME()==null)?"null": "'" + getFIRST_NAME()+"'" ) + "," +
       ((getLAST_NAME()==null)?"null": "'" + getLAST_NAME()+"'" ) + "," +
       ((getADDRESS_1()==null)?"null": "'" + getADDRESS_1()+"'" ) + "," +
       ((getADDRESS_2()==null)?"null": "'" + getADDRESS_2()+"'" ) + "," +
       ((getCITY()==null)?"null": "'" + getCITY()+"'" ) + "," +
       ((getSTATE()==null)?"null": "'" + getSTATE()+"'" ) + "," +
       ((getCOUNTRY()==null)?"null": "'" + getCOUNTRY()+"'" ) + "," +
       ((getZIPCODE()==null)?"null": "'" + getZIPCODE()+"'" ) + "," +
       ((getPLUS4()==null)?"null": "'" + getPLUS4()+"'" ) + "," +
       ((getPHONE_NBR()==null)?"null": "'" + getPHONE_NBR()+"'" ) + "," +
       ((getPHONE_EXTN()==null)?"null": "'" + getPHONE_EXTN()+"'" ) + "," +
       ((getBANK_ACCT_TYPE()==null)?"null": "'" + getBANK_ACCT_TYPE()+"'" ) + "," +
       ((getBANK_ACCT_NUMBER()==null)?"null": "'" + getBANK_ACCT_NUMBER()+"'" ) + "," +
       ((getROUTING_NBR()==null)?"null": "'" + getROUTING_NBR()+"'" ) + "," +
       ((getIS_BLOCKED()==null)?"null": "'" + getIS_BLOCKED()+"'" ) + "," +
       ((getBILLING_TYPE()==null)?"null": "'" + getBILLING_TYPE()+"'" ) + "," +
       getBILLING_PRIORITY() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
