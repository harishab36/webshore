package com.hello.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hello.entities.User;

@Repository("userDao")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Boolean addUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if(user.getUserId() >= 0) {
			System.out.println("Adding.");
			session.persist(user);
		}else {
			System.out.println("Updating.");
			session.update(user);
		}
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public List<User> getUserList(String userName) {
		Session session = sessionFactory.openSession();
		List<User> userList = session.createCriteria(User.class).list();
		return userList;
	}

	@Override
	public Boolean deleteUser(String userId) {
		Session session = sessionFactory.openSession();
		User user = session.get(User.class, Integer.parseInt(userId));

		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
		user = session.get(User.class, Integer.parseInt(userId));
		return user == null;
	}

	@Override
	public User editUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		User updatedUser = session.get(User.class, user.getUserId());
		return updatedUser;
	}

	@Override
	public User getUser(int userId) {
		Session session = sessionFactory.openSession();
		User user = session.get(User.class, userId);
		return user;
	}

}
