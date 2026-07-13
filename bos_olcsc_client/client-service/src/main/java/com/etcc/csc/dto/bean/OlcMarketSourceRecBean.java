package com.etcc.csc.dto.bean;

import java.io.Serializable;
import java.math.BigDecimal;

//Copied from com.etcc.csc.ws.tolltag.dao.OlcMarketSourceRec in OLCSCService module.
public class OlcMarketSourceRecBean implements Serializable {
  private static final long serialVersionUID = 7972857080585856074L;
  private BigDecimal m_msId;
  private String m_msDescr;

  public OlcMarketSourceRecBean() { }

  public OlcMarketSourceRecBean(BigDecimal msId, String msDescr) {
    setMsId(msId);
    setMsDescr(msDescr);
  }


  /* accessor methods */
  public BigDecimal getMsId()
  { return m_msId; }

  public void setMsId(BigDecimal msId)
  { m_msId = msId; }

  public String getMsDescr()
  { return m_msDescr; }

  public void setMsDescr(String msDescr)
  { m_msDescr = msDescr; }
}
