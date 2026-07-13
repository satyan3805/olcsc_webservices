/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.service;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

import oracle.j2ee.ejb.StatelessDeployment;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Sensitive;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.MultiVehicleUploadDAO;
import com.etcc.csc.dao.TagDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;
import com.etcc.csc.util.StringUtil;
import com.etcc.csc.validation.TagRequestValidator;

/**
 * TagRequest is the Component that's published for web service tagrequest.ws, which provides the following operations:
 * <ul>
 * <li>addTag
 * <li>addTagsReceipt
 * <li>confirmAddTags
 * <li>getAcctTags
 * <li>getTagBySeqNum
 * <li>updateTag
 * <li>deleteTag
 * <li>getTagApplicationAgreement
 * <li>getTagAuthorities
 * </ul>
 *
 * @author (original) Wade Wang
 * @author (task 488) Stephen Davidson
 * @author (task 488) Milosh Boroyevich
 * @since phase 1
 */
@Stateless(name="com/etcc/Tag")
@StatelessDeployment(transactionTimeout=60)
//Annotated for future compatibility with JSR 181 - these annotations are not yet in use.
//@WebService(name = "Tag", targetNamespace = "http://ws.csc.etcc.com/Tag")
public class Tag implements TagInterface {

    //@WebMethod(operationName = "addTag", action = "urn:addTag")
    //@WebResult(name = "tag")
    public TagDTO addTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
            throws EtccException, EtccSecurityException {
    	//Express Account Changes
    	boolean isExpAccount =new App().isExpressAccount(acctLoginDTO.getAcctId());
        TagRequestValidator validator = new TagRequestValidator();
        validator.validateTag(tagRequestDto,isExpAccount);
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);

        return tagRequestDAO.addTag(acctLoginDTO, tagRequestDto, posId);
    }

    //@WebMethod(operationName = "updateTag", action = "urn:updateTag")
    //@WebResult(name = "tag")
    public TagDTO updateTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        // validation
        TagRequestValidator validator = new TagRequestValidator();
        boolean isExpAccount =new App().isExpressAccount(acctLoginDTO.getAcctId());
        validator.validateTag(tagRequestDto,isExpAccount);
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.updateTag(acctLoginDTO, tagRequestDto, posId);
    }

    //@WebMethod(operationName = "deleteTag", action = "urn:deleteTag")
    //@WebResult(name = "tag")
    public TagDTO deleteTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        // validation
        TagRequestValidator validator = new TagRequestValidator();
        boolean isExpAccount =new App().isExpressAccount(acctLoginDTO.getAcctId());
        validator.validateTag(tagRequestDto,isExpAccount);
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.deleteTag(acctLoginDTO, tagRequestDto, posId);
    }

    //@WebMethod(operationName = "confirmAddTags", action = "urn:confirmAddTags")
    //@WebResult(name = "tag")
    public TagDTO confirmAddTags(AccountLoginDTO acctLoginDTO, long transactionId, TagDTO.DeliveryMethod deliveryMethod,List<TagDTO> tagDtos) throws EtccException, EtccSecurityException {
        // validation
        TagRequestValidator validator = new TagRequestValidator();
        validator.validateConfirmAddTags();

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.confirmAddTags(acctLoginDTO, transactionId, deliveryMethod,tagDtos);
    }

    //@WebMethod(operationName = "addTagsReceipt", action = "urn:addTagsReceipt")
    //@WebResult(name = "tagMessage")
    public String addTagsReceipt(AccountLoginDTO acctLoginDTO, String reportFormat)
            throws EtccException, EtccSecurityException {
        // validation
        TagRequestValidator validator = new TagRequestValidator();
        validator.validateAddTagsReceipt();

        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.addTagsReceipt(acctLoginDTO, reportFormat);
    }

    //@WebMethod(operationName = "getAccountTags", action = "urn:getAccountTags")
    //@WebResult(name = "accountTags")
    public AccountTagsDTO getAccountTags(AccountLoginDTO acctLoginDTO, String searchValue) throws EtccException,
            EtccSecurityException {

        // validation - none needed
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        final AccountTagsDTO accountTags = tagRequestDAO.getAccountTags(acctLoginDTO, searchValue);
        //Get this here, while we still have a connection to the DB open.
        final MultiVehicleUploadDAO multiVehicleDao = daoFactory.getDAO(MultiVehicleUploadDAO.class);
        accountTags.setMultiVehicleAllowed(multiVehicleDao.isMultiVehicleUploadAllowed(acctLoginDTO));
        return accountTags;
    }

    //@WebMethod(operationName = "getTagBySeqNum", action = "urn:getTagBySeqNum")
    //@WebResult(name = "tag")
    public TagDTO getTagBySeqNum(AccountLoginDTO acctLoginDTO, String seqNumber) throws EtccException,
            EtccSecurityException {
        // validation - none needed
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.getTagBySeqNum(acctLoginDTO, seqNumber);
    }

    //@WebMethod(operationName = "getMailedTags", action = "urn:getMailedTags")
    //@WebResult(name = "tagList")
    public List<TagDTO> getMailedTags(String transactionId, String driverLicenseNumber, String driverLicenseState,
            @Sensitive(displayValue="<TaxID>") String taxId) throws EtccException, EtccSecurityException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.getMailedTags(transactionId, driverLicenseNumber, driverLicenseState, taxId);
    }

    //@WebMethod(operationName = "activateMailedTags", action = "urn:activateMailedTags")
    //@WebResult(name = "tagResponse")
    public ResultDTO activateMailedTags(String transactionId) throws EtccException, EtccSecurityException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.activateMailedTags(transactionId);
    }

    //@WebMethod(operationName = "getTagApplicationAgreement", action = "urn:getTagApplicationAgreement")
    //@WebResult(name = "string")
	public String getTagApplicationAgreement() throws EtccException {
	    DAOFactory daoFactory = DAOFactory.getDAOFactory();
	    TagDAO taDao = daoFactory.getDAO(TagDAO.class);
	    return taDao.getTagApplicationAgreement();
	}

	//@WebMethod(operationName = "getAgencyInfo", action = "urn:getAgencyInfo")
    //@WebResult(name = "string")
	public String getAgencyInfo() throws EtccException {
	    DAOFactory daoFactory = DAOFactory.getDAOFactory();
	    TagDAO taDao = daoFactory.getDAO(TagDAO.class);
	    return taDao.getAgencyInfo();
	}


    //@WebMethod(operationName = "getTagAuthorities", action = "urn:getTagAuthorities")
    //@WebResult(name = "tagAuthorities")
	public Collection<TagAuthorityDTO> getTagAuthorities() throws EtccException, EtccSecurityException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
	    TagDAO taDao = daoFactory.getDAO(TagDAO.class);
		return taDao.getTagAuthorities();
	}

	public double calculateActivationFee(AccountLoginDTO acctLoginDTO,
			long stickerTagCount, long motocycleCount, long licensePlateTagCount)
			throws EtccException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
	    TagDAO taDao = daoFactory.getDAO(TagDAO.class);
		return taDao.calculateActivationFee(acctLoginDTO, stickerTagCount, motocycleCount, licensePlateTagCount);
	}
	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceReactivateDTO) throws EtccException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
	    TagDAO taDao = daoFactory.getDAO(TagDAO.class);
		return taDao.replaceOrReactivateTag(tagReplaceReactivateDTO);
	}
	
	public ResultDTO activateMailedTagsSecurely(Long acctId, String acctType, String dbSessionId,
			String ipAddress, String loginId,String transactionId) throws EtccException, EtccSecurityException {
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        return tagRequestDAO.activateMailedTags(transactionId);
    }



}
