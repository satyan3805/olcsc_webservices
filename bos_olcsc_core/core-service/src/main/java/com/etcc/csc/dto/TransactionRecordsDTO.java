package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;
import com.etcc.csc.dao.generated.OlcAccountHistoryRecBean;

public class TransactionRecordsDTO extends BaseDTO{
    OlcAccountHistoryRecBean[] records;


    public void setRecords(OlcAccountHistoryRecBean[] records) {
        this.records = records;
    }

    public OlcAccountHistoryRecBean[] getRecords() {
        return records;
    }
}
