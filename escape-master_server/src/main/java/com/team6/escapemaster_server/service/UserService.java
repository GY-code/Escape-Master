package com.team6.escapemaster_server.service;

import com.team6.escapemaster_server.algorithm.KnnPlusKernel;
import com.team6.escapemaster_server.algorithm.RssObj;
import com.team6.escapemaster_server.entity.User;
import com.team6.escapemaster_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    public String userRegisterS1(String phone_number, String password) {
        //号码未注册才进入
        if(userMapper.findUserByNumber(phone_number)==null){
            System.out.println("fake");
            return "register continue";
        }
        else
            return "register failed";
    }

    public String userRegisterS2(String phone_number, String password, String nickname, int gender, String signature) {
        try {
            System.out.println(phone_number+" "+password+" "+nickname+" "+gender+" "+signature);
            if(userMapper.insertUser(phone_number,password,nickname,gender,signature)!=0)
                return "register success";
        } catch (Exception e) {
            System.out.println("registerS2 failed");
        }
        return "register failed";
    }


    public String userLogin(String ph,String pw) {
        User user = userMapper.findUserByNumber(ph);
        if(user==null){
            return "Login failed";
        }else{
            if(pw.equals(user.getPassword())){
                return "Login success";
            }
            else
                return "Login failed";
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

    public String getPosition(double signal1, double signal2, double signal3, double signal4, double signal5, double signal6) {
        ArrayList<RssObj> list = new ArrayList<>();
        RssObj tar = new RssObj();

        return KnnPlusKernel.getPosByKNK(list,tar,3);
    }
}

