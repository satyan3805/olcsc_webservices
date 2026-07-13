/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.presentation.form.TagRequestForm;
import com.etcc.csc.util.SessionUtil;

public class TagRequestPayInfoAction extends CSCBaseAction {
    private static final Logger logger = Logger.getLogger(TagRequestPayInfoAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
      throws Exception
    {
        HttpSession session = request.getSession();
        AccountLoginDTO accountLoginDTO = SessionUtil.getSessionAccountLogin(session);
        TagRequestForm tagRequestForm = (TagRequestForm) form;
        long transId = tagRequestForm.getRetailTransId();
        logger.info("transId:"+transId);

//        try
//        {
/*                double paymentAmount =
                    new TolltagWSSoapHttpPortClient().getTagPayInfo(accountLoginDTO.getAcctId(),
                                                                accountLoginDTO.getDbSessionId(),
                                                                accountLoginDTO.getLoginId(),
                                                                accountLoginDTO.getLastLoginIp(),
                                                                transId);
                logger.debug("paymentAmount:"+paymentAmount);
*/


//            if (paymentAmount < 0.01) {
            AccountDelegate acctDel = new AccountDelegate();
            List<TagDTO> tags = tagRequestForm.getSavedVehicles();
            TagDTO[] tagArray = tags.toArray(new TagDTO[tags.size()]);
            boolean paymentOwed = acctDel.isPaymentOwed(accountLoginDTO, transId, tagArray);
            logger.info("isPaymentOwed: "+ paymentOwed);
            if (!paymentOwed) {

                if(!isTokenValid(request))
                {
                  return mapping.findForward("success");
                }
                TagDelegate delegate = new TagDelegate();
                BaseDTO confirmed = delegate.confirmAddTags(accountLoginDTO, transId, TagDTO.DeliveryMethod.MAIL,null);

                if (saveErrorMessages(request, confirmed, "saveFailed")){
                    return mapping.findForward("failure");
                } //else
                resetToken(request);
                saveToken(request);
                return mapping.findForward("success");
            }//else
                SessionUtil.setRetailTransId(session, transId);
                request.setAttribute( "callBack", "addTags" );
                request.setAttribute("addedTags", tagArray);

                return mapping.findForward("payment");

//         }catch (EtccErrorMessageException errorMessageException) {
//             saveErrorMessages(request,errorMessageException);
//             return mapping.findForward("failure");
//         }
    }
}
