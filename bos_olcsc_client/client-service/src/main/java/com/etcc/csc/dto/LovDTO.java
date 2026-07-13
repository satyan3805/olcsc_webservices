/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

/**
 * This object is to be used as part of a List of Values (LOV).
 * Note: this class is not a List, but is expected to be included as part of a list.
 */
//Moved from OLCSCService module.
public class LovDTO extends BaseDTO {
    /**
     * Unique ID for serialization
     */
    //Do NOT regenerate for external clients such as Idea.
    private static final long serialVersionUID = 2446169504878442837L;

    private String id;
    private String label;

    /**
     * Constructor.
     */
    public LovDTO() { }

    /**
     * Constructor.
     * @param id The ID of this LOV.
     * @param label the label of this LOV.
     */
    public LovDTO(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("id=");
        sb.append(this.id);
        sb.append("label=");
        sb.append(this.label);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
        // end getId
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
        // end setId
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return this.label;
        // end getLabel
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
        // end setLabel
    }
}
