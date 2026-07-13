package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class OLC_VPS_INV_REC_NRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.OLC_VPS_INV_REC_N";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final OLC_VPS_INV_REC_NRef _OLC_VPS_INV_REC_NRefFactory = new OLC_VPS_INV_REC_NRef();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_INV_REC_NRefFactory; }
  /* constructor */
  public OLC_VPS_INV_REC_NRef()
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
    OLC_VPS_INV_REC_NRef r = new OLC_VPS_INV_REC_NRef();
    r._ref = (REF) d;
    return r;
  }

  public static OLC_VPS_INV_REC_NRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (OLC_VPS_INV_REC_NRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to OLC_VPS_INV_REC_NRef: "+exn.toString()); }
  }

  public OLC_VPS_INV_REC_N getValue() throws SQLException
  {
     return (OLC_VPS_INV_REC_N) OLC_VPS_INV_REC_N.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(OLC_VPS_INV_REC_N c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
