/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.action;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.delegate.AccountDelegate;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagUpdateResultDTO;
import com.etcc.csc.presentation.form.AccountTagForm;
import com.etcc.csc.util.UIDateUtil;
import com.etcc.csc.util.SessionUtil;

public class AccountTollTagSaveAction extends CSCBaseAction {
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) 
    throws Exception {

    AccountTagForm acctTagForm = (AccountTagForm) form;
    AccountDelegate acctDel = new AccountDelegate();
    HttpSession session = request.getSession();
    AccountLoginDTO acctLoginDto = SessionUtil.getSessionAccountLogin(session);
    if (acctLoginDto != null) {
        TagDTO[] tags = convertTagsToDTO(acctTagForm.getTollTags(), 
            acctTagForm.getIndex(), acctTagForm.getMaxRows());
/*
//        TagDTO dupTag = validateTags(tags);
        TagDTO dupTag = null;
        if (dupTag != null) {
            ActionMessage msg = DtoUtil.createActionMessage("accountTagForm.error.duplicate", dupTag.getLicPlate() + " - " + dupTag.getLicState());
            messages.add("tagError", msg);
            saveMessages(request, messages);
            acctTagForm.setShowFormValue(true);
        } else {
*/
            TagUpdateResultDTO tagResult = acctDel.updateAccountTags(
                acctLoginDto, tags, !acctTagForm.isOverrideDuplicates());
            acctTagForm.setSuccessful(tagResult.isSuccessful());
            acctTagForm.setDuplicateTags(tagResult.getDuplicateTags());
            if(saveErrorMessages(request, tagResult, "tagError")){
                acctTagForm.setShowFormValue(true);
            } 
            if (acctTagForm.isSuccessful()) {
                ActionMessages messages = getMessages(request);
                 ActionMessage msg = new ActionMessage("message.save.success");
                 messages.add("success", msg);
                 saveMessages(request, messages);
            } else if (acctTagForm.getDuplicateTags() != null &&
                    acctTagForm.getDuplicateTags().size() > 0){
                acctTagForm.setHasDuplicates(true);
                acctTagForm.setShowFormValue(true);
            }

            if (acctTagForm.isShowFormValue()) {
                acctTagForm.setEditedTollTags(acctTagForm.getTollTags());
            }

            boolean pageSet = false;
            String param = (String) session.getAttribute("displaytag");
            String paramValue = null;
            if (param != null) {
                if (request.getParameter(param) != null) {
                    pageSet = true;
                }
                if (param.length() > 0) {
                    paramValue = (String) session
                        .getAttribute("displaytagValue");
                } else {
                    pageSet = true;
                }
            } else {
                pageSet = true;
            }
            
            if (!pageSet) {
                ActionForward forward = mapping.findForward("success");
                ActionForward newForward = new ActionForward(forward);
                newForward.setPath(newForward.getPath() + "?" 
                   + param + "=" + paramValue);
                return newForward;
            }
//        }
    } else {
        throw new EtccSecurityException("Security violation in "
            + "AccountTollTagSaveAction");
    }
    return mapping.findForward("success");
  }

    private TagDTO[] convertTagsToDTO(Collection<AccountTagForm> tags, int index, int maxRows) throws Exception {
        TagDTO[] result = null;
        if (tags != null) {
//            result = new TagDTO[tags.size()];
            result = new TagDTO[maxRows];
            int idx = 0;
            int copied = 0;
            for (Iterator<AccountTagForm> i=tags.iterator(); i.hasNext(); ) {
                AccountTagForm atForm = i.next();
                if (idx >= index && copied < maxRows) {
                    TagDTO tagDto = new TagDTO();
                    BeanUtils.copyProperties(tagDto, atForm);
                    tagDto.setPlateExpireDate(UIDateUtil.stringToCalendar(atForm.getExpirationDate()));
                    result[copied++] = tagDto;
                }
                idx++;
            }
        }
        return result;
    }

/*
    private TagDTO validateTags(TagDTO[] tags) {
        if (tags != null) {
            for (int i=0; i<tags.length; i++) {
                if (duplicateTag(i, tags[i], tags)) {
                    return tags[i];
                }
            }
        }
        return null;
    }

    private boolean duplicateTag(int index, TagDTO tag, TagDTO[] tags) {
        if (tags != null && tag != null) {
            for (int i=0; i<tags.length; i++) {
                if (i != index && !tags[i].getTemporaryLicPlate()) {
                    if (equals(tag.getLicPlate(), tags[i].getLicPlate())
                            && equals(tag.getLicState(), 
                            tags[i].getLicState())) {
                        return true;        
                    }
                }
            }
        }
        return false;
    }

    private boolean equals(String value1 , String value2) {
        if (value1 != null && value2 != null) {
            return value1.equals(value2);
        }
        return false;
    }
*/
}
