package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VIEW_MONTHLY_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VIEW_MONTHLY_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 91,91,12,12,12,12,12,12,12,12,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[12];
  protected static final OLC_VIEW_MONTHLY_REC _OLC_VIEW_MONTHLY_RECFactory = new OLC_VIEW_MONTHLY_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VIEW_MONTHLY_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[12], _sqlType, _factory); }
  public OLC_VIEW_MONTHLY_REC()
  { _init_struct(true); }
  public OLC_VIEW_MONTHLY_REC(java.sql.Timestamp TRANSACTION_DATE, java.sql.Timestamp POSTED_DATE, String TAG_ID, String UNIT_ID, String LICENSE_PLATE, String LANE, String LANE_FULL_NAME, String DIRECTION, String LOCATION, String DESCRIPTION, java.math.BigDecimal AMOUNT, java.math.BigDecimal SERIAL_NUMBER) throws SQLException
  { _init_struct(true);
    setTRANSACTION_DATE(TRANSACTION_DATE);
    setPOSTED_DATE(POSTED_DATE);
    setTAG_ID(TAG_ID);
    setUNIT_ID(UNIT_ID);
    setLICENSE_PLATE(LICENSE_PLATE);
    setLANE(LANE);
    setLANE_FULL_NAME(LANE_FULL_NAME);
    setDIRECTION(DIRECTION);
    setLOCATION(LOCATION);
    setDESCRIPTION(DESCRIPTION);
    setAMOUNT(AMOUNT);
    setSERIAL_NUMBER(SERIAL_NUMBER);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VIEW_MONTHLY_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VIEW_MONTHLY_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.sql.Timestamp getTRANSACTION_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(0); }

  public void setTRANSACTION_DATE(java.sql.Timestamp TRANSACTION_DATE) throws SQLException
  { _struct.setAttribute(0, TRANSACTION_DATE); }


  public java.sql.Timestamp getPOSTED_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setPOSTED_DATE(java.sql.Timestamp POSTED_DATE) throws SQLException
  { _struct.setAttribute(1, POSTED_DATE); }


  public String getTAG_ID() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setTAG_ID(String TAG_ID) throws SQLException
  { _struct.setAttribute(2, TAG_ID); }


  public String getUNIT_ID() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setUNIT_ID(String UNIT_ID) throws SQLException
  { _struct.setAttribute(3, UNIT_ID); }


  public String getLICENSE_PLATE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLICENSE_PLATE(String LICENSE_PLATE) throws SQLException
  { _struct.setAttribute(4, LICENSE_PLATE); }


  public String getLANE() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setLANE(String LANE) throws SQLException
  { _struct.setAttribute(5, LANE); }


  public String getLANE_FULL_NAME() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setLANE_FULL_NAME(String LANE_FULL_NAME) throws SQLException
  { _struct.setAttribute(6, LANE_FULL_NAME); }


  public String getDIRECTION() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setDIRECTION(String DIRECTION) throws SQLException
  { _struct.setAttribute(7, DIRECTION); }


  public String getLOCATION() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setLOCATION(String LOCATION) throws SQLException
  { _struct.setAttribute(8, LOCATION); }


  public String getDESCRIPTION() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setDESCRIPTION(String DESCRIPTION) throws SQLException
  { _struct.setAttribute(9, DESCRIPTION); }


  public java.math.BigDecimal getAMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(10); }

  public void setAMOUNT(java.math.BigDecimal AMOUNT) throws SQLException
  { _struct.setAttribute(10, AMOUNT); }


  public java.math.BigDecimal getSERIAL_NUMBER() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(11); }

  public void setSERIAL_NUMBER(java.math.BigDecimal SERIAL_NUMBER) throws SQLException
  { _struct.setAttribute(11, SERIAL_NUMBER); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VIEW_MONTHLY_REC" + "(" +
       getTRANSACTION_DATE() + "," +
       getPOSTED_DATE() + "," +
       ((getTAG_ID()==null)?"null": "'" + getTAG_ID()+"'" ) + "," +
       ((getUNIT_ID()==null)?"null": "'" + getUNIT_ID()+"'" ) + "," +
       ((getLICENSE_PLATE()==null)?"null": "'" + getLICENSE_PLATE()+"'" ) + "," +
       ((getLANE()==null)?"null": "'" + getLANE()+"'" ) + "," +
       ((getLANE_FULL_NAME()==null)?"null": "'" + getLANE_FULL_NAME()+"'" ) + "," +
       ((getDIRECTION()==null)?"null": "'" + getDIRECTION()+"'" ) + "," +
       ((getLOCATION()==null)?"null": "'" + getLOCATION()+"'" ) + "," +
       ((getDESCRIPTION()==null)?"null": "'" + getDESCRIPTION()+"'" ) + "," +
       getAMOUNT() + "," +
       getSERIAL_NUMBER() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
