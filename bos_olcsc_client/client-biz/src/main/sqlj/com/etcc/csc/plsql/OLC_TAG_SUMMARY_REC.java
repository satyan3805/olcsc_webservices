package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_TAG_SUMMARY_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_TAG_SUMMARY_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,12,12,12,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[6];
  protected static final OLC_TAG_SUMMARY_REC _OLC_TAG_SUMMARY_RECFactory = new OLC_TAG_SUMMARY_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_TAG_SUMMARY_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[6], _sqlType, _factory); }
  public OLC_TAG_SUMMARY_REC()
  { _init_struct(true); }
  public OLC_TAG_SUMMARY_REC(String TAG_ID, String UNIT_ID, String LICENSE_PLATE, String DESCRIPTION, java.math.BigDecimal QUANTITY, java.math.BigDecimal AMOUNT) throws SQLException
  { _init_struct(true);
    setTAG_ID(TAG_ID);
    setUNIT_ID(UNIT_ID);
    setLICENSE_PLATE(LICENSE_PLATE);
    setDESCRIPTION(DESCRIPTION);
    setQUANTITY(QUANTITY);
    setAMOUNT(AMOUNT);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_TAG_SUMMARY_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_TAG_SUMMARY_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getTAG_ID() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setTAG_ID(String TAG_ID) throws SQLException
  { _struct.setAttribute(0, TAG_ID); }


  public String getUNIT_ID() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setUNIT_ID(String UNIT_ID) throws SQLException
  { _struct.setAttribute(1, UNIT_ID); }


  public String getLICENSE_PLATE() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLICENSE_PLATE(String LICENSE_PLATE) throws SQLException
  { _struct.setAttribute(2, LICENSE_PLATE); }


  public String getDESCRIPTION() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setDESCRIPTION(String DESCRIPTION) throws SQLException
  { _struct.setAttribute(3, DESCRIPTION); }


  public java.math.BigDecimal getQUANTITY() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setQUANTITY(java.math.BigDecimal QUANTITY) throws SQLException
  { _struct.setAttribute(4, QUANTITY); }


  public java.math.BigDecimal getAMOUNT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setAMOUNT(java.math.BigDecimal AMOUNT) throws SQLException
  { _struct.setAttribute(5, AMOUNT); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_TAG_SUMMARY_REC" + "(" +
       ((getTAG_ID()==null)?"null": "'" + getTAG_ID()+"'" ) + "," +
       ((getUNIT_ID()==null)?"null": "'" + getUNIT_ID()+"'" ) + "," +
       ((getLICENSE_PLATE()==null)?"null": "'" + getLICENSE_PLATE()+"'" ) + "," +
       ((getDESCRIPTION()==null)?"null": "'" + getDESCRIPTION()+"'" ) + "," +
       getQUANTITY() + "," +
       getAMOUNT() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
