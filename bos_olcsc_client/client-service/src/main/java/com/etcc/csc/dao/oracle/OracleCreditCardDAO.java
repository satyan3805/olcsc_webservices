/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.CreditCardDAO;
import com.etcc.csc.dto.AccountCreditCardDTO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.CreditCardDTO;
import com.etcc.csc.dto.ErrorMessageDTO;
import com.etcc.csc.dto.PaymentResultDTO;
import com.etcc.csc.dto.PaymentType;
import com.etcc.csc.plsql.OLC_CR_CARD_TYPE_REC;
import com.etcc.csc.plsql.OLC_ERROR_MSG_REC;
import com.etcc.csc.plsql.OLC_PAYMENT_INFO_REC;
import com.etcc.csc.util.StringUtil;

/**
 * Oracle Implementation of the Credit Card DAO.  Based on OracleNewCreditCardDAO from OLCSCService.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
public class OracleCreditCardDAO extends CreditCardDAO {
    private static final Logger logger = Logger.getLogger(OracleCreditCardDAO.class);

    public AccountCreditCardDTO getAccountCreditCard(AccountLoginDTO acctLoginDto)
            throws EtccException, EtccSecurityException {

        AccountCreditCardDTO ccDto = null;
        CallableStatement cstmt = null;
        final long acctId = acctLoginDto.getAcctId();
        try {
        	logger.info(new StringBuilder("getAccountCreditCard: acct=").append(acctId));
            /*
             FUNCTION Get_Payment_Info(p_doc_id           VARCHAR2,
                                       p_doc_type         VARCHAR2 DEFAULT 'AC',
                                       p_session_id       VARCHAR2,
                                       p_ip_address       VARCHAR2,
                                       p_user_id          VARCHAR2,
                                       o_payment_info     OUT olc_payment_info_rec,
                                       o_error_msg_arr    OUT olc_error_msg_arr) RETURN NUMBER ;
             */

            cstmt = this.conn.prepareCall("{? = call Olcsc_Acct_Mgmt.Get_Payment_Info(?, ?, ?, ?, ?, ?, ?)}");

            Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
            typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OLC_ERROR_MSG_REC.class);
            typeMap.put("OL_OWNER.OLC_PAYMENT_INFO_REC",
            		OLC_PAYMENT_INFO_REC.class);
            this.conn.setTypeMap(typeMap);

            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.registerOutParameter(idx++, Types.STRUCT,
                "OL_OWNER.OLC_PAYMENT_INFO_REC");
             cstmt.registerOutParameter(idx, Types.ARRAY,
                "OL_OWNER.OLC_ERROR_MSG_ARR"); // error

            cstmt.execute();

            short docExists = cstmt.getShort(1);
            if (docExists == 1) {
            	OLC_PAYMENT_INFO_REC tempRec = (OLC_PAYMENT_INFO_REC) cstmt.getObject(idx-1);
                if (tempRec != null) {
                    ccDto = new AccountCreditCardDTO();
                    ccDto.setAcctId(acctId);
                    ccDto.setAddress1(tempRec.getADDRESS_1());
                    ccDto.setAddress2(tempRec.getADDRESS_2());
                    ccDto.setCardType(CreditCardDTO.CreditCardType.valueOfType(tempRec.getCARD_TYPE().intValue()));
                    ccDto.setCardExpiresDate(tempRec.getCARD_EXPIRES());
//                    ccDto.setCardExpires(DateUtil.getMonthYear(
//                        DateUtil.timestampToCalendar(
//                        tempRec.getCardExpires())));
                    ccDto.setCardNbr(tempRec.getCARD_NBR());
                    ccDto.setCity(tempRec.getCITY());
                    ccDto.setNameOnCard(tempRec.getFIRST_NAME() + " " + tempRec.getLAST_NAME());
                    ccDto.setPlus4(tempRec.getPLUS4());
                    ccDto.setState(tempRec.getSTATE());
                    ccDto.setZipCode(tempRec.getZIPCODE());
                    // validate payment type
                    assert PaymentType.valueOfCode(tempRec.getPMT_TYPE()) == ccDto.getPaymentType() :
                    	"Unexpected Credit Card payment type of: " + tempRec.getPMT_TYPE();
                }
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running getAccountCreditCard: "
                + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return ccDto;
    }

    public PaymentResultDTO updateAccountCreditCard(
            AccountLoginDTO acctLoginDto, AccountCreditCardDTO
            acctCreditCardDto, boolean fromPaymentScreen)
            throws EtccException, EtccSecurityException {


        PaymentResultDTO paymentResultDto = new PaymentResultDTO();
        CallableStatement cstmt = null;
        try {
        	logger.info(new StringBuilder("updateAccountCreditCard: ").append(acctCreditCardDto));
/*
 FUNCTION SET_PAYMENT_INFO( p_acct_id                     NUMBER,
                              p_doc_type                  VARCHAR2 DEFAULT 'AC',
                              p_session_id                VARCHAR2 DEFAULT NULL,
                              p_ip_address                VARCHAR2 DEFAULT NULL,
                              p_user_id                   VARCHAR2 DEFAULT NULL,
                              p_payment_info              olc_payment_info_rec,
                              p_coming_from_payment      VARCHAR2 DEFAULT 'N',
                              p_rtl_trxn_id               IN OUT NUMBER  ,
                              p_pmt_amt                   IN OUT NUMBER ,
                              p_ui_call                   BOOLEAN DEFAULT TRUE,
                              o_error_msg_arr             OUT OLC_ERROR_MSG_ARR) RETURN NUMBER ;

 */


            cstmt = this.conn.prepareCall("{? = call OLCSC_ACCT_MGMT.set_payment_info(?,?,?,?,?,?,?,?,?,null,?)}");

            Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
            typeMap.put("OL_OWNER.OLC_PAYMENT_INFO_REC", OLC_PAYMENT_INFO_REC.class);
            typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OLC_ERROR_MSG_REC.class);
            this.conn.setTypeMap(typeMap);

            byte idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId());
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString());
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setObject(idx++, convertFromDto(acctCreditCardDto));
            cstmt.setString(idx++, StringUtil.booleanToString(fromPaymentScreen));
            if (acctCreditCardDto.getRetailTransId() != 0) {
                cstmt.setLong(idx, acctCreditCardDto.getRetailTransId());
            } else {
                cstmt.setNull(idx, Types.NUMERIC);
            }
            cstmt.registerOutParameter(idx++, Types.NUMERIC); // txn id
            if (fromPaymentScreen) {
                cstmt.setDouble(idx, acctCreditCardDto.getPaymentAmount());
            } else {
                cstmt.setNull(idx, Types.NUMERIC); // pmt amt
            }
            cstmt.registerOutParameter(idx++, Types.NUMERIC); // pmt amt
            cstmt.registerOutParameter(idx, Types.ARRAY,
                "OL_OWNER.OLC_ERROR_MSG_ARR"); // error

            cstmt.execute();

            short result = cstmt.getShort(1);
            paymentResultDto.setRetailTransId(cstmt.getLong(idx-2));
            paymentResultDto.setAmountDue(cstmt.getDouble(idx-1));
            if (result == 0) {
            	OLC_ERROR_MSG_REC[] errs = (OLC_ERROR_MSG_REC[]) ((Array) cstmt.getObject(idx)).getArray();
                ErrorMessageDTO[] errors = OracleUtil.convertToMessages(errs);
                if (errors == null || errors.length == 0) {
                    ErrorMessageDTO errMsgDto = new ErrorMessageDTO();
                    errMsgDto.setMessage("Invalid information entered. Please try again.");
                    paymentResultDto.addError(errMsgDto);
                } else {
                    paymentResultDto.setErrors(errors);
                }
            } else if (result == -1){
                throw new EtccSecurityException("Security exception in updateAccountCreditCard()");
            } else {
                paymentResultDto.setSuccessful(true);
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running updateAccountCreditCard: "
              + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return paymentResultDto;
    }

    @Override
    protected Collection<CreditCardDTO> loadCreditCardTypes() throws EtccException {
        Collection<CreditCardDTO> result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_UTIL.GET_CR_CARD_TYPES(?, ?)}");

            Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();
            typeMap.put("OL_OWNER.OLC_ERROR_MSG_REC", OLC_ERROR_MSG_REC.class);
            typeMap.put("OL_OWNER.OLC_CR_CARD_TYPE_REC", OLC_CR_CARD_TYPE_REC.class);
            this.conn.setTypeMap(typeMap);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_CR_CARD_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();

            if (cstmt.getInt(1) == 1) {
                Object[] objArray =
                    (Object[])cstmt.getArray(2).getArray(this.conn.getTypeMap());
                if (objArray != null && objArray.length > 0) {
                    result = new ArrayList<CreditCardDTO>();
                    for (int i = 0; i < objArray.length; i++) {
                        CreditCardDTO ccDto = new CreditCardDTO();
                        ccDto.setCardCode(((OLC_CR_CARD_TYPE_REC)objArray[i]).getCARD_CODE());
                        ccDto.setCardName(((OLC_CR_CARD_TYPE_REC)objArray[i]).getCARD_NAME());
                        result.add(ccDto);
                    }
                }
            } else {
                throw new EtccException("Error running getCreditCards");
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running getCreditCards: " + sqle,
                                    sqle);
        } finally {
            close(cstmt);
        }
        return result;
    }

    private OLC_PAYMENT_INFO_REC convertFromDto(AccountCreditCardDTO accDto)
    		throws SQLException, EtccException {
        OLC_PAYMENT_INFO_REC result = null;
        if (accDto != null) {
            result = new OLC_PAYMENT_INFO_REC();
            result.setADDRESS_1(accDto.getAddress1());
            result.setADDRESS_2(accDto.getAddress2());
            result.setCARD_TYPE(new BigDecimal(accDto.getCardType().getType()));
            Date cardExpiresDate = accDto.getCardExpiresDate();
            result.setCARD_EXPIRES(cardExpiresDate == null ? null : new Timestamp(cardExpiresDate.getTime()));
//            result.setCardExpires(DateUtil.monthYearToTimestamp(
//                accDto.getCardExpires()));
            result.setCARD_NBR(accDto.getCardNbr());
            result.setCITY(accDto.getCity());
            String nameOnCard = accDto.getNameOnCard();
            String[] names = nameOnCard.split(" ");
			result.setFIRST_NAME(names[0]);
            result.setLAST_NAME(names.length>0? names[1]:null);
            result.setPLUS4(accDto.getPlus4());
            result.setSTATE(accDto.getState());
            result.setZIPCODE(accDto.getZipCode());
            result.setPMT_TYPE("9"); // credit
        }
        return result;
    }
}
