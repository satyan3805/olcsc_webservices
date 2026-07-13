package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_CR_CARD_TYPE_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_CR_CARD_TYPE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 1,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[2];
  protected static final OLC_CR_CARD_TYPE_REC _OLC_CR_CARD_TYPE_RECFactory = new OLC_CR_CARD_TYPE_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_CR_CARD_TYPE_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[2], _sqlType, _factory); }
  public OLC_CR_CARD_TYPE_REC()
  { _init_struct(true); }
  public OLC_CR_CARD_TYPE_REC(String CARD_CODE, String CARD_NAME) throws SQLException
  { _init_struct(true);
    setCARD_CODE(CARD_CODE);
    setCARD_NAME(CARD_NAME);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_CR_CARD_TYPE_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_CR_CARD_TYPE_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getCARD_CODE() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setCARD_CODE(String CARD_CODE) throws SQLException
  { _struct.setAttribute(0, CARD_CODE); }


  public String getCARD_NAME() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setCARD_NAME(String CARD_NAME) throws SQLException
  { _struct.setAttribute(1, CARD_NAME); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_CR_CARD_TYPE_REC" + "(" +
       ((getCARD_CODE()==null)?"null": "'" + getCARD_CODE()+"'" ) + "," +
       ((getCARD_NAME()==null)?"null": "'" + getCARD_NAME()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
