package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_IOP_ACCT_INFO implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_IOP_ACCT_INFO";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final OLC_IOP_ACCT_INFO _OLC_IOP_ACCT_INFOFactory = new OLC_IOP_ACCT_INFO();

  public static ORADataFactory getORADataFactory()
  { return _OLC_IOP_ACCT_INFOFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public OLC_IOP_ACCT_INFO()
  { _init_struct(true); }
  public OLC_IOP_ACCT_INFO(java.math.BigDecimal ACCT_ID, java.math.BigDecimal AGCY_ID, String AGCY_ABBREV, String AGCY_NAME, String REASON_CODE, String IS_ACTIVE) throws SQLException
  { _init_struct(true);
    setACCT_ID(ACCT_ID);
    setAGCY_ID(AGCY_ID);
    setAGCY_ABBREV(AGCY_ABBREV);
    setAGCY_NAME(AGCY_NAME);
    setREASON_CODE(REASON_CODE);
    setIS_ACTIVE(IS_ACTIVE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_IOP_ACCT_INFO o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_IOP_ACCT_INFO();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCT_ID(java.math.BigDecimal ACCT_ID) throws SQLException
  { _struct.setAttribute(0, ACCT_ID); }


  public java.math.BigDecimal getAGCY_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setAGCY_ID(java.math.BigDecimal AGCY_ID) throws SQLException
  { _struct.setAttribute(1, AGCY_ID); }


  public String getAGCY_ABBREV() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setAGCY_ABBREV(String AGCY_ABBREV) throws SQLException
  { _struct.setAttribute(2, AGCY_ABBREV); }


  public String getAGCY_NAME() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setAGCY_NAME(String AGCY_NAME) throws SQLException
  { _struct.setAttribute(3, AGCY_NAME); }


  public String getREASON_CODE() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setREASON_CODE(String REASON_CODE) throws SQLException
  { _struct.setAttribute(4, REASON_CODE); }


  public String getIS_ACTIVE() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setIS_ACTIVE(String IS_ACTIVE) throws SQLException
  { _struct.setAttribute(5, IS_ACTIVE); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_IOP_ACCT_INFO" + "(" +
       getACCT_ID() + "," +
       getAGCY_ID() + "," +
       ((getAGCY_ABBREV()==null)?"null": "'" + getAGCY_ABBREV()+"'" ) + "," +
       ((getAGCY_NAME()==null)?"null": "'" + getAGCY_NAME()+"'" ) + "," +
       ((getREASON_CODE()==null)?"null": "'" + getREASON_CODE()+"'" ) + "," +
       ((getIS_ACTIVE()==null)?"null": "'" + getIS_ACTIVE()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
