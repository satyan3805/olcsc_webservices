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

public class OLC_UNINVOICED_VIOLS_ARR implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_UNINVOICED_VIOLS_ARR";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OLC_UNINVOICED_VIOLS_ARR _OLC_UNINVOICED_VIOLS_ARRFactory = new OLC_UNINVOICED_VIOLS_ARR();

  public static ORADataFactory getORADataFactory()
  { return _OLC_UNINVOICED_VIOLS_ARRFactory; }
  /* constructors */
  public OLC_UNINVOICED_VIOLS_ARR()
  {
    this((OLC_UNINVOICED_VIOLS_REC[])null);
  }

  public OLC_UNINVOICED_VIOLS_ARR(OLC_UNINVOICED_VIOLS_REC[] a)
  {
    _array = new MutableArray(2002, a, OLC_UNINVOICED_VIOLS_REC.getORADataFactory());
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
    OLC_UNINVOICED_VIOLS_ARR a = new OLC_UNINVOICED_VIOLS_ARR();
    a._array = new MutableArray(2002, (ARRAY) d, OLC_UNINVOICED_VIOLS_REC.getORADataFactory());
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
  public OLC_UNINVOICED_VIOLS_REC[] getArray() throws SQLException
  {
    return (OLC_UNINVOICED_VIOLS_REC[]) _array.getObjectArray(
      new OLC_UNINVOICED_VIOLS_REC[_array.length()]);
  }

  public OLC_UNINVOICED_VIOLS_REC[] getArray(long index, int count) throws SQLException
  {
    return (OLC_UNINVOICED_VIOLS_REC[]) _array.getObjectArray(index,
      new OLC_UNINVOICED_VIOLS_REC[_array.sliceLength(index, count)]);
  }

  public void setArray(OLC_UNINVOICED_VIOLS_REC[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(OLC_UNINVOICED_VIOLS_REC[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public OLC_UNINVOICED_VIOLS_REC getElement(long index) throws SQLException
  {
    return (OLC_UNINVOICED_VIOLS_REC) _array.getObjectElement(index);
  }

  public void setElement(OLC_UNINVOICED_VIOLS_REC a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.OLC_UNINVOICED_VIOLS_ARR" + "(";
     OLC_UNINVOICED_VIOLS_REC[] a = (OLC_UNINVOICED_VIOLS_REC[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
