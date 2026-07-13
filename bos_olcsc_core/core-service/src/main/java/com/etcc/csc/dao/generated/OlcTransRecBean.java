package com.etcc.csc.dao.generated;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.etcc.csc.plsql.OlcTransRec;

public class OlcTransRecBean
{

  private java.util.Calendar m_transactionDate;
  private String m_tagId;
  private String m_licPlate;
  private String m_licState;
  private String m_lane;
  private String m_direction;
  private String m_location;
  private String m_transTypeDescr;
  private java.math.BigDecimal m_amount;

  public OlcTransRecBean()
  {
  }

  public OlcTransRecBean(OlcTransRec olcTransRec)
  {
    Calendar transactionDate = Calendar.getInstance( );
    
    try{
    transactionDate.setTime( new Date(olcTransRec.getTransactionDate().getTime()) );
    setTransactionDate(transactionDate);
    setTagId(olcTransRec.getTagId());
    setLicPlate(olcTransRec.getLicPlate());
    setLicState(olcTransRec.getLicState());
    setLane(olcTransRec.getLane());
    setDirection(olcTransRec.getDirection());
    setLocation(olcTransRec.getLocation());
    setTransTypeDescr(olcTransRec.getTransTypeDescr());
    setAmount(olcTransRec.getAmount());
    
    }catch(Exception e){
    	
    }
  }


  public void setTagId(String tagId)
  {
    this.m_tagId = tagId;
  }

  public String getTagId()
  {
    return m_tagId;
  }

  public void setLicPlate(String licPlate)
  {
    this.m_licPlate = licPlate;
  }

  public String getLicPlate()
  {
    return m_licPlate;
  }

  public void setLicState(String licState)
  {
    this.m_licState = licState;
  }

  public String getLicState()
  {
    return m_licState;
  }

  public void setLane(String lane)
  {
    this.m_lane = lane;
  }

  public String getLane()
  {
    return m_lane;
  }

  public void setDirection(String direction)
  {
    this.m_direction = direction;
  }

  public String getDirection()
  {
    return m_direction;
  }

  public void setLocation(String location)
  {
    this.m_location = location;
  }

  public String getLocation()
  {
    return m_location;
  }

  public void setTransTypeDescr(String transTypeDescr)
  {
    this.m_transTypeDescr = transTypeDescr;
  }

  public String getTransTypeDescr()
  {
    return m_transTypeDescr;
  }

  public void setAmount(BigDecimal amount)
  {
    this.m_amount = amount;
  }

  public BigDecimal getAmount()
  {
    return m_amount;
  }

  public void setTransactionDate(Calendar transactionDate)
  {
    this.m_transactionDate = transactionDate;
  }

  public Calendar getTransactionDate()
  {
    return m_transactionDate;
  }
}
