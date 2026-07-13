package com.etcc.csc.dao.oracle.util;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.AccountPaymentMethodDTO;


public class PersonAndAddressDAOHelper {

	
	private static final int DESC_OLCSC_ID = 8;
	private static final int PERSON_CREATE_OLC_EVENT_ID = 21042;
	private static  int ACCT_TYP_PERSONAL = 131;
	private static  int ACCT_TYP_CORPORATE = 135;
	private static  int account_type_id = 0;
	private static final String ADDR_STS_CURRENT = "C";
	private static final String ADDR_TYPE_BILLING = "B";
	private static final String i_primary_address ="N"; 
	private static final String OLCSC_USER ="OLCSC_USER";
	private static  String state_code=null;
	private static  String is_default="Y";
	private static  String is_Addr_Stnd_flg="N";
	private static PersonAndAddressDAOHelper instance = new PersonAndAddressDAOHelper();
	private final Logger logger = Logger.getLogger(PersonAndAddressDAOHelper.class);
	  
	
	private PersonAndAddressDAOHelper(){
	}
	public static PersonAndAddressDAOHelper getInstance(){
		
		return instance;
	}
	
 public Long createPersons(Connection conn, String personName , Long account_id) throws EtccException {
	 	logger.info("personName  -->"+personName);
	 	logger.info("account_id  -->"+account_id);
		Long person_Id = null;
		String l_person_type_code =null;
		CallableStatement cstmt = null;
		PreparedStatement ps = null;
		try {
			logger.info("Enter createPersons method");
			if(account_id !=null && account_id >0) {
		            String query = "SELECT account_type_id FROM accounts WHERE account_id = ?";
		            ps = conn.prepareStatement(query);
		            ps.setLong(1, account_id);
		            ResultSet rs =  ps.executeQuery();
		            if(rs.next()) {
		            	account_type_id = rs.getInt(1);
		            }      
			}
			if (account_type_id==ACCT_TYP_PERSONAL){
				l_person_type_code="I";
			}if(account_type_id==ACCT_TYP_CORPORATE){
				l_person_type_code="C";
			}
			cstmt = conn.prepareCall("{call Persons_API.Create_Persons(?, ?, ?, ?, ?, ?, ?, ?)}");
            cstmt.setInt(1, DESC_OLCSC_ID);
            cstmt.setString(2, l_person_type_code);
            cstmt.setString(3,personName); 
            cstmt.setString(4, null);
            cstmt.setString(5, null);
            cstmt.setString(6,null); 
            cstmt.setLong(7, PERSON_CREATE_OLC_EVENT_ID);
            cstmt.registerOutParameter(8,Types.DOUBLE); 
            cstmt.execute();
            person_Id = cstmt.getLong(8);
		} catch (SQLException ex) {
            throw new EtccException("Exception in create person  " + ex, ex);
        }
		 return person_Id;
	}
 public Long createAddress(Connection conn, Long person_id , AccountPaymentMethodDTO paymentMethodDTO) throws EtccException {
		Long address_Id = null;
		CallableStatement cstmt = null;
		PreparedStatement ps = null;
		java.util.Date today = new java.util.Date();
		try {
		 logger.info("Enter createAddress method");
		 if(paymentMethodDTO.getState()!=null && paymentMethodDTO.getState().length()>0) {
			 state_code = paymentMethodDTO.getState().toString();   
		 }else{
	            String query = "select st.state_code from states st where st.country_code =? and st.is_default=?";
	            ps = conn.prepareStatement(query);
	            ps.setString(1, StringUtils.isEmpty(paymentMethodDTO.getCountry())? "USA" : paymentMethodDTO.getCountry());
	            ps.setString(2, is_default);
	            ResultSet rs =  ps.executeQuery();
	            if(rs.next()) {
	            	state_code = rs.getString(1);
	            }  
		 }
		 cstmt = conn.prepareCall("{call address_maintenance_api.create_address(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
         cstmt.setLong("i_person_id",person_id);
         cstmt.setString("i_addr_line1", paymentMethodDTO.getAddress1());
         cstmt.setString("i_addr_line2", paymentMethodDTO.getAddress2());
         cstmt.setString("i_city", paymentMethodDTO.getCity());
         cstmt.setString("i_state", state_code);
         cstmt.setString("i_zipcode", paymentMethodDTO.getZip());
         cstmt.setString("i_zip_code_ext", paymentMethodDTO.getPlus4());
         cstmt.setString("i_country", StringUtils.isEmpty(paymentMethodDTO.getCountry())? "USA" : paymentMethodDTO.getCountry());
         cstmt.setInt("i_data_entry_source_id", DESC_OLCSC_ID);
         cstmt.setString("i_address_status_code", ADDR_STS_CURRENT);
         cstmt.setString("i_address_type_code", ADDR_TYPE_BILLING);
         cstmt.setString("i_address_status_reason_code", null);
         cstmt.setString("i_primary_address", i_primary_address);
         cstmt.setNull("i_end_date", Types.TIMESTAMP);
         cstmt.setNull("i_dmv_id", Types.BIGINT);
         cstmt.setDate("i_effective_date", new java.sql.Date(today.getTime()));
         cstmt.setString("i_created_by", OLCSC_USER);
         cstmt.setNull("i_shift_id", Types.BIGINT);
         cstmt.setLong("i_event_id",PERSON_CREATE_OLC_EVENT_ID);
         cstmt.registerOutParameter("o_address_id",Types.BIGINT);
         cstmt.registerOutParameter("o_error_code",Types.VARCHAR);
         cstmt.registerOutParameter("o_error_message",Types.VARCHAR);
         cstmt.registerOutParameter("o_patron_error_message",Types.VARCHAR);
		 //cstmt.setNull(24, Types.BIGINT);//24
		 cstmt.setString("i_is_addr_stand_flg1", is_Addr_Stnd_flg);
         cstmt.execute();
         address_Id = cstmt.getLong("o_address_id");
		} catch (SQLException ex) {
         throw new EtccException("Exception in create address  " + ex, ex);
     }
		 return address_Id;
	}
 
 
 
	public Long getPersonId(Connection conn, String invoiceId) throws EtccException {
		PreparedStatement ps = null;
		try {
			//String query = "select person_id from tag_owner.invoices inv where inv.invoice_number = ?";
			String query = "select person_id from tag_owner.invoices inv where inv.INVOICE_ID = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException ex) {
			throw new EtccException("Exception in get person  Id" + ex, ex);
		}
		return null;
	}
 }