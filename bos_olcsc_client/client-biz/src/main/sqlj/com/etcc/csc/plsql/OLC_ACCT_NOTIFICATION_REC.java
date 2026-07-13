package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_ACCT_NOTIFICATION_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_ACCT_NOTIFICATION_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final OLC_ACCT_NOTIFICATION_REC _OLC_ACCT_NOTIFICATION_RECFactory = new OLC_ACCT_NOTIFICATION_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_ACCT_NOTIFICATION_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public OLC_ACCT_NOTIFICATION_REC()
  { _init_struct(true); }
  public OLC_ACCT_NOTIFICATION_REC(java.math.BigDecimal ACCOUNT_ID, String NOTIFICATION_TYPE, String NTFT_LONG_DESC, String NOTIFICATION_FORMAT, String YES_OR_NO, java.math.BigDecimal SECTION) throws SQLException
  { _init_struct(true);
    setACCOUNT_ID(ACCOUNT_ID);
    setNOTIFICATION_TYPE(NOTIFICATION_TYPE);
    setNTFT_LONG_DESC(NTFT_LONG_DESC);
    setNOTIFICATION_FORMAT(NOTIFICATION_FORMAT);
    setYES_OR_NO(YES_OR_NO);
    setSECTION(SECTION);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_ACCT_NOTIFICATION_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_ACCT_NOTIFICATION_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(0, ACCOUNT_ID); }


  public String getNOTIFICATION_TYPE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setNOTIFICATION_TYPE(String NOTIFICATION_TYPE) throws SQLException
  { _struct.setAttribute(1, NOTIFICATION_TYPE); }


  public String getNTFT_LONG_DESC() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setNTFT_LONG_DESC(String NTFT_LONG_DESC) throws SQLException
  { _struct.setAttribute(2, NTFT_LONG_DESC); }


  public String getNOTIFICATION_FORMAT() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setNOTIFICATION_FORMAT(String NOTIFICATION_FORMAT) throws SQLException
  { _struct.setAttribute(3, NOTIFICATION_FORMAT); }


  public String getYES_OR_NO() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setYES_OR_NO(String YES_OR_NO) throws SQLException
  { _struct.setAttribute(4, YES_OR_NO); }


  public java.math.BigDecimal getSECTION() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setSECTION(java.math.BigDecimal SECTION) throws SQLException
  { _struct.setAttribute(5, SECTION); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_ACCT_NOTIFICATION_REC" + "(" +
       getACCOUNT_ID() + "," +
       ((getNOTIFICATION_TYPE()==null)?"null": "'" + getNOTIFICATION_TYPE()+"'" ) + "," +
       ((getNTFT_LONG_DESC()==null)?"null": "'" + getNTFT_LONG_DESC()+"'" ) + "," +
       ((getNOTIFICATION_FORMAT()==null)?"null": "'" + getNOTIFICATION_FORMAT()+"'" ) + "," +
       ((getYES_OR_NO()==null)?"null": "'" + getYES_OR_NO()+"'" ) + "," +
       getSECTION() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
