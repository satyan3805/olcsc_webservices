package com.etcc.csc.webservice.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.AccountDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.BaseDTO;
import com.etcc.csc.dto.DocumentUploadResponse;
import com.etcc.csc.dto.GetAccountDocumentResponse;
import com.etcc.csc.dto.GetAccountNotificationsDocumentResponse;
import com.etcc.csc.dto.ValidateDocDownloadResponse;
import com.etcc.csc.service.App;
import com.etcc.csc.webservice.rest.dto.DocumentDownloadRequest;
import com.etcc.csc.webservice.rest.dto.DocumentUploadRequest;
import com.etcc.csc.webservice.rest.dto.GetAccountDocumentRequest;
import com.etcc.csc.webservice.rest.dto.GetAcctNotificationsDocumentsRequest;
import com.google.gson.Gson;

@Path("/document")
public class DocumentRest {

	private static final Logger logger = Logger.getLogger(DocumentRest.class);
	private static final List<String> SUPPORTED_DOC_TYPE = Arrays.asList("jpg", "jpeg", "txt", "gif", "docx", "pdf",
			"doc", "png", "xlsx", "xls");
	private static final long MAX_FILE_SIZE_IN_BYTES = 1048576;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public DocumentUploadResponse upoladDcoument(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("documentUploadRequest") String documentUploadRequest) throws EtccException {

		final Gson gson = new Gson();
		final DocumentUploadRequest documentUploadRequestObject = gson.fromJson(documentUploadRequest,
				DocumentUploadRequest.class);

		logger.info("AccountId ["+documentUploadRequestObject.getAcctId()+"] DocumentUploadRequest ["+documentUploadRequestObject+"] FileMetaData ["+fileMetaData+"]");

		final String[] FileNameAndExtension = fileMetaData.getFileName().split("\\.");

		if (!SUPPORTED_DOC_TYPE.contains(FileNameAndExtension[1])) {
			final DocumentUploadResponse response = new DocumentUploadResponse();
			response.setErrors(BaseDTO
					.convertToMessages(Arrays.asList("File type " + FileNameAndExtension[1] + " is not supported")));
			response.setValid(false);
			return response;
		}

		final StringBuilder fileNameBuilder = new StringBuilder();

		final String fileName = fileNameBuilder.append(FileNameAndExtension[0]).append("_")
				.append(documentUploadRequestObject.getAcctId()).append("_").append(getMMddhhmmss()).append(".")
				.append(FileNameAndExtension[1]).toString();

		final StringBuilder fileLocationBuilder = new StringBuilder();

		final String fileLocation = fileLocationBuilder.append(getSysParam("ACCOUNT_DOCUMENT_LOCATION")).append("/")
				.append(fileName).toString();

		writeFile(fileInputStream, fileLocation);

		final Long acctId = documentUploadRequestObject.getAcctId();
		final String acctType = documentUploadRequestObject.getLoginType();
		final String dbSessionId = documentUploadRequestObject.getDbSessionId();
		final String ipAddress = documentUploadRequestObject.getLastLoginIp();
		final String loginId = documentUploadRequestObject.getLoginId();

		final String action = documentUploadRequestObject.getAction();
		final String documentType = documentUploadRequestObject.getDocumentType();
		final String description = documentUploadRequestObject.getDescription() ;
		final Long accountDocumentId = documentUploadRequestObject.getAccountDocumentId();

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final DocumentUploadResponse documentUploadResponse = accountDao.uploadDocument(acctId, acctType, dbSessionId,
				ipAddress, loginId, fileLocation, fileName, action, documentType,
				 description, accountDocumentId);

		logger.info("acctId ["+acctId+"] DocumentUploadResponse ["+documentUploadResponse+"]");
		return documentUploadResponse;

	}

	private void writeFile(final InputStream fileInputStream, final String fileLocation) {
		OutputStream outputStream = null;
		try {
			int read = 0;
			final byte[] bytes = new byte[1024];
			long size = 0;
			outputStream = new FileOutputStream(new File(fileLocation));
			while ((read = fileInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
				size += read;
				if (size > MAX_FILE_SIZE_IN_BYTES) {
					throw new IOException("File size exceeds 1MB");
				}
			}
			outputStream.flush();
			outputStream.close();
		} catch (IOException ioException) {
			logger.error("IOException while writing file ["+fileLocation+"] IoException Message["+ioException.getMessage()+"]", ioException);
			final File file = new File(fileLocation);
			if(file.exists() && !file.isDirectory()) { 
				final boolean isDeleted = file.delete();
				logger.error("file ["+fileLocation+"] isDeleted ["+isDeleted+"]");
			}
			throw new WebApplicationException("Error while uploading file. Please try again !!");
		} finally {

			try {
				if (outputStream != null) {

					outputStream.close();
				}
			} catch (IOException ioException) {
				logger.error("Unable to close output stream ["+ioException.getMessage()+"]", ioException);
			}

			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException ioException) {
				logger.error("Unable to close fileInputStream stream ["+ioException.getMessage()+"]",ioException);
			}

		}
	}

	@POST
	@Path("/getAcctDocuments")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public GetAccountDocumentResponse getAcctDocuments(GetAccountDocumentRequest getAccountDocumentRequest)
			throws EtccException {

		logger.info("AccountId ["+getAccountDocumentRequest.getAcctId()+"] GetAccountDocumentRequest ["+getAccountDocumentRequest+"] ");
		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final GetAccountDocumentResponse accountDocument = accountDao.getAccountDocument(
				getAccountDocumentRequest.getAcctId(), getAccountDocumentRequest.getLoginType(),
				getAccountDocumentRequest.getDbSessionId(), getAccountDocumentRequest.getLastLoginIp(),
				getAccountDocumentRequest.getLoginId());

		logger.info("AccountId ["+getAccountDocumentRequest.getAcctId()+"] GetAccountDocumentResponse ["+accountDocument+"]");
		return accountDocument;
	}

	private String getSysParam(final String paramName) throws EtccException {
		return new App().getSysParam(paramName);
	}

	private String getMMddhhmmss() {

		String stringDate = "";
		try {
			final Date date = new Date();
			final SimpleDateFormat DateFor = new SimpleDateFormat("MMddhhmmss");
			stringDate = DateFor.format(date);

		} catch (Exception exception) {
			logger.error("Exception in getMMddhhmmss--> ["+exception.getMessage()+"]", exception);
		}
		return stringDate;
	}
	
	@POST
	@Path("/getAcctNotificationsDocuments")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public GetAccountNotificationsDocumentResponse getAcctNotificationsDocuments(
			GetAcctNotificationsDocumentsRequest getAcctNotificationsDocumentsRequest) throws EtccException {

		logger.info("AccountId ["+getAcctNotificationsDocumentsRequest.getAcctId()+"]  LoginId ["+getAcctNotificationsDocumentsRequest.getLoginId()+"]   GetAcctNotificationsDocumentsRequest ["+getAcctNotificationsDocumentsRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		final GetAccountNotificationsDocumentResponse acctNotificationDocuments = accountDao.getAcctNotificationDocuments(
				getAcctNotificationsDocumentsRequest.getAcctId(), getAcctNotificationsDocumentsRequest.getLoginType(),
				getAcctNotificationsDocumentsRequest.getDbSessionId(),
				getAcctNotificationsDocumentsRequest.getIpAddress(), getAcctNotificationsDocumentsRequest.getLoginId());

		logger.info("AccountId ["+getAcctNotificationsDocumentsRequest.getAcctId()+"]  LoginId ["+getAcctNotificationsDocumentsRequest.getLoginId()+"] GetAccountNotificationsDocumentResponse ["+acctNotificationDocuments+"]");

		return acctNotificationDocuments;
	}
	 
	@POST
	@Path("/download")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response downloadPdfFile(final DocumentDownloadRequest documentDownloadRequest) {

		logger.info("AccountId ["+documentDownloadRequest.getAcctId()+"]  LoginId ["+documentDownloadRequest.getLoginId()+"]  DocumentDownloadRequest ["+documentDownloadRequest+"]");

		final AccountDAO accountDao = DAOFactory.getDAOFactory().getDAO(AccountDAO.class);
		ValidateDocDownloadResponse validateDocDownload = null;
		try {
			validateDocDownload = accountDao.validateDocDownload(documentDownloadRequest.getAcctId(),
					documentDownloadRequest.getLoginType(), documentDownloadRequest.getDbSessionId(),
					documentDownloadRequest.getIpAddress(), documentDownloadRequest.getLoginId());
		} catch (EtccException etccException) {
			logger.error("Exception occurred while validating user", etccException);
		}

		if (validateDocDownload == null || !validateDocDownload.getIsAccountValidationSuccess()) {
			return Response.serverError().entity(validateDocDownload).build();
		}

		final StreamingOutput fileStream = new StreamingOutput() {

			public void write(OutputStream output) throws IOException, WebApplicationException {
				try {
					java.nio.file.Path path = Paths.get(documentDownloadRequest.getFileLocation());
					byte[] data = Files.readAllBytes(path);
					output.write(data);
					output.flush();
				} catch (Exception e) {
					throw new WebApplicationException("File Not Found !!");
				}
			}

		};
		return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition", "attachment; filename =" + documentDownloadRequest.getFileName())
				.build();
	}

}
