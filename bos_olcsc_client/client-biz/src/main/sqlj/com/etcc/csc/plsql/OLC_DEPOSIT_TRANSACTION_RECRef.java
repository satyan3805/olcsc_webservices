package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class OLC_DEPOSIT_TRANSACTION_RECRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.OLC_DEPOSIT_TRANSACTION_REC";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final OLC_DEPOSIT_TRANSACTION_RECRef _OLC_DEPOSIT_TRANSACTION_RECRefFactory = new OLC_DEPOSIT_TRANSACTION_RECRef();

  public static ORADataFactory getORADataFactory()
  { return _OLC_DEPOSIT_TRANSACTION_RECRefFactory; }
  /* constructor */
  public OLC_DEPOSIT_TRANSACTION_RECRef()
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
    OLC_DEPOSIT_TRANSACTION_RECRef r = new OLC_DEPOSIT_TRANSACTION_RECRef();
    r._ref = (REF) d;
    return r;
  }

  public static OLC_DEPOSIT_TRANSACTION_RECRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (OLC_DEPOSIT_TRANSACTION_RECRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to OLC_DEPOSIT_TRANSACTION_RECRef: "+exn.toString()); }
  }

  public OLC_DEPOSIT_TRANSACTION_REC getValue() throws SQLException
  {
     return (OLC_DEPOSIT_TRANSACTION_REC) OLC_DEPOSIT_TRANSACTION_REC.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(OLC_DEPOSIT_TRANSACTION_REC c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
