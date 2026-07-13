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

public class OLC_UNINVOICED_VIOLS_ARR_N implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OLC_UNINVOICED_VIOLS_ARR_N _OLC_UNINVOICED_VIOLS_ARR_NFactory = new OLC_UNINVOICED_VIOLS_ARR_N();

  public static ORADataFactory getORADataFactory()
  { return _OLC_UNINVOICED_VIOLS_ARR_NFactory; }
  /* constructors */
  public OLC_UNINVOICED_VIOLS_ARR_N()
  {
    this((OLC_UNINVOICED_VIOLS_REC_N[])null);
  }

  public OLC_UNINVOICED_VIOLS_ARR_N(OLC_UNINVOICED_VIOLS_REC_N[] a)
  {
    _array = new MutableArray(2002, a, OLC_UNINVOICED_VIOLS_REC_N.getORADataFactory());
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
    OLC_UNINVOICED_VIOLS_ARR_N a = new OLC_UNINVOICED_VIOLS_ARR_N();
    a._array = new MutableArray(2002, (ARRAY) d, OLC_UNINVOICED_VIOLS_REC_N.getORADataFactory());
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
  public OLC_UNINVOICED_VIOLS_REC_N[] getArray() throws SQLException
  {
    return (OLC_UNINVOICED_VIOLS_REC_N[]) _array.getObjectArray(
      new OLC_UNINVOICED_VIOLS_REC_N[_array.length()]);
  }

  public OLC_UNINVOICED_VIOLS_REC_N[] getArray(long index, int count) throws SQLException
  {
    return (OLC_UNINVOICED_VIOLS_REC_N[]) _array.getObjectArray(index,
      new OLC_UNINVOICED_VIOLS_REC_N[_array.sliceLength(index, count)]);
  }

  public void setArray(OLC_UNINVOICED_VIOLS_REC_N[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(OLC_UNINVOICED_VIOLS_REC_N[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public OLC_UNINVOICED_VIOLS_REC_N getElement(long index) throws SQLException
  {
    return (OLC_UNINVOICED_VIOLS_REC_N) _array.getObjectElement(index);
  }

  public void setElement(OLC_UNINVOICED_VIOLS_REC_N a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.OLC_UNINVOICED_VIOLS_ARR_N" + "(";
     OLC_UNINVOICED_VIOLS_REC_N[] a = (OLC_UNINVOICED_VIOLS_REC_N[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
