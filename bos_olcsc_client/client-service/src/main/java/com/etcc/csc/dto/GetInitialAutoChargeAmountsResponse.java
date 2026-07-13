package com.etcc.csc.dto;

public class GetInitialAutoChargeAmountsResponse extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3766025370970544933L;
	private String initialPrepaidBalance;
	private String autoChargAmount;
	private String lowBalanceLevel;

	public String getInitialPrepaidBalance() {
		return initialPrepaidBalance;
	}

	public void setInitialPrepaidBalance(String initialPrepaidBalance) {
		this.initialPrepaidBalance = initialPrepaidBalance;
	}

	public String getAutoChargAmount() {
		return autoChargAmount;
	}

	public void setAutoChargAmount(String autoChargAmount) {
		this.autoChargAmount = autoChargAmount;
	}

	public String getLowBalanceLevel() {
		return lowBalanceLevel;
	}

	public void setLowBalanceLevel(String lowBalanceLevel) {
		this.lowBalanceLevel = lowBalanceLevel;
	}

	@Override
	public String toString() {
		return "GetInitialAutochargeAmountsResponse [initialPrepaidBalance=" + initialPrepaidBalance
				+ ", autoChargAmount=" + autoChargAmount + ", lowBalanceLevel=" + lowBalanceLevel + "]";
	}

}
