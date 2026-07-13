package com.etcc.csc.webservice.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.TagDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;
import com.etcc.csc.service.App;
import com.etcc.csc.validation.TagRequestValidator;
import com.etcc.csc.webservice.rest.dto.ActivateMailedTagsRequest;
import com.etcc.csc.webservice.rest.dto.CalculateActivationFeeRequest;
import com.etcc.csc.webservice.rest.dto.GetAccountTagsRequest;
import com.etcc.csc.webservice.rest.dto.GetMailedTagsRequest;
import com.etcc.csc.webservice.rest.dto.OracleConfirmTagRequest;
import com.etcc.csc.webservice.rest.dto.OracleTagRequest;

@Path("/oracleTag")
public class OracleTagRest {

	private static final Logger logger = Logger.getLogger(OracleTagRest.class);

	@POST
	@Path("/addTag")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public TagDTO addTag(OracleTagRequest oracleTagRequest) throws EtccException, EtccSecurityException {
		
		final AccountLoginDTO acctLoginDto = oracleTagRequest.getAcctLoginDto();
		final TagDTO tagDTO = oracleTagRequest.getTagDTO();
		final Long posId = oracleTagRequest.getPosId();
		
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] addTagRequest ["+oracleTagRequest+"]");

		//Express Account Changes
    	boolean isExpAccount = new App().isExpressAccount(acctLoginDto.getAcctId());
        TagRequestValidator validator = new TagRequestValidator();
        validator.validateTag(tagDTO,isExpAccount);
        
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        TagDTO addTagResponse = tagRequestDAO.addTag(acctLoginDto, tagDTO, posId);
        
        logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] addTagResponse ["+addTagResponse+"]");
        
        return addTagResponse;
	}
	
	@POST
	@Path("/confirmAddTags")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public TagDTO confirmAddTags(OracleConfirmTagRequest oracleConfirmTagRequest) throws EtccException, EtccSecurityException {
		
		final AccountLoginDTO acctLoginDto = oracleConfirmTagRequest.getAcctLoginDto();
		final long transactionId = oracleConfirmTagRequest.getTransactionId();
		final TagDTO.DeliveryMethod deliveryMethod = oracleConfirmTagRequest.getDeliveryMethod();
		final List<TagDTO> tagDtos = oracleConfirmTagRequest.getTagDtos();
		
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] confirmAddTagsRequest ["+oracleConfirmTagRequest+"]");
		
		// validation
        TagRequestValidator validator = new TagRequestValidator();
        validator.validateConfirmAddTags();

        /*DAOFactory daoFactory = DAOFactory.getDAOFactory();
		OracleTagDAO oracleTagDAO = daoFactory.getDAO(OracleTagDAO.class);*/
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        
        TagDTO confirmAddTags = tagRequestDAO.confirmAddTags(acctLoginDto, transactionId, deliveryMethod, tagDtos);
        
        logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] confirmAddTagsResponse ["+confirmAddTags+"]");
        
        return confirmAddTags;
	}
	
	@POST
	@Path("/updateTag")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public TagDTO updateTag(OracleTagRequest oracleTagRequest) throws EtccException, EtccSecurityException {
		
		final AccountLoginDTO acctLoginDto = oracleTagRequest.getAcctLoginDto();
		final TagDTO tagDTO = oracleTagRequest.getTagDTO();
		final Long posId = oracleTagRequest.getPosId();

		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] updateTagRequest ["+oracleTagRequest+"]");
		
        // validation
        TagRequestValidator validator = new TagRequestValidator();
        boolean isExpAccount =new App().isExpressAccount(acctLoginDto.getAcctId());
        validator.validateTag(tagDTO,isExpAccount);
        
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
        
        TagDTO updateTagResponse = tagRequestDAO.updateTag(acctLoginDto, tagDTO, posId);
        
        logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] updateTagResponse ["+updateTagResponse+"]");
        
        return updateTagResponse;
	}	
	
	@POST
	@Path("/getAccountTags")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountTagsDTO getAccountTags(GetAccountTagsRequest getAccountTagsRequest) throws EtccException, EtccSecurityException {
		
		final AccountLoginDTO acctLoginDto = getAccountTagsRequest.getAcctLoginDto();		
		final String searchValue = getAccountTagsRequest.getSearchValue();
		
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] getAccountTagsRequest ["+getAccountTagsRequest+"]");
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);        
        AccountTagsDTO accountTagsDTO = tagRequestDAO.getAccountTags(acctLoginDto, searchValue);
        
        logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] getAccountTagsResponse ["+accountTagsDTO+"]");
        // Return AccountTagsDTO object which contains both AccountTags and PBP Tags Tags
        return accountTagsDTO;
        
	}
	
	@POST
	@Path("/replaceOrReactivateTag")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceOrReactivateDTO) throws EtccException, EtccSecurityException {
		
		logger.info("AcctId ["+tagReplaceOrReactivateDTO.getAccountId()+"] LoginId ["+tagReplaceOrReactivateDTO.getUserId()+"] replaceOrReactivateTagRequest ["+tagReplaceOrReactivateDTO+"]");
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);        
        ResultDTO replaceOrReactivateTagResponse = tagRequestDAO.replaceOrReactivateTag(tagReplaceOrReactivateDTO);
        
        logger.info("AcctId ["+tagReplaceOrReactivateDTO.getAccountId()+"] LoginId ["+tagReplaceOrReactivateDTO.getUserId()+"] replaceOrReactivateTagResponse ["+tagReplaceOrReactivateDTO+"]");
        
        return replaceOrReactivateTagResponse;
	}
	
	@POST
	@Path("/calculateActivationFee")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public String calculateActivationFee(CalculateActivationFeeRequest calculateActivationFeeRequest) throws EtccException, EtccSecurityException {
		
		final AccountLoginDTO acctLoginDto = calculateActivationFeeRequest.getAcctLoginDto();		
		final long motocycleCount = calculateActivationFeeRequest.getMotocycleCount();
		final long licensePlateTagCount = calculateActivationFeeRequest.getLicensePlateTagCount();
		final long stickerTagCount = calculateActivationFeeRequest.getStickerTagCount();
		
		logger.info("AcctId ["+ acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] calculateActivationFeeRequest ["+calculateActivationFeeRequest+"]");
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
        TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);        
         double finalAmount = tagRequestDAO.calculateActivationFee(acctLoginDto, stickerTagCount, motocycleCount, licensePlateTagCount);
         String finalAmountString = Double.toString(finalAmount);
         
         logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] calculateActivationFeeResponse ["+finalAmountString+"]");
         
         return finalAmountString;
         
	}
	
	@POST
	@Path("/deleteTag")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public TagDTO deleteTag(OracleTagRequest oracleTagRequest) throws EtccException {

		final AccountLoginDTO acctLoginDto = oracleTagRequest.getAcctLoginDto();
		final TagDTO tagDTO = oracleTagRequest.getTagDTO();
		final Long posId = oracleTagRequest.getPosId();
		
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] deleteTagRequest ["+oracleTagRequest+"]");

		// validation
		TagRequestValidator validator = new TagRequestValidator();
		boolean isExpAccount = new App().isExpressAccount(acctLoginDto.getAcctId());
		validator.validateTag(tagDTO, isExpAccount);

		final DAOFactory daoFactory = DAOFactory.getDAOFactory();
		final TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);
		
		final TagDTO deleteTagResponse = tagRequestDAO.deleteTag(acctLoginDto, tagDTO, posId);
		
		logger.info("AcctId ["+acctLoginDto.getAcctId()+"] LoginId ["+acctLoginDto.getLoginId()+"] deleteTagResponse ["+deleteTagResponse+"]");

		return deleteTagResponse;
	}
	
	@POST
	@Path("/activateMailedTags")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO activateMailedTags(ActivateMailedTagsRequest activateMailedTagsRequest) throws EtccException {

		final Long acctId = activateMailedTagsRequest.getAcctId();
		final String acctType =  activateMailedTagsRequest.getLoginType();
		final String dbSessionId = activateMailedTagsRequest.getDbSessionId();
		final String ipAddress = activateMailedTagsRequest.getIpAddress();
		final String loginId = activateMailedTagsRequest.getLoginId();
		final String transactionId = activateMailedTagsRequest.getTransactionId();

		logger.info("AcctId ["+activateMailedTagsRequest.getAcctId()+"] LoginId ["+activateMailedTagsRequest.getLoginId()+"] activateMailedTagsRequest ["+activateMailedTagsRequest+"]");

		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);

		final ResultDTO activateMailedTagsResponse = tagRequestDAO.activateMailedTagsSecurely(acctId, acctType, dbSessionId, ipAddress, loginId, transactionId);

		logger.info("AcctId ["+activateMailedTagsRequest.getAcctId()+"] LoginId ["+activateMailedTagsRequest.getLoginId()+"] activateMailedTagsResponse ["+activateMailedTagsResponse+"]");

		return activateMailedTagsResponse;
	}

	@POST
	@Path("/getMailedTags")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public List<TagDTO> getMailedTags(GetMailedTagsRequest getMailedTagsRequest)
			throws EtccException, EtccSecurityException {

		logger.info("AcctId ["+getMailedTagsRequest.getAcctId()+"] LoginId ["+getMailedTagsRequest.getLoginId()+"] getMailedTagsRequest ["+getMailedTagsRequest+"]");

		final DAOFactory daoFactory = DAOFactory.getDAOFactory();
		final TagDAO tagRequestDAO = daoFactory.getDAO(TagDAO.class);

		final List<TagDTO> mailedTags = tagRequestDAO.getMailedTags(getMailedTagsRequest.getTransactionId(),
				getMailedTagsRequest.getDriverLicenseNumber(), getMailedTagsRequest.getDriverLicenseState(),
				getMailedTagsRequest.getTaxId());

		logger.info("AcctId ["+getMailedTagsRequest.getAcctId()+"] LoginId ["+getMailedTagsRequest.getLoginId()+"] getMailedTagsReseponse ["+mailedTags+"]");

		return mailedTags;
	}

}
