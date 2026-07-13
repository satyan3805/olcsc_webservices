/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;

/**
 * Interface for managing Tags and tag issuing authorities.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
// Based on com.etcc.csc.tagrequest.TagRequestInterface and com.etcc.csc.service.TagAuthorityInterface in OLCSCService module.
@Local
public interface TagInterface extends ServiceInterface {

    /**
     * Add a Tag to the Account. <code>sessionId</code>, <code>ipAddress</code>, <code>userId</code> are used to
     * determine if the user is properly logged in.
     * @param acctLoginDTO the user's account information
     * @param tagRequestDto The Tag to add to the User's account.
     * @param posId
     * @return the tag, with any alerts or exceptions that may have been generated.
     * @throws EtccException If any exceptions occur adding the tag to the database.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public TagDTO addTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
            throws EtccException, EtccSecurityException;

    /**
     * Update an existing tag on the account.
     * @param acctLoginDTO the user's account information
     * @param tagDto The Tag to update
     * @param posId
     * @return the tag, with any alerts or exceptions that may have been generated.
     * @throws EtccException If any exceptions occur adding the tag to the database.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public TagDTO updateTag(AccountLoginDTO acctLoginDTO, TagDTO tagDto, Long posId) throws EtccException, EtccSecurityException;

    /**
     * Delete an existing tag on the account.
     * @param acctLoginDTO the user's account information
     * @param tagDto The Tag to delete
     * @param posId
     * @return the tag, with any alerts or exceptions that may have been generated.
     * @throws EtccException If any exceptions occur adding the tag to the database.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public TagDTO deleteTag(AccountLoginDTO acctLoginDTO, TagDTO tagDto, Long posId) throws EtccException, EtccSecurityException;

    public TagDTO confirmAddTags(AccountLoginDTO acctLoginDTO, long transactionId, TagDTO.DeliveryMethod deliveryMethod, List<TagDTO> vehicleIds) throws EtccException, EtccSecurityException;

    public String addTagsReceipt(AccountLoginDTO acctLoginDTO, String reportFormat)
            throws EtccException, EtccSecurityException;

    /**
     * Gets the tags for an Account.
     * @param acctLoginDTO The account to get the tags for.
     * @param searchValue
     * @return The Tags for the account.
     * @throws EtccException If any exceptions occur adding the tag to the database.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public AccountTagsDTO getAccountTags(AccountLoginDTO acctLoginDTO, String searchValue) throws EtccException,
            EtccSecurityException;

    /**
     * Retrieves a tag by its sequence number.
     * @param acctLoginDTO The account to get the tag for.
     * @param seqNumber the sequence number of the tag.
     * @return the tag matching the supplied sequence number.
     * @throws EtccException If any exceptions occur adding the tag to the database.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public TagDTO getTagBySeqNum(AccountLoginDTO acctLoginDTO, String seqNumber) throws EtccException,
            EtccSecurityException;

    public List<TagDTO> getMailedTags(String transactionId, String driverLicenseNumber, String driverLicenseState,
            String taxId) throws EtccException, EtccSecurityException;

    public ResultDTO activateMailedTags(String transactionId) throws EtccException;

    /**
     * Returns a collection of tag authorities.
     * @return a collection of tag authorities
     */
    public Collection<TagAuthorityDTO> getTagAuthorities() throws EtccException, EtccSecurityException;
    public String getTagApplicationAgreement() throws EtccException;
	public String getAgencyInfo() throws EtccException;

	public double calculateActivationFee(AccountLoginDTO acctLoginDTO,long stickerTagCount, long motocycleCount, long licensePlateTagCount) throws EtccException;
	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceReactivateDTO) throws EtccException;
	
	public ResultDTO activateMailedTagsSecurely(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId,String transactionId) throws EtccException, EtccSecurityException;

}
