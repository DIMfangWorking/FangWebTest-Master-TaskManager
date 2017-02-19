package com.xinwei.taskmanager.action.outter;

import com.xinwei.taskmanager.action.AbstractAction;
import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.rpcmodel.TaskReportReqModel;
import com.xinwei.taskmanager.model.rpcmodel.ResultAndMessageModel;
import com.xinwei.uem.model.AbstractInnerMessage;
import com.xinwei.uem.util.Convert;

public class TaskReportAction extends AbstractAction {

	@Override
	public AbstractInnerMessage action(AbstractInnerMessage message) throws Throwable {
		String result = message.getBody();
		TaskReportReqModel reportReqModel = (TaskReportReqModel) Convert.parserJson(result, TaskReportReqModel.class);
		
		TaskRecord taskRecordNeedShow = facadeTaskService.taskReport(reportReqModel);
		
		if (taskRecordNeedShow != null) {
			AbstractInnerMessage replyMsg = new AbstractInnerMessage();
			replyMsg.setMessageId("reply report task msg");
			//logger.info("test task complete. result :" + taskRecordNeedShow.getResult());
			ResultAndMessageModel resultAndMessageModel = new ResultAndMessageModel();
			resultAndMessageModel.setResult(0);
			resultAndMessageModel.setMessage("success");
			String responseBody = Convert.toJson(resultAndMessageModel);
			replyMsg.setBody(responseBody);
			return replyMsg;
		} else {
			throw new Throwable("Error when update task report");
		}
	}

}
