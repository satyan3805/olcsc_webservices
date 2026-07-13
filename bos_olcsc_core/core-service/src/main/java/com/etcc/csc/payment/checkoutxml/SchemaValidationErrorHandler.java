package com.etcc.csc.payment.checkoutxml;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SchemaValidationErrorHandler implements ErrorHandler {
	private static Logger logger = Logger
			.getLogger(SchemaValidationErrorHandler.class);

	public void warning(SAXParseException exception) throws SAXException {
		logger.debug("\nWARNING in Payment Checkout XML Generation");
		logger.debug(
				"WARNING in Payment Checkout XML Generation"
						+ exception.getMessage(), exception);
	}

	public void error(SAXParseException exception) throws SAXException {
		logger.debug("\nERROR in Payment Checkout XML Generation");
		logger.error(
				"ERROR in Payment Checkout XML Generation"
						+ exception.getMessage(), exception);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		logger.debug("\nFATAL ERROR in Payment Checkout XML Generation");
		logger.error("FATAL ERROR in Payment Checkout XML Generation"
				+ exception.getMessage(), exception);
	}
}
