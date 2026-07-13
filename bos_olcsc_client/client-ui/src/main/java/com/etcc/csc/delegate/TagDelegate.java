/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.TagReplaceOrReactivateDTO;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.TagInterface;

public class TagDelegate implements TagInterface {
    private static final Logger logger = Logger.getLogger(TagDelegate.class);

    public TagDTO addTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId)
            throws EtccException, EtccSecurityException {
        logger.trace("**************===========> 41 - came to TagRequestDelegate.addTag()");
        // investigateRegex(tagRequestDto); // keep this for TagValidator quick test
        if (tagRequestDto.getAcctTagSeq() == 0) {
            // To force the addition of this new tag.
            tagRequestDto.setAcctTagSeq(-1);
        }
        TagDTO resultDto = stub().addTag(acctLoginDTO, tagRequestDto, posId);
        logger.trace("**************===========> After stub().addTag");
        return resultDto;
    }

    public TagDTO updateTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        logger.trace("**************===========> 42A - came to TagRequestDelegate.updateTag()");
        return stub().updateTag(acctLoginDTO, tagRequestDto, posId);
    }

    public TagDTO deleteTag(AccountLoginDTO acctLoginDTO, TagDTO tagRequestDto, Long posId) throws EtccException, EtccSecurityException {
        logger.trace("**************===========> 42B - came to TagRequestDelegate.deleteTag()");
        return stub().deleteTag(acctLoginDTO, tagRequestDto, posId);
    }

    public TagDTO confirmAddTags(AccountLoginDTO acctLoginDTO, long transactionId, TagDTO.DeliveryMethod deliveryMethod,List<TagDTO> tagDtos) throws EtccException, EtccSecurityException {
        logger.trace("**************===========> 43 - came to TagRequestDelegate.confirmAddTags()");
        return stub().confirmAddTags(acctLoginDTO, transactionId, deliveryMethod,tagDtos);// Nov 20 Fix
    }

    public String addTagsReceipt(AccountLoginDTO acctLoginDTO, String reportFormat)
            throws EtccException, EtccSecurityException {
        logger.trace("**************===========> 44 - came to TagRequestDelegate.addTagsReceipt()");
        return stub().addTagsReceipt(acctLoginDTO, reportFormat);
    }

    public AccountTagsDTO getAccountTags(AccountLoginDTO acctLoginDTO, String searchString) throws EtccException,
            EtccSecurityException {
        logger.trace("**************===========> 45 - came to TagRequestDelegate.getAccountTags()");
        return stub().getAccountTags(acctLoginDTO, searchString);
    }

    public TagDTO getTagBySeqNum(AccountLoginDTO acctLoginDTO, String seqNumber) throws EtccException,
            EtccSecurityException {
        logger.trace("**************===========> 46 - came to TagRequestDelegate.getTagBySeqNum()");
        return stub().getTagBySeqNum(acctLoginDTO, seqNumber);
    }

    public List<TagDTO> getMailedTags(String transactionId, String driverLicenseNumber, String driverLicenseState,
            String taxId) throws EtccException, EtccSecurityException {
        return stub().getMailedTags(transactionId, driverLicenseNumber, driverLicenseState, taxId);
    }

    public ResultDTO activateMailedTags(String transactionId) throws EtccException, EtccSecurityException {
        return stub().activateMailedTags(transactionId);
    }

    public Collection<TagAuthorityDTO> getTagAuthorities() throws EtccException, EtccSecurityException {
        return stub().getTagAuthorities();
    }

    public String getTagApplicationAgreement() throws EtccException {
	    logger.trace("**************===========> 47 - came to TagRequestDelegate.getTagApplicationAgreement()");
        return stub().getTagApplicationAgreement();
    }

	public String getAgencyInfo() throws EtccException {
	    logger.trace("**************===========> 48 - came to TagRequestDelegate.getAgencyInfo()");
        return stub().getAgencyInfo();
    }


    public double calculateActivationFee(AccountLoginDTO acctLoginDTO,
			long stickerTagCount, long motocycleCount, long licensePlateTagCount)
			throws EtccException {
		// TODO Auto-generated method stub
		return stub().calculateActivationFee(acctLoginDTO,stickerTagCount,motocycleCount,licensePlateTagCount);

	}

	private TagInterface stub() {
        return ServiceFactory.getImplementation(TagInterface.class);
    }

	public ResultDTO replaceOrReactivateTag(TagReplaceOrReactivateDTO tagReplaceReactivateDTO) throws EtccException {
		return stub().replaceOrReactivateTag(tagReplaceReactivateDTO);
	}
 /*
       public static void main(String[] args) throws EtccException,EtccSecurityException{
           String dateStr = "12/01/2007 11:1130";
           String dateStrend = "12/4/2007 11:1130";
           TagDTO tagRequestDto = new TagDTO();
           tagRequestDto.setPbpStart(dateStr);
           tagRequestDto.setPbpEnd(dateStrend);
           TagRequestDelegate dele = new TagRequestDelegate();
           String sessionId = "123123fsdf";
           String ipAddress="12.2.2.2";
           String userId = "yeueu12123";

           Long posId = null;
           try{

              dele.addTag(sessionId, ipAddress, userId, tagRequestDto, posId);
              //dele.addTag("fwfw123","120.202.20.2","yua0293",tagRequestDto, 1);


               System.out.println("result ");

           }catch(EtccException ex){
               System.out.println("exception"+ex);
               throw ex;
           }
       }
*/

	public ResultDTO activateMailedTagsSecurely(Long acctId, String acctType, String dbSessionId, String ipAddress,
			String loginId, String transactionId) throws EtccException, EtccSecurityException {
		return stub().activateMailedTagsSecurely(acctId, acctType, dbSessionId, ipAddress, loginId, transactionId);
	}

/*
    private void investigateRegex(TagDTO tagRequestDto) throws EtccException {
        com.etcc.csc.tagrequest.types.TagDTO tag = convert(tagRequestDto);
        if (isEmpty (tag.getLicState()))
            throw new EtccException("Invalid Tag Information. LicState is a required field");
        if (!isEmpty (tag.getLicPlate()) && (!tag.getLicPlate().matches("^[a-zA-Z0-9]{1,15}$")))
            throw new EtccException("Invalid Tag Information. Incorrect value for LicPlate");
        if ((tag.getTemporaryLicPlate() != null && tag.getTemporaryLicPlate() == true) || !isEmpty (tag.getLicPlate()))
            ; // fine
        else
            throw new EtccException("Invalid Tag Information. License plate Number is required when it is not a Temporary License Plate");
        if (isEmpty (tag.getVehicleYear()) ||  !isLong (tag.getVehicleYear()) || (tag.getVehicleYear().length() != 4))
                throw new EtccException("Invalid Tag Information. VehicleYear should be 4 digits");
        if (!isMaxLength(tag.getVehicleColor(), 20, true)) throw new EtccException("Invalid Tag Information. VehicleColor is a required field");

        if (isEmpty (tag.getVehicleMake()) || (!tag.getVehicleMake().matches("^[0-9a-zA-Z\\s,#.\\-]*$")))
                        throw new EtccException("Invalid Tag Information. Incorrect value for VehicleMake");
        if (!isMaxLength(tag.getVehicleModel(), 30, true) ||  (!tag.getVehicleModel().matches("^[0-9a-zA-Z\\s,#.\\-]*$")))
                throw new EtccException("Invalid Tag Information. Incorrect value for VehicleModel");
        if (!isEmpty (tag.getNickname()) && (!isMaxLength(tag.getNickname(), 30, false) || !tag.getNickname().matches("^[0-9a-zA-Z\\s,#.\\-\']*$")))
                throw new EtccException("Invalid Tag Information. Incorrect value for the optional field Nickname");
        //if (isEmpty  (tag.getPbpStart()))     throw new EtccException("Invalid Tag Information. PbpStart is a required field");

        if (!isEmpty (tag.getNickname()) && (!isMaxLength(tag.getNickname(), 30, false) ||  !tag.getNickname().matches("^[0-9a-zA-Z ,#.\\-\']*$")))
                throw new EtccException("Invalid Tag Information. Incorrect value for the optional field Nickname");
        if(tag.getTagTypeCode().equals("P")){
            if (isDate(tag.getPbpStart()) == false)
                throw new EtccException("Invalid Tag Information. PbpStart date format must be MM/dd/yyyy HH:mm:ss");
            if (isDate(tag.getPbpEnd()) == false)
                throw new EtccException("Invalid Tag Information. PbpEnd date format must be MM/dd/yyyy HH:mm:ss");
        }
    }
*/

}
