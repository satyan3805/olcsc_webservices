package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class OLC_VPS_INV_REC_DRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.OLC_VPS_INV_REC_D";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final OLC_VPS_INV_REC_DRef _OLC_VPS_INV_REC_DRefFactory = new OLC_VPS_INV_REC_DRef();

  public static ORADataFactory getORADataFactory()
  { return _OLC_VPS_INV_REC_DRefFactory; }
  /* constructor */
  public OLC_VPS_INV_REC_DRef()
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
    OLC_VPS_INV_REC_DRef r = new OLC_VPS_INV_REC_DRef();
    r._ref = (REF) d;
    return r;
  }

  public static OLC_VPS_INV_REC_DRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (OLC_VPS_INV_REC_DRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to OLC_VPS_INV_REC_DRef: "+exn.toString()); }
  }

  public OLC_VPS_INV_REC_D getValue() throws SQLException
  {
     return (OLC_VPS_INV_REC_D) OLC_VPS_INV_REC_D.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(OLC_VPS_INV_REC_D c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
