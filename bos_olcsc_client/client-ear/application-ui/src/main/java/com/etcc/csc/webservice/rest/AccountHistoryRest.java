/**
 * 
 */
package com.etcc.csc.webservice.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.AccountHistoryDAO;
import com.etcc.csc.dao.DAOFactory;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTransactionDTO;
import com.etcc.csc.dto.AccountTransactionHistoryResponse;
import com.etcc.csc.dto.TransactionRecordsDTO;
import com.etcc.csc.webservice.rest.dto.AccountTransactionHistoryRequest;
import com.etcc.csc.webservice.rest.dto.GetTransactionRecordsRequest;
import com.etcc.csc.webservice.rest.dto.ViewTransactionMainRequest;

/**
 * @author adeshpande
 *
 */
@Path("/accountHistory")
public class AccountHistoryRest {

	private static final Logger logger = Logger.getLogger(AccountHistoryRest.class);

	@POST
	@Path("/getTransactionRecords")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public TransactionRecordsDTO getTransactionRecords(GetTransactionRecordsRequest getTransactionRecordsRequest)
			throws EtccException, EtccSecurityException, ParseException {

		final AccountLoginDTO accountLoginDTO = getTransactionRecordsRequest.getAcctLoginDto();
		final Calendar startDate = stringToCalendar(getTransactionRecordsRequest.getStartDate());
		final Calendar endDate = stringToCalendar(getTransactionRecordsRequest.getEndDate());
		final String agencyId = getTransactionRecordsRequest.getAgencyId();
		final String tagId = getTransactionRecordsRequest.getTagId();
		final String transFilter = getTransactionRecordsRequest.getTransFilter();
		final String nickName = getTransactionRecordsRequest.getNickName();
		final String nickNameType = getTransactionRecordsRequest.getNickNameType();
		final String dateType = getTransactionRecordsRequest.getDateType();

		logger.info("AcctId ["+accountLoginDTO.getAcctId()+"] LoginId ["+accountLoginDTO.getLoginId()+"] getTransactionRecordsRequest ["+getTransactionRecordsRequest+"]");

		final AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
		final TransactionRecordsDTO getTransactionRecordsResponse = accountHistoryDAO.getTransactionRecords(
				accountLoginDTO, startDate, endDate, agencyId, tagId, transFilter, nickName, nickNameType, dateType);

		logger.info("AcctId ["+accountLoginDTO.getAcctId()+"] LoginId ["+accountLoginDTO.getLoginId()+"] getTransactionRecordsResponse ["+getTransactionRecordsResponse+"]");

		return getTransactionRecordsResponse;
	}

	@POST
	@Path("/viewTransactionMain")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountTransactionDTO viewTransactionMain(ViewTransactionMainRequest viewTransactionMainRequest)
			throws EtccException, EtccSecurityException, ParseException {

		final AccountLoginDTO accountLoginDTO = viewTransactionMainRequest.getAcctLoginDto();
		final Calendar startDate = stringToCalendar(viewTransactionMainRequest.getStartDate());
		final Calendar endDate = stringToCalendar(viewTransactionMainRequest.getEndDate());
		final String acctVPNType = viewTransactionMainRequest.getAcctVPNType();

		logger.info("AcctId ["+accountLoginDTO.getAcctId()+"] LoginId ["+accountLoginDTO.getLoginId()+"] viewTransactionMainRequest ["+viewTransactionMainRequest+"]");

		final AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
		final AccountTransactionDTO viewTransactionMainResponse = accountHistoryDAO.viewTransactionMain(accountLoginDTO,
				startDate, endDate, acctVPNType);

		logger.info("AcctId ["+accountLoginDTO.getAcctId()+"] LoginId ["+accountLoginDTO.getLoginId()+"] viewTransactionMainResponse ["+viewTransactionMainResponse+"]");

		return viewTransactionMainResponse;
	}

	@POST
	@Path("/accountTransactionHistory")
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	public AccountTransactionHistoryResponse getAccountTransactionHistory(
			AccountTransactionHistoryRequest accountTransactionHistoryRequest) throws EtccException, ParseException {

		logger.info("AcctId ["+accountTransactionHistoryRequest.getAcctId()+"] LoginId ["+accountTransactionHistoryRequest.getLoginId()+"] getAccountTransactionHistory ["+accountTransactionHistoryRequest+"]");
		
		final Date fromDate = stringToDate(accountTransactionHistoryRequest.getFromDate());
		final Date toDate = stringToDate(accountTransactionHistoryRequest.getToDate());

		final AccountHistoryDAO accountHistoryDAO = DAOFactory.getDAOFactory().getDAO(AccountHistoryDAO.class);
		final AccountTransactionHistoryResponse accountTransactionHistory = accountHistoryDAO
				.getAccountTransactionHistory(accountTransactionHistoryRequest.getAcctId(),
						accountTransactionHistoryRequest.getLoginType(), accountTransactionHistoryRequest.getDbSessionId(),
						accountTransactionHistoryRequest.getIpAddress(), accountTransactionHistoryRequest.getLoginId(),
						fromDate, toDate,
						accountTransactionHistoryRequest.getDateType(),
						accountTransactionHistoryRequest.getCategoryTab(),
						accountTransactionHistoryRequest.getSubCategoryTab(),
						accountTransactionHistoryRequest.getAcctVehIds(),
						accountTransactionHistoryRequest.getAcctTagIds(),
						accountTransactionHistoryRequest.getPageSize(),
						accountTransactionHistoryRequest.getPageNumber(),
						accountTransactionHistoryRequest.getSortOrder(), accountTransactionHistoryRequest.getEventId(),
						accountTransactionHistoryRequest.getParamId(),
						accountTransactionHistoryRequest.getMaxReturnRows());

		logger.info("AcctId ["+accountTransactionHistoryRequest.getAcctId()+"] LoginId ["+accountTransactionHistoryRequest.getLoginId()+"] accountTransactionHistory ["+accountTransactionHistory+"]");

		return accountTransactionHistory;
	}
	
	private Date stringToDate(String date) throws ParseException {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		final Date fromDateParse = sdf.parse(date);

		return fromDateParse;
	}

	private Calendar stringToCalendar(String calendar) throws ParseException {
		Calendar instance = Calendar.getInstance();
		instance.setTime(stringToDate(calendar));
		return instance;
	}
}
