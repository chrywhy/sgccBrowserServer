package com.chry.browserServer.web;

import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chry.browserServer.db.DbConnection;
import com.chry.browserServer.db.model.IUserDao;
import com.chry.browserServer.db.model.User;

@RestController
@RequestMapping(value="/login")
public class LoginController {
    @RequestMapping(value="/{id}/{password}", method=RequestMethod.GET)
    public String login(@PathVariable String id, @PathVariable String password) {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			IUserDao userDao = session.getMapper(IUserDao.class);
			User user = userDao.getUserById(id);
			if(id.equals(user.getUid()) && password.equals(user.getPassword())) {
				return "OK";
			} else {
				return null;
			}
		} finally {
			session.close();
		}
    }
}