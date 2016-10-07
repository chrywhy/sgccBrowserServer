package com.chry.browserServer.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chry.browserServer.db.DbConnection;
import com.chry.browserServer.db.model.IUserDao;
import com.chry.browserServer.db.model.User;
import com.chry.util.Helper;
import com.chry.util.security.TokenManager;

@RestController
@RequestMapping(value="/users")
public class UserController {
	static Logger logger = LogManager.getLogger(UserController.class.getName());
	
    @RequestMapping(value="", method=RequestMethod.GET)
//    public List<User> getUsers() {
    public List<User> getUsers(@RequestHeader("token") String token) {
    	if (!TokenManager.check(token)) {
    		logger.error("Incorrect token: maybe invalid access -" + token);
    		return null;
    	}
		SqlSession session = DbConnection.getSession().openSession();
		try {
			IUserDao userDao = session.getMapper(IUserDao.class);
			List<User> users = new ArrayList<User>();
			users = userDao.getAllUsers();
			return users;
		} finally {
			session.close();
		}
    }

    @RequestMapping(value="/{uid}", method=RequestMethod.POST, headers = {"Content-type=application/json"})
    public User addUser(@RequestBody @Valid User user, @RequestHeader("token") String token) {
    	if (!TokenManager.check(token)) {
    		logger.error("Incorrect token: maybe invalid access -" + token);
    		return null;
    	}
		SqlSession session = DbConnection.getSession().openSession();
		try {
			if (logger.isInfoEnabled()) {
				String passwd = user.getPassword();
				user.setPassword("******");
				logger.info("Add user: " + Helper.toJson(user));
				user.setPassword(passwd);
			}
			IUserDao userDao = session.getMapper(IUserDao.class);
			userDao.insertUser(user);
			session.commit();
			return user;
		} finally {
			session.close();
		}
    }
    
    @RequestMapping(value="/{uid}", method=RequestMethod.GET)
    public User getUser(@PathVariable String uid, @RequestHeader("token") String token) {
    	if (!TokenManager.check(token)) {
    		logger.error("Incorrect token: maybe invalid access -" + token);
    		return null;
    	}		
    	SqlSession session = DbConnection.getSession().openSession();
		try {
			IUserDao userDao = session.getMapper(IUserDao.class);
			User user = userDao.getUserById(uid);
			return user;
		} finally {
			session.close();
		}
    }

    @RequestMapping(value="/{uid}", method=RequestMethod.PUT, headers = {"Content-type=application/json"})
    public User addUser(@PathVariable String uid, @RequestBody @Valid User user, @RequestHeader("token") String token) {
    	if (!TokenManager.check(token)) {
    		logger.error("Incorrect token: maybe invalid access -" + token);
    		return null;
    	}
    	SqlSession session = DbConnection.getSession().openSession();
		try {
			if (logger.isInfoEnabled()) {
				String passwd = user.getPassword();
				user.setPassword("******");
				logger.info("Update user: " + Helper.toJson(user));
				user.setPassword(passwd);
			}
			IUserDao userDao = session.getMapper(IUserDao.class);
			userDao.updateUser(user);
			session.commit();
			return user;
		} finally {
			session.close();
		}
    }

    @RequestMapping(value="/{uid}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable String uid, @RequestHeader("token") String token) {
    	if (!TokenManager.check(token)) {
    		logger.error("Incorrect token: maybe invalid access -" + token);
    		return null;
    	}
    	SqlSession session = DbConnection.getSession().openSession();
		try {
			IUserDao userDao = session.getMapper(IUserDao.class);
			userDao.deleteUser(uid);
			session.commit();
			return "{\"code\":\"0\"}";
		} finally {
			session.close();
		}
    }
}