package com.team6.escapemaster_server.controller;


import com.team6.escapemaster_server.entity.User;
import com.team6.escapemaster_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getPosition")
    public String getPosition(int r, double s1, double s2, double s3, double s4, double s5, double s6){
        return userService.getPosition(r,s1,s2,s3,s4,s5,s6);
    }

    //Test
    @RequestMapping("hello")
    public String Hello() {
        return "hello server";
    }

    //注册 :S1
    @RequestMapping("RegisterS1")
    public String userRegisterS1(String phone_number, String password) {
        return userService.userRegisterS1(phone_number, password);
    }

    //注册 :S2
    @RequestMapping("RegisterS2")
    public String userRegisterS2(String phone_number, String password,String nickname,String gender,String signature) {
        int sex = Integer.parseInt(gender);
        return userService.userRegisterS2(phone_number, password,nickname,sex,signature);
    }

    //登录
    @RequestMapping("Login")
    public String userLogin(String ph,String pw){
        return userService.userLogin(ph,pw);
    }

    //修改个人信息
    @RequestMapping("UpdateUser")
    public Integer updateUser(int id, String phone_number, String password, String nickname, int gender, String signature) {
        return userService.updateUser(id, phone_number, password,nickname,gender,signature);
    }

    //查找用户
    @RequestMapping("findUserById/{id}")
    public String GetUser(@PathVariable int id) {
        User user = userService.findUserById(id);
        if(user==null){
            return "not found";
        }else{
            return user.toString();
        }
    }

    //注销账号
    @RequestMapping("deleteUserById/{id}")
    public Integer DelUser(@PathVariable int id) {
        return userService.deleteUserById(id);
    }

    @RequestMapping("multiFind/{password}")
    public String multiFind(@PathVariable String password) {
        StringBuilder res = new StringBuilder();
        java.util.List<User> resList = userService.findUserByPassword(password);
        for (User user:
             resList) {
            res.append(user.toString()).append("//");
        }
        return res.toString();
    }

}
