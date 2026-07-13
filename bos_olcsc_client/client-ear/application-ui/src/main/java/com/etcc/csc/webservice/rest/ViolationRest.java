package com.etcc.csc.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dao.ViolationDAO;
import com.etcc.csc.dto.CheckEligibilityResponseDTO;
import com.etcc.csc.dto.GetViolationResponseDTO;
import com.etcc.csc.dto.MakeViolationPaymentRequest;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.SearchOpenViolationResponse;
import com.etcc.csc.webservice.rest.dto.CheckEligibilityRequest;
import com.etcc.csc.webservice.rest.dto.GetViolationsRequest;
import com.etcc.csc.webservice.rest.dto.SearchOpenViolationsRequest;

@Path("/violation")
public class ViolationRest {

	private static final Logger logger = Logger.getLogger(ViolationRest.class);

	@POST
	@Path("/checkEligibility")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public CheckEligibilityResponseDTO checkEligibility(CheckEligibilityRequest checkEligibilityRequest)
			throws EtccException {

		final String licPlate = checkEligibilityRequest.getLicPlate();
		final String licState = checkEligibilityRequest.getLicState();
		final String invoiceNo = checkEligibilityRequest.getInvoiceNo();
		final Long accountId = checkEligibilityRequest.getAccountId();
		final String ipAddress = checkEligibilityRequest.getIpAddress();
		final String jsessionId = checkEligibilityRequest.getJsessionId();
		final String sourceUserName = checkEligibilityRequest.getSourceUserName();
		final String user = checkEligibilityRequest.getUser();
		final String sessionId = checkEligibilityRequest.getSessionId();
		final Integer PmtPlanId = checkEligibilityRequest.getPmtPlanId();
		final String source = checkEligibilityRequest.getSource();

		logger.info("AcctId ["+accountId+"] LoginId ["+user+"] CheckEligibilityRequest ["+checkEligibilityRequest+"]");
		final CheckEligibilityResponseDTO checkEligibilityResponseDTO = violationDAO().checkEligibility(licPlate,
				licState, invoiceNo, accountId, ipAddress, jsessionId, sourceUserName, user, sessionId, PmtPlanId,
				source);

		logger.info("AcctId ["+accountId+"] LoginId ["+user+"] CheckEligibilityResponse ["+checkEligibilityResponseDTO+"]");

		return checkEligibilityResponseDTO;
	}

	@POST
	@Path("/searchOpenViolations")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public SearchOpenViolationResponse searchOpenViolations(SearchOpenViolationsRequest searchOpenViolationsRequest)
			throws EtccException {

		final String licPlate = searchOpenViolationsRequest.getLicPlate();
		final String licState = searchOpenViolationsRequest.getLicState();

		logger.info("licPlate ["+licPlate+"] licState ["+licState+"] SearchOpenViolationsRequest ["+searchOpenViolationsRequest+"]");

		final SearchOpenViolationResponse searchOpenViolationsResponse = violationDAO().searchOpenViolations(licPlate,
				licState);

		logger.info("licPlate ["+licPlate+"] licState ["+licState+"] SearchOpenViolationResponse ["+searchOpenViolationsResponse+"] ");

		return searchOpenViolationsResponse;

	}
	
	@POST
	@Path("/getViolations")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getViolations(GetViolationsRequest getViolationsRequest) throws EtccException {

		logger.info("licPlate [" + getViolationsRequest.getLicPlate() + "] licState ["
				+ getViolationsRequest.getLicState() + "] AcctId [" + getViolationsRequest.getAccountId()
				+ "] GetViolationsRequest [" + getViolationsRequest + "]");

		try {

			final GetViolationResponseDTO violations = violationDAO().getViolationsForCCRMA(
					getViolationsRequest.getLicPlate(), getViolationsRequest.getLicState(),
					getViolationsRequest.getInvoiceNo(), getViolationsRequest.getAccountId(),
					getViolationsRequest.getIpAddress(), getViolationsRequest.getJsessionId(),
					getViolationsRequest.getSourceUserName(), getViolationsRequest.getUser(),
					getViolationsRequest.getSessionId(), getViolationsRequest.getPmtPlanId());

			logger.info("licPlate [" + getViolationsRequest.getLicPlate() + "] licState ["
					+ getViolationsRequest.getLicState() + "] AcctId [" + getViolationsRequest.getAccountId()
					+ "]GetViolationResponseDTO [" + violations + "] ");

			return Response.status(Status.OK).entity(violations).type(MediaType.APPLICATION_JSON).build();

		} catch (ClassCastException classCastException) {
			logger.error(
					"licPlate [" + getViolationsRequest.getLicPlate() + "] licState ["
							+ getViolationsRequest.getLicState() + "] accountId [" + getViolationsRequest.getAccountId()
							+ "] ClassCastException in getViolations [" + classCastException.getMessage() + "]",
					classCastException);

			return Response.status(Status.SERVICE_UNAVAILABLE)
					.entity("Unable to Process the request at this time, please retry").type(MediaType.APPLICATION_JSON)
					.build();

		} catch (Exception exception) {
			logger.error("licPlate [" + getViolationsRequest.getLicPlate() + "] licState ["
					+ getViolationsRequest.getLicState() + "] accountId [" + getViolationsRequest.getAccountId()
					+ "] Exception in getViolations	[" + exception.getMessage() + "	]", exception);

			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to Process the request")
					.type(MediaType.APPLICATION_JSON).build();

		}

	}

	private ViolationDAO violationDAO() {
		DAOFactory daoFactory = DAOFactory.getDAOFactory();
		ViolationDAO dao = daoFactory.getDAO(ViolationDAO.class);
		return dao;
	}
	
	
	@POST
	@Path("/makeViolationPayment")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public ResultDTO makeViolationPayment(MakeViolationPaymentRequest makeViolationPaymentRequest)
			throws EtccException {

		logger.info("MakeViolationPaymentRequest ["+makeViolationPaymentRequest+"]");

		final ResultDTO makeViolationPayment = violationDAO().makeViolationPayment(makeViolationPaymentRequest);

		logger.info("makeViolationPaymentResponse ["+makeViolationPayment+"] ");

		return makeViolationPayment;

	}

}
