package com.xinwei.taskmanager.model.rpcmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.xinwei.taskmanager.model.sub.ReportInfo;
import com.xinwei.taskmanager.model.sub.SubResource;

/**
 * Resource�ظ�Task��resource��Ϣ
 *
 */
public class ResourceResTaskModel implements Serializable {

	private static final long serialVersionUID = -5689583962693570089L;
	private String result;
	private String message;
	private ResourceGot data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResourceGot getData() {
		return data;
	}

	public void setData(ResourceGot data) {
		this.data = data;
	}

}
