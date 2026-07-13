package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountYearlySummaryDTO;
import com.etcc.csc.dto.SummaryAvailableDTO;
import com.etcc.csc.util.UIDateUtil;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ViewSummaryAction extends Action {
    private static final Logger logger = Logger.getLogger(ViewSummaryAction.class);
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        
         HttpSession session = request.getSession();
         AccountHistoryDelegate acctHistoryDelegate = new AccountHistoryDelegate();
         SummaryAvailableDTO summaryAvailDTO;
         AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
         if (accountLogin==null)
            throw new EtccSecurityException();

         summaryAvailDTO = acctHistoryDelegate.hasLastYearSummary(accountLogin);

         if (summaryAvailDTO == null) {
             return mapping.findForward("failure");
         }
         
         if(!summaryAvailDTO.isSummaryAvail()) {
             return mapping.findForward("notAvailable");
         }
         
        DynaValidatorForm summaryForm = (DynaValidatorForm) form;
        String reportFormat = ActionFormUtil.getString(summaryForm, "reportFormat");
         
         if (!reportFormat.equalsIgnoreCase("html")) {
             String url = getPdfFileUrl(request);
             if (url==null)
                 return mapping.findForward("failure");
                 
             url = url + buildParameterString(request, UIDateUtil.getJanOfLastYear());
             request.setAttribute( ReportParamBean.KEY, url);
             return mapping.findForward( "pdfResult" );       
         }
         
         System.out.println("Janurary of Last year: " + UIDateUtil.getJanOfLastYear() );
         AccountYearlySummaryDTO summaryDTO = acctHistoryDelegate.getSummaryHTML(accountLogin,UIDateUtil.getJanOfLastYear());
         if (summaryDTO==null ||
            ((summaryDTO.getAcctSummary()==null || summaryDTO.getAcctSummary().length==0) && 
            ((summaryDTO.getTagSummary()==null || summaryDTO.getTagSummary().length==0))))
            return mapping.findForward("failure");

         request.setAttribute("valid", Boolean.valueOf(summaryAvailDTO.isSummaryAvail()));
         request.setAttribute("accountSummary", summaryDTO);
         request.setAttribute("lastYear", Integer.valueOf(UIDateUtil.getLastYear()));
         return mapping.findForward("htmlResult");
    }
    
    private String getPdfFileUrl(HttpServletRequest request) throws Exception
    {
        AccountHistoryDelegate delegate = new AccountHistoryDelegate();
        String reportFileUrl = 
            delegate.getSummaryPDF(SessionUtil.getSessionAccountLogin(request.getSession()), null);
            
        logger.info("Summary reportFileUrl:"+reportFileUrl);
        
        return reportFileUrl;
    }
    
    private String buildParameterString(HttpServletRequest request, String statementMonth) {
    	AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
    	StringBuilder sb = new StringBuilder();
    	sb.append("&p_acct_no=");
    	sb.append(loginDTO.getAcctId());
    	sb.append("&p_posted_date=");
    	sb.append(convertMonthString(statementMonth));
    	sb.append("&p_report_type=");
    	sb.append("YEARLY");
    	sb.append("&p_session_id=");
    	sb.append(loginDTO.getDbSessionId());
    	sb.append("&p_user_id=");
    	sb.append(loginDTO.getLoginId());
    	sb.append("&p_ip_address=");
    	sb.append(loginDTO.getLastLoginIp());
    	return sb.toString();
    }
    
    private String convertMonthString(String monthString) {
        int pos = monthString.indexOf(", ");
        String result = monthString;
        if (pos !=-1)
            result = monthString.subSequence(0, 3)+","+ monthString.substring(pos+2);
        return result;
    }
}
