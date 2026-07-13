package com.etcc.csc.service;

import java.util.Collection;

import com.etcc.csc.common.BusinessObjectInterface;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.TagDTO;

/**
 * Contains methods for managing vehicle-related info.
 */
public interface VehicleInterface extends BusinessObjectInterface {
	/**
	 * Retrieves a list of vehicle classes.
	 * 
	 * @return A collection of VehicleClassDTO.
	 * @throws EtccException
	 */
	public Collection getVehicleClasses(String lang) throws EtccException;

	public Collection getLicPlateTypes(String lang) throws EtccException;;

	public String[] getValidationMessages(AccountLoginDTO accountLoginDTO,
			TagDTO tag, long eventId, String action) throws EtccException;

	public TagDTO[] saveVehicleInfo(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, String action) throws EtccSecurityException,
			EtccException;

	public String[] deleteVehicle(AccountLoginDTO acctLoginDto, TagDTO tagDTO)
			throws EtccSecurityException, EtccException;

	public boolean findExistingLicPlateNbr(String licPlate, Long accountId)
			throws EtccException;

	public String calculateAutochargeAmount(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException;
}
