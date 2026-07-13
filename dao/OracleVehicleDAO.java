package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.lang.StringUtils;

import com.etcc.csc.common.DelegateEnum;
import com.etcc.csc.common.DelegateFactory;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Logger;
import com.etcc.csc.common.StringUtil;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagInfoDTO;
import com.etcc.csc.dto.AccountTagInfoHolderDTO;
import com.etcc.csc.dto.AccountVehicleInfoDTO;
import com.etcc.csc.dto.AccountVehicleInfoHolderDTO;
import com.etcc.csc.dto.DMVInfoHolder;
import com.etcc.csc.dto.DMVVehicleOwnerDTO;
import com.etcc.csc.dto.LicensePlateTypeDTO;
import com.etcc.csc.dto.TagDTO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.enums.EventEnum;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcAccountVehRec;
import com.etcc.csc.plsql.OlcVehicleClassesRec;
import com.etcc.csc.plsql.OlcVehiclePlateTypesRec;
import com.etcc.csc.plsql.StringArray;
import com.etcc.csc.service.CountryInterface;

public class OracleVehicleDAO extends VehicleDAO {
	private static final String PROTECTED_ACCOUNT = "132";
	private Logger logger = Logger.getLogger(OracleVehicleDAO.class);

	public OracleVehicleDAO() {
	}

	public Collection getVehicleClasses(String lang) throws EtccException {
		logger.info("Entering getVehicleClasses() @ OracleVehicleDAO");
		Collection result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getVehicleClasses() :: Calling Olcsc_util.GET_VEHICLE_CLASSES");
			cstmt = conn.prepareCall("{? = call "
					+ "Olcsc_util.GET_VEHICLE_CLASSES(?,?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			typeMap.put("OL_OWNER.OLC_VEHICLE_CLASSES_REC",
					OlcVehicleClassesRec.class);
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, "EN");
			cstmt.setString(3, lang);
			cstmt.registerOutParameter(4, Types.ARRAY,
					"OL_OWNER.OLC_VEHICLE_CLASSES_ARR");
			cstmt.registerOutParameter(5, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			Object[] objArray = (Object[]) cstmt.getArray(4).getArray();
			if (objArray != null && objArray.length > 0) {
				logger.debug("Inside if (objArray != null && objArray.length > 0) @ getVehicleClasses()");
				result = new ArrayList();
				for (int i = 0; i < objArray.length; i++) {
					VehicleClassDTO vcDto = new VehicleClassDTO();
					OlcVehicleClassesRec temp = (OlcVehicleClassesRec) objArray[i];
					vcDto.setDefaultValueFlag(Util.stringToBoolean(temp
							.getDefaultValueFlag()));
					vcDto.setVehicleClassCode(temp.getVehicleClassCode() == null ? ""
							: temp.getVehicleClassCode().toString());
					vcDto.setVehicleClassDescr(temp.getVehicleClassDescr());
					vcDto.setVehicleClassLongDescr(temp
							.getVehicleClassLongDescr());
					try {
						vcDto.setVehicleClassOrder(temp.getVehicleClassOrder()
								.shortValueExact());
					} catch (Exception ex) {
						logger.error(
								"Error in getVehicleClasses() @ OracleVehicleDAO: "
										+ ex, ex);
					}
					result.add(vcDto);
				}// end of for (int i = 0; i < objArray.length; i++)
			}// end of if (objArray != null && objArray.length > 0)
		} catch (SQLException sqle) {
			logger.error("Error in getVehicleClasses() @ OracleVehicleDAO: "
					+ sqle, sqle);
			throw new EtccException("Error running getVehicleClasses: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
		logger.info("Leaving getVehicleClasses() @ OracleVehicleDAO");
		return result;
	}// end of getVehicleClasses()

	public Collection getLicPlateTypes(String lang) throws EtccException {
		logger.info("Entering getLicPlateTypes() @ OracleVehicleDAO");
		ArrayList result = null;
		try {
			/*
			 * FUNCTION GET_Lic_plate_types(P_VEHICLE_LICPLATETYPES OUT
			 * OLC_VEHICLE_PLATE_TYPES_ARR, P_ERROR_ARR OUT OLC_ERROR_MSG_ARR)
			 * RETURN NUMBER; 04/21 Joemon: This procedure is not existing as of
			 * now , please remove comments for the type after its done.
			 */
			setConnection(Util.getDbConnection());
			logger.info("getLicPlateTypes() :: Calling OLCSC_UTIL.GET_LIC_PLATE_TYPES");
			cstmt = conn.prepareCall("{? = call "
					+ "OLCSC_UTIL.GET_LIC_PLATE_TYPES(?, ?, ?, ?, ?)}");

			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			typeMap.put("OL_OWNER.OLC_VEHICLE_PLATE_TYPES_REC",
					OlcVehiclePlateTypesRec.class);
			conn.setTypeMap(typeMap);

			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, "EN");
			logger.debug("param " + 2 + "= EN");
			cstmt.setString(3, lang);
			logger.debug("param " + 3 + "= " + lang);
			cstmt.registerOutParameter(4, Types.ARRAY,
					"OL_OWNER.OLC_VEHICLE_PLATE_TYPES_ARR");
			cstmt.registerOutParameter(5, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setLong(6, EventEnum.GET_VELCRO_REQUEST.getId());
			cstmt.execute();

			Array array = (Array) cstmt.getObject(4);
			Object[] objArray = (Object[]) array.getArray();

			if (objArray != null && objArray.length > 0) {
				logger.debug("Inside if (objArray != null && objArray.length > 0) @ getLicPlateTypes()");
				result = new ArrayList();
				LicensePlateTypeDTO defaultLicPlateType = null;
				for (int i = 0; i < objArray.length; i++) {
					OlcVehiclePlateTypesRec temp = (OlcVehiclePlateTypesRec) objArray[i];
					if ("Y".equalsIgnoreCase(temp.getDefaultFlag())) {
						logger.debug("Inside if (\"Y\".equalsIgnoreCase(temp.getDefaultFlag())) @ getLicPlateTypes()");
						// Default license plate type
						defaultLicPlateType = new LicensePlateTypeDTO();
						defaultLicPlateType.setLicPlateCode(temp
								.getPlateTypeCode().toString());
						defaultLicPlateType.setPlateTypeDescr(temp
								.getPlateTypeDescr());
					} else {
						logger.debug("Inside else of if (\"Y\".equalsIgnoreCase(temp.getDefaultFlag())) @ getLicPlateTypes()");
						LicensePlateTypeDTO vcDto = new LicensePlateTypeDTO();
						vcDto.setLicPlateCode(temp.getPlateTypeCode()
								.toString());
						vcDto.setPlateTypeDescr(temp.getPlateTypeDescr());
						result.add(vcDto);
					}
				}

				// Set the default lic plate type at the 0th index
				if (defaultLicPlateType != null) {
					logger.debug("Inside if (defaultLicPlateType != null) @ getLicPlateTypes()");
					result.add(0, defaultLicPlateType);
				}
			}
		} catch (SQLException sqle) {
			logger.error("Error in getLicPlateTypes() @ OracleVehicleDAO: "
					+ sqle, sqle);
			throw new EtccException("Error running getLicPlateTypes: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
		logger.info("Leaving getLicPlateTypes() @ OracleVehicleDAO");
		return result;
	}// end of getLicPlateTypes()

	public String[] getValidationMessages(AccountLoginDTO accountLoginDTO,
			TagDTO tag, long eventId, String action) throws EtccException {
		logger.info("Entering getValidationMessages() @ OracleVehicleDAO");
		try {
			setConnection(Util.getDbConnection());
			String resultArray[] = new String[10];
			List<AccountVehicleInfoDTO> acctVehInfoLst = new ArrayList<AccountVehicleInfoDTO>();
			List<AccountTagInfoDTO> acctTagInfoLst = new ArrayList<AccountTagInfoDTO>();
			List<DMVVehicleOwnerDTO> dmvList = new ArrayList<DMVVehicleOwnerDTO>();
			Timestamp tmp = null;
			logger.info("getValidationMessages() :: Calling Olcsc_acct_mgmt.Ini_Val_Get_Dmv_Trn_Cnt_Amt");
			cstmt = conn
					.prepareCall("{? = call "
							+ "Olcsc_acct_mgmt.Ini_Val_Get_Dmv_Trn_Cnt_Amt(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,?,?,?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			typeMap.put("RITE_COMMON.STRING_ARRAY", StringArray.class);
			conn.setTypeMap(typeMap);

			byte idx = 1;
			int result = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);

			logger.debug("param " + idx + "= " + accountLoginDTO.getAcctId());
			cstmt.setLong(idx++, accountLoginDTO.getAcctId());

			logger.debug("param " + idx + "= "
					+ accountLoginDTO.getDbSessionId());
			cstmt.setString(idx++, accountLoginDTO.getDbSessionId());
			logger.debug("param " + idx + "= "
					+ accountLoginDTO.getLastLoginIp());
			cstmt.setString(idx++, accountLoginDTO.getLastLoginIp());
			logger.debug("param " + idx + "= " + accountLoginDTO.getLoginId());
			cstmt.setString(idx++, accountLoginDTO.getLoginId());
			logger.debug("param " + idx + "= " + tag.getLicPlate());
			cstmt.setString(idx++, tag.getLicPlate());
			logger.debug("param " + idx + "= " + tag.getLicState());
			cstmt.setString(idx++, tag.getLicState());
			CountryInterface cntry = (CountryInterface) DelegateFactory
					.create(DelegateEnum.COUNTRY_DELEGATE);
			String defaultCountryCode = cntry.getDefaultCountryCode();
			logger.debug("param " + idx + "= " + defaultCountryCode);
			cstmt.setString(idx++, defaultCountryCode);
			logger.debug("param " + idx + "= " + tag.getPlateTypeCode());
			cstmt.setString(idx++, tag.getPlateTypeCode());
			logger.debug("param " + idx + "= " + tag.getResponsibilityCode());
			cstmt.setString(idx++, tag.getResponsibilityCode());
			// Pass null here or else dmv info will not be retrieved
			logger.debug("param " + idx + "= NULL");
			cstmt.setNull(idx++, Types.INTEGER);

			if (tag.getEffectiveDate() != null) {
				logger.debug("Inside if (tag.getEffectiveDate() != null) @ getValidationMessages()");
				if (tag.getAccountVehicleId() > 0l
						&& StringUtils.isNotBlank(tag.getAgencyId())
						&& StringUtils.isNotBlank(tag.getTagId())) {
					logger.debug("Inside if (tag.getAccountVehicleId() > 0l && StringUtils.isNotBlank(tag.getAgencyId()) && StringUtils.isNotBlank(tag.getTagId()))  @ getValidationMessages()");
					// this activate tag for an existing vehicle scenario
					tmp = new Timestamp(System.currentTimeMillis());
				} else {
					logger.debug("Inside else of if (tag.getAccountVehicleId() > 0l && StringUtils.isNotBlank(tag.getAgencyId()) && StringUtils.isNotBlank(tag.getTagId()))  @ getValidationMessages()");
					tmp = new Timestamp(tag.getEffectiveDate()
							.getTimeInMillis());
				}
			}

			logger.debug("param " + idx + "= " + tmp);
			cstmt.setTimestamp(idx++, tmp);

			if (tag.getExpirDate() != null) {
				logger.debug("Inside if (tag.getExpirDate() != null) @ getValidationMessages()");
				tmp = new Timestamp(tag.getExpirDate().getTimeInMillis());
				logger.debug("param " + idx + "= " + tmp);
				cstmt.setTimestamp(idx++, tmp);
			} else {
				logger.debug("Inside else of if (tag.getExpirDate() != null) @ getValidationMessages()");
				logger.debug("param " + idx + "= NULL");
				cstmt.setNull(idx++, Types.TIMESTAMP);
			}

			logger.debug("param " + idx + "= " + tag.getVinNumber());
			cstmt.setString(idx++, tag.getVinNumber());
			logger.debug("param " + idx + "= " + tag.getAccountVehicleId());
			cstmt.setLong(idx++, tag.getAccountVehicleId());
			logger.debug("param " + idx + "= " + eventId);
			cstmt.setLong(idx++, eventId);
			if (!StringUtils.isBlank(tag.getAgencyId())
					&& !StringUtils.isBlank(tag.getTagId())) {
				logger.debug("Inside if (!StringUtils.isBlank(tag.getAgencyId()) && !StringUtils.isBlank(tag.getTagId())) @ getValidationMessages()");
				logger.debug("param " + idx + "= " + tag.getTagId());
				cstmt.setString(idx++, tag.getTagId());
				logger.debug("param " + idx + "= " + tag.getAgencyId());
				cstmt.setString(idx++, tag.getAgencyId());
			} else {
				logger.debug("Inside else of if (!StringUtils.isBlank(tag.getAgencyId()) && !StringUtils.isBlank(tag.getTagId())) @ getValidationMessages()");
				logger.debug("param " + idx + "= " + "NULL");
				cstmt.setNull(idx++, Types.VARCHAR);
				logger.debug("param " + idx + "= " + "NULL");
				cstmt.setNull(idx++, Types.VARCHAR);
			}// end of if()

			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			result = cstmt.getInt(1);
			if (result == 1) {
				logger.debug("Inside if (result == 1) @ getValidationMessages()");
				idx--;
				logger.debug("Getting tag info count at index : " + idx);
				Array tagCntArray = cstmt.getArray(idx--);

				logger.debug("Getting transaction count at index : " + idx);
				Array txnCntArray = cstmt.getArray(idx--);

				logger.debug("Getting dmvArray count at index : " + idx);
				Array dmvArray = cstmt.getArray(idx--);

				Object objTags = tagCntArray == null ? null : tagCntArray
						.getArray();
				if (objTags != null && objTags.getClass().isArray()) {
					logger.debug("Inside if (objTags != null && objTags.getClass().isArray()) @ getValidationMessages()");
					String[] objArray = (String[]) objTags;
					for (String aRecord : objArray) {
						logger.debug("Tag information:" + aRecord);
						AccountTagInfoHolderDTO arrayHolder = Util.readXMLData(
								aRecord, AccountTagInfoHolderDTO.class);
						if (arrayHolder != null
								&& arrayHolder.getAcctTagRecord() != null
								&& arrayHolder.getAcctTagRecord().length > 0) {
							logger.debug("Inside if (arrayHolder != null && arrayHolder.getAcctTagRecord() != null && arrayHolder.getAcctTagRecord().length > 0) @ getValidationMessages()");
							for (AccountTagInfoDTO singleRow : arrayHolder
									.getAcctTagRecord()) {
								if (singleRow != null) {
									logger.debug("Inside if (singleRow != null) != null) @ getValidationMessages()");
									acctTagInfoLst.add(singleRow);
								}
							}
						}
					}
				}

				Object obj = txnCntArray.getArray();
				if (obj != null && obj.getClass().isArray()) {
					logger.debug("Inside if (obj != null && obj.getClass().isArray())) @ getValidationMessages()");
					String[] objArray = (String[]) obj;
					for (String aRecord : objArray) {
						logger.debug("Vehicle information" + aRecord);
						AccountVehicleInfoHolderDTO arrayHolder = Util
								.readXMLData(aRecord,
										AccountVehicleInfoHolderDTO.class);
						if (arrayHolder != null
								&& arrayHolder.getAcctvehRecord() != null
								&& arrayHolder.getAcctvehRecord().length > 0) {
							logger.debug("Inside if (arrayHolder != null && arrayHolder.getAcctvehRecord() != null && arrayHolder.getAcctvehRecord().length > 0) @ getValidationMessages()");
							for (AccountVehicleInfoDTO singleRow : arrayHolder
									.getAcctvehRecord()) {
								if (singleRow != null) {
									logger.debug("Inside if (singleRow != null) @ getValidationMessages()");
									acctVehInfoLst.add(singleRow);
								}
							}
						}
					}
				}

				Object obj1 = dmvArray.getArray();
				if (obj1 != null && obj1.getClass().isArray()) {
					logger.debug("Inside if (obj1 != null && obj1.getClass().isArray()) @ getValidationMessages()");
					String[] StrArray = (String[]) obj1;
					for (String aRecord : StrArray) {
						logger.debug("DMV information" + aRecord);
						DMVInfoHolder arrayHolder = Util.readXMLData(aRecord,
								DMVInfoHolder.class);
						if (arrayHolder != null
								&& arrayHolder.getDmvvehRecord() != null
								&& arrayHolder.getDmvvehRecord().length > 0) {
							logger.debug("Inside if (arrayHolder != null && arrayHolder.getDmvvehRecord() != null && arrayHolder.getDmvvehRecord().length > 0) @ getValidationMessages()");
							for (DMVVehicleOwnerDTO singleRow : arrayHolder
									.getDmvvehRecord()) {
								if (singleRow != null) {
									logger.debug("Inside if (singleRow != null) @ getValidationMessages()");
									dmvList.add(singleRow);
								}
							}
						}
					}
				}

				return Util.processVehMessages(acctVehInfoLst, acctTagInfoLst,
						dmvList, accountLoginDTO.getAcctId(), action);
			} else {
				logger.debug("Inside else of if (result == 1) @ getValidationMessages()");
				Array errors = (Array) cstmt.getObject(idx);
				Object array[] = (Object[]) errors.getArray();
				String errorMessage = "";
				if (array != null && array.length >= 0) {
					logger.debug("Inside if (array != null && array.length >= 0) @ getValidationMessages()");
					for (int i = 0; i < array.length; i++) {
						ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
						logger.error(msgRec.getErrMsg());
						errorMessage = msgRec.getErrMsg();
						if (!StringUtils.isBlank(errorMessage)) {
							logger.debug("Inside if (!StringUtils.isBlank(errorMessage)) @ getValidationMessages()");
							break;
						}
					}
				}
				if (StringUtils.isBlank(errorMessage)) {
					logger.debug("Inside if (StringUtils.isBlank(errorMessage)) @ getValidationMessages()");
					errorMessage = "Could not process request, please try again.";
				}

				String[] returnArr = new String[17];
				returnArr[0] = "N";
				returnArr[1] = errorMessage;
				//M6378: transfer Vehicles/Tags
				returnArr[15] = "";
				returnArr[16] = "";
				logger.info("Leaving getValidationMessages() @ OracleVehicleDAO");
				return returnArr;
			}// end of if-else()
		} catch (SQLException sqle) {
			logger.error(
					"Error in getValidationMessages() @ OracleVehicleDAO: "
							+ sqle, sqle);
			throw new EtccException("getValidationMessages: " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
	}// end of getValidationMessages()

	public TagDTO[] saveVehicleInfo(AccountLoginDTO acctLoginDto,
			TagDTO[] tags, String action) throws EtccSecurityException,
			EtccException {
		logger.info("Entering saveVehicleInfo() @ OracleVehicleDAO");
		String result = null;
		try {
			logger.debug("Calling  Olcsc_acct_mgmt.Maintain_Acct_Veh_Tag");
			setConnection(Util.getDbConnection());
			logger.info("saveVehicleInfo() :: Calling Olcsc_acct_mgmt.Maintain_Acct_Veh_Tag");
			cstmt = conn
					.prepareCall("{? = call Olcsc_acct_mgmt.Maintain_Acct_Veh_Tag (?,?,?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ACCOUNT_VEH_REC", OlcAccountVehRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_ACCOUNT_VEH_ARR", conn);
			ARRAY strArr = new ARRAY(arraydesc, conn,
					convertTagDTOToAcctVehRct(tags, action));

			cstmt.registerOutParameter(1, Types.SMALLINT);

			logger.debug("param 2= " + acctLoginDto.getAcctId());
			cstmt.setLong(2, acctLoginDto.getAcctId());

			logger.debug("param 3= " + acctLoginDto.getDbSessionId());
			cstmt.setString(3, acctLoginDto.getDbSessionId());

			logger.debug("param 4= " + acctLoginDto.getLastLoginIp());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());

			logger.debug("param 5= " + acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLoginId());
			cstmt.setString(6, action);

			logger.debug("param 7= OLC_ACCOUNT_VEH_ARR");
			cstmt.setArray(7, strArr);
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ACCOUNT_VEH_ARR");

			if (action.equals("E") || action.equals("N")) {
				logger.debug("Inside if (action.equals(\"E\") || action.equals(\"N\")) @ saveVehicleInfo()");
				logger.debug("param 8= " + EventEnum.EDITVEHICLE.getId());
				cstmt.setLong(8, EventEnum.EDITVEHICLE.getId());
			} else {
				logger.debug("Inside else of if (action.equals(\"E\") || action.equals(\"N\")) @ saveVehicleInfo()");
				logger.debug("param 8= " + EventEnum.OLCSC_ADD_VEH.getId());
				cstmt.setLong(8, EventEnum.OLCSC_ADD_VEH.getId());
			}

			cstmt.registerOutParameter(9, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");

			cstmt.execute();

			short success = cstmt.getShort(1);
			logger.debug("success= " + success);
			if (success == 1) {
				logger.debug("Inside if (success == 1) @ saveVehicleInfo()");
				Array returnedArr = cstmt.getArray(7);
				Object[] objArray = (Object[]) returnedArr.getArray();

				if (objArray != null && objArray.length > 0) {
					logger.debug("Inside if (objArray != null && objArray.length > 0) @ saveVehicleInfo()");
					for (int i = 0; i < objArray.length; i++) {
						OlcAccountVehRec temp = (OlcAccountVehRec) objArray[i];
						logger.debug("Returned vehicle id: "
								+ temp.getAcctVehId() + ", returned tag id : "
								+ temp.getAcctTagId());
						logger.debug("Returned tag id: " + temp.getTagId()
								+ ", returned agency id : "
								+ temp.getAgencyId());
						TagDTO dtoTemp = tags[i];
						if (temp.getAcctVehId() != null) {
							logger.debug("Inside if (temp.getAcctVehId() != null) @ saveVehicleInfo()");
							dtoTemp.setAccountVehicleId(temp.getAcctVehId()
									.longValue());
						}
						if (temp.getAcctTagId() != null) {
							logger.debug("Inside if (temp.getAcctTagId() != null) @ saveVehicleInfo()");
							dtoTemp.setAcctTagSeq(temp.getAcctTagId()
									.longValue());
						}
						dtoTemp.setTagId(temp.getTagId());
						dtoTemp.setAgencyId(temp.getAgencyId());
					}
				}
			} else if (success == -1) {
				logger.debug("Inside if (success == -1) @ saveVehicleInfo()");
				logger.error("Error in saveVehicleInfo() @ OracleVehicleDAO :: Security Exception in saveVehicleInfo()");
				throw new EtccSecurityException(
						"Security exception in saveVehicleInfo");
			} else if (success == 0) {
				logger.debug("Inside if (success == 0) @ saveVehicleInfo()");
				// Populate the error message to the first entry in the TagsDTO
				// array
				TagDTO dtoTemp = tags[0];
				Array errors = (Array) cstmt.getObject(9);
				Collection coll = Util.convertErrorMsgs(errors);
				if (coll == null || coll.size() == 0) {
					logger.debug("Inside if (coll == null || coll.size() == 0) @ saveVehicleInfo()");
					coll = new ArrayList();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto
							.setMessage("Could not process request, please try again.");
					coll.add(errMsgDto);
				}
				dtoTemp.setErrors(coll);
			}

		} catch (SQLException sqle) {
			logger.error("Error in saveVehicleInfo() @ OracleVehicleDAO: "
					+ sqle, sqle);
			throw new EtccException("Error running saveVehicleInfo: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
		logger.info("Leaving saveVehicleInfo() @ OracleVehicleDAO");
		return tags;
	}// end of saveVehicleInfo()

	private OlcAccountVehRec[] convertTagDTOToAcctVehRct(TagDTO[] tags,
			String action) throws EtccException {
		logger.info("Entering convertTagDTOToAcctVehRct() @ OracleVehicleDAO");

		OlcAccountVehRec[] array = null;
		try {
			if (tags != null) {
				logger.debug("Inside if (tags != null) @ convertTagDTOToAcctVehRct()");
				array = new OlcAccountVehRec[tags.length];
				Collection tempList = new ArrayList();
				for (int i = 0; i < tags.length; i++) {
					OlcAccountVehRec temp = new OlcAccountVehRec();
					TagDTO tagDto = tags[i];

					temp.setAcctTagId(tagDto.getAcctTagSeq() <= 0 ? null
							: new BigDecimal(tagDto.getAcctTagSeq()));
					temp.setAcctVehId(tagDto.getAccountVehicleId() <= 0 ? null
							: new BigDecimal(tagDto.getAccountVehicleId()));

					temp.setTransferFlag(StringUtils.isBlank(tagDto
							.getTagTransferFlag()) ? "N" : tagDto
							.getTagTransferFlag());
					if ("T".equalsIgnoreCase(tagDto.getTagTransferFlag())) {
						logger.debug("Inside if (\"T\".equalsIgnoreCase(tagDto.getTagTransferFlag())) @ convertTagDTOToAcctVehRct()");
						temp.setAddTagInd("N");
						logger.debug("Inside if (!StringUtils.isBlank(tagDto.getAgencyId()) && !StringUtils.isBlank(tagDto.getTagId())) @ convertTagDTOToAcctVehRct()");
						if(StringUtil.isNotBlankOrNull(tagDto.getAgencyId())){
							temp.setAgencyId(tagDto.getAgencyId());
						}
					    if(StringUtil.isNotBlankOrNull(tagDto.getTagId())) {
							temp.setTagId(tagDto.getTagId());
						}
					} else if ("Y"
							.equalsIgnoreCase(tagDto.getTagTransferFlag())) {
						logger.debug("Inside else if (\"Y\".equalsIgnoreCase(tagDto.getTagTransferFlag())) @ convertTagDTOToAcctVehRct()");
						temp.setAddTagInd("N");
					} else {
						logger.debug("Inside else of if (\"T\".equalsIgnoreCase(tagDto.getTagTransferFlag())) @ convertTagDTOToAcctVehRct()");
						// tag request to existing vehicle defect
						temp.setAddTagInd("Y");
						if (action.equalsIgnoreCase("EDIT")
								&& (!StringUtils.isBlank(tagDto.getAgencyId()) && !StringUtils
										.isBlank(tagDto.getTagId()))) {
							logger.debug("Inside if (action.equalsIgnoreCase(\"EDIT\") && (!StringUtils.isBlank(tagDto.getAgencyId()) && !StringUtils.isBlank(tagDto.getTagId()))) @ convertTagDTOToAcctVehRct()");
							temp.setAddTagInd("Y");
						} else if ("N".equalsIgnoreCase(tagDto.getTagExists())) {// Tag
																					// request
							logger.debug("Inside else if (\"N\".equalsIgnoreCase(tagDto.getTagExists())) @ convertTagDTOToAcctVehRct()");
							temp.setAddTagInd("Y");
						}
						logger.debug("Inside if (!StringUtils.isBlank(tagDto.getAgencyId()) && !StringUtils.isBlank(tagDto.getTagId())) @ convertTagDTOToAcctVehRct()");
						if(StringUtil.isNotBlankOrNull(tagDto.getAgencyId())){
							temp.setAgencyId(tagDto.getAgencyId());
							temp.setAddTagInd("Y");
						}
					    if(StringUtil.isNotBlankOrNull(tagDto.getTagId())) {
							temp.setTagId(tagDto.getTagId());
						}
					}

					temp.setLicPlate(tagDto.getLicPlate());
					temp.setLicPlateTypeCode(tagDto.getPlateTypeCode());
					temp.setLicState(tagDto.getLicState());

					if (tagDto.getResponsibilityCode() != null) {
						logger.debug("Inside if (tagDto.getResponsibilityCode() != null) @ convertTagDTOToAcctVehRct()");
						temp.setRespTypeCode(tagDto.getResponsibilityCode());
					} else {
						logger.debug("Inside else of if (tagDto.getResponsibilityCode() != null) @ convertTagDTOToAcctVehRct()");
						temp.setRespTypeCode("O");
					}
					if (tagDto.getEffectiveDate() != null) {
						logger.debug("Inside if (tagDto.getEffectiveDate() != null) @ convertTagDTOToAcctVehRct()");
						temp.setEffectiveDate(new Timestamp(tagDto
								.getEffectiveDate().getTime().getTime()));
					}
					if (tagDto.getExpireDate() != null) {
						logger.debug("Inside if (tagDto.getExpireDate() != null) @ convertTagDTOToAcctVehRct()");
						temp.setExpiryDate(new Timestamp(tagDto.getExpireDate()
								.getTime()));
					} else if (tagDto.getExpirDate() != null) {
						logger.debug("Inside else if (tagDto.getExpirDate() != null) @ convertTagDTOToAcctVehRct()");
						temp.setExpiryDate(new Timestamp(tagDto.getExpirDate()
								.getTimeInMillis()));
					}
					if (tagDto.getVehicleYear() != null) {
						logger.debug("Inside if (tagDto.getVehicleYear() != null) @ convertTagDTOToAcctVehRct()");
						temp.setVehicleYear(new BigDecimal(tagDto
								.getVehicleYear()));
					}
					temp.setVehicleColor(tagDto.getVehicleColor());
					temp.setVehicleMake(tagDto.getVehicleMake());
					temp.setVehicleModel(tagDto.getVehicleModel());
					if (tagDto.getVehicleClassCode() != null) {
						logger.debug("Inside if (tagDto.getVehicleClassCode() != null) @ convertTagDTOToAcctVehRct()");
						temp.setVehicleClassId(new BigDecimal(tagDto
								.getVehicleClassCode()));
					}
					if (tagDto.getPlateExpirDate() != null) {
						logger.debug("Inside if (tagDto.getPlateExpirDate() != null) @ convertTagDTOToAcctVehRct()");
						temp.setPlateExpirDate(new Timestamp(tagDto
								.getPlateExpirDate().getTime().getTime()));
					}
					temp.setVin(String.valueOf(tagDto.getVinNumber()));
					temp.setRevCatCode("R");
					temp.setDmvVehicleId(tagDto.getDmvVehicleId() <= 0 ? null
							: new BigDecimal(tagDto.getDmvVehicleId()));
					temp.setIsDmvAccepted(tagDto.isDmvAccepted() ? "Y" : "N");

					logger.debug("getAcctTagId = " + temp.getAcctTagId());
					logger.debug("getAcctVehId = " + temp.getAcctVehId());
					logger.debug("getTagId = " + temp.getTagId());
					logger.debug("getAgencyId = " + temp.getAgencyId());
					logger.debug("getLicPlate = " + temp.getLicPlate());
					logger.debug("getLicPlateTypeCode = "
							+ temp.getLicPlateTypeCode());
					logger.debug("getLicState = " + temp.getLicState());
					logger.debug("getRespTypeCode = " + temp.getRespTypeCode());
					logger.debug("getRevCatCode = " + temp.getRevCatCode());
					logger.debug("getEffectiveDate = "
							+ temp.getEffectiveDate());
					logger.debug("getExpiryDate = " + temp.getExpiryDate());
					logger.debug("getVehicleYear = " + temp.getVehicleYear());
					logger.debug("getVehicleColor = " + temp.getVehicleColor());
					logger.debug("getVehicleMake = " + temp.getVehicleMake());
					logger.debug("getVehicleModel = " + temp.getVehicleModel());
					logger.debug("getVehicleClassId = "
							+ temp.getVehicleClassId());
					logger.debug("getPlateExpirDate = "
							+ temp.getPlateExpirDate());
					logger.debug("getAddTagInd = " + temp.getAddTagInd());
					logger.debug("getVin = " + temp.getVin());
					logger.info("getDmvVehicleId = " + temp.getDmvVehicleId());
					logger.debug("getIsDmvAccepted = "
							+ temp.getIsDmvAccepted());
					logger.debug("getTransferFlag = " + temp.getTransferFlag());
					tempList.add(temp);
				}
				array = (OlcAccountVehRec[]) tempList.toArray(array);
				;
			}// end of if (tags != null)
			logger.info("Leaving convertTagDTOToAcctVehRct() @ OracleVehicleDAO");
			return array;
		} catch (Exception ex) {
			logger.error(
					"Error in convertTagDTOToAcctVehRct() @ OracleVehicleDAO: "
							+ ex, ex);
			throw new EtccException("Error copying properties: " + ex, ex);
		}// end of try-catch()
	}// end of convertTagDTOToAcctVehRct()

	public String[] deleteVehicle(AccountLoginDTO acctLoginDto, TagDTO tagDTO)
			throws EtccSecurityException, EtccException {
		logger.info("Entering deleteVehicle() @ OracleVehicleDAO");

		String[] result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("deleteVehicle() :: Calling Olcsc_Acct_mgmt.Delete_Veh_Tag");
			cstmt = conn
					.prepareCall("{? = call  Olcsc_Acct_mgmt.Delete_Veh_Tag (?,?,?, ?,?,?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			cstmt.registerOutParameter(1, Types.SMALLINT);
			logger.debug("param 2= " + acctLoginDto.getAcctId());
			cstmt.setLong(2, acctLoginDto.getAcctId());
			logger.debug("param 3= " + acctLoginDto.getDbSessionId());
			cstmt.setString(3, acctLoginDto.getDbSessionId());
			logger.debug("param 4= " + acctLoginDto.getLastLoginIp());
			cstmt.setString(4, acctLoginDto.getLastLoginIp());
			logger.debug("param 5= " + acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLoginId());
			logger.debug("param 6= " + tagDTO.getAccountVehicleId());
			cstmt.setLong(6, tagDTO.getAccountVehicleId());
			logger.debug("param 7= " + EventEnum.OLCSC_ADD_VEH.getId());
			cstmt.setLong(7, EventEnum.OLCSC_ADD_VEH.getId());
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short success = cstmt.getShort(1);

			if (success == 1) {
				logger.debug("Inside if (success == 1) @ deleteVehicle()");
				result = new String[] { "Y", "No Error" };
			} else if (success == -1) {
				logger.debug("Inside else if (success == -1) @ deleteVehicle()");
				logger.error("Error in deleteVehicle() @ OracleVehicleDAO :: Security Exception in deleteVehicle()");
				throw new EtccSecurityException(
						"Security exception in deleteVehicle");
			} else if (success == 0) {
				logger.debug("Inside else if (success == 0) @ deleteVehicle()");
				Array errors = (Array) cstmt.getObject(8);
				Object array[] = (Object[]) errors.getArray();
				String errorMessage = "";
				if (array != null && array.length >= 0) {
					for (int i = 0; i < array.length; i++) {
						ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
						logger.error(msgRec.getErrMsg());
						errorMessage = msgRec.getErrMsg();
						if (!StringUtils.isBlank(errorMessage)) {
							logger.debug("Inside if (!StringUtils.isBlank(errorMessage)) @ deleteVehicle()");
							break;
						}
					}
				}
				if (StringUtils.isBlank(errorMessage)) {
					logger.debug("Inside if (StringUtils.isBlank(errorMessage)) @ deleteVehicle()");
					errorMessage = "Could not process request, please try again.";
				}

				String[] returnArr = new String[10];
				returnArr[0] = "N";
				returnArr[1] = errorMessage;
				result = new String[] { "N", errorMessage };
			}

		} catch (SQLException sqle) {
			logger.error(
					"Error in deleteVehicle() @ OracleVehicleDAO: " + sqle,
					sqle);
			throw new EtccException("Error running saveVehicleInfo: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
		logger.info("Leaving deleteVehicle() @ OracleVehicleDAO");
		return result;
	}// end of deleteVehicle()

	public boolean findExistingLicPlateNbr(String licPlate, Long accountId)
			throws EtccException {
		logger.info("Entering findExistingLicPlateNbr() @ OracleVehicleDAO");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT AV.LIC_PLATE_NUMBER ");
		sb.append(" FROM ACCOUNT_VEHICLES AV, ACCOUNTS ACT ");
		sb.append(" WHERE AV.LIC_PLATE_NUMBER = ?");
		sb.append(" AND AV.ACCOUNT_ID = ACT.ACCOUNT_ID ");
		sb.append(" AND AV.ACCOUNT_VEHICLE_STATUS_CODE = 'A'");
		if (accountId != null) {
			logger.debug("Inside first if (accountId != null) @ findExistingLicPlateNbr()");
			sb.append(" AND AV.ACCOUNT_ID != ? ");
		}// end of if (accountId != null)
		sb.append(" AND AV.ASSIGNMENT_END_DATE IS NULL ");
		sb.append(" AND ACT.ACCOUNT_TYPE_ID = ");
		sb.append(PROTECTED_ACCOUNT);
		sb.append(" AND ACT.ACCOUNT_STATUS_ID = 1 ");
		try {
			setConnection(Util.getDbConnection());
			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, licPlate);
			if (accountId != null) {
				logger.debug("Inside second if (accountId != null) @ findExistingLicPlateNbr()");
				ps.setLong(2, accountId);
			}
			rs = ps.executeQuery();
			String licPlateNbr = "";
			if (rs.next()) {
				logger.debug("Inside if (rs.next()) @ findExistingLicPlateNbr()");
				licPlateNbr = rs.getString(1);
			}
			if (StringUtil.isNotBlankOrNull(licPlateNbr)) {
				logger.debug("Inside if (StringUtil.isNotBlankOrNull(licPlateNbr)) @ findExistingLicPlateNbr()");
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in findExistingLicPlateNbr() @ OracleVehicleDAO: "
							+ sqle, sqle);
			throw new EtccException("Error getting license plate: " + sqle,
					sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
		logger.info("Leaving findExistingLicPlateNbr() @ OracleVehicleDAO");
		return false;
	}// end of findExistingLicPlateNbr()

	public String calculateAutochargeAmount(AccountLoginDTO acctLoginDto)
			throws EtccSecurityException, EtccException {
		logger.info("Entering calculateAutochargeAmount() @ OracleVehicleDAO");
		String message = null;
		logger.debug("prepare to call procedure ACCOUNT_MAINTENANCE_API.Update_Autocharge_Amounts(?,?,?, ?,?, ?,?,?)");
		try {
			setConnection(Util.getDbConnection());
			logger.info("calculateAutochargeAmount() :: Calling ACCOUNT_MAINTENANCE_API.Update_Autocharge_Amounts");
			cstmt = conn
					.prepareCall("{call ACCOUNT_MAINTENANCE_API.Update_Autocharge_Amounts (?,?,?, ?,?, ?,?,?)}");
			logger.debug("param 1: event id = "
					+ EventEnum.UPDATEAUTOCHARGE.getId());
			cstmt.setLong(1, EventEnum.UPDATEAUTOCHARGE.getId());
			logger.debug("param 2: login user id" + acctLoginDto.getLoginId()
					+ ", user = OLCSC_USER");
			cstmt.setString(2, "OLCSC_USER");
			logger.debug("param 3: account id = " + acctLoginDto.getAcctId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			logger.debug("param 4: autocharge amount = null");
			cstmt.setNull(4, Types.VARCHAR);
			logger.debug("param 5: low balance amount = null ");
			cstmt.setNull(5, Types.VARCHAR);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			cstmt.registerOutParameter(8, Types.VARCHAR);
			cstmt.execute();

			message = cstmt.getString(7);
			if (message != null) {
				logger.debug("Inside if (message != null) @ calculateAutochargeAmount()");
				logger.debug("got message " + message);
			} else {
				logger.debug("Inside else of if (message != null) @ calculateAutochargeAmount()");
				logger.debug("no change in autocharge amount");
			}
		} catch (SQLException sqle) {
			logger.error(
					"Error in calculateAutochargeAmount() @ OracleVehicleDAO: "
							+ sqle, sqle);
			throw new EtccException("Error running calculateAutochargeAmount: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch()
		logger.info("Leaving calculateAutochargeAmount() @ OracleVehicleDAO");
		return message;
	}// end of calculateAutochargeAmount()

}// end of
