package com.xinwei.taskmanager.model.dto;

import com.xinwei.taskmanager.model.rpcmodel.TaskReportReqModel;

public class TaskReportFromSlave {
	private TaskReportReqModel taskReportReqModel;
	private int count;

	public TaskReportReqModel getTaskReportReqModel() {
		return taskReportReqModel;
	}

	public void setTaskReportReqModel(TaskReportReqModel taskReportReqModel) {
		this.taskReportReqModel = taskReportReqModel;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
