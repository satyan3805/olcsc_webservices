/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.dummy;

import java.util.HashMap;
import java.util.Map;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.AppDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.Invoice;
import com.etcc.csc.service.AppInterface;
import com.etcc.csc.service.AppTestImpl;

/**
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class DummyAppDAO extends AppDAO {
    private static final Map<String, String> SYS_PARAMS;
    private static final AppInterface impl = new AppTestImpl();

    static {
        //While many threads can be reading, this only only written to here.
        SYS_PARAMS = new HashMap<String, String>();
        SYS_PARAMS.put(AppInterface.MAX_AUTHORIZED_CONTACTS, "3");
        SYS_PARAMS.put(AppInterface.CLIENT_PHONE1, "555-555-5555");
        SYS_PARAMS.put(AppInterface.MAX_REBILL_AMT, "4800");
        SYS_PARAMS.put(AppInterface.REBILL_MIN_BALANCE_RATIO, "0.25");
    }

    /**
     * @see AppDAO#contactUs(long, String, String, String, String, String, String)
     */
    @Override
    public boolean contactUs(long docId, String docType, String licState, String licPlate, String replyAddress,
            String comment, String dbSessionId) throws EtccException {
        return impl.contactUs(docId, docType, licState, licPlate, replyAddress, comment, dbSessionId);
    }

    /**
     * @see AppDAO#getPOSId(String)
     */
    @Override
    public Long getPOSId(String url) throws EtccException {
        return impl.getPOSId(url);
    }

    /**
     * @see AppDAO#getVeaText(AccountLoginDTO, Invoice[])
     */
    @Override
    public String getVeaText(AccountLoginDTO acctLoginDto, Invoice[] invoices) throws EtccException {
        return impl.getVeaText(acctLoginDto, invoices);
    }

    /**
     * @see AppDAO#loadParam(String)
     */
    @Override
    protected String loadParam(String paramName) throws EtccException {
        return SYS_PARAMS.get(paramName);
    }

	@Override
	public Long[] getAccountPostingAndShiftId(AccountLoginDTO acctLoginDTO,
			Long eventId) throws EtccException {
		return new Long[]{1L,2L};
	}

	@Override
	public Long getAccountBillingMethodIdByToken(long token)
			throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getAcctVehicleId(long accid, String lic_plate_number,
			String lic_plate_state) throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getInvoiceId(String invoiceNum) throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double recalculateAutochargeAndSave(Long accountId, String userName)
			throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPdfFilepath(String invoiceNum) throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExpressAccount(Long accountId) throws EtccException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getRegExp(String userValue) throws EtccException {
		// TODO Auto-generated method stub
		return null;
	}

}
