/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.report.ReportParamBean;
import com.etcc.csc.util.HttpResponseUtil;

import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProcessReportAction extends Action {
    private static final Logger logger = Logger.getLogger(ProcessReportAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws Exception {
        try {
            String url = ( String ) request.getAttribute(ReportParamBean.KEY);
            logger.info("url in ProcessReportAction: " + url);
            String format = request.getParameter("format");

            if ("html".equalsIgnoreCase(format)) {
                response.setContentType(FileFormat.CONTENT_TYPE_HTML);
                String htmlReport = 
                    HttpResponseUtil.parseISToString(new URL(url).openConnection().getInputStream());
                request.setAttribute("htmlReport", htmlReport);
                return mapping.findForward("htmlReport");
            }

            // TODO: BUG: It seems this action only supports HTML reports as returning "null" will cause a struts error.
            FileFormat expectedContentType;
            if ("xls".equalsIgnoreCase(format))
              expectedContentType = FileFormat.XLS;
            else
              expectedContentType = FileFormat.PDF;

            URLConnection urlConnection = new URL(url).openConnection( );
            if ( !HttpResponseUtil.verifyContentType( urlConnection, expectedContentType ) ) {
              throw new EtccReportException( "Wrong content type" ); 
            }

            response.setContentType( expectedContentType.getContentType() );
            HttpResponseUtil.copyStream(urlConnection.getInputStream(), response.getOutputStream());
        }
        catch (Throwable t) {
            throw new EtccReportException(t);
        }
        return null;
    }
}
