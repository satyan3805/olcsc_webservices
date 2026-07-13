package com.etcc.csc.dao.oracle;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.SessionDAO;
import com.etcc.csc.dto.SessionDTO;

/**
 * Oracle implementation of the Session DAO. Copied from com.etcc.csc.dao.OraclenewSessionDAO.
 * 
 * @author Stephen Davidson
 * 
 */
public class OracleSessionDAO extends SessionDAO {

    // private final Logger logger = Logger.getLogger(OracleSessionDAO.class);

    public String makeSession(SessionDTO sessionDto) throws EtccException {
        CallableStatement cstmt = null;
        try {

            /*
             FUNCTION CREATE_SESSION(p_Document_id IN       NUMBER, 
                                   p_doc_type      IN       VARCHAR2, 
                                   p_Ip_address    IN       VARCHAR2 ,                        
                                   p_login_id      IN       VARCHAR2 DEFAULT NULL,
                                   p_user_env_attribute1        VARCHAR2 DEFAULT NULL,                                                      
                                   p_user_env_attribute2        VARCHAR2 DEFAULT NULL,                                                          
                                   p_user_env_attribute3        VARCHAR2 DEFAULT NULL,                                                              
                                   p_user_env_attribute4        VARCHAR2 DEFAULT NULL, 
                                   p_user_env_attribute5        VARCHAR2 DEFAULT NULL, 
                                   p_user_env_attribute6        VARCHAR2 DEFAULT NULL,
                                   p_user_env_attribute7        VARCHAR2 DEFAULT NULL, 
                                   p_user_env_attribute8        VARCHAR2 DEFAULT NULL,
                                   p_user_env_attribute9        VARCHAR2 DEFAULT NULL, 
                                   p_user_env_attribute10       VARCHAR2 DEFAULT NULL,
                                   p_called_from                VARCHAR2 DEFAULT NULL,                        
                                   o_session_id       OUT   VARCHAR2 ,                         
                                   o_error_msg_arr    OUT   OLC_error_msg_arr) RETURN NUMBER;
             */

            // create session object
            cstmt = this.conn.prepareCall("{? = call OLCSC_HTTP_SESSION.CREATE_SESSION(?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            int idx = 1;
            cstmt.registerOutParameter(idx++, Types.SMALLINT);
            cstmt.setLong(idx++, sessionDto.getDocumentId());
            cstmt.setString(idx++, sessionDto.getDocType());
            cstmt.setString(idx++, sessionDto.getIpAddress());
            cstmt.setString(idx++, sessionDto.getUserName().toUpperCase());
            // cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.setString(idx++, null);
            cstmt.registerOutParameter(idx++, Types.VARCHAR);
            cstmt.registerOutParameter(idx, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.executeUpdate();
            int result = cstmt.getInt(1);
            String sessionId = cstmt.getString(idx - 1);

            if (result != 1) {
                throw new EtccException("Unable to create account session for user: " + sessionDto.getDocumentId());
            }
            return sessionId;
        } catch (SQLException e) {
            throw new EtccException("Database Exception in createSession() for user " + sessionDto.getDocumentId() + ": "+ e, e);
        } finally {
            close(cstmt);
        }
    }

    public void destroySession(String dbSessionId) throws EtccException {
        CallableStatement cstmt = null;
        try {

            /*
             FUNCTION logout (p_session_id IN VARCHAR2,
                            o_error_msg_arr OUT OLC_ERROR_MSG_ARR ) RETURN NUMBER;
            */

            // create session object
        	setTypeMap();
            cstmt = this.conn.prepareCall("{? = call OLCSC_HTTP_SESSION.LOGOUT(?, ?)}");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.setString(2, dbSessionId);
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.executeUpdate();
            int result = cstmt.getInt(1);

            if (result != 1) {
                throw new EtccException("Unable to destroy session with id: " + dbSessionId);
            }
        } catch (SQLException e) {
            throw new EtccException("Database Exception in destroySession() for sessionid: " + dbSessionId + ": " + e, e);
        } finally {
            close(cstmt);
        }
    }
}
