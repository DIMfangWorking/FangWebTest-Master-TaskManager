package com.xinwei.taskmanager.deploymodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;
import com.xinwei.taskmanager.model.sub.ReportInfo;
import com.xinwei.taskmanager.model.sub.SubResource;

public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5991917297837396840L;

	@Field("major_id")
	@CheckField(unique = true, required = true)
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	private int major_id = 0;

	@Field("type")
	@CheckField(required = true)
	private String type = null;

	@Field("name")
	@CheckField(unique = true, required = true)
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	private String name = null;

	@Field("ip")
	@CheckField(required = true)
	private String ip = null;

	@Field("port")
	private int port = 4000;

	@Field("decs")
	private String desc = null;

	@Field("cpu")
	private double cpu = 0;

	@Field("mem")
	private double mem = 0;

	@Field("uptime")
	private int uptime = 0;

	@Field("hostname")
	private String hostname = null;

	@Field("platfrom")
	private String platfrom = "";

	@Field("register_date")
	private Date register_date = null;

	@Field("status")
	private String status = null;

	@Field("sub_resource")
	private List<SubResource> sub_resource = null;

	@Field("report_info")
	private List<ReportInfo> report_info = null;

	public int getMajor_id() {
		return major_id;
	}

	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public double getMem() {
		return mem;
	}

	public void setMem(double mem) {
		this.mem = mem;
	}

	public int getUptime() {
		return uptime;
	}

	public void setUptime(int uptime) {
		this.uptime = uptime;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPlatfrom() {
		return platfrom;
	}

	public void setPlatfrom(String platfrom) {
		this.platfrom = platfrom;
	}

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SubResource> getSub_resource() {
		return sub_resource;
	}

	public void setSub_resource(List<SubResource> sub_resource) {
		this.sub_resource = sub_resource;
	}

	public List<ReportInfo> getReport_info() {
		return report_info;
	}

	public void setReport_info(List<ReportInfo> report_info) {
		this.report_info = report_info;
	}
}
