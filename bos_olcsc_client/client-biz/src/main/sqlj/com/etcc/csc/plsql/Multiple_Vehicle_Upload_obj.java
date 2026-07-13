package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class Multiple_Vehicle_Upload_obj implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_OBJ";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 2,12,12,12,12,12,12,12,12,12,12,12,12,12,12 };
  protected static ORADataFactory[] _factory = new ORADataFactory[15];
  protected static final Multiple_Vehicle_Upload_obj _Multiple_Vehicle_Upload_objFactory = new Multiple_Vehicle_Upload_obj();

  public static ORADataFactory getORADataFactory()
  { return _Multiple_Vehicle_Upload_objFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[15], _sqlType, _factory); }
  public Multiple_Vehicle_Upload_obj()
  { _init_struct(true); }
  public Multiple_Vehicle_Upload_obj(java.math.BigDecimal ROW_NO, String VEH_ACTION, String IS_EZTAG, String LICENSEPLATESTATE, String LICENSEPLATENUMBER, String IS_TEMPORARY_PLATE, String VEH_CLASS_DESC, String VEHICLE_YEAR, String VEHICLE_COLOR, String VEHICLE_MAKE, String VEHICLE_MODEL, String VEHICLE_NICKNAME, String ERROR_TYPE, String ERROR_MSG, String VEH_CLASS_CODE) throws SQLException
  { _init_struct(true);
    setROW_NO(ROW_NO);
    setVEH_ACTION(VEH_ACTION);
    setIS_EZTAG(IS_EZTAG);
    setLICENSEPLATESTATE(LICENSEPLATESTATE);
    setLICENSEPLATENUMBER(LICENSEPLATENUMBER);
    setIS_TEMPORARY_PLATE(IS_TEMPORARY_PLATE);
    setVEH_CLASS_DESC(VEH_CLASS_DESC);
    setVEHICLE_YEAR(VEHICLE_YEAR);
    setVEHICLE_COLOR(VEHICLE_COLOR);
    setVEHICLE_MAKE(VEHICLE_MAKE);
    setVEHICLE_MODEL(VEHICLE_MODEL);
    setVEHICLE_NICKNAME(VEHICLE_NICKNAME);
    setERROR_TYPE(ERROR_TYPE);
    setERROR_MSG(ERROR_MSG);
    setVEH_CLASS_CODE(VEH_CLASS_CODE);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(Multiple_Vehicle_Upload_obj o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new Multiple_Vehicle_Upload_obj();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getROW_NO() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setROW_NO(java.math.BigDecimal ROW_NO) throws SQLException
  { _struct.setAttribute(0, ROW_NO); }


  public String getVEH_ACTION() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setVEH_ACTION(String VEH_ACTION) throws SQLException
  { _struct.setAttribute(1, VEH_ACTION); }


  public String getIS_EZTAG() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setIS_EZTAG(String IS_EZTAG) throws SQLException
  { _struct.setAttribute(2, IS_EZTAG); }


  public String getLICENSEPLATESTATE() throws SQLException
  { return (String) _struct.getAttribute(3); }

  public void setLICENSEPLATESTATE(String LICENSEPLATESTATE) throws SQLException
  { _struct.setAttribute(3, LICENSEPLATESTATE); }


  public String getLICENSEPLATENUMBER() throws SQLException
  { return (String) _struct.getAttribute(4); }

  public void setLICENSEPLATENUMBER(String LICENSEPLATENUMBER) throws SQLException
  { _struct.setAttribute(4, LICENSEPLATENUMBER); }


  public String getIS_TEMPORARY_PLATE() throws SQLException
  { return (String) _struct.getAttribute(5); }

  public void setIS_TEMPORARY_PLATE(String IS_TEMPORARY_PLATE) throws SQLException
  { _struct.setAttribute(5, IS_TEMPORARY_PLATE); }


  public String getVEH_CLASS_DESC() throws SQLException
  { return (String) _struct.getAttribute(6); }

  public void setVEH_CLASS_DESC(String VEH_CLASS_DESC) throws SQLException
  { _struct.setAttribute(6, VEH_CLASS_DESC); }


  public String getVEHICLE_YEAR() throws SQLException
  { return (String) _struct.getAttribute(7); }

  public void setVEHICLE_YEAR(String VEHICLE_YEAR) throws SQLException
  { _struct.setAttribute(7, VEHICLE_YEAR); }


  public String getVEHICLE_COLOR() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setVEHICLE_COLOR(String VEHICLE_COLOR) throws SQLException
  { _struct.setAttribute(8, VEHICLE_COLOR); }


  public String getVEHICLE_MAKE() throws SQLException
  { return (String) _struct.getAttribute(9); }

  public void setVEHICLE_MAKE(String VEHICLE_MAKE) throws SQLException
  { _struct.setAttribute(9, VEHICLE_MAKE); }


  public String getVEHICLE_MODEL() throws SQLException
  { return (String) _struct.getAttribute(10); }

  public void setVEHICLE_MODEL(String VEHICLE_MODEL) throws SQLException
  { _struct.setAttribute(10, VEHICLE_MODEL); }


  public String getVEHICLE_NICKNAME() throws SQLException
  { return (String) _struct.getAttribute(11); }

  public void setVEHICLE_NICKNAME(String VEHICLE_NICKNAME) throws SQLException
  { _struct.setAttribute(11, VEHICLE_NICKNAME); }


  public String getERROR_TYPE() throws SQLException
  { return (String) _struct.getAttribute(12); }

  public void setERROR_TYPE(String ERROR_TYPE) throws SQLException
  { _struct.setAttribute(12, ERROR_TYPE); }


  public String getERROR_MSG() throws SQLException
  { return (String) _struct.getAttribute(13); }

  public void setERROR_MSG(String ERROR_MSG) throws SQLException
  { _struct.setAttribute(13, ERROR_MSG); }


  public String getVEH_CLASS_CODE() throws SQLException
  { return (String) _struct.getAttribute(14); }

  public void setVEH_CLASS_CODE(String VEH_CLASS_CODE) throws SQLException
  { _struct.setAttribute(14, VEH_CLASS_CODE); }

  public String toString()
  { try {
     return "OL_OWNER.MULTIPLE_VEHICLE_UPLOAD_OBJ" + "(" +
       getROW_NO() + "," +
       ((getVEH_ACTION()==null)?"null": "'" + getVEH_ACTION()+"'" ) + "," +
       ((getIS_EZTAG()==null)?"null": "'" + getIS_EZTAG()+"'" ) + "," +
       ((getLICENSEPLATESTATE()==null)?"null": "'" + getLICENSEPLATESTATE()+"'" ) + "," +
       ((getLICENSEPLATENUMBER()==null)?"null": "'" + getLICENSEPLATENUMBER()+"'" ) + "," +
       ((getIS_TEMPORARY_PLATE()==null)?"null": "'" + getIS_TEMPORARY_PLATE()+"'" ) + "," +
       ((getVEH_CLASS_DESC()==null)?"null": "'" + getVEH_CLASS_DESC()+"'" ) + "," +
       ((getVEHICLE_YEAR()==null)?"null": "'" + getVEHICLE_YEAR()+"'" ) + "," +
       ((getVEHICLE_COLOR()==null)?"null": "'" + getVEHICLE_COLOR()+"'" ) + "," +
       ((getVEHICLE_MAKE()==null)?"null": "'" + getVEHICLE_MAKE()+"'" ) + "," +
       ((getVEHICLE_MODEL()==null)?"null": "'" + getVEHICLE_MODEL()+"'" ) + "," +
       ((getVEHICLE_NICKNAME()==null)?"null": "'" + getVEHICLE_NICKNAME()+"'" ) + "," +
       ((getERROR_TYPE()==null)?"null": "'" + getERROR_TYPE()+"'" ) + "," +
       ((getERROR_MSG()==null)?"null": "'" + getERROR_MSG()+"'" ) + "," +
       ((getVEH_CLASS_CODE()==null)?"null": "'" + getVEH_CLASS_CODE()+"'" ) +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
