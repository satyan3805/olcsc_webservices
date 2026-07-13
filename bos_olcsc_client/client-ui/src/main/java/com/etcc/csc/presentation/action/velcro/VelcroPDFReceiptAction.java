package com.etcc.csc.presentation.action.velcro;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.delegate.VelcroDelegate;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.SessionUtil;

import java.net.URLEncoder;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

public class VelcroPDFReceiptAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {

    VelcroDelegate del = new VelcroDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    if (acctLoginDto != null) {
        String url = del.getVelcroReceiptPDF(acctLoginDto);
        
        System.out.println("***url: " + url);
        
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("&p_acct_id=");
        sb.append(acctLoginDto.getAcctId());
        sb.append("&p_doc_type=");
        sb.append(AccountLoginDTO.LoginType.AC);
        sb.append("&p_session_id=");
        sb.append(acctLoginDto.getDbSessionId());
        sb.append("&p_ip_address=");
        sb.append(acctLoginDto.getLastLoginIp());
        sb.append("&p_user_id=");
        String loginId = acctLoginDto.getLoginId();
        loginId = URLEncoder.encode(loginId, "UTF-8");
        sb.append(loginId);
        
        request.setAttribute(ReportParamBean.KEY, sb.toString());
    } else {
        throw new EtccSecurityException("Security violation in "
            + "VelcroPDFReceiptAction");
    }
    return mapping.findForward("success");
    }
}
