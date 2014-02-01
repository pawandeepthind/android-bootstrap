package com.app.model.itunes;

import java.io.Serializable;
import java.util.List;

public class ItunesSearchResults implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 423016180357177526L;
	private Integer resultCount;
	private List<Item> results;

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public List<Item> getResults() {
		return results;
	}

	public void setResults(List<Item> results) {
		this.results = results;
	}

}
