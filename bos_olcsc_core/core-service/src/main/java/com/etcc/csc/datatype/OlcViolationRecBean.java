package com.etcc.csc.datatype;

import java.math.BigDecimal;
import java.util.Calendar;

public class OlcViolationRecBean {
	private BigDecimal violationId;
	private java.util.Calendar violationTime;
	private String laneName;

	public OlcViolationRecBean() {
	}

	public void setViolationId(BigDecimal violationId) {
		this.violationId = violationId;
	}

	public BigDecimal getViolationId() {
		return violationId;
	}

	public void setViolationTime(Calendar violationTime) {
		this.violationTime = violationTime;
	}

	public Calendar getViolationTime() {
		return violationTime;
	}

	public void setLaneName(String laneName) {
		this.laneName = laneName;
	}

	public String getLaneName() {
		return laneName;
	}
}
