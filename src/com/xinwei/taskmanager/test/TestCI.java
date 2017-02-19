package com.xinwei.taskmanager.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.xinwei.taskmanager.services.facade.impl.parent.CreateTaskParent;
import com.xinwei.taskmanager.services.facade.impl.parent.TaskResultParent;

public class TestCI {
	private CreateTaskParent createTaskParent = null;

	@BeforeClass
	public void beforeTestCI() {
		System.out.println("Test start");
	}

	@Test
	public void TestCIXML() {
		
	}

	@AfterClass
	public void afterTestCI() {
		System.out.println("Test start");
	}

	public CreateTaskParent getCreateTaskParent() {
		return createTaskParent;
	}

	public void setCreateTaskParent(CreateTaskParent createTaskParent) {
		this.createTaskParent = createTaskParent;
	}

}
