package com.etcc.csc.oracleerrortest.dao;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import oracle.jpub.runtime.MutableStruct;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.STRUCT;

public class ErrorMsgRec implements ORAData, ORADataFactory {
	public static final String _SQL_NAME = "OL_OWNER.OLC_ERROR_MSG_REC";
	public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

	protected MutableStruct _struct;

	protected static int[] _sqlType = { 12 };
	protected static ORADataFactory[] _factory = new ORADataFactory[1];
	protected static final ErrorMsgRec _ErrorMsgRecFactory = new ErrorMsgRec();

	public static ORADataFactory getORADataFactory() {
		return _ErrorMsgRecFactory;
	}

	/* constructors */
	protected void _init_struct(boolean init) {
		if (init)
			_struct = new MutableStruct(new Object[1], _sqlType, _factory);
	}

	public ErrorMsgRec() {
		_init_struct(true);
	}

	public ErrorMsgRec(String errMsg) throws SQLException {
		_init_struct(true);
		setErrMsg(errMsg);
	}

	/* ORAData interface */
	public Datum toDatum(Connection c) throws SQLException {
		return _struct.toDatum(c, _SQL_NAME);
	}

	/* ORADataFactory interface */
	public ORAData create(Datum d, int sqlType) throws SQLException {
		return create(null, d, sqlType);
	}

	protected ORAData create(ErrorMsgRec o, Datum d, int sqlType)
			throws SQLException {
		if (d == null)
			return null;
		if (o == null)
			o = new ErrorMsgRec();
		o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
		return o;
	}

	/* accessor methods */
	public String getErrMsg() throws SQLException {
		return (String) _struct.getAttribute(0);
	}

	public void setErrMsg(String errMsg) throws SQLException {
		_struct.setAttribute(0, errMsg);
	}
}
