package com.xinwei.taskmanager.services.facade.impl.parent;

import com.xinwei.taskmanager.model.TaskRecord;

public class RerunTaskParent extends CreateTaskParent{

	public TaskRecord getRerunTaskInfo(int original_id) {
		return taskRecordService.getTaskById(original_id);
	}

}
