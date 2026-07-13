package com.etcc.csc.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Contains base dao functionality.
 */
public abstract class BaseDAO {
    protected Connection conn;
    protected ResultSet rs;
    protected PreparedStatement ps;
    protected CallableStatement cstmt;
    protected Statement stmt;

    public BaseDAO() {
    }

    /**
   * Sets the database connection for the dao.
   * @param conn
   */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
   * Returns the current database connection for this dao.
   * @return
   */
    public Connection getConnection() {
        return conn;
    }

    /**
   * Closes all open resources such as connection and resultset.
   */
    public void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
            }
        }

        if (cstmt != null) {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }
}
