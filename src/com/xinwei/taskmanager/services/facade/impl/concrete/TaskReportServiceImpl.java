package com.xinwei.taskmanager.services.facade.impl.concrete;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.dto.TaskReportFromSlave;
import com.xinwei.taskmanager.model.rpcmodel.TaskReportReqModel;
import com.xinwei.taskmanager.services.facade.impl.parent.TaskReportParent;

public class TaskReportServiceImpl extends TaskReportParent {

	@Override
	public TaskRecord taskReport(TaskReportReqModel reportReqModel) {
		TaskRecord taskRecord = taskRecordService.getTaskById(reportReqModel.getId());
		if (taskRecord.getStatus() == "close") {
			taskReportMap.remove(taskRecord.getId());
			return taskRecord;
		}
		this.setReportReqModel(reportReqModel);
		int key = reportReqModel.getId();
		if (!taskReportMap.containsKey(key)) {
			TaskReportFromSlave taskReportFromSlave = new TaskReportFromSlave();
			taskReportFromSlave.setCount(5);
			taskReportFromSlave.setTaskReportReqModel(reportReqModel);
			taskReportMap.put(key, taskReportFromSlave);
			taskRecord = taskRecordService.updateTaskRecord(taskRecord, reportReqModel);

			checkSlaveIsDead(key);

		} else {
			taskReportMap.get(key).setCount(5);
			taskRecord = taskRecordService.updateTaskRecord(taskRecord, reportReqModel);
		}

		return taskRecord;
	}
}
