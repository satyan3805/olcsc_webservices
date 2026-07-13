package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccErrorMessageException;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FulfillmentDTO;
import com.etcc.csc.dto.InventoryItemAttrbsDTO;
import com.etcc.csc.dto.InventoryItemDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcFulfillmentInfoRec;
import com.etcc.csc.plsql.OlcInfoAcctInventoryArr;
import com.etcc.csc.plsql.OlcInfoAcctInventoryRec;
import com.etcc.csc.plsql.OlcInfoInventoryItemsArr;
import com.etcc.csc.plsql.OlcInfoInventoryItemsRec;

public class OracleInventoryDAO extends InventoryDAO {
	private Logger logger = Logger.getLogger(OracleInventoryDAO.class);

	public OracleInventoryDAO() {
	}

	public Collection<InventoryItemDTO> getAvailableItems(
			AccountLoginDTO acctLoginDto) throws EtccErrorMessageException,
			Exception {
		logger.info("Entering getAvailableItems() @ OracleInventoryDAO");
		Collection<InventoryItemDTO> result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getAvailableItems() :: Calling Olcsc_Acct_Mgmt.INVENTORY_GET_AVAILABLE_ITEMS");
			cstmt = conn
					.prepareCall("{? = call "
							+ "Olcsc_Acct_Mgmt.INVENTORY_GET_AVAILABLE_ITEMS(?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			logger.debug("Inside getAvailableItems() @ OracleInventoryDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getAvailableItems() @ OracleInventoryDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getAvailableItems() @ OracleInventoryDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getAvailableItems() @ OracleInventoryDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(6, Types.ARRAY, "RITE_COMMON.STRING_ARRAY");
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR"); // error
			cstmt.execute();
			Object[] objArray = (Object[]) cstmt.getArray(6).getArray();
			if (objArray != null && objArray.length > 0) {
				result = new ArrayList<InventoryItemDTO>();
				for (int i = 0; i < objArray.length; i++) {
					InventoryItemDTO ivnDTO = new InventoryItemDTO();
					String temp = (String) objArray[i];
					String tokens[] = temp.split(",");
					ivnDTO.setInventoryTypeId(Long.parseLong(tokens[0]));
					ivnDTO.setItemDscp(tokens[1]);
					if (tokens.length == 4) {
						String priceTypeId = tokens[2];
						String price = tokens[3];
						if (priceTypeId != null)
							ivnDTO.setPriceTypeId(priceTypeId);
						if (price != null)
							ivnDTO.setItemPrice(Double.parseDouble(price));
					}
					result.add(ivnDTO);
				}
			}
		} catch (SQLException sqle) {
			logger.error("Error in getAvailableItems() @ OracleInventoryDAO ",
					sqle);
			throw new EtccException("Error running Inventory get Items: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getAvailableItems() @ OracleInventoryDAO");
		return result;
	}// end of getAvailableItems()

	public Collection<InventoryItemAttrbsDTO> getItemsAtrbs(
			AccountLoginDTO acctLoginDto, String inventroyTypeId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering getItemsAtrbs() @ OracleInventoryDAO");
		Collection result = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getItemsAtrbs() :: Calling Olcsc_Acct_Mgmt.GET_INFO_INVENTORY_ITEMS");
			cstmt = conn
					.prepareCall("{? = call "
							+ "Olcsc_Acct_Mgmt.GET_INFO_INVENTORY_ITEMS(?,?,?,?,?,?,?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_INFO_INVENTORY_ITEMS_REC",
					OlcInfoInventoryItemsRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			logger.debug("Inside getItemsAtrbs() @ OracleInventoryDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getItemsAtrbs() @ OracleInventoryDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getItemsAtrbs() @ OracleInventoryDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getItemsAtrbs() @ OracleInventoryDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setInt(6, Integer.parseInt(inventroyTypeId));
			cstmt.registerOutParameter(7, Types.ARRAY,
					"OL_OWNER.OLC_INFO_INVENTORY_ITEMS_ARR");
			cstmt.registerOutParameter(8, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR"); // error
			cstmt.execute();
			short success = cstmt.getShort(1);
			if (success == 1) {
				HashMap<Integer, InventoryItemAttrbsDTO> itemMap = new HashMap<Integer, InventoryItemAttrbsDTO>();
				Object[] objArray = (Object[]) cstmt.getArray(7).getArray();
				if (objArray != null && objArray.length > 0) {
					result = new ArrayList<InventoryItemAttrbsDTO>();
					for (int i = 0; i < objArray.length; i++) {
						OlcInfoInventoryItemsRec invnItemInfo = (OlcInfoInventoryItemsRec) objArray[i];
						Integer attrbId = Integer.valueOf(invnItemInfo
								.getAttributeId().intValue());
						if (itemMap.get(attrbId) != null) {
							// Add to the possible value list
							InventoryItemAttrbsDTO tempDTO = itemMap
									.get(attrbId);
							tempDTO.addToAttrbValueAsList(invnItemInfo
									.getInventoryTypeAttrValueId().toString(),
									invnItemInfo.getPossibleValue());

						} else {
							InventoryItemAttrbsDTO tempDTO = createInventoryItemAttrbsDTO(invnItemInfo);
							itemMap.put(attrbId, tempDTO);
						}
					}
				}
				result = new ArrayList<InventoryItemAttrbsDTO>(itemMap.values());
			} else {
				result = Util.convertErrorMsgs(cstmt.getArray(8));
				acctLoginDto.setErrors(result);
				logger.debug("Inside getItemsAtrbs() @ OracleInventoryDAO :: No Attrb Found for this item");
				result = new ArrayList();
				// TODO
			}// end of if-else()
		} catch (SQLException sqle) {
			logger.error("Error in getItemsAtrbs() @ OracleInventoryDAO ", sqle);
			throw new EtccException("Error running Inventory get Items: "
					+ sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getItemsAtrbs() @ OracleInventoryDAO");
		return result;
	}// end of getItemsAtrbs()

	private InventoryItemAttrbsDTO createInventoryItemAttrbsDTO(
			OlcInfoInventoryItemsRec invnItemInfo) throws SQLException {
		logger.info("Entering createInventoryItemAttrbsDTO() @ OracleInventoryDAO");
		InventoryItemAttrbsDTO tempDTO = new InventoryItemAttrbsDTO();
		Integer attrbId = Integer.valueOf(invnItemInfo.getAttributeId()
				.intValue());
		tempDTO.setAttrbId(attrbId.toString());
		tempDTO.setAttrbName(invnItemInfo.getName());
		tempDTO.setAttrbPrice(Double.valueOf((invnItemInfo.getPrice())
				.doubleValue()));
		tempDTO.setAttrbPromptText(invnItemInfo.getPromptText());
		tempDTO.setAttrbType(invnItemInfo.getDataType());
		tempDTO.setAttrbValue(invnItemInfo.getPossibleValue());
		tempDTO.setIsDisplyedInDropDown(invnItemInfo.getIsDisplayedInDropdown());
		// M3295 - rkarandikar
		if (invnItemInfo.getMaxLength() != null)
			tempDTO.setMaxLength(Integer.valueOf(invnItemInfo.getMaxLength()
					.intValue()));
		tempDTO.setIsRequired(invnItemInfo.getRequired());
		tempDTO.addToAttrbValueAsList(invnItemInfo
				.getInventoryTypeAttrValueId().toString(), invnItemInfo
				.getPossibleValue());
		logger.info("Leaving createInventoryItemAttrbsDTO() @ OracleInventoryDAO");
		return tempDTO;
	}// end of createInventoryItemAttrbsDTO()

	public FulfillmentDTO getFulfillmentDetails(AccountLoginDTO acctLoginDto,
			Long fulfillmentId) throws EtccErrorMessageException, Exception {
		logger.info("Entering getFulfillmentDetails() @ OracleInventoryDAO");
		FulfillmentDTO fulfillmentDTO = new FulfillmentDTO();
		try {
			setConnection(Util.getDbConnection());
			logger.info("getFulfillmentDetails() :: Calling Olcsc_Acct_Mgmt.FULFILLMENT_RETRV_INFO");
			cstmt = conn
					.prepareCall("{? = call "
							+ "Olcsc_Acct_Mgmt.FULFILLMENT_RETRV_INFO(?, ?, ?, ?, ?, ?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_FULFILLMENT_INFO_REC",
					OlcFulfillmentInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			int idx = 1;
			logger.debug("Inside getFulfillmentDetails() @ OracleInventoryDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getFulfillmentDetails() @ OracleInventoryDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getFulfillmentDetails() @ OracleInventoryDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getFulfillmentDetails() @ OracleInventoryDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setLong(idx++, fulfillmentId);
			// cstmt.setNull(idx++, java.sql.Types.NULL);
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_FULFILLMENT_INFO_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setInt(idx, 5);
			cstmt.execute();
			int success = cstmt.getShort(1);
			logger.debug("Inside getFulfillmentDetails() @ OracleInventoryDAO :: Success ::-->"
					+ success);
			if (success == 1) {
				Object[] objArray = (Object[]) cstmt.getArray(idx - 2)
						.getArray();
				List<InventoryItemDTO> invItemList = invArrayToList(objArray);
				fulfillmentDTO.setFulfillmentId(fulfillmentId);
				fulfillmentDTO.setInventoryItems(invItemList);
			}// end of if()
		} catch (SQLException sqle) {
			logger.error(
					"Error in getFulfillmentDetails() @ OracleInventoryDAO ",
					sqle);
			throw new EtccException("Error running OracleInventoryDAO(): "
					+ ": " + sqle, sqle);
		} catch (Exception e) {
			logger.error(
					"Error in getFulfillmentDetails() @ OracleInventoryDAO ", e);
			throw new EtccException(e);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getFulfillmentDetails() @ OracleInventoryDAO");
		return fulfillmentDTO;
	}// end of getFulfillmentDetails()

	private OlcFulfillmentInfoRec[] invListToArray(
			List<InventoryItemDTO> inventoryItemList) throws EtccException {
		logger.info("Entering invListToArray() @ OracleInventoryDAO");
		try {
			OlcFulfillmentInfoRec[] result = null;
			int i = 0;
			if (inventoryItemList != null) {
				result = new OlcFulfillmentInfoRec[inventoryItemList.size()];
				for (InventoryItemDTO itemDTO : inventoryItemList) {
					logger.debug("Inside invListToArray() @ OracleInventoryDAO :: Inventory Type Id ::-->"
							+ itemDTO.getInventoryTypeId());
					OlcFulfillmentInfoRec fulfillmentInfoRec = new OlcFulfillmentInfoRec();
					Long fulfillmentDtlId = itemDTO.getFulfillmentDetailId();
					if (fulfillmentDtlId != null) {
						fulfillmentInfoRec
								.setFulfillmentDetailId(new BigDecimal(
										fulfillmentDtlId.longValue()));
					}
					fulfillmentInfoRec.setInventoryTypeId(new BigDecimal(
							itemDTO.getInventoryTypeId()));
					fulfillmentInfoRec.setUnits(new BigDecimal(itemDTO
							.getItemQuantity()));
					fulfillmentInfoRec.setInventoryTypeDesc(itemDTO
							.getItemDscp());
					fulfillmentInfoRec.setInventoryPrice(new BigDecimal(itemDTO
							.getItemPrice()));
					fulfillmentInfoRec.setFlagValue(itemDTO.getUpdateFlag());
					List<InventoryItemAttrbsDTO> itemAttrbs = itemDTO
							.getInventoryItemAttrbs();
					if (itemAttrbs != null && !itemAttrbs.isEmpty()) {
						OlcInfoInventoryItemsRec[] attrbs = new OlcInfoInventoryItemsRec[itemAttrbs
								.size()];
						OlcInfoInventoryItemsArr itemsArr = new OlcInfoInventoryItemsArr();
						int j = 0;
						for (InventoryItemAttrbsDTO attrbsDTO : itemAttrbs) {
							OlcInfoInventoryItemsRec attrItemsRec = new OlcInfoInventoryItemsRec();
							attrItemsRec.setAttributeId(new BigDecimal(
									attrbsDTO.getAttrbId()));
							attrItemsRec.setName(attrbsDTO.getAttrbName());
							attrItemsRec.setPossibleValue(attrbsDTO
									.getAttrbValue());
							if (attrbsDTO.getInventoryTypeAttrValueId() != null) {
								attrItemsRec
										.setInventoryTypeAttrValueId(new BigDecimal(
												attrbsDTO
														.getInventoryTypeAttrValueId()));
							}
							attrbs[j++] = attrItemsRec;
						}
						itemsArr.setArray(attrbs);
						fulfillmentInfoRec.setInfoInventoryItemsArr(itemsArr);
					}
					result[i++] = fulfillmentInfoRec;
				}
			}
			logger.info("Leaving invListToArray() @ OracleInventoryDAO");
			return result;
		} catch (Exception e) {
			logger.error("Error in invListToArray() @ OracleInventoryDAO ", e);
			throw new EtccException("Error copying inv items: " + e);
		}// end of try-catch-finally()
	}// end of invListToArray()

	private List<InventoryItemDTO> invArrayToList(Object[] olcFullfillmentRecArr)
			throws EtccException {
		logger.info("Entering invArrayToList() @ OracleInventoryDAO");
		try {
			List<InventoryItemDTO> result = new ArrayList<InventoryItemDTO>();
			if (olcFullfillmentRecArr != null
					&& olcFullfillmentRecArr.length > 0) {
				OlcFulfillmentInfoRec olcFullfillmentRec = null;
				InventoryItemDTO itemDTO = null;
				for (int idx = 0; idx < olcFullfillmentRecArr.length; idx++) {
					olcFullfillmentRec = (OlcFulfillmentInfoRec) olcFullfillmentRecArr[idx];
					itemDTO = new InventoryItemDTO();
					logger.debug("Inside invArrayToList() @ OracleInventoryDAO :: Fulfillment Detail Id ::-->"
							+ olcFullfillmentRec.getFulfillmentDetailId());
					itemDTO.setFulfillmentDetailId(new Long(olcFullfillmentRec
							.getFulfillmentDetailId().longValue()));
					itemDTO.setInventoryTypeId(new Long(olcFullfillmentRec
							.getInventoryTypeId().longValue()));
					itemDTO.setItemQuantity(olcFullfillmentRec.getUnits()
							.intValue());
					itemDTO.setItemDscp(olcFullfillmentRec
							.getInventoryTypeDesc());
					itemDTO.setItemPrice(olcFullfillmentRec.getInventoryPrice()
							.doubleValue());

					List<InventoryItemAttrbsDTO> itemAttrsDTOList = new ArrayList<InventoryItemAttrbsDTO>();
					OlcInfoInventoryItemsArr itemsArr = olcFullfillmentRec
							.getInfoInventoryItemsArr();
					if (itemsArr != null) {
						InventoryItemAttrbsDTO attrbsDTO = null;
						OlcInfoInventoryItemsRec attrItemsRec = null;
						for (int j = 0; j < itemsArr.length(); j++) {
							attrItemsRec = itemsArr.getElement(j);
							if (attrItemsRec.getAttributeId() != null) {
								attrbsDTO = new InventoryItemAttrbsDTO();
								attrbsDTO.setAttrbId(attrItemsRec
										.getAttributeId().toString());
								attrbsDTO.setAttrbValue(attrItemsRec
										.getPossibleValue());
								attrbsDTO.setAttrbName(attrItemsRec.getName());
								if (attrItemsRec.getInventoryTypeAttrValueId() != null) {
									attrbsDTO
											.setInventoryTypeAttrValueId(new Long(
													attrItemsRec
															.getInventoryTypeAttrValueId()
															.longValue()));
								}
								itemAttrsDTOList.add(attrbsDTO);
							}
						}
						itemDTO.setInventoryItemAttrbs(itemAttrsDTOList);
					}
					OlcInfoAcctInventoryArr acctInventoryArr = olcFullfillmentRec
							.getInfoAcctInventoryArr();
					Long[] accountInventoryIds = null;
					if (acctInventoryArr != null) {
						OlcInfoAcctInventoryRec acctInvRec = null;
						accountInventoryIds = new Long[acctInventoryArr
								.length()];
						for (int j = 0; j < acctInventoryArr.length(); j++) {
							acctInvRec = acctInventoryArr.getElement(j);
							if (acctInvRec.getAccountInventoryId() != null) {
								accountInventoryIds[j] = new Long(acctInvRec
										.getAccountInventoryId().longValue());
							}
						}
					}
					itemDTO.setAccountInventory(accountInventoryIds);
					result.add(itemDTO);
				}
			}
			logger.info("Leaving invArrayToList() @ OracleInventoryDAO");
			return result;
		} catch (Exception e) {
			logger.error("Error in invArrayToList() @ OracleInventoryDAO ", e);
			throw new EtccException("Error copying inv items: " + e);
		}// end of try-catch-finally()
	}// end of invArrayToList()

	public FulfillmentDTO addToFulfillment(AccountLoginDTO acctLoginDto,
			List<InventoryItemDTO> inventoryItemList, Long fulfillmentId)
			throws EtccErrorMessageException, Exception {
		logger.info("Entering addToFulfillment() @ OracleInventoryDAO");
		FulfillmentDTO fulfillmentDTO = new FulfillmentDTO();
		try {
			setConnection(Util.getDbConnection());
			logger.info("addToFulfillment() :: Calling Olcsc_Acct_Mgmt.FULFILLMENT_REQUEST");
			cstmt = conn
					.prepareCall("{? = call "
							+ "Olcsc_Acct_Mgmt.FULFILLMENT_REQUEST(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			Map typeMap = new HashMap();
			typeMap.put("OL_OWNER.OLC_FULFILLMENT_INFO_REC",
					OlcFulfillmentInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			int idx = 1;
			ArrayDescriptor arraydesc = ArrayDescriptor.createDescriptor(
					"OL_OWNER.OLC_FULFILLMENT_INFO_ARR", conn);
			ARRAY array = new ARRAY(arraydesc, conn,
					invListToArray(inventoryItemList));
			logger.debug("Inside addToFulfillment() @ OracleInventoryDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside addToFulfillment() @ OracleInventoryDAO :: Account Id ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside addToFulfillment() @ OracleInventoryDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside addToFulfillment() @ OracleInventoryDAO :: Last Login IP Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			cstmt.registerOutParameter(1, Types.SMALLINT);
			cstmt.setString(2, acctLoginDto.getDbSessionId());
			cstmt.setLong(3, acctLoginDto.getAcctId());
			cstmt.setString(4, acctLoginDto.getLoginId());
			cstmt.setString(5, acctLoginDto.getLastLoginIp());
			cstmt.setString(6, "N");
			cstmt.setNull(7, java.sql.Types.NULL);
			cstmt.setNull(8, java.sql.Types.NULL);
			cstmt.setInt(9, 5);
			cstmt.setArray(10, array);
			cstmt.registerOutParameter(10, Types.ARRAY,
					"OL_OWNER.OLC_FULFILLMENT_INFO_ARR");
			if (fulfillmentId != null) {
				cstmt.setLong(11, fulfillmentId);
			} else {
				cstmt.registerOutParameter(11, Types.INTEGER);
			}
			cstmt.registerOutParameter(12, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			int success = cstmt.getShort(1);
			logger.debug("Inside addToFulfillment() @ OracleInventoryDAO :: Success ::-->"
					+ success);
			if (success == 1 && fulfillmentId == null) {
				int fId = cstmt.getInt(11);
				fulfillmentId = new Long(fId);
			}
			logger.debug("Inside addToFulfillment() @ OracleInventoryDAO :: Fulfillment Id ::-->"
					+ fulfillmentId);
			Object[] objArray = (Object[]) cstmt.getArray(10).getArray();
			List<InventoryItemDTO> invItemList = invArrayToList(objArray);
			fulfillmentDTO.setFulfillmentId(fulfillmentId);
			fulfillmentDTO.setInventoryItems(invItemList);
		} catch (SQLException sqle) {
			logger.error("Error in addToFulfillment() @ OracleInventoryDAO ",
					sqle);
			throw new EtccException("Error running addToFulfillment(): " + ": "
					+ sqle, sqle);
		} catch (Exception e) {
			logger.error("Error in addToFulfillment() @ OracleInventoryDAO ", e);
			throw new EtccException(e);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving addToFulfillment() @ OracleInventoryDAO");
		return fulfillmentDTO;
	}// end of addToFulfillment()

}// end of OracleInventoryDAO Class
