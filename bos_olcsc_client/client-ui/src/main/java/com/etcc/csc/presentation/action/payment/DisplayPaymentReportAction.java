/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@Deprecated
public class DisplayPaymentReportAction extends Action {

    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        /*
        String paymentId = SessionUtil.getPaymentContext( request.getSession() ).getPaymentId();
        String format = request.getParameter("format");
        AccountLoginDTO accountLogin = 
            SessionUtil.getSessionAccountLogin(request.getSession());

        String expectedContentType = processContentType(format);
        URLConnection connection = new URL(processReportUrl(paymentId, format, accountLogin)).openConnection();
        if ( !HttpResponseUtil.verifyContentType( connection, expectedContentType ) ) 
        {
          throw new EtccReportException( "Wrong content type" );    
        }
        response.setContentType(expectedContentType );
        HttpResponseUtil.copyStream(connection.getInputStream(), 
                                    response.getOutputStream());
        */
        return null;
    }

/*
    private String processReportUrl(String paymentId, String format, 
                                    AccountLoginDTO accountLogin) throws Exception {
        String docType = accountLogin.getLoginType();
        if (StringUtils.isEmpty(docType)) {
            docType = "AC";
        }

        long docId = accountLogin.getAcctId();
        if ("IN".equals(docType) || "CA".equals(docType)) {
            docId = accountLogin.getInvoiceId();
        }

        String reportUrl = 
            new Payment().getPaymentReceipt(new BigDecimal(docId), 
                                                                docType, 
                                                                accountLogin.getDbSessionId(), 
                                                                accountLogin.getLastLoginIp(), 
                                                                accountLogin.getLoginId(), 
                                                                new BigDecimal(paymentId), 
                                                                format);

        StringBuilder fullUrl = new StringBuilder(reportUrl);
        fullUrl.append("&p_doc_id=");
        fullUrl.append(docId);
        fullUrl.append("&p_doc_type=");
        fullUrl.append(docType);
        fullUrl.append("&p_ip_address=");
        fullUrl.append(accountLogin.getLastLoginIp());
        fullUrl.append("&p_payment_id=");
        fullUrl.append( paymentId );
        fullUrl.append("&p_session_id=");
        fullUrl.append(accountLogin.getDbSessionId());
        fullUrl.append("&p_user_id=");
        String loginId = accountLogin.getLoginId();
        loginId = URLEncoder.encode(loginId, "UTF-8");
        fullUrl.append(loginId);
        
        return fullUrl.toString();
    }


    private String processContentType(String format ) 
    {
        String contentType = "";
        if ("PDF".equals(format)) {
          contentType = HttpResponseUtil.CONTENT_TYPE_PDF;
        } else if ("HTML".equals(format)) {
          contentType = "text/html";
        }
        return contentType;
    }
    */
}
