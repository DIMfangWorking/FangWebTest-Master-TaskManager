package com.xinwei.taskmanager.model.http;

import com.xinwei.taskmanager.model.http.sub.User;

public class AuthUser {
	private String result;
	private String message;
	private User user;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
