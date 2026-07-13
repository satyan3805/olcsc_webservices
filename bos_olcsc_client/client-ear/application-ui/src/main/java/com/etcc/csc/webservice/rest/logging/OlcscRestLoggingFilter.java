package com.etcc.csc.webservice.rest.logging;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.log4j.Logger;
import org.glassfish.jersey.message.internal.ReaderWriter;

public class OlcscRestLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final Logger logger = Logger.getLogger(OlcscRestLoggingFilter.class);

	public void filter(ContainerRequestContext requestContext) throws IOException {

		final StringBuilder requestInfoBuilder = new StringBuilder();
		requestInfoBuilder.append("	Path [ ").append(requestContext.getUriInfo().getPath()).append(" ]")
				.append(" RequestBody [ ").append(getEntityBody(requestContext)).append(" ]");
		logger.info("HTTP REQUEST " + requestInfoBuilder.toString());
	}

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		final StringBuilder responseInfoBuilder = new StringBuilder();
		responseInfoBuilder.append(" Path [ ").append(requestContext.getUriInfo().getPath()).append(" ]")
				.append(" - ResponseBody [ ").append(responseContext.getEntity()).append(" ]");
		logger.info("HTTP RESPONSE : " + responseInfoBuilder.toString());
	}

	private String getEntityBody(ContainerRequestContext requestContext) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final InputStream in = requestContext.getEntityStream();

		final StringBuilder requestBody = new StringBuilder();
		try {
			ReaderWriter.writeTo(in, out);

			byte[] requestEntity = out.toByteArray();
			if (requestEntity.length == 0) {
				requestBody.append("").append("\n");
			} else {
				requestBody.append(new String(requestEntity)).append("\n");
			}
			requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));

		} catch (IOException ioException) {
			logger.error("Unable to get RequestBody", ioException);
		}
		return requestBody.toString();
	}

}
