package com.xinwei.taskmanager.model.dto;

import java.util.LinkedList;
import java.util.List;

public class ErrorMessage {
	private List<String> errorList;

	public List<String> getErrorList() {
		if (errorList == null) {
			errorList = new LinkedList<String>();
		}
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

}
