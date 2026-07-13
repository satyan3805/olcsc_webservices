package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCOUNT_INFO_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCOUNT_INFO_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,2,91,12,12,12,12,12,2,12,12,2,12,2,2,12,12,12,12,2,2,2,2,2,2,2,2,2,12,12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[50];
  protected static final OLC_ACCOUNT_INFO_REC _OLC_ACCOUNT_INFO_RECFactory = new OLC_ACCOUNT_INFO_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCOUNT_INFO_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[50], _sqlType, _factory); }
  public OLC_ACCOUNT_INFO_REC()
  { _init_struct(true); }
  public OLC_ACCOUNT_INFO_REC(String ACCT_TYPE_CODE, String ACCT_TYPE_DESCR, String FIRST_NAME, String MIDDLE_INITIAL, String LAST_NAME, String ADDRESS1, String ADDRESS2, String CITY, String STATE, String ZIP_CODE, String PLUS4, String HOME_PHO_NBR, String WORK_PHO_NBR, String MOBILE_PHO_NBR, String WORK_PHO_EXT, String DRIVER_LIC_NBR, String DRIVER_LIC_STATE, String COMPANY_NAME, java.math.BigDecimal BALANCE_AMT, java.sql.Timestamp BAL_LAST_UPDATED, String EMAIL_ADDRESS, String EMAIL_ADDRESS2, String EMAIL_ADDRESS3, String MO_STMT_FLAG, String BAD_ADDRESS_FLAG, java.math.BigDecimal MS_ID, String SECURITY_QUESTION, String SECURITY_QUESTION_ANSWER, java.math.BigDecimal Q_ID, String COMPANY_TAX_ID, java.math.BigDecimal REBILL_AMT, java.math.BigDecimal LOW_BAL_LEVEL, String ADDRESS3, String ADDRESS4, String COUNTRY, String PMT_TYPE_CODE, java.math.BigDecimal NO_OF_TAGS, java.math.BigDecimal CC_DEPOSIT_INCREMENT, java.math.BigDecimal EFT_DEPOSIT_INCREMENT, java.math.BigDecimal CC_MIN_BAL_INCREMENT, java.math.BigDecimal EFT_MIN_BAL_INCREMENT, java.math.BigDecimal CC_DEPOSIT_AMT, java.math.BigDecimal EFT_DEPOSIT_AMT, java.math.BigDecimal CC_MIN_BAL_AMT, java.math.BigDecimal EFT_MIN_BAL_AMT, String ALT_EMAIL, String PLAN, String HOME_PHO_EXT, String MOBILE_PHO_EXT, String SMS_ALERTS_OPT_IN) throws SQLException
  { _init_struct(true);
    setACCT_TYPE_CODE(ACCT_TYPE_CODE);
    setACCT_TYPE_DESCR(ACCT_TYPE_DESCR);
    setFIRST_NAME(FIRST_NAME);
    setMIDDLE_INITIAL(MIDDLE_INITIAL);
    setLAST_NAME(LAST_NAME);
    setADDRESS1(ADDRESS1);
    setADDRESS2(ADDRESS2);
    setCITY(CITY);
    setSTATE(STATE);
    setZIP_CODE(ZIP_CODE);
    setPLUS4(PLUS4);
    setHOME_PHO_NBR(HOME_PHO_NBR);
    setWORK_PHO_NBR(WORK_PHO_NBR);
    setMOBILE_PHO_NBR(MOBILE_PHO_NBR);
    setWORK_PHO_EXT(WORK_PHO_EXT);
    setDRIVER_LIC_NBR(DRIVER_LIC_NBR);
    setDRIVER_LIC_STATE(DRIVER_LIC_STATE);
    setCOMPANY_NAME(COMPANY_NAME);
    setBALANCE_AMT(BALANCE_AMT);
    setBAL_LAST_UPDATED(BAL_LAST_UPDATED);
    setEMAIL_ADDRESS(EMAIL_ADDRESS);
    setEMAIL_ADDRESS2(EMAIL_ADDRESS2);
    setEMAIL_ADDRESS3(EMAIL_ADDRESS3);
    setMO_STMT_FLAG(MO_STMT_FLAG);
    setBAD_ADDRESS_FLAG(BAD_ADDRESS_FLAG);
    setMS_ID(MS_ID);
    setSECURITY_QUESTION(SECURITY_QUESTION);
    setSECURITY_QUESTION_ANSWER(SECURITY_QUESTION_ANSWER);
    setQ_ID(Q_ID);
    setCOMPANY_TAX_ID(COMPANY_TAX_ID);
    setREBILL_AMT(REBILL_AMT);
    setLOW_BAL_LEVEL(LOW_BAL_LEVEL);
    setADDRESS3(ADDRESS3);
    setADDRESS4(ADDRESS4);
    setCOUNTRY(COUNTRY);
    setPMT_TYPE_CODE(PMT_TYPE_CODE);
    setNO_OF_TAGS(NO_OF_TAGS);
    setCC_DEPOSIT_INCREMENT(CC_DEPOSIT_INCREMENT);
    setEFT_DEPOSIT_INCREMENT(EFT_DEPOSIT_INCREMENT);
    setCC_MIN_BAL_INCREMENT(CC_MIN_BAL_INCREMENT);
    setEFT_MIN_BAL_INCREMENT(EFT_MIN_BAL_INCREMENT);
    setCC_DEPOSIT_AMT(CC_DEPOSIT_AMT);
    setEFT_DEPOSIT_AMT(EFT_DEPOSIT_AMT);
    setCC_MIN_BAL_AMT(CC_MIN_BAL_AMT);
    setEFT_MIN_BAL_AMT(EFT_MIN_BAL_AMT);
    setALT_EMAIL(ALT_EMAIL);
    setPLAN(PLAN);
    setHOME_PHO_EXT(HOME_PHO_EXT);
    setMOBILE_PHO_EXT(MOBILE_PHO_EXT);
    setSMS_ALERTS_OPT_IN(SMS_ALERTS_OPT_IN);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCOUNT_INFO_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCOUNT_INFO_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getACCT_TYPE_CODE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setACCT_TYPE_CODE(String ACCT_TYPE_CODE) throws SQLException
  { _struct.setAttribute(0, ACCT_TYPE_CODE); }


  public String getACCT_TYPE_DESCR() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setACCT_TYPE_DESCR(String ACCT_TYPE_DESCR) throws SQLException
  { _struct.setAttribute(1, ACCT_TYPE_DESCR); }


  public String getFIRST_NAME() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setFIRST_NAME(String FIRST_NAME) throws SQLException
  { _struct.setAttribute(2, FIRST_NAME); }


  public String getMIDDLE_INITIAL() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setMIDDLE_INITIAL(String MIDDLE_INITIAL) throws SQLException
  { _struct.setAttribute(3, MIDDLE_INITIAL); }


  public String getLAST_NAME() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLAST_NAME(String LAST_NAME) throws SQLException
  { _struct.setAttribute(4, LAST_NAME); }


  public String getADDRESS1() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setADDRESS1(String ADDRESS1) throws SQLException
  { _struct.setAttribute(5, ADDRESS1); }


  public String getADDRESS2() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setADDRESS2(String ADDRESS2) throws SQLException
  { _struct.setAttribute(6, ADDRESS2); }


  public String getCITY() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setCITY(String CITY) throws SQLException
  { _struct.setAttribute(7, CITY); }


  public String getSTATE() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setSTATE(String STATE) throws SQLException
  { _struct.setAttribute(8, STATE); }


  public String getZIP_CODE() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setZIP_CODE(String ZIP_CODE) throws SQLException
  { _struct.setAttribute(9, ZIP_CODE); }


  public String getPLUS4() throws SQLException
  { return (String) _struct.getAttribute(10); }

  public void setPLUS4(String PLUS4) throws SQLException
  { _struct.setAttribute(10, PLUS4); }


  public String getHOME_PHO_NBR() throws SQLException
  { return (String) _struct.getAttribute(11); }

  public void setHOME_PHO_NBR(String HOME_PHO_NBR) throws SQLException
  { _struct.setAttribute(11, HOME_PHO_NBR); }


  public String getWORK_PHO_NBR() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setWORK_PHO_NBR(String WORK_PHO_NBR) throws SQLException
  { _struct.setAttribute(12, WORK_PHO_NBR); }


  public String getMOBILE_PHO_NBR() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setMOBILE_PHO_NBR(String MOBILE_PHO_NBR) throws SQLException
  { _struct.setAttribute(13, MOBILE_PHO_NBR); }


  public String getWORK_PHO_EXT() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setWORK_PHO_EXT(String WORK_PHO_EXT) throws SQLException
  { _struct.setAttribute(14, WORK_PHO_EXT); }


  public String getDRIVER_LIC_NBR() throws SQLException
  { return (String) _struct.getAttribute(15); }

  public void setDRIVER_LIC_NBR(String DRIVER_LIC_NBR) throws SQLException
  { _struct.setAttribute(15, DRIVER_LIC_NBR); }


  public String getDRIVER_LIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(16); }

  public void setDRIVER_LIC_STATE(String DRIVER_LIC_STATE) throws SQLException
  { _struct.setAttribute(16, DRIVER_LIC_STATE); }


  public String getCOMPANY_NAME() throws SQLException
  { return (String) _struct.getAttribute(17); }

  public void setCOMPANY_NAME(String COMPANY_NAME) throws SQLException
  { _struct.setAttribute(17, COMPANY_NAME); }


  public java.math.BigDecimal getBALANCE_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(18); }

  public void setBALANCE_AMT(java.math.BigDecimal BALANCE_AMT) throws SQLException
  { _struct.setAttribute(18, BALANCE_AMT); }


  public java.sql.Timestamp getBAL_LAST_UPDATED() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(19); }

  public void setBAL_LAST_UPDATED(java.sql.Timestamp BAL_LAST_UPDATED) throws SQLException
  { _struct.setAttribute(19, BAL_LAST_UPDATED); }


  public String getEMAIL_ADDRESS() throws SQLException
  { return (String) _struct.getAttribute(20); }

  public void setEMAIL_ADDRESS(String EMAIL_ADDRESS) throws SQLException
  { _struct.setAttribute(20, EMAIL_ADDRESS); }


  public String getEMAIL_ADDRESS2() throws SQLException
  { return (String) _struct.getAttribute(21); }

  public void setEMAIL_ADDRESS2(String EMAIL_ADDRESS2) throws SQLException
  { _struct.setAttribute(21, EMAIL_ADDRESS2); }


  public String getEMAIL_ADDRESS3() throws SQLException
  { return (String) _struct.getAttribute(22); }

  public void setEMAIL_ADDRESS3(String EMAIL_ADDRESS3) throws SQLException
  { _struct.setAttribute(22, EMAIL_ADDRESS3); }


  public String getMO_STMT_FLAG() throws SQLException
  { return (String) _struct.getAttribute(23); }

  public void setMO_STMT_FLAG(String MO_STMT_FLAG) throws SQLException
  { _struct.setAttribute(23, MO_STMT_FLAG); }


  public String getBAD_ADDRESS_FLAG() throws SQLException
  { return (String) _struct.getAttribute(24); }

  public void setBAD_ADDRESS_FLAG(String BAD_ADDRESS_FLAG) throws SQLException
  { _struct.setAttribute(24, BAD_ADDRESS_FLAG); }


  public java.math.BigDecimal getMS_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(25); }

  public void setMS_ID(java.math.BigDecimal MS_ID) throws SQLException
  { _struct.setAttribute(25, MS_ID); }


  public String getSECURITY_QUESTION() throws SQLException
  { return (String) _struct.getAttribute(26); }

  public void setSECURITY_QUESTION(String SECURITY_QUESTION) throws SQLException
  { _struct.setAttribute(26, SECURITY_QUESTION); }


  public String getSECURITY_QUESTION_ANSWER() throws SQLException
  { return (String) _struct.getAttribute(27); }

  public void setSECURITY_QUESTION_ANSWER(String SECURITY_QUESTION_ANSWER) throws SQLException
  { _struct.setAttribute(27, SECURITY_QUESTION_ANSWER); }


  public java.math.BigDecimal getQ_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(28); }

  public void setQ_ID(java.math.BigDecimal Q_ID) throws SQLException
  { _struct.setAttribute(28, Q_ID); }


  public String getCOMPANY_TAX_ID() throws SQLException
  { return (String) _struct.getAttribute(29); }

  public void setCOMPANY_TAX_ID(String COMPANY_TAX_ID) throws SQLException
  { _struct.setAttribute(29, COMPANY_TAX_ID); }


  public java.math.BigDecimal getREBILL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(30); }

  public void setREBILL_AMT(java.math.BigDecimal REBILL_AMT) throws SQLException
  { _struct.setAttribute(30, REBILL_AMT); }


  public java.math.BigDecimal getLOW_BAL_LEVEL() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(31); }

  public void setLOW_BAL_LEVEL(java.math.BigDecimal LOW_BAL_LEVEL) throws SQLException
  { _struct.setAttribute(31, LOW_BAL_LEVEL); }


  public String getADDRESS3() throws SQLException
  { return (String) _struct.getAttribute(32); }

  public void setADDRESS3(String ADDRESS3) throws SQLException
  { _struct.setAttribute(32, ADDRESS3); }


  public String getADDRESS4() throws SQLException
  { return (String) _struct.getAttribute(33); }

  public void setADDRESS4(String ADDRESS4) throws SQLException
  { _struct.setAttribute(33, ADDRESS4); }


  public String getCOUNTRY() throws SQLException
  { return (String) _struct.getAttribute(34); }

  public void setCOUNTRY(String COUNTRY) throws SQLException
  { _struct.setAttribute(34, COUNTRY); }


  public String getPMT_TYPE_CODE() throws SQLException
  { return (String) _struct.getAttribute(35); }

  public void setPMT_TYPE_CODE(String PMT_TYPE_CODE) throws SQLException
  { _struct.setAttribute(35, PMT_TYPE_CODE); }


  public java.math.BigDecimal getNO_OF_TAGS() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(36); }

  public void setNO_OF_TAGS(java.math.BigDecimal NO_OF_TAGS) throws SQLException
  { _struct.setAttribute(36, NO_OF_TAGS); }


  public java.math.BigDecimal getCC_DEPOSIT_INCREMENT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(37); }

  public void setCC_DEPOSIT_INCREMENT(java.math.BigDecimal CC_DEPOSIT_INCREMENT) throws SQLException
  { _struct.setAttribute(37, CC_DEPOSIT_INCREMENT); }


  public java.math.BigDecimal getEFT_DEPOSIT_INCREMENT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(38); }

  public void setEFT_DEPOSIT_INCREMENT(java.math.BigDecimal EFT_DEPOSIT_INCREMENT) throws SQLException
  { _struct.setAttribute(38, EFT_DEPOSIT_INCREMENT); }


  public java.math.BigDecimal getCC_MIN_BAL_INCREMENT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(39); }

  public void setCC_MIN_BAL_INCREMENT(java.math.BigDecimal CC_MIN_BAL_INCREMENT) throws SQLException
  { _struct.setAttribute(39, CC_MIN_BAL_INCREMENT); }


  public java.math.BigDecimal getEFT_MIN_BAL_INCREMENT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(40); }

  public void setEFT_MIN_BAL_INCREMENT(java.math.BigDecimal EFT_MIN_BAL_INCREMENT) throws SQLException
  { _struct.setAttribute(40, EFT_MIN_BAL_INCREMENT); }


  public java.math.BigDecimal getCC_DEPOSIT_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(41); }

  public void setCC_DEPOSIT_AMT(java.math.BigDecimal CC_DEPOSIT_AMT) throws SQLException
  { _struct.setAttribute(41, CC_DEPOSIT_AMT); }


  public java.math.BigDecimal getEFT_DEPOSIT_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(42); }

  public void setEFT_DEPOSIT_AMT(java.math.BigDecimal EFT_DEPOSIT_AMT) throws SQLException
  { _struct.setAttribute(42, EFT_DEPOSIT_AMT); }


  public java.math.BigDecimal getCC_MIN_BAL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(43); }

  public void setCC_MIN_BAL_AMT(java.math.BigDecimal CC_MIN_BAL_AMT) throws SQLException
  { _struct.setAttribute(43, CC_MIN_BAL_AMT); }


  public java.math.BigDecimal getEFT_MIN_BAL_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(44); }

  public void setEFT_MIN_BAL_AMT(java.math.BigDecimal EFT_MIN_BAL_AMT) throws SQLException
  { _struct.setAttribute(44, EFT_MIN_BAL_AMT); }


  public String getALT_EMAIL() throws SQLException
  { return (String) _struct.getAttribute(45); }

  public void setALT_EMAIL(String ALT_EMAIL) throws SQLException
  { _struct.setAttribute(45, ALT_EMAIL); }


  public String getPLAN() throws SQLException
  { return (String) _struct.getAttribute(46); }

  public void setPLAN(String PLAN) throws SQLException
  { _struct.setAttribute(46, PLAN); }
  
  public String getHOME_PHO_EXT() throws SQLException
  { return (String) _struct.getAttribute(47); }

  public void setHOME_PHO_EXT(String HOME_PHO_EXT) throws SQLException
  { _struct.setAttribute(47, HOME_PHO_EXT); }
  
  public String getMOBILE_PHO_EXT() throws SQLException
  { return (String) _struct.getAttribute(48); }

  public void setMOBILE_PHO_EXT(String MOBILE_PHO_EXT) throws SQLException
  { _struct.setAttribute(48, MOBILE_PHO_EXT); }


  public String getSMS_ALERTS_OPT_IN() throws SQLException
  { return (String) _struct.getAttribute(49); }

  public void setSMS_ALERTS_OPT_IN(String SMS_ALERTS_OPT_IN) throws SQLException
  { _struct.setAttribute(49, SMS_ALERTS_OPT_IN); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ACCOUNT_INFO_REC" + "(" +
       ((getACCT_TYPE_CODE()==null)?"null": "'" + getACCT_TYPE_CODE()+"'" ) + "," +
       ((getACCT_TYPE_DESCR()==null)?"null": "'" + getACCT_TYPE_DESCR()+"'" ) + "," +
       ((getFIRST_NAME()==null)?"null": "'" + getFIRST_NAME()+"'" ) + "," +
       ((getMIDDLE_INITIAL()==null)?"null": "'" + getMIDDLE_INITIAL()+"'" ) + "," +
       ((getLAST_NAME()==null)?"null": "'" + getLAST_NAME()+"'" ) + "," +
       ((getADDRESS1()==null)?"null": "'" + getADDRESS1()+"'" ) + "," +
       ((getADDRESS2()==null)?"null": "'" + getADDRESS2()+"'" ) + "," +
       ((getCITY()==null)?"null": "'" + getCITY()+"'" ) + "," +
       ((getSTATE()==null)?"null": "'" + getSTATE()+"'" ) + "," +
       ((getZIP_CODE()==null)?"null": "'" + getZIP_CODE()+"'" ) + "," +
       ((getPLUS4()==null)?"null": "'" + getPLUS4()+"'" ) + "," +
       ((getHOME_PHO_NBR()==null)?"null": "'" + getHOME_PHO_NBR()+"'" ) + "," +
       ((getWORK_PHO_NBR()==null)?"null": "'" + getWORK_PHO_NBR()+"'" ) + "," +
       ((getMOBILE_PHO_NBR()==null)?"null": "'" + getMOBILE_PHO_NBR()+"'" ) + "," +
       ((getWORK_PHO_EXT()==null)?"null": "'" + getWORK_PHO_EXT()+"'" ) + "," +
       ((getDRIVER_LIC_NBR()==null)?"null": "'" + getDRIVER_LIC_NBR()+"'" ) + "," +
       ((getDRIVER_LIC_STATE()==null)?"null": "'" + getDRIVER_LIC_STATE()+"'" ) + "," +
       ((getCOMPANY_NAME()==null)?"null": "'" + getCOMPANY_NAME()+"'" ) + "," +
       getBALANCE_AMT() + "," +
       getBAL_LAST_UPDATED() + "," +
       ((getEMAIL_ADDRESS()==null)?"null": "'" + getEMAIL_ADDRESS()+"'" ) + "," +
       ((getEMAIL_ADDRESS2()==null)?"null": "'" + getEMAIL_ADDRESS2()+"'" ) + "," +
       ((getEMAIL_ADDRESS3()==null)?"null": "'" + getEMAIL_ADDRESS3()+"'" ) + "," +
       ((getMO_STMT_FLAG()==null)?"null": "'" + getMO_STMT_FLAG()+"'" ) + "," +
       ((getBAD_ADDRESS_FLAG()==null)?"null": "'" + getBAD_ADDRESS_FLAG()+"'" ) + "," +
       getMS_ID() + "," +
       ((getSECURITY_QUESTION()==null)?"null": "'" + getSECURITY_QUESTION()+"'" ) + "," +
       ((getSECURITY_QUESTION_ANSWER()==null)?"null": "'" + getSECURITY_QUESTION_ANSWER()+"'" ) + "," +
       getQ_ID() + "," +
       ((getCOMPANY_TAX_ID()==null)?"null": "'" + getCOMPANY_TAX_ID()+"'" ) + "," +
       getREBILL_AMT() + "," +
       getLOW_BAL_LEVEL() + "," +
       ((getADDRESS3()==null)?"null": "'" + getADDRESS3()+"'" ) + "," +
       ((getADDRESS4()==null)?"null": "'" + getADDRESS4()+"'" ) + "," +
       ((getCOUNTRY()==null)?"null": "'" + getCOUNTRY()+"'" ) + "," +
       ((getPMT_TYPE_CODE()==null)?"null": "'" + getPMT_TYPE_CODE()+"'" ) + "," +
       getNO_OF_TAGS() + "," +
       getCC_DEPOSIT_INCREMENT() + "," +
       getEFT_DEPOSIT_INCREMENT() + "," +
       getCC_MIN_BAL_INCREMENT() + "," +
       getEFT_MIN_BAL_INCREMENT() + "," +
       getCC_DEPOSIT_AMT() + "," +
       getEFT_DEPOSIT_AMT() + "," +
       getCC_MIN_BAL_AMT() + "," +
       getEFT_MIN_BAL_AMT() + "," +
       ((getALT_EMAIL()==null)?"null": "'" + getALT_EMAIL()+"'" ) + "," +
       ((getPLAN()==null)?"null": "'" + getPLAN()+"'" ) +
       ((getHOME_PHO_EXT()==null)?"null": "'" + getHOME_PHO_EXT()+"'" ) + "," +
       ((getMOBILE_PHO_EXT()==null)?"null": "'" + getMOBILE_PHO_EXT()+"'" ) + "," +
       ((getSMS_ALERTS_OPT_IN()==null)?"null": "'" + getSMS_ALERTS_OPT_IN()+"'" ) + "," +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
