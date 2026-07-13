package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class MULTIPLE_VEHICLE_UPLOAD_RECRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_REC";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final MULTIPLE_VEHICLE_UPLOAD_RECRef _MULTIPLE_VEHICLE_UPLOAD_RECRefFactory = new MULTIPLE_VEHICLE_UPLOAD_RECRef();

  public static ORADataFactory getORADataFactory()
  { return _MULTIPLE_VEHICLE_UPLOAD_RECRefFactory; }
  /* constructor */
  public MULTIPLE_VEHICLE_UPLOAD_RECRef()
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
    MULTIPLE_VEHICLE_UPLOAD_RECRef r = new MULTIPLE_VEHICLE_UPLOAD_RECRef();
    r._ref = (REF) d;
    return r;
  }

  public static MULTIPLE_VEHICLE_UPLOAD_RECRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (MULTIPLE_VEHICLE_UPLOAD_RECRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to MULTIPLE_VEHICLE_UPLOAD_RECRef: "+exn.toString()); }
  }

  public MULTIPLE_VEHICLE_UPLOAD_REC getValue() throws SQLException
  {
     return (MULTIPLE_VEHICLE_UPLOAD_REC) MULTIPLE_VEHICLE_UPLOAD_REC.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(MULTIPLE_VEHICLE_UPLOAD_REC c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
