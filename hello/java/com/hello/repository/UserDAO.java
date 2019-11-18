package com.hello.repository;

import java.util.List;

import com.hello.entities.User;

public interface UserDAO {

	Boolean addUser(User user);

	Boolean deleteUser(String userId);

	User editUser(User user);

	User getUser(int userId);

	List<User> getUserList(String userName);

}
