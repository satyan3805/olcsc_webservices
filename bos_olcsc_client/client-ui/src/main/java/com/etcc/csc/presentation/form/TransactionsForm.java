/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.util.UIDateUtil;

public class TransactionsForm extends DynaValidatorForm {
	@Override
	public void reset(ActionMapping actionMapping, 
                    HttpServletRequest request)
  {
    super.reset(actionMapping, request);
    int defaultRange = getDateRange();
    set("startDate", UIDateUtil.getShortDate(UIDateUtil.getPrevDate(defaultRange)));
    set("endDate", UIDateUtil.getCurrentShortDate());
  }

  private int getDateRange() {
    try
    {
      AppDelegate appDel = new AppDelegate();
      return Integer.parseInt(appDel.getViewTransDefaultRange());
    }
    catch(Exception ex) {
        return 30;
    }
  }
}
