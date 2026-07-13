package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class OLC_IOP_ACCT_INFORef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.OLC_IOP_ACCT_INFO";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final OLC_IOP_ACCT_INFORef _OLC_IOP_ACCT_INFORefFactory = new OLC_IOP_ACCT_INFORef();

  public static ORADataFactory getORADataFactory()
  { return _OLC_IOP_ACCT_INFORefFactory; }
  /* constructor */
  public OLC_IOP_ACCT_INFORef()
  {
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _ref;
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    OLC_IOP_ACCT_INFORef r = new OLC_IOP_ACCT_INFORef();
    r._ref = (REF) d;
    return r;
  }

  public static OLC_IOP_ACCT_INFORef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (OLC_IOP_ACCT_INFORef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to OLC_IOP_ACCT_INFORef: "+exn.toString()); }
  }

  public OLC_IOP_ACCT_INFO getValue() throws SQLException
  {
     return (OLC_IOP_ACCT_INFO) OLC_IOP_ACCT_INFO.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(OLC_IOP_ACCT_INFO c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
