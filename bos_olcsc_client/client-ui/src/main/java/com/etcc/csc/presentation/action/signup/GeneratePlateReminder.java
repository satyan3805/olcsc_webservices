/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action.signup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountUtilDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.presentation.action.CSCBaseAction;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

public class GeneratePlateReminder extends CSCBaseAction {

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) 
	throws Exception {

		HttpSession session = request.getSession();
        AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);

        TagRequestForm tagRequestForm = (TagRequestForm)session.getAttribute("tagRequestFormEmail");
		if (tagRequestForm == null) {
		    tagRequestForm = (TagRequestForm)form;
		} else {
		    SessionUtil.setVehicles(request, tagRequestForm.getSavedVehicles());
			resetToken(request);
			saveToken(request);
			tagRequestForm.clearForm();
			request.setAttribute("vehFlag", Boolean.FALSE);
			request.setAttribute("tagSalesAmt", Double.valueOf(tagRequestForm.getTagSaleAmount()));
			request.setAttribute("depositAmt", Double.valueOf(tagRequestForm.getDepositAmount()));
			request.setAttribute("totalAmt", Double.valueOf(tagRequestForm.getTotalAmount()));
			request.setAttribute("retailTransId", Long.valueOf(tagRequestForm.getRetailTransId()));
		}

		//            com.etcc.csc.accountutil.dto.AccountLoginDTO newLogin = 
		//                new com.etcc.csc.accountutil.dto.AccountLoginDTO();
		//               DtoUtil.copySimpleProperties(newLogin,acctLoginDto); 
		AccountUtilDelegate accountUtilDelegate = new AccountUtilDelegate();
		String licPlate=request.getParameter("p_lic_plate");
		String page=request.getParameter("page");
		ResultDTO baseDto = accountUtilDelegate.generatePlateReminder(acctLoginDto,licPlate);
		if(baseDto.getErrors()!=null) {
			saveErrorMessages(request,baseDto.getErrors(), "saveFailed");
		}

		request.setAttribute("tagRequestFormRequest",tagRequestForm) ;
		request.setAttribute("tagRequestForm",tagRequestForm);

		if(page.equalsIgnoreCase("signup"))        
			return mapping.findForward("signup");
		else
			return mapping.findForward("manage");
	}
}
