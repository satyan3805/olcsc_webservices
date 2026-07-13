package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_MENU_ITEM_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_MENU_ITEM_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,12,2,12,12,2,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[9];
  protected static final OLC_MENU_ITEM_REC _OLC_MENU_ITEM_RECFactory = new OLC_MENU_ITEM_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_MENU_ITEM_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[9], _sqlType, _factory); }
  public OLC_MENU_ITEM_REC()
  { _init_struct(true); }
  public OLC_MENU_ITEM_REC(java.math.BigDecimal AP_ITEM_ID, String ITEM_TYPE, String ITEM_LABEL, String ITEM_URL, java.math.BigDecimal PARENT_AP_ITEM_ID, String ITEM_IMAGE, String ITEM_HELP_TEXT, java.math.BigDecimal ORDER_WITHIN_PARENT, java.math.BigDecimal MENU_LEVEL) throws SQLException
  { _init_struct(true);
    setAP_ITEM_ID(AP_ITEM_ID);
    setITEM_TYPE(ITEM_TYPE);
    setITEM_LABEL(ITEM_LABEL);
    setITEM_URL(ITEM_URL);
    setPARENT_AP_ITEM_ID(PARENT_AP_ITEM_ID);
    setITEM_IMAGE(ITEM_IMAGE);
    setITEM_HELP_TEXT(ITEM_HELP_TEXT);
    setORDER_WITHIN_PARENT(ORDER_WITHIN_PARENT);
    setMENU_LEVEL(MENU_LEVEL);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_MENU_ITEM_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_MENU_ITEM_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getAP_ITEM_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setAP_ITEM_ID(java.math.BigDecimal AP_ITEM_ID) throws SQLException
  { _struct.setAttribute(0, AP_ITEM_ID); }


  public String getITEM_TYPE() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setITEM_TYPE(String ITEM_TYPE) throws SQLException
  { _struct.setAttribute(1, ITEM_TYPE); }


  public String getITEM_LABEL() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setITEM_LABEL(String ITEM_LABEL) throws SQLException
  { _struct.setAttribute(2, ITEM_LABEL); }


  public String getITEM_URL() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setITEM_URL(String ITEM_URL) throws SQLException
  { _struct.setAttribute(3, ITEM_URL); }


  public java.math.BigDecimal getPARENT_AP_ITEM_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(4); }

  public void setPARENT_AP_ITEM_ID(java.math.BigDecimal PARENT_AP_ITEM_ID) throws SQLException
  { _struct.setAttribute(4, PARENT_AP_ITEM_ID); }


  public String getITEM_IMAGE() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setITEM_IMAGE(String ITEM_IMAGE) throws SQLException
  { _struct.setAttribute(5, ITEM_IMAGE); }


  public String getITEM_HELP_TEXT() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setITEM_HELP_TEXT(String ITEM_HELP_TEXT) throws SQLException
  { _struct.setAttribute(6, ITEM_HELP_TEXT); }


  public java.math.BigDecimal getORDER_WITHIN_PARENT() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setORDER_WITHIN_PARENT(java.math.BigDecimal ORDER_WITHIN_PARENT) throws SQLException
  { _struct.setAttribute(7, ORDER_WITHIN_PARENT); }


  public java.math.BigDecimal getMENU_LEVEL() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(8); }

  public void setMENU_LEVEL(java.math.BigDecimal MENU_LEVEL) throws SQLException
  { _struct.setAttribute(8, MENU_LEVEL); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_MENU_ITEM_REC" + "(" +
       getAP_ITEM_ID() + "," +
       ((getITEM_TYPE()==null)?"null": "'" + getITEM_TYPE()+"'" ) + "," +
       ((getITEM_LABEL()==null)?"null": "'" + getITEM_LABEL()+"'" ) + "," +
       ((getITEM_URL()==null)?"null": "'" + getITEM_URL()+"'" ) + "," +
       getPARENT_AP_ITEM_ID() + "," +
       ((getITEM_IMAGE()==null)?"null": "'" + getITEM_IMAGE()+"'" ) + "," +
       ((getITEM_HELP_TEXT()==null)?"null": "'" + getITEM_HELP_TEXT()+"'" ) + "," +
       getORDER_WITHIN_PARENT() + "," +
       getMENU_LEVEL() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
