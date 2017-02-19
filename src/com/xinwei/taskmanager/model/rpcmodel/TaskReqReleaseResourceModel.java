package com.xinwei.taskmanager.model.rpcmodel;

import java.io.Serializable;

import com.xinwei.uem.util.Convert;

/**
 * 
 * Task请求释放资源的模型
 *
 */
public class TaskReqReleaseResourceModel implements Serializable {
	private static final long serialVersionUID = -6865858661718078005L;
	private String type;
	private String major_id;
	private String minor_id;

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getMinor_id() {
		return minor_id;
	}

	public void setMinor_id(String minor_id) {
		this.minor_id = minor_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getJson(TaskReqReleaseResourceModel taskReqReleaseResourceModel) {
		return Convert.toJson(taskReqReleaseResourceModel);
	}

	@Override
	public String toString() {
		return "TaskReqReleaseResourceModel [type=" + type + ", major_id=" + major_id + ", minor_id=" + minor_id + "]";
	}
}
