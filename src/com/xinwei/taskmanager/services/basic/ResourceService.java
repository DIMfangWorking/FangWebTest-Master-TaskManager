package com.xinwei.taskmanager.services.basic;

import com.xinwei.taskmanager.model.rpcmodel.ResourceResTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqReleaseResourceModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqResourceModel;

public interface ResourceService {
	
	public String releaseResourceByMajorId(TaskReqReleaseResourceModel taskReqReleaseResourceModel);

	public ResourceResTaskModel chooseRealOrSimResource(TaskReqResourceModel taskReqResourceModel);
}
