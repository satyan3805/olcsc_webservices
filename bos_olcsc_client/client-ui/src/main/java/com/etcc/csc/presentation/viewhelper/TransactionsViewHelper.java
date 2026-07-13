/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.presentation.viewhelper;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.etcc.csc.dto.bean.OlcAccountHistoryRecBean;
import com.etcc.csc.dto.bean.OlcAccountTagRecBean;
import com.etcc.csc.dto.bean.TxnTypeBean;

public class TransactionsViewHelper {
  public TransactionsViewHelper() {
  }

  public TransactionsViewHelper(OlcAccountTagRecBean[] accountTagRecs, 
                                OlcAccountHistoryRecBean[] olcTransRecBeans,
                                TxnTypeBean[] olcTransTypes,
                                String strStartIndex) throws Exception
  {
    Collection<LabelValueBean> tags = new ArrayList<LabelValueBean>();
    tags.add(new LabelValueBean("All", "All"));
    if (accountTagRecs != null){
        for (OlcAccountTagRecBean accountTagRec : accountTagRecs) {
            tags.add(new LabelValueBean(accountTagRec.getAgencyId() + 
                    accountTagRec.getTagId(), 
                    accountTagRec.getAgencyId() + 
                    delimiter + 
                    accountTagRec.getTagId()));
            
        }
    }
    setTags(tags);
    
//    Collection txnTypes = new ArrayList();
//    if (olcTransTypes!=null)
//    {
//        for(int i=0; i<olcTransTypes.length;i++) {
//            txnTypes.add(new LabelValueBean(olcTransTypes[i].getDescription(),
//                                            olcTransTypes[i].getTransTypeId().toString()));
//        }
//    }
    setTxnTypes(olcTransTypes);

    // replace with the result from db
    Collection<LabelValueBean> periods = new ArrayList<LabelValueBean>();
    periods.add(new LabelValueBean("Last 30 days", "Last 30 days"));
    periods.add(new LabelValueBean("Last 60 days", "Last 60 days"));
    periods.add(new LabelValueBean("Last 90 days", "Last 90 days"));
    setPeriods(periods);

    setNumberOfRecords( olcTransRecBeans == null ? 0 : olcTransRecBeans.length );
    setStartIndex( StringUtils.isEmpty( strStartIndex ) ? 0 : Integer.parseInt(strStartIndex) ); 

    processIndex();
  }

  private static final String delimiter = "$|$";
  private static final int limitPerPage = 50;
  private static final int outLimit = -1;

  private Collection<LabelValueBean> tags;
  private Collection<LabelValueBean> periods;
  private Collection records;
  private int startIndex;
  private int endIndex;
  private int prevIndex;
  private int nextIndex;
  private int numberOfRecords;
  private TxnTypeBean[] txnTypes;


  private void processIndex()
  {
    setEndIndex(getStartIndex() + limitPerPage - 1);
    if (getEndIndex() >= getNumberOfRecords())
    {
      setEndIndex(getNumberOfRecords() - 1);
    }

    setPrevIndex(getStartIndex() - limitPerPage);
    if (getPrevIndex() < 0)
    {
      setPrevIndex(outLimit);
    }

    setNextIndex(getEndIndex() + 1);
    if (getNextIndex() >= getNumberOfRecords())
    {
      setNextIndex(outLimit);
    }
  }


  public void setTags(Collection<LabelValueBean> tags)
  {
    this.tags = tags;
  }

  public Collection<LabelValueBean> getTags()
  {
    return tags;
  }

  public void setPeriods(Collection<LabelValueBean> periods)
  {
    this.periods = periods;
  }

  public Collection<LabelValueBean> getPeriods()
  {
    return periods;
  }

  public void setRecords(Collection records)
  {
    this.records = records;
  }

  public Collection getRecords()
  {
    return records;
  }

  public void setStartIndex(int startIndex)
  {
    this.startIndex = startIndex;
  }

  public int getStartIndex()
  {
    return startIndex;
  }

  public void setPrevIndex(int prevIndex)
  {
    this.prevIndex = prevIndex;
  }

  public int getPrevIndex()
  {
    return prevIndex;
  }

  public void setNextIndex(int nextIndex)
  {
    this.nextIndex = nextIndex;
  }

  public int getNextIndex()
  {
    return nextIndex;
  }

  public void setNumberOfRecords(int numberOfRecords)
  {
    this.numberOfRecords = numberOfRecords;
  }

  public int getNumberOfRecords()
  {
    return numberOfRecords;
  }

  public void setEndIndex(int endIndex)
  {
    this.endIndex = endIndex;
  }

  public int getEndIndex()
  {
    return endIndex;
  }

  public void setTxnTypes(TxnTypeBean[] txnTypes) {
    this.txnTypes = txnTypes;
  }

  public TxnTypeBean[] getTxnTypes() {
    return txnTypes;
  }
}
