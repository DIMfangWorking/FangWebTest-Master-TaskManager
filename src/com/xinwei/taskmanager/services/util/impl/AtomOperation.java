package com.xinwei.taskmanager.services.util.impl;

import com.xinwei.taskmanager.services.basic.CaseManagerService;

public abstract class AtomOperation {
	protected CaseManagerService caseManagerService;

	public void setCaseManagerService(CaseManagerService caseManagerService) {
		this.caseManagerService = caseManagerService;
	}
}
