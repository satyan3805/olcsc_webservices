package com.etcc.csc.plsql;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class OLC_S_QUESTIONS_REC implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "OL_OWNER.OLC_S_QUESTIONS_REC";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  protected static int[] _sqlType =  { 12,2,12,2 };
  protected static ORADataFactory[] _factory = new ORADataFactory[4];
  protected static final OLC_S_QUESTIONS_REC _OLC_S_QUESTIONS_RECFactory = new OLC_S_QUESTIONS_REC();

  public static ORADataFactory getORADataFactory()
  { return _OLC_S_QUESTIONS_RECFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public OLC_S_QUESTIONS_REC()
  { _init_struct(true); }
  public OLC_S_QUESTIONS_REC(String SECURITY_QUESTION, java.math.BigDecimal SECURITY_QUESTION_ID, String TRANS_SECURITY_QUESTION, java.math.BigDecimal SECURITY_QUESTION_ORDER) throws SQLException
  { _init_struct(true);
    setSECURITY_QUESTION(SECURITY_QUESTION);
    setSECURITY_QUESTION_ID(SECURITY_QUESTION_ID);
    setTRANS_SECURITY_QUESTION(TRANS_SECURITY_QUESTION);
    setSECURITY_QUESTION_ORDER(SECURITY_QUESTION_ORDER);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(OLC_S_QUESTIONS_REC o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new OLC_S_QUESTIONS_REC();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public String getSECURITY_QUESTION() throws SQLException
  { return (String) _struct.getAttribute(0); }

  public void setSECURITY_QUESTION(String SECURITY_QUESTION) throws SQLException
  { _struct.setAttribute(0, SECURITY_QUESTION); }


  public java.math.BigDecimal getSECURITY_QUESTION_ID() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(1); }

  public void setSECURITY_QUESTION_ID(java.math.BigDecimal SECURITY_QUESTION_ID) throws SQLException
  { _struct.setAttribute(1, SECURITY_QUESTION_ID); }


  public String getTRANS_SECURITY_QUESTION() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setTRANS_SECURITY_QUESTION(String TRANS_SECURITY_QUESTION) throws SQLException
  { _struct.setAttribute(2, TRANS_SECURITY_QUESTION); }


  public java.math.BigDecimal getSECURITY_QUESTION_ORDER() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setSECURITY_QUESTION_ORDER(java.math.BigDecimal SECURITY_QUESTION_ORDER) throws SQLException
  { _struct.setAttribute(3, SECURITY_QUESTION_ORDER); }

  public String toString()
  { try {
     return "OL_OWNER.OLC_S_QUESTIONS_REC" + "(" +
       ((getSECURITY_QUESTION()==null)?"null": "'" + getSECURITY_QUESTION()+"'" ) + "," +
       getSECURITY_QUESTION_ID() + "," +
       ((getTRANS_SECURITY_QUESTION()==null)?"null": "'" + getTRANS_SECURITY_QUESTION()+"'" ) + "," +
       getSECURITY_QUESTION_ORDER() +
     ")";
    } catch (Exception e) { return e.toString(); }
  }

}
