/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.VehicleDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.AccountTagForm;
import com.etcc.csc.util.CoreDateUtil;
import com.etcc.csc.util.SessionUtil;

public class AccountTollTagDisplayAction extends Action {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {
    
    AccountTagForm acctTagForm = (AccountTagForm) form;
    
    AccountDelegate acctDel = new AccountDelegate();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(
        request.getSession());
    List<AccountTagForm> tags = null;
    
    if (acctLoginDto != null) {
        TagDTO[] newTags  = acctDel.getAccountTags(acctLoginDto);
        if (newTags != null) {
            tags = new ArrayList<AccountTagForm>(newTags.length);
            for (TagDTO tagDTO : newTags) {
                AccountTagForm tag = new AccountTagForm();
                BeanUtils.copyProperties(tag,tagDTO);
                tag.setExpirationDate(CoreDateUtil.getShortDate(tag.getPlateExpirDate()));

                tags.add(tag);
            }
        }
        acctTagForm.setTollTags(tags);
    } else {
        throw new EtccSecurityException("Security violation in "
            + "AccountTollTagDisplayAction");
    }
    StateDelegate stateDel = new StateDelegate();
    request.setAttribute("states", stateDel.getStates());
    
    VehicleDelegate vehicleDel = new VehicleDelegate();
    request.setAttribute("vehicleClasses", vehicleDel.getVehicleClasses());
    
    if (acctTagForm.isSuccessful()) {
        acctTagForm.setOverrideDuplicates(false);
    }
    
    if (acctTagForm.isEditMode()) {
        @SuppressWarnings("unchecked")
        Enumeration params = request.getParameterNames();
        if (params != null) {
            while (params.hasMoreElements()) {
                String param = (String) params.nextElement();
                if (param.startsWith("d-")) {
                    request.getSession().setAttribute("displaytag", param);
                    request.getSession().setAttribute("displaytagValue", 
                        request.getParameter(param));
                }
            }
        }
    } else {
        request.getSession().removeAttribute("displaytag");
        request.getSession().removeAttribute("displaytagValue");
    }
    return mapping.findForward("success");
  }
}
