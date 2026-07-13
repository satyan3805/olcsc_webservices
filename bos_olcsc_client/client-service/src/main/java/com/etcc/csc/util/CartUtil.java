package com.etcc.csc.util;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountEFTDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountPaymentMethodDTO;
import com.etcc.csc.payment.checkoutxml.CartItemXmlDetails;
import com.etcc.csc.payment.checkoutxml.CartXML;
import com.etcc.csc.payment.checkoutxml.PaymentCart;
import com.etcc.csc.payment.checkoutxml.PaymentConfirmation;
import com.etcc.csc.payment.checkoutxml.PaymentItem;

public class CartUtil {
	private static Logger logger = Logger.getLogger(CartUtil.class);
	private static CartUtil util = null;

	public final static String MERCHARD_ID = "01169894";

	private CartUtil() {
	}

	public static CartUtil getInstance() {
		logger.info("Entering getInstance() @ CartUtil");
		util = util == null ? new CartUtil() : util;
		logger.info("Leaving getInstance() @ CartUtil");
		return util;
	}

	public String generatecheckoutxml(Long accountId,
			List<CartXML> carts, AccountPaymentMethodDTO billingInfo, BigDecimal payAmount, Long[] ids) throws Exception {
		logger.info("Entering generatecheckoutxml() @ CartUtil");
		String xml = "";
		int paymentXmlSequenceNumber = 0;
		List<PaymentItem> paymentItems = new ArrayList<PaymentItem>();
		List<PaymentConfirmation> paymentConfirmation = new ArrayList<PaymentConfirmation>();
		List<CartItemXmlDetails> cartItems = new ArrayList<CartItemXmlDetails>();
		if (billingInfo != null && payAmount.doubleValue() > 0) {
			logger.debug("Inside if (card != null) @ generatecheckoutxml()");
			PaymentItem pymtItem = new PaymentItem();
			if (billingInfo instanceof AccountCreditCardDTO)
			{
				AccountCreditCardDTO ccDTO = (AccountCreditCardDTO) billingInfo;
				pymtItem.setPaymentForm("card");
				logger.debug("Inside else of if (StringUtil.isNotBlankOrNull(card.getAccountBillingMethodId())) @ generatecheckoutxml()");
				if (ccDTO.getToken()!=0) {
					logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getTokenId())) @ generatecheckoutxml()");
					pymtItem.setToken(ccDTO.getToken());
				}


				//if (StringUtil.isNotBlankOrNull(billingInfo.getCreditCardTypeId())) {
				logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getCreditCardTypeId())) @ generatecheckoutxml()");
				pymtItem.setCreditCardTypeId(Long.valueOf(ccDTO.getCardType().getType()));
				//}

				if (ccDTO.getCardExpiresDate()!=null) {
					logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getCardExpiryDate())) @ generatecheckoutxml()");
					SimpleDateFormat ccExpDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					String ccExpDate = ccExpDateFormat.
							format(ccDTO.getCardExpiresDate());
					pymtItem.setCreditCardExpDate(ccExpDate);
				}

				if (!StringUtil.isEmpty(ccDTO.getCardNbr())) {
					logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getCardNumber())) @ generatecheckoutxml()");
					pymtItem.setCcLast4(Integer.parseInt(ccDTO.getCardNbr().substring(ccDTO.getCardNbr().length()-4)));
				}



				if (ccDTO.getToken()!=0)
				{
					pymtItem.setToken(ccDTO.getToken());
				}
			}
			else if (billingInfo instanceof AccountEFTDTO )
			{
				AccountEFTDTO eftDto = (AccountEFTDTO) billingInfo;
				pymtItem.setPaymentForm("eft");
				pymtItem.setAbaNumber(eftDto.getRoutingNumber());
				String baNumber = eftDto.getAccountNumber();
				pymtItem.setBaLast4(baNumber.substring(baNumber.length()-4));
				pymtItem.setBankAccountNumber(baNumber);
			}

			if (billingInfo.getAccountBillingMethodId()!=null) {
				logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getAccountBillingMethodId())) @ generatecheckoutxml()");
				pymtItem.setBillingMethod(billingInfo
						.getAccountBillingMethodId());
			} else {

				if (billingInfo.getAddressId()!=null) {
					logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getAddressId())) @ generatecheckoutxml()");
					pymtItem.setAddressId(billingInfo.getAddressId());
				}
				if (billingInfo.getPhoneId()!=null) {
					logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getPersonPhoneId())) @ generatecheckoutxml()");
					pymtItem.setPersonPhoneId(billingInfo.getPhoneId());
				}
			}
			if (billingInfo.getPersonId()!=null) {
				logger.debug("Inside if (StringUtil.isNotBlankOrNull(card.getPersonId())) @ generatecheckoutxml()");
				pymtItem.setPersonId(billingInfo.getPersonId());
			}
			pymtItem.setAmount(payAmount.doubleValue());

			// 7 is for WEBSITE
			pymtItem.setDeliveryMode(new Long(7));
			pymtItem.setSequence(++paymentXmlSequenceNumber);
			paymentItems.add(pymtItem);
		}

		Iterator<CartXML> iter = carts.iterator();
		while (iter.hasNext()) {
				CartXML theItem = iter.next();
				logger.debug("Inside if (theItem.getCartItemDetailsForPayment() != null) @ generatecheckoutxml()");
				if (theItem instanceof CartItemXmlDetails) {
					logger.debug("Inside if (theItem.getCartItemDetailsForPayment() instanceof CartItemXmlDetails) @ generatecheckoutxml()");
					CartItemXmlDetails temp = (CartItemXmlDetails) theItem;
					//ngdoan - 7881 - start - Comment out the code below because of out of date
//					if (CartItemTypeEnum.APPLY_UNDIRECTED.getValue().equals(
//							temp.getItemType())
//							&& carts.size() > 1) {
//						logger.debug("Inside if (CartItemTypeEnum.APPLY_UNDIRECTED.getValue().equals(temp.getItemType()) && cart.getCartItems().size() > 1) @ generatecheckoutxml()");
//						temp.setItemType(CartItemTypeEnum.APPLY_TO_BALANCE
//								.getValue());
//					}
					//ngdoan - 7881 - end - Comment out the code below because of out of date
					temp.setSequence(++paymentXmlSequenceNumber);
					cartItems.add(temp);

				} else if (theItem instanceof PaymentItem) {
					logger.debug("Inside else if (theItem.getCartItemDetailsForPayment() instanceof PaymentItem) @ generatecheckoutxml()");
					PaymentItem pymtItemFromCart = (PaymentItem) theItem;
					pymtItemFromCart.setSequence(++paymentXmlSequenceNumber);
					paymentItems.add(pymtItemFromCart);
					//theItem.setSequenceNumber(paymentXmlSequenceNumber);
				}
		}


		long cartId = 0;
		long shiftId = 0;

//		Long[] ids = new AppDelegate().getAccountPostingAndShiftId(acctLoginDTO,1L);
		shiftId = ids[0];
		cartId = ids[1];

		logger.debug("Inside generatecheckoutxml() @ CartUtil:: SHIFT ID ::-->"
				+ shiftId);
		logger.debug("Inside generatecheckoutxml() @ CartUtil:: Cart Id ::-->"
				+ cartId);
		PaymentCart paymentCart = new PaymentCart();
		paymentCart.setAccountId(accountId);
		paymentCart.setShiftId(shiftId);
		paymentCart.setCartId(cartId);

		if (!paymentItems.isEmpty()) {
			logger.debug("Inside if (!paymentItems.isEmpty()) @ generatecheckoutxml()");
			paymentCart.setPayment(paymentItems);
		}
		// Add list of payment confirmations for reserved credit cards
		if (!paymentConfirmation.isEmpty()) {
			logger.debug("Inside if (!paymentConfirmation.isEmpty()) @ generatecheckoutxml()");
			paymentCart.setPaymentConfirmation(paymentConfirmation);
		}
		// Add list of cart items to the Payment Cart
		if (!cartItems.isEmpty()) {
			logger.debug("Inside if (!cartItems.isEmpty()) @ generatecheckoutxml()");
			paymentCart.setCartItem(cartItems);
		}
		try {
			logger.debug("Creating payment xml starts....");
			JAXBContext context = JAXBContext.newInstance(PaymentCart.class);
			logger.debug("After retrieveing JAXBContext...." + context);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			logger.debug("Validation  payment xml complete....");
			// Build the xml file
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(paymentCart, stringWriter);
			stringWriter.flush();
			xml = stringWriter.getBuffer().toString();
			stringWriter.close();
		} catch (IOException ex) {
			logger.error("Error in generatecheckoutxml() @ CartUtil ", ex);
		} catch (JAXBException jex) {
			logger.error("Error in generatecheckoutxml() @ CartUtil ", jex);
		}
		// end of try-catch()
		logger.debug("XML generated is :  ***************\n" + xml
				+ "\n********************");
		logger.info("Leaving generatecheckoutxml() @ CartUtil");
		return xml;
	}// end of generatecheckoutxml()



	/**
	 * @param accountId
	 * @param accountVehicleId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public CartItemXmlDetails createEndPostingApiCartItem(AccountLoginDTO account,
			Map items, Long eventId ) throws Exception {
		logger.info("Entering createEndPostingApiCartItem() @ CartUtil");
		CartItemXmlDetails cartItemXmlDetails = new CartItemXmlDetails();
		cartItemXmlDetails.setItemType(CartItemTypeEnum.POST_ACTION.getValue());
		cartItemXmlDetails.setAmount(0.00);
		cartItemXmlDetails.setComments(generateParam(account, items, eventId));
		cartItemXmlDetails.setPostAction("post_am_payment_api");
		return cartItemXmlDetails;
	}


	/**
	 * @param hmCartStatus
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private String generateParam(AccountLoginDTO account, Map items, Long eventId) throws Exception {
		logger.info("Entering addEndPostingCartItemToCart() @ CartUtil");

		String planDetailId = "";
		String accountTypeId = "";
		String rebillAmount = (String) items.get("rebillAmount");
		String lowBalanceAmount = (String) items.get("lowBalanceAmount");
		String intialPrepaidBalance = ""; // This should not be passed at all to
											// API
		String accountTagIds = (String) items.get("accountTagIds");
		String accountInventoryIds = (String) items.get("accountInventoryIds");
		String accountPendingTagIds = (String) items
				.get("accountpendingTagIds");
		String accountVehicleIds = (String) items.get("accountVehicleIds");

		rebillAmount = StringUtils.trimToEmpty(rebillAmount);
		lowBalanceAmount = StringUtils.trimToEmpty(lowBalanceAmount);
		intialPrepaidBalance = StringUtils.trimToEmpty(intialPrepaidBalance);
		accountTagIds = StringUtils.trimToEmpty(accountTagIds);
		accountInventoryIds = StringUtils.trimToEmpty(accountInventoryIds);
		accountVehicleIds = StringUtils.trimToEmpty(accountVehicleIds);

		if (accountTagIds.startsWith(",")) {
			logger.debug("Inside if (accountTagIds.startsWith(\",\")) @ addEndPostingCartItemToCart()");
			accountTagIds = accountTagIds.replaceFirst(",", "");
		}
		if (accountInventoryIds.startsWith(",")) {
			logger.debug("Inside if (accountInventoryIds.startsWith(\",\")) @ addEndPostingCartItemToCart()");
			accountInventoryIds = accountInventoryIds.replaceFirst(",", "");
		}

		if (accountVehicleIds.startsWith(",")) {
			logger.debug("Inside if (accountVehicleIds.startsWith(\",\")) @ addEndPostingCartItemToCart()");
			accountVehicleIds = accountVehicleIds.replaceFirst(",", "");
		}

		StringBuilder params = new StringBuilder("");
		params.append(accountTagIds).append(";");
		params.append(accountVehicleIds).append(";");
		params.append(accountInventoryIds).append(";");
		params.append(lowBalanceAmount).append(";");
		params.append(rebillAmount).append(";");
		params.append(intialPrepaidBalance).append(";");
		params.append(planDetailId).append(";");
		params.append(accountTypeId).append(";");
		params.append(eventId).append(";");

		return params.toString();
	}


}// end of CartUtil Class
