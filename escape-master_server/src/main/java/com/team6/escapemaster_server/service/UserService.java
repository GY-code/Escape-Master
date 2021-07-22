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

    public String userRegister(String phone_number, String password) {
        //号码未注册才进入
        if(userMapper.findUserByNumber(phone_number)==null){
            try {
                if(userMapper.insertUser(phone_number,password)!=0)
                    return "success";
            } catch (Exception e) {
            }
        }
        return "register failed";
    }

    public String userLogin(String phone_number,String password) {
        User user = userMapper.findUserByNumber(phone_number);
        if(user==null){
            return "账号未注册";
        }else{
            if(password.equals(user.getPassword())){
                return "登录成功";
            }
            else
                return "密码错误";
        }
    }

    public Integer deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    public Integer updateUser(int id, String phone_number, String password, String nickname, int gender, String signature) {
        return userMapper.updateUserById(id, phone_number, password,nickname,gender,signature);
    }

    public java.util.List<User> findUserByPassword(String password) {
        return userMapper.findUserByPassword(password);
    }

}

