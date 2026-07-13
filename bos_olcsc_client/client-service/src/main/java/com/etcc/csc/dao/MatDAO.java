package com.etcc.csc.dao;

import java.util.List;

import com.etcc.csc.service.mat.CheckMatEligibilityProcResponse;
import com.etcc.csc.service.mat.MatDaoInterface;
import com.etcc.csc.service.mat.PostMatCcPaymentProcResponse;
import com.etcc.csc.service.mat.PostMatEftPaymentProcResponse;
import com.etcc.csc.service.webservice.ViolationType;
import com.etcc.olcsc.attlas.exceptions.AttlasException;
//import com.etcc.olcsc.attlas.model.ViolationProcType;

/**
 * Interface for MAT data access
 * @author vsparks
 *
 */
public abstract class MatDAO extends BaseDAO implements MatDaoInterface {
	
	
}