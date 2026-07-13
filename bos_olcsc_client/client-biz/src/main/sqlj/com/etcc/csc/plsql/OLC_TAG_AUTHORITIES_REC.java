package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_TAG_AUTHORITIES_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_TAG_AUTHORITIES_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  protected static final OLC_TAG_AUTHORITIES_REC _OLC_TAG_AUTHORITIES_RECFactory = new OLC_TAG_AUTHORITIES_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_TAG_AUTHORITIES_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public OLC_TAG_AUTHORITIES_REC()
  { _init_struct(true); }
  public OLC_TAG_AUTHORITIES_REC(java.math.BigDecimal TA_ID, String TAG_IDENTIFIER, String NAME, String BARCODE_PREFIX) throws SQLException
  { _init_struct(true);
    setTA_ID(TA_ID);
    setTAG_IDENTIFIER(TAG_IDENTIFIER);
    setNAME(NAME);
    setBARCODE_PREFIX(BARCODE_PREFIX);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_TAG_AUTHORITIES_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_TAG_AUTHORITIES_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getTA_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setTA_ID(java.math.BigDecimal TA_ID) throws SQLException
  { _struct.setAttribute(0, TA_ID); }


  public String getTAG_IDENTIFIER() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setTAG_IDENTIFIER(String TAG_IDENTIFIER) throws SQLException
  { _struct.setAttribute(1, TAG_IDENTIFIER); }


  public String getNAME() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setNAME(String NAME) throws SQLException
  { _struct.setAttribute(2, NAME); }


  public String getBARCODE_PREFIX() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setBARCODE_PREFIX(String BARCODE_PREFIX) throws SQLException
  { _struct.setAttribute(3, BARCODE_PREFIX); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_TAG_AUTHORITIES_REC" + "(" +
       getTA_ID() + "," +
       ((getTAG_IDENTIFIER()==null)?"null": "'" + getTAG_IDENTIFIER()+"'" ) + "," +
       ((getNAME()==null)?"null": "'" + getNAME()+"'" ) + "," +
       ((getBARCODE_PREFIX()==null)?"null": "'" + getBARCODE_PREFIX()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
