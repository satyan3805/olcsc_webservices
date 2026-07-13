/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.MonthlyInvoicesDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.dto.InvoiceDatesDTO;
import com.etcc.csc.service.ReportInterface;
import com.etcc.csc.util.SessionUtil;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.dto.bean.OlcCorpInvRecP;


public class AccountMonthlyInvoicesAction extends BaseReportAction {
    private static final Logger logger = Logger.getLogger(AccountMonthlyInvoicesAction.class);
    private static final String ERROR_PROPERTY = "viewMonthlyInvoiceErrors";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
//        SessionUtil.getAcctDTO(request);
        HttpSession session = request.getSession();
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
        SessionUtil.setTertiaryMenusInRequest(request);
        String invoiceId;

        invoiceId = request.getParameter("p_invoice_id");

        if (StringUtil.stringToBoolean(request.getParameter("pdf"))) {

        	 invoiceId = request.getParameter("p_invoice_id");
        }
        else if (StringUtil.stringToBoolean(request.getParameter("excel")))
        {
        	  invoiceId = request.getParameter("p_invoice_id");

        }
        else
        {
        	invoiceId = "021200103009";
        }


              String args = "p_invoice_id=" + invoiceId.substring(6, invoiceId.length());
              logger.info("Value of args " +args);

              String result = processReport(request, response,
                ReportInterface.ReportType.MNTH_REP,
                "MonthlyInvoicesReport",
                new Date(),
                args,
                ERROR_PROPERTY);
				logger.info("Value of result " + result);


        if (result != null)
            return mapping.findForward(result);

        //AccountHistoryDelegate delegate = new AccountHistoryDelegate();

        String statementMonth = request.getParameter("statementMonth");

        logger.info("Value of statementMonth" + statementMonth);

        Collection<LabelValueBean> monthList = getMonthList(session);

        logger.info("Value of monthList" + monthList);

        request.setAttribute("monthList", monthList);

        if (statementMonth == null) {
            Iterator<LabelValueBean> itr = monthList.iterator();
            if (itr.hasNext()) {
                statementMonth = itr.next().getValue();
            }
        }

        if (statementMonth != null) {

        	logger.info("Value of statement month" + statementMonth);
        	MonthlyInvoicesDTO monthlyInvoices =
                getMonthlyInvoicesDTO(statementMonth, session);

        	if (saveErrorMessages(request, monthlyInvoices, ERROR_PROPERTY)) {
                request.setAttribute("itemsFound", null); }

            if (monthlyInvoices.getInvoiceDetails() == null)
                logger.info("***monthlyInvoices.invoiceDetails is null");
            monthlyInvoices.setStatementMonth(statementMonth);
            monthlyInvoices.setAcctId(accountLogin.getAcctId());
            request.setAttribute("monthlyInvoices", monthlyInvoices);
            return mapping.findForward("success");
        }
        return mapping.findForward("empty");
    }

    private MonthlyInvoicesDTO getMonthlyInvoicesDTO(String statementMonth, HttpSession session) throws EtccException, EtccSecurityException {
        AccountLoginDTO accountLogin =
            SessionUtil.getSessionAccountLogin(session);
        AccountHistoryDelegate acctHistoryDelegate =
            new AccountHistoryDelegate();
        MonthlyInvoicesDTO dto =
            acctHistoryDelegate.getMonthlyInvoices(statementMonth, accountLogin);


        return dto;
    }

    /**
     * TODO: acctHistoryDelegate.getStatementDates returns a collection of strings
     * -- NOT a collection of OLC_CORP_INV_REC_P!!!!
     * @param session the HTTP servlet request object
     * @return collection of label-value beans representing the list of months
     * @throws EtccSecurityException
     * @throws EtccException
     * @see AcctHistoryDelegate#getStatementDates
     */
    private Collection<LabelValueBean> getMonthList(HttpSession session) throws EtccSecurityException, EtccException {
//        try {
            InvoiceDatesDTO datesDTO;
            AccountHistoryDelegate acctHistoryDelegate =
                new AccountHistoryDelegate();

            AccountLoginDTO accountLogin =
                SessionUtil.getSessionAccountLogin(session);
            if (accountLogin == null)
                throw new EtccSecurityException();
            // TODO: verify that second parameter should be false
            // TODO: acctHistoryDelegate.getStatementDates returns a collection of strings -- NOT OLC_CORP_INV_REC_P!!!!
            datesDTO = acctHistoryDelegate.getInvoiceDates(accountLogin);

            logger.info("Value of datesDTO" + datesDTO);

            Collection<LabelValueBean> dateComboList = new ArrayList<LabelValueBean>();

            //BUG: The DAO & DTO needs to be reconciled with this.
            logger.warn("Data Type Mismatch between DAO, DTO & UI");

            OlcCorpInvRecP[] dates = null;

            if (datesDTO != null)
                dates = datesDTO.getInvoiceDates();

             if (dates != null && dates.length  > 0) {

            	 for (OlcCorpInvRecP dateString : dates) {

            		 String label = dateString.getStatementPeriod();
            		 String value = dateString.getInvoicePeriod();

            		 dateComboList.add(new LabelValueBean(label, value));
            	 }


             }
			   return dateComboList;
//        } catch (SQLException e) {
//            throw new EtccException(e);
//        }
    }

    /*
     private MonthlyInvoicesDTO getMonthlyInvoicesDTO() {
        MonthlyInvoicesDTO dto = new MonthlyInvoicesDTO();
        try {
             dto.setAcctId(1000);
             dto.setFirstName("first");
             dto.setLastName("last");
             dto.setAddress1("address1");
             dto.setAddress2("address2");
             dto.setCity("city");
             dto.setState("state");
             dto.setZip("zip");
             dto.setCountry("USA");

             dto.setInvoiceSubTotal(-25);

             dto.setInvSumBeginningBalance(100);
             dto.setInvSumToll(-10);
             dto.setInvSumTollQty(2);
             dto.setInvSumInvoiceFee(-20);
             dto.setInvSumInvoiceFeeQty(1);
             dto.setInvSumTagRentalFee(-30);
             dto.setInvSumTagRentalFeeQty(1);
             dto.setInvSumEndingBalance(40);
             dto.setBalSumBalanceRequirement(500);
             dto.setBalSumCurrentBalance(50);
             dto.setBalSumReplenishmentAmt(450);
             dto.setInvoiceDetails(getOlcAccountHistoryRecBeans());
             dto.setOutstandingInvoices(getOlcAcctSummaryRecBeans());
        } catch (Exception e) {
             e.printStackTrace();
        }
         return dto;
     }

     private OlcAccountHistoryRecBean[] getOlcAccountHistoryRecBeans() {
         OlcAccountHistoryRecBean[] beans = new OlcAccountHistoryRecBean[2];

         OlcAccountHistoryRecBean bean = new OlcAccountHistoryRecBean();
         bean.setTrxnDate(new GregorianCalendar());
         bean.setLocationName("location 1");
         bean.setPostedDate(new GregorianCalendar());
         bean.setVehicleNickName("vehicle 1");
         bean.setTransType("Toll");
         bean.setAmount(new BigDecimal(-0.60));
         beans[0] = bean;

         bean = new OlcAccountHistoryRecBean();
         bean.setTrxnDate(new GregorianCalendar());
         bean.setLocationName("location 2");
         bean.setPostedDate(new GregorianCalendar());
         bean.setVehicleNickName("vehicle 2");
         bean.setTransType("Tag rental");
         bean.setAmount(new BigDecimal(-8.00));
         beans[1] = bean;

         return beans;
     }

     private OlcAcctSummaryRecBean[] getOlcAcctSummaryRecBeans() throws SQLException {
         OlcAcctSummaryRecBean[] beans = new OlcAcctSummaryRecBean[2];

         OlcAcctSummaryRec rec = new OlcAcctSummaryRec();
         rec.setAmount(new BigDecimal(10));
         rec.setDescription("January balance");
         rec.setQuantity(new BigDecimal(1));
         OlcAcctSummaryRecBean bean = new OlcAcctSummaryRecBean(rec);
         beans[0] = bean;

         rec = new OlcAcctSummaryRec();
         rec.setAmount(new BigDecimal(20));
         rec.setDescription("February balance");
         rec.setQuantity(new BigDecimal(1));
         bean = new OlcAcctSummaryRecBean(rec);
         beans[1] = bean;

         return beans;
     }
     */
}
