/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

import com.etcc.csc.util.StringUtil;

/**
 * Data Transfer object for Multiple Vehicle Upload.
 * @author Milosh Boroyevich
 */
//Based on the original version from OLCSCService.
public class MultiVehicleUploadDTO extends BaseDTO {
    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 7933865136457444100L;
    private static final Logger logger = Logger.getLogger(MultiVehicleUploadDTO.class);

    public static final String[] UPLOAD_FILE_HEADER = {
        "* Action","* EZ TAG?","* License Plate State","* License Plate Number","* Temporary Plate?",
        "* Vehicle Class","* Vehicle Year","* Vehicle Make","* Vehicle Model","* Vehicle Color","* Vehicle Nickname"
        };
    public static final int UPLOAD_FILE_COLS = UPLOAD_FILE_HEADER.length;

    private long posId;
    private long transactionId;

    private int hardErrorCount; // UI Display for number of hard errors encountered during processing
    private int vehicleAddCount; // number of vehicle add requests processed
    private int vehicleInactivateCount; // number of vehicle inactivate requests processed
    private long reportId; // id of the pdf report

    private double tagActivateAmt; // UI Display and charged at fulfillment
    private double pendingDepositAmt; // Deposit due at time of fulfillment
    private double minBalAmt; // Minimum balance amount for the account
    private double totalAmt; // Total due at time of checkout


    /** Tag objects representing processed records. */
    private MultiVehicleDTO[] multiVehicleTags;
    /** Tag objects representing subset of processed ADD records. */
    private MultiVehicleDTO[] multiVehicleAddTags;
    private boolean invalidFile = false; // Whether the buffer passed to the constructor is invalid
    //Defect#9521
    private String fileName;

    /**
     * Constructor.
     */
    public MultiVehicleUploadDTO() {
    }

    //Defect#9521
    public MultiVehicleUploadDTO(BufferedReader reader, int maxSize, String fileName) {    	
    	this(reader, maxSize);
    	this.fileName = fileName;
    }
    /**
     * Constructor to initialize the tags from the specified reader.
     * If the format of the specified reader is invalid, <tt>invalidFile</tt> is set to <tt>true</tt>.
     * @param reader buffered reader (in CSV format) to parse (one tag per row)
     * @param maxSize maximum number of tags to process
     * @see #getMultiVehicleTags()
     * @see #isInvalidFile()
     */
    // moved from com.etcc.csc.presentation.action.accountManagement.AccountUploadMultipleVehicleAction
    public MultiVehicleUploadDTO(BufferedReader reader, int maxSize) {
        this.posId = -1;
        this.transactionId = -1;
        this.invalidFile = false;

        ArrayList<MultiVehicleDTO> multiVehicleDTOs = new ArrayList<MultiVehicleDTO>();
        ArrayList<MultiVehicleDTO> multiVehicleAddDTOs = new ArrayList<MultiVehicleDTO>();
        try {
            boolean addRow = false;
            MultiVehicleDTO multiVehicleDTO = null;
            String line = null;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("#")) {
                    this.invalidFile = true;
                    break;
                }

                if (row == (maxSize+1))
                    break;

                String str[] = line.split(",", -1);
                if (row == 0) {
                    // header checking
                    for (int i = 0; i < str.length; i++) {
                        if (!str[i].trim().equalsIgnoreCase(UPLOAD_FILE_HEADER[i])) {
                            this.invalidFile = true;
                            break;
                        }
                    }
                } else if (str.length < UPLOAD_FILE_COLS) {
                    // pad the array with null
//                    str = Arrays.copyOf(str, UPLOAD_FILE_COLS);  // requires JDK 1.6
                    String[] copy = new String[UPLOAD_FILE_COLS];
                    System.arraycopy(str, 0, copy, 0, str.length);
                    str = copy;
                }

                if (this.invalidFile || str.length != UPLOAD_FILE_COLS) {
                    this.invalidFile = true;
                    break;
                }
                if (row == 0) {
                    addRow = false;
                } else if (!StringUtil.isEmpty(str[0]) ||
                        !StringUtil.isEmpty(str[1]) ||
                        !StringUtil.isEmpty(str[2]) ||
                        !StringUtil.isEmpty(str[3]) ||
                        !StringUtil.isEmpty(str[4]) ||
                        !StringUtil.isEmpty(str[5]) ||
                        !StringUtil.isEmpty(str[6]) ||
                        !StringUtil.isEmpty(str[7]) ||
                        !StringUtil.isEmpty(str[8]) ||
                        !StringUtil.isEmpty(str[9]) ||
                        !StringUtil.isEmpty(str[10]))
                {
                    if (row == 1 || row == 2) {
                        // MB: Why the special condition for the first 2 rows of data?
                        if (!StringUtil.isEmpty(str[3]) ||
                                !StringUtil.isEmpty(str[8]) ||
                                !StringUtil.isEmpty(str[9]) ||
                                !StringUtil.isEmpty(str[10]))
                            addRow = true;
                    } else {
                        addRow = true;
                    }
                }

                if (addRow) {
                    multiVehicleDTO = new MultiVehicleDTO();
                    multiVehicleDTO.setRowNumber(row+1);
                    for (int i=0; i < str.length; i++) {
                        switch (i) {
                        case 0:
                            multiVehicleDTO.setAction(str[i].trim());
                            break;
                        case 1:
                            boolean ezTagRequired = StringUtil.stringToBoolean(str[i]);
                            multiVehicleDTO.setEzTagRequired(ezTagRequired);
                            multiVehicleDTO.setEzTagEnter(str[i]); //Defect#9521
                            if (!ezTagRequired)
                                multiVehicleDTO.setTagTypeCode("F");
                            break;
                        case 2:
                            multiVehicleDTO.setLicState(str[i].trim());
                            break;
                        case 3:
                            multiVehicleDTO.setLicPlate(str[i].trim());
                            break;
                        case 4:
                            multiVehicleDTO.setTemporaryLicPlate(StringUtil.stringToBoolean(str[i]));
                            multiVehicleDTO.setTemporaryLicPlateEnter(str[i]); //Defect#9521
                            break;
                        case 5:
                            multiVehicleDTO.setVehicleClassCode(str[i].trim());
                            break;
                        case 6:
                            multiVehicleDTO.setVehicleYear(str[i].trim());
                            break;
                        case 7:
                            multiVehicleDTO.setVehicleMake(str[i].trim());
                            break;
                        case 8:
                            multiVehicleDTO.setVehicleModel(str[i].trim());
                            break;
                        case 9:
                            multiVehicleDTO.setVehicleColor(str[i].trim());
                            break;
                        case 10:
                            multiVehicleDTO.setNickname(str[i].trim());
                            break;
                        }
                         logger.trace("Cell values for row: " + row + " and column: " + i + " are: " + str[i]);
                         logger.info("License Plate Number: "+ multiVehicleDTO.getLicPlate() + ", EZ TAG?: " + multiVehicleDTO.isEzTagRequired() + ", Temporary Plate?: " +multiVehicleDTO.isTemporaryLicPlate());
                    }// for loop
                    multiVehicleDTOs.add(multiVehicleDTO);
                    //Defect#9521
                    //if (multiVehicleDTO.getUploadAction() == MultiVehicleDTO.UploadAction.ADD)
                    if (multiVehicleDTO.getAction().equalsIgnoreCase(MultiVehicleDTO.ADD))
                        multiVehicleAddDTOs.add(multiVehicleDTO);
                    addRow = false;
                }// if addrow
                row++;
            }
            reader.close();
        } catch (IOException ioe) {
            logger.info("Exception in generateMultiVehicleList: " + ioe.getMessage(), ioe);
            this.invalidFile = true;
        }
        this.multiVehicleTags = multiVehicleDTOs.toArray(new MultiVehicleDTO[multiVehicleDTOs.size()]);
        this.multiVehicleAddTags = multiVehicleAddDTOs.toArray(new MultiVehicleDTO[multiVehicleAddDTOs.size()]);
    }

    /**
     * Returns <tt>true</tt> if there is at least one <tt>ADD</tt> EZ Tag present.
     * @return <tt>true</tt> if there is at least one <tt>ADD</tt> EZ Tag present
     * @see #getMultiVehicleAddTags()
     * @see MultiVehicleDTO.UploadAction#ADD
     * @see MultiVehicleDTO#isEzTagRequired()
     */
    @IgnoreProperty
    public boolean isAddEzTagsExist() {
        if (multiVehicleAddTags != null)
            for (MultiVehicleDTO tag : multiVehicleAddTags)
                if (tag.isEzTagRequired())
                    return true;
        return false;
    }


    @Override
    public String toString() {
        return toString(-1);
    }

    /**
     * This service creates a string representation of the values in the entity.
     *
     * @param maxMultiVehicleTags if -1 all multiVehicleTags are converted toString, if 0 then no multiVehicleTags are
     *            converted toString, if bigger than 0 then this is the upper limit of multiVehicleTags toString
     *            conversion, if less than -1, then invalid value
     * @return Representation of this object as a String, with up to the max amount of vehicle tags listed.
     */
    public String toString(int maxMultiVehicleTags) {
        return toStringBuilder(maxMultiVehicleTags).toString();
    }

    /**
     *
     * @see MultiVehicleUploadDTO#toString(int)
     * @param maxMultiVehicleTags
     * @return Representation of this object as a StringBuilder, with up to the max amount of vehicle tags listed.
     */
    public StringBuilder toStringBuilder(int maxMultiVehicleTags) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("posId=");
        sb.append(this.posId);
        sb.append(", transactionId=");
        sb.append(this.transactionId);
        sb.append(", hardErrorCount="); // UI Display for number of hard errors encountered during processing" +
        sb.append(this.hardErrorCount);
        sb.append(", vehicleAddCount="); // number of vehicle add requests processed
        sb.append(this.vehicleAddCount);
        sb.append(", vehicleInactivateCount="); // number of vehicle inactivate requests processed
        sb.append(this.vehicleInactivateCount);
        sb.append(", reportId="); // id of the pdf report
        sb.append(this.reportId);
        sb.append(", tagActivateAmt="); // UI Display and charged at fulfillment
        sb.append(this.tagActivateAmt);
        sb.append(", pendingDepositAmt="); // Deposit due at time of fulfillment
        sb.append(this.pendingDepositAmt);
        sb.append(", minBalAmt="); // Minimum balance amount for the account
        sb.append(this.minBalAmt);
        sb.append(", totalAmt="); // Total due at time of checkout
        sb.append(this.totalAmt);
        if (maxMultiVehicleTags == -1) {
            sb.append(", multiVehicleTags="); // Tag objects representing processed records
            sb.append(Arrays.toString(this.multiVehicleTags));
        } else if (maxMultiVehicleTags > 0) {
            sb.append(", multiVehicleTags (maxMultiVehicleTags="); // Tag objects representing processed records
            sb.append(maxMultiVehicleTags);
            sb.append(")=");
            sb.append(StringUtil.toStringBuilder(this.multiVehicleTags, maxMultiVehicleTags));
        }
        sb.append(super.toString());
        sb.append("]");
        return sb;
    }

    /**
     * @return the posId
     */
    public long getPosId() {
        return this.posId;
        // end getPosId
    }

    /**
     * @param posId the posId to set
     */
    public void setPosId(long posId) {
        this.posId = posId;
        // end setPosId
    }

    /**
     * @return the transactionId
     */
    public long getTransactionId() {
        return this.transactionId;
        // end getTransactionId
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
        // end setTransactionId
    }

    /**
     * @return the hardErrorCount
     */
    public int getHardErrorCount() {
        return this.hardErrorCount;
        // end getHardErrorCount
    }

    /**
     * @param hardErrorCount the hardErrorCount to set
     */
    public void setHardErrorCount(int hardErrorCount) {
        this.hardErrorCount = hardErrorCount;
        // end setHardErrorCount
    }

    /**
     * @return the vehicleAddCount
     */
    public int getVehicleAddCount() {
        return this.vehicleAddCount;
        // end getVehicleAddCount
    }

    /**
     * @param vehicleAddCount the vehicleAddCount to set
     */
    public void setVehicleAddCount(int vehicleAddCount) {
        this.vehicleAddCount = vehicleAddCount;
        // end setVehicleAddCount
    }

    /**
     * @return the vehicleInactivateCount
     */
    public int getVehicleInactivateCount() {
        return this.vehicleInactivateCount;
        // end getVehicleInactivateCount
    }

    /**
     * @param vehicleInactivateCount the vehicleInactivateCount to set
     */
    public void setVehicleInactivateCount(int vehicleInactivateCount) {
        this.vehicleInactivateCount = vehicleInactivateCount;
        // end setVehicleInactivateCount
    }

    /**
     * @return the reportId
     */
    public long getReportId() {
        return this.reportId;
        // end getReportId
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(long reportId) {
        this.reportId = reportId;
        // end setReportId
    }

    /**
     * @return the tagActivateAmt
     */
    public double getTagActivateAmt() {
        return this.tagActivateAmt;
        // end getTagActivateAmt
    }

    /**
     * @param tagActivateAmt the tagActivateAmt to set
     */
    public void setTagActivateAmt(double tagActivateAmt) {
        this.tagActivateAmt = tagActivateAmt;
        // end setTagActivateAmt
    }

    /**
     * @return the pendingDepositAmt
     */
    public double getPendingDepositAmt() {
        return this.pendingDepositAmt;
        // end getPendingDepositAmt
    }

    /**
     * @param pendingDepositAmt the pendingDepositAmt to set
     */
    public void setPendingDepositAmt(double pendingDepositAmt) {
        this.pendingDepositAmt = pendingDepositAmt;
        // end setPendingDepositAmt
    }

    /**
     * @return the minBalAmt
     */
    public double getMinBalAmt() {
        return this.minBalAmt;
        // end getMinBalAmt
    }

    /**
     * @param minBalAmt the minBalAmt to set
     */
    public void setMinBalAmt(double minBalAmt) {
        this.minBalAmt = minBalAmt;
        // end setMinBalAmt
    }

    /**
     * @return the totalAmt
     */
    public double getTotalAmt() {
        return this.totalAmt;
        // end getTotalAmt
    }

    /**
     * @param totalAmt the totalAmt to set
     */
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
        // end setTotalAmt
    }

    /**
     * @return the multiVehicleTags
     */
    public MultiVehicleDTO[] getMultiVehicleTags() {
        return this.multiVehicleTags;
        // end getMultiVehicleTags
    }

    /**
     * @param multiVehicleTags the multiVehicleTags to set
     */
    public void setMultiVehicleTags(MultiVehicleDTO[] multiVehicleTags) {
        this.multiVehicleTags = multiVehicleTags;
        // end setMultiVehicleTags
    }

    public void setMultiVehicleAddTags(MultiVehicleDTO[] multiVehicleAddTags) {
        this.multiVehicleAddTags = multiVehicleAddTags;
    }

    public MultiVehicleDTO[] getMultiVehicleAddTags() {
        return multiVehicleAddTags;
    }

    /**
     * @return the invalidFile
     */
    public boolean isInvalidFile() {
        return invalidFile;
    }

    /**
     * @param invalidFile the invalidFile to set
     */
    public void setInvalidFile(boolean invalidFile) {
        this.invalidFile = invalidFile;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	


}
