package com.app.model.error;

import com.app.model.Dto;

public class Properties extends Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 127586055844965808L;
	private String field;
	private String message;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
