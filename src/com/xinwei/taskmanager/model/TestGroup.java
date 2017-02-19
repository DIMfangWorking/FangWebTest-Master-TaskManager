package com.xinwei.taskmanager.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class TestGroup {
	
	public class Argv {

		@CheckField(required = true)
		@Field("name")
		private String name;

		@CheckField(required = true)
		@Field("value")
		private String value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
	@Field("id")
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	@CheckField(unique=true, required=true)
	private int id;
	
	@Field("name")
	@CheckField(trim=true, unique=true)
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	private String name;
	
	@CheckField(required=true)
	@Field("type")
	private String type;
	
	@Field("user")
	private String user;
	
	@Field("date")
	private Date date;
	
	@Field("update")
	private Date update;
	
	@Field("desc")
	private String desc;
	
	@Field("testcase")
	private List<TestCase> testcase;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<TestCase> getTestcase() {
		return testcase;
	}

	public void setTestcase(List<TestCase> testcase) {
		this.testcase = testcase;
	}

}
