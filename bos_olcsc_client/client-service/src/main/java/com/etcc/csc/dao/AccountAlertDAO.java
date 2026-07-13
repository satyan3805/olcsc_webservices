/*
 * Copyright 2009 Electronic Transaction Consultants 
 */
package com.etcc.csc.dao;

import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.service.AccountAlertInterface;

public abstract class AccountAlertDAO extends BaseDAO implements AccountAlertInterface {
    protected static final int ADDRESS_CLEANSE_ALERT_TYPE_ID = 12;

    protected String toErrorString(ErrorMessageDTO[] c) {
        StringBuilder sb = new StringBuilder();
        for (ErrorMessageDTO message : c) {
            sb.append(message.getMessage());
            sb.append("\n");
        }
        return sb.toString();
    }
}
