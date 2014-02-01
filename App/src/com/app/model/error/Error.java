package com.app.model.error;

import java.io.Serializable;

public class Error implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5477553375493901829L;
	private String type;

	public Collection collection;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

}
