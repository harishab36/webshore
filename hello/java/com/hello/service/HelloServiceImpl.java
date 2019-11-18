package com.hello.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hello.entities.User;
import com.hello.repository.UserDAO;

@Service("helloService")
public class HelloServiceImpl implements HelloService {

	@Autowired
	UserDAO userDAO;

	@Override
	public Boolean addUser(User user) {
		System.out.println("Servicing " + user.getName());
		userDAO.addUser(user);
		return true;
	}

	@Override
	public List<User> getUserList(String userName) {
		return userDAO.getUserList(null);
	}

	@Override
	public Boolean deleteUser(String userId) {
		return userDAO.deleteUser(userId);
	}

	@Override
	public User editUser(User user) {
		return userDAO.editUser(user);
	}

	@Override
	public User getUser(int userId) {
		return userDAO.getUser(userId);
	}

}
