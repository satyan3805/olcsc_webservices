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
import com.etcc.csc.dao.VehicleDAO;
import com.etcc.csc.dto.VehicleClassDTO;
import com.etcc.csc.dto.VehicleMakeDTO;
import com.etcc.csc.plsql.OLC_VEHICLE_CLASSES_REC;
import com.etcc.csc.plsql.OLC_VEH_MAKE_TYPE_ARR;
import com.etcc.csc.plsql.OLC_VEH_MAKE_TYPE_REC;
import com.etcc.csc.util.StringUtil;

/**
 * Oracle implementation of the VehicleMakeDAO.
 * @author Stephen Davidson
 * @author Milosh Boroyevich
 */
// Copied from com.etcc.csc.dao.OracleVehicleMakeDAO and com.etcc.csc.dao.OracleVehicleDAO
public class OracleVehicleDAO extends VehicleDAO {
    @Override
    public Collection<VehicleClassDTO> loadVehicleClasses() throws EtccException {
        Collection<VehicleClassDTO> result = null;
/*        StringBuilder sb = new StringBuilder();
        sb.append(" select * ");
        sb.append(" from vehicle_classes ");
*/
        CallableStatement cstmt = null;
        try {

/*
 FUNCTION GET_VEHICLE_CLASSES(P_VEHICLE_CLASSES OUT OLC_VEHICLE_CLASSES_ARR,
                              P_ERROR_ARR OUT OLC_ERROR_MSG_ARR) RETURN NUMBER;

 */

            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_VEHICLE_CLASSES_REC", OLC_VEHICLE_CLASSES_REC.class);
            cstmt = this.conn.prepareCall("{? = call Olcsc_util.GET_VEHICLE_CLASSES(?, ?)}");

            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_VEHICLE_CLASSES_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");
            cstmt.execute();

            Object[] objArray = (Object[]) cstmt.getArray(2).getArray();
            if (objArray != null && objArray.length > 0) {
                final int length = objArray.length;
                result = new ArrayList<VehicleClassDTO>(length);
                for (int i = 0; i < length; i++) {
                    VehicleClassDTO vcDto = new VehicleClassDTO();
                    OLC_VEHICLE_CLASSES_REC temp = (OLC_VEHICLE_CLASSES_REC) objArray[i];
                    vcDto.setDefaultValueFlag(StringUtil.stringToBoolean(temp.getDEFAULT_VALUE_FLAG()));
                    vcDto.setVehicleClassCode(temp.getVEHICLE_CLASS_CODE().toString());
                    vcDto.setVehicleClassDescr(temp.getVEHICLE_CLASS_DESCR());
                    vcDto.setVehicleClassLongDescr(temp.getVEHICLE_CLASS_LONG_DESCR());
                    try {
                        vcDto.setVehicleClassOrder(temp.getVEHICLE_CLASS_ORDER().shortValue());
                    } catch (Exception e) {
                        // ignore error
                    }
                    result.add(vcDto);
                }
            }

        } catch (SQLException sqle) {
            throw new EtccException("Error running getVehicleClasses: " + sqle, sqle);
        } finally {
            close(cstmt);
        }
        return result;
    }

    @Override
    public Collection<VehicleMakeDTO> loadVehicleMakes() throws EtccException {
        Collection<VehicleMakeDTO> result = null;
        CallableStatement cstmt = null;
        try {
            Map<String, Class<?>> typeMap = setTypeMap();
            typeMap.put("OL_OWNER.OLC_VEH_MAKE_TYPE_REC", OLC_VEH_MAKE_TYPE_REC.class);
            cstmt = this.conn.prepareCall("{? = call OLCSC_UTIL.get_vehicle_make_dpdn(?,?)}");
            cstmt.registerOutParameter(1, Types.SMALLINT);
            cstmt.registerOutParameter(2, Types.ARRAY, "OL_OWNER.OLC_VEH_MAKE_TYPE_ARR");
            cstmt.registerOutParameter(3, Types.ARRAY, "OL_OWNER.OLC_ERROR_MSG_ARR");

            cstmt.execute();
            Array vehicleMakes =  (Array)cstmt.getObject(2);
            Object array[] = (Object[]) vehicleMakes.getArray();
            if (array!=null) {
                final int size = array.length;
                result = new ArrayList<VehicleMakeDTO>(size);
                for (int i=0; i<size; i++) {
                    result.add(createVehicleMake((OLC_VEH_MAKE_TYPE_REC)array[i]));
                }
            }
        }catch(SQLException t) {
            throw new EtccException("Error getting vehicle makes: "+t.getMessage(), t)    ;
        }finally{
            close(cstmt);
        }

        return result;
    }

    private VehicleMakeDTO createVehicleMake(OLC_VEH_MAKE_TYPE_REC rec) throws SQLException {
        VehicleMakeDTO vehicleMakeDto = new VehicleMakeDTO();
        if (rec != null) {
        	//TODO : motocycle flag
            vehicleMakeDto.setMotorcycleFlag(false);/*rec.getMotorcycleFlag().equalsIgnoreCase("Y")?true:false*/;
            vehicleMakeDto.setVehicleMake(rec.getMAKE());
        }
        return vehicleMakeDto;
    }
}
