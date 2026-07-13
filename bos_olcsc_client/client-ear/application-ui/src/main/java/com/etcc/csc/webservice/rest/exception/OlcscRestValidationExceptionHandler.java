package com.etcc.csc.webservice.rest.exception;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.validation.ValidationException;

public class OlcscRestValidationExceptionHandler implements ExceptionMapper<ValidationException> {

	private static final Logger logger = Logger.getLogger(OlcscRestValidationExceptionHandler.class);

	public Response toResponse(ValidationException validationException) {

		logger.error("ValidationException " + validationException);

		final List<String> errorMessagesList = Arrays.asList(validationException.getErrorMessages());

		ResultDTO resultDTO = null;
		if (errorMessagesList != null && !errorMessagesList.isEmpty()) {
			resultDTO = new ResultDTO();

			final ErrorMessageDTO[] errorArray = new ErrorMessageDTO[errorMessagesList.size()];
			for (int i = 0; i < errorMessagesList.size(); i++) {
				errorArray[i] = new ErrorMessageDTO("Validation_Error", errorMessagesList.get(i));

			}
			resultDTO.setErrors(errorArray);
			resultDTO.setValid(false);

		}
		return Response.status(Status.BAD_REQUEST).entity(resultDTO).type(MediaType.APPLICATION_JSON).build();
	}

}
