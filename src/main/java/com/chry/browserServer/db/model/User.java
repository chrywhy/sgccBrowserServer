package com.chry.browserServer.db.model;

public class User {
	private String _uid;
	private String _name;
	private String _password;
	private Integer _role;

	public User() {
		_uid = "root";
		_name = "超级管理员";
		_password = "12345";
	}
	
	public User(String uid, String name, String password) {
		_uid = uid;
		_name = name;
		_password = password;
	}
	
	public String getUid() {
		return _uid;
	}
	
	public void setUid(String uid) {
		_uid = uid;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public Integer getRole() {
		return _role;
	}

	public void setRole(Integer role) {
		_role = role;
	}
}
