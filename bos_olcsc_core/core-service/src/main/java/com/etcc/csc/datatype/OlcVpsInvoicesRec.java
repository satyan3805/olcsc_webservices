package com.etcc.csc.datatype;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import oracle.jpub.runtime.MutableStruct;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.STRUCT;

public class OlcVpsInvoicesRec implements ORAData, ORADataFactory {
	public static final String _SQL_NAME = "OL_OWNER.OLC_VPS_INVOICES_REC";
	public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

	protected MutableStruct _struct;

	protected static int[] _sqlType = { 2, 91, 91, 12, 12, 12, 12, 2, 2, 2, 2,
			2003 };
	protected static ORADataFactory[] _factory = new ORADataFactory[12];
	static {
		_factory[11] = OlcViolationArr.getORADataFactory();
	}
	protected static final OlcVpsInvoicesRec _OlcVpsInvoicesRecFactory = new OlcVpsInvoicesRec();

	public static ORADataFactory getORADataFactory() {
		return _OlcVpsInvoicesRecFactory;
	}

	/* constructors */
	protected void _init_struct(boolean init) {
		if (init)
			_struct = new MutableStruct(new Object[12], _sqlType, _factory);
	}

	public OlcVpsInvoicesRec() {
		_init_struct(true);
	}

	public OlcVpsInvoicesRec(java.math.BigDecimal invoiceId,
			java.sql.Timestamp invoiceDate, java.sql.Timestamp currDueDate,
			String firstName, String lastName, String licPlateNbr,
			String licState, java.math.BigDecimal invoiceAmount,
			java.math.BigDecimal veaAmount, java.math.BigDecimal onlineFee,
			java.math.BigDecimal violatorId, OlcViolationArr violations)
			throws SQLException {
		_init_struct(true);
		setInvoiceId(invoiceId);
		setInvoiceDate(invoiceDate);
		setCurrDueDate(currDueDate);
		setFirstName(firstName);
		setLastName(lastName);
		setLicPlateNbr(licPlateNbr);
		setLicState(licState);
		setInvoiceAmount(invoiceAmount);
		setVeaAmount(veaAmount);
		setOnlineFee(onlineFee);
		setViolatorId(violatorId);
		setViolations(violations);
	}

	/* ORAData interface */
	public Datum toDatum(Connection c) throws SQLException {
		return _struct.toDatum(c, _SQL_NAME);
	}

	/* ORADataFactory interface */
	public ORAData create(Datum d, int sqlType) throws SQLException {
		return create(null, d, sqlType);
	}

	protected ORAData create(OlcVpsInvoicesRec o, Datum d, int sqlType)
			throws SQLException {
		if (d == null)
			return null;
		if (o == null)
			o = new OlcVpsInvoicesRec();
		o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
		return o;
	}

	/* accessor methods */
	public java.math.BigDecimal getInvoiceId() throws SQLException {
		return (java.math.BigDecimal) _struct.getAttribute(0);
	}

	public void setInvoiceId(java.math.BigDecimal invoiceId)
			throws SQLException {
		_struct.setAttribute(0, invoiceId);
	}

	public java.sql.Timestamp getInvoiceDate() throws SQLException {
		return (java.sql.Timestamp) _struct.getAttribute(1);
	}

	public void setInvoiceDate(java.sql.Timestamp invoiceDate)
			throws SQLException {
		_struct.setAttribute(1, invoiceDate);
	}

	public java.sql.Timestamp getCurrDueDate() throws SQLException {
		return (java.sql.Timestamp) _struct.getAttribute(2);
	}

	public void setCurrDueDate(java.sql.Timestamp currDueDate)
			throws SQLException {
		_struct.setAttribute(2, currDueDate);
	}

	public String getFirstName() throws SQLException {
		return (String) _struct.getAttribute(3);
	}

	public void setFirstName(String firstName) throws SQLException {
		_struct.setAttribute(3, firstName);
	}

	public String getLastName() throws SQLException {
		return (String) _struct.getAttribute(4);
	}

	public void setLastName(String lastName) throws SQLException {
		_struct.setAttribute(4, lastName);
	}

	public String getLicPlateNbr() throws SQLException {
		return (String) _struct.getAttribute(5);
	}

	public void setLicPlateNbr(String licPlateNbr) throws SQLException {
		_struct.setAttribute(5, licPlateNbr);
	}

	public String getLicState() throws SQLException {
		return (String) _struct.getAttribute(6);
	}

	public void setLicState(String licState) throws SQLException {
		_struct.setAttribute(6, licState);
	}

	public java.math.BigDecimal getInvoiceAmount() throws SQLException {
		return (java.math.BigDecimal) _struct.getAttribute(7);
	}

	public void setInvoiceAmount(java.math.BigDecimal invoiceAmount)
			throws SQLException {
		_struct.setAttribute(7, invoiceAmount);
	}

	public java.math.BigDecimal getVeaAmount() throws SQLException {
		return (java.math.BigDecimal) _struct.getAttribute(8);
	}

	public void setVeaAmount(java.math.BigDecimal veaAmount)
			throws SQLException {
		_struct.setAttribute(8, veaAmount);
	}

	public java.math.BigDecimal getOnlineFee() throws SQLException {
		return (java.math.BigDecimal) _struct.getAttribute(9);
	}

	public void setOnlineFee(java.math.BigDecimal onlineFee)
			throws SQLException {
		_struct.setAttribute(9, onlineFee);
	}

	public java.math.BigDecimal getViolatorId() throws SQLException {
		return (java.math.BigDecimal) _struct.getAttribute(10);
	}

	public void setViolatorId(java.math.BigDecimal violatorId)
			throws SQLException {
		_struct.setAttribute(10, violatorId);
	}

	public OlcViolationArr getViolations() throws SQLException {
		return (OlcViolationArr) _struct.getAttribute(11);
	}

	public void setViolations(OlcViolationArr violations) throws SQLException {
		_struct.setAttribute(11, violations);
	}
}
