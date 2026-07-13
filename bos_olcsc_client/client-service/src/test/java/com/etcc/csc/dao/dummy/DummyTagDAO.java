/*
 * Copyright 2010 Electronic Transaction Consultants
 */

package com.etcc.csc.dao.dummy;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.TagDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;

/**
 * @author Stephen Davidson
 */
public class DummyTagDAO extends TagDAO {

    @Override
    protected AlertDTO[] convertAlertMsgs(Array alerts) throws SQLException {

        //return null;
        throw new UnsupportedOperationException("convertAlertMsgs not implemented yet.");

    }

    @Override
    protected ErrorMessageDTO[] convertErrorMsgs(Array errors) throws SQLException {

        //return null;
        throw new UnsupportedOperationException("convertErrorMsgs not implemented yet.");

    }

    @Override
    protected String loadTagApplicationAgreement() throws EtccException {

        //return null;
        throw new UnsupportedOperationException("loadTagApplicationAgreement not implemented yet.");

    }


	 @Override
    protected String loadAgencyInfo() throws EtccException {

        //return null;
        throw new UnsupportedOperationException("loadagencyinfo not implemented yet.");

    }


    @Override
    protected Collection<TagAuthorityDTO> loadTagAuthorities() throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("loadTagAuthorities not implemented yet.");

    }

    public ResultDTO activateMailedTags(String transactionId) throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("activateMailedTags not implemented yet.");

    }

    public TagDTO addTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
            throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("addTag not implemented yet.");

    }

    public String addTagsReceipt(AccountLoginDTO acctLoginDTO, String reportFormat)
            throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("addTagsReceipt not implemented yet.");

    }

    public TagDTO confirmAddTags(AccountLoginDTO acctLoginDTO, long transactionId, TagDTO.DeliveryMethod deliveryMethod, List<TagDTO> tagDtos) throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("confirmAddTags not implemented yet.");

    }

    public AccountTagsDTO getAccountTags(AccountLoginDTO acctLoginDTO, String searchValue) throws EtccException,
            EtccSecurityException {
        AccountTagsDTO dto = new AccountTagsDTO();
        dto.setAccountTags(getTags(acctLoginDTO.getAcctId()));
        return dto;
    }

    public List<TagDTO> getMailedTags(String transactionId, String driverLicenseNumber, String driverLicenseState,
            String taxId) throws EtccException, EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getMailedTags not implemented yet.");

    }

    public TagDTO getTagBySeqNum(AccountLoginDTO acctLoginDTO, String seqNumber) throws EtccException,
            EtccSecurityException {

        //return null;
        throw new UnsupportedOperationException("getTagBySeqNum not implemented yet.");

    }

    @Override
    protected TagDTO modifyTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, TransactionType transType, Long posId) throws EtccException {
        throw new UnsupportedOperationException("modifyTag not implemented yet.");
    }

    static Collection<TagDTO> getTags(final long acctId){
        Collection<TagDTO> tags = new ArrayList<TagDTO>();
        TagDTO tag = new TagDTO();
        tag.setAcctId(acctId);
        tag.setAcctSuspended(false);
        Calendar expirDate = Calendar.getInstance();
        expirDate.add(Calendar.YEAR, 1);
        tag.setExpireDate(expirDate);
        tags.add(tag);
        return tags;
    }

	public double calculateActivationFee(AccountLoginDTO acctLoginDTO,
			long stickerTagCount, long motocycleCount, long licensePlateTagCount)
			throws EtccException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceReactivateDTO) throws EtccException {
		 throw new UnsupportedOperationException("replaceReactiveTag not implemented yet.");
	}

	public ResultDTO activateMailedTagsSecurely(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String transactionId) throws EtccException, EtccSecurityException {
		return null;
	}

}
