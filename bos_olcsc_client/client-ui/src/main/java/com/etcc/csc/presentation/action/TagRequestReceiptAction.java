/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.SessionUtil;

public class TagRequestReceiptAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response)
      throws Exception
    {
        TagDelegate delegate = new TagDelegate();
        AccountLoginDTO accountLogin = SessionUtil.getSessionAccountLogin(request.getSession());
        if (accountLogin == null)
            throw new EtccSecurityException();
        String format = request.getParameter("format");
        if (format == null)
            format = "PDF";

        String reportUrl = delegate.addTagsReceipt(accountLogin, format.toUpperCase());
        if (reportUrl == null)
            return mapping.findForward("failure");
        else
        {
            long transctionId = ((TagRequestForm)form).getRetailTransId();
            reportUrl = reportUrl.trim() + "&P_RTL_TRANS=" + transctionId;  
            reportUrl = buildReportParameter(reportUrl, accountLogin,request,format);
            request.setAttribute( ReportParamBean.KEY, reportUrl);
            return mapping.findForward("success");
        }
    }
    
    private String buildReportParameter(String url, AccountLoginDTO loginDTO,HttpServletRequest request, String format) throws Exception{
        StringBuilder buffer = new StringBuilder(url);
        buffer.append("&p_doc_id=");
            buffer.append(loginDTO.getAcctId());
        buffer.append("&p_session_id=");
            buffer.append(loginDTO.getDbSessionId());
        buffer.append("&p_user_id=");
            String loginId = loginDTO.getLoginId();
            loginId = URLEncoder.encode(loginId, "UTF-8");
            buffer.append(loginId);
        buffer.append("&p_ip_address=");
            buffer.append(loginDTO.getLastLoginIp());
        buffer.append("&p_doc_type=");
            buffer.append(AccountLoginDTO.LoginType.AC);
//        if ("html".equalsIgnoreCase(format))
//        {
//            buffer.append("&p_file_loc=");
//                buffer.append(buildLogoPath(request));
//        }
        return buffer.toString();
    }
}
