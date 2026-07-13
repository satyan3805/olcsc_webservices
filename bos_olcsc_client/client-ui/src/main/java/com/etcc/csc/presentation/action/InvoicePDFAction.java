/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccReportException;
import com.etcc.csc.common.FileFormat;
import com.etcc.csc.delegate.ViolationDelegate;
import com.etcc.csc.dto.DocumentDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.InvoiceDTO;
import com.etcc.csc.dto.ViolatorDTO;
import com.etcc.csc.presentation.form.SelectedInvoiceForm;

/**
 * Gets the Invoice PDF from the Backend and displays to the user.
 */
@Deprecated
public class InvoicePDFAction extends Action {
    private static final Logger logger = Logger.getLogger(InvoicePDFAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String invNum = request.getParameter("invoice_number");

            SelectedInvoiceForm selectedInvoiceForm =
                (SelectedInvoiceForm) request.getSession().getAttribute("selectedInvoiceForm");
            ViolatorDTO violatorDTO = selectedInvoiceForm.getViolatorDTO();

            ViolationDelegate delegate = new ViolationDelegate();
            if (logger.isDebugEnabled()) {
                String requestParameters = request.getQueryString();
                logger.debug("InvoicePDFAction.requestParameters= " + requestParameters);
                logger.debug("InvoicePDFAction.invCode= " + invNum);
            }
//            InvoiceDTO invoiceById = violatorDTO.getInvoiceById(invNum);
//            DocumentDTO invoicePDF = delegate.getInvoiceDoc(violatorDTO, invoiceById);

//            if (!invoicePDF.hasErrors()) {
//                response.setContentType(FileFormat.CONTENT_TYPE_PDF);
//                response.getOutputStream().write(invoicePDF.getDocument());
//            } else {
//                response.setContentType(FileFormat.CONTENT_TYPE_HTML);
//                StringBuilder error = new StringBuilder();
//                // get the last error and write it to the response object
//                for (ErrorMessageDTO e : invoicePDF.getErrors()) {
//                    error.append(e.getMessage()).append("<br />");
//                }
//                logger.info("InvoicePDFAction.error=" + error);
//                response.getWriter().write(error.toString());
//            }
        } catch (Exception e) {
            logger.info("InvoicePDFAction error writing output streams: " + e.getMessage(), e);
            throw new EtccReportException(e);
        }
        return null;
    }
}
