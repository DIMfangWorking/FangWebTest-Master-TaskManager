package com.xinwei.taskmanager.services.facade.impl.concrete;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.dto.ErrorMessage;
import com.xinwei.taskmanager.model.dto.TaskReqFromWebModel;
import com.xinwei.taskmanager.model.dto.TestGroupSelection;
import com.xinwei.taskmanager.model.rpcmodel.CreateAutoTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.ResourceResTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqResourceModel;
import com.xinwei.taskmanager.services.facade.impl.parent.CreateTaskParent;
import com.xinwei.uem.model.AbstractInnerMessage;
import com.xinwei.uem.util.Convert;

public class CreateAutoTaskServiceImpl extends CreateTaskParent {

	@Override
	public void run() {
		try {
			percedureTask();
		} catch (Throwable e) {
			errorMessage.getErrorList().add(e.toString());
			taskFailupdateTaskRecord();
			logger.info("done function. data : ");
			for (String errorString : errorMessage.getErrorList()) {
				logger.info(errorString);
			}
			failThenReleaseResource();
			resourceResTaskModel = null;
			e.printStackTrace();
		}
	}

	@Override
	public TaskRecord createAutoTask(CreateAutoTaskModel createAutoTaskModel, TaskReqFromWebModel taskReqFromWebModel,
			boolean innerMessage) {
		try {

			if (resourceResTaskModel == null || resourceResTaskModel.getData().getMajor_id() == -1) {
				String type = createAutoTaskModel.getType();
				String env_type = createAutoTaskModel.getEnv_type();
				String ci_type = createAutoTaskModel.getCi_type();

				TaskReqResourceModel taskReqResourceModel = new TaskReqResourceModel();
				taskReqResourceModel.setType(type);
				resourceResTaskModel = resourceService.chooseRealOrSimResource(taskReqResourceModel);
				if (resourceResTaskModel.getData().getMajor_id() != -1) {
					this.setCreateAutoTaskModel(createAutoTaskModel);
					this.setTaskReqFromWebModel(taskReqFromWebModel);
					taskRecordGot = new TaskRecord();
					errorMessage = new ErrorMessage();
					TestGroupSelection testGroupSelection = null;

					taskRecordGot.setTask_type("auto");
					taskRecordGot.setBin_file(createAutoTaskModel.getBin_file());
					if (createAutoTaskModel.getCode_path() != null && createAutoTaskModel.getRevision() != null) {
						taskRecordGot.setCode_path(createAutoTaskModel.getCode_path());
						taskRecordGot.setRevision(createAutoTaskModel.getRevision());
					}
					taskRecordGot.setEnv_type(env_type);
					testGroupSelection = caseManagerService.getTestGroup(type, env_type, ci_type);
					if (testGroupSelection == null) {
						String original_task_json = Convert.toJson(taskRecordGot);
						cacheService.pushCITaskCache("ci_task_cache", original_task_json);
						logger.info("not found ci config");
					}
					this.setTestGroupSelection(testGroupSelection);
					this.getCommitAuthor(taskReqResourceModel.getType());
					saveRecordOfTask(createAutoTaskModel);
					taskExecutor.execute(this);
					return taskRecordGot;
				} else {
					if (innerMessage) {
						return null;
					} else {
						sendToOtherWorkQueue(createAutoTaskModel.getType(), createAutoTaskModel, taskReqFromWebModel,
								-1);
					}
				}

			} else {
				if (innerMessage) {
					return null;
				} else {
					sendToOtherWorkQueue(createAutoTaskModel.getType(), createAutoTaskModel, taskReqFromWebModel, -1);
				}
			}
		} catch (Throwable e) {
			logger.error(e.getMessage());
			failThenReleaseResource();
		}
		return null;
	}
}
