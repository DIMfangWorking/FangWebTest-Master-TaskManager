package com.xinwei.taskmanager.model.http.sub;

import java.util.List;

public class User {
	private String name;
	private String group;
	private List<String> leader;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<String> getLeader() {
		return leader;
	}

	public void setLeader(List<String> leader) {
		this.leader = leader;
	}

}
