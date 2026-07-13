package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_TRANS_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_TRANS_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 91,12,12,12,12,12,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[9];
  protected static final OLC_TRANS_REC _OLC_TRANS_RECFactory = new OLC_TRANS_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_TRANS_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[9], _sqlType, _factory); }
  public OLC_TRANS_REC()
  { _init_struct(true); }
  public OLC_TRANS_REC(java.sql.Timestamp TRANSACTION_DATE, String TAG_ID, String LIC_PLATE, String LIC_STATE, String LANE, String DIRECTION, String LOCATION, String TRANS_TYPE_DESCR, java.math.BigDecimal AMOUNT) throws SQLException
  { _init_struct(true);
    setTRANSACTION_DATE(TRANSACTION_DATE);
    setTAG_ID(TAG_ID);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setLANE(LANE);
    setDIRECTION(DIRECTION);
    setLOCATION(LOCATION);
    setTRANS_TYPE_DESCR(TRANS_TYPE_DESCR);
    setAMOUNT(AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_TRANS_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_TRANS_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.sql.Timestamp getTRANSACTION_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(0); }

  public void setTRANSACTION_DATE(java.sql.Timestamp TRANSACTION_DATE) throws SQLException
  { _struct.setAttribute(0, TRANSACTION_DATE); }


  public String getTAG_ID() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setTAG_ID(String TAG_ID) throws SQLException
  { _struct.setAttribute(1, TAG_ID); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(2, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(3, LIC_STATE); }


  public String getLANE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLANE(String LANE) throws SQLException
  { _struct.setAttribute(4, LANE); }


  public String getDIRECTION() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setDIRECTION(String DIRECTION) throws SQLException
  { _struct.setAttribute(5, DIRECTION); }


  public String getLOCATION() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setLOCATION(String LOCATION) throws SQLException
  { _struct.setAttribute(6, LOCATION); }


  public String getTRANS_TYPE_DESCR() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setTRANS_TYPE_DESCR(String TRANS_TYPE_DESCR) throws SQLException
  { _struct.setAttribute(7, TRANS_TYPE_DESCR); }


  public java.math.BigDecimal getAMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setAMOUNT(java.math.BigDecimal AMOUNT) throws SQLException
  { _struct.setAttribute(8, AMOUNT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_TRANS_REC" + "(" +
       getTRANSACTION_DATE() + "," +
       ((getTAG_ID()==null)?"null": "'" + getTAG_ID()+"'" ) + "," +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       ((getLANE()==null)?"null": "'" + getLANE()+"'" ) + "," +
       ((getDIRECTION()==null)?"null": "'" + getDIRECTION()+"'" ) + "," +
       ((getLOCATION()==null)?"null": "'" + getLOCATION()+"'" ) + "," +
       ((getTRANS_TYPE_DESCR()==null)?"null": "'" + getTRANS_TYPE_DESCR()+"'" ) + "," +
       getAMOUNT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
