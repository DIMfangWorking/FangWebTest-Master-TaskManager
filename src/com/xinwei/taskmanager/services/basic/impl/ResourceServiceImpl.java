package com.xinwei.taskmanager.services.basic.impl;

import java.util.Map;

import org.slf4j.Logger;

import com.xinwei.taskmanager.dao.InnerCommunicate;
import com.xinwei.taskmanager.model.rpcmodel.ResourceReleaseResultModel;
import com.xinwei.taskmanager.model.rpcmodel.ResourceResTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqReleaseResourceModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqResourceModel;
import com.xinwei.taskmanager.services.basic.ResourceService;
import com.xinwei.uem.model.AbstractInnerMessage;
import com.xinwei.uem.util.Convert;

public class ResourceServiceImpl implements ResourceService {
	private static Logger logger = null;
	protected InnerCommunicate innerCommunicate = null;

	@Override
	public String releaseResourceByMajorId(TaskReqReleaseResourceModel model) {
		AbstractInnerMessage paramReq = new AbstractInnerMessage();
		AbstractInnerMessage paramRes = null;
		try {
			paramReq.setTarget("resource");
			paramReq.setMessageId("release resource");
			/*
			 * paramReq.setBody( "{\"major_id\":\"" + model.getMajor_id() +
			 * "\", \"minor_id\":\"" + model.getMinor_id() + "\"}");
			 */
			logger.info("释放对象是 : " + model.getJson(model));
			paramReq.setBody(model.getJson(model));
			paramRes = innerCommunicate.syncCallServices(paramReq);
		} catch (Throwable e) {
			logger.error("error update resource status");
		}
		String resResult = paramRes.getBody();
		ResourceReleaseResultModel releaseResultModel = (ResourceReleaseResultModel) Convert.parserJson(resResult,
				ResourceReleaseResultModel.class);
		return releaseResultModel.getResult();
	}

	@Override
	public ResourceResTaskModel chooseRealOrSimResource(TaskReqResourceModel model) {
		AbstractInnerMessage paramReq = new AbstractInnerMessage();
		AbstractInnerMessage paramRes = null;
		paramReq.setTarget("resource");
		paramReq.setMessageId("alloc resource");
		try {
			if (model.getType().equals("simulation") || model.getType().equals("real")) {
				paramReq.setBody("{\"type\":\"" + model.getType() + "\"}");
				paramRes = innerCommunicate.syncCallServices(paramReq);
			} else {
				logger.error("not resource task type!");
			}
		} catch (Throwable e) {
			logger.error("not resource task type! ", e);
		}
		String resResult = paramRes.getBody();
		ResourceResTaskModel resourceResTaskModel = (ResourceResTaskModel) Convert.parserJson(resResult,
				ResourceResTaskModel.class);
		return resourceResTaskModel;
	}

	public InnerCommunicate getInnerCommunicate() {
		return innerCommunicate;
	}

	public void setInnerCommunicate(InnerCommunicate innerCommunicate) {
		this.innerCommunicate = innerCommunicate;
	}
}
