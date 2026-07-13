package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_VPS_INV_REC_D implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_INV_REC_D";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,2,91,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[7];
  protected static final OLC_VPS_INV_REC_D _OLC_VPS_INV_REC_DFactory = new OLC_VPS_INV_REC_D();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_INV_REC_DFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[7], _sqlType, _factory); }
  public OLC_VPS_INV_REC_D()
  { _init_struct(true); }
  public OLC_VPS_INV_REC_D(java.math.BigDecimal TRANSACTION_ID, String INVOICE_NUMBER, java.math.BigDecimal AMOUNT, java.sql.Timestamp TRANSACTION_DATE, String STATUS, String V_LOCATION, java.math.BigDecimal ORIG_TOLL_AMOUNT) throws SQLException
  { _init_struct(true);
    setTRANSACTION_ID(TRANSACTION_ID);
    setINVOICE_NUMBER(INVOICE_NUMBER);
    setAMOUNT(AMOUNT);
    setTRANSACTION_DATE(TRANSACTION_DATE);
    setSTATUS(STATUS);
    setV_LOCATION(V_LOCATION);
    setORIG_TOLL_AMOUNT(ORIG_TOLL_AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_VPS_INV_REC_D o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_VPS_INV_REC_D();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getTRANSACTION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setTRANSACTION_ID(java.math.BigDecimal TRANSACTION_ID) throws SQLException
  { _struct.setAttribute(0, TRANSACTION_ID); }


  public String getINVOICE_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setINVOICE_NUMBER(String INVOICE_NUMBER) throws SQLException
  { _struct.setAttribute(1, INVOICE_NUMBER); }


  public java.math.BigDecimal getAMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setAMOUNT(java.math.BigDecimal AMOUNT) throws SQLException
  { _struct.setAttribute(2, AMOUNT); }


  public java.sql.Timestamp getTRANSACTION_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(3); }

  public void setTRANSACTION_DATE(java.sql.Timestamp TRANSACTION_DATE) throws SQLException
  { _struct.setAttribute(3, TRANSACTION_DATE); }


  public String getSTATUS() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setSTATUS(String STATUS) throws SQLException
  { _struct.setAttribute(4, STATUS); }


  public String getV_LOCATION() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setV_LOCATION(String V_LOCATION) throws SQLException
  { _struct.setAttribute(5, V_LOCATION); }


  public java.math.BigDecimal getORIG_TOLL_AMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setORIG_TOLL_AMOUNT(java.math.BigDecimal ORIG_TOLL_AMOUNT) throws SQLException
  { _struct.setAttribute(6, ORIG_TOLL_AMOUNT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_VPS_INV_REC_D" + "(" +
       getTRANSACTION_ID() + "," +
       ((getINVOICE_NUMBER()==null)?"null": "'" + getINVOICE_NUMBER()+"'" ) + "," +
       getAMOUNT() + "," +
       getTRANSACTION_DATE() + "," +
       ((getSTATUS()==null)?"null": "'" + getSTATUS()+"'" ) + "," +
       ((getV_LOCATION()==null)?"null": "'" + getV_LOCATION()+"'" ) + "," +
       getORIG_TOLL_AMOUNT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
