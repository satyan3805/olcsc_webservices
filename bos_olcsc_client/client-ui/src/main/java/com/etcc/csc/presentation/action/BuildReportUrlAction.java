/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountHistoryDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.SessionUtil;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BuildReportUrlAction extends Action{
//    private static final Logger logger = Logger.getLogger(BuildReportUrlAction.class);
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        String requestParameters = request.getQueryString();
        AccountHistoryDelegate delegate = new AccountHistoryDelegate();
        String url = delegate.getReportServletUrl("ParkingReceipt");
//        url = "http://nttacsc5:7779/reports/rwservlet?dwd4&desformat=PDF&report=C:\\OLCSC\\Dev\\reports\\ParkingReceipt.rdf";
        url = url + requestParameters;
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        if (accountLogin == null)
            throw new EtccSecurityException();
        url = buildParameterString(url,accountLogin );
        request.setAttribute(ReportParamBean.KEY, url);
        return mapping.findForward("success");
    }
    
    private String buildParameterString(String url,AccountLoginDTO loginDTO) throws Exception{
        StringBuilder buffer = new StringBuilder(url);
        buffer.append("&p_acct_no=");
            buffer.append(loginDTO.getAcctId());
        buffer.append("&p_session_id=");
            buffer.append(loginDTO.getDbSessionId());
        buffer.append("&p_user=");
            String loginId = loginDTO.getLoginId();
            loginId = URLEncoder.encode(loginId, "UTF-8");
            buffer.append(loginId);
        buffer.append("&p_ip_address=");
            buffer.append(loginDTO.getLastLoginIp());
        return buffer.toString();
    }      
}
