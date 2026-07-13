package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a Violator.
 */
public class ViolatorDTO extends BaseDTO {
    private long violatorId;
    private String firstName;
    private String lastName;
    private String firstName2;
    private String lastName2;
    private String licPlateNbr;
    private String licPlateState;
    private String phoneNbr;
    private String emailAddr;
    private String driverLicNbr;
    private String driverLicState;
    private String comment;
    private String type;
    private String race;
    private String gender;
    private String dob;
    private String ssn;
    private int bounceCount;
    private int violationCount;
    private int excusalCount;
    private boolean vea;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleBody;
    private String vehicleYear;
    private String vehicleColor;
    private int dpsViolatorName;
    private Collection invoices;
    private Collection uninvoicedViolations;
    private double totalInvoiceAmount;
    private double totalInvoiceVeaAmount;
    private double totalUninvoicedAmount;
    
    public ViolatorDTO() {
    }


    public void setViolatorId(long violatorId) {
        this.violatorId = violatorId;
    }

    public long getViolatorId() {
        return violatorId;
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

    public void setFirstName2(String firstName2) {
        this.firstName2 = firstName2;
    }

    public String getFirstName2() {
        return firstName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLicPlateNbr(String licPlateNbr) {
        this.licPlateNbr = licPlateNbr;
    }

    public String getLicPlateNbr() {
        return licPlateNbr;
    }

    public void setLicPlateState(String licPlateState) {
        this.licPlateState = licPlateState;
    }

    public String getLicPlateState() {
        return licPlateState;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setDriverLicNbr(String driverLicNbr) {
        this.driverLicNbr = driverLicNbr;
    }

    public String getDriverLicNbr() {
        return driverLicNbr;
    }

    public void setDriverLicState(String driverLicState) {
        this.driverLicState = driverLicState;
    }

    public String getDriverLicState() {
        return driverLicState;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRace() {
        return race;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setBounceCount(int bounceCount) {
        this.bounceCount = bounceCount;
    }

    public int getBounceCount() {
        return bounceCount;
    }

    public void setViolationCount(int violationCount) {
        this.violationCount = violationCount;
    }

    public int getViolationCount() {
        return violationCount;
    }

    public void setExcusalCount(int excusalCount) {
        this.excusalCount = excusalCount;
    }

    public int getExcusalCount() {
        return excusalCount;
    }

    public void setVea(boolean vea) {
        this.vea = vea;
    }

    public boolean isVea() {
        return vea;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleBody(String vehicleBody) {
        this.vehicleBody = vehicleBody;
    }

    public String getVehicleBody() {
        return vehicleBody;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setDpsViolatorName(int dpsViolatorName) {
        this.dpsViolatorName = dpsViolatorName;
    }

    public int getDpsViolatorName() {
        return dpsViolatorName;
    }

    public void setInvoices(Collection invoices) {
        this.invoices = invoices;
    }

    public Collection getInvoices() {
        return invoices;
    }
    public void setUninvoicedViolations(Collection uninvoicedViolations) {
        this.uninvoicedViolations = uninvoicedViolations;
    }

    public Collection getUninvoicedViolations() {
        return uninvoicedViolations;
    }
    public void addInvoice(InvoiceDTO invDto) {
        if (invoices == null) {
            invoices = new ArrayList();
        }
        invoices.add(invDto);
    }
    public void setTotalInvoiceAmount(double totalInvoiceAmount) {
        this.totalInvoiceAmount = totalInvoiceAmount;
    }

    public double getTotalInvoiceAmount() {
        return totalInvoiceAmount;
    }

    public void setTotalInvoiceVeaAmount(double totalInvoiceVeaAmount) {
        this.totalInvoiceVeaAmount = totalInvoiceVeaAmount;
    }

    public double getTotalInvoiceVeaAmount() {
        return totalInvoiceVeaAmount;
    }

    public void setTotalUninvoicedAmount(double totalUninvoicedAmount) {
        this.totalUninvoicedAmount = totalUninvoicedAmount;
    }

    public double getTotalUninvoicedAmount() {
        return totalUninvoicedAmount;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("violatorId=");
        sb.append(violatorId);
        sb.append("firstName=");
        sb.append(firstName);
        sb.append(", lastName=");
        sb.append(lastName);
        sb.append(", firstName2=");
        sb.append(firstName2);
        sb.append(", lastName2=");
        sb.append(lastName2);
        sb.append(", licPlateNbr=");
        sb.append(licPlateNbr);
        sb.append(", licPlateState=");
        sb.append(licPlateState);
        sb.append(", phoneNbr=");
        sb.append(phoneNbr);
        sb.append(", emailAddr=");
        sb.append(emailAddr);
        sb.append(", driverLicNbr=");
        sb.append(driverLicNbr);
        sb.append(", driverLicState=");
        sb.append(driverLicState);
        sb.append(", comment=");
        sb.append(comment);
        sb.append(", type=");
        sb.append(type);
        sb.append(", race=");
        sb.append(race);
        sb.append(", gender=");
        sb.append(gender);
        sb.append(", dob=");
        sb.append(dob);
        sb.append(", ssn=");
        sb.append(ssn);
        sb.append(", bounceCount=");
        sb.append(bounceCount);
        sb.append(", violationCount=");
        sb.append(violationCount);
        sb.append(", excusalCount=");
        sb.append(excusalCount);
        sb.append(", vea=");
        sb.append(vea);
        sb.append(", vehicleMake=");
        sb.append(vehicleMake);
        sb.append(", vehicleBody=");
        sb.append(vehicleBody);
        sb.append(", vehicleYear=");
        sb.append(vehicleYear);
        sb.append(", vehicleColor=");
        sb.append(vehicleColor);
        sb.append(", dpsViolatorName=");
        sb.append(dpsViolatorName);
        sb.append("]");
        return sb.toString();
    }

}
