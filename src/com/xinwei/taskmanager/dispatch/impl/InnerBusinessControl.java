package com.xinwei.taskmanager.dispatch.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.xinwei.taskmanager.model.dto.TaskReqFromWebModel;
import com.xinwei.taskmanager.model.rpcmodel.CreateAutoTaskModel;
import com.xinwei.taskmanager.services.facade.FacadeTaskService;
import com.xinwei.uem.util.Convert;
import com.xinwei.uem.util.RPCCallback;

public class InnerBusinessControl implements RPCCallback {

	private static Logger logger = null;
	private FacadeTaskService facadeAutoTaskService = null;
	private FacadeTaskService facadeReRunTaskService = null;

	@Override
	public Map<String, String> MessageProcess(String messageId, String body) throws Throwable {
		Map<String, String> result = new HashMap<String, String>();
		logger.info("MessageId " + messageId);
		logger.info(Thread.currentThread().getName());
		result.put("MessageID", messageId);
		result.put("Body", body);
		logger.info("任务在队列" + Thread.currentThread().getName() + "中等待");
		if (messageId.equals("Create Auto Task")) {
			int splitIndex = body.indexOf("|");
			String StringforCreateAutoTaskModel = body.substring(0, splitIndex);
			String StringforTaskReqFromWebModel = body.substring(splitIndex + 1, body.length());
			CreateAutoTaskModel createAutoTaskModel = (CreateAutoTaskModel) Convert
					.parserJson(StringforCreateAutoTaskModel, CreateAutoTaskModel.class);
			TaskReqFromWebModel taskReqFromWebModel = (TaskReqFromWebModel) Convert
					.parserJson(StringforTaskReqFromWebModel, TaskReqFromWebModel.class);
			while (facadeAutoTaskService.createAutoTask(createAutoTaskModel, taskReqFromWebModel, true) == null) {
				Thread.sleep(10000);
			}
		} else if (messageId.equals("Rerun Auto Task")) {
			while (facadeReRunTaskService.reRunAutoTask(Integer.parseInt(body), true) == null) {
				Thread.sleep(10000);
			}
		}

		return result;
	}

	public FacadeTaskService getFacadeAutoTaskService() {
		return facadeAutoTaskService;
	}

	public void setFacadeAutoTaskService(FacadeTaskService facadeAutoTaskService) {
		this.facadeAutoTaskService = facadeAutoTaskService;
	}

	public FacadeTaskService getFacadeReRunTaskService() {
		return facadeReRunTaskService;
	}

	public void setFacadeReRunTaskService(FacadeTaskService facadeReRunTaskService) {
		this.facadeReRunTaskService = facadeReRunTaskService;
	}

}
