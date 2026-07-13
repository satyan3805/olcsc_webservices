package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class Multiple_Vehicle_Upload_objRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_OBJ";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final Multiple_Vehicle_Upload_objRef _Multiple_Vehicle_Upload_objRefFactory = new Multiple_Vehicle_Upload_objRef();

  public static ORADataFactory getORADataFactory()
  { return _Multiple_Vehicle_Upload_objRefFactory; }
  /* constructor */
  public Multiple_Vehicle_Upload_objRef()
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
    Multiple_Vehicle_Upload_objRef r = new Multiple_Vehicle_Upload_objRef();
    r._ref = (REF) d;
    return r;
  }

  public static Multiple_Vehicle_Upload_objRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (Multiple_Vehicle_Upload_objRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to Multiple_Vehicle_Upload_objRef: "+exn.toString()); }
  }

  public Multiple_Vehicle_Upload_obj getValue() throws SQLException
  {
     return (Multiple_Vehicle_Upload_obj) Multiple_Vehicle_Upload_obj.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Multiple_Vehicle_Upload_obj c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
  public String toString()
  { try {
      return "REF " + _ref.getBaseTypeName() + "(" + _ref + ")";
    } catch (SQLException e) { return e.toString(); }
  }

}
