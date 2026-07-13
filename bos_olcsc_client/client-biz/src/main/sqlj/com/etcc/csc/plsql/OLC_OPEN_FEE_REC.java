package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_OPEN_FEE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_OPEN_FEE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2,2,2,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final OLC_OPEN_FEE_REC _OLC_OPEN_FEE_RECFactory = new OLC_OPEN_FEE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_OPEN_FEE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public OLC_OPEN_FEE_REC()
  { _init_struct(true); }
  public OLC_OPEN_FEE_REC(String FEE_CODE, java.math.BigDecimal FEE_DUE_OPEN_AMT, java.math.BigDecimal FEE_DISC_AMT, java.math.BigDecimal FEE_DUE_AFTER_DISC_AMT, java.math.BigDecimal RETAIL_DETAIL_ID, java.math.BigDecimal FEE_GRP_ID) throws SQLException
  { _init_struct(true);
    setFEE_CODE(FEE_CODE);
    setFEE_DUE_OPEN_AMT(FEE_DUE_OPEN_AMT);
    setFEE_DISC_AMT(FEE_DISC_AMT);
    setFEE_DUE_AFTER_DISC_AMT(FEE_DUE_AFTER_DISC_AMT);
    setRETAIL_DETAIL_ID(RETAIL_DETAIL_ID);
    setFEE_GRP_ID(FEE_GRP_ID);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_OPEN_FEE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_OPEN_FEE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getFEE_CODE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setFEE_CODE(String FEE_CODE) throws SQLException
  { _struct.setAttribute(0, FEE_CODE); }


  public java.math.BigDecimal getFEE_DUE_OPEN_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setFEE_DUE_OPEN_AMT(java.math.BigDecimal FEE_DUE_OPEN_AMT) throws SQLException
  { _struct.setAttribute(1, FEE_DUE_OPEN_AMT); }


  public java.math.BigDecimal getFEE_DISC_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setFEE_DISC_AMT(java.math.BigDecimal FEE_DISC_AMT) throws SQLException
  { _struct.setAttribute(2, FEE_DISC_AMT); }


  public java.math.BigDecimal getFEE_DUE_AFTER_DISC_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setFEE_DUE_AFTER_DISC_AMT(java.math.BigDecimal FEE_DUE_AFTER_DISC_AMT) throws SQLException
  { _struct.setAttribute(3, FEE_DUE_AFTER_DISC_AMT); }


  public java.math.BigDecimal getRETAIL_DETAIL_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setRETAIL_DETAIL_ID(java.math.BigDecimal RETAIL_DETAIL_ID) throws SQLException
  { _struct.setAttribute(4, RETAIL_DETAIL_ID); }


  public java.math.BigDecimal getFEE_GRP_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setFEE_GRP_ID(java.math.BigDecimal FEE_GRP_ID) throws SQLException
  { _struct.setAttribute(5, FEE_GRP_ID); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_OPEN_FEE_REC" + "(" +
       ((getFEE_CODE()==null)?"null": "'" + getFEE_CODE()+"'" ) + "," +
       getFEE_DUE_OPEN_AMT() + "," +
       getFEE_DISC_AMT() + "," +
       getFEE_DUE_AFTER_DISC_AMT() + "," +
       getRETAIL_DETAIL_ID() + "," +
       getFEE_GRP_ID() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
