package com.etcc.csc.dao.generated;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.etcc.csc.plsql.OlcAccountHistoryRec;

public class OlcAccountHistoryRecBean {
    public OlcAccountHistoryRecBean() {
    }

    public OlcAccountHistoryRecBean(OlcAccountHistoryRec olcAccountHistoryRec) throws SQLException{
        if (olcAccountHistoryRec!=null)
        {
            Calendar transactionDate = Calendar.getInstance();
            if (olcAccountHistoryRec.getTrxnDate()!=null)
            {
                transactionDate.setTime(new Date(olcAccountHistoryRec.getTrxnDate().getTime()));
                setTrxnDate(transactionDate);
            }

            Calendar postedDate = Calendar.getInstance();
            //System.out.println("posted date:"+olcAccountHistoryRec.getPostedDate());
            if (olcAccountHistoryRec.getPostedDate()!=null) {
                postedDate.setTime(new Date(olcAccountHistoryRec.getPostedDate().getTime()));
                setPostedDate(postedDate);
            }

            setTagId(olcAccountHistoryRec.getTagId());
            setLicensePlate(olcAccountHistoryRec.getLicensePlate());
            setLaneName(olcAccountHistoryRec.getLaneName());
            setLocationName(olcAccountHistoryRec.getLocationName());
            setTransType(olcAccountHistoryRec.getTransType());
            setAmount(olcAccountHistoryRec.getAmount());
            setDirection(olcAccountHistoryRec.getDirection());
            setParkingReportUrl(olcAccountHistoryRec.getParkingReportUrl());
            setSerialNum(olcAccountHistoryRec.getSerialNum());
            setLaneFullName(olcAccountHistoryRec.getLaneFullName());
            setUnitId(olcAccountHistoryRec.getUnitId());
        }
    }

    private Calendar m_trxnDate;
    private Calendar m_postedDate;
    private String m_tagId;
    private String m_licensePlate;
    private String m_laneName;
    private String m_laneFullName;
    private String m_locationName;
    private String m_transType;
    private java.math.BigDecimal m_amount;
    private String m_direction;
    private String m_parkingReportUrl;
    private java.math.BigDecimal m_serialNum;
    private String unitId;


    public void setTagId(String tagId) {
        this.m_tagId = tagId;
    }

    public String getTagId() {
        return m_tagId;
    }

    public void setLicensePlate(String licensePlate) {
        this.m_licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return m_licensePlate;
    }

    public void setLaneName(String laneName) {
        this.m_laneName = laneName;
    }

    public String getLaneName() {
        return m_laneName;
    }

    public void setLocationName(String locationName) {
        this.m_locationName = locationName;
    }

    public String getLocationName() {
        return m_locationName;
    }

    public void setTransType(String transType) {
        this.m_transType = transType;
    }

    public String getTransType() {
        return m_transType;
    }

    public void setAmount(BigDecimal amount) {
        this.m_amount = amount;
    }

    public BigDecimal getAmount() {
        return m_amount;
    }

    public void setTrxnDate(Calendar trxnDate) {
        this.m_trxnDate = trxnDate;
    }

    public Calendar getTrxnDate() {
        return m_trxnDate;
    }

    public void setPostedDate(Calendar postedDate){
        this.m_postedDate = postedDate;
    }

    public Calendar getPostedDate() {
        return m_postedDate;
    }


    public void setDirection(String direction) {
        this.m_direction = direction;
    }

    public String getDirection() {
        return m_direction;
    }

    public void setParkingReportUrl(String parkingReportUrl) {
        this.m_parkingReportUrl = parkingReportUrl;
    }

    public String getParkingReportUrl() {
        return m_parkingReportUrl;
    }

    public void setSerialNum(BigDecimal serialNum) {
        this.m_serialNum = serialNum;
    }

    public BigDecimal getSerialNum() {
        return m_serialNum;
    }

    public String getLaneFullName() {
        return m_laneFullName;
    }

    public void setLaneFullName(String laneFullName){
        this.m_laneFullName = laneFullName;
    }


    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitId() {
        return unitId;
    }
}
