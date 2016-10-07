package com.chry.browserServer.db.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface IUserDao {
    public User getUserById(@Param("uid")String uid);
    public List<User> getAllUsers();
    public void insertUser(User user);
    public void deleteUser(@Param("uid")String uid);
    public void updateUser(User user);
    public void updatePassword(@Param("uid")String uid, @Param("password")String newPassword);
}
