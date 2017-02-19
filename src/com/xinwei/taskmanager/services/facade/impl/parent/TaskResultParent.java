package com.xinwei.taskmanager.services.facade.impl.parent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.xinwei.taskmanager.model.dto.CICache;
import com.xinwei.taskmanager.model.dto.ErrorMessage;
import com.xinwei.taskmanager.model.dto.MailContext;
import com.xinwei.taskmanager.model.dto.TaskGroupSnapshot;
import com.xinwei.taskmanager.model.http.AuthUser;
import com.xinwei.taskmanager.model.http.ReqUser;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqReleaseResourceModel;
import com.xinwei.taskmanager.model.sub.ResourceSnapshot;
import com.xinwei.taskmanager.services.facade.impl.AbstractFacadeTaskServicerImpl;
import com.xinwei.taskmanager.services.util.JavaMail;
import com.xinwei.taskmanager.services.util.SetWebServer;
import com.xinwei.uem.model.AbstractInnerMessage;
import com.xinwei.uem.util.Convert;

public class TaskResultParent extends AbstractFacadeTaskServicerImpl implements Runnable {

	public void getCacheLog() {
		if (resultReqModel != null && resultReqModel.getCache_key() != null && resultReqModel.getTest_group() != null) {
			logger.info("getCacheLog start");
			logger.info(resultReqModel.toString());
			ciCache = cacheService.getCacheLog(resultReqModel.getCache_key(), resultReqModel.getTest_group());
		} else if (resultReqModel != null) {
			ciCache = new CICache();
			ciCache.setResult(resultReqModel.getResult());
			List<String> logs = new LinkedList<String>();
			logs.add("解压缩.rar文件失败");
			ciCache.setLogs(logs);
		}
	}

	public void delCacheLog() {
		if (resultReqModel != null && resultReqModel.getCache_key() != null) {
			cacheService.delCITaskCache(resultReqModel.getCache_key());
		}
	}

	public void delStepLog() {
		if (resultReqModel != null && resultReqModel.getCache_key() != null) {
			cacheService.delCITaskCache(resultReqModel.getCache_key() + ".step");
		}
	}

	public void closeTask() {
		if (taskRecordForResult != null) {
			taskRecordForResult.setStatus("close");
		}
	}

	public void manualTaskResult() {
		if (taskRecordForResult != null) {
			if (taskRecordForResult.getType().equals("simulation")) {
				return;
			}
			if (taskRecordForResult.getTask_type().equals("auto")) {
				return;
			}
			generateRealImage();
		}
	}

	public void taskFailupdateTaskRecord() {
		logger.info("task fail update task record start ");
		if (taskRecordForResult != null && !String.valueOf(taskRecordForResult.getId()).equals("")) {
			List<String> logs = new ArrayList<String>();
			taskRecordForResult.setResult("fail");
			taskRecordForResult.setStatus("close");
			taskRecordForResult.setFail_message(errorMessage.getErrorList().toString());
			logs.add(taskRecordForResult.getFail_message());
			taskRecordForResult.setLogs(logs);
			String resource_snapshot = Convert.toJson(taskRecordForResult.getResource_snapshot());
			taskRecordForResult.setResource_snapshot(resource_snapshot);
			taskRecordForResult.setId(resultReqModel.getId());
			// 更新task record
			taskRecordService.updateTaskRecord(taskRecordGot);
		}
	}

	/**
	 * Deal with the result of auto-task
	 */
	public void autoTaskResult() {
		if (taskRecordForResult.getTask_type().equals("auto")) {
			if (taskRecordForResult.getType().equals("simulation")) {
				logger.info("simulation run task result ");
			}
			if (taskRecordForResult.getType().equals("simulation")) {
				try {
					mailContext.setFrom("CloudTest_CI_auto <guowei@bj.xinwei.com.cn>");
					getCIResult();
					sendEmail();
				} catch (Throwable e) {
					logger.info("Error when auto generate the result" + e.getMessage());
				}
			} else if (taskRecordForResult.getType().equals("real")) {
				generateRealImage();
				try {
					mailContext.setFrom("CloudTest_auto <zhangfang@bj.xinwei.com.cn>");
					getCIResult();
					sendEmail();
				} catch (Throwable e) {
					e.printStackTrace();
					logger.info("Error when auto generate the result" + e.getMessage());
				}

			}
		}
	}

	/**
	 * get CI Result
	 */
	public void getCIResult() {
		testGroupSelection = caseManagerService.getTestGroup(taskRecordForResult.getType(),
				taskRecordForResult.getEnv_type(), taskRecordForResult.getCi_type());
		List<String> cc = new ArrayList<>();
		List<String> to = new ArrayList<>();
		String user = "";
		AuthUser authUser = new AuthUser();
		ReqUser reqUser = new ReqUser();
		ResourceSnapshot resourceSnapshot = Convert.parserJson(taskRecordForResult.getResource_snapshot(),
				ResourceSnapshot.class);

		String rkey = resourceSnapshot.getName() + "_" + taskRecordForResult.getId() + ".ci_report";
		webServer = SetWebServer.setWebServer(webServer);
		mailContext.setSubject("Cloud CI Build #" + taskRecordForResult.getId() + "-"
				+ (ciCache.getResult().equals("success") ? "success" : "fail"));
		// to.add(taskRecordForResult.getUser() + "@bj.xinwei.com.cn");
		if (taskRecordForResult.getAuthor() != null) {
			for (String author : taskRecordForResult.getAuthor()) {
				to.add(author + "@bj.xinwei.com.cn");
			}
		}
		mailContext.setTo(to);

		if (testGroupSelection.getcIConfig() != null && testGroupSelection.getcIConfig().getEmail_notify().size() > 0) {
			mailContext.setCc(testGroupSelection.getcIConfig().getEmail_notify());
		} else {
			cc.add("quleixing@bj.xinwei.com.cn");
			cc.add("liujinxing@bj.xinwei.com.cn");
			cc.add("zhangfang@bj.xinwei.com.cn");
			cc.add("guowei@bj.xinwei.com.cn");
		}
		if (taskRecordForResult.getAuthor() != null) {
			for (String name : taskRecordForResult.getAuthor()) {
				reqUser.setName(name);
				user = getUserFromAuth("http://172.31.4.5:8011/user/info", Convert.toJson(reqUser));
				authUser = (AuthUser) Convert.parserJson(user, AuthUser.class);
				logger.info(user);
				if (authUser != null) {
					for (String leaderFromAuthUser : authUser.getUser().getLeader()) {
						if (!cc.contains(leaderFromAuthUser + "@bj.xinwei.com.cn")) {
							cc.add(leaderFromAuthUser + "@bj.xinwei.com.cn");
						}
					}
				}
			}
		}

		cc.add("zhangfang@bj.xinwei.com.cn");
		cc.add("quleixing@bj.xinwei.com.cn");
		cc.add("guowei@bj.xinwei.com.cn");
		
		mailContext.setCc(cc);
		Object content = cacheService.getCIEmailCache(rkey);
		dealWithTextNHtml((String) content);
		cacheService.delCITaskCache(rkey);
	}

	private void dealWithTextNHtml(String content) {
		String urlLink = "http://" + webServer.getIp() + ":" + webServer.getPort()
				+ "/TaskInfo.html?ResType=auto&TaskId=" + taskRecordForResult.getId();
		String ci_result = "";
		if (content != null && content.length() > 0) {
			ci_result = content.replace("{result_url}", urlLink);
		} else {
			ci_result = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">" + "<head>"
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>CompileResult</title>" + "<STYLE type=\"text/css\">" +

					"body{font-family:Consolas,Microsoft Yahei}" + "table,td,tr,th{font-size:12px;}"
					+ "table{ margin:0 auto;}"
					+ ".common{align:center; text-align:left; margin-bottom:20px; border-top:1px solid #d8d8d8; border-left:1px solid #d8d8d8;}"
					+ ".common th{ line-height:20px; font-weight:normal; background-color:#AFD7FF; border-right:1px solid #d8d8d8; border-bottom:1px solid #d8d8d8;}"
					+ ".common td{ padding:5px 3px; line-height:16px; border-right:1px solid #d8d8d8; border-bottom:1px solid #d8d8d8;}"
					+ ".common td{ line-height:16px;}" +

					".bluebar{ height:4px; line-height:4px; overflow:hidden; background-color:#2f9ae0;}"
					+ ".list_dl{ overflow:hidden; _display:inline-block; border:1px solid #cbddeb; border-bottom:0;}"
					+ ".last_dl{ border-bottom:1px solid #cbddeb; white-space:nowrap;}"
					+ ".list_dl dt{ width:578px; height:38px; line-height:40px; overflow:hidden; padding-left:50px; float:left; font-size:14px;}"
					+ ".list_dl dd{ width:130px; height:31px; line-height:14px; padding-top:7px; float:left; color:#999999; overflow:hidden;}"
					+ ".list_dl dd.cli_dd{ width:70px;}" + ".list_dl dd span{ display:block;}" +

					".list_dl dt.tiline{ width:908px; height:24px; line-height:24px; background-color:#f0f9fe; color:#333333; font-weight:bold; font-size:12px;}"
					+ "dl.bluebg{ height:38px; line-height:38px; padding-top:0; background-color:#f0f9fe; color:#333333;}"
					+ "dl.bluebg dd{ height:38px; line-height:38px; padding-top:0; color:#333333;}" +

					"</STYLE>" + "</head>" + "<body>" + "<table width=\"90%\" class = \"common\">"
					+ "<tr><th>ERROR</th></tr>" + "<tr><td>error information see " + urlLink + "</td></tr>"
					+ "<tr><th>Powered by CloudTestPlatform</tr></th></table></body> </html>";
		}
		mailContext.setHtml(ci_result);
		mailContext.setText(ci_result);
	}

	private String getUserFromAuth(String url, String user) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/json;charset=UTF-8");
		headers.setContentType(mediaType);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		HttpEntity<String> param = new HttpEntity<>(user, headers);

		String result = restTemplate.postForObject(url, param, String.class);

		return result;
	}

	/**
	 * Send Email
	 */
	public void sendEmail() {
		JavaMail mail = new JavaMail();
		try {
			mail.sendMail(mailContext);
		} catch (MessagingException e) {
			errorMessage.getErrorList().add(String.valueOf(e));
			e.printStackTrace();
		}
	}

	/**
	 * generate the chart according to the task result from real environment
	 */
	public void generateRealImage() {
		// 从redis或者mongoDb里面取出TaskRecordStory
		// 发消息 生成图片
		if (taskRecordForResult.getType().equals("simulation")) {
			return;
		}
		AbstractInnerMessage paramReq = new AbstractInnerMessage();
		try {
			paramReq.setTarget("chartmanager");
			paramReq.setMessageId("chart");
			paramReq.setBody(String.valueOf(resultReqModel.getId()));
			innerCommunicate.notifyServices(paramReq);
			logger.info("send to Chartmanager");
		} catch (Throwable e) {
			logger.error("error when generateRealImage : " + e);
		}
	}

	/**
	 * In process of completeness of flow of task
	 */
	public void autoTaskCompletionProcess() {
		if (taskRecordForResult != null) {
			logger.info("Complete task result start ");

			taskRecordForResult.setStatus("close");
			if (ciCache.getResult().equals("success")) {
				taskRecordForResult.setResult("success");
			} else if (ciCache.getResult().equals("fail")) {
				taskRecordForResult.setResult("fail");
			}
			taskRecordForResult.setLogs(ciCache.getLogs());
			taskRecordForResult.setLog_file(resultReqModel.getLog_file());
			taskRecordForResult.setRun_time(resultReqModel.getRun_time());
			taskRecordService.updateTaskRecord(taskRecordForResult);
		}
	}

	public void releaseResource() {
		TaskReqReleaseResourceModel taskReqReleaseResourceModel = new TaskReqReleaseResourceModel();
		taskReqReleaseResourceModel.setMajor_id(String.valueOf(taskRecordForResult.getResource().getMajor_id()));
		taskReqReleaseResourceModel.setMinor_id(String.valueOf(taskRecordForResult.getResource().getMinor_id()));
		taskReqReleaseResourceModel.setType(taskRecordForResult.getType());
		logger.info("准备释放" + taskRecordForResult.getId() + "所占用的资源对象");
		String msg = resourceService.releaseResourceByMajorId(taskReqReleaseResourceModel);
		logger.info("The response to release resource from Resourse : " + msg);
	}

	@Override
	public void run() {
		try {
			errorMessage = new ErrorMessage();
			taskGroupSnapshot = new TaskGroupSnapshot();
			mailContext = new MailContext();
			getCacheLog();
			delCacheLog();
			delStepLog();
			closeTask();
			manualTaskResult();
			autoTaskResult();
			autoTaskCompletionProcess();
			releaseResource();
		} catch (Throwable e) {
			errorMessage.getErrorList().add(e.toString());
			taskFailupdateTaskRecord();
			releaseResource();
			logger.info("Error when get task result " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		TaskResultParent taskResultParent = new TaskResultParent();
		ReqUser reqUser = new ReqUser();
		reqUser.setName("zhangfang");
		String user = taskResultParent.getUserFromAuth("http://172.31.4.5:8011/user/info", Convert.toJson(reqUser));
		AuthUser authUser = Convert.parserJson(user, AuthUser.class);
		System.out.println(user);
		for (String leaderFromAuthUser : authUser.getUser().getLeader()) {
			System.out.println(leaderFromAuthUser);
		}
	}
}
