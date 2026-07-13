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

public class OLCSC_FRP_API_INVOICE_TBL_ACC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLCSC_FRP_API_INVOICE_TBL_ACC";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final OLCSC_FRP_API_INVOICE_TBL_ACC _OLCSC_FRP_API_INVOICE_TBL_ACCFactory = new OLCSC_FRP_API_INVOICE_TBL_ACC();

  public static ORADataFactory getORADataFactory()
  { return _OLCSC_FRP_API_INVOICE_TBL_ACCFactory; }
  /* constructors */
  public OLCSC_FRP_API_INVOICE_TBL_ACC()
  {
    this((OLCSC_FRP_API_INVOICE_REC_ACC[])null);
  }

  public OLCSC_FRP_API_INVOICE_TBL_ACC(OLCSC_FRP_API_INVOICE_REC_ACC[] a)
  {
    _array = new MutableArray(2002, a, OLCSC_FRP_API_INVOICE_REC_ACC.getORADataFactory());
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
    OLCSC_FRP_API_INVOICE_TBL_ACC a = new OLCSC_FRP_API_INVOICE_TBL_ACC();
    a._array = new MutableArray(2002, (ARRAY) d, OLCSC_FRP_API_INVOICE_REC_ACC.getORADataFactory());
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
  public OLCSC_FRP_API_INVOICE_REC_ACC[] getArray() throws SQLException
  {
    return (OLCSC_FRP_API_INVOICE_REC_ACC[]) _array.getObjectArray(
      new OLCSC_FRP_API_INVOICE_REC_ACC[_array.length()]);
  }

  public OLCSC_FRP_API_INVOICE_REC_ACC[] getArray(long index, int count) throws SQLException
  {
    return (OLCSC_FRP_API_INVOICE_REC_ACC[]) _array.getObjectArray(index,
      new OLCSC_FRP_API_INVOICE_REC_ACC[_array.sliceLength(index, count)]);
  }

  public void setArray(OLCSC_FRP_API_INVOICE_REC_ACC[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(OLCSC_FRP_API_INVOICE_REC_ACC[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public OLCSC_FRP_API_INVOICE_REC_ACC getElement(long index) throws SQLException
  {
    return (OLCSC_FRP_API_INVOICE_REC_ACC) _array.getObjectElement(index);
  }

  public void setElement(OLCSC_FRP_API_INVOICE_REC_ACC a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

  public String toString()
  { try { String r = "OL_OWNER.OLCSC_FRP_API_INVOICE_TBL_ACC" + "(";
     OLCSC_FRP_API_INVOICE_REC_ACC[] a = (OLCSC_FRP_API_INVOICE_REC_ACC[])getArray();
     for (int i=0; i<a.length; ) {
       r = r + a[i];
       i++; if (i<a.length) r = r + ","; }
     r = r + ")"; return r;
    } catch (SQLException e) { return e.toString(); }
  }

}
