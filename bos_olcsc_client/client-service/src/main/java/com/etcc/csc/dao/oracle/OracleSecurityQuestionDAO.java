/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dao.oracle;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.SecurityQuestionDAO;
import com.etcc.csc.dto.SecurityQuestionDTO;
import com.etcc.csc.plsql.OLC_S_QUESTIONS_REC;

/**
 * Oracle specific DAO for the Security Questions.
 */
public class OracleSecurityQuestionDAO extends SecurityQuestionDAO {

    @Override
    protected Collection<SecurityQuestionDTO> loadSecurityQuestions() throws EtccException {
        ArrayList<SecurityQuestionDTO> result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_UTIL.get_security_questions(?)}");
            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_S_QUESTIONS_REC", OLC_S_QUESTIONS_REC.class);

            cstmt.registerOutParameter(1, Types.ARRAY, "OL_OWNER.OLC_S_QUESTIONS_ARR");
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            Array securityQuestions = (Array) cstmt.getObject(1);
            Object array[] = (Object[]) securityQuestions.getArray();
            if (array != null) {
                result = new ArrayList<SecurityQuestionDTO>();
                for (Object object : array) {
                    // result.add(((OlcSQuestionsRec)array[i]).getSecurityQuestion());
                    result.add(createObject((OLC_S_QUESTIONS_REC) object));
                }
            }
        } catch (SQLException t) {
            // BUG: Does the OLC_ERROR_MSG_ARR get set? If so, where is the
            // value?
            throw new EtccException("Error getting security questions: " + t.getMessage(), t);
        } finally {
            close(cstmt);
        }
        return result;
    }

    private SecurityQuestionDTO createObject(OLC_S_QUESTIONS_REC rec) throws SQLException {
        SecurityQuestionDTO sqdto = new SecurityQuestionDTO();
        if (rec != null) {
            sqdto.setSecurityQuestion(rec.getSECURITY_QUESTION());
            sqdto.setSecurityQuestionID(rec.getSECURITY_QUESTION_ID().intValue());
        }
        return sqdto;
    }
}
