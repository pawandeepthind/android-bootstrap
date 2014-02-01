package com.app.model.error;

import java.io.Serializable;

public class Items implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -282363265381773347L;
	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
