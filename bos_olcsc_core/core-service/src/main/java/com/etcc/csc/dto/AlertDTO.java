package com.etcc.csc.dto;

import java.util.List;

import org.apache.commons.betwixt.digester.InfoRule;

import com.etcc.csc.common.BaseDTO;

/**
 * Represents an alert message for a given account.
 */
public class AlertDTO extends BaseDTO {
    private String alertMsg;
    private Long invoiceId;
    private List<CitationInfoRecDTO> infoCitationItems;
    public AlertDTO() {
    }

    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }

    public String getAlertMsg() {
        return alertMsg;
    }
    
    public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public List<CitationInfoRecDTO> getInfoCitationItems() {
		return infoCitationItems;
	}

	public void setInfoCitationItems(List<CitationInfoRecDTO> infoCitationItems) {
		this.infoCitationItems = infoCitationItems;
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("AlertDTO alertMsg=");
        sb.append(alertMsg);
        sb.append("AlertDTO invoiceId=");
        sb.append(invoiceId);
        if (infoCitationItems!=null && infoCitationItems.size() > 0) {
        	for (CitationInfoRecDTO infoCitationItem : infoCitationItems) {
                sb.append("CitationInfoRecDTO invoiceId=");
                sb.append(infoCitationItem.getInvoiceId());
                sb.append("CitationInfoRecDTO citationId=");
                sb.append(infoCitationItem.getCitationId());
                sb.append("CitationInfoRecDTO courtId=");
                sb.append(infoCitationItem.getCourtId());
                sb.append("CitationInfoRecDTO courtName=");
                sb.append(infoCitationItem.getCourtName());
                sb.append("CitationInfoRecDTO judge=");
                sb.append(infoCitationItem.getJudge());
                sb.append("CitationInfoRecDTO address1=");
                sb.append(infoCitationItem.getAddress1());
                sb.append("CitationInfoRecDTO address2=");
                sb.append(infoCitationItem.getAddress2());
                sb.append("CitationInfoRecDTO city=");
                sb.append(infoCitationItem.getCity());
                sb.append("CitationInfoRecDTO state=");
                sb.append(infoCitationItem.getState());
                sb.append("CitationInfoRecDTO zipCode=");
                sb.append(infoCitationItem.getZipCode());
                sb.append("CitationInfoRecDTO plus4=");
                sb.append(infoCitationItem.getPlus4());
                sb.append("CitationInfoRecDTO courtDate=");
                sb.append(infoCitationItem.getCourtDate());
        	}
        }
        sb.append("]");
        return sb.toString();
    }
}
