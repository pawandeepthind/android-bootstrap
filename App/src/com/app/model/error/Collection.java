package com.app.model.error;

import java.util.List;

import com.app.model.Dto;

public class Collection extends Dto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7136414739413469616L;
	private Integer count;
	private List<Items> items;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

}
