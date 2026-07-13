/*
 * Copyright 2010 Electronic Transaction Consultants
 */
package com.etcc.csc.it;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.meterware.httpunit.Button;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

/**
 * Base class for all Integration Tests (IT).
 * <p>Note: The HCTRA JavaScript support uses functionality and elements
 * way beyond what exists in JavaScript 1.1.
 * <a href="http://www.httpunit.org/doc/javascript-support.html">HttpUnit JavaScript support</a>
 * is very basic at present. The near-term goal is full JavaScript 1.1 support.
 * </p>
 * @author Milosh Boroyevich
 */
public abstract class BaseIntegrationTest {
    private static final Logger logger = Logger.getLogger(BaseIntegrationTest.class);

    private static final String DEFAULT_MVN_BASE_URL = "http://localhost:8080/web/";
    @SuppressWarnings("unused")
    private static final String DEFAULT_OC4J_BASE_URL = "http://localhost:7001/eztagstore/";
    private static final String URL_KEY = "ETCC_HCTRA_BASE_URL";

    private static final String OLCSC_USERNAME_P = "userName";
	private static final String OLCSC_PASSWORD_P = "password";
	private static final String OLCSC_USERNAME_V = "TEST488A";
	private static final String OLCSC_PASSWORD_V = "PASSWORD";
	private static final String OLVPS_STATE_P = "licPlateState";
	private static final String OLVPS_PLATE_P = "licPlateNbr";
	private static final String OLVPS_INVOICE_P = "invoiceId";
	private static final String OLVPS_STATE_V = "TX";
	private static final String OLVPS_PLATE_V = "P99DKB";
	private static final String OLVPS_INVOICE_V = "0301157592";

    private static String baseUrl = null;

    /**
     * Default Constructor disables scripting.
     * @see HttpUnitOptions#setScriptingEnabled(boolean)
     */
    public BaseIntegrationTest() {
        HttpUnitOptions.setScriptingEnabled(false);
    }

    /**
     * Returns the response from making a request to the specified HREF appended to the base URL.
     * @param href append this to the base URL
     * @return the response
     * @throws SAXException if there is an error parsing the retrieved page
     * @see #getBaseUrl()
     */
    protected WebResponse getResponse(String href) throws SAXException {
    	String url = (href == null ? getBaseUrl() : getBaseUrl() + href);
        try {
            return new WebConversation().getResponse(url);
        } catch (IOException e) {
            Logger.getLogger(getClass()).error("Error requesting (" + url + "): " + e.getMessage(), e);
        }
        return null;
    }

    protected WebResponse getAccountLoginResponse() throws IOException, SAXException {
    	WebResponse resp = getResponse(null);
        if (resp == null) return resp;  // could not connect to server
    	logger.trace(toString(resp.getLinks()));
    	WebForm form = resp.getForms()[0];      // select the first form in the page
    	logger.trace("Initial: " + toString(form));
    	assertEquals("accountUserForm", form.getName());
    	form.setParameter(OLCSC_USERNAME_P, OLCSC_USERNAME_V);
    	form.setParameter(OLCSC_PASSWORD_P, OLCSC_PASSWORD_V);
    	logger.trace("Final: " + toString(form));
    	return form.submit();
    }

	protected WebResponse getViolatorLoginResponse() throws IOException, SAXException {
		WebResponse resp = getResponse("violatorLoginDisplay.do");
		if (resp == null) return resp;  // could not connect to server
		logger.trace(toString(resp.getLinks()));
		WebForm form = resp.getForms()[0];      // select the first form in the page
		logger.trace("Initial: " + toString(form));
		assertEquals("", form.getName());
		form.setParameter(OLVPS_STATE_P, OLVPS_STATE_V);
		form.setParameter(OLVPS_PLATE_P, OLVPS_PLATE_V);
		form.setParameter(OLVPS_INVOICE_P, OLVPS_INVOICE_V);
		logger.trace("Final: " + toString(form));
		return form.submit();
	}


	/**
     * Returns the base URL to be used for integration tests.
     * @return the base URL
     * @see #getResponse(String)
     * @see System#getProperty(String)
     * @see System#getenv(String)
     * @see #DEFAULT_MVN_BASE_URL
     */
    private static String getBaseUrl() {
        if (baseUrl == null)
            baseUrl = System.getProperty(URL_KEY);
        if (baseUrl == null)
            baseUrl = System.getenv(URL_KEY);
        return (baseUrl == null ? DEFAULT_MVN_BASE_URL : baseUrl);
    }

    protected static void setBaseUrl(String baseUrl) {
        BaseIntegrationTest.baseUrl = baseUrl;
    }

    protected static String toString(WebForm form) {
        return toStringBuilder(form).toString();
    }

    protected static StringBuilder toStringBuilder(WebForm form) {
        StringBuilder sb = new StringBuilder("WebForm[");
        sb.append("id=").append(form.getID());
        sb.append(",name=").append(form.getName());
        sb.append(",action=").append(form.getAction());
        sb.append("]=[\n\t parameters=[");
        String[] names = form.getParameterNames();
        if (names != null && names.length > 0) {
            for (String name : names)
                sb.append(name).append('=').append(Arrays.toString(form.getParameterValues(name))).append(", ");
            sb.setLength(sb.length()-2);
        }
        sb.append("],\n\t buttons=");
        sb.append(toStringBuilder(form.getButtons()));
        return sb.append(']');
    }

    protected static String toString(Button button) {
        return "Button[" + button.getName() + '=' + button.getValue() + ']';
    }

    protected static String toString(HTMLElement[] array) {
        return toStringBuilder(array).toString();
    }

    protected static StringBuilder toStringBuilder(HTMLElement[] array) {
        StringBuilder sb = new StringBuilder();
        String delim = (array.length > 1 ? "\n" : "");
        sb.append(delim).append('[');
        for (HTMLElement e : array)
            sb.append(delim).append('\t').append(toString(e));
        sb.append(delim).append(']');
        return sb;
    }

    protected static String toString(HTMLElement e) {
    	if (e instanceof WebLink)
    		return toString((WebLink)e);
    	if (e instanceof WebForm)
    		return toString((WebForm)e);
    	if (e instanceof Button)
    		return toString((Button)e);
        return '[' + e.getTitle() + '=' + e.getText() + ']';
    }

    protected static String toString(WebLink link) {
        return "WebLink[" + link.getText() + '=' + link.getURLString() + ']';
    }
}
