package com.xinwei.taskmanager.model.sub;

import java.util.Date;
import java.util.List;

public class ResourceSnapshot {
	/* ��Դ�����ͣ���ʵ/ģ�⣩ */
	private String type = null;
	/* ��Դ��IP��ַ */
	private String ip = null;
	/* ��Դ��״̬ */
	private String status = null;
	/* ��Դ���豸�� */
	private int major_id = 0;
	/* ��Դ�ı��� */
	private String name = null;
	/* ��Դ���������������BBU+����RRU */
	private String config = null;

	private int minor_id = 0;
	private int port = 0;
	private String desc = null;
	private double cpu = 0;
	private double mem = 0;
	private int uptime = 0;
	private String hostname = null;
	private String platfrom = "";
	private Date register_date = null;
	private List<ReportInfo> report_info = null;
	private List<SubResource> sub_resource = null;

	public ResourceSnapshot() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMajor_id() {
		return major_id;
	}

	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public int getMinor_id() {
		return minor_id;
	}

	public void setMinor_id(int minor_id) {
		this.minor_id = minor_id;
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

	public List<ReportInfo> getReport_info() {
		return report_info;
	}

	public void setReport_info(List<ReportInfo> report_info) {
		this.report_info = report_info;
	}

	public List<SubResource> getSub_resource() {
		return sub_resource;
	}

	public void setSub_resource(List<SubResource> sub_resource) {
		this.sub_resource = sub_resource;
	}
}
