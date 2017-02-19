package com.xinwei.taskmanager.services.facade.impl.concrete;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.dto.ErrorMessage;
import com.xinwei.taskmanager.model.dto.TestGroupSelection;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqResourceModel;
import com.xinwei.taskmanager.services.facade.impl.parent.RerunTaskParent;
import com.xinwei.uem.util.Convert;

public class RerunAutoTaskServiceImpl extends RerunTaskParent {

	@Override
	public void run() {
		try {
			sendTaskToSlave();
			updateOriginalTask();
		} catch (Throwable e) {
			taskFailupdateTaskRecord();
			logger.info("done function. data : ", errorMessage != null ? errorMessage.toString()
					: taskRecordGot.toJson(taskRecordGot) + ", Exception : " + e.getMessage());
			failThenReleaseResource();
		}
	}

	@Override
	public TaskRecord reRunAutoTask(int task_id, boolean innerMessage) {
		try {

			taskRecordGot = taskRecordService.getTaskById(task_id);
			errorMessage = new ErrorMessage();
			TestGroupSelection testGroupSelection = null;

			logger.info("rerun auto task start " + task_id);
			original_task = getRerunTaskInfo(task_id);
			original_task.setTask_type("auto");
			TaskReqResourceModel taskReqResourceModel = new TaskReqResourceModel();
			taskReqResourceModel.setType(original_task.getType());
			if (original_task.getRerun_id() == 0) {

				resourceResTaskModel = resourceService.chooseRealOrSimResource(taskReqResourceModel);
				if (resourceResTaskModel.getData().getMajor_id() != -1) {
					String type = original_task.getType();
					String env_type = original_task.getEnv_type();
					String ci_type = original_task.getCi_type();
					testGroupSelection = caseManagerService.getTestGroup(type, env_type, ci_type);
					if (testGroupSelection == null) {
						String original_task_json = Convert.toJson(original_task);
						cacheService.pushCITaskCache("ci_task_cache", original_task_json);
						logger.info("not found ci config");
					}
					this.setTestGroupSelection(testGroupSelection);
					getCommitAuthor(taskReqResourceModel.getType()	);
					saveRecordOfTask();
					taskExecutor.execute(this);
					return taskRecordGot;
				} else {
					if (innerMessage) {
						return null;
					} else {
						sendToOtherWorkQueue(original_task.getType(), null, null, task_id);
					}
				}
			} else {
				return original_task;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage());
			failThenReleaseResource();
		}

		return null;
	}
}
