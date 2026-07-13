package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCOUNT_TAG_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCOUNT_TAG_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,1,2,12,12,12,12,12,12,12,12,12,12,12,12,12,91,12,12,12,12,91,91,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[24];
  protected static final OLC_ACCOUNT_TAG_REC _OLC_ACCOUNT_TAG_RECFactory = new OLC_ACCOUNT_TAG_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCOUNT_TAG_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[24], _sqlType, _factory); }
  public OLC_ACCOUNT_TAG_REC()
  { _init_struct(true); }
  public OLC_ACCOUNT_TAG_REC(java.math.BigDecimal ACCT_TAG_SEQ, String ACCT_TAG_STATUS, java.math.BigDecimal ACCT_VEHICLE_ID, String BARCODE_PREFIX, String TAG_ID, String AGENCY_ID, String AGENCY_DESC, String LIC_PLATE, String LIC_STATE, String VEHICLE_YEAR, String VEHICLE_COLOR, String VEHICLE_MAKE, String VEHICLE_MODEL, String VEHICLE_CLASS_CODE, String VEHICLE_CLASS_DESC, String TEMP_PLATE_FLAG, java.sql.Timestamp PLATE_EXPIR_DATE, String TAG_STATUS_DESC, String NICKNAME, String TAG_TYPE_CODE, String HAS_VIOL_INVOICES, java.sql.Timestamp PBP_START_DATE, java.sql.Timestamp PBP_END_DATE, String TAGSTATUSFLIP) throws SQLException
  { _init_struct(true);
    setACCT_TAG_SEQ(ACCT_TAG_SEQ);
    setACCT_TAG_STATUS(ACCT_TAG_STATUS);
    setACCT_VEHICLE_ID(ACCT_VEHICLE_ID);
    setBARCODE_PREFIX(BARCODE_PREFIX);
    setTAG_ID(TAG_ID);
    setAGENCY_ID(AGENCY_ID);
    setAGENCY_DESC(AGENCY_DESC);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setVEHICLE_YEAR(VEHICLE_YEAR);
    setVEHICLE_COLOR(VEHICLE_COLOR);
    setVEHICLE_MAKE(VEHICLE_MAKE);
    setVEHICLE_MODEL(VEHICLE_MODEL);
    setVEHICLE_CLASS_CODE(VEHICLE_CLASS_CODE);
    setVEHICLE_CLASS_DESC(VEHICLE_CLASS_DESC);
    setTEMP_PLATE_FLAG(TEMP_PLATE_FLAG);
    setPLATE_EXPIR_DATE(PLATE_EXPIR_DATE);
    setTAG_STATUS_DESC(TAG_STATUS_DESC);
    setNICKNAME(NICKNAME);
    setTAG_TYPE_CODE(TAG_TYPE_CODE);
    setHAS_VIOL_INVOICES(HAS_VIOL_INVOICES);
    setPBP_START_DATE(PBP_START_DATE);
    setPBP_END_DATE(PBP_END_DATE);
    setTAGSTATUSFLIP(TAGSTATUSFLIP);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCOUNT_TAG_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCOUNT_TAG_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCT_TAG_SEQ() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCT_TAG_SEQ(java.math.BigDecimal ACCT_TAG_SEQ) throws SQLException
  { _struct.setAttribute(0, ACCT_TAG_SEQ); }


  public String getACCT_TAG_STATUS() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setACCT_TAG_STATUS(String ACCT_TAG_STATUS) throws SQLException
  { _struct.setAttribute(1, ACCT_TAG_STATUS); }


  public java.math.BigDecimal getACCT_VEHICLE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setACCT_VEHICLE_ID(java.math.BigDecimal ACCT_VEHICLE_ID) throws SQLException
  { _struct.setAttribute(2, ACCT_VEHICLE_ID); }


  public String getBARCODE_PREFIX() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setBARCODE_PREFIX(String BARCODE_PREFIX) throws SQLException
  { _struct.setAttribute(3, BARCODE_PREFIX); }


  public String getTAG_ID() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setTAG_ID(String TAG_ID) throws SQLException
  { _struct.setAttribute(4, TAG_ID); }


  public String getAGENCY_ID() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setAGENCY_ID(String AGENCY_ID) throws SQLException
  { _struct.setAttribute(5, AGENCY_ID); }


  public String getAGENCY_DESC() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setAGENCY_DESC(String AGENCY_DESC) throws SQLException
  { _struct.setAttribute(6, AGENCY_DESC); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(7, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(8, LIC_STATE); }


  public String getVEHICLE_YEAR() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setVEHICLE_YEAR(String VEHICLE_YEAR) throws SQLException
  { _struct.setAttribute(9, VEHICLE_YEAR); }


  public String getVEHICLE_COLOR() throws SQLException
  { return (String) _struct.getAttribute(10); }

  public void setVEHICLE_COLOR(String VEHICLE_COLOR) throws SQLException
  { _struct.setAttribute(10, VEHICLE_COLOR); }


  public String getVEHICLE_MAKE() throws SQLException
  { return (String) _struct.getAttribute(11); }

  public void setVEHICLE_MAKE(String VEHICLE_MAKE) throws SQLException
  { _struct.setAttribute(11, VEHICLE_MAKE); }


  public String getVEHICLE_MODEL() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setVEHICLE_MODEL(String VEHICLE_MODEL) throws SQLException
  { _struct.setAttribute(12, VEHICLE_MODEL); }


  public String getVEHICLE_CLASS_CODE() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setVEHICLE_CLASS_CODE(String VEHICLE_CLASS_CODE) throws SQLException
  { _struct.setAttribute(13, VEHICLE_CLASS_CODE); }


  public String getVEHICLE_CLASS_DESC() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setVEHICLE_CLASS_DESC(String VEHICLE_CLASS_DESC) throws SQLException
  { _struct.setAttribute(14, VEHICLE_CLASS_DESC); }


  public String getTEMP_PLATE_FLAG() throws SQLException
  { return (String) _struct.getAttribute(15); }

  public void setTEMP_PLATE_FLAG(String TEMP_PLATE_FLAG) throws SQLException
  { _struct.setAttribute(15, TEMP_PLATE_FLAG); }


  public java.sql.Timestamp getPLATE_EXPIR_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(16); }

  public void setPLATE_EXPIR_DATE(java.sql.Timestamp PLATE_EXPIR_DATE) throws SQLException
  { _struct.setAttribute(16, PLATE_EXPIR_DATE); }


  public String getTAG_STATUS_DESC() throws SQLException
  { return (String) _struct.getAttribute(17); }

  public void setTAG_STATUS_DESC(String TAG_STATUS_DESC) throws SQLException
  { _struct.setAttribute(17, TAG_STATUS_DESC); }


  public String getNICKNAME() throws SQLException
  { return (String) _struct.getAttribute(18); }

  public void setNICKNAME(String NICKNAME) throws SQLException
  { _struct.setAttribute(18, NICKNAME); }


  public String getTAG_TYPE_CODE() throws SQLException
  { return (String) _struct.getAttribute(19); }

  public void setTAG_TYPE_CODE(String TAG_TYPE_CODE) throws SQLException
  { _struct.setAttribute(19, TAG_TYPE_CODE); }


  public String getHAS_VIOL_INVOICES() throws SQLException
  { return (String) _struct.getAttribute(20); }

  public void setHAS_VIOL_INVOICES(String HAS_VIOL_INVOICES) throws SQLException
  { _struct.setAttribute(20, HAS_VIOL_INVOICES); }


  public java.sql.Timestamp getPBP_START_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(21); }

  public void setPBP_START_DATE(java.sql.Timestamp PBP_START_DATE) throws SQLException
  { _struct.setAttribute(21, PBP_START_DATE); }


  public java.sql.Timestamp getPBP_END_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(22); }

  public void setPBP_END_DATE(java.sql.Timestamp PBP_END_DATE) throws SQLException
  { _struct.setAttribute(22, PBP_END_DATE); }


  public String getTAGSTATUSFLIP() throws SQLException
  { return (String) _struct.getAttribute(23); }

  public void setTAGSTATUSFLIP(String TAGSTATUSFLIP) throws SQLException
  { _struct.setAttribute(23, TAGSTATUSFLIP); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ACCOUNT_TAG_REC" + "(" +
       getACCT_TAG_SEQ() + "," +
       ((getACCT_TAG_STATUS()==null)?"null": "'" + getACCT_TAG_STATUS()+"'" ) + "," +
       getACCT_VEHICLE_ID() + "," +
       ((getBARCODE_PREFIX()==null)?"null": "'" + getBARCODE_PREFIX()+"'" ) + "," +
       ((getTAG_ID()==null)?"null": "'" + getTAG_ID()+"'" ) + "," +
       ((getAGENCY_ID()==null)?"null": "'" + getAGENCY_ID()+"'" ) + "," +
       ((getAGENCY_DESC()==null)?"null": "'" + getAGENCY_DESC()+"'" ) + "," +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       ((getVEHICLE_YEAR()==null)?"null": "'" + getVEHICLE_YEAR()+"'" ) + "," +
       ((getVEHICLE_COLOR()==null)?"null": "'" + getVEHICLE_COLOR()+"'" ) + "," +
       ((getVEHICLE_MAKE()==null)?"null": "'" + getVEHICLE_MAKE()+"'" ) + "," +
       ((getVEHICLE_MODEL()==null)?"null": "'" + getVEHICLE_MODEL()+"'" ) + "," +
       ((getVEHICLE_CLASS_CODE()==null)?"null": "'" + getVEHICLE_CLASS_CODE()+"'" ) + "," +
       ((getVEHICLE_CLASS_DESC()==null)?"null": "'" + getVEHICLE_CLASS_DESC()+"'" ) + "," +
       ((getTEMP_PLATE_FLAG()==null)?"null": "'" + getTEMP_PLATE_FLAG()+"'" ) + "," +
       getPLATE_EXPIR_DATE() + "," +
       ((getTAG_STATUS_DESC()==null)?"null": "'" + getTAG_STATUS_DESC()+"'" ) + "," +
       ((getNICKNAME()==null)?"null": "'" + getNICKNAME()+"'" ) + "," +
       ((getTAG_TYPE_CODE()==null)?"null": "'" + getTAG_TYPE_CODE()+"'" ) + "," +
       ((getHAS_VIOL_INVOICES()==null)?"null": "'" + getHAS_VIOL_INVOICES()+"'" ) + "," +
       getPBP_START_DATE() + "," +
       getPBP_END_DATE() + "," +
       ((getTAGSTATUSFLIP()==null)?"null": "'" + getTAGSTATUSFLIP()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
