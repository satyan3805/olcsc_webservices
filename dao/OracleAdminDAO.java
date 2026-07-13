package com.etcc.csc.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.Util;

public class OracleAdminDAO extends AdminDAO {
	private Logger logger = Logger.getLogger(OracleAdminDAO.class);

	public OracleAdminDAO() {
	}

	public void insertSessionCount(String serverName, int count) {
		logger.info("Entering insertSessionCount() @ OracleAdminDAO");
		setConnection(Util.getDbConnection());
		try {
			String query = "INSERT INTO OLC_ANONYMOUS_SESSIONS ( SERVER_ID, LAST_UPDATE, NUMBER_SESSIONS ) VALUES (?,?,?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, serverName);
			ps.setTimestamp(2, new Timestamp(new Date().getTime()));
			ps.setInt(3, count);
			ps.executeUpdate();
		} catch (Exception ex) {
			logger.error("Error in insertSessionCount() @ OracleAdminDAO ", ex);
		} finally {
			closeConnection();
		}// end of insertSessionCount()
		logger.info("Leaving insertSessionCount() @ OracleAdminDAO");
	}// end of insertSessionCount()

}// end of OracleAdminDAO Class