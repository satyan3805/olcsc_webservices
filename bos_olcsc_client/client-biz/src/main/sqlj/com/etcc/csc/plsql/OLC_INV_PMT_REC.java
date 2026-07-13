package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_INV_PMT_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_INV_PMT_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,2,2,2,2003,12,2003 };
  protected static ORADataFactory[] _factory = new ORADataFactory[7];
  static
  {
    _factory[4] = OLC_OPEN_FEE_ARR.getORADataFactory();
    _factory[6] = OLC_VPS_INV_ARR_D.getORADataFactory();
  }
  protected static final OLC_INV_PMT_REC _OLC_INV_PMT_RECFactory = new OLC_INV_PMT_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_INV_PMT_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[7], _sqlType, _factory); }
  public OLC_INV_PMT_REC()
  { _init_struct(true); }
  public OLC_INV_PMT_REC(java.math.BigDecimal ACCOUNT_ID, java.math.BigDecimal INVOICE_ID, java.math.BigDecimal INVOICE_PAID_AMT, java.math.BigDecimal INVOICE_DISC_AMT, OLC_OPEN_FEE_ARR INVOICE_FEE_ARR, String DISCOUNT_RULE, OLC_VPS_INV_ARR_D LINE_ITEMS) throws SQLException
  { _init_struct(true);
    setACCOUNT_ID(ACCOUNT_ID);
    setINVOICE_ID(INVOICE_ID);
    setINVOICE_PAID_AMT(INVOICE_PAID_AMT);
    setINVOICE_DISC_AMT(INVOICE_DISC_AMT);
    setINVOICE_FEE_ARR(INVOICE_FEE_ARR);
    setDISCOUNT_RULE(DISCOUNT_RULE);
    setLINE_ITEMS(LINE_ITEMS);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_INV_PMT_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_INV_PMT_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getACCOUNT_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setACCOUNT_ID(java.math.BigDecimal ACCOUNT_ID) throws SQLException
  { _struct.setAttribute(0, ACCOUNT_ID); }


  public java.math.BigDecimal getINVOICE_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setINVOICE_ID(java.math.BigDecimal INVOICE_ID) throws SQLException
  { _struct.setAttribute(1, INVOICE_ID); }


  public java.math.BigDecimal getINVOICE_PAID_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(2); }

  public void setINVOICE_PAID_AMT(java.math.BigDecimal INVOICE_PAID_AMT) throws SQLException
  { _struct.setAttribute(2, INVOICE_PAID_AMT); }


  public java.math.BigDecimal getINVOICE_DISC_AMT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setINVOICE_DISC_AMT(java.math.BigDecimal INVOICE_DISC_AMT) throws SQLException
  { _struct.setAttribute(3, INVOICE_DISC_AMT); }


  public OLC_OPEN_FEE_ARR getINVOICE_FEE_ARR() throws SQLException
  { return (OLC_OPEN_FEE_ARR) _struct.getAttribute(4); }

  public void setINVOICE_FEE_ARR(OLC_OPEN_FEE_ARR INVOICE_FEE_ARR) throws SQLException
  { _struct.setAttribute(4, INVOICE_FEE_ARR); }


  public String getDISCOUNT_RULE() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setDISCOUNT_RULE(String DISCOUNT_RULE) throws SQLException
  { _struct.setAttribute(5, DISCOUNT_RULE); }


  public OLC_VPS_INV_ARR_D getLINE_ITEMS() throws SQLException
  { return (OLC_VPS_INV_ARR_D) _struct.getAttribute(6); }

  public void setLINE_ITEMS(OLC_VPS_INV_ARR_D LINE_ITEMS) throws SQLException
  { _struct.setAttribute(6, LINE_ITEMS); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_INV_PMT_REC" + "(" +
       getACCOUNT_ID() + "," +
       getINVOICE_ID() + "," +
       getINVOICE_PAID_AMT() + "," +
       getINVOICE_DISC_AMT() + "," +
       getINVOICE_FEE_ARR() + "," +
       ((getDISCOUNT_RULE()==null)?"null": "'" + getDISCOUNT_RULE()+"'" ) + "," +
       getLINE_ITEMS() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
