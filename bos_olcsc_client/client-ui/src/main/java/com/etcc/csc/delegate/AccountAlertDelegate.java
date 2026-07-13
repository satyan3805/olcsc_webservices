package com.etcc.csc.delegate;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.service.AccountAlertInterface;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.util.UIDateUtil;

/**
 * GUI Wrapper for the AccountAlert service.
 * @author mmiller (original author)
 * @author Stephen Davidson (Task 488 refactor)
 */
public class AccountAlertDelegate implements AccountAlertInterface {
    private static final Logger logger = Logger.getLogger(AccountAlertDelegate.class);
/*
    private com.etcc.csc.accountalert.dto.AlertDTO[] toDtoAlerts(com.etcc.csc.accountalert.types.AlertDTO[] typesAlert) {
      if(typesAlert == null){
            return null;
        }
        com.etcc.csc.accountalert.dto.AlertDTO[] dtoAlerts = new com.etcc.csc.accountalert.dto.AlertDTO[typesAlert.length];
        for(int i=0;i<typesAlert.length;i++){
            com.etcc.csc.accountalert.dto.AlertDTO dtoAlert = new com.etcc.csc.accountalert.dto.AlertDTO();
            DtoUtil.copySimpleProperties(dtoAlert,typesAlert[i]);
            dtoAlerts[i] = dtoAlert;
           // if(dtoAlerts[i] != null && dtoAlerts[i].getAlertMsg()!= null)
            //logger.info("The alerts are as follows ...." + dtoAlerts[i].getAlertMsg());
            //This is for address cleansing alerts 
              //if(dtoAlerts[i].getAlertMsg().endsWith("address?")){ 
                  //String url = this.request.getContextPath()+"/addressCleansing.do?addClean=";
                   //dtoAlerts[i].setAlertMsg(dtoAlerts[i].getAlertMsg() + " <br> " + "<a href='+ url +\"A\"'> Yes </a> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='+ url +\"R\"'> No </a>   " );
             // }
        }
        return dtoAlerts;
    }
*/

    public AlertDTO[] getAcctSummAlerts(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
        // This routine is occasionally crashing due to DB interactions in the backend, possibly error reporting.
        logger.info("Start accountAlertWS.getAcctSummaryAlerts()" + UIDateUtil.timeStamp());
        AlertDTO[] typesAlert = null;
        typesAlert = stub().getAcctSummAlerts(acctLoginDto);
        logger.info("End accountAlertWS.getAcctSummaryAlerts()" + UIDateUtil.timeStamp());
        return typesAlert;
    }

//    /**
//     * Converts an array to a collection.
//     * @param alertDTOs The array to convert to a collection
//     * @return Collection of Alerts
//     * @deprecated The Alerts should already be available as a collection from the Account objects.  Other APIs
//     * should already be returning collections as well.
//     */
//    @Deprecated
//    public Collection<AlertDTO> getAlertCollection(AlertDTO[] alertDTOs){
//        if (alertDTOs == null){
//            return null;
//        } //else
//        return Arrays.asList(alertDTOs);
//    }

    public AlertDTO[] getVehicleInfoAlerts(AccountLoginDTO acctLoginDto) throws EtccSecurityException,EtccException {
         AlertDTO[] typesAlert = null;
         try {
        	 typesAlert = stub().getVehicleInfoAlerts(acctLoginDto);
        	 if (logger.isDebugEnabled()) {
        		 logger.debug("The Violation Alerts: " + Arrays.toString(typesAlert));
        	 }
         }catch(RuntimeException e){
        	 e.printStackTrace();             
        	 throw new EtccException("Account Alerts Exception", e);
         }
         return typesAlert;
    }

    public AlertDTO[] getContactInfoAlerts(AccountLoginDTO acctLoginDto) {
    	return null;
    }

   private AccountAlertInterface stub() {
       try {
	   return ServiceFactory.getImplementation(AccountAlertInterface.class);
       } catch (NoClassDefFoundError e){
           logger.fatal("Unable to load factory class:" + e.getMessage(), e);
           throw e;
       }
   }

   public ResultDTO addressCleanseAlertResponse(AccountLoginDTO accountLoginDto, String response)throws EtccException {
	   try{
		   return stub().addressCleanseAlertResponse(accountLoginDto,response);
	   } catch(EtccSecurityException se){
		   throw new EtccException("Account Alerts Security Exception");
	   } catch (RuntimeException e) {
		   logger.error("Runtime exception getting Address Cleanse Alert Response: " + e.getMessage(), e);
		   throw e;
	   }
    }
}
