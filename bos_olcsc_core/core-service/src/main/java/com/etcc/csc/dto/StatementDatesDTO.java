package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.Collection;

public class StatementDatesDTO extends BaseDTO{
    
    private Collection dates;
    
    public StatementDatesDTO() {
    }


    public void setDates(Collection dates) {
        this.dates = dates;
    }

    public Collection getDates() {
        return dates;
    }

  
}
