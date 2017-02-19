package com.xinwei.taskmanager.services.facade.impl;

import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.slf4j.Logger;
import org.springframework.core.task.TaskExecutor;

import com.xinwei.taskmanager.dao.InnerCommunicate;
import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.conf.WebServer;
import com.xinwei.taskmanager.model.dto.CICache;
import com.xinwei.taskmanager.model.dto.ErrorMessage;
import com.xinwei.taskmanager.model.dto.MailContext;
import com.xinwei.taskmanager.model.dto.TaskGroupSnapshot;
import com.xinwei.taskmanager.model.dto.TaskReportFromSlave;
import com.xinwei.taskmanager.model.dto.TaskReqFromWebModel;
import com.xinwei.taskmanager.model.dto.TestGroupSelection;
import com.xinwei.taskmanager.model.rpcmodel.CreateAutoTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.ResourceResTaskModel;
import com.xinwei.taskmanager.model.rpcmodel.SendToSlaveRes;
import com.xinwei.taskmanager.model.rpcmodel.TaskReportReqModel;
import com.xinwei.taskmanager.model.rpcmodel.TaskResultReqModel;
import com.xinwei.taskmanager.services.basic.CacheService;
import com.xinwei.taskmanager.services.basic.CaseManagerService;
import com.xinwei.taskmanager.services.basic.ResourceService;
import com.xinwei.taskmanager.services.basic.TaskRecordService;
import com.xinwei.taskmanager.services.facade.FacadeTaskService;
import com.xinwei.taskmanager.services.util.AtomOperationService;

public abstract class AbstractFacadeTaskServicerImpl implements FacadeTaskService {

	protected InnerCommunicate innerCommunicate = null;
	protected static Logger logger = null;
	protected TaskExecutor taskExecutor = null;
	protected Map<Integer, TaskReportFromSlave> taskReportMap = null;
	protected Map<Integer, TimerTask> timerTaskMap = null;
	/**
	 * Create Task to-use
	 */
	// AtomAction Map
	protected Map<String, AtomOperationService> atomOperationMap;
	// Global variable
	public static boolean loadedVersionFalg = false;
	// Services
	protected TaskRecordService taskRecordService = null;
	protected ResourceService resourceService = null;
	protected CaseManagerService caseManagerService = null;
	protected CacheService cacheService = null;
	// Models
	protected TestGroupSelection testGroupSelection = null;
	protected TaskReqFromWebModel taskReqFromWebModel = null;
	protected ResourceResTaskModel resourceResTaskModel = null;
	protected TaskGroupSnapshot taskGroupSnapshot = null;
	protected SendToSlaveRes sendToSlaveRes = null;

	// For getCommitUser method to use and create task to user as well
	protected TaskRecord taskRecordGot = null;
	protected TaskRecord original_task = null;
	protected ErrorMessage errorMessage = null;

	/**
	 * 
	 * Task Result to-use
	 */
	// Models
	protected TaskRecord taskRecordForResult = null;
	protected CICache ciCache = null;
	protected TaskResultReqModel resultReqModel = null;
	protected MailContext mailContext = null;
	protected WebServer webServer = null;

	/**
	 * report
	 */
	protected TaskReportReqModel reportReqModel = null;
	protected ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = null;
	
	public TaskReportReqModel getReportReqModel() {
		return reportReqModel;
	}

	public void setReportReqModel(TaskReportReqModel reportReqModel) {
		this.reportReqModel = reportReqModel;
	}

	@Override
	public String taskResult(TaskResultReqModel resultReqModel) throws Throwable {
		throw new Exception("not impl");
	}

	@Override
	public TaskRecord taskReport(TaskReportReqModel reportReqModel) throws Throwable {
		throw new Exception("not impl");
	}

	@Override
	public TaskRecord createTask(TaskReqFromWebModel taskReqModel) throws Throwable {
		throw new Exception("not impl");
	}

	@Override
	public TaskRecord createAutoTask(CreateAutoTaskModel createAutoTaskModel, TaskReqFromWebModel taskReqFromWebModel,
			boolean innerMessage) throws Throwable {
		throw new Exception("not impl");
	}

	@Override
	public TaskRecord reRunAutoTask(int task_id, boolean innerMessage) throws Throwable {
		throw new Exception("not impl");
	}

	public Map<String, AtomOperationService> getAtomOperationMap() {
		return atomOperationMap;
	}

	public void setAtomOperationMap(Map<String, AtomOperationService> atomOperationMap) {
		this.atomOperationMap = atomOperationMap;
	}

	public TaskRecord getOriginal_task() {
		return original_task;
	}

	public void setOriginal_task(TaskRecord original_task) {
		this.original_task = original_task;
	}

	public InnerCommunicate getInnerCommunicate() {
		return innerCommunicate;
	}

	public void setInnerCommunicate(InnerCommunicate innerCommunicate) {
		this.innerCommunicate = innerCommunicate;
	}

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public Map<String, AtomOperationService> getAtomActionMap() {
		return atomOperationMap;
	}

	public void setAtomActionMap(Map<String, AtomOperationService> atomOperationMap) {
		this.atomOperationMap = atomOperationMap;
	}

	public TaskRecordService getTaskRecordService() {
		return taskRecordService;
	}

	public void setTaskRecordService(TaskRecordService taskRecordService) {
		this.taskRecordService = taskRecordService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public CaseManagerService getCaseManagerService() {
		return caseManagerService;
	}

	public void setCaseManagerService(CaseManagerService caseManagerService) {
		this.caseManagerService = caseManagerService;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public TestGroupSelection getTestGroupSelection() {
		return testGroupSelection;
	}

	public void setTestGroupSelection(TestGroupSelection testGroupSelection) {
		this.testGroupSelection = testGroupSelection;
	}

	public TaskReqFromWebModel getTaskReqFromWebModel() {
		return taskReqFromWebModel;
	}

	public void setTaskReqFromWebModel(TaskReqFromWebModel taskReqFromWebModel) {
		this.taskReqFromWebModel = taskReqFromWebModel;
	}

	public ResourceResTaskModel getResourceResTaskModel() {
		return resourceResTaskModel;
	}

	public void setResourceResTaskModel(ResourceResTaskModel resourceResTaskModel) {
		this.resourceResTaskModel = resourceResTaskModel;
	}

	public TaskGroupSnapshot getTaskGroupSnapshot() {
		return taskGroupSnapshot;
	}

	public void setTaskGroupSnapshot(TaskGroupSnapshot taskGroupSnapshot) {
		this.taskGroupSnapshot = taskGroupSnapshot;
	}

	public SendToSlaveRes getSendToSlaveRes() {
		return sendToSlaveRes;
	}

	public void setSendToSlaveRes(SendToSlaveRes sendToSlaveRes) {
		this.sendToSlaveRes = sendToSlaveRes;
	}

	public TaskRecord getTaskRecordGot() {
		return taskRecordGot;
	}

	public void setTaskRecordGot(TaskRecord taskRecordGot) {
		this.taskRecordGot = taskRecordGot;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public TaskRecord getTaskRecordForResult() {
		return taskRecordForResult;
	}

	public void setTaskRecordForResult(TaskRecord taskRecordForResult) {
		this.taskRecordForResult = taskRecordForResult;
	}

	public TaskResultReqModel getResultReqModel() {
		return resultReqModel;
	}

	public void setResultReqModel(TaskResultReqModel resultReqModel) {
		this.resultReqModel = resultReqModel;
	}

	public MailContext getMailContext() {
		return mailContext;
	}

	public void setMailContext(MailContext mailContext) {
		this.mailContext = mailContext;
	}

	protected CreateAutoTaskModel createAutoTaskModel = null;

	public CreateAutoTaskModel getCreateAutoTaskModel() {
		return createAutoTaskModel;
	}

	public void setCreateAutoTaskModel(CreateAutoTaskModel createAutoTaskModel) {
		this.createAutoTaskModel = createAutoTaskModel;
	}

	public WebServer getWebServer() {
		return webServer;
	}

	public void setWebServer(WebServer webServer) {
		this.webServer = webServer;
	}

	public Map<Integer, TaskReportFromSlave> getTaskReportMap() {
		return taskReportMap;
	}

	public void setTaskReportMap(Map<Integer, TaskReportFromSlave> taskReportMap) {
		this.taskReportMap = taskReportMap;
	}

	public Map<Integer, TimerTask> getTimerTaskMap() {
		return timerTaskMap;
	}

	public void setTimerTaskMap(Map<Integer, TimerTask> timerTaskMap) {
		this.timerTaskMap = timerTaskMap;
	}

	public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
		return scheduledThreadPoolExecutor;
	}

	public void setScheduledThreadPoolExecutor(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
		this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
	}
}
