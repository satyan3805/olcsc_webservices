package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

public class SummaryAvailableDTO extends BaseDTO{
    private boolean summaryAvail;
    
    public SummaryAvailableDTO() {
    }


    public void setSummaryAvail(boolean summaryAvail) {
        this.summaryAvail = summaryAvail;
    }

    public boolean isSummaryAvail() {
        return summaryAvail;
    }
}
