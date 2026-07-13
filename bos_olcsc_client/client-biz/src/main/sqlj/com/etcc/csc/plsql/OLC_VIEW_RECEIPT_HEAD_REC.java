package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VIEW_RECEIPT_HEAD_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VIEW_RECEIPT_HEAD_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,91,12,2,1 };
  protected static ORADataFactory[] _factory = new ORADataFactory[5];
  protected static final OLC_VIEW_RECEIPT_HEAD_REC _OLC_VIEW_RECEIPT_HEAD_RECFactory = new OLC_VIEW_RECEIPT_HEAD_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VIEW_RECEIPT_HEAD_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[5], _sqlType, _factory); }
  public OLC_VIEW_RECEIPT_HEAD_REC()
  { _init_struct(true); }
  public OLC_VIEW_RECEIPT_HEAD_REC(java.math.BigDecimal TRANS_AMT, java.sql.Timestamp TRANS_DATE, String TRANS_TYPE_DESCR, java.math.BigDecimal RETAIL_TRANS_ID, String HAS_FLAG) throws SQLException
  { _init_struct(true);
    setTRANS_AMT(TRANS_AMT);
    setTRANS_DATE(TRANS_DATE);
    setTRANS_TYPE_DESCR(TRANS_TYPE_DESCR);
    setRETAIL_TRANS_ID(RETAIL_TRANS_ID);
    setHAS_FLAG(HAS_FLAG);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VIEW_RECEIPT_HEAD_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VIEW_RECEIPT_HEAD_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getTRANS_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setTRANS_AMT(java.math.BigDecimal TRANS_AMT) throws SQLException
  { _struct.setAttribute(0, TRANS_AMT); }


  public java.sql.Timestamp getTRANS_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(1); }

  public void setTRANS_DATE(java.sql.Timestamp TRANS_DATE) throws SQLException
  { _struct.setAttribute(1, TRANS_DATE); }


  public String getTRANS_TYPE_DESCR() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setTRANS_TYPE_DESCR(String TRANS_TYPE_DESCR) throws SQLException
  { _struct.setAttribute(2, TRANS_TYPE_DESCR); }


  public java.math.BigDecimal getRETAIL_TRANS_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setRETAIL_TRANS_ID(java.math.BigDecimal RETAIL_TRANS_ID) throws SQLException
  { _struct.setAttribute(3, RETAIL_TRANS_ID); }


  public String getHAS_FLAG() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setHAS_FLAG(String HAS_FLAG) throws SQLException
  { _struct.setAttribute(4, HAS_FLAG); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VIEW_RECEIPT_HEAD_REC" + "(" +
       getTRANS_AMT() + "," +
       getTRANS_DATE() + "," +
       ((getTRANS_TYPE_DESCR()==null)?"null": "'" + getTRANS_TYPE_DESCR()+"'" ) + "," +
       getRETAIL_TRANS_ID() + "," +
       ((getHAS_FLAG()==null)?"null": "'" + getHAS_FLAG()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
