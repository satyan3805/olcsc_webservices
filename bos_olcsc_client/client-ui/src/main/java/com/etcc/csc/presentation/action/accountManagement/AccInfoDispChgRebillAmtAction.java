/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action.accountManagement;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AppDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.presentation.form.AccountForm;
import com.etcc.csc.service.AppInterface;
import com.etcc.csc.util.SessionUtil;

/**
 * The action to handle the display of the Change Rebill Amount screen.
 */
public class AccInfoDispChgRebillAmtAction extends Action {
    private static final Logger logger = Logger.getLogger(AccInfoDispChgRebillAmtAction.class);
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                                 throws Exception {
    	AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(request.getSession());
    	AccountDTO accountDTO = SessionUtil.getAcctDTO(request);
    	AccountForm accountForm = new AccountForm();
    	accountForm.setRebillAmt(accountDTO.getRebillAmt());
    	accountForm.setLowBalLevel(accountDTO.getLowBalLevel());
    	PaymentType acctPmtType = accountDTO.getPmtTypeEnum();
    	accountForm.setPmtTypeEnum(acctPmtType);
    	AppDelegate appDel = new AppDelegate();
    	accountForm.setReqMaxRebillAmt(Double.parseDouble(appDel.getSysParam(AppInterface.MAX_REBILL_AMT)));
    	//BUG: Not actually used at this time.
    	double rebill_min_balance_ratio =  Double.parseDouble(appDel.getSysParam(AppInterface.REBILL_MIN_BALANCE_RATIO));

    	//accountForm.setPmtTypeCode(BillingInfoDTO.PAYMENT_CODE_CREDIT);
    	//accountForm.setDepAmt(accountForm.getPmtTypeCode().equals(BillingInfoDTO.PAYMENT_CODE_CREDIT)?40.00:80.00);
    	accountForm.setDepAmt(acctPmtType == PaymentType.CREDIT ? accountDTO.getDepositIncrementCC() : accountDTO.getDepositIncrementEFT());
    	accountForm.setNoOfTags(accountDTO.getNoOfTags());
    	accountForm.setReqMinRebillAmtForCC(accountDTO.getDepositAmtCC());
    	accountForm.setReqMinRebillAmtForEFT(accountDTO.getDepositAmtEFT());
    	double rebill_min_amt = 0.0;
//    	if (acctPmtType == PaymentType.CREDIT) {
//    		TagDelegate tagReqDel = new TagDelegate();
//    		AccountTagsDTO acctTagDto = tagReqDel.getAccountTags(acctLoginDto,"");
//    		if (acctTagDto.getAccountTags() != null ) {
//	    		logger.info("No of Tags ==" + acctTagDto.getAccountTags().size());
//	    		accountForm.setNoOfTags(acctTagDto.getAccountTags().size());
//    		}
//    		else
//    		{
//    			accountForm.setNoOfTags(0);
//    		}
//    		rebill_min_amt = getReqMinRebillamt(rebill_min_balance_ratio,PaymentType.CREDIT,accountForm.getReqMinRebillAmtForCC(),accountForm.getNoOfTags());
//    	} else {
//    		rebill_min_amt = getReqMinRebillamt(rebill_min_balance_ratio,PaymentType.EFT,accountForm.getReqMinRebillAmtForEFT(),accountForm.getNoOfTags());
//    	}
    	if (acctPmtType == PaymentType.CREDIT) {
    		rebill_min_amt = accountDTO.getDepositAmtCC();
    	}
		else
		{
			rebill_min_amt = accountDTO.getDepositAmtEFT();
		}
    	logger.info("The rebill_min_amt ==" + rebill_min_amt);
    	accountForm.setReqLowBalLevelForCC(accountDTO.getMinBalCC());
    	accountForm.setReqLowBalLevelForEFT(accountDTO.getMinBalEFT());
    	//accountForm.setNoOfTags(4);
    	//accountForm.setReqMinRebillAmt(getReqMinRebillamt(accountForm.getNoOfTags(), (int)accountForm.getDepAmt()));
    	accountForm.setReqMinRebillAmt(rebill_min_amt); //Lakshmi..
    	request.setAttribute(SessionUtil.REQUEST_ACCOUNT_INFO, accountDTO);
    	request.setAttribute("accountForm", accountForm);

    	return mapping.findForward("success");
    }

//     private double formatDouble(double num){
//         String fmt = "0.00";
//         DecimalFormat df = new DecimalFormat(fmt);
//         String str = df.format(num);
//         return num;
//     }

//     private boolean isMultipleOf3(double input){
////         boolean  x = false;
//         String fmt = "0.00";
//         DecimalFormat df = new DecimalFormat(fmt);
//         String str = df.format(input);
//         //StringBuilder sb = new StringBuilder(str);
//         String str1 = str.substring(str.indexOf("."),str.length());
//         logger.info("String checks ...." + str1);
//         if(str1.equals("00")){
//             return true;
//         }
//         return false;
//     }
//
//     private double getReqMinRebillamt(double rebill_min_balance_ratio, PaymentType credit, double amt, int no_of_tags) {
//        double total = 0.00;
//        if (logger.isTraceEnabled()){
//            logger.trace("getReqMinRebillamt()  " + rebill_min_balance_ratio + " " + amt + " " + no_of_tags + " " + credit);
//        }
//        if (credit == PaymentType.CREDIT) {
//            if(isMultipleOf3(Math.ceil(no_of_tags/3)) == false){
//                logger.info("Credit bill == " + (40 * (Math.ceil(no_of_tags/3)+1)));
//                total = 40 * (Math.ceil(no_of_tags/3)+1);
//            }else{
//                total = 40 * (Math.ceil(no_of_tags/3));
//            }
//        }else{
//            logger.info("EFT Bill == "+ 80 * Math.round(no_of_tags/3));
//            total = 80 * (Math.ceil(no_of_tags/3)+1);
//        }
//        return total;
////         return formatDouble(total);
//     }
}
