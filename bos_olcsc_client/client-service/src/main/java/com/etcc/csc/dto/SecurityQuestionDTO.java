/*
 * Copyright 2009 Electronic Transaction Consultants
 */
package com.etcc.csc.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for Security Questions.
 *
 */
public class SecurityQuestionDTO implements Serializable {

    /**
     * Unique Id for serialization.
     */
    //Do NOT modify for external clients such as HCTRA & Idea.
    private static final long serialVersionUID = 5571403837946524737L;
    
    private String securityQuestion;
    private int securityQuestionID;

    /**
     * Constructor.
     */
    public SecurityQuestionDTO() {
        super();
        // end <init>
    }

    /**
     * Convenience Constructor, generally used by the test code.
     * @param securityQuestionID The ID of the Security Question.
     * @param securityQuestion The Security question.
     */
    public SecurityQuestionDTO(int securityQuestionID, String securityQuestion) {
        super();
        this.securityQuestion = securityQuestion;
        this.securityQuestionID = securityQuestionID;
    }

    /**
     * @return the securityQuestion
     */
    public String getSecurityQuestion() {
        return this.securityQuestion;
        // end getSecurityQuestion
    }

    /**
     * @param securityQuestion the securityQuestion to set
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
        // end setSecurityQuestion
    }

    /**
     * @return the securityQuestionID
     */
    public int getSecurityQuestionID() {
        return this.securityQuestionID;
        // end getSecurityQuestionID
    }

    /**
     * @param securityQuestionID the securityQuestionID to set
     */
    public void setSecurityQuestionID(int securityQuestionID) {
        this.securityQuestionID = securityQuestionID;
        // end setSecurityQuestionID
    }

}// end SecurityQuestionDTO
