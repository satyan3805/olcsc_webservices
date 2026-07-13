/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.delegate.LovDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPreferencesDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.LovDTO;
import com.etcc.csc.dto.TransactionRecordsDTO;
import com.etcc.csc.dto.bean.OlcAccountHistoryRecBean;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.util.UIDateUtil;

public class AccountTransactionsAction extends BaseReportAction {
    private static final Logger logger = Logger.getLogger(AccountTransactionsAction.class);

    private static final String ERROR_PROPERTY = "viewTransactonErrors";

    public static final String TRANSACTIONS_TRANS_REC =
        "TRANSACTIONS_TRANS_REC";

    public static final String TRANSACTIONS_PARKING_REC =
        "TRANSACTIONS_PARKING_REC";

    public static final String NICK_NAMES = "nickNames";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        //         SessionUtil.getAcctDTO(request);
        DynaValidatorForm dynaForm = (DynaValidatorForm)form;

        AccountLoginDTO accountLogin =
            SessionUtil.getSessionAccountLogin(request.getSession());
        if (accountLogin == null)
            throw new EtccSecurityException("session timed out in AccountTransactionsAction");

        //         if (StringUtil.stringToBoolean(request.getParameter(AccountPreferencesDTO.HOUSTON_AIRPORT_SYSTEM)))
        //         {
        //            //TODO need to check whether the payment type is credit card, if not need to direct to an error page
        //            ;
        //         }

        AccountHistoryDelegate ahDelegate = new AccountHistoryDelegate();
        request.setAttribute("siteCat", Boolean.FALSE);
//        UtilDelegate utilDelegate = new UtilDelegate();
        AccountTransactionDTO acDto = ahDelegate.viewTransactionMain(accountLogin, null, null, null);

        request.setAttribute(NICK_NAMES, acDto.getVehicleNickNames());
	// QC_11613 dateType field added start
        String dateType=dynaForm.getString("dateType");
        if(dateType != null && !dateType.isEmpty()){
        	request.setAttribute("dateType", dateType);
        }
        // QC_11613 dateType field added end 
        String startString = dynaForm.getString("startDate");
        Calendar startCal = Calendar.getInstance();
        if (startString != null)
            startCal.setTime(UIDateUtil.parseDate(startString));


        String endString = dynaForm.getString("endDate");
        Calendar endCal = Calendar.getInstance();
        if (endString != null)
            endCal.setTime(UIDateUtil.parseDate(endString));

        String transactionType = dynaForm.getString("transactionType");

        String nickNameValue = dynaForm.getString("nickName");
        String nickName = null;
        String nickNameType = null;
        if (!"all".equalsIgnoreCase(nickNameValue)) {
        	if (!StringUtil.isEmpty(nickNameValue) && nickNameValue.contains(":::")){
		            String[] values = nickNameValue.split(":::");
		            if (values != null && values.length == 2) {
		                nickName = values[0];
		                nickNameType = values[1];
		            }
        	}
        }

        //logger.debug("startString : "+startString);
        //logger.debug("endString : "+endString);
       // logger.debug("transactionType : "+transactionType);
       // logger.debug("nickNameValue : "+nickNameValue);
        Collection<LovDTO> transType = new LovDelegate().getLov(LovDelegate.MSTAT_SORT_ACCT_SUM_BY);
       // logger.debug("size of array" + transTypeCol.size());
        logger.info("The size of the Transactions: " + transType.size());
        request.setAttribute("transTypeList", transType);
		// QC_11613 dateType field added
        TransactionRecordsDTO transactionsDTO =
            ahDelegate.getTransactionRecords(accountLogin, startCal, endCal,
                                             null, null, transactionType,
                                             nickName, nickNameType,dateType);

        if (saveErrorMessages(request, transactionsDTO, ERROR_PROPERTY)) {
            request.setAttribute("itemsFound", null);
        }
        //         else if (ahRecBeans == null || ahRecBeans.length==0)
        //         {
        //             logger.info("no transactions from " + startString + " to "+ endString);
        //             request.setAttribute("itemsFound", "0");
        //         }
        //         else
        //             request.setAttribute("itemsFound", ahRecBeans[ahRecBeans.length-1].getSerialNum());


        SessionUtil.setTertiaryMenusInRequest(request);

        OlcAccountHistoryRecBean[] ahRecBeans = null;
        OlcAccountHistoryRecBean[] parkingRecBeans = null;

        if (StringUtil.stringToBoolean(request.getParameter(AccountPreferencesDTO.HOUSTON_AIRPORT_SYSTEM))) {
            parkingRecBeans = transactionsDTO.getParkingRecords();
            request.setAttribute(AccountPreferencesDTO.HOUSTON_AIRPORT_SYSTEM, Boolean.TRUE);
        } else {
            ahRecBeans = transactionsDTO.getRecords();
            request.setAttribute(AccountPreferencesDTO.HOUSTON_AIRPORT_SYSTEM, Boolean.FALSE);
        }

        request.setAttribute(TRANSACTIONS_TRANS_REC, ahRecBeans);
        request.setAttribute(TRANSACTIONS_PARKING_REC, parkingRecBeans);
        if (ahRecBeans != null) {
            if (ahRecBeans.length > 0) {
                request.setAttribute("itemsFound", ahRecBeans[ahRecBeans.length-1].getSerialNum());
                request.setAttribute("totalAmt", Double.valueOf(transactionsDTO.getTotalAmt()));
            }
        }

        if (parkingRecBeans != null)
            request.setAttribute("itemsFoundParking",
                                 parkingRecBeans[parkingRecBeans.length - 1].getSerialNum());
        if (!StringUtil.isEmpty(nickNameValue)){
        	String[] values = nickNameValue.split(":::");
            if (values != null && values.length == 2) {
            	nickNameValue = values[0];
            }
        }

        String dwnDateType;
        if(dateType.equalsIgnoreCase("TRANSACTION")){
        	dwnDateType="T";
        }else{
        	dwnDateType="P";
        }
        StringBuilder args=new StringBuilder("p_acct_id="+accountLogin.getAcctId()+"&p_date_from="+startString+"&p_date_to="+endString+"&p_vehicle_filter="+nickNameValue+"&p_trans_type_filter="+transactionType.replaceAll("\\s", "%20")+"&p_date_type="+dwnDateType);
        //2309
        AccountDTO acctDTO = SessionUtil.getAcctDTO(request);
        args.append("&p_css_name="+acctDTO.getFirstName() + ", " + acctDTO.getLastName());
        if (StringUtil.stringToBoolean(request.getParameter("preview"))) {
            request.setAttribute("pageSize", Integer.valueOf(0));
            request.setAttribute("preview", Boolean.TRUE);
            return mapping.findForward("preview");
        } else {

            String result = processReport(request, response,
                    ReportInterface.ReportType.TRANS_REP,
                    "TransactionsReport",
                    null,
                    args.toString(),
                    ERROR_PROPERTY);
            if (result != null)
                return mapping.findForward(result);
            request.setAttribute("pageSize", Integer.valueOf(10));
            request.setAttribute("preview", Boolean.FALSE);
            request.setAttribute("pdf", Boolean.FALSE);
            request.setAttribute("excel", Boolean.FALSE);
            if (Boolean.parseBoolean(request.getParameter("siteCat")))
                request.setAttribute("siteCat", Boolean.TRUE);
            return mapping.findForward("success");
        }
    }
}
