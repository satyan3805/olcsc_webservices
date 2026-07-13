package com.etcc.csc.datatype;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import oracle.jdbc.OracleTypes;

public class OlcMarketSourceRec implements SQLData {
	public static final String _SQL_NAME = "OL_OWNER.OLC_MARKET_SOURCE_REC";
	public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

	private java.math.BigDecimal m_msId;
	private String m_msDescr;

	/* constructor */
	public OlcMarketSourceRec() {
	}

	public OlcMarketSourceRec(java.math.BigDecimal msId, String msDescr)
			throws SQLException {
		setMsId(msId);
		setMsDescr(msDescr);
	}

	public void readSQL(SQLInput stream, String type) throws SQLException {
		setMsId(stream.readBigDecimal());
		setMsDescr(stream.readString());
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		stream.writeBigDecimal(getMsId());
		stream.writeString(getMsDescr());
	}

	public String getSQLTypeName() throws SQLException {
		return _SQL_NAME;
	}

	/* accessor methods */
	public java.math.BigDecimal getMsId() {
		return m_msId;
	}

	public void setMsId(java.math.BigDecimal msId) {
		m_msId = msId;
	}

	public String getMsDescr() {
		return m_msDescr;
	}

	public void setMsDescr(String msDescr) {
		m_msDescr = msDescr;
	}

}
