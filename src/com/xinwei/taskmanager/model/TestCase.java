package com.xinwei.taskmanager.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;
import com.xinwei.taskmanager.model.sub.Argv;
import com.xinwei.taskmanager.model.sub.SequenceOfOpera;

public class TestCase {

	@Field("id")
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	@CheckField(required = true, unique = true)
	private int id;

	@Field("name")
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	@CheckField(required = true, unique = true, trim = true)
	private String name;

	@Field("type")
	private String type;

	@Field("user")
	private String user;

	@Field("version")
	private String version;

	@Field("date")
	private Date date;

	@Field("update")
	private Date update;

	@Field("desc")
	private String desc;

	@Field("argv")
	private List<Argv> argv;

	@Field("sequenceOfOpera")
	private List<SequenceOfOpera> sequenceOfOpera;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public List<Argv> getArgv() {
		return argv;
	}

	public void setArgv(List<Argv> argv) {
		this.argv = argv;
	}

	public List<SequenceOfOpera> getSequenceOfOpera() {
		return sequenceOfOpera;
	}

	public void setSequenceOfOpera(List<SequenceOfOpera> sequenceOfOpera) {
		this.sequenceOfOpera = sequenceOfOpera;
	}

}
