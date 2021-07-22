package com.team6.escapemaster_server.controller;


import com.team6.escapemaster_server.entity.User;
import com.team6.escapemaster_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbop")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("findUserById/{id}")
    public String GetUser(@PathVariable int id) {
        return userService.findUserById(id).toString();
    }

    @RequestMapping("InsertUser")
    public boolean InsUser(String mobile, String password) {
        return userService.insertIntoUser(mobile, password) != -1;
    }

    @RequestMapping("deleteUserById/{id}")
    public Integer DelUser(@PathVariable int id) {
        return userService.deleteUserById(id);
    }

    @RequestMapping("UpdateUser")
    public Integer UpdUser(int id, String mobile, String password) {
        return userService.updateUserById(id, mobile, password);
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
