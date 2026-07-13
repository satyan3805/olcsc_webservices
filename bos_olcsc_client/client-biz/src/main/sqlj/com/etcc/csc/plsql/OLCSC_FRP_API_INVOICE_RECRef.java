package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class OLCSC_FRP_API_INVOICE_RECRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.OLCSC_FRP_API_INVOICE_REC";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final OLCSC_FRP_API_INVOICE_RECRef _OLCSC_FRP_API_INVOICE_RECRefFactory = new OLCSC_FRP_API_INVOICE_RECRef();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_FRP_API_INVOICE_RECRefFactory; }
  /* constructor */
  public OLCSC_FRP_API_INVOICE_RECRef()
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
    OLCSC_FRP_API_INVOICE_RECRef r = new OLCSC_FRP_API_INVOICE_RECRef();
    r._ref = (REF) d;
    return r;
  }

  public static OLCSC_FRP_API_INVOICE_RECRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (OLCSC_FRP_API_INVOICE_RECRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to OLCSC_FRP_API_INVOICE_RECRef: "+exn.toString()); }
  }

  public OLCSC_FRP_API_INVOICE_REC getValue() throws SQLException
  {
     return (OLCSC_FRP_API_INVOICE_REC) OLCSC_FRP_API_INVOICE_REC.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(OLCSC_FRP_API_INVOICE_REC c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
