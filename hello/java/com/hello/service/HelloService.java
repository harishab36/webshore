package com.hello.service;

import java.util.List;

import com.hello.entities.User;

public interface HelloService {

	Boolean addUser(User user);

	List<User> getUserList(String userName);

	Boolean deleteUser(String userId);

	User getUser(int userId);

	User editUser(User user);

}
