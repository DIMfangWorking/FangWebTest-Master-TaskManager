package com.xinwei.taskmanager.model.rpcmodel;

import java.util.List;

public class CreateAutoTaskModel {

	private String type;
	private String env_type;
	private String revision;
	private String code_path;
	private String bin_file;
	private String ci_type;
	private String date;
	private List<String> author;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnv_type() {
		return env_type;
	}

	public void setEnv_type(String env_type) {
		this.env_type = env_type;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getCode_path() {
		return code_path;
	}

	public void setCode_path(String code_path) {
		this.code_path = code_path;
	}

	public String getBin_file() {
		return bin_file;
	}

	public void setBin_file(String bin_file) {
		this.bin_file = bin_file;
	}

	public String getCi_type() {
		return ci_type;
	}

	public void setCi_type(String ci_type) {
		this.ci_type = ci_type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<String> getAuthor() {
		return author;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

}
