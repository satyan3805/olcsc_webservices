package com.etcc.csc.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.service.AdminInterface;

/**
 * DAO for the Admin service.  This class has the default implementation.
 * 
 * @author Stephen Davidson
 */
public abstract class AdminDAO extends BaseDAO implements AdminInterface {
    private final Logger logger = Logger.getLogger(AdminDAO.class);

    public void insertSessionCount(String serverName, int count) throws EtccException{
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO OLC_ANONYMOUS_SESSIONS ( SERVER_ID, LAST_UPDATE, NUMBER_SESSIONS ) VALUES (?,?,?)";
            ps = this.conn.prepareStatement(query);
            ps.setString(1, serverName);
            ps.setTimestamp(2, new Timestamp(new Date().getTime()));
            ps.setInt(3, count);
            ps.executeUpdate();
        } catch (SQLException e) {
            // handle this exception quietly
            SQLException current = e;
            do {
                this.logger.warn("Exception logging Session Count [" + serverName + '/' + count + "]: " + e.getMessage());
            } while ((current = current.getNextException()) != null);
            this.logger.warn(e.getMessage(),e);
        }
    }
}
