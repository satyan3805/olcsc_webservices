package com.etcc.csc.dao.oracle;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dao.CountryDAO;
import com.etcc.csc.dto.CountryDTO;
import com.etcc.csc.plsql.OLC_COUNTRY_CODE_REC;

/**
 * Oracle implementation of the CountryDAO.
 * @author Stephen Davidson
 */
public class OracleCountryDAO extends CountryDAO{

    protected static CountryDTO getCountryByName(final String name) throws EtccException{
        return CountryDAO.getCountryByName(name);
    }

    /**
     * @see com.etcc.csc.dao.CountryDAO#loadCountries()
     */
    @Override
    protected Collection<CountryDTO> loadCountries() throws EtccException {
        Collection<CountryDTO> result = null;
        CallableStatement cstmt = null;
        try {
            cstmt = this.conn.prepareCall("{? = call OLCSC_UTIL.get_country_dpdn(?,?)}");
            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_COUNTRY_CODE_REC", OLC_COUNTRY_CODE_REC.class);
            this.conn.setTypeMap(typeMap);

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_COUNTRY_CODE_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            Array countries =  (Array)cstmt.getObject(2);
            Object array[] = (Object[]) countries.getArray();
            if (array!=null) {
                result = new ArrayList<CountryDTO>();
                for (int i=0; i<array.length; i++) {
                    //result.add(((OlcSQuestionsRec)array[i]).getSecurityQuestion());
                    result.add(createObject((OLC_COUNTRY_CODE_REC)array[i]));
                }
            }
        }catch(SQLException t) {
            throw new EtccException("error getting security questions "+t, t)    ;
        }finally{
            close(cstmt);
        }

        return result;
    }

    private CountryDTO createObject(OLC_COUNTRY_CODE_REC rec) throws SQLException {
        CountryDTO ctdto = new CountryDTO();
        if (rec != null) {
            ctdto.setCountryCode(rec.getCOUNTRY_CODE());
            ctdto.setCountry(rec.getCOUNTRY_NAME());
        }
        return ctdto;
    }

}
