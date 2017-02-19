package com.xinwei.taskmanager.deploymodel;

import java.util.List;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

import java.util.Date;

public class TestGroup {
	public enum Type {
		simulation, real, both
	}
	
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
	
	public class TestCase {
		@Field("id")
		@CheckField(required=true)
		private int id;
		
		@Field("name")
		@CheckField(required=true)
		private String name;
		
		@Field("argv")
		private Argv argv;
		
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
		public Argv getArgv() {
			return argv;
		}
		public void setArgv(Argv argv) {
			this.argv = argv;
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
	private Type type;
	
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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
