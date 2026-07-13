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

public class OLCSC_FRP_API_INVOICE_TBL implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLCSC_FRP_API_INVOICE_TBL";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OLCSC_FRP_API_INVOICE_TBL _OLCSC_FRP_API_INVOICE_TBLFactory = new OLCSC_FRP_API_INVOICE_TBL();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_FRP_API_INVOICE_TBLFactory; }
  /* constructors */
  public OLCSC_FRP_API_INVOICE_TBL()
  {
    this((OLCSC_FRP_API_INVOICE_REC[])null);
  }

  public OLCSC_FRP_API_INVOICE_TBL(OLCSC_FRP_API_INVOICE_REC[] a)
  {
    _array = new MutableArray(2002, a, OLCSC_FRP_API_INVOICE_REC.getORADataFactory());
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
    OLCSC_FRP_API_INVOICE_TBL a = new OLCSC_FRP_API_INVOICE_TBL();
    a._array = new MutableArray(2002, (ARRAY) d, OLCSC_FRP_API_INVOICE_REC.getORADataFactory());
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
  public OLCSC_FRP_API_INVOICE_REC[] getArray() throws SQLException
  {
    return (OLCSC_FRP_API_INVOICE_REC[]) _array.getObjectArray(
      new OLCSC_FRP_API_INVOICE_REC[_array.length()]);
  }

  public OLCSC_FRP_API_INVOICE_REC[] getArray(long index, int count) throws SQLException
  {
    return (OLCSC_FRP_API_INVOICE_REC[]) _array.getObjectArray(index,
      new OLCSC_FRP_API_INVOICE_REC[_array.sliceLength(index, count)]);
  }

  public void setArray(OLCSC_FRP_API_INVOICE_REC[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(OLCSC_FRP_API_INVOICE_REC[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public OLCSC_FRP_API_INVOICE_REC getElement(long index) throws SQLException
  {
    return (OLCSC_FRP_API_INVOICE_REC) _array.getObjectElement(index);
  }

  public void setElement(OLCSC_FRP_API_INVOICE_REC a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.OLCSC_FRP_API_INVOICE_TBL" + "(";
     OLCSC_FRP_API_INVOICE_REC[] a = (OLCSC_FRP_API_INVOICE_REC[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
