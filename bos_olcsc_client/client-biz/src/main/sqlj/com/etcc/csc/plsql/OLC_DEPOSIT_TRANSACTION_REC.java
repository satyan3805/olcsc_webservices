package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_DEPOSIT_TRANSACTION_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_DEPOSIT_TRANSACTION_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLC_DEPOSIT_TRANSACTION_REC _OLC_DEPOSIT_TRANSACTION_RECFactory = new OLC_DEPOSIT_TRANSACTION_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_DEPOSIT_TRANSACTION_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLC_DEPOSIT_TRANSACTION_REC()
  { _init_struct(true); }
  public OLC_DEPOSIT_TRANSACTION_REC(String TRANS_TYPE_DESCR, java.math.BigDecimal TRANS_AMT) throws SQLException
  { _init_struct(true);
    setTRANS_TYPE_DESCR(TRANS_TYPE_DESCR);
    setTRANS_AMT(TRANS_AMT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_DEPOSIT_TRANSACTION_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_DEPOSIT_TRANSACTION_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getTRANS_TYPE_DESCR() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setTRANS_TYPE_DESCR(String TRANS_TYPE_DESCR) throws SQLException
  { _struct.setAttribute(0, TRANS_TYPE_DESCR); }


  public java.math.BigDecimal getTRANS_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setTRANS_AMT(java.math.BigDecimal TRANS_AMT) throws SQLException
  { _struct.setAttribute(1, TRANS_AMT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_DEPOSIT_TRANSACTION_REC" + "(" +
       ((getTRANS_TYPE_DESCR()==null)?"null": "'" + getTRANS_TYPE_DESCR()+"'" ) + "," +
       getTRANS_AMT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
