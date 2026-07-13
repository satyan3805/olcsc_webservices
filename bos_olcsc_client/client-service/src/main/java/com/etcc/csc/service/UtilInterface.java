package com.etcc.csc.service;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.LovDTO;
import com.etcc.csc.dto.bean.OlcMarketSourceRecBean;

import java.util.Collection;

//Copied from com.etcc.csc.service.LovInterface and com.etcc.csc.service.UtilInterface in OLCSCService module.
@Deprecated
public interface UtilInterface extends ServiceInterface {
    /**
     * Retrieves a collection of credit card types.
     * @return Returns a collection of CreditCardDTO.
     * @throws EtccException
     * @deprecated use {@link CreditCardInterface#getCreditCardTypes()}
     */
    @Deprecated
    Collection<CreditCardDTO> getCreditCardTypes() throws EtccException;

    /**
     * @return
     * @throws EtccException
     * @deprecated use {@link TolltagInterface#getMarketSource()}
     */
    @Deprecated
    public OlcMarketSourceRecBean[] getMarketSource() throws EtccException;

    /**
     * Retrieves the collection of lov.
     * @return the collection of lov
     * @deprecated use {@link LovInterface#getLov(String)}
     */
    @Deprecated
    public Collection<LovDTO> getLov(String lovName) throws EtccException, EtccSecurityException;

    @Deprecated
    public static String MSTAT_SORT_ACCT_SUM_BY = "mstatb";
    @Deprecated
    public static String MSTAT_SORT_TAG_SUM_BY = "mstatc";
    @Deprecated
    public static String MSTAT_SORT_STMT_DETAILS_BY = "mstatd";
}
