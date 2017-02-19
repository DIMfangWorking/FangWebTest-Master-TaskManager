package com.xinwei.taskmanager.services.facade.impl.parent;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.TestCase;
import com.xinwei.taskmanager.model.TestGroup;
import com.xinwei.taskmanager.model.dto.TaskGroupSnapshot;
import com.xinwei.taskmanager.model.dto.TaskReqFromWebModel;
import com.xinwei.taskmanager.model.rpcmodel.CreateAutoTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.SendToSlaveReq;
import com.xinwei.taskmanager.model.rpcmodel.SendToSlaveRes;
import com.xinwei.taskmanager.model.rpcmodel.TaskReqReleaseResourceModel;
import com.xinwei.taskmanager.model.rpcmodel.sub.AccessInformation;
import com.xinwei.taskmanager.model.sub.ReportInfo;
import com.xinwei.taskmanager.model.sub.Resource;
import com.xinwei.taskmanager.model.sub.ResourceSnapshot;
import com.xinwei.taskmanager.model.sub.SequenceOfOpera;
import com.xinwei.taskmanager.model.sub.SequenceOfOpera.Argv;
import com.xinwei.taskmanager.model.sub.SubResource;
import com.xinwei.taskmanager.services.facade.impl.AbstractFacadeTaskServicerImpl;
import com.xinwei.taskmanager.services.util.AtomOperationService;
import com.xinwei.taskmanager.services.util.DBSetOptionParse;
import com.xinwei.uem.model.AbstractInnerMessage;
import com.xinwei.uem.util.Convert;

public class CreateTaskParent extends AbstractFacadeTaskServicerImpl implements Runnable {

	public void updateOriginalTask() {
		logger.info("update original task");
		if (original_task != null) {
			logger.info("real update original task old ", original_task.getId(), taskRecordGot.getId());
			original_task.setRerun_id(taskRecordGot.getId());
			taskRecordService.saveTaskRecord(original_task);
		}
	}

	public void getCommitAuthor(String task_type) throws Throwable {
		try {
			if (!"simulation".equals(task_type)) {
				return;
			}
			String svn_user = testGroupSelection.getcIConfig().getSvn_user();
			String svn_password = testGroupSelection.getcIConfig().getSvn_password();
			int revision = 0;
			String code_path = "";
			if (taskRecordGot.getRevision() != null) {
				revision = Integer.parseInt(taskRecordGot.getRevision());
			}
			if (taskRecordGot.getCode_path() != null) {
				code_path = taskRecordGot.getCode_path();
			}

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(code_path));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svn_user,
					svn_password);
			repository.setAuthenticationManager(authManager);
			repository.log(null, revision, revision, false, false, new ISVNLogEntryHandler() {
				@Override
				public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
					logger.info(logEntry.getAuthor());
					taskRecordGot.setUser(logEntry.getAuthor());
				}
			});
		} catch (Throwable e) {
			logger.error(" ", e);
			throw new Throwable(e.getMessage());
		}
	}

	public synchronized void saveRecordOfTask(Object... objects) {
		if (objects.length == 0) {
			try {
				ResourceSnapshot resourceSnapshot = new ResourceSnapshot();
				int uuid = taskRecordService.findTheLargestId();
				taskRecordGot.setId(uuid + 1);
				taskRecordGot.setResult("fail");
				taskRecordGot.setStatus("run");
				taskRecordGot.setRun_time(0);
				taskRecordGot.setLogs(null);
				setResourceSnapshot(resourceSnapshot);
				taskRecordGot.setResource_snapshot(Convert.toJson(resourceSnapshot));
				taskRecordGot.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				taskRecordService.saveTaskRecord(taskRecordGot);
				original_task.setRerun_id(taskRecordGot.getId());
				taskRecordService.updateTaskRecord(original_task);
			} catch (Throwable e) {
				logger.error("Error when Save task : " + e);
			}
		} else {
			int uuid = taskRecordService.findTheLargestId();
			taskRecordGot.setId(uuid + 1);
			taskRecordGot.setResult("fail");
			taskRecordGot.setStatus("run");
			taskRecordGot.setRun_time(0);
			taskRecordGot.setTask_type("auto");
			CreateAutoTaskModel createAutoTaskModel = (CreateAutoTaskModel) objects[0];
			taskRecordGot.setBin_file(createAutoTaskModel.getBin_file());
			if (createAutoTaskModel.getCode_path() != null && createAutoTaskModel.getRevision() != null) {
				taskRecordGot.setCode_path(createAutoTaskModel.getCode_path());
				taskRecordGot.setRevision(createAutoTaskModel.getRevision());
			}
			taskRecordGot.setDate(createAutoTaskModel.getDate());
			taskRecordGot.setEnv_type(createAutoTaskModel.getEnv_type());
			taskRecordGot.setCi_type(createAutoTaskModel.getCi_type());
			taskRecordGot.setType(createAutoTaskModel.getType());
			taskRecordGot.setUser(testGroupSelection.getcIConfig().getSvn_user());
			taskRecordGot.setTest_group(testGroupSelection.getTest_group().getName());
			Resource resource = new Resource();
			ResourceSnapshot resourceSnapshot = new ResourceSnapshot();
			resource.setMajor_id(resourceResTaskModel.getData().getMajor_id());
			if (resourceResTaskModel.getData().getType().equals("simulation")) {
				resource.setMinor_id(resourceResTaskModel.getData().getMinor_id());
			}

			taskRecordGot.setResource(resource);
			setResourceSnapshot(resourceSnapshot);
			taskRecordGot.setResource_snapshot(Convert.toJson(resourceSnapshot));

			taskRecordGot.setMeasured_object(taskReqFromWebModel.getMeasured_object());
			taskRecordGot.setAuthor(createAutoTaskModel.getAuthor());

			taskRecordService.saveTaskRecord(taskRecordGot);

		}
	}

	private void setResourceSnapshot(ResourceSnapshot resourceSnapshot) {
		resourceSnapshot.setName(resourceResTaskModel.getData().getName());
		resourceSnapshot.setMajor_id(resourceResTaskModel.getData().getMajor_id());
		resourceSnapshot.setIp(resourceResTaskModel.getData().getIp());
		resourceSnapshot.setStatus(resourceResTaskModel.getData().getStatus());
		resourceSnapshot.setType(resourceResTaskModel.getData().getType());
		resourceSnapshot.setCpu(resourceResTaskModel.getData().getCpu());
		resourceSnapshot.setDesc(resourceResTaskModel.getData().getDesc());
		resourceSnapshot.setHostname(resourceResTaskModel.getData().getHostname());
		resourceSnapshot.setMem(resourceResTaskModel.getData().getMem());
		resourceSnapshot.setPlatfrom(resourceResTaskModel.getData().getPlatfrom());
		resourceSnapshot.setPort(resourceResTaskModel.getData().getPort());
		resourceSnapshot.setRegister_date(resourceResTaskModel.getData().getRegister_date());
		resourceSnapshot.setReport_info(resourceResTaskModel.getData().getReport_info());
		resourceSnapshot.setSub_resource(resourceResTaskModel.getData().getSub_resource());
		resourceSnapshot.setUptime(resourceResTaskModel.getData().getUptime());
		resourceSnapshot.setMinor_id(resourceResTaskModel.getData().getMinor_id());
	}

	public void createTestFrameworkPara() {
		logger.info("run create Test Framework Para start ");
		DocumentFactory factory = new DocumentFactory();
		Document document = factory.createDocument();
		// 根节点 InitConfig
		Element root = document.addElement("InitConfig");
		// 节点TaskDriver
		Element taskDriver = root.addElement("TaskDriver");
		taskDriver.addText("0");
		// 节点CurEnvironment
		Element curEnvironment = root.addElement("CurEnvironment");
		curEnvironment.addText("0");
		// 节点Compile
		Element compile = root.addElement("Compile");
		compile.addAttribute("url",
				taskReqFromWebModel != null ? taskReqFromWebModel.getCode_path() : taskRecordGot.getCode_path());
		compile.addAttribute("revision",
				taskReqFromWebModel != null ? taskReqFromWebModel.getRevision() : taskRecordGot.getRevision());
		compile.addAttribute("svn_user", testGroupSelection != null ? testGroupSelection.getcIConfig().getSvn_user()
				: taskReqFromWebModel.getMeasured_object().getFtp().getUser());
		compile.addAttribute("svn_password",
				testGroupSelection != null ? testGroupSelection.getcIConfig().getSvn_password()
						: taskReqFromWebModel.getMeasured_object().getFtp().getUser());

		// 节点testTask
		Element testTask = root.addElement("TestTask");
		ReportInfo reportInfo = null;
		SubResource subResource = null;
		if (createAutoTaskModel.getType().equals("simulation")) {
			for (ReportInfo info : resourceResTaskModel.getData().getReport_info()) {
				if (info.getMinor_id() == resourceResTaskModel.getData().getMinor_id()) {
					reportInfo = info;
					break;
				}
			}
			// taskRecordGot.setResource_snapshot(Convert.toJson(reportInfo));
		} else if (createAutoTaskModel.getType().equals("real")) {
			// taskRecordGot.setResource_snapshot(Convert.toJson(resourceResTaskModel.getData()));
			reportInfo = resourceResTaskModel.getData().getReport_info().get(0);
			subResource = resourceResTaskModel.getData().getSub_resource().get(0);
		}
		String testGroupName = testGroupSelection != null ? testGroupSelection.getTest_group().getName()
				: taskReqFromWebModel.getTest_group();
		// 节点testTask的次节点testCaseGroup
		Element testCaseGroup = testTask.addElement("TestCaseGroup");
		testCaseGroup.addAttribute("name", testGroupName);
		// 节点testTask的次节点EnbId
		Element enbId = testCaseGroup.addElement("EnbId");
		enbId.setText(String.valueOf(subResource.getEnbID()));

		taskGroupSnapshot = new TaskGroupSnapshot();
		taskGroupSnapshot.setName(testGroupName);
		if (testGroupSelection == null) {
			TestGroup testGroup = caseManagerService.findByName(taskReqFromWebModel.getTest_group());
			List<TestCase> testCases = caseManagerService.getTestCaseFromTestGroup(taskReqFromWebModel.getTest_group());
			taskGroupSnapshot.setType(testGroup.getType());
			taskGroupSnapshot.setTestcases(testCases);
		} else {
			taskGroupSnapshot.setType(testGroupSelection.getTest_group().getType());
			taskGroupSnapshot.setTestcases(testGroupSelection.getTest_group().getTestcase());
		}
		for (TestCase testCase : taskGroupSnapshot.getTestcases()) {
			Element testcase = testCaseGroup.addElement("TestCase");
			testcase.addAttribute("name", testCase.getName());
			testcase.addAttribute("times", "1");
		}
		String XMLString = "";
		try {
			// 设置为缩减型格式
			OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
			// 设置文件编码
			xmlFormat.setEncoding("UTF-8");
			// 设置换行
			xmlFormat.setNewlines(true);
			// 生成缩进
			xmlFormat.setIndent(true);
			// 使用两个空格进行缩进
			xmlFormat.setIndent("  ");
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File("createTestFrameworkPara.xml")),
					xmlFormat);
			xmlWriter.write(document);
			XMLString = document.asXML();
			xmlWriter.close();
		} catch (Exception e) {
			logger.error("Get error when write XML : " + e);
			errorMessage.getErrorList().add("Get error when write XML : " + e);
		}
		taskGroupSnapshot.setXML(XMLString);
	}

	public void createTestCasePara() {
		logger.info("run create Test Case Para start ");

		List<Integer> ids = new ArrayList<>();

		int count = 0;
		AtomOperationService atomActionService = null;

		Map<String, String> testCaseMap = new HashMap<String, String>();

		for (TestCase testCase : taskGroupSnapshot.getTestcases()) {
			ids.add(testCase.getId());
		}
		taskRecordGot.setTaskgroup_snapshot(Convert.toJson(taskGroupSnapshot));
		List<TestCase> testCases = caseManagerService.findTestCaseByIds(ids);
		if (testCases == null || testCases.size() == 0) {
			logger.error("No testcases found");
			errorMessage.getErrorList().add("No testcases found");
			return;
		} else {
			taskGroupSnapshot.setTestcases(null);
			for (TestCase testCase : testCases) {
				DocumentFactory factory = new DocumentFactory();
				Document document = factory.createDocument();
				// 根节点 InitConfig
				Element root = document.addElement("TestCase");
				root.addAttribute("name", testCase.getName());
				root.addAttribute("tcg_name", taskGroupSnapshot.getName());
				// 节点SupportEnvironment
				Element supportEnvironment = root.addElement("SupportEnvironment");
				supportEnvironment.addText("0");
				for (SequenceOfOpera atomAction : testCase.getSequenceOfOpera()) {
					Element atomActionXML = root.addElement("AtomAction");
					atomActionXML.addAttribute("name", atomAction.getName());
					if (atomAction.getName().equals("LoadedVersion")
							&& ((taskReqFromWebModel == null) || (taskReqFromWebModel.getMeasured_object() == null
									|| taskReqFromWebModel.getMeasured_object().getFtp() == null))) {
						logger.info("No version was found");
						return;
					}
					if (atomAction.getName().equals("LoadedVersion")) {
						atomActionService = atomOperationMap.get("LoadedVersion");
						atomActionService.atomAction(atomActionXML);
					}
					if (atomAction.getName().equals("EIDetail")) {
						atomActionService = atomOperationMap.get("EIDetail");
						atomActionService.atomAction(atomActionXML);
					}
					for (Argv argv : atomAction.getArgv()) {
						// logger.info("test case atom action Property : " +
						// argv.toString());
						switch (argv.getName()) {
						case "EnbID":
							atomActionService = atomOperationMap.get("EnbID");
							if (resourceResTaskModel.getData().getType().equals("real")) {
								for (SubResource subResource : resourceResTaskModel.getData().getSub_resource())
									if (subResource.getEnbID() > 0) {
										atomActionService.atomAction(atomActionXML, subResource);
									}
							} else {
								for (ReportInfo reportInfo : resourceResTaskModel.getData().getReport_info()) {
									if (reportInfo.getMinor_id() == resourceResTaskModel.getData().getMinor_id()) {
										atomActionService.atomAction(atomActionXML, reportInfo);
									}
								}
							}
							break;
						case "PDNIP":
							atomActionService = atomOperationMap.get("PDNIP");
							if (resourceResTaskModel.getData().getType().equals("real")) {
								for (SubResource subResource : resourceResTaskModel.getData().getSub_resource())
									if (subResource.getPdnip() != null) {
										atomActionService.atomAction(atomActionXML, subResource);
									}
							} else {
								for (ReportInfo reportInfo : resourceResTaskModel.getData().getReport_info()) {
									if (reportInfo.getMinor_id() == resourceResTaskModel.getData().getMinor_id()) {
										atomActionService.atomAction(atomActionXML, reportInfo);
									}
								}
							}
							break;
						case "VersionName":
							if (atomAction.getName().equals("LoadedVersion")
									|| atomAction.getName().equals("CheckVersion")) {
								String original = taskReqFromWebModel.getMeasured_object().getFtp().getOriginal()
										.substring(0, taskReqFromWebModel.getMeasured_object().getFtp().getOriginal()
												.lastIndexOf(".") + 1);
								atomActionService = atomOperationMap.get("VersionName");
								atomActionService.atomAction(atomActionXML,
										(original + "BIN").replace("_macro_V", "."));
							}
							break;
						case "Option":
							if (atomAction.getName().equals("SetDataBase")
									|| atomAction.getName().equals("ReConfigEnb")) {
								try {
									DBSetOptionParse option = new DBSetOptionParse(argv.getValue());
									option.check();
									String value = option.getFinalJson();
									atomActionService = atomOperationMap.get("Option");
									atomActionService.atomAction(atomActionXML, value);
								} catch (Exception e) {
									logger.error("db option error. " + e.getMessage());
									errorMessage.getErrorList().add("db option error. " + e.getMessage());
									;
								}
							}
							break;
						default:
							atomActionService = atomOperationMap.get("Default");
							atomActionService.atomAction(atomActionXML, argv);
							break;
						}
					}
				}
				String XMLString = "";
				try {
					XMLWriter xmlWriter = null;
					// 设置为缩减型格式
					OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
					// 设置文件编码
					xmlFormat.setEncoding("UTF-8");
					// 设置换行
					xmlFormat.setNewlines(true);
					// 生成缩进
					xmlFormat.setIndent(true);
					// 使用两个空格进行缩进
					xmlFormat.setIndent("  ");

					xmlWriter = new XMLWriter(new FileOutputStream(new File("createTestCasePara" + count + ".xml")),
							xmlFormat);
					xmlWriter.write(document);
					XMLString = document.asXML();
					testCaseMap.put(testCase.getName(), XMLString);
					count++;
					xmlWriter.close();
				} catch (Exception e) {
					logger.error("Get error when write XML : " + e);
				}
			}
		}
		taskGroupSnapshot.setTestcase(testCaseMap);
		taskRecordGot.setTaskgroup_snapshot(Convert.toJson(taskGroupSnapshot));
	}

	public void sendTaskToSlave() {
		logger.info("send Task To Slave start");
		AbstractInnerMessage paramReq = new AbstractInnerMessage();
		AbstractInnerMessage paramRes = null;
		AccessInformation accessInformation = new AccessInformation();
		accessInformation.setIp(resourceResTaskModel.getData().getIp());
		accessInformation.setPort(resourceResTaskModel.getData().getPort());
		accessInformation.setMethod("POST");
		accessInformation.setPath("/slave/task");
		List<AccessInformation> accessInformations = new ArrayList<>();
		accessInformations.add(accessInformation);
		SendToSlaveReq<TaskRecord> sendToSlaveReq = new SendToSlaveReq<TaskRecord>();
		sendToSlaveReq.setAccessInformation(accessInformations);
		/*
		 * String taskRecordGotString = Convert.toJson(taskRecordGot);
		 * TaskRecordForSlave taskRecordForSlave =
		 * Convert.parserJson(taskRecordGotString, TaskRecordForSlave.class); if
		 * (taskReqFromWebModel != null) {
		 * taskRecordForSlave.setMeasured_object(taskReqFromWebModel.
		 * getMeasured_object()); }
		 */
		sendToSlaveReq.setData(taskRecordGot);
		String req = Convert.toJson(sendToSlaveReq);
		try {
			paramReq.setTarget("gateway");
			paramReq.setMessageId("sendTo slave");
			paramReq.setBody(req);
			paramRes = innerCommunicate.syncCallServices(paramReq);
		} catch (Throwable e) {
			logger.error("error when send to slave : " + e.getMessage());
			errorMessage.getErrorList().add("error when send to slave");
		}
		String resResult = paramRes.getBody();
		logger.info(resResult);
		sendToSlaveRes = (SendToSlaveRes) Convert.parserJson(resResult, SendToSlaveRes.class);
	}

	public void updateTaskRecord() {
		logger.info("update task record start ");
		taskRecordGot = taskRecordService.getTaskById(sendToSlaveRes.getTask_id());
		// String resource_snapshot =
		// Convert.toJson(resourceResTaskModel.getData().getReport_info());
		// taskRecordGot.setResource_snapshot(resource_snapshot);
		String taskgroup_snapshot = Convert.toJson(taskGroupSnapshot);
		taskRecordGot.setTaskgroup_snapshot(taskgroup_snapshot);
		taskRecordService.updateTaskRecord(taskRecordGot);
	}

	public void taskFailupdateTaskRecord() {
		logger.info("task fail update task record start ");
		if (taskRecordGot != null && !String.valueOf(taskRecordGot.getId()).equals("")) {
			List<String> logs = new ArrayList<String>();
			taskRecordGot.setResult("fail");
			taskRecordGot.setStatus("close");
			taskRecordGot.setFail_message(errorMessage.getErrorList().toString());
			logs.add(taskRecordGot.getFail_message());
			taskRecordGot.setLogs(logs);

			// String resource_snapshot =
			// Convert.toJson(resourceResTaskModel.getData());
			// taskRecordGot.setResource_snapshot(resource_snapshot);
			// 更新task record
			taskRecordService.updateTaskRecord(taskRecordGot);

		}
	}

	public void failThenReleaseResource() {
		TaskReqReleaseResourceModel taskReqReleaseResourceModel = new TaskReqReleaseResourceModel();
		taskReqReleaseResourceModel.setMajor_id(String.valueOf(resourceResTaskModel.getData().getMajor_id()));
		taskReqReleaseResourceModel.setMinor_id(String.valueOf(resourceResTaskModel.getData().getMinor_id()));
		taskReqReleaseResourceModel.setType(taskRecordGot.getType());
		String msg = resourceService.releaseResourceByMajorId(taskReqReleaseResourceModel);
		logger.info("The response to release resource from Resourse : " + msg);
	}

	public void sendToOtherWorkQueue(String type, CreateAutoTaskModel createAutoTask,
			TaskReqFromWebModel taskReqFromWeb, int task_id) {
		AbstractInnerMessage paramReq = new AbstractInnerMessage();
		try {
			if (type.equals("real")) {
				paramReq.setTarget("InnerQueue01ForReal");
			} else if (type.equals("simulation")) {
				paramReq.setTarget("InnerQueue02ForSim");
			}

			if (createAutoTask != null) {
				paramReq.setMessageId("Create Auto Task");
				paramReq.setBody(Convert.toJson(createAutoTask) + "|" + Convert.toJson(taskReqFromWeb));
			} else if (task_id > -1) {
				paramReq.setMessageId("Rerun Auto Task");
				paramReq.setBody(String.valueOf(task_id));
			}
			innerCommunicate.notifyServices(paramReq);
		} catch (Throwable e) {
			logger.error("Error when send to Other queues");
		}
	}

	@Override
	public void run() {

	}

	public void percedureTask() throws Throwable {
		createTestFrameworkPara();
		createTestCasePara();
		sendTaskToSlave();
		if (sendToSlaveRes.getMessage().equals("Success")) {
			updateTaskRecord();
			resourceResTaskModel = null;
		} else {
			throw new Throwable("Failed when slave executed the task");
		}
	}

}
