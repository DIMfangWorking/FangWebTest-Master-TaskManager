package com.xinwei.taskmanager.model.sub;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class SequenceOfOpera {
	
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
	@CheckField(required = true)
	private int id;

	@Field("name")
	@CheckField(required = true)
	private String name;

	@Field("type")
	@CheckField(required = true)
	private String type;

	@Field("argv")
	private List<Argv> argv;

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

	public List<Argv> getArgv() {
		return argv;
	}

	public void setArgv(List<Argv> argv) {
		this.argv = argv;
	}

}
