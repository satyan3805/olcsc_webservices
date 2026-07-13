package com.etcc.csc.common;


import com.etcc.csc.dao.AccountDAO;

import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dao.AdminDAO;
import com.etcc.csc.dao.CountryDAO;
import com.etcc.csc.dao.InventoryDAO;
import com.etcc.csc.dao.PasswordRetrievalDAO;
import com.etcc.csc.dao.TagAuthorityDAO;
import com.etcc.csc.dao.TagRequestDAO;
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dao.DbLoggerDAO;
import com.etcc.csc.dao.MenuDAO;
import com.etcc.csc.dao.OrderDAO;
import com.etcc.csc.oracleerrortest.dao.ErrorTestDAO;

import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dao.StateDAO;

import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dao.VelcroDAO;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dao.PaymentDAO;
import com.etcc.csc.dao.TolltagDAO;

public abstract class DAOFactory {
    // List of DAO types supported by the factory
    public static final int ORACLE = 1;

    public static final int DEFAULT_DAO = 1;

    public DAOFactory() {
    }

    // There will be a method for each DAO that can be 
    // created. The concrete factories will have to 
    // implement these methods.

    public abstract AccountDAO getAccountDAO();
    
    public abstract AdminDAO getAdminDAO();

    public abstract ErrorTestDAO getErrorTestDAO();

    public abstract AccountHistoryDAO getAccountHistoryDAO();

    public abstract MenuDAO getMenuDAO();

    public abstract ViolationDAO getViolationDAO();

    public abstract StateDAO getStateDAO();    

    public abstract CountryDAO getcountryDAO();

    public abstract TagAuthorityDAO getTagAuthorityDAO();

    public abstract SessionDAO getSessionDAO();

    public abstract PasswordRetrievalDAO getPasswordRetrievalDAO();

    public abstract DbLoggerDAO getDbLoggerDAO();

    public abstract SecurityQuestionDAO getSecurityQuestionDAO();

    public abstract CreditCardDAO getCreditCardDAO();

    public abstract VehicleDAO getVehicleDAO();

    public abstract TolltagDAO getTolltagDAO();
    
    public abstract PaymentDAO getPaymentDAO();

    public abstract TagRequestDAO getTagRequestDAO();

    public abstract VelcroDAO getVelcroDAO();

    public abstract OrderDAO getOrderDAO();
    public abstract AppDAO getAppDAO();
    public abstract com.etcc.csc.dao.ZipCashDAO getZipCashDAO();
    public abstract InventoryDAO getInventoryDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
        case ORACLE:
            return new OracleDAOFactory();
        default:
            return null;
        }
    }

    public static DAOFactory getDAOFactory() {
        return getDAOFactory(DEFAULT_DAO);
    }
}
