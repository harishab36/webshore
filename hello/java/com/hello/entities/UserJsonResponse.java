package com.hello.entities;

import java.util.List;

import org.springframework.util.StringUtils;

public class UserJsonResponse {

	private User user;
	private List<User> userList;
	private String errors;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getErrors() {
		return errors;
	}

	public void addErrorMessgae(String msg) {

		if (StringUtils.isEmpty(errors)) {
			errors = new String(msg);
			return;
		} else {
			errors += " and "+msg;
		}

	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
