/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.payment;

import com.etcc.csc.dto.Invoice;
import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.presentation.datatype.PaymentContext;
import com.etcc.csc.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class AcceptVeaAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		DynaValidatorForm dynaForm = (DynaValidatorForm) form;
		PaymentContext paymentContext = SessionUtil.getPaymentContext(session);
		String target = "success";

		AppDelegate appDel = new AppDelegate();

		Object[] licPlates = paymentContext.getVeaLicPlates();
		int index = ((Integer) dynaForm.get("index")).intValue();
		if (paymentContext.isVeaAccepted() && licPlates != null) {
			if (index >= licPlates.length) {
				target = "next";
			} else {
				Object vea = licPlates[index++];
				BeanUtils.copyProperties(dynaForm, vea);
				dynaForm.set("index", Integer.valueOf(index));

				/*
				 * String veaText = appDel.getVeaText2(SessionUtil
				 * .getSessionAccountLogin(session),
				 * paymentContext.getInvoices());
				 */
				String veaText = appDel.getVeaText(
						SessionUtil.getSessionAccountLogin(session),
						filterInvoices(paymentContext.getInvoices(), 
								dynaForm.getString("licPlateNumber"), 
								dynaForm.getString("licPlateState"))
				);
				request.setAttribute("vea", veaText);
			}
		} else {
			target = "next";
		}
		return mapping.findForward(target);
	}

	private Invoice[] filterInvoices(Invoice[] invoices, String licPlateNbr, String licPlateState) {
		Invoice[] result = null;
		if (invoices != null) {
			List<Invoice> temp = new ArrayList<Invoice>();
			for (Invoice invoice : invoices) {
                if (invoice.getLicPlateNumber().equals(licPlateNbr)
                        && invoice.getLicPlateState().equals(licPlateState)) {
                    temp.add(invoice);
                }
                
            }
			if (temp.size() > 0) {
				result = temp.toArray(new Invoice[] {});
			}
		}
		return result;
	}
}
