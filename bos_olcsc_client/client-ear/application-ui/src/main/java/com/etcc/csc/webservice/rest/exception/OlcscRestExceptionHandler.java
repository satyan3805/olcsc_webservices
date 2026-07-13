package com.etcc.csc.webservice.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

public class OlcscRestExceptionHandler implements ExceptionMapper<Throwable> {

	private static final Logger logger = Logger.getLogger(OlcscRestExceptionHandler.class);

	public Response toResponse(final Throwable exception) {

		logger.error("Unable to process the request", exception);

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to Process the request")
				.type(MediaType.APPLICATION_JSON).build();
	}

}