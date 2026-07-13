package com.etcc.csc.presentation.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.delegate.StateDelegate;
import com.etcc.csc.delegate.TagDelegate;
import com.etcc.csc.dto.StateDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.presentation.form.OnlineAccessForm;

public class DisplayPasswordRetrievalAction extends CSCBaseAction{
    public DisplayPasswordRetrievalAction() {
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

        getStates(request, form);
        getTagAuthorities(request);
        return mapping.findForward("success");
    }
    
    private void getStates(HttpServletRequest request, ActionForm form) throws Exception {
        StateDelegate stateDel = new StateDelegate();
        Collection<LabelValueBean> statesOptions = new ArrayList<LabelValueBean>();
        for (StateDTO stateDTO : stateDel.getStates()) {
            statesOptions.add(new LabelValueBean(stateDTO.getStateCode(), stateDTO.getStateCode()));
            if (stateDTO.isDefaultValueFlag())
            {
                OnlineAccessForm dynaForm = (OnlineAccessForm) form;
                if (dynaForm.getDriverLicState() == null || dynaForm.getDriverLicState().length()== 0 )
                    dynaForm.setDriverLicState(stateDTO.getStateCode());
            }
        }
        request.setAttribute("states", statesOptions);  
    }
    
    private void getTagAuthorities (HttpServletRequest request) throws Exception {
        TagDelegate taDel = new TagDelegate();
        Collection<LabelValueBean> tagAuthorityOptions = new ArrayList<LabelValueBean>();
        for (TagAuthorityDTO tagAuthorityDto : taDel.getTagAuthorities()) {
            tagAuthorityOptions.add(new LabelValueBean(tagAuthorityDto.getTagIdentifier(), tagAuthorityDto.getTagIdentifier()));
        }
        request.setAttribute("tagAuthorities", tagAuthorityOptions);
    }
}
