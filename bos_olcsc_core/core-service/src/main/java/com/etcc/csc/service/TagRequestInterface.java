package com.etcc.csc.service;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;

public interface TagRequestInterface extends BusinessObjectInterface {
	public TagDTO addTag(String sessionId, String ipAddress, String userId,
			TagDTO tagRequestDto, Long posId) throws EtccException,
			EtccSecurityException;

	public TagDTO modifyTag(String sessionId, String ipAddress, String userId,
			TagDTO tagRequestDto, String transType, Long posId)
			throws EtccException, EtccSecurityException;

	public boolean confirmAddTags(String sessionId, String ipAddress,
			String userId, String acctId, long transactionId)
			throws EtccException, EtccSecurityException;

	public boolean confirmAddTagsTwo(String sessionId, String ipAddress,
			String userId, String acctId, long transactionId)
			throws EtccException, EtccSecurityException;

	public String addTagsReceipt(String sessionId, String ipAddress,
			String userId, String acctId, String reportFormat)
			throws EtccException, EtccSecurityException;

	public int activateTag(AccountLoginDTO loginDto, TagDTO tagRequestDTO)
			throws EtccException, EtccSecurityException;
}
