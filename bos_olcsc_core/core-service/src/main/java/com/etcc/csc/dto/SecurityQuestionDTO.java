package com.etcc.csc.dto;

import com.etcc.csc.common.BaseDTO;

public  class SecurityQuestionDTO extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3224892926924039150L;

	private String questionId;
	
	private String securityQuestion;
	
	private String transSecurityQuestion;
	
	private String answer;
	
	private String securityQuestionOrder;
	
	private String answer2;
	
	private String prevQuestionId = "";

	public SecurityQuestionDTO() {
		super();
	}




	public SecurityQuestionDTO(String questionId, String securityQuestion,
			String transSecurityQuestion, String answer,
			String securityQuestionOrder, String answer2, String prevQuestionId) {
		super();
		this.questionId = questionId;
		this.securityQuestion = securityQuestion;
		this.transSecurityQuestion = transSecurityQuestion;
		this.answer = answer;
		this.securityQuestionOrder = securityQuestionOrder;
		this.answer2 = answer2;
		this.prevQuestionId = prevQuestionId;
	}




	public SecurityQuestionDTO(String questionId, String securityQuestion,
			String transSecurityQuestion, String securityQuestionOrder) {
		super();
		this.questionId = questionId;
		this.securityQuestion = securityQuestion;
		this.transSecurityQuestion = transSecurityQuestion;
		this.securityQuestionOrder = securityQuestionOrder;
	}




	public SecurityQuestionDTO(String questionId,String answer,
			String securityQuestionOrder) {
		super();
		this.questionId = questionId;
		this.answer = answer;
		this.answer2=answer;
		this.securityQuestionOrder = securityQuestionOrder;
		this.prevQuestionId = questionId;
	}




	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getTransSecurityQuestion() {
		return transSecurityQuestion;
	}

	public void setTransSecurityQuestion(String transSecurityQuestion) {
		this.transSecurityQuestion = transSecurityQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getSecurityQuestionOrder() {
		return securityQuestionOrder;
	}

	public void setSecurityQuestionOrder(String securityQuestionOrder) {
		this.securityQuestionOrder = securityQuestionOrder;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getPrevQuestionId() {
		return prevQuestionId;
	}

	public void setPrevQuestionId(String prevQuestionId) {
		this.prevQuestionId = prevQuestionId;
	}

}
