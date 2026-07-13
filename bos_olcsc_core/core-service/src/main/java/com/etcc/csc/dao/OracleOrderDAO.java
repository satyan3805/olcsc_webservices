package com.etcc.csc.dao;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.DateUtil;
import com.etcc.csc.common.ErrorMessageDTO;
import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.common.Util;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AddressDTO;
import com.etcc.csc.dto.FulfillmentDetailToAccountItemDTO;
import com.etcc.csc.dto.OrderDTO;
import com.etcc.csc.dto.PickupLocationDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.etcc.csc.plsql.OlcAddrInfoRec;
import com.etcc.csc.plsql.OlcFulfillmentStatRec;
import com.etcc.csc.plsql.OlcOlpFulfillmentDetailRec;
import com.etcc.csc.plsql.OlcPosLocRec;

public class OracleOrderDAO extends OrderDAO {

	private Logger logger = Logger.getLogger(OracleOrderDAO.class);

	public OracleOrderDAO() {
	}

	/**
	 * Retrieves all orders belonging to an account.
	 * 
	 * @return Collection
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public List<OrderDTO> getOrders(AccountLoginDTO acctLoginDto,
			boolean pendingOnly) throws EtccException, EtccSecurityException {
		logger.info("Entering getOrders() @ OracleOrderDAO");
		List<OrderDTO> col = null;
		try {
			setConnection(Util.getDbConnection());
			logger.info("getOrders() :: Calling Olcsc_Acct_Mgmt.Get_Fulfillment_Stat");
			cstmt = conn.prepareCall("{? = call "
					+ "Olcsc_Acct_Mgmt.Get_Fulfillment_Stat(?,?,?,?,?,?,?,?)}");
			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_FULFILLMENT_STAT_REC",
					OlcFulfillmentStatRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			logger.debug("Inside getOrders() @ OracleOrderDAO :: Account Id Value ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getOrders() @ OracleOrderDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getOrders() @ OracleOrderDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getOrders() @ OracleOrderDAO :: Last Login Ip Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			logger.debug("Inside getOrders() @ OracleOrderDAO :: PendingOnly  ::-->"
					+ pendingOnly);
			cstmt.setString(idx++, "0");// event id temporarily passed as 0
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_FULFILLMENT_STAT_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.setString(idx, (pendingOnly ? "Y" : "N"));
			cstmt.execute();
			short found = cstmt.getShort(1);
			logger.debug("Inside getOrders() @ OracleOrderDAO :: Output Value ::-->"
					+ found);
			if (found == 1) {
				Array orders = (Array) cstmt.getObject(idx - 2);
				if (orders != null) {
					col = new ArrayList<OrderDTO>();
					Object array[] = (Object[]) orders.getArray();
					for (int i = 0; i < array.length; i++) {
						OlcFulfillmentStatRec tempOrder = (OlcFulfillmentStatRec) array[i];
						if (tempOrder != null) {
							OrderDTO orderDto = new OrderDTO();
							orderDto.setAcctId(acctLoginDto.getAcctId());
							orderDto.setFulfillmentId(tempOrder
									.getFulfillmentId().toString());

							orderDto.setAcctId(acctLoginDto.getAcctId());
							orderDto.setOrderDate(DateUtil
									.timestampToCalendar(tempOrder
											.getOrderDate()));
							orderDto.setStatus(tempOrder.getFulfillmentStatus());
							orderDto.setType(tempOrder.getFulfillmentType());
							orderDto.setParentFulfillmentId((tempOrder
									.getParentFulfillmentId() == null ? ""
									: tempOrder.getParentFulfillmentId()
											.toString()));
							orderDto.setStatusDate(DateUtil
									.timestampToCalendar(tempOrder
											.getStatusChangeDate()));
							BigDecimal addrId = tempOrder.getAddressId();
							if (addrId != null) {
								orderDto.setAddrId(new Long(addrId.longValue()));
							}
							orderDto.setDeliveryMethod(tempOrder
									.getDeliveryMethod());
							orderDto.setDelAdd1(tempOrder.getAddress1());
							orderDto.setDelAdd2(tempOrder.getAddress2());
							orderDto.setCity(tempOrder.getCity());
							orderDto.setState(tempOrder.getState());
							orderDto.setZipCode(tempOrder.getZipCode());
							orderDto.setLicPlate(tempOrder.getLicensePlate());
							orderDto.setIsPartial(tempOrder.getIsPartial());
							orderDto.setFullTagId(tempOrder.getTagId());
							orderDto.setLocation(tempOrder.getPname());
							orderDto.setQty(tempOrder.getQuantity().intValue());
							orderDto.setIsPrepaid(tempOrder.getIsPrepaid());
							col.add(orderDto);
						}
					}
				}
			} else if (found == -1) {
				logger.error("Error in getOrders() @ OracleOrderDAO:: Security Exception ");
				throw new EtccSecurityException("Security Exception in "
						+ "getOrders()");
			}
		} catch (SQLException sqlex) {
			logger.error("Error in getOrders() @ OracleOrderDAO ", sqlex);
			throw new EtccException("Error running getOrders(acctId): "
					+ acctLoginDto.getAcctId() + ": " + sqlex, sqlex);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getOrders() @ OracleOrderDAO");
		return col;
	}// end of getOrders()

	/**
	 * Updates the order.
	 * 
	 * @return Collection
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public String updateOrder(AccountLoginDTO acctLoginDto, OrderDTO orderDTO,
			String callType) throws EtccException, EtccSecurityException {
		logger.info("Entering updateOrder() @ OracleOrderDAO");
		try {
			setConnection(Util.getDbConnection());
			logger.info("updateOrder() :: Calling Olcsc_Acct_Mgmt.FULFILLMENT_UPDATE");
			cstmt = conn
					.prepareCall("{? = call Olcsc_Acct_Mgmt.FULFILLMENT_UPDATE(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);
			int idx = 1;
			OlcAddrInfoRec olcAddreInfoRec = new OlcAddrInfoRec();
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());

			logger.debug("Inside updateOrder() @ OracleOrderDAO :: Account Id Value ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside updateOrder() @ OracleOrderDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside updateOrder() @ OracleOrderDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside updateOrder() @ OracleOrderDAO :: Last Login Ip Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			logger.debug("Inside updateOrder() @ OracleOrderDAO :: CallType  ::-->"
					+ callType);

			if (callType.equals("U")) {
				if (orderDTO.getIsNewAddress().equals("Y")) {
					olcAddreInfoRec = new OlcAddrInfoRec();
					olcAddreInfoRec.setFirstName(orderDTO.getDelPFName());
					olcAddreInfoRec.setLastName(orderDTO.getDelPLName());
					olcAddreInfoRec.setAddress1(orderDTO.getDelAdd1());
					olcAddreInfoRec.setAddress2(orderDTO.getDelAdd2());
					olcAddreInfoRec.setCity(orderDTO.getCity());
					olcAddreInfoRec.setState(orderDTO.getState());
					olcAddreInfoRec.setZipCode(orderDTO.getZipCode());
					olcAddreInfoRec.setPlus4(orderDTO.getPlus4());
					olcAddreInfoRec.setCountryCode(orderDTO.getCountryCode());
					cstmt.setString(idx++, "Y");
				} else {
					cstmt.setString(idx++, "N");
				}
			} else {
				cstmt.setString(idx++, "N");
			}
			Long addrId = orderDTO.getAddrId();
			if (addrId == null) {
				cstmt.setNull(idx++, java.sql.Types.BIGINT);
			} else {
				cstmt.setLong(idx++, addrId);
			}
			cstmt.setLong(idx++, Long.parseLong(orderDTO.getFulfillmentId()));
			cstmt.setString(idx++, orderDTO.getDeliveryMethod());
			Long pickupLocationId = orderDTO.getPickupLocationId();
			if (pickupLocationId == null) {
				cstmt.setNull(idx++, java.sql.Types.BIGINT);
			} else {
				cstmt.setLong(idx++, pickupLocationId);
			}
			cstmt.setString(idx++, callType);
			cstmt.setInt(idx++, 6); // eventId
			cstmt.setObject(idx++, olcAddreInfoRec);
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short found = cstmt.getShort(1);
			logger.debug("Inside updateOrder() @ OracleOrderDAO :: Output Value ::-->"
					+ found);
			if (found == 1) {
				Array errs = (Array) cstmt.getObject(idx);
				Collection<ErrorMessageDTO> errors = Util
						.convertErrorMsgs(errs);
				if (errors == null || errors.size() == 0) {
					errors = new ArrayList<ErrorMessageDTO>();
					ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
					errMsgDto.setMessage("Invalid information entered. "
							+ "Please try again.");
					errors.add(errMsgDto);
				}
			}
		} catch (SQLException sqlEx) {
			logger.error("Error in updateOrder() @ OracleOrderDAO ", sqlEx);
			throw new EtccException("Error running updateOrder(acctId): "
					+ acctLoginDto.getAcctId() + ": " + sqlEx, sqlEx);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving updateOrder() @ OracleOrderDAO");
		return "";
	}// end of updateOrder()

	/**
	 * Retrieves all Addresses belonging to an account.
	 * 
	 * @return Collection
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public List<AddressDTO> getShippingAddresses(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getShippingAddresses() @ OracleOrderDAO");
		List<AddressDTO> col = new ArrayList<AddressDTO>();
		try {
			setConnection(Util.getDbConnection());
			logger.info("getShippingAddresses() :: Calling Olcsc_Acct_Mgmt.FULFILLMENT_MAIL_PICKUP");
			cstmt = conn
					.prepareCall("{? = call Olcsc_Acct_Mgmt.FULFILLMENT_MAIL_PICKUP(?,?,?,?,?,?,?,?)}");
			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_ADDR_INFO_REC", OlcAddrInfoRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());

			logger.debug("Inside getShippingAddresses() @ OracleOrderDAO :: Account Id Value ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getShippingAddresses() @ OracleOrderDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getShippingAddresses() @ OracleOrderDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getShippingAddresses() @ OracleOrderDAO :: Last Login Ip Address ::-->"
					+ acctLoginDto.getLastLoginIp());

			cstmt.setString(idx++, "Y"); // Delivery Method Mail
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ADDR_INFO_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_POS_LOC_ARR");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short found = cstmt.getShort(1);
			logger.debug("Inside getShippingAddresses() @ OracleOrderDAO :: Output Value ::-->"
					+ found);
			if (found == 1) {
				Array addresses = (Array) cstmt.getObject(idx - 2);
				if (addresses != null) {
					// col = new ArrayList<AddressDTO>();
					Object array[] = (Object[]) addresses.getArray();
					for (int i = 0; i < array.length; i++) {
						OlcAddrInfoRec tempAddr = (OlcAddrInfoRec) array[i];
						AddressDTO addressDto = new AddressDTO();
						addressDto.setAddressId(new Long(tempAddr
								.getAddressId().longValue()));
						addressDto.setFirstName(tempAddr.getFirstName());
						addressDto.setLastName(tempAddr.getLastName());
						addressDto.setAddress1(tempAddr.getAddress1());
						addressDto.setAddress2(tempAddr.getAddress2());
						addressDto.setCity(tempAddr.getCity());
						addressDto.setState(tempAddr.getState());
						addressDto.setCountryCode(tempAddr.getCountryCode());
						addressDto.setPlus4(tempAddr.getPlus4());

						col.add(addressDto);
					}
				}
			} else if (found == -1) {
				logger.error("Error in getShippingAddresses() @ OracleOrderDAO:: Security Exception ");
				throw new EtccSecurityException("Security Exception in "
						+ "getShippingAddresses()");
			}
		} catch (SQLException sqle) {
			logger.error("Error in getShippingAddresses() @ OracleOrderDAO ",
					sqle);
			throw new EtccException(
					"Error running getShippingAddresses(acctId): "
							+ acctLoginDto.getAcctId() + ": " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getShippingAddresses() @ OracleOrderDAO");
		return col;
	}// end of getShippingAddresses()

	/**
	 * Retrieves all Pickup locations belonging to an account.
	 * 
	 * @return Collection
	 * @throws EtccException
	 * @throws EtccSecurityException
	 */
	public List<PickupLocationDTO> getPOSLocations(AccountLoginDTO acctLoginDto)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getPOSLocations() @ OracleOrderDAO");
		List<PickupLocationDTO> col = new ArrayList<PickupLocationDTO>();
		try {
			setConnection(Util.getDbConnection());
			logger.info("getPOSLocations() :: Calling Olcsc_Acct_Mgmt.FULFILLMENT_MAIL_PICKUP");
			cstmt = conn
					.prepareCall("{? = call Olcsc_Acct_Mgmt.FULFILLMENT_MAIL_PICKUP(?,?,?,?,?,?,?,?)}");
			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_POS_LOC_REC", OlcPosLocRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());

			logger.debug("Inside getPOSLocations() @ OracleOrderDAO :: Account Id Value ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getPOSLocations() @ OracleOrderDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getPOSLocations() @ OracleOrderDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getPOSLocations() @ OracleOrderDAO :: Last Login Ip Address ::-->"
					+ acctLoginDto.getLastLoginIp());

			cstmt.setString(idx++, "P"); // Delivery Method Pickup
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_ADDR_INFO_ARR");
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_POS_LOC_ARR");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();

			short found = cstmt.getShort(1);
			logger.debug("Inside getPOSLocations() @ OracleOrderDAO :: Output Value ::-->"
					+ found);
			if (found == 1) {
				Array posLocations = (Array) cstmt.getObject(idx - 1);
				if (posLocations != null) {
					// col = new ArrayList<PickupLocationDTO>();
					Object array[] = (Object[]) posLocations.getArray();
					for (int i = 0; i < array.length; i++) {
						OlcPosLocRec tempPosLoc = (OlcPosLocRec) array[i];
						PickupLocationDTO posLocDTO = new PickupLocationDTO();
						posLocDTO.setPickupLocationId(new Long(tempPosLoc
								.getPickUpLocId().longValue()));
						posLocDTO.setPosName(tempPosLoc.getPosName());
						posLocDTO.setAddress1(tempPosLoc.getAddress1());
						posLocDTO.setAddress2(tempPosLoc.getAddress2());
						posLocDTO.setCity(tempPosLoc.getCity());
						posLocDTO.setState(tempPosLoc.getState());
						col.add(posLocDTO);
					}
				}
			} else if (found == -1) {
				logger.error("Error in getPOSLocations() @ OracleOrderDAO:: Security Exception ");
				throw new EtccSecurityException("Security Exception in "
						+ "getPOSLocations()");
			}
		} catch (SQLException sqle) {
			logger.error("Error in getPOSLocations() @ OracleOrderDAO ", sqle);
			throw new EtccException("Error running getPOSLocations(acctId): "
					+ acctLoginDto.getAcctId() + ": " + sqle, sqle);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getPOSLocations() @ OracleOrderDAO");
		return col;
	}// end of getPOSLocations()

	public List<Object> getFulfillmentRelatedAccountItems(
			AccountLoginDTO acctLoginDto, String fulfillmentId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getFulfillmentRelatedAccountItems() @ OracleOrderDAO");
		List<Object> col = new ArrayList<Object>();
		try {
			logger.debug("Inside getFulfillmentRelatedAccountItems() @ OracleOrderDAO :: Account Id Value ::-->"
					+ acctLoginDto.getAcctId());
			logger.debug("Inside getFulfillmentRelatedAccountItems() @ OracleOrderDAO :: DB Session Id ::-->"
					+ acctLoginDto.getDbSessionId());
			logger.debug("Inside getFulfillmentRelatedAccountItems() @ OracleOrderDAO :: Login Id ::-->"
					+ acctLoginDto.getLoginId());
			logger.debug("Inside getFulfillmentRelatedAccountItems() @ OracleOrderDAO :: Last Login Ip Address ::-->"
					+ acctLoginDto.getLastLoginIp());
			logger.debug("Inside getFulfillmentRelatedAccountItems() @ OracleOrderDAO :: Fulfillment Id ::-->"
					+ fulfillmentId);

			setConnection(Util.getDbConnection());
			logger.info("getFulfillmentRelatedAccountItems() :: Calling OLCSC_ACCT_MGMT.GET_FULFILLMENT_INFO");
			cstmt = conn
					.prepareCall("{? = call OLCSC_ACCT_MGMT.GET_FULFILLMENT_INFO(?,?,?,?,?,?,?,?,?)}");
			Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
			typeMap.put("OL_OWNER.OLC_OLP_FULFILLMENT_DETAIL_REC",
					OlcOlpFulfillmentDetailRec.class);
			typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", ErrorMsgRec.class);
			conn.setTypeMap(typeMap);

			int idx = 1;
			cstmt.registerOutParameter(idx++, Types.SMALLINT);
			cstmt.setString(idx++, acctLoginDto.getDbSessionId());
			cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
			cstmt.setLong(idx++, acctLoginDto.getAcctId());
			cstmt.setString(idx++, "AC");
			cstmt.setString(idx++, acctLoginDto.getLoginId());
			cstmt.setLong(idx++, Long.valueOf(fulfillmentId));
			cstmt.setString(idx++, "0");// event id temporarily passed as 0
			cstmt.registerOutParameter(idx++, Types.ARRAY,
					"OL_OWNER.OLC_OLP_FULFILLMENT_DETAIL_ARR");
			cstmt.registerOutParameter(idx, Types.ARRAY,
					"OL_OWNER.OLC_ERROR_MSG_ARR");
			cstmt.execute();
			short found = cstmt.getShort(1);
			logger.debug("Inside getFulfillmentRelatedAccountItems() @ OracleOrderDAO :: Output Value ::-->"
					+ found);
			if (found == 1) {
				Array fulfillmentDetails = (Array) cstmt.getObject(idx - 1);
				if (fulfillmentDetails != null) {

					Object array[] = (Object[]) fulfillmentDetails.getArray();
					logger.debug("OLCSC_ACCT_MGMT.GET_FULFILLMENT_INFO returned result size::"
							+ array.length);
					for (int i = 0; i < array.length; i++) {
						OlcOlpFulfillmentDetailRec tempDetailRec = (OlcOlpFulfillmentDetailRec) array[i];
						FulfillmentDetailToAccountItemDTO fulfillmentDetailsDTO = new FulfillmentDetailToAccountItemDTO();
						fulfillmentDetailsDTO.setFulfillmentId(tempDetailRec
								.getFulfillmentId());
						fulfillmentDetailsDTO
								.setFulfillmentDetailId(tempDetailRec
										.getFulfillmentDetailId());
						fulfillmentDetailsDTO.setAccountTagId(tempDetailRec
								.getAccountTagId());
						fulfillmentDetailsDTO
								.setAccountInventoryId(tempDetailRec
										.getAccountInventoryId());
						fulfillmentDetailsDTO.setAppliedAmount(tempDetailRec
								.getAppliedAmount() != null ? tempDetailRec
								.getAppliedAmount().doubleValue() : 0.0);
						fulfillmentDetailsDTO.setCartId(tempDetailRec
								.getAccountPostingId() != null ? tempDetailRec
								.getAccountPostingId().longValue() : null);

						col.add(fulfillmentDetailsDTO);
					}
				} else {
					logger.debug("OLCSC_ACCT_MGMT.GET_FULFILLMENT_INFO did not returned results...");
					ErrorMessageDTO err = new ErrorMessageDTO();
					err.setMessage("No data found");
					col.add(err);
				}
			} else if (found == -1) {
				logger.error("OLCSC_ACCT_MGMT.GET_FULFILLMENT_INFO returned -1");
				Array errs = (Array) cstmt.getObject(idx);
				Collection<ErrorMessageDTO> errors = Util
						.convertErrorMsgs(errs);
				if (errors != null && errors.size() == 0) {
					col.addAll(errors);
					/*
					 * String errorMsg = ""; for(ErrorMessageDTO err: errors){
					 * errorMsg += err.getMessage(); } throw new EtccException(
					 * "Error running getFulfillmentRelatedAccountItems(acctId, fulfillmentId): "
					 * + acctLoginDto.getAcctId() + ", " + fulfillmentId + ": "
					 * + errorMsg);
					 */
				} else {
					logger.error("Error in getFulfillmentRelatedAccountItems() @ OracleOrderDAO:: Security Exception ");
					throw new EtccSecurityException(
							"Security Exception in getFulfillmentRelatedAccountItems()");
				}
			} else {
				logger.error("OLCSC_ACCT_MGMT.GET_FULFILLMENT_INFO returned 0");
				Array errs = (Array) cstmt.getObject(idx);
				Collection<ErrorMessageDTO> errors = Util
						.convertErrorMsgs(errs);
				if (errors != null && errors.size() == 0) {
					col.addAll(errors);
				}
			}
		} catch (SQLException sqlEx) {
			logger.error(
					"Error in getFulfillmentRelatedAccountItems() @ OracleOrderDAO ",
					sqlEx);
			throw new EtccException(
					"Error running getFulfillmentRelatedAccountItems(acctId, fulfillmentId): "
							+ acctLoginDto.getAcctId() + ", " + fulfillmentId
							+ " >> cought SQL Exception ::"
							+ sqlEx.getMessage(), sqlEx);
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getFulfillmentRelatedAccountItems() @ OracleOrderDAO");
		return col;
	}// end of getFulfillmentRelatedAccountItems()

	/**
	 * This method will retrieve the pick-up location hours based on
	 * 'pickupLocationId'
	 */
	public String getPickupLocationHours(BigDecimal pickupLocationId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getPickupLocationHours() @ OracleOrderDAO");
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		String pickupLocationHours = "";
		try {
			setConnection(Util.getDbConnection());
			sql.append("select BUSINESS_DAY || ' (' ||START_TIME || ' to ' ||END_TIME || ')' from(SELECT DECODE(DAY_OF_WEEK,1,'Mon',2,'Tue',3,'Wed',4,'Thu',5,'Fri',6,'Sat',7,'Sun','Daily') ");
			sql.append(" BUSINESS_DAY,TO_CHAR(START_TIME, 'HH:MI AM') START_TIME,TO_CHAR(END_TIME, 'HH:MI AM') END_TIME FROM POS_DAYS_OF_OPERATION WHERE POS_ID = ? AND START_TIME IS NOT NULL");
			sql.append(" AND END_TIME IS NOT NULL ORDER BY DAY_OF_WEEK)");
			logger.debug("Inside getPickupLocationHours() @ OracleOrderDAO :: SQL ::-->"
					+ sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, pickupLocationId.intValue());
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					if (rs.getString(1) != null && rs.getString(1) != "")
						pickupLocationHours = pickupLocationHours
								+ rs.getString(1) + "#";
				}
			}
		} catch (Exception ex) {
			logger.error("Error in getPickupLocationHours() @ OracleOrderDAO ",
					ex);
			throw new EtccException(
					"Error running getPickupLocationHours(BigDecimal pickupLocationId) : ");
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getPickupLocationHours() @ OracleOrderDAO");
		return pickupLocationHours;
	}// end of getPickupLocationHours()

	/**
	 * This method will retrieve the pick-up location address based on
	 * 'pickupLocationId'
	 */
	public String getPickupLocationAddress(BigDecimal pickupLocationId)
			throws EtccException, EtccSecurityException {
		logger.info("Entering getPickupLocationAddress() @ OracleOrderDAO");
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer fullAddress = new StringBuffer();
		try {
			setConnection(Util.getDbConnection());
			ps = conn
					.prepareStatement("select pl.pos_name,pl.address1,pl.address2,pl.city,pl.state,pl.zip_code as aa from pos_locations pl where pl.pos_id = ? ");
			ps.setInt(1, pickupLocationId.intValue());
			logger.debug("Inside getPickupLocationAddress() @ OracleOrderDAO :: Pickup Location Id ::-->"
					+ pickupLocationId.intValue());
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				if (rs.getString(1) != null && !rs.getString(1).equals(""))
					fullAddress = fullAddress.append(rs.getString(1));

				if (rs.getString(2) != null && !rs.getString(2).equals(""))
					fullAddress = fullAddress.append(", " + rs.getString(2));

				if (rs.getString(3) != null && !rs.getString(3).equals(""))
					fullAddress = fullAddress.append(", " + rs.getString(3));

				if (rs.getString(4) != null && !rs.getString(4).equals(""))
					fullAddress = fullAddress.append(", " + rs.getString(4));

				if (rs.getString(5) != null && !rs.getString(5).equals(""))
					fullAddress = fullAddress.append(", " + rs.getString(5));

				if (rs.getString(6) != null && !rs.getString(6).equals(""))
					fullAddress = fullAddress.append(" " + rs.getString(6));
			}
		} catch (Exception ex) {
			logger.error(
					"Error in getPickupLocationAddress() @ OracleOrderDAO ", ex);
			throw new EtccException(
					"Error running getPickupLocationAddress(BigDecimal pickupLocationId) : ");
		} finally {
			closeConnection();
		}// end of try-catch-finally()
		logger.info("Leaving getPickupLocationAddress() @ OracleOrderDAO");
		return fullAddress.toString();
	}// end of getPickupLocationAddress()

}// end of OracleOrderDAO Class
