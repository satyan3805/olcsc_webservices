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

public class OLCSC_VEHICLE_NICKNAME_ARR implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLCSC_VEHICLE_NICKNAME_ARR";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OLCSC_VEHICLE_NICKNAME_ARR _OLCSC_VEHICLE_NICKNAME_ARRFactory = new OLCSC_VEHICLE_NICKNAME_ARR();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_VEHICLE_NICKNAME_ARRFactory; }
  /* constructors */
  public OLCSC_VEHICLE_NICKNAME_ARR()
  {
    this((OLCSC_VEHICLE_NICKNAME_REC[])null);
  }

  public OLCSC_VEHICLE_NICKNAME_ARR(OLCSC_VEHICLE_NICKNAME_REC[] a)
  {
    _array = new MutableArray(2002, a, OLCSC_VEHICLE_NICKNAME_REC.getORADataFactory());
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
    OLCSC_VEHICLE_NICKNAME_ARR a = new OLCSC_VEHICLE_NICKNAME_ARR();
    a._array = new MutableArray(2002, (ARRAY) d, OLCSC_VEHICLE_NICKNAME_REC.getORADataFactory());
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
  public OLCSC_VEHICLE_NICKNAME_REC[] getArray() throws SQLException
  {
    return (OLCSC_VEHICLE_NICKNAME_REC[]) _array.getObjectArray(
      new OLCSC_VEHICLE_NICKNAME_REC[_array.length()]);
  }

  public OLCSC_VEHICLE_NICKNAME_REC[] getArray(long index, int count) throws SQLException
  {
    return (OLCSC_VEHICLE_NICKNAME_REC[]) _array.getObjectArray(index,
      new OLCSC_VEHICLE_NICKNAME_REC[_array.sliceLength(index, count)]);
  }

  public void setArray(OLCSC_VEHICLE_NICKNAME_REC[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(OLCSC_VEHICLE_NICKNAME_REC[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public OLCSC_VEHICLE_NICKNAME_REC getElement(long index) throws SQLException
  {
    return (OLCSC_VEHICLE_NICKNAME_REC) _array.getObjectElement(index);
  }

  public void setElement(OLCSC_VEHICLE_NICKNAME_REC a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.OLCSC_VEHICLE_NICKNAME_ARR" + "(";
     OLCSC_VEHICLE_NICKNAME_REC[] a = (OLCSC_VEHICLE_NICKNAME_REC[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
