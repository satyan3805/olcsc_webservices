package com.etcc.csc.datatype;

import java.math.BigDecimal;
import java.util.Calendar;

public class Invoice {
    private BigDecimal amount;
    private BigDecimal veaAmount;
    private BigDecimal onlineFee;
    private String violatorId;
    private String id;
    private Calendar invoiceDate;
    private Calendar dueDate;
    private String firstName;
    private String lastName;
    private String licPlateNumber;
    private String licPlateState;
    private Violation [] violations;
    private boolean authorized;
    private boolean veaEligible;
    private BigDecimal lateFee;
    private BigDecimal premium;
    private BigDecimal mailHandlingFee;
    private BigDecimal newTrans;
    private BigDecimal pastDueAmount;
    private int violationSize;
    private BigDecimal pastDueLateFeeAmount;
    private BigDecimal pastDueMailFeeAmount;
    private BigDecimal caAccountId;
    private String caPhoneNumber;
    private String caCompany;
    private BigDecimal tollTrxnAmount; 
    private BigDecimal retailChargeAmount; 
    
  

	public Invoice() {
    }
    
    
    public BigDecimal getCaAccountId() {
		return caAccountId;
	}
	public void setCaAccountId(BigDecimal caAccountId) {
		this.caAccountId = caAccountId;
	}

	public String getCaPhoneNumber() {
		return caPhoneNumber;
	}


	public void setCaPhoneNumber(String caPhoneNumber) {
		this.caPhoneNumber = caPhoneNumber;
	}


	public String getCaCompany() {
		return caCompany;
	}


	public void setCaCompany(String caCompany) {
		this.caCompany = caCompany;
	}


	public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInvoiceDate(Calendar invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Calendar getInvoiceDate() {
        return invoiceDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLicPlateNumber(String licPlateNumber) {
        this.licPlateNumber = licPlateNumber;
    }

    public String getLicPlateNumber() {
        return licPlateNumber;
    }

    public void setLicPlateState(String licPlateState) {
        this.licPlateState = licPlateState;
    }

    public String getLicPlateState() {
        return licPlateState;
    }


/*    
    public void addViolation( Violation violation ) {
        if (  violations == null ) {
            violations = new ArrayList();
        }
        violations.add(violation  );
    }
*/
    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void setVeaAmount(BigDecimal veaAmount) {
        this.veaAmount = veaAmount;
    }

    public BigDecimal getVeaAmount() {
        return veaAmount;
    }

    public void setOnlineFee(BigDecimal onlineFee) {
        this.onlineFee = onlineFee;
    }

    public BigDecimal getOnlineFee() {
        return onlineFee;
    }

    public void setViolatorId(String violatorId) {
        this.violatorId = violatorId;
    }

    public String getViolatorId() {
        return violatorId;
    }

    public void setViolations(Violation[] violations) {
        this.violations = violations;
        if (violations != null) {
            setViolationSize(violations.length);
        }
    }

    public Violation[] getViolations() {
        return violations;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setVeaEligible(boolean veaEligible) {
        this.veaEligible = veaEligible;
    }

    public boolean isVeaEligible() {
        return veaEligible;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setMailHandlingFee(BigDecimal mailHandlingFee) {
        this.mailHandlingFee = mailHandlingFee;
    }

    public BigDecimal getMailHandlingFee() {
        return mailHandlingFee;
    }

    public void setNewTrans(BigDecimal newTrans) {
        this.newTrans = newTrans;
    }

    public BigDecimal getNewTrans() {
        return newTrans;
    }

    public void setPastDueAmount(BigDecimal pastDueAmount) {
        this.pastDueAmount = pastDueAmount;
    }

    public BigDecimal getPastDueAmount() {
        return pastDueAmount;
    }
/*    public int getViolationSize() {
        if (violations != null) {
            return violations.length;
        } else {
            return 0;
        }
    }
*/

    public void setViolationSize(int violationSize) {
        this.violationSize = violationSize;
    }

    public int getViolationSize() {
        return violationSize;
    }

    public void setPastDueLateFeeAmount(BigDecimal pastDueLateFeeAmount) {
        this.pastDueLateFeeAmount = pastDueLateFeeAmount;
    }

    public BigDecimal getPastDueLateFeeAmount() {
        return pastDueLateFeeAmount;
    }

    public void setPastDueMailFeeAmount(BigDecimal pastDueMailFeeAmount) {
        this.pastDueMailFeeAmount = pastDueMailFeeAmount;
    }

    public BigDecimal getPastDueMailFeeAmount() {
        return pastDueMailFeeAmount;
    }
    
    
    public BigDecimal getTollTrxnAmount() {
		return tollTrxnAmount;
	}


	public void setTollTrxnAmount(BigDecimal tollTrxnAmount) {
		this.tollTrxnAmount = tollTrxnAmount;
	}


	public BigDecimal getRetailChargeAmount() {
		return retailChargeAmount;
	}


	public void setRetailChargeAmount(BigDecimal retailChargeAmount) {
		this.retailChargeAmount = retailChargeAmount;
	}
    
}// end of Invoice Class 
