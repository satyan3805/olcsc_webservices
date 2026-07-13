package com.etcc.csc.presentation.action;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountStatementDTO;
import com.etcc.csc.dto.StatementDatesDTO;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.ActionFormUtil;
import com.etcc.csc.util.SessionUtil;

public class ViewStatementAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(ViewStatementAction.class);
//    private static final String currentPageKey= "currentPage";
    private static final String ACCOUNT_STATEMENT_KEY = "accountStatement";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception{

        boolean getMonthsSuccess = false;
        getMonthsSuccess = getStatementMonths(request,form);
        if (!getMonthsSuccess)
            return mapping.findForward("failure");
            
        String VPNType = getVPNType(request);
        
        DynaValidatorForm statementForm = (DynaValidatorForm) form;
//        String currentPage = ActionFormUtil.getString(statementForm, currentPageKey);
        String reportFormat = ActionFormUtil.getString(statementForm, "reportFormat");
        String statementMonth = ActionFormUtil.getString(statementForm, "statementMonth");
        System.out.println("statementMonth:" + statementMonth);
        
        if (!reportFormat.equalsIgnoreCase("html"))
        {
            String url = getPdfFileUrl(request, VPNType);
            if (url==null)
                return mapping.findForward("failure");
                
            url = url + buildParameterString(request, statementMonth);
            request.setAttribute( ReportParamBean.KEY, url);
            return mapping.findForward( "pdfResult" );   
        }
                
        if (statementMonth == null || statementMonth.equals(""))
            statementMonth = getFirstMonthInList(request);
            
        retrieveStatement(statementMonth, request, VPNType);

        return mapping.findForward("htmlResult");
    }

    private boolean getStatementMonths(HttpServletRequest request, ActionForm form) throws Exception {
    
        HttpSession session = request.getSession();
//        if (session.getAttribute(Constants.SESSION_MONTH_LIST)!=null)
//            return true;
        
        AccountHistoryDelegate acctHistoryDelegate = new AccountHistoryDelegate();
        StatementDatesDTO datesDTO;
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(session);
        if (accountLogin == null)
            throw new EtccSecurityException();
        datesDTO = acctHistoryDelegate.getStatementDates(accountLogin, true);
        DynaValidatorForm statementForm = (DynaValidatorForm) form;
        Collection<LabelValueBean> dateComboList;
        dateComboList = new ArrayList<LabelValueBean>();
        
        String[] dates = datesDTO.getDates();
        
        if (dates!=null && dates.length>0)
        {
            boolean setDefaultMonth = false;
            for (String dateString : dates) {
                if (!setDefaultMonth && statementForm.getString("statementMonth")==null) {
                    statementForm.set("statementMonth", dateString);
                    setDefaultMonth = true;
                }
                dateComboList.add(new LabelValueBean(dateString, dateString) );
            }
        }
        else
        {
            logger.info("No month returned from api");
            return false;
        }
            
        request.setAttribute("monthList", dateComboList);  
        return true;
    }

    private String getFirstMonthInList(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Collection<LabelValueBean> monthList =
        	(Collection<LabelValueBean>)request.getAttribute("monthList");
        for (LabelValueBean valueBean : monthList)
            return valueBean.getValue();
        return null;
    }
    
    private void retrieveStatement(String statementMonth, HttpServletRequest request, String VPNType) throws Exception{
        AccountHistoryDelegate delegate = new AccountHistoryDelegate();
        AccountStatementDTO statementDTO = delegate.getStatementHTML(SessionUtil.getSessionAccountLogin(request.getSession()), statementMonth, VPNType);

        if (statementDTO != null) {
            if (saveErrorMessages(request, statementDTO, "viewStatementErrors")){
        		request.setAttribute("itemsFound", null); 
        	} else if (statementDTO.getRecords() == null || statementDTO.getRecords().length == 0) 
        		request.setAttribute("itemsFound", "0");
        	else
        		request.setAttribute("itemsFound", (statementDTO.getRecords())[statementDTO.getRecords().length-1].getSerialNumber());
        } else
            request.setAttribute("itemsFound", "0");

        request.setAttribute(ACCOUNT_STATEMENT_KEY, statementDTO);
        request.setAttribute("statementMonth", statementMonth);
    }

    private String getPdfFileUrl(HttpServletRequest request, String VPNType) throws Exception {
        AccountHistoryDelegate delegate = new AccountHistoryDelegate();
        String reportFileUrl = 
            delegate.getStatementPDF(SessionUtil.getSessionAccountLogin(request.getSession()), VPNType);
        logger.info("reportFileUrl:"+reportFileUrl);
        return reportFileUrl;
    }

    private String buildParameterString(HttpServletRequest request, String statementMonth) throws Exception {
        AccountLoginDTO loginDTO = SessionUtil.getSessionAccountLogin(request.getSession());
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("&p_acct_no=");
            buffer.append(loginDTO.getAcctId());
        buffer.append("&p_posted_date=");
            buffer.append(convertMonthString(statementMonth));
        buffer.append("&p_report_type=");
            buffer.append("MONTHLY");
        buffer.append("&p_session_id=");
            buffer.append(loginDTO.getDbSessionId());
        buffer.append("&p_user_id=");
            String loginId = loginDTO.getLoginId();
            loginId = URLEncoder.encode(loginId, "UTF-8");
            buffer.append(loginId);

        buffer.append("&p_ip_address=");
            buffer.append(loginDTO.getLastLoginIp());
        
        return buffer.toString();
    }
    
    private String convertMonthString(String monthString) {
        int pos = monthString.indexOf(", ");
        String result = monthString;
        if (pos !=-1)
            result = monthString.subSequence(0, pos+1)+ monthString.substring(pos+2);
        return result;
        
    }
    
    private String getVPNType(HttpServletRequest request) throws Exception{
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
        AccountDTO acctDto = null;
        if (acctLoginDto != null) {
            acctDto = SessionUtil.getAcctDTO(request);
            if (acctDto!=null)
            {
                String VPNType = acctDto.getVpnType();
                request.setAttribute("vpnType", VPNType);
                logger.info("vpnType in ViewStatementAction: "+ VPNType);
                return VPNType;
            }
        }
        else {
            throw new EtccSecurityException();
        }
        return null;
    }
    
}
