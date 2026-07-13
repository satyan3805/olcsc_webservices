package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCOUNT_HISTORY_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCOUNT_HISTORY_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 91,91,12,12,12,12,12,12,12,12,2,12,12,12,2,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[16];
  protected static final OLC_ACCOUNT_HISTORY_REC _OLC_ACCOUNT_HISTORY_RECFactory = new OLC_ACCOUNT_HISTORY_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCOUNT_HISTORY_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[16], _sqlType, _factory); }
  public OLC_ACCOUNT_HISTORY_REC()
  { _init_struct(true); }
  public OLC_ACCOUNT_HISTORY_REC(java.sql.Timestamp TRXN_DATE, java.sql.Timestamp POSTED_DATE, String TAG_ID, String LICENSE_PLATE, String LICENSE_STATE, String NICKNAME, String LANE_NAME, String LANE_FULL_NAME, String LOCATION_NAME, String TRANS_TYPE, java.math.BigDecimal AMOUNT, String UNIT_ID, String DIRECTION, String PARKING_REPORT_URL, java.math.BigDecimal SERIAL_NUM, String VEHICLE_CLASS_CODE) throws SQLException
  { _init_struct(true);
    setTRXN_DATE(TRXN_DATE);
    setPOSTED_DATE(POSTED_DATE);
    setTAG_ID(TAG_ID);
    setLICENSE_PLATE(LICENSE_PLATE);
    setLICENSE_STATE(LICENSE_STATE);
    setNICKNAME(NICKNAME);
    setLANE_NAME(LANE_NAME);
    setLANE_FULL_NAME(LANE_FULL_NAME);
    setLOCATION_NAME(LOCATION_NAME);
    setTRANS_TYPE(TRANS_TYPE);
    setAMOUNT(AMOUNT);
    setUNIT_ID(UNIT_ID);
    setDIRECTION(DIRECTION);
    setPARKING_REPORT_URL(PARKING_REPORT_URL);
    setSERIAL_NUM(SERIAL_NUM);
    setVEHICLE_CLASS_CODE(VEHICLE_CLASS_CODE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCOUNT_HISTORY_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCOUNT_HISTORY_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.sql.Timestamp getTRXN_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(0); }

  public void setTRXN_DATE(java.sql.Timestamp TRXN_DATE) throws SQLException
  { _struct.setAttribute(0, TRXN_DATE); }


  public java.sql.Timestamp getPOSTED_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setPOSTED_DATE(java.sql.Timestamp POSTED_DATE) throws SQLException
  { _struct.setAttribute(1, POSTED_DATE); }


  public String getTAG_ID() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setTAG_ID(String TAG_ID) throws SQLException
  { _struct.setAttribute(2, TAG_ID); }


  public String getLICENSE_PLATE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setLICENSE_PLATE(String LICENSE_PLATE) throws SQLException
  { _struct.setAttribute(3, LICENSE_PLATE); }


  public String getLICENSE_STATE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLICENSE_STATE(String LICENSE_STATE) throws SQLException
  { _struct.setAttribute(4, LICENSE_STATE); }


  public String getNICKNAME() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setNICKNAME(String NICKNAME) throws SQLException
  { _struct.setAttribute(5, NICKNAME); }


  public String getLANE_NAME() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setLANE_NAME(String LANE_NAME) throws SQLException
  { _struct.setAttribute(6, LANE_NAME); }


  public String getLANE_FULL_NAME() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setLANE_FULL_NAME(String LANE_FULL_NAME) throws SQLException
  { _struct.setAttribute(7, LANE_FULL_NAME); }


  public String getLOCATION_NAME() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLOCATION_NAME(String LOCATION_NAME) throws SQLException
  { _struct.setAttribute(8, LOCATION_NAME); }


  public String getTRANS_TYPE() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setTRANS_TYPE(String TRANS_TYPE) throws SQLException
  { _struct.setAttribute(9, TRANS_TYPE); }


  public java.math.BigDecimal getAMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setAMOUNT(java.math.BigDecimal AMOUNT) throws SQLException
  { _struct.setAttribute(10, AMOUNT); }


  public String getUNIT_ID() throws SQLException
  { return (String) _struct.getAttribute(11); }

  public void setUNIT_ID(String UNIT_ID) throws SQLException
  { _struct.setAttribute(11, UNIT_ID); }


  public String getDIRECTION() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setDIRECTION(String DIRECTION) throws SQLException
  { _struct.setAttribute(12, DIRECTION); }


  public String getPARKING_REPORT_URL() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setPARKING_REPORT_URL(String PARKING_REPORT_URL) throws SQLException
  { _struct.setAttribute(13, PARKING_REPORT_URL); }


  public java.math.BigDecimal getSERIAL_NUM() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(14); }

  public void setSERIAL_NUM(java.math.BigDecimal SERIAL_NUM) throws SQLException
  { _struct.setAttribute(14, SERIAL_NUM); }


  public String getVEHICLE_CLASS_CODE() throws SQLException
  { return (String) _struct.getAttribute(15); }

  public void setVEHICLE_CLASS_CODE(String VEHICLE_CLASS_CODE) throws SQLException
  { _struct.setAttribute(15, VEHICLE_CLASS_CODE); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ACCOUNT_HISTORY_REC" + "(" +
       getTRXN_DATE() + "," +
       getPOSTED_DATE() + "," +
       ((getTAG_ID()==null)?"null": "'" + getTAG_ID()+"'" ) + "," +
       ((getLICENSE_PLATE()==null)?"null": "'" + getLICENSE_PLATE()+"'" ) + "," +
       ((getLICENSE_STATE()==null)?"null": "'" + getLICENSE_STATE()+"'" ) + "," +
       ((getNICKNAME()==null)?"null": "'" + getNICKNAME()+"'" ) + "," +
       ((getLANE_NAME()==null)?"null": "'" + getLANE_NAME()+"'" ) + "," +
       ((getLANE_FULL_NAME()==null)?"null": "'" + getLANE_FULL_NAME()+"'" ) + "," +
       ((getLOCATION_NAME()==null)?"null": "'" + getLOCATION_NAME()+"'" ) + "," +
       ((getTRANS_TYPE()==null)?"null": "'" + getTRANS_TYPE()+"'" ) + "," +
       getAMOUNT() + "," +
       ((getUNIT_ID()==null)?"null": "'" + getUNIT_ID()+"'" ) + "," +
       ((getDIRECTION()==null)?"null": "'" + getDIRECTION()+"'" ) + "," +
       ((getPARKING_REPORT_URL()==null)?"null": "'" + getPARKING_REPORT_URL()+"'" ) + "," +
       getSERIAL_NUM() + "," +
       ((getVEHICLE_CLASS_CODE()==null)?"null": "'" + getVEHICLE_CLASS_CODE()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
