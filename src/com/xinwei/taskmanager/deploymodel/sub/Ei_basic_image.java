package com.xinwei.taskmanager.deploymodel.sub;

import java.io.Serializable;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class Ei_basic_image implements Serializable {

	private static final long serialVersionUID = 4089403879004215144L;
	
	@CheckField(required = true, trim = true)
	private String name;
	
	@CheckField(required = true, trim = true)
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
