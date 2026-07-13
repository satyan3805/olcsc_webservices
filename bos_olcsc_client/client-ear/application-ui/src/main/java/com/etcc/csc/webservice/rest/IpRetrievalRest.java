package com.etcc.csc.webservice.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/ip")
public class IpRetrievalRest {
	private static final Logger logger = Logger.getLogger(IpRetrievalRest.class);

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Path("/getUserIp")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public String getUserIp() throws UnknownHostException {

		final Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				final String headerName = headerNames.nextElement();
				logger.info("Header Name [" + headerName + "] HeaderValue [" + httpServletRequest.getHeader(headerName)
						+ "]");
			}
		}

		String remoteAddress = httpServletRequest.getRemoteAddr();

		String xip = httpServletRequest.getHeader("X-Forwarded-For");
		if (!isGoodIp(xip)) {
			xip = httpServletRequest.getHeader("X_Forwarded_For");
			if (!isGoodIp(xip)) {
				xip = httpServletRequest.getHeader("Proxy-Client-IP");
				if (!isGoodIp(xip)) {
					xip = httpServletRequest.getHeader("CLIENTIP");
				}
			}
		}
		if (isGoodIp(xip)) {
			remoteAddress = xip;
		}

		if (remoteAddress.equals("0:0:0:0:0:0:0:1") || remoteAddress.equals("127.0.0.1")) {
			logger.info("Converting Loopback IP Address [" + remoteAddress + "] to Local Host IP Address");

			final InetAddress hostAddress = InetAddress.getLocalHost();
			remoteAddress = hostAddress.getHostAddress();
		}
		logger.info("User's Remote IP Address [" + remoteAddress + "]");

		return remoteAddress;
	}

	private boolean isGoodIp(String xip) {
		if (xip == null || xip.trim().length() < 8) {
			return false;
		} else {
			return true;
		}
	}
}
