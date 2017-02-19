package com.xinwei.taskmanager.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

@Document(collection = "ciconfigs")
public class CIConfig implements Serializable {

	private static final long serialVersionUID = 6114537624749253016L;

	@Field("Id")
	@CheckField(required = true)
	private int Id;

	@Field("type")
	@CheckField(required = true, trim = true)
	private String type;

	@Field("env_type")
	@CheckField(required = true, trim = true)
	private String env_type;

	@Field("Another_Name")
	@CheckField(required = true)
	private String Another_Name;

	@Field("svn_user")
	@CheckField(required = true)
	private String svn_user;

	@Field("svn_password")
	@CheckField(required = true)
	private String svn_password;

	@Field("email_notify")
	@CheckField(required = true)
	private List<String> email_notify;

	@Field("test_group")
	@CheckField(required = true)
	private String test_group;

	@Field("ci_type")
	@CheckField(required = true)
	private String ci_type;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

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

	public String getAnother_Name() {
		return Another_Name;
	}

	public void setAnother_Name(String another_name) {
		Another_Name = another_name;
	}

	public String getSvn_user() {
		return svn_user;
	}

	public void setSvn_user(String svn_user) {
		this.svn_user = svn_user;
	}

	public String getSvn_password() {
		return svn_password;
	}

	public void setSvn_password(String svn_password) {
		this.svn_password = svn_password;
	}

	public List<String> getEmail_notify() {
		return email_notify;
	}

	public void setEmail_notify(List<String> email_notify) {
		this.email_notify = email_notify;
	}

	public String getTest_group() {
		return test_group;
	}

	public void setTest_group(String test_group) {
		this.test_group = test_group;
	}

	public String getCi_type() {
		return ci_type;
	}

	public void setCi_type(String ci_type) {
		this.ci_type = ci_type;
	}

}
