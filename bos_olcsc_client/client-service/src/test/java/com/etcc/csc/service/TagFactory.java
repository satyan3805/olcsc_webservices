/*
 * Copyright 2010 Electronic Transaction Consultants 
 */
package com.etcc.csc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.etcc.csc.common.AgencyEnum;
import com.etcc.csc.dto.AccountLoginDTO;
import com.etcc.csc.dto.AccountTagsDTO;
import com.etcc.csc.dto.TagAuthorityDTO;
import com.etcc.csc.dto.TagDTO;

/**
 * Creates a test implementation of <tt>TagAuthorityInterface</tt>.
 * @author Stephen Davidson
 */
public class TagFactory {
    
    // Package level. The ServiceFactoryTestImpl can override the package level access to access this method.
    static void loadImpl(ServiceFactoryTestImpl f, final TagInterface mocked) {
        Mockery context = f.getMockeryContext();
        try {
            context.checking(new Expectations(){{
                allowing(mocked).getTagAuthorities();
                will(returnValue(getTagAuthorities()));
                allowing(mocked).getAccountTags(with(any(AccountLoginDTO.class)), with(any(String.class)));
                will(returnValue(getTags()));
            }});
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Should never happen here.
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * <pre>
     * <b>ID   Identifier   Name                 Barcode</b>
     * 5    HCTRA        Houston Tollway      HCTR
     * 3    TEX          Texas Tollway Auth   TEX.
     * 6    TXDT         TX Dept Trans        TXDT
     * 7    OTA          OK Turnpike Auth     OTA.
     * </pre>
     */
    static Collection<TagAuthorityDTO> getTagAuthorities() {
        Collection<TagAuthorityDTO> auths = new ArrayList<TagAuthorityDTO>(4);
        TagAuthorityDTO auth = new TagAuthorityDTO();
        auth.setDefaultValueFlag(true);
        auth.setTaId(5);
        auth.setTagIdentifier("HCTRA");
        auth.setName("Houston Tollway");
        auth.setBarcodePrefix("HCTR");
        auths.add(auth);
        auth = new TagAuthorityDTO();
        auth.setTaId(3);
        auth.setTagIdentifier("TEX");
        auth.setName("Texas Tollway Auth");
        auth.setBarcodePrefix("TEX.");
        auths.add(auth);
        auth = new TagAuthorityDTO();
        auth.setTaId(6);
        auth.setTagIdentifier("TXDT");
        auth.setName("TX Dept Trans");
        auth.setBarcodePrefix("TXDT");
        auths.add(auth);
        auth = new TagAuthorityDTO();
        auth.setTaId(7);
        auth.setTagIdentifier("OTA");
        auth.setName("OK Turnpike Auth");
        auth.setBarcodePrefix("OTA.");
        auths.add(auth);
        return auths;
    }

    static AccountTagsDTO getTags(){
        AccountTagsDTO dto = new AccountTagsDTO();
        TagDTO tag = new TagDTO();
        tag.setAcctId(AccountFactory.POPULATED_ACCOUNT_ID);
        tag.setAgencyId(AgencyEnum.AGENCY_HARRIS_COUNTY.getCode());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        tag.setExpireDate(cal);
        tag.setAssignedDate(cal);
        tag.setLicPlate(AccountFactory.LICENSE_PLATE);
        tag.setLicState(AccountFactory.LICENSE_STATE);
        tag.setTagId("TAGID");
        tag.setAcctTagStatus("A");
        tag.setTagStatusDesc("active");
        tag.setAcctSuspended(false);
        tag.setVehicleClassCode("2");
        tag.setVehicleYear("2002");
        tag.setVehicleColor("orange");
        tag.setVehicleMake("Ferari");
        tag.setVehicleModel("Modena 320");
        tag.setNickname("cool car");
        ArrayList<TagDTO> tags = new ArrayList<TagDTO>(1);
        tags.add(tag);
        dto.setAccountTags(tags);
        dto.setMultiVehicleAllowed(true);
        return dto;
    }
}
