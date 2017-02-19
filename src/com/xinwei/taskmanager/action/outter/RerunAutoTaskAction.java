package com.xinwei.taskmanager.action.outter;

import com.xinwei.taskmanager.action.AbstractAction;
import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.rpcmodel.ReRunRecvMsg;
import com.xinwei.taskmanager.model.rpcmodel.ReplyCreateTaskModel;
import com.xinwei.uem.model.AbstractInnerMessage;
import com.xinwei.uem.util.Convert;

public class RerunAutoTaskAction extends AbstractAction {

	@Override
	public AbstractInnerMessage action(AbstractInnerMessage message) throws Throwable {
		String result = message.getBody();
		ReRunRecvMsg reRunRecvMsg = (ReRunRecvMsg) Convert.parserJson(result, ReRunRecvMsg.class);

		TaskRecord taskRecord = facadeTaskService.reRunAutoTask(reRunRecvMsg.getOriginal_id(), false);

		AbstractInnerMessage replyMsg = new AbstractInnerMessage();
		replyMsg.setMessageId("ReRun");
		ReplyCreateTaskModel replyCreateTaskModel = new ReplyCreateTaskModel();
		if (taskRecord != null) {
			replyCreateTaskModel.setResult(0);
			replyCreateTaskModel.setMessage("success");
			replyCreateTaskModel.setTaskId(taskRecord.getId());
			String responseBody = Convert.toJson(replyCreateTaskModel);
			replyMsg.setBody(responseBody);
		} else {
			replyMsg.setBody("Task is in a Waiting queue, applying for a resource");
		}
		return replyMsg;
	}

}
