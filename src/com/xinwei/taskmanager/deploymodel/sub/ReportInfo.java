package com.xinwei.taskmanager.deploymodel.sub;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class ReportInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -841272394435477772L;

	@CheckField(required = true)
	@Field("minor_id")
	private int minor_id = 0;

	@Field("recource_type")
	private String resource_type = null;

	@Field("name")
	@CheckField(required = true, trim = true)
	private String name = null;

	@Field("status")
	@CheckField(required = true)
	private String status = null;

	@Field("ip")
	private String ip = null;

	@Field("epcip")
	private String epcip = null;

	@Field("pdnip")
	private String pdnip = null;

	@Field("enbName")
	private String enbName = null;

	@Field("enbName")
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
