package com.xinwei.taskmanager;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskServer {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:resource/applicationContext.xml");
		ac.start();
	}
}