package com.xinwei.taskmanager.model.sub;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class Resource {

	@CheckField(required = true)
	@Field("major_id")
	private int major_id;

	@Field("minor_id")
	@Transient
	private int minor_id;

	public int getMajor_id() {
		return major_id;
	}

	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}

	public int getMinor_id() {
		return minor_id;
	}

	public void setMinor_id(int minor_id) {
		this.minor_id = minor_id;
	}

}
