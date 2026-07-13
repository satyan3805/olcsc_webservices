package com.etcc.csc.presentation.datatype;

import java.io.Serializable;
//weblogic upgrade cluster issues fix 
public class LoginCreationInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private String email;

    public LoginCreationInfo() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
