package com.etcc.csc.common;

import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dao.AdminDAO;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dao.CountryDAO;
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dao.DbLoggerDAO;
import com.etcc.csc.dao.InventoryDAO;
import com.etcc.csc.dao.MenuDAO;
import com.etcc.csc.dao.OracleAccountDAO;
import com.etcc.csc.dao.OracleAccountHistoryDAO;
import com.etcc.csc.dao.OracleAdminDAO;
import com.etcc.csc.dao.OracleAppDAO;
import com.etcc.csc.dao.OracleCountryDAO;
import com.etcc.csc.dao.OracleCreditCardDAO;
import com.etcc.csc.dao.OracleDbLoggerDAO;
import com.etcc.csc.dao.OracleInventoryDAO;
import com.etcc.csc.dao.OracleMenuDAO;
import com.etcc.csc.dao.OracleOrderDAO;
import com.etcc.csc.dao.OraclePasswordRetrievalDAO;
import com.etcc.csc.dao.OraclePaymentDAO;
import com.etcc.csc.dao.OracleSecurityQuestionDAO;
import com.etcc.csc.dao.OracleSessionDAO;
import com.etcc.csc.dao.OracleStateDAO;
import com.etcc.csc.dao.OracleTagAuthorityDAO;
import com.etcc.csc.dao.OracleTagRequestDAO;
import com.etcc.csc.dao.OracleTolltagDAO;
import com.etcc.csc.dao.OracleVehicleDAO;
import com.etcc.csc.dao.OracleVelcroDAO;
import com.etcc.csc.dao.OracleViolationDAO;
import com.etcc.csc.dao.OracleZipCashDAO;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.dao.PasswordRetrievalDAO;
import com.etcc.csc.dao.PaymentDAO;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dao.TagAuthorityDAO;
import com.etcc.csc.dao.TagRequestDAO;
import com.etcc.csc.dao.TolltagDAO;
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dao.VelcroDAO;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dao.ZipCashDAO;
import com.etcc.csc.oracleerrortest.dao.ErrorTestDAO;
import com.etcc.csc.oracleerrortest.dao.OracleErrorTestDAO;

/**
 * Oracle implementation of the dao factory.
 */
public class OracleDAOFactory extends DAOFactory {

	public OracleDAOFactory() {
	}

	public AccountDAO getAccountDAO() {
		return new OracleAccountDAO();

	}

	public AccountHistoryDAO getAccountHistoryDAO() {
		return new OracleAccountHistoryDAO();

	}

	public ErrorTestDAO getErrorTestDAO() {
		return new OracleErrorTestDAO();

	}

	public MenuDAO getMenuDAO() {
		return new OracleMenuDAO();

	}

	public ViolationDAO getViolationDAO() {
		return new OracleViolationDAO();

	}

	public StateDAO getStateDAO() {
		return new OracleStateDAO();

	}

	public TagAuthorityDAO getTagAuthorityDAO() {
		return new OracleTagAuthorityDAO();

	}

	public SessionDAO getSessionDAO() {
		return new OracleSessionDAO();

	}

	public PasswordRetrievalDAO getPasswordRetrievalDAO() {
		return new OraclePasswordRetrievalDAO();

	}

	public DbLoggerDAO getDbLoggerDAO() {
		return new OracleDbLoggerDAO();

	}

	public SecurityQuestionDAO getSecurityQuestionDAO() {
		return new OracleSecurityQuestionDAO();

	}

	public CreditCardDAO getCreditCardDAO() {
		return new OracleCreditCardDAO();

	}

	public VehicleDAO getVehicleDAO() {
		return new OracleVehicleDAO();

	}

	public TolltagDAO getTolltagDAO() {
		return new OracleTolltagDAO();

	}

	public PaymentDAO getPaymentDAO() {
		return new OraclePaymentDAO();

	}

	public TagRequestDAO getTagRequestDAO() {
		return new OracleTagRequestDAO();

	}

	public VelcroDAO getVelcroDAO() {
		return new OracleVelcroDAO();

	}

	public OrderDAO getOrderDAO() {
		return new OracleOrderDAO();

	}

	public AppDAO getAppDAO() {
		return new OracleAppDAO();

	}

	public AdminDAO getAdminDAO() {
		return new OracleAdminDAO();

	}

	public ZipCashDAO getZipCashDAO() {
		return new OracleZipCashDAO();

	}

	@Override
	public InventoryDAO getInventoryDAO() {
		return new OracleInventoryDAO();
	}

	@Override
	public CountryDAO getcountryDAO() {
		return new OracleCountryDAO();
	}
}
