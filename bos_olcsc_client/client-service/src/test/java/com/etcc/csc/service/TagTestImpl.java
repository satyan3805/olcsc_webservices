/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.AlertDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;

/**
 * Creates a test implementation of <tt>TagInterface</tt>.
 * @author Milosh Boroyevich
 */
public class TagTestImpl implements TagInterface {
	private static final Logger logger = Logger.getLogger(TagTestImpl.class);

	public ResultDTO activateMailedTags(String transactionId)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public TagDTO addTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException,
			EtccSecurityException {
	    if (tagRequestDto.getLicPlate().equalsIgnoreCase(AccountFactory.LICENSE_PLATE)
	            && tagRequestDto.getLicState().equalsIgnoreCase(AccountFactory.LICENSE_STATE)) {
	        ErrorMessageDTO[] errors = new ErrorMessageDTO[] {
	            new ErrorMessageDTO()
	                .withMessage("Duplicate license plate on account: " + AccountFactory.LICENSE_STATE + '-' + AccountFactory.LICENSE_PLATE)
	        };
            tagRequestDto.setErrors(errors);
	    } else {
	        tagRequestDto.setTagSalesAmt(220.0d);
	        tagRequestDto.setDepositAmt(500.0d);
	        tagRequestDto.setTotalAmt(720.0d);
	        tagRequestDto.setTransactionId(999999999);
	        tagRequestDto.setAcctTagComments("Military Contract Tag");
	    }
        AlertDTO[] alerts = new AlertDTO[] { new AlertDTO(0, "Alerting of something.") };
        tagRequestDto.setAlerts(alerts);
		logger.debug("Mock adding tag: " + tagRequestDto);
		return tagRequestDto;
	}

	public String addTagsReceipt(AccountLoginDTO acctLoginDTO, String reportFormat)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public TagDTO confirmAddTags(AccountLoginDTO acctLoginDTO, long transactionId,
	        TagDTO.DeliveryMethod deliveryMethod, List<TagDTO> tagDtos) throws EtccException, EtccSecurityException {
	    logger.info("confirmAddTags success:" + transactionId + "|" + deliveryMethod);
	    return new TagDTO();
	}

	public AccountTagsDTO getAccountTags(AccountLoginDTO acctLoginDTO,
			String searchValue) throws EtccException, EtccSecurityException {
		if (acctLoginDTO != null)
			return TagFactory.getTags();
		return new AccountTagsDTO();
	}

	public List<TagDTO> getMailedTags(String transactionId,
			String driverLicenseNumber, String driverLicenseState, String taxId)
			throws EtccException, EtccSecurityException {
		logger.debug("No mailed tags.");
		return new ArrayList<TagDTO>();
	}

	public String getTagApplicationAgreement() throws EtccException {
		return "This is the TAG APPLICATION AGREEMENT.";
	}

	public String getAgencyInfo() throws EtccException {
		return "This is the AGENCY INFORMATION.";
	}


	public Collection<TagAuthorityDTO> getTagAuthorities()
			throws EtccException, EtccSecurityException {
		return TagFactory.getTagAuthorities();
	}

	public TagDTO getTagBySeqNum(AccountLoginDTO acctLoginDTO, String seqNumber)
			throws EtccException, EtccSecurityException {
		throw new UnsupportedOperationException();
	}

	public TagDTO updateTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
	        throws EtccException, EtccSecurityException {
	    logger.info("updateTag success: " + tagRequestDto);
	    return tagRequestDto;
	}

	public TagDTO deleteTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
	        throws EtccException, EtccSecurityException {
	    throw new UnsupportedOperationException();
	}

	public double calculateActivationFee(AccountLoginDTO acctLoginDTO,
			long stickerTagCount, long motocycleCount, long licensePlateTagCount)
			throws EtccException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceReactivateDTO) throws EtccException {
		return null;
	}

	public ResultDTO activateMailedTagsSecurely(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String transactionId) throws EtccException, EtccSecurityException {
		return null;
	}

}
