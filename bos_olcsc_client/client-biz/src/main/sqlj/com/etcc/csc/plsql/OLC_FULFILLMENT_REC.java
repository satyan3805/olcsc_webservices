package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_FULFILLMENT_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_FULFILLMENT_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,2,91,91,12,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[9];
  protected static final OLC_FULFILLMENT_REC _OLC_FULFILLMENT_RECFactory = new OLC_FULFILLMENT_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_FULFILLMENT_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[9], _sqlType, _factory); }
  public OLC_FULFILLMENT_REC()
  { _init_struct(true); }
  public OLC_FULFILLMENT_REC(String FULFILLMENT_TYPE, String FULFILLMENT_STATUS, java.math.BigDecimal UNITS, java.sql.Timestamp REQUESTED_DATE, java.sql.Timestamp FULFILLMENT_DATE, String LOCATION, String TAG_ID, String LIC_PLATE, java.math.BigDecimal TRANSACTION_ID) throws SQLException
  { _init_struct(true);
    setFULFILLMENT_TYPE(FULFILLMENT_TYPE);
    setFULFILLMENT_STATUS(FULFILLMENT_STATUS);
    setUNITS(UNITS);
    setREQUESTED_DATE(REQUESTED_DATE);
    setFULFILLMENT_DATE(FULFILLMENT_DATE);
    setLOCATION(LOCATION);
    setTAG_ID(TAG_ID);
    setLIC_PLATE(LIC_PLATE);
    setTRANSACTION_ID(TRANSACTION_ID);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_FULFILLMENT_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_FULFILLMENT_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getFULFILLMENT_TYPE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setFULFILLMENT_TYPE(String FULFILLMENT_TYPE) throws SQLException
  { _struct.setAttribute(0, FULFILLMENT_TYPE); }


  public String getFULFILLMENT_STATUS() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setFULFILLMENT_STATUS(String FULFILLMENT_STATUS) throws SQLException
  { _struct.setAttribute(1, FULFILLMENT_STATUS); }


  public java.math.BigDecimal getUNITS() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setUNITS(java.math.BigDecimal UNITS) throws SQLException
  { _struct.setAttribute(2, UNITS); }


  public java.sql.Timestamp getREQUESTED_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(3); }

  public void setREQUESTED_DATE(java.sql.Timestamp REQUESTED_DATE) throws SQLException
  { _struct.setAttribute(3, REQUESTED_DATE); }


  public java.sql.Timestamp getFULFILLMENT_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(4); }

  public void setFULFILLMENT_DATE(java.sql.Timestamp FULFILLMENT_DATE) throws SQLException
  { _struct.setAttribute(4, FULFILLMENT_DATE); }


  public String getLOCATION() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setLOCATION(String LOCATION) throws SQLException
  { _struct.setAttribute(5, LOCATION); }


  public String getTAG_ID() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setTAG_ID(String TAG_ID) throws SQLException
  { _struct.setAttribute(6, TAG_ID); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(7, LIC_PLATE); }


  public java.math.BigDecimal getTRANSACTION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setTRANSACTION_ID(java.math.BigDecimal TRANSACTION_ID) throws SQLException
  { _struct.setAttribute(8, TRANSACTION_ID); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_FULFILLMENT_REC" + "(" +
       ((getFULFILLMENT_TYPE()==null)?"null": "'" + getFULFILLMENT_TYPE()+"'" ) + "," +
       ((getFULFILLMENT_STATUS()==null)?"null": "'" + getFULFILLMENT_STATUS()+"'" ) + "," +
       getUNITS() + "," +
       getREQUESTED_DATE() + "," +
       getFULFILLMENT_DATE() + "," +
       ((getLOCATION()==null)?"null": "'" + getLOCATION()+"'" ) + "," +
       ((getTAG_ID()==null)?"null": "'" + getTAG_ID()+"'" ) + "," +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       getTRANSACTION_ID() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
