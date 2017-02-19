package com.xinwei.taskmanager.services.facade.impl.concrete;

import com.xinwei.taskmanager.model.rpcmodel.TaskResultReqModel;
import com.xinwei.taskmanager.services.facade.impl.parent.TaskResultParent;

public class TaskResultServiceImpl extends TaskResultParent {
	public TaskResultServiceImpl() {
	}

	@Override
	public String taskResult(TaskResultReqModel resultReqModel) {
		this.resultReqModel = resultReqModel;
		taskRecordForResult = taskRecordService.getTaskById(resultReqModel.getId());
		taskExecutor.execute(this);
		return "success";
	}
}
