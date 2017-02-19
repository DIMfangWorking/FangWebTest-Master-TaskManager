package com.xinwei.taskmanager.deploymodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;
import com.xinwei.taskmanager.model.sub.Ei_basic_image;
import com.xinwei.uem.util.Convert;
import com.xinwei.taskmanager.model.sub.Resource;

public class TaskRecord implements Serializable {

	private static final long serialVersionUID = 1541896136569605493L;

	public enum Type {
		simulation, real
	}

	public enum Task_type {
		auto, manual
	}

	public enum Status {
		run, close
	}

	public enum Result {
		success, fail
	}

	@Field("id")
	@CheckField(unique = true, required = true)
	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	private int id;

	@Field("rerun_id")
	private int rerun_id;

	@Field("type")
	@CheckField(required = true)
	private Type type;

	@Field("resource")
	public Resource resource;

	@Field("resource_snapshot")
	private String resource_snapshot;

	@Field("task_type")
	private Task_type task_type;

	@Field("env_type")
	private String env_type;

	@Field("user")
	@CheckField(required = true)
	private String user;

	@Field("date")
	private Date date;

	@Field("status")
	@CheckField(required = true)
	private Status status;

	@Field("step")
	private String step;

	@Field("run_time")
	private int run_time;

	@Field("revision")
	private String revision;

	@Field("code_path")
	private String code_path;

	@Field("bin_file")
	private String bin_file;

	@Field("test_group")
	@CheckField(required = true)
	private String test_group;

	@Field("taskgroup_snapshot")
	private String taskgroup_snapshot;

	@Field("result")
	private Result result;

	@Field("logs")
	private List<String> logs;

	@Field("ei_basic")
	private List<Object> ei_basic;

	@Field("ei_basic_image")
	private List<Ei_basic_image> ei_basic_image;

	@Field("log_file")
	private String log_file;

	@Field("fail_message")
	private String fail_message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRerun_id() {
		return rerun_id;
	}

	public void setRerun_id(int rerun_id) {
		this.rerun_id = rerun_id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getResource_snapshot() {
		return resource_snapshot;
	}

	public void setResource_snapshot(String resource_snapshot) {
		this.resource_snapshot = resource_snapshot;
	}

	public Task_type getTask_type() {
		return task_type;
	}

	public void setTask_type(Task_type task_type) {
		this.task_type = task_type;
	}

	public String getEnv_type() {
		return env_type;
	}

	public void setEnv_type(String env_type) {
		this.env_type = env_type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public int getRun_time() {
		return run_time;
	}

	public void setRun_time(int run_time) {
		this.run_time = run_time;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getCode_path() {
		return code_path;
	}

	public void setCode_path(String code_path) {
		this.code_path = code_path;
	}

	public String getBin_file() {
		return bin_file;
	}

	public void setBin_file(String bin_file) {
		this.bin_file = bin_file;
	}

	public String getTest_group() {
		return test_group;
	}

	public void setTest_group(String test_group) {
		this.test_group = test_group;
	}

	public String getTaskgroup_snapshot() {
		return taskgroup_snapshot;
	}

	public void setTaskgroup_snapshot(String taskgroup_snapshot) {
		this.taskgroup_snapshot = taskgroup_snapshot;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public List<String> getLogs() {
		return logs;
	}

	public void setLogs(List<String> logs) {
		this.logs = logs;
	}

	public List<Object> getEi_basic() {
		return ei_basic;
	}

	public void setEi_basic(List<Object> ei_basic) {
		this.ei_basic = ei_basic;
	}

	public List<Ei_basic_image> getEi_basic_image() {
		return ei_basic_image;
	}

	public void setEi_basic_image(List<Ei_basic_image> ei_basic_image) {
		this.ei_basic_image = ei_basic_image;
	}

	public String getLog_file() {
		return log_file;
	}

	public void setLog_file(String log_file) {
		this.log_file = log_file;
	}

	public String getFail_message() {
		return fail_message;
	}

	public void setFail_message(String fail_message) {
		this.fail_message = fail_message;
	}

	public String toJson(TaskRecord taskRecord) {
		String taskRecordJson = Convert.toJson(taskRecord);
		return taskRecordJson;
	}

}
