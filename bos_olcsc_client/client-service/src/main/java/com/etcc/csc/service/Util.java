package com.etcc.csc.service;

import java.util.Collection;

import javax.jws.WebService;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.DbLoggerDAO;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dao.StateDAO;
import com.etcc.csc.dao.TagDAO;
import com.etcc.csc.dao.TolltagDAO;
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.LovDTO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;
import com.etcc.csc.dto.bean.OlcMarketSourceRecBean;

/**
 * Util is the Component that's published for web service util.ws,
 * which provides the following operations:
 * <ul>
 * <li>getCountry
 * <li>getCreditCardTypes
 * <li>getMarketSource
 * <li>getSecurityQuestion
 * <li>getSecurityQuestions
 * <li>getStates
 * <li>getTagApplicationAgreement
 * <li>getTagAuthorities
 * <li>getVehicleClasses
 * <li>getVehicleMakes
 * </ul>
 *
 * @author      Wade Wang
 * @since       phase 1
 */
@SuppressWarnings("deprecation")
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Util", targetNamespace = "http://ws.csc.etcc.com/Util")
@Deprecated
public class Util implements UtilInterface {

    public Collection<LovDTO> getLov(String lovName) throws EtccException, EtccSecurityException {
        throw new RuntimeException("Not currently implemented");
//        try {
//          DAOFactory daoFactory = DAOFactory.getDAOFactory();
//          LovDAO stateDao = daoFactory.getDAO(LovDAO.class);
//          Collection<LovDTO> col = stateDao.getLov(lovName);
//          return col;
//        } catch (EtccSecurityException ese) {
//          this.logger.error("Security exception in getLov() " + ese, ese);
//          throw ese;
//        } catch (EtccException ee) {
//          // TODO: must call db api to log error
//          // this is because the stack trace cannot be transported over
//          // the web service
//          this.logger.error("Exception in getStates() " + ee, ee);
//          throw ee;
//        }
    }

    /**
     * Retrieves the collection of states for the default country.
     * @return
     */
    StateDTO[] getStates() throws EtccException, EtccSecurityException {
          DAOFactory daoFactory = DAOFactory.getDAOFactory();
          StateDAO stateDao = daoFactory.getDAO(StateDAO.class);
          StateDTO[] col = stateDao.getStates();
          return col;
    }

//    /**
//     * Retrieves the collection of countries from the country table.
//     * @return
//     */
//    //TODO: This should go through the Country worker object.
//    public Collection<CountryDTO> getCountries() throws EtccException {
//		ActivityLogger.logWSCall(this, "getCountries", null);
//            DAOFactory daoFactory = DAOFactory.getDAOFactory();
//            CountryDAO countryDao = daoFactory.getDAO(CountryDAO.class);
//            Collection<CountryDTO> col = countryDao.getCountries();
//            return col;
//    }

//    //TODO: This should go through the Country worker object.
//    public static String getCountryCode(String countryName) throws EtccException {
//        String countryCode = "";
//        if ((countryName != null) && (countryName.trim().length() > 0)) {
//            DAOFactory daoFactory = DAOFactory.getDAOFactory();
//            CountryDAO countryDao = daoFactory.getDAO(CountryDAO.class);
//            Collection<CountryDTO> countries = countryDao.getCountries();
//            for (CountryDTO country : countries) {
//                if (country.getCountry().equalsIgnoreCase(countryName)) {
//                    countryCode = country.getCountryCode();
//                }
//            }
//        }
//            return countryCode;
//    }


    /**
     * Retrieves a list of vehicle classes.
     * @return A collection of VehicleClassDTO.
     * @throws EtccException
     */
    public Collection<VehicleClassDTO> getVehicleClasses() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        VehicleDAO vehicleDao = daoFactory.getDAO(VehicleDAO.class);
        Collection<VehicleClassDTO> col = vehicleDao.getVehicleClasses();
        return col;
    }

    Collection<TagAuthorityDTO> getTagAuthorities() throws EtccException,
        EtccSecurityException {

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO taDao = daoFactory.getDAO(TagDAO.class);
        return taDao.getTagAuthorities();
    }


    String getTagApplicationAgreement( ) throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO taDao = daoFactory.getDAO(TagDAO.class);
        return taDao.getTagApplicationAgreement();
    }

    Collection<SecurityQuestionDTO> getSecurityQuestions() throws EtccException, EtccSecurityException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        SecurityQuestionDAO securityQuestionDao = daoFactory.getDAO(SecurityQuestionDAO.class);
        Collection<SecurityQuestionDTO> col = securityQuestionDao.getSecurityQuestions();
        return col;
    }

    Collection<VehicleMakeDTO> getVehicleMakes() throws EtccException{
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        VehicleDAO vehicleMakeDao = daoFactory.getDAO(VehicleDAO.class);
        Collection<VehicleMakeDTO> col = vehicleMakeDao.getVehicleMakes();
        return col;
    }


    String logError(String message, String stack) throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DbLoggerDAO dbLoggerDao = daoFactory.getDAO(DbLoggerDAO.class);
        String result = dbLoggerDao.logError(message, stack);
        return result;
    }

    String logSecurityViolation(String message) throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DbLoggerDAO dbLoggerDao = daoFactory.getDAO(DbLoggerDAO.class);
        String result = dbLoggerDao.logSecurityViolation(message);
        return result;
    }

    public Collection<CreditCardDTO> getCreditCardTypes() throws EtccException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CreditCardDAO ccDao = daoFactory.getDAO(CreditCardDAO.class);
        Collection<CreditCardDTO> col = ccDao.getCreditCardTypes();
        return col;
    }


    public OlcMarketSourceRecBean[] getMarketSource() throws EtccException {
        // validation - none needed
        return DAOFactory.getDAOFactory().getDAO(TolltagDAO.class).getMarketSource();
    }

}
