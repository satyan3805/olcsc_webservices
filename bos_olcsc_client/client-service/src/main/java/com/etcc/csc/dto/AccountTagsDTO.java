/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.util.Collection;

import org.codehaus.xfire.aegis.type.java5.IgnoreProperty;

/**
 * Account Tags Data Transfer Object.  Based on the AccountTagsDTO's from the original OLSCSC Services project.
 * @author Stephen Davidson
 */
public class AccountTagsDTO extends BaseDTO {

    /**
     * Unique ID for serialization with version.
     */
    //Do not regenerate for external clients such as Idea/HCTRA.
    private static final long serialVersionUID = 174808676757029054L;
    
    private Collection<TagDTO> accountTags;
    private Collection<TagDTO> pbpTags;
    private boolean multiVehicleAllowed;

    /**
     * Gets the number of Account Tags.
     * @return the number of Account tags.
     */
    @IgnoreProperty
    public int getAccountTagCount() {
        if (this.accountTags != null)
            return this.accountTags.size();
        // else
        return 0;
    }

    /**
     * Gets the number of PBP Tags.
     * @return the number of PBP tags
     */
    @IgnoreProperty
    public int getPbpTagCount() {
        return (this.pbpTags == null ? 0 : this.pbpTags.size());
    }

    /**
     * Returns <tt>true</tt> if any of the PBP Tags are active.
     * @return <tt>true</tt> if any of the PBP Tags are active
     */
    @IgnoreProperty
    public boolean isActivePbpTagExists() {
        if (this.pbpTags != null)
            for (TagDTO tagDto : this.pbpTags)
                if (tagDto.isActivePbpTagExist()
                        || "active".equalsIgnoreCase(tagDto.getTagStatusDesc()))
                    return true;
        return false;
    }

    public void activatePbpTagFlags() {
        if (this.pbpTags != null && !this.pbpTags.isEmpty())
            for (TagDTO tagDTO : this.pbpTags)
                tagDTO.setActivePbpTagExist(true);
    }

    /**
     * Sets the Tags to "AccountSuspended".
     * @see TagDTO#setAcctSuspended(boolean)
     * @see #getAccountTags()
     * @see #getPbpTags()
     */
    public void suspendAllTags() {
        if (this.accountTags != null && !this.accountTags.isEmpty())
            for (TagDTO tagDTO : this.accountTags)
                tagDTO.setAcctSuspended(true);
        if (this.pbpTags != null && !this.pbpTags.isEmpty())
            for (TagDTO tagDTO : this.pbpTags)
                tagDTO.setAcctSuspended(true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("accountTags=");
        sb.append(this.accountTags);
        sb.append("pbpTags=");
        sb.append(this.pbpTags);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the accountTags
     */
    public Collection<TagDTO> getAccountTags() {
        return this.accountTags;
    }

    /**
     * @param accountTags the accountTags to set
     */
    public void setAccountTags(Collection<TagDTO> accountTags) {
        this.accountTags = accountTags;
    }

    /**
     * @return the multiVehicleAllowed
     */
    public boolean isMultiVehicleAllowed() {
        return this.multiVehicleAllowed;
    }

    /**
     * @param multiVehicleAllowed the multiVehicleAllowed to set
     */
    public void setMultiVehicleAllowed(boolean multiVehicleAllowed) {
        this.multiVehicleAllowed = multiVehicleAllowed;
    }

    /**
     * @return the pbpTags
     */
    public Collection<TagDTO> getPbpTags() {
        return this.pbpTags;
    }

    /**
     * @param pbpTags the pbpTags to set
     */
    public void setPbpTags(Collection<TagDTO> pbpTags) {
        this.pbpTags = pbpTags;
    }
}
