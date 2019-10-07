package com.allen.imsystem.dao;

import com.allen.imsystem.dao.mappers.UserMapper;
import com.allen.imsystem.model.dto.FriendGroup;
import com.allen.imsystem.model.pojo.User;
import com.allen.imsystem.model.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    public User findUserWithUid(String uid){
        return userMapper.selectUserWithUid(uid);
    }

    public User findUserWithEmail(String email){
        return userMapper.selectUserWithEmail(email);
    }

    public UserInfo findUserInfo(String uid){
        return userMapper.selectUserInfo(uid);
    }

    public Integer countUid(String uid){
        return userMapper.selectCountUid(uid);
    }
    public Integer countEmail(String email) {
        return userMapper.selectCountEmail(email);
    }

    public Integer insertUser(User user){
        return userMapper.insertUser(user);
    }

    public Integer insertUserInfo(UserInfo userInfo){
        return userMapper.insertUserInfo(userInfo);
    }

    public Integer updateUser(User user){
        return userMapper.updateUser(user);
    }

    public Integer updateUserInfo(UserInfo userInfo){
        return userMapper.updateUserInfo(userInfo);
    }


}
