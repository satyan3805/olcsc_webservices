package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLCSC_UTIL_ACCOUNT_INFO_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLCSC_UTIL_ACCOUNT_INFO_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[3];
  protected static final OLCSC_UTIL_ACCOUNT_INFO_REC _OLCSC_UTIL_ACCOUNT_INFO_RECFactory = new OLCSC_UTIL_ACCOUNT_INFO_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_UTIL_ACCOUNT_INFO_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[3], _sqlType, _factory); }
  public OLCSC_UTIL_ACCOUNT_INFO_REC()
  { _init_struct(true); }
  public OLCSC_UTIL_ACCOUNT_INFO_REC(String COLUMN_NAME, String COLUMN_VALUE, String COLUMN_FLAG) throws SQLException
  { _init_struct(true);
    setCOLUMN_NAME(COLUMN_NAME);
    setCOLUMN_VALUE(COLUMN_VALUE);
    setCOLUMN_FLAG(COLUMN_FLAG);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLCSC_UTIL_ACCOUNT_INFO_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLCSC_UTIL_ACCOUNT_INFO_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getCOLUMN_NAME() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setCOLUMN_NAME(String COLUMN_NAME) throws SQLException
  { _struct.setAttribute(0, COLUMN_NAME); }


  public String getCOLUMN_VALUE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setCOLUMN_VALUE(String COLUMN_VALUE) throws SQLException
  { _struct.setAttribute(1, COLUMN_VALUE); }


  public String getCOLUMN_FLAG() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setCOLUMN_FLAG(String COLUMN_FLAG) throws SQLException
  { _struct.setAttribute(2, COLUMN_FLAG); }

  public String toString()
  { try {
     return "OL_OWNER.OLCSC_UTIL_ACCOUNT_INFO_REC" + "(" +
       ((getCOLUMN_NAME()==null)?"null": "'" + getCOLUMN_NAME()+"'" ) + "," +
       ((getCOLUMN_VALUE()==null)?"null": "'" + getCOLUMN_VALUE()+"'" ) + "," +
       ((getCOLUMN_FLAG()==null)?"null": "'" + getCOLUMN_FLAG()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
