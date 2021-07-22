package com.team6.escapemaster_server.service;

import com.team6.escapemaster_server.entity.User;
import com.team6.escapemaster_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    public Integer insertIntoUser(String mobile, String password) {
        try {
            return userMapper.insertIntoUser(mobile, password);
        } catch (Exception e) {
            return -1;
        }

    }

    public Integer deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    public Integer updateUserById(int id, String mobile, String password) {
        return userMapper.updateUserById(id, mobile, password);
    }

    public java.util.List<User> findUserByPassword(String password) {
        return userMapper.findUserByPassword(password);
    }

}

