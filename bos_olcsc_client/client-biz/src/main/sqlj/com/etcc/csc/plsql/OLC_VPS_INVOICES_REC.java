package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_INVOICES_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_INVOICES_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,91,91,12,12,12,12,2,2,2,2,2003,2,12,12,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[17];
  static
  {
    _factory[11] = OLC_VIOLATION_ARR.getORADataFactory();
  }
  protected static final OLC_VPS_INVOICES_REC _OLC_VPS_INVOICES_RECFactory = new OLC_VPS_INVOICES_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_INVOICES_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[17], _sqlType, _factory); }
  public OLC_VPS_INVOICES_REC()
  { _init_struct(true); }
  public OLC_VPS_INVOICES_REC(java.math.BigDecimal INVOICE_ID, java.sql.Timestamp INVOICE_DATE, java.sql.Timestamp CURR_DUE_DATE, String FIRST_NAME, String LAST_NAME, String LIC_PLATE_NBR, String LIC_STATE, java.math.BigDecimal INVOICE_AMOUNT, java.math.BigDecimal VEA_AMOUNT, java.math.BigDecimal ONLINE_FEE, java.math.BigDecimal VIOLATOR_ID, OLC_VIOLATION_ARR VIOLATIONS, java.math.BigDecimal CA_ACCOUNT_ID, String CA_PHONE_NUMBER, String CA_COMPANY, java.math.BigDecimal TOLL_TRXN_AMOUNT, java.math.BigDecimal RETAIL_CHARGE_AMOUNT) throws SQLException
  { _init_struct(true);
    setINVOICE_ID(INVOICE_ID);
    setINVOICE_DATE(INVOICE_DATE);
    setCURR_DUE_DATE(CURR_DUE_DATE);
    setFIRST_NAME(FIRST_NAME);
    setLAST_NAME(LAST_NAME);
    setLIC_PLATE_NBR(LIC_PLATE_NBR);
    setLIC_STATE(LIC_STATE);
    setINVOICE_AMOUNT(INVOICE_AMOUNT);
    setVEA_AMOUNT(VEA_AMOUNT);
    setONLINE_FEE(ONLINE_FEE);
    setVIOLATOR_ID(VIOLATOR_ID);
    setVIOLATIONS(VIOLATIONS);
    setCA_ACCOUNT_ID(CA_ACCOUNT_ID);
    setCA_PHONE_NUMBER(CA_PHONE_NUMBER);
    setCA_COMPANY(CA_COMPANY);
    setTOLL_TRXN_AMOUNT(TOLL_TRXN_AMOUNT);
    setRETAIL_CHARGE_AMOUNT(RETAIL_CHARGE_AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_INVOICES_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_INVOICES_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getINVOICE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setINVOICE_ID(java.math.BigDecimal INVOICE_ID) throws SQLException
  { _struct.setAttribute(0, INVOICE_ID); }


  public java.sql.Timestamp getINVOICE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setINVOICE_DATE(java.sql.Timestamp INVOICE_DATE) throws SQLException
  { _struct.setAttribute(1, INVOICE_DATE); }


  public java.sql.Timestamp getCURR_DUE_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(2); }

  public void setCURR_DUE_DATE(java.sql.Timestamp CURR_DUE_DATE) throws SQLException
  { _struct.setAttribute(2, CURR_DUE_DATE); }


  public String getFIRST_NAME() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setFIRST_NAME(String FIRST_NAME) throws SQLException
  { _struct.setAttribute(3, FIRST_NAME); }


  public String getLAST_NAME() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLAST_NAME(String LAST_NAME) throws SQLException
  { _struct.setAttribute(4, LAST_NAME); }


  public String getLIC_PLATE_NBR() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setLIC_PLATE_NBR(String LIC_PLATE_NBR) throws SQLException
  { _struct.setAttribute(5, LIC_PLATE_NBR); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(6, LIC_STATE); }


  public java.math.BigDecimal getINVOICE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setINVOICE_AMOUNT(java.math.BigDecimal INVOICE_AMOUNT) throws SQLException
  { _struct.setAttribute(7, INVOICE_AMOUNT); }


  public java.math.BigDecimal getVEA_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setVEA_AMOUNT(java.math.BigDecimal VEA_AMOUNT) throws SQLException
  { _struct.setAttribute(8, VEA_AMOUNT); }


  public java.math.BigDecimal getONLINE_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(9); }

  public void setONLINE_FEE(java.math.BigDecimal ONLINE_FEE) throws SQLException
  { _struct.setAttribute(9, ONLINE_FEE); }


  public java.math.BigDecimal getVIOLATOR_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setVIOLATOR_ID(java.math.BigDecimal VIOLATOR_ID) throws SQLException
  { _struct.setAttribute(10, VIOLATOR_ID); }


  public OLC_VIOLATION_ARR getVIOLATIONS() throws SQLException
  { return (OLC_VIOLATION_ARR) _struct.getAttribute(11); }

  public void setVIOLATIONS(OLC_VIOLATION_ARR VIOLATIONS) throws SQLException
  { _struct.setAttribute(11, VIOLATIONS); }


  public java.math.BigDecimal getCA_ACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(12); }

  public void setCA_ACCOUNT_ID(java.math.BigDecimal CA_ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(12, CA_ACCOUNT_ID); }


  public String getCA_PHONE_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setCA_PHONE_NUMBER(String CA_PHONE_NUMBER) throws SQLException
  { _struct.setAttribute(13, CA_PHONE_NUMBER); }


  public String getCA_COMPANY() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setCA_COMPANY(String CA_COMPANY) throws SQLException
  { _struct.setAttribute(14, CA_COMPANY); }


  public java.math.BigDecimal getTOLL_TRXN_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(15); }

  public void setTOLL_TRXN_AMOUNT(java.math.BigDecimal TOLL_TRXN_AMOUNT) throws SQLException
  { _struct.setAttribute(15, TOLL_TRXN_AMOUNT); }


  public java.math.BigDecimal getRETAIL_CHARGE_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(16); }

  public void setRETAIL_CHARGE_AMOUNT(java.math.BigDecimal RETAIL_CHARGE_AMOUNT) throws SQLException
  { _struct.setAttribute(16, RETAIL_CHARGE_AMOUNT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VPS_INVOICES_REC" + "(" +
       getINVOICE_ID() + "," +
       getINVOICE_DATE() + "," +
       getCURR_DUE_DATE() + "," +
       ((getFIRST_NAME()==null)?"null": "'" + getFIRST_NAME()+"'" ) + "," +
       ((getLAST_NAME()==null)?"null": "'" + getLAST_NAME()+"'" ) + "," +
       ((getLIC_PLATE_NBR()==null)?"null": "'" + getLIC_PLATE_NBR()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       getINVOICE_AMOUNT() + "," +
       getVEA_AMOUNT() + "," +
       getONLINE_FEE() + "," +
       getVIOLATOR_ID() + "," +
       getVIOLATIONS() + "," +
       getCA_ACCOUNT_ID() + "," +
       ((getCA_PHONE_NUMBER()==null)?"null": "'" + getCA_PHONE_NUMBER()+"'" ) + "," +
       ((getCA_COMPANY()==null)?"null": "'" + getCA_COMPANY()+"'" ) + "," +
       getTOLL_TRXN_AMOUNT() + "," +
       getRETAIL_CHARGE_AMOUNT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
