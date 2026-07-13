package com.etcc.csc.dao;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.FileMultiVehicleUploadDTO;
import com.etcc.csc.dto.MultiVehicleDTO;
import com.etcc.csc.dto.MultiVehicleUploadDTO;
import com.etcc.csc.service.App;
import com.etcc.csc.service.MultiVehicleUploadInterface;

/**
 * Base DAO for MultiVehicleUpload Operations.
 * @author (task 488) Stephen Davidson
 */
public abstract class MultiVehicleUploadDAO extends BaseDAO implements MultiVehicleUploadInterface {
    /**
     * Determine whether multiple vehicle upload is allowed for the specified account login.
     * @param accountLogin the user login to check
     * @return <tt>true</tt> if the account is allowed to execute multiple vehicle uploads
     * @throws EtccException If any exceptions occur talking to the Database.
     * @throws EtccSecurityException If the user is not properly logged in.
     */
    public abstract boolean isMultiVehicleUploadAllowed(AccountLoginDTO accountLogin)
        throws EtccException, EtccSecurityException;

    protected abstract MultiVehicleDTO convertVehicleRec(Object vehicle) throws SQLException;
    protected abstract MultiVehicleDTO convertVehicleRecFile(Object vehicle) throws SQLException;

    protected MultiVehicleDTO[] convertVehicleRecs(Array theInput,Long acctId) throws SQLException {
        List<MultiVehicleDTO> result = null;
        Object array[] = (Object[])theInput.getArray();
        if (array != null && array.length >= 0) {
            result = new ArrayList<MultiVehicleDTO>(array.length);
            for (int i = 0; i < array.length; i++) {
                MultiVehicleDTO vehicleDTO = convertVehicleRec(array[i]);
                try {
					Long acctVehicleId = new App().getAcctVehicleId(acctId,vehicleDTO.getLicPlate(),vehicleDTO.getLicState());
					vehicleDTO.setAcctVehicleId(acctVehicleId);
					vehicleDTO.setAcctId(acctId);
				} catch (EtccException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if (vehicleDTO != null)
                    result.add(vehicleDTO);
            }
        }
        return result == null ? null : result.toArray(new MultiVehicleDTO[result.size()]);
    }
    protected MultiVehicleDTO[] convertVehicleRecsFile(Array theInput) throws SQLException {
        List<MultiVehicleDTO> result = null;
        Object array[] = (Object[])theInput.getArray();
        if (array != null && array.length >= 0) {
            result = new ArrayList<MultiVehicleDTO>(array.length);
            for (int i = 0; i < array.length; i++) {
                MultiVehicleDTO vehicleDTO = convertVehicleRecFile(array[i]);
                if (vehicleDTO != null)
                    result.add(vehicleDTO);
            }
        }
        return result == null ? null : result.toArray(new MultiVehicleDTO[result.size()]);
    }

}
