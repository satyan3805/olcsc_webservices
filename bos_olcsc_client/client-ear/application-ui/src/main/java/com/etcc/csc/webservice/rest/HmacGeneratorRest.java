package com.etcc.csc.webservice.rest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import org.apache.commons.lang.StringUtils;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.App;
import com.etcc.csc.webservice.rest.dto.GetRequestID;
import com.google.gson.Gson;

@Path("/hmac")
public class HmacGeneratorRest {

	private static final Logger logger = Logger.getLogger(HmacGeneratorRest.class);

	private static final String FIRST_DATA_CCRMA_WEB_COMPANY_CODE = "FIRST_DATA_CCRMA_WEB_COMPANY_CODE";
	private static final String FIRST_DATA_CCRMA_WEB_ACCOUNT_ID = "FIRST_DATA_CCRMA_WEB_ACCOUNT_ID";

	private static final String FIRST_DATA_HMAC_SHA256 = "FIRST_DATA_HMAC_SHA256";
	private static final String FIRST_DATA_HMAC_URL = "FIRST_DATA_HMAC_URL";
	private static final String FIRST_DATA_AUTH_CODE = "FIRST_DATA_AUTH_CODE";

	private static final String FIRST_DATA_TRANSACTION_TYPE = "FIRST_DATA_TRANSACTION_TYPE";
	private static final String FIRST_DATA_LANGUAGE = "FIRST_DATA_LANGUAGE";
	private static final String FIRST_DATA_CURRENCY_CODE = "FIRST_DATA_CURRENCY_CODE";
	private static final String FIRST_DATA_CCRMA_WEB_CUSTOMER_NAME = "FIRST_DATA_CCRMA_WEB_CUSTOMER_NAME";
	private static final String FIRST_DATA_PAYMENT_METHOD = "FIRST_DATA_PAYMENT_METHOD";
	private static final String FIRST_DATA_TRANSACTION_AMOUNT = "FIRST_DATA_TRANSACTION_AMOUNT";
	private static final String FIRST_DATA_OPEN_IN_IFRAME = "FIRST_DATA_OPEN_IN_IFRAME";
	private static final String FIRST_DATA_REDIRECT_URL = "FIRST_DATA_REDIRECT_URL";
	private static final String FIRST_DATA_CCRMA_WEB_STYLE_CSS = "FIRST_DATA_CCRMA_WEB_STYLE_CSS";
	private static final String FIRST_DATA_USER_ID_FLAG = "FIRST_DATA_USER_ID_FLAG";
	
	private static final int sucessCode = 200;
	private static final String FIRST_DATA_CCRMA_WEB_MERCHANT_ID = "FIRST_DATA_CCRMA_WEB_MERCHANT_ID";

	static Gson gson = new Gson();

	@Path("/generate")
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public String getHMACGenValue() {
		String hmacData64String = "";
		String mdresult = "";
		try {

			final String apiAuthCode = getSysParam(FIRST_DATA_AUTH_CODE);

			final String accountId = getSysParam(FIRST_DATA_CCRMA_WEB_ACCOUNT_ID);
			final String url = getSysParam(FIRST_DATA_HMAC_URL);
			final String HMAC_SHA256 = getSysParam(FIRST_DATA_HMAC_SHA256);
			final String timestamp = Long.toString(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
			final String nonce = UUID.randomUUID().toString();
			final String contentToEncode = getRequestId();
			logger.info("contentToEncode-->" + contentToEncode);
			final byte[] content = contentToEncode.getBytes(StandardCharsets.UTF_8);

			mdresult = new String(content);
			final String signatureRawData = accountId + "POST" + url + timestamp + nonce + mdresult;
			final byte[] secretKeyByteArray = Base64.decodeBase64(apiAuthCode);
			byte[] signature = null;
			try {
				signature = signatureRawData.getBytes("UTF-8");
			} catch (UnsupportedEncodingException unsupportedEncodingException) {
				logger.error("Exception in signature "+ unsupportedEncodingException.getMessage()+" ",unsupportedEncodingException);
			}
			final Mac hMacSHA256 = Mac.getInstance(HMAC_SHA256);
			final SecretKeySpec secretKey = new SecretKeySpec(secretKeyByteArray, HMAC_SHA256);
			hMacSHA256.init(secretKey);
			final byte[] Signaturebytes = hMacSHA256.doFinal(signature);
			final String Signature64String = Base64.encodeBase64String(Signaturebytes);
			final String HmacData = accountId + ":" + Signature64String + ":" + nonce + ":" + timestamp;
			hmacData64String = new String(Base64.encodeBase64(HmacData.getBytes()));
			logger.info("HMAC: ["+hmacData64String+"]");
			return hmacData64String;
		} catch (Exception exception) {
			logger.error("Exception in getHMACGenerator-->"+ exception.getMessage(), exception);
		}
		return hmacData64String;
	}

	private String getRequestId() {
		String getRequestIdJson = "";
		try {
			final GetRequestID getRequestID = new GetRequestID();
			getRequestID.setAccountid(getSysParam(FIRST_DATA_CCRMA_WEB_ACCOUNT_ID));
			getRequestID.setTransactionType(getSysParam(FIRST_DATA_TRANSACTION_TYPE));
			getRequestID.setCustomerid(getSysParam(FIRST_DATA_CCRMA_WEB_COMPANY_CODE));
			if ("Y".equals(getSysParam(FIRST_DATA_USER_ID_FLAG))) {
				getRequestID.setUserid(getSysParam(FIRST_DATA_CCRMA_WEB_ACCOUNT_ID));
			} else {
				getRequestID.setUserid(getMMddhhmmss());
			}
			getRequestID.setLanguage(getSysParam(FIRST_DATA_LANGUAGE));
			getRequestID.setCompanycode(getSysParam(FIRST_DATA_CCRMA_WEB_COMPANY_CODE));
			getRequestID.setCurrencycode(getSysParam(FIRST_DATA_CURRENCY_CODE));
			getRequestID.setCustomername(getSysParam(FIRST_DATA_CCRMA_WEB_CUSTOMER_NAME));
			getRequestID.setPaymentmethod(getSysParam(FIRST_DATA_PAYMENT_METHOD));
			getRequestID.setTransactionamount(getSysParam(FIRST_DATA_TRANSACTION_AMOUNT));
			getRequestID.setOpeniniframe(getSysParam(FIRST_DATA_OPEN_IN_IFRAME));
			getRequestID.setRedirecturl(getSysParam(FIRST_DATA_REDIRECT_URL));
			getRequestID.setStylecss(getSysParam(FIRST_DATA_CCRMA_WEB_STYLE_CSS));

			getRequestIdJson = gson.toJson(getRequestID);

		} catch (Exception exception) {
			logger.error("Exception in getRequestID--> "+ exception.getMessage(), exception);
		}
		return getRequestIdJson;
	}

	private String getMMddhhmmss() {

		String stringDate = "";
		try {
			final Date date = new Date();
			final SimpleDateFormat DateFor = new SimpleDateFormat("MMddhhmmss");
			stringDate = DateFor.format(date);

		} catch (Exception exception) {
			logger.error("Exception in getMMddhhmmss--> "+ exception.getMessage(), exception);
		}
		return stringDate;
	}

	private String getSysParam(final String paramName) throws EtccException {
		return new App().getSysParam(paramName);
	}
	
	@Path("/getRequestId")
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public String getRequestIdFromFirstData() throws Exception {
		CloseableHttpResponse httpResponse = null;
		String jsonResponse = null;
		RequestConfig config = null;
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			final String timeOut = getSysParam("FIRST_DATA_TIME_OUT");
			
			final String serviceURL = getSysParam("FIRST_DATA_HMAC_URL");
			logger.info("Snap pay web service url " + serviceURL);
			
			config = requestConfig(timeOut);
			
			final HttpPost httpPost = new HttpPost(serviceURL);
			httpPost.setConfig(config);
			httpPost.addHeader("AccountID", getSysParam(FIRST_DATA_CCRMA_WEB_ACCOUNT_ID));
			httpPost.addHeader("Authorization", "Basic " + getAuthentication()); // MTAwMTY5NTE4NjojU3c1ZitmKHVIMzZCUyN4Sw==
			httpPost.addHeader("MerchantID", getSysParam(FIRST_DATA_CCRMA_WEB_MERCHANT_ID));
			httpPost.addHeader("Signature", "Hmac " + getHMACGenValue());
			
			final StringEntity se = new StringEntity(getRequestId());
			httpPost.setEntity(se);
			
			// Calling get request id of snap pay
			httpResponse = httpClient.execute(httpPost);
			jsonResponse = EntityUtils.toString(httpResponse.getEntity());

			if (!StringUtils.isEmpty(jsonResponse) && sucessCode == httpResponse.getStatusLine().getStatusCode()) {
				logger.info("snap pay reqno " + jsonResponse);
				// request.setAttribute("snapPayReqno", jsonResponse);
			} else {
				logger.info("Error response for first data " + jsonResponse);
			}
		} catch (Exception e) {
			logger.error("failed in consuming the web service GetRequestID", e);

		} finally {
			httpClient.close();
		}
		return jsonResponse;
	}

	private RequestConfig requestConfig(String timeOut) {
		RequestConfig config;
		int timeout = 0;
		if (!StringUtils.isEmpty(timeOut)) {
			timeout = Integer.parseInt(timeOut);
		}
		config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000)
				.setSocketTimeout(timeout * 1000).build();
		logger.info("WithOut proxy RequestConfig:: " + config + " time out value in seconds-->" + timeout);
		return config;
	}

	private String getAuthentication() {
		String auth = "";
		try {

			String apiUserId = getSysParam("FIRST_DATA_USER_ID");
			String apiPassword = getSysParam("FIRST_DATA_PASSWORD");
			String temp = apiUserId + ":" + apiPassword;
			auth = new String(Base64.encodeBase64String(temp.getBytes()));
			// logger.info("Authentication"+auth);
		} catch (Exception e) {
			logger.error("Exception in getAuthentication-->" + e);
		}
		return auth;
	}

}
