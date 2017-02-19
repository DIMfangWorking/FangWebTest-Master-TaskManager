package com.xinwei.taskmanager.model.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xinwei.taskmanager.model.TestCase;

public class TaskGroupSnapshot implements Serializable {
	private static final long serialVersionUID = 3471676783322420356L;
	private String name;
	private String type;
	private List<TestCase> testcases;
	private Map<String, String> testcase;
	private String XML;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXML() {
		return XML;
	}

	public void setXML(String xML) {
		XML = xML;
	}

	public List<TestCase> getTestcases() {
		return testcases;
	}

	public void setTestcases(List<TestCase> testcases) {
		this.testcases = testcases;
	}

	public Map<String, String> getTestcase() {
		return testcase;
	}

	public void setTestcase(Map<String, String> testcase) {
		this.testcase = testcase;
	}

}
