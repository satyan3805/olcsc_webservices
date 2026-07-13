/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dao.VelcroDAO;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;

public class OracleVelcroDAO extends VelcroDAO {
    private static final Logger logger = Logger.getLogger(OracleVelcroDAO.class);

    /**
     * Retrieves the account's personal info prior to ordering velcro.
     * @return VelcroDTO
     * @throws EtccException
     * @throws EtccSecurityException
     */
    public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {
    	VelcroDTO velcroDto = null;
        CallableStatement cstmt = null;
        try {
        	logger.info(new StringBuilder("getVelcroInfo: ").append(acctLoginDto.getAcctId()));

/*
 FUNCTION Get_Velcro(p_acct_id        NUMBER,
                     p_session        VARCHAR2, 
                     p_ip_address     VARCHAR2,
                     p_user           VARCHAR2,
                     p_sets_ordered   NUMBER,
                     p_full_name      OUT VARCHAR2,                     
                     p_company_name   OUT accounts.company_name%TYPE,
                     p_address1       OUT accounts.address1%TYPE,
                     p_address2       OUT accounts.address2%TYPE,
                     p_city           OUT accounts.city%TYPE,
                     p_state          OUT accounts.state%TYPE,
                     p_zip_code       OUT accounts.zip_code%TYPE,
                     p_plus4          OUT accounts.plus4%TYPE,
                     p_bad_addr_flag  OUT accounts.bad_address_flag%TYPE,
                     p_vehicle_cnt    OUT NUMBER,
                     o_error_msg_arr  OUT olc_error_msg_arr) RETURN NUMBER;                                           

 */
            cstmt = this.conn.prepareCall("{? = call "
                + "Olcsc_Acct_Mgmt.get_velcro("
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)}");

            setTypeMap();

            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId()); 
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setInt(idx++, -1);
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // fullname
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // companyName
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // address1
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // address2
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // city
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // state
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // zipCode
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // plus4
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // bad addr flag
            cstmt.registerOutParameter(idx++, Types.INTEGER); // vehicleCnt
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR"); // error

            cstmt.execute();

            short found = cstmt.getShort(1);
            if (found == 1) {
                velcroDto = new VelcroDTO();
                velcroDto.setAcctId(acctLoginDto.getAcctId());
                velcroDto.setActiveTolltag(cstmt.getInt(idx-1));
                velcroDto.setPlus4(cstmt.getString(idx-3));
                velcroDto.setZipCode(cstmt.getString(idx-4));
                velcroDto.setState(cstmt.getString(idx-5));
                velcroDto.setCity(cstmt.getString(idx-6));
                velcroDto.setAddress2(cstmt.getString(idx-7));
                velcroDto.setAddress1(cstmt.getString(idx-8));
                velcroDto.setCompanyName(cstmt.getString(idx-9));
                velcroDto.setName(cstmt.getString(idx-10));
            } else if (found == -1) {
                throw new EtccSecurityException("Security Exception in getVelcroInfo() for Account: "
                        + acctLoginDto.getAcctId());
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running getVelcroInfo for account " + acctLoginDto.getAcctId() + ": "
                    + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
        return velcroDto;
    }

    public ResultDTO submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty) 
            throws EtccException, EtccSecurityException {

        CallableStatement cstmt = null;
        try {
            if (logger.isTraceEnabled()){
                logger.trace("submitVelcroRequest: " + acctLoginDto.getAcctId()+", qty: " + qty);
            }
    /*
    FUNCTION Get_Velcro(p_acct_id        NUMBER,
                     p_session        VARCHAR2, 
                     p_ip_address     VARCHAR2,
                     p_user           VARCHAR2,
                     p_sets_ordered   NUMBER,
                     p_full_name      OUT VARCHAR2,                     
                     p_company_name   OUT accounts.company_name%TYPE,
                     p_address1       OUT accounts.address1%TYPE,
                     p_address2       OUT accounts.address2%TYPE,
                     p_city           OUT accounts.city%TYPE,
                     p_state          OUT accounts.state%TYPE,
                     p_zip_code       OUT accounts.zip_code%TYPE,
                     p_plus4          OUT accounts.plus4%TYPE,
                     p_bad_addr_flag  OUT accounts.bad_address_flag%TYPE,
                     p_vehicle_cnt    OUT NUMBER,
                     o_error_msg_arr  OUT olc_error_msg_arr) RETURN NUMBER;                                           

    */

            setTypeMap();
            cstmt = conn.prepareCall("{? = call "
                + "Olcsc_Acct_Mgmt.get_velcro("
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)}");


            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, acctLoginDto.getAcctId()); 
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.setInt(idx++, qty);
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // fullname
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // companyName
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // address1
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // address2
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // city
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // state
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // zipCode
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // plus4
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // bad addr flag
            cstmt.registerOutParameter(idx++, Types.INTEGER); // vehicleCnt
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR"); // error

            cstmt.execute();
            short success = cstmt.getShort(1);
            if (success == 0) {
                return new ResultDTO().withErrors(OracleUtil.convertToMessages(cstmt.getArray(idx)));
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in submitVelcroRequest() for account: " + acctLoginDto.getAcctId());
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running submitVelcroRequest for account " + acctLoginDto.getAcctId() + ": "
                    + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
        return null;
    }

    /**
     * Returns the url that will generate a PDF velcro receipt.
     * @param acctLoginDto
     * @return
     * @throws EtccException
     * @throws EtccSecurityException
     */
    public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto) throws EtccException, EtccSecurityException {

        String result = null;
        CallableStatement cstmt = null;
        try {
            if (logger.isTraceEnabled()){
        	 logger.trace("getVelcroReceiptPDF: " + acctLoginDto.getAcctId());
            }
    /*
     create or replace FUNCTION Velcro_Receipt_PDF(p_report_format VARCHAR2,
                                   p_acct_id       NUMBER,
                                   p_doc_type      VARCHAR2,
                                   p_session_id    VARCHAR2, 
                                   p_ip_address    VARCHAR2,
                                   p_user_id       VARCHAR2,                               
                                   o_report_url    OUT VARCHAR2,
                                   o_err_msg_arr   OUT OLC_ERROR_MSG_ARR)

    */
            setTypeMap();
            cstmt = this.conn.prepareCall("{? = call olcsc_rep.Velcro_Receipt_PDF(?, ?, ?, ?, ?, ?, ?, ?)}");

            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setString(idx++, "PDF"); 
            cstmt.setLong(idx++, acctLoginDto.getAcctId()); 
            cstmt.setString(idx++, AccountLoginDTO.LoginType.AC.toString()); 
            cstmt.setString(idx++, acctLoginDto.getDbSessionId());
            cstmt.setString(idx++, acctLoginDto.getLastLoginIp());
            cstmt.setString(idx++, acctLoginDto.getLoginId());
            cstmt.registerOutParameter(idx++, Types.VARCHAR); // report_url
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR"); // error

            cstmt.execute();

            short success = cstmt.getShort(1);
            if (success == 1) {
                result = cstmt.getString(idx-1);
            } else if (success == -1) {
                throw new EtccSecurityException("Security Exception in getVelcroReceiptPDF for account: "
                        + acctLoginDto.getAcctId());
            }
        } catch (SQLException sqle) {
            throw new EtccException("Error running getVelcroReceiptPDF for account " + acctLoginDto.getAcctId() + ": " 
                + sqle.getMessage(), sqle);
        } finally {
            close(cstmt);
        }
        return result;
    }
}
