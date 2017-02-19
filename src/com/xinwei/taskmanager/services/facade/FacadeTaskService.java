package com.xinwei.taskmanager.services.facade;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.dto.TaskReqFromWebModel;
import com.xinwei.taskmanager.model.rpcmodel.CreateAutoTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReportReqModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskResultReqModel;

public interface FacadeTaskService {
	public TaskRecord createTask(TaskReqFromWebModel taskReqModel) throws Throwable;

	public String taskResult(TaskResultReqModel resultReqModel) throws Throwable;

	public TaskRecord taskReport(TaskReportReqModel reportReqModel) throws Throwable;

	public TaskRecord createAutoTask(CreateAutoTaskModel createAutoTaskModel, TaskReqFromWebModel taskReqFromWebModel,
			boolean innerMessage) throws Throwable;

	public TaskRecord reRunAutoTask(int task_id, boolean innerMessage) throws Throwable;
}
