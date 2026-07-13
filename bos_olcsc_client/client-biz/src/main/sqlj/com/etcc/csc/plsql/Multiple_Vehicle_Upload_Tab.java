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

public class Multiple_Vehicle_Upload_Tab implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_TAB";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Multiple_Vehicle_Upload_Tab _Multiple_Vehicle_Upload_TabFactory = new Multiple_Vehicle_Upload_Tab();

  public static ORADataFactory getORADataFactory()
  { return _Multiple_Vehicle_Upload_TabFactory; }
  /* constructors */
  public Multiple_Vehicle_Upload_Tab()
  {
    this((Multiple_Vehicle_Upload_obj[])null);
  }

  public Multiple_Vehicle_Upload_Tab(Multiple_Vehicle_Upload_obj[] a)
  {
    _array = new MutableArray(2002, a, Multiple_Vehicle_Upload_obj.getORADataFactory());
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
    Multiple_Vehicle_Upload_Tab a = new Multiple_Vehicle_Upload_Tab();
    a._array = new MutableArray(2002, (ARRAY) d, Multiple_Vehicle_Upload_obj.getORADataFactory());
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
  public Multiple_Vehicle_Upload_obj[] getArray() throws SQLException
  {
    return (Multiple_Vehicle_Upload_obj[]) _array.getObjectArray(
      new Multiple_Vehicle_Upload_obj[_array.length()]);
  }

  public Multiple_Vehicle_Upload_obj[] getArray(long index, int count) throws SQLException
  {
    return (Multiple_Vehicle_Upload_obj[]) _array.getObjectArray(index,
      new Multiple_Vehicle_Upload_obj[_array.sliceLength(index, count)]);
  }

  public void setArray(Multiple_Vehicle_Upload_obj[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Multiple_Vehicle_Upload_obj[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Multiple_Vehicle_Upload_obj getElement(long index) throws SQLException
  {
    return (Multiple_Vehicle_Upload_obj) _array.getObjectElement(index);
  }

  public void setElement(Multiple_Vehicle_Upload_obj a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_TAB" + "(";
     Multiple_Vehicle_Upload_obj[] a = (Multiple_Vehicle_Upload_obj[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
