/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.presentation.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.util.DtoUtil;
import com.etcc.csc.util.StringUtil;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class CSCBaseAction extends Action {
    private static final Logger logger = Logger.getLogger(CSCBaseAction.class);

    protected static final String DEFAULT_ALERTS_PROPERTY = "alerts";
    protected static final String DEFAULT_ERROR_KEY = "error.generic";

    /**
     * @deprecated use {@link #saveAlerts(HttpServletRequest, AlertDTO[])}
     */
    @Deprecated
    protected void saveAlerts(HttpServletRequest request, Collection<AlertDTO> alerts) {
        saveAlerts(request, alerts.toArray(new AlertDTO[alerts.size()]));
    }

    protected boolean saveAlerts(HttpServletRequest request, AlertDTO[] alerts) {
        return saveAlerts(request, null, alerts);
    }

    /**
     * Saves the Messages from the alerts to the Request.
     * @param request request to which the messages are saved
     * @param dto Data Transfer object with the alerts
     * @return <tt>true</tt> if any alerts were saved
     * @see BaseDTO#getAlerts()
     * @see #saveAlerts(HttpServletRequest, AlertDTO[])
     */
    protected final boolean saveAlerts(HttpServletRequest request, BaseDTO dto) {
        if (dto == null)
            return false;
        return saveAlerts(request, dto.getAlerts());
    }

    /**
     * Add the specified alerts to the action messages while appending the specified string.
     * @param request the HTTP servlet request
     * @param appendMessage text to append to each alert message
     * @param alerts alerts to convert to messages
     * @return <tt>true</tt> if any messages are saved
     */
    protected boolean saveAlerts(HttpServletRequest request, String appendMessage, AlertDTO[] alerts) {
        if (alerts == null || alerts.length == 0) {
            return false;
        }
        //else
        ActionMessages messages = getMessages(request);
        for (AlertDTO alert : alerts) {
            if (appendMessage == null)
                addMessage(messages, alert, DEFAULT_ALERTS_PROPERTY);
            else
                addMessage(messages, alert, DEFAULT_ALERTS_PROPERTY, appendMessage);
        }
        super.saveMessages(request, messages);
        return true;
    }

    /**
     * Add the specified alert strings to the action messages.
     * @param request the HTTP servlet request
     * @param alerts array of alert message strings
     * @return <tt>true</tt> if any messages are saved
     */
    protected boolean saveAlerts(HttpServletRequest request, String[] alerts) {
        if (alerts == null || alerts.length == 0) {
            return false;
        }
        //else
        ActionMessages messages = getMessages(request);
        boolean modified = false;
        for (String text : alerts)
            modified = (addMessage(messages, text, DEFAULT_ALERTS_PROPERTY) != null) || modified;
        super.saveMessages(request, messages);
        return modified;
    }

    protected boolean saveAlert(HttpServletRequest request, String alert) {
        ActionMessages messages = addMessage(getMessages(request), alert, DEFAULT_ALERTS_PROPERTY);
        if (messages != null) {
            super.saveMessages(request, messages);
            return true;
        }
        return false;
    }

    protected boolean saveAlert(HttpServletRequest request, AlertDTO alert) {
        return saveAlert(request, alert, DEFAULT_ALERTS_PROPERTY);
    }

    protected boolean saveAlert(HttpServletRequest request, AlertDTO alert, String property) {
        ActionMessages messages = addMessage(getMessages(request), alert, property);
        if (messages != null) {
            super.saveMessages(request, messages);
            return true;
        }
        return false;
    }

    private ActionMessages addMessage(ActionMessages messages, AlertDTO alert, String property) {
        if (alert == null || StringUtil.isEmpty(property))
            return null;
        ActionMessage msg = DtoUtil.createActionMessage(DEFAULT_ERROR_KEY, alert);
        messages.add(property, msg);
        return messages;
    }

    private ActionMessages addMessage(ActionMessages messages, AlertDTO alert, String property, String additionalMessage) {
        if (alert == null || StringUtil.isEmpty(property))
            return null;
        ActionMessage msg = new ActionMessage(DEFAULT_ERROR_KEY, alert.getAlertMsg() + additionalMessage);
        messages.add(property, msg);
        return messages;
    }

    private ActionMessages addMessage(ActionMessages messages, String text, String property) {
        if (StringUtil.isEmpty(text) || StringUtil.isEmpty(property))
            return null;
        ActionMessage msg = new ActionMessage(DEFAULT_ERROR_KEY, text);
        messages.add(property, msg);
        return messages;
    }

    /**
     * @deprecated use {@link #saveErrorMessages(HttpServletRequest, ErrorMessageDTO[], String)}
     * @param request
     * @param errorMessageDTOs
     */
    @Deprecated
    protected final void saveErrorMessages(HttpServletRequest request, ErrorMessageDTO[] errorMessageDTOs) {
        saveErrorMessages(request, errorMessageDTOs, this.getClass().getName());
    }
    
    /**
     * Saves the Messages from the ErrorMessageDTOs to the Request.
     * @param request The request the messages are being saved to.  Used by the JSPs to read the errors.
     * @param dto The Data Transfer object with the errors.
     * @param actionName The key (property) for adding the messages to ActionMessages
     * @return <tt>true</tt> if any error messages were saved.
     */
    protected final boolean saveErrorMessages(HttpServletRequest request, BaseDTO dto, String actionName) {
        if (dto == null) {
            return false;
        }//else
        return saveErrorMessages(request, dto.getErrors(), actionName);
    }

    /**
     * Saves the Messages from the ErrorMessageDTOs to the Request.
     * @param request The request the messages are being saved to.  Used by the JSPs to read the errors.
     * @param errorMessageDTOs The errors
     * @param property key for adding the messages to ActionMessages (sometimes the action name)
     * @return <tt>true</tt> if any error messages were saved.
     * @see #addMessage(ActionMessages, ErrorMessageDTO, String)
     */
    protected final boolean saveErrorMessages(HttpServletRequest request, ErrorMessageDTO[] errorMessageDTOs, String property) {
        if (errorMessageDTOs == null || errorMessageDTOs.length == 0) {
            //nothing to do
            return false;
        }//else
        ActionMessages messages = getMessages(request); 
        for (ErrorMessageDTO errorMessageDTO : errorMessageDTOs) {
            addMessage(messages, errorMessageDTO, property);
        }
        super.saveMessages(request, messages);
        return true;
    }

    /**
     * Saves the message from the ErrorMessageDTO to the Request.
     * @param request The request the messages are being saved to.  Used by the JSPs to read the errors.
     * @param errorMessage the error
     * @param property key for adding the messages to ActionMessages (sometimes the action name)
     * @return <tt>true</tt> if any error messages were saved.
     */
    protected final boolean saveErrorMessage(HttpServletRequest request, ErrorMessageDTO errorMessage, String property) {
        ActionMessages messages = addMessage(getMessages(request), errorMessage, property);
        if (messages != null) {
            super.saveMessages(request, messages);
            return true;
        }
        return false;
    }

    /**
     * Add the specified messages to the request.
     * @param request the HTTP servlet request
     * @param errorMessages array of message strings
     * @param property key for adding the messages to ActionMessagess
     * @return
     */
    protected final boolean saveErrorMessages(HttpServletRequest request, String[] errorMessages, String property) {
        if (errorMessages == null || errorMessages.length == 0)
            return false;
        ActionMessages messages = getMessages(request); 
        for (String message : errorMessages)
            addMessage(messages, message, property);
        super.saveMessages(request, messages);
        return true;
    }

    /**
     * Saves the Messages from the errors to the Request.
     * @param request The request the messages are being saved to.  Used by the JSPs to read the errors.
     * @param errors The errors
     * @param resource Indicates whether the errors are bundle keys or literal values
     * @param property key for adding the messages to ActionMessages (sometimes the action name)
     * @return <tt>true</tt> if any error messages were saved.
     * @see DtoUtil#createActionMessage(Object, boolean)
     */
    protected final boolean saveErrorMessages(HttpServletRequest request, Collection<? extends Object> errors, boolean resource, String property) {
        if (errors == null || errors.size() == 0)
            return false;
        ActionMessages messages = getMessages(request); 
        for (Object key : errors)
            addMessage(messages, key, resource, property);
        super.saveMessages(request, messages);
        return true;
    }

    /**
     * Saves the Messages from the error message exception to the Request.
     * @param request The request the messages are being saved to.  Used by the JSPs to read the errors.
     * @param errorMessageException exception object containing the messages
     * @return <tt>true</tt> if any error messages were saved.
     * @see #saveErrorMessages(HttpServletRequest, Collection, boolean, String)
     */
    protected final boolean saveErrorMessages(HttpServletRequest request, EtccErrorMessageException errorMessageException) {
        return saveErrorMessages(request, errorMessageException.getErrorMessages(), false, ActionErrors.GLOBAL_MESSAGE);
    }

    private final ActionMessages addMessage(ActionMessages messages, ErrorMessageDTO errorMessage, String property) {
        if (errorMessage == null || StringUtil.isEmpty(property))
            return null;
        if (logger.isDebugEnabled())
            logger.debug("saveErrorMessage:" + errorMessage);
        ActionMessage msg;
        String text = toString(errorMessage);
        String key = errorMessage.getKey();
        if (text == null) {
            if (StringUtil.isEmpty(key))
                errorMessage.setKey(DEFAULT_ERROR_KEY);
            msg = DtoUtil.createActionMessage(errorMessage);
        } else {
            if (StringUtil.isEmpty(key))
                key = DEFAULT_ERROR_KEY;
            msg = DtoUtil.createActionMessage(key, text);
        }
        messages.add(property, msg);
        return messages;
    }

    private final ActionMessages addMessage(ActionMessages messages, Object key, boolean resource, String property) {
        if (key == null || StringUtil.isEmpty(property))
            return null;
        ActionMessage msg = DtoUtil.createActionMessage(key, resource);
        messages.add(property, msg);
        return messages;
    }

    /**
     * Default implementation returns <tt>null</tt>.
     * Action classes may override to provide a modified error message
     * (e.g. when a URL may need to be appended or altered).
     * @param errorMessageDTO The message to convert
     * @return The string representation (<tt>null</tt> if not overridden)
     */
    protected String toString(ErrorMessageDTO errorMessageDTO) {
        return null;
    }

    /**
     * @deprecated use {@link #saveErrorMessage} {@link #saveErrorMessages}
     */
    @Deprecated
    @Override
    protected final void saveErrors(HttpServletRequest request, ActionMessages errors) {
        super.saveErrors(request, errors);
    }
    /**
     * @deprecated use {@link #saveErrorMessage} {@link #saveErrorMessages}
     */
    @Deprecated
    @Override
    protected final void saveErrors(HttpSession session, ActionMessages errors) {
        super.saveErrors(session, errors);
    }
    /**
     * @deprecated use {@link #saveAlert} {@link #saveAlerts}
     */
    @Deprecated
    @Override
    protected final void saveMessages(HttpServletRequest request, ActionMessages messages) {
        super.saveMessages(request, messages);
    }
    /**
     * @deprecated use {@link #saveAlert} {@link #saveAlerts}
     */
    @Deprecated
    @Override
    protected final void saveMessages(HttpSession session, ActionMessages messages) {
        super.saveMessages(session, messages);
    }
}
