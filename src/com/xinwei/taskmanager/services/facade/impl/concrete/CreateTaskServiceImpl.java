package com.xinwei.taskmanager.services.facade.impl.concrete;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.dto.ErrorMessage;
import com.xinwei.taskmanager.model.dto.TaskReqFromWebModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqResourceModel;
import com.xinwei.taskmanager.services.facade.impl.parent.CreateTaskParent;

public class CreateTaskServiceImpl extends CreateTaskParent {

	@Override
	public void run() {
		try {
			percedureTask();
		} catch (Throwable e) {
			taskFailupdateTaskRecord();
			if (errorMessage != null) {
				logger.info("Failed to run the thread for creating task : ", errorMessage != null ? errorMessage.toString()
						: taskRecordGot.toJson(taskRecordGot) + ", Exception : " + e.getMessage());
				if (taskRecordGot != null && taskRecordGot.getTask_type().equals("auto")) {
					cacheService.pushCITaskCache("ci_task_cache", taskRecordGot.toJson(taskRecordGot));
				}
			}
			failThenReleaseResource();
		}
	}

	/**
	 * ��Ҫ���� ��ѡ��Ҫ����resource������ �ٿ�������һ���߳�ȥִ��Test������Task record�ͷ���һ����Ϣ��slaveͨ��RPC
	 */
	@Override
	public TaskRecord createTask(TaskReqFromWebModel taskReqModel) {
		try {
			TaskReqResourceModel taskReqResourceModel = new TaskReqResourceModel();
			errorMessage = new ErrorMessage();
			taskReqFromWebModel = taskReqModel;
			taskReqResourceModel.setType(taskReqModel.getType());
			resourceResTaskModel = resourceService.chooseRealOrSimResource(taskReqResourceModel);
			if (resourceResTaskModel != null && resourceResTaskModel.getData().getMajor_id() != -1) {

				saveRecordOfTask();
				// ���������߳�ȥִ��Test�͸���
				taskExecutor.execute(this);

				return taskRecordGot;
			}
		} catch (Throwable e) {
			taskFailupdateTaskRecord();
			if (resourceResTaskModel.getData().getMajor_id() != -1) {
				failThenReleaseResource();
			}
			logger.error("error when create Task : " + e);
		}
		return null;
	}
}
