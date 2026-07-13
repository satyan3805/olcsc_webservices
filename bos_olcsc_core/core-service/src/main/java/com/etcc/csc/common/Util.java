package com.etcc.csc.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.etcc.csc.dto.AccountTagInfoDTO;
import com.etcc.csc.dto.AccountVehicleInfoDTO;
import com.etcc.csc.dto.DMVVehicleOwnerDTO;
import com.etcc.csc.oracleerrortest.dao.ErrorMsgRec;
import com.wutka.jox.JOXBeanInputStream;

public class Util {
	private static Logger logger = Logger.getLogger(Util.class);
	private static final boolean THROW_ON_LOAD_FAILURE = true;
	private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
	private static final String SUFFIX = ".properties";

	public Util() {
	}

	/**
	 * Returns a database connection.
	 * 
	 * @return
	 */
	public static Connection getDbConnection() {
		try {
			logger.debug("Getting db connection for "
					+ Constants.APP_DATASOURCE);
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource) initialContext
					.lookup(Constants.APP_DATASOURCE);
			logger.debug("Got a connection.");
			return datasource.getConnection();
		} catch (Throwable t) {
			logger.error("Error getting a connection "
					+ Constants.APP_DATASOURCE + " " + t, t);
			throw new EtccSysException(
					"Unable to obtain data source connection " + t, t);
		}
	}

	/**
	 * Returns a connection to a specific database.
	 * 
	 * @param connName
	 * @return
	 */
	public static Connection getDbConnection(String connName) {
		try {
			logger.debug("Getting db connection for " + connName);
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource) initialContext
					.lookup(connName);
			logger.debug("Got a connection.");
			return datasource.getConnection();
		} catch (Throwable t) {
			logger.error("Error getting a connection " + connName + " " + t, t);
			throw new EtccSysException(
					"Unable to obtain data source connection " + t, t);
		}
	}

	public static Collection<ErrorMessageDTO> convertErrorMsgs(Array errors)
			throws SQLException {

		Collection<ErrorMessageDTO> result = null;
		Object array[] = (Object[]) errors.getArray();
		if (array != null && array.length >= 0) {
			result = new ArrayList<ErrorMessageDTO>();
			for (int i = 0; i < array.length; i++) {
				ErrorMsgRec msgRec = (ErrorMsgRec) array[i];
				if (!StringUtils.isBlank(msgRec.getErrMsg())) {
					ErrorMessageDTO errorDTO = new ErrorMessageDTO();
					logger.error(msgRec.getErrMsg());
					errorDTO.setMessage(msgRec.getErrMsg());
					result.add(errorDTO);
				}
			}
		}
		return result;
	}

	/**
	 * Converts a Y/N string to boolean.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean stringToBoolean(String value) {
		if (value == null) {
			return false;
		} else if (value.equalsIgnoreCase("Y")) {
			return true;
		} else {
			return false;
		}
	}

	public static String booleanToString(boolean value) {
		if (value) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * Looks up a resource named 'name' in the classpath. The resource must map
	 * to a file with .properties extention. The name is assumed to be absolute
	 * and can use either "/" or "." for package segment separation with an
	 * optional leading "/" and optional ".properties" suffix. Thus, the
	 * following names refer to the same resource:
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Resource
	 * some/pkg/Resource.properties
	 * /some/pkg/Resource
	 * /some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 *            classpath resource name [may not be null]
	 * @param loader
	 *            classloader through which to load the resource [null is
	 *            equivalent to the application loader]
	 * 
	 * @return resource converted to java.util.Properties [may be null if the
	 *         resource was not found and THROW_ON_LOAD_FAILURE is false]
	 * @throws IllegalArgumentException
	 *             if the resource was not found and THROW_ON_LOAD_FAILURE is
	 *             true
	 */
	public static Properties loadProperties(String name, ClassLoader loader) {

		if (name == null)
			throw new IllegalArgumentException("null input: name");

		if (name.startsWith("/"))
			name = name.substring(1);

		if (name.endsWith(SUFFIX))
			name = name.substring(0, name.length() - SUFFIX.length());

		Properties result = null;

		InputStream in = null;
		try {
			if (loader == null)
				loader = ClassLoader.getSystemClassLoader();

			if (LOAD_AS_RESOURCE_BUNDLE) {
				name = name.replace('/', '.');

				// Throws MissingResourceException on lookup failures:
				final ResourceBundle rb = ResourceBundle.getBundle(name,
						Locale.getDefault(), loader);

				result = new Properties();
				for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
					final String key = (String) keys.nextElement();
					final String value = rb.getString(key);

					result.put(key, value);
				}
			} else {
				name = name.replace('.', '/');

				if (!name.endsWith(SUFFIX))
					name = name.concat(SUFFIX);

				// Returns null on lookup failures:
				in = loader.getResourceAsStream(name);
				if (in != null) {
					result = new Properties();
					result.load(in); // Can throw IOException
				}
			}
		} catch (Exception e) {
			result = null;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Throwable ignore) {
					logger.equals(ignore);
				}
		}

		if (THROW_ON_LOAD_FAILURE && (result == null)) {
			throw new IllegalArgumentException("could not load ["
					+ name
					+ "]"
					+ " as "
					+ (LOAD_AS_RESOURCE_BUNDLE ? "a resource bundle"
							: "a classloader resource"));
		}

		return result;
	}

	/**
	 * A convenience overload of {@link #loadProperties(String, ClassLoader)}
	 * that uses the current thread's context classloader.
	 */
	public static Properties loadProperties(final String name) {
		return loadProperties(name, Thread.currentThread()
				.getContextClassLoader());
	}

	public String processPhoneNumber(String areaCode, String prefix,
			String suffix) {
		return StringUtils.isNotEmpty(areaCode)
				&& StringUtils.isNotEmpty(prefix)
				&& StringUtils.isNotEmpty(suffix) ? areaCode + prefix + suffix
				: "";
	}

	/**
	 * Return the full url of a request
	 * 
	 * @param request
	 * @return String
	 */
	public static String getContextPath(HttpServletRequest request) {
		String virtual_host_name = request.getHeader("Host");
		// logger.debug("print Host infor>>"+virtual_host_name);
		String original_url = request.getContextPath();

		String protocol = "http://";

		if (request.getProtocol().indexOf("HTTPS") != -1) {
			protocol = "https://";
		}

		if (virtual_host_name != null && !virtual_host_name.equals("")) {
			String html_base = protocol + virtual_host_name + original_url; // original_url.replaceAll(original_server_port,
			// virtual_host_name);
			return html_base;
		} else {
			return original_url;
		}
	}

	public static <T> T readXMLData(String xmlData, Class<T> clasz) {
		T obj = null;
		if (xmlData != null && xmlData.length() > 0) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					xmlData.getBytes());
			JOXBeanInputStream joxIn = new JOXBeanInputStream(inputStream);
			try {
				obj = (T) joxIn.readObject(clasz);
			} catch (IOException ioex) {
				logger.error(
						"SQLUtils.readXMLData caught IOException while parsing xml data:\n"
								+ xmlData, ioex);
				throw new RuntimeException(
						"SQLUtils.readXMLData() unable to parse xml data:\n"
								+ xmlData, ioex);
			} finally {
				try {
					if (joxIn != null) {
						joxIn.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException ex) {
					logger.error(
							"SQLUtils.readXMLData unable to close resources",
							ex);
				}
			}
		}
		return obj;
	}

	public static String[] processVehMessages(
			List<AccountVehicleInfoDTO> existVehicles,
			List<AccountTagInfoDTO> existTags,
			List<DMVVehicleOwnerDTO> dmvArray, Long accountId, String action) {
		String validationPassed = "Y";
		String validationMessageFromAPI = ""; // TODO populate any error
												// messages from API
		String numberOfVehicles = "0";
		String transactionChargesPresent = "N";
		String transactionAmount = "0.00";
		String dmvInfoPresent = "N";
		String make = "";
		String model = "";
		String year = "";
		String vin = "";
		String dmvVehicleId = "";
		String tagExistsOnVehicle = "N";
		String numberOfTags = "0";
		String tagTransactionChargesPresent = "N";
		String tagTransactionAmount = "0.00";
		//M6378: transfer vehicles/tags
		String sourceAccountVehicleId = null; 
		String sourceAccountTagId = null; 

		long vehFound = existVehicles == null ? 0 : existVehicles.size();
		if (vehFound > 1) {
			validationPassed = "N";
		}

		numberOfVehicles = String.valueOf(vehFound);

		if (validationPassed.equals("Y")) {
			// If vehicle validation has passed, and there is overlap, check if
			// tag exists on the vehicle
			if (existVehicles != null && existVehicles.size() == 1) {
				tagExistsOnVehicle = "Y".equalsIgnoreCase(existVehicles.get(0)
						.getTagexists()) ? "Y" : "N";
			}
		}

		long tagFound = existTags == null ? 0 : existTags.size();
		if ("N".equalsIgnoreCase(tagExistsOnVehicle)) {
			// If tag does not exist on another vehicle, check if the entered
			// tag info overlaps
			if (tagFound > 1) {
				// validation fails if there is no tag on the
				validationPassed = "N";
			}
		}
		numberOfTags = String.valueOf(tagFound);

		if (validationPassed.equals("Y")) {
			if (existVehicles != null && existVehicles.size() == 1) {
				Double vTollAmt = 0.0;
				Double transactionAmt = 0.0;

				try {
					vTollAmt = Double.parseDouble(existVehicles.get(0)
							.getTransactionvtollamount());
				} catch (Exception e) {
					vTollAmt = 0.0;
				}
				try {
					transactionAmt = Double.parseDouble(existVehicles.get(0)
							.getTransactionamount());
				} catch (Exception e) {
					transactionAmt = 0.0;
				}

				Double totalAmount = vTollAmt + transactionAmt;
				transactionChargesPresent = totalAmount > 0.0 ? "Y" : "N";
				transactionAmount = new DecimalFormat("#0.00")
						.format(transactionAmt);
				sourceAccountVehicleId = existVehicles.get(0).getAccountvehicleid()+""; 
				sourceAccountTagId = existVehicles.get(0).getAccounttagid()+""; 

			}

			if (existTags != null && existTags.size() == 1) {
				Double transactionAmt = 0.0;

				try {
					transactionAmt = Double.parseDouble(existTags.get(0)
							.getTransactionamount());
				} catch (Exception e) {
					transactionAmt = 0.0;
				}

				Double totalAmount = transactionAmt;
				tagTransactionChargesPresent = totalAmount > 0.0 ? "Y" : "N";
				tagTransactionAmount = new DecimalFormat("#0.00")
						.format(totalAmount);

			}

			if (dmvArray != null && !dmvArray.isEmpty()) {
				dmvInfoPresent = "Y";
				DMVVehicleOwnerDTO tempDMVVehOwnerDTO = new DMVVehicleOwnerDTO();
				tempDMVVehOwnerDTO = dmvArray.get(0);
				make = tempDMVVehOwnerDTO.getVehiclemake();
				model = tempDMVVehOwnerDTO.getVehiclemodel();
				year = tempDMVVehOwnerDTO.getVehicleyear();
				vin = tempDMVVehOwnerDTO.getVin();
				dmvVehicleId = tempDMVVehOwnerDTO.getDmvvehicleid();
			}
		}
		/*
		 * ///////////////remove hardcoded values after
		 * testing////////////////////////////////////////////////////
		 * validationPassed = "Y"; validationMessageFromAPI = ""; //TODO
		 * populate any error messages from API numberOfVehicles = "0";
		 * transactionChargesPresent = "Y"; transactionAmount = "1.00";
		 * dmvInfoPresent = "Y"; make = "BMW"; model = "S1"; year = "2012"; vin
		 * = "8888444411116666"; dmvVehicleId = "84512"; tagExistsOnVehicle =
		 * "N"; numberOfTags = "0"; tagTransactionChargesPresent = "Y";
		 * tagTransactionAmount = "2.00"; ///////////////remove hardcoded values
		 * after testing////////////////////////////////////////////////////
		 */

		String[] result = { validationPassed, validationMessageFromAPI,
				numberOfVehicles, transactionChargesPresent, transactionAmount,
				dmvInfoPresent, make, model, year, vin, dmvVehicleId,
				tagExistsOnVehicle, numberOfTags, tagTransactionChargesPresent,
				tagTransactionAmount,
				sourceAccountVehicleId, sourceAccountTagId};

		return result;

	}

	public static String getAmountInCurrencyFormat(Double money) {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		if (money != null)
			return numberFormat.format(money);
		return "0.00";

	}
}
