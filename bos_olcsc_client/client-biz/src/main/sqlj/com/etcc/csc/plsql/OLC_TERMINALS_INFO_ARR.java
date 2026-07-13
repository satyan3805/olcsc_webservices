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

public class OLC_TERMINALS_INFO_ARR implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_TERMINALS_INFO_ARR";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OLC_TERMINALS_INFO_ARR _OLC_TERMINALS_INFO_ARRFactory = new OLC_TERMINALS_INFO_ARR();

  public static ORADataFactory getORADataFactory()
  { return _OLC_TERMINALS_INFO_ARRFactory; }
  /* constructors */
  public OLC_TERMINALS_INFO_ARR()
  {
    this((OLC_TERMINALS_INFO_REC[])null);
  }

  public OLC_TERMINALS_INFO_ARR(OLC_TERMINALS_INFO_REC[] a)
  {
    _array = new MutableArray(2002, a, OLC_TERMINALS_INFO_REC.getORADataFactory());
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
    OLC_TERMINALS_INFO_ARR a = new OLC_TERMINALS_INFO_ARR();
    a._array = new MutableArray(2002, (ARRAY) d, OLC_TERMINALS_INFO_REC.getORADataFactory());
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
  public OLC_TERMINALS_INFO_REC[] getArray() throws SQLException
  {
    return (OLC_TERMINALS_INFO_REC[]) _array.getObjectArray(
      new OLC_TERMINALS_INFO_REC[_array.length()]);
  }

  public OLC_TERMINALS_INFO_REC[] getArray(long index, int count) throws SQLException
  {
    return (OLC_TERMINALS_INFO_REC[]) _array.getObjectArray(index,
      new OLC_TERMINALS_INFO_REC[_array.sliceLength(index, count)]);
  }

  public void setArray(OLC_TERMINALS_INFO_REC[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(OLC_TERMINALS_INFO_REC[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public OLC_TERMINALS_INFO_REC getElement(long index) throws SQLException
  {
    return (OLC_TERMINALS_INFO_REC) _array.getObjectElement(index);
  }

  public void setElement(OLC_TERMINALS_INFO_REC a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
