package com.xinwei.taskmanager.model.sub;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class SubResource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1155424753408535176L;

	@CheckField(required = true)
	private int minor_id = 0;

	@Field("resource_type")
	private String resource_type = null;

	@CheckField(required = true, trim = true)
	@Field("name")
	private String name = null;

	@Field("ip")
	private String ip = null;

	@Field("epcip")
	private String epcip = null;

	@Field("pdnip")
	private String pdnip = null;

	@Field("enbName")
	private String enbName = null;

	@Field("enbID")
	private int enbID = 0;

	@Field("Other")
	private String Other = "";

	public int getMinor_id() {
		return minor_id;
	}

	public void setMinor_id(int minor_id) {
		this.minor_id = minor_id;
	}

	public String getResource_type() {
		return resource_type;
	}

	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEpcip() {
		return epcip;
	}

	public void setEpcip(String epcip) {
		this.epcip = epcip;
	}

	public String getPdnip() {
		return pdnip;
	}

	public void setPdnip(String pdnip) {
		this.pdnip = pdnip;
	}

	public String getEnbName() {
		return enbName;
	}

	public void setEnbName(String enbName) {
		this.enbName = enbName;
	}

	public int getEnbID() {
		return enbID;
	}

	public void setEnbID(int enbID) {
		this.enbID = enbID;
	}

	public String getOther() {
		return Other;
	}

	public void setOther(String other) {
		Other = other;
	}
}
