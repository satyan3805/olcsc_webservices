package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.etcc.csc.common.EtccException;
import com.etcc.csc.dto.LovDTO;

/**
 * Lookup a List Of Values (LOV).
 * The current implementation does not interact with the database.
 * @author Milosh Boroyevich
 */
public abstract class LovFactory implements LovInterface {
	private static final Logger logger = Logger.getLogger(LovFactory.class);

	public static Collection<LovDTO> getStaticLov(String lovName) throws EtccException {
		ArrayList<LovDTO> result = null;
		if (logger.isTraceEnabled()){
			Thread.dumpStack();
			logger.trace("print out xxx LovDAO:"+lovName);
		}
		result = new ArrayList<LovDTO>();
		// Monthly statement, sort account summary by
		if ( lovName.equals(LovInterface.MSTAT_SORT_ACCT_SUM_BY)) {
		    LovDTO dto = new LovDTO("Description", "Description");
		    result.add(dto);
		    dto = new LovDTO("Quantity", "Quantity");
		    result.add(dto);
		    dto = new LovDTO("Amount", "Amount");
		    result.add(dto);
		}

		// Monthly statement, sort tag summary by
		if ( lovName.equals(LovInterface.MSTAT_SORT_TAG_SUM_BY)) {
		    LovDTO dto = new LovDTO("Tag Number", "Tag Number");
		    result.add(dto);
		    dto = new LovDTO("Description", "Description");
		    result.add(dto);
		    dto = new LovDTO("Quantity", "Quantity");
		    result.add(dto);
		    dto = new LovDTO("Amount", "Amount");
		    result.add(dto);
		}

		// Monthly statement, sort statement details by
		if ( lovName.equals(LovInterface.MSTAT_SORT_STMT_DETAILS_BY)) {
		    LovDTO dto = new LovDTO("Posted Date", "Posted Date");
		    result.add(dto);
		    dto = new LovDTO("Tag Number", "Tag Number");
		    result.add(dto);
		    dto = new LovDTO("Location", "Location");
		    result.add(dto);
		    dto = new LovDTO("Description", "Description");
		    result.add(dto);
		    dto = new LovDTO("Quantity", "Quantity");
		    result.add(dto);
		    dto = new LovDTO("Amount", "Amount");
		    result.add(dto);
		}
		return result;
	}

	public Collection<LovDTO> getLov(String lovName) throws EtccException {
		return LovFactory.getStaticLov(lovName);
	}
}
