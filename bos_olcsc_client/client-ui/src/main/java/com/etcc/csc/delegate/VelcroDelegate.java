/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.delegate;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.common.EtccSecurityException;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.ResultDTO;
import com.etcc.csc.dto.VelcroDTO;
import com.etcc.csc.service.ServiceFactory;
import com.etcc.csc.service.VelcroInterface;

/**
 * Wrapper between the web service stub and the web service client.
 */
public class VelcroDelegate implements VelcroInterface {
    public VelcroDTO getVelcroInfo(AccountLoginDTO acctLoginDto) 
        throws EtccException, EtccSecurityException {
        try {
//            VelcroWS_Service service = new VelcroWS_ServiceLocator();
//            VelcroWS_PortType port = service.getVelcroWSSoapHttpPort();
//            com.etcc.csc.service.types.AccountLoginDTO paramAcctLoginDto
//                = new com.etcc.csc.service.types.AccountLoginDTO();
//            if (acctLoginDto != null) {
//                BeanUtils.copyProperties(paramAcctLoginDto, acctLoginDto);
//            }
//            GetVelcroInfo param = new GetVelcroInfo(paramAcctLoginDto);
//            Object result = port.getVelcroInfo(param).getResult();
//            VelcroDTO velcroDto = null;
//            if (result != null) {
//                velcroDto = new VelcroDTO();
//                BeanUtils.copyProperties(velcroDto, result);
//            }
//            return velcroDto;
//            return new Velcro().getVelcroInfo(acctLoginDto);
        	return stub().getVelcroInfo(acctLoginDto);
        } catch (EtccSecurityException se) {
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            throw new EtccException("Error running getVelcroInfo: " + t, t);
        }
    }

    public ResultDTO submitVelcroRequest(AccountLoginDTO acctLoginDto, int qty) 
        throws EtccException, EtccSecurityException {
        
        try {
//            VelcroWS_Service service = new VelcroWS_ServiceLocator();
//            VelcroWS_PortType port = service.getVelcroWSSoapHttpPort();
//            com.etcc.csc.service.types.AccountLoginDTO paramAcctLoginDto
//                = new com.etcc.csc.service.types.AccountLoginDTO();
//            if (acctLoginDto != null) {
//                BeanUtils.copyProperties(paramAcctLoginDto, acctLoginDto);
//            }
//            SubmitVelcroRequest param = new SubmitVelcroRequest(
//                paramAcctLoginDto, qty);
//            Object[] result = port.submitVelcroRequest(param).getResult();
//            if (result != null) {
//                return Arrays.asList(result);
//            }
//            return null;
//            return new Velcro().submitVelcroRequest(acctLoginDto, qty);
        	return stub().submitVelcroRequest(acctLoginDto, qty);
        } catch (EtccSecurityException se) {
            throw new EtccSecurityException(se);
        } catch (Throwable t) {
            throw new EtccException("Error running submitVelcroRequest: " + t, 
                t);
        }
    }
    
	public String getVelcroReceiptPDF(AccountLoginDTO acctLoginDto)
        throws EtccException, EtccSecurityException {
		try {
//        VelcroWS_Service service = new VelcroWS_ServiceLocator();
//        VelcroWS_PortType port = service.getVelcroWSSoapHttpPort();
//        com.etcc.csc.service.types.AccountLoginDTO paramAcctLoginDto
//            = new com.etcc.csc.service.types.AccountLoginDTO();
//        if (acctLoginDto != null) {
//            BeanUtils.copyProperties(paramAcctLoginDto, acctLoginDto);
//        }
//        GetVelcroReceiptPDF param = new GetVelcroReceiptPDF(
//            paramAcctLoginDto);
//        return port.getVelcroReceiptPDF(param).getResult();
//        return new Velcro().getVelcroReceiptPDF(acctLoginDto);
			return stub().getVelcroReceiptPDF(acctLoginDto);
		} catch (EtccSecurityException se) {
			throw new EtccSecurityException(se);
		} catch (Throwable t) {
			throw new EtccException("Error running getVelcroReceiptPDF: " + t, t);
		}
    }

	private VelcroInterface stub() {
		return ServiceFactory.getImplementation(VelcroInterface.class);
	}
}
