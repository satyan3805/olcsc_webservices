package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_HAS_PARK_RECEIPT_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_HAS_PARK_RECEIPT_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 91,12,91,12,12,12,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[8];
  protected static final OLC_HAS_PARK_RECEIPT_REC _OLC_HAS_PARK_RECEIPT_RECFactory = new OLC_HAS_PARK_RECEIPT_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_HAS_PARK_RECEIPT_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[8], _sqlType, _factory); }
  public OLC_HAS_PARK_RECEIPT_REC()
  { _init_struct(true); }
  public OLC_HAS_PARK_RECEIPT_REC(java.sql.Timestamp ENTRY_DATE, String ENTRY_LANE, java.sql.Timestamp EXIT_DATE, String EXIT_LANE, String EZ_TAG_NUMBER, String LIC_PLATE, String LIC_STATE, java.math.BigDecimal PARKING_FEE) throws SQLException
  { _init_struct(true);
    setENTRY_DATE(ENTRY_DATE);
    setENTRY_LANE(ENTRY_LANE);
    setEXIT_DATE(EXIT_DATE);
    setEXIT_LANE(EXIT_LANE);
    setEZ_TAG_NUMBER(EZ_TAG_NUMBER);
    setLIC_PLATE(LIC_PLATE);
    setLIC_STATE(LIC_STATE);
    setPARKING_FEE(PARKING_FEE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_HAS_PARK_RECEIPT_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_HAS_PARK_RECEIPT_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.sql.Timestamp getENTRY_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(0); }

  public void setENTRY_DATE(java.sql.Timestamp ENTRY_DATE) throws SQLException
  { _struct.setAttribute(0, ENTRY_DATE); }


  public String getENTRY_LANE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setENTRY_LANE(String ENTRY_LANE) throws SQLException
  { _struct.setAttribute(1, ENTRY_LANE); }


  public java.sql.Timestamp getEXIT_DATE() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(2); }

  public void setEXIT_DATE(java.sql.Timestamp EXIT_DATE) throws SQLException
  { _struct.setAttribute(2, EXIT_DATE); }


  public String getEXIT_LANE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setEXIT_LANE(String EXIT_LANE) throws SQLException
  { _struct.setAttribute(3, EXIT_LANE); }


  public String getEZ_TAG_NUMBER() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setEZ_TAG_NUMBER(String EZ_TAG_NUMBER) throws SQLException
  { _struct.setAttribute(4, EZ_TAG_NUMBER); }


  public String getLIC_PLATE() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setLIC_PLATE(String LIC_PLATE) throws SQLException
  { _struct.setAttribute(5, LIC_PLATE); }


  public String getLIC_STATE() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setLIC_STATE(String LIC_STATE) throws SQLException
  { _struct.setAttribute(6, LIC_STATE); }


  public java.math.BigDecimal getPARKING_FEE() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setPARKING_FEE(java.math.BigDecimal PARKING_FEE) throws SQLException
  { _struct.setAttribute(7, PARKING_FEE); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_HAS_PARK_RECEIPT_REC" + "(" +
       getENTRY_DATE() + "," +
       ((getENTRY_LANE()==null)?"null": "'" + getENTRY_LANE()+"'" ) + "," +
       getEXIT_DATE() + "," +
       ((getEXIT_LANE()==null)?"null": "'" + getEXIT_LANE()+"'" ) + "," +
       ((getEZ_TAG_NUMBER()==null)?"null": "'" + getEZ_TAG_NUMBER()+"'" ) + "," +
       ((getLIC_PLATE()==null)?"null": "'" + getLIC_PLATE()+"'" ) + "," +
       ((getLIC_STATE()==null)?"null": "'" + getLIC_STATE()+"'" ) + "," +
       getPARKING_FEE() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
