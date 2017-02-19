package com.xinwei.taskmanager.services.basic;

import java.util.List;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.rpcmodel.TaskReportReqModel;

public interface TaskRecordService {
	public TaskRecord saveTaskRecord(TaskRecord taskRecord);

	public String updateTaskRecord(TaskRecord taskRecord);

	public TaskRecord getTaskById(int id);

	public List<TaskRecord> findAllTask();

	public String insertAll(List<TaskRecord> taskRecord);

	public int findTheLargestId();

	public TaskRecord updateTaskRecord(TaskRecord taskRecordNeedShow, TaskReportReqModel reportReqModel);
}
