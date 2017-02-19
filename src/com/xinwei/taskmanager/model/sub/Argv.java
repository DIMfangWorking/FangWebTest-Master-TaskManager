package com.xinwei.taskmanager.model.sub;

import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class Argv {

	public enum Type {
		interger, string, ip
	}

	@Field("name")
	@CheckField(trim = true, required = true)
	private String name;

	@Field("type")
	@CheckField(trim = true, required = true)
	private Type type;

	@Field("value")
	private String value;

	@Field("comment")
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
