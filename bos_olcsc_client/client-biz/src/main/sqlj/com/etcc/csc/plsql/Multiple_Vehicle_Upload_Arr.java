package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.jpub.runtime.MutableArray;

public class Multiple_Vehicle_Upload_Arr implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_ARR";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Multiple_Vehicle_Upload_Arr _Multiple_Vehicle_Upload_ArrFactory = new Multiple_Vehicle_Upload_Arr();

  public static ORADataFactory getORADataFactory()
  { return _Multiple_Vehicle_Upload_ArrFactory; }
  /* constructors */
  public Multiple_Vehicle_Upload_Arr()
  {
    this((MULTIPLE_VEHICLE_UPLOAD_REC[])null);
  }

  public Multiple_Vehicle_Upload_Arr(MULTIPLE_VEHICLE_UPLOAD_REC[] a)
  {
    _array = new MutableArray(2002, a, MULTIPLE_VEHICLE_UPLOAD_REC.getORADataFactory());
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _array.toDatum(c, _SQL_NAME);
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    Multiple_Vehicle_Upload_Arr a = new Multiple_Vehicle_Upload_Arr();
    a._array = new MutableArray(2002, (ARRAY) d, MULTIPLE_VEHICLE_UPLOAD_REC.getORADataFactory());
    return a;
  }

  public int length() throws SQLException
  {
    return _array.length();
  }

  public int getBaseType() throws SQLException
  {
    return _array.getBaseType();
  }

  public String getBaseTypeName() throws SQLException
  {
    return _array.getBaseTypeName();
  }

  public ArrayDescriptor getDescriptor() throws SQLException
  {
    return _array.getDescriptor();
  }

  /* array accessor methods */
  public MULTIPLE_VEHICLE_UPLOAD_REC[] getArray() throws SQLException
  {
    return (MULTIPLE_VEHICLE_UPLOAD_REC[]) _array.getObjectArray(
      new MULTIPLE_VEHICLE_UPLOAD_REC[_array.length()]);
  }

  public MULTIPLE_VEHICLE_UPLOAD_REC[] getArray(long index, int count) throws SQLException
  {
    return (MULTIPLE_VEHICLE_UPLOAD_REC[]) _array.getObjectArray(index,
      new MULTIPLE_VEHICLE_UPLOAD_REC[_array.sliceLength(index, count)]);
  }

  public void setArray(MULTIPLE_VEHICLE_UPLOAD_REC[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(MULTIPLE_VEHICLE_UPLOAD_REC[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public MULTIPLE_VEHICLE_UPLOAD_REC getElement(long index) throws SQLException
  {
    return (MULTIPLE_VEHICLE_UPLOAD_REC) _array.getObjectElement(index);
  }

  public void setElement(MULTIPLE_VEHICLE_UPLOAD_REC a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_ARR" + "(";
     MULTIPLE_VEHICLE_UPLOAD_REC[] a = (MULTIPLE_VEHICLE_UPLOAD_REC[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
