package com.team6.escapemaster_server.service;

import com.alibaba.fastjson.JSON;
import com.team6.escapemaster_server.algorithm.KnnPlusKernel;
import com.team6.escapemaster_server.algorithm.RssObj;
import com.team6.escapemaster_server.entity.TempPoint;
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
            System.out.println("ph:"+phone_number+"pw:"+password+"nn:"+nickname+"gd:"+gender+"sgn:"+signature);
            if(userMapper.insertUser(phone_number,password,nickname,gender,signature)!=0)
                return "register success";
        } catch (Exception e) {
            e.printStackTrace();
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
                return JSON.toJSONString(user);
                //return "Login success";
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

    public String getPosition(int room_id,double signal1, double signal2, double signal3, double signal4, double signal5, double signal6) {
        ArrayList<RssObj> list = new ArrayList<>();
        list = getPointsByRoom(room_id);
        RssObj tar = new RssObj();
        tar.signalAVGs.add(signal1);tar.signalAVGs.add(signal2);
        tar.signalAVGs.add(signal3);tar.signalAVGs.add(signal4);
        tar.signalAVGs.add(signal5);tar.signalAVGs.add(signal6);

        return KnnPlusKernel.getPosByKNK(list,tar,3);
    }

    private ArrayList<RssObj> getPointsByRoom(int room_id) {
        ArrayList<TempPoint> tList = userMapper.getTempPointsByRoom(room_id);
        ArrayList<RssObj> rList = new ArrayList<>();
        for(TempPoint tPoint:tList){
            RssObj rssObj = new RssObj();
            rssObj.pos.x=tPoint.getPos_x();
            rssObj.pos.y=tPoint.getPos_y();

            rssObj.signalAVGs.add(tPoint.getAvg_1());rssObj.signalAVGs.add(tPoint.getAvg_4());
            rssObj.signalAVGs.add(tPoint.getAvg_2());rssObj.signalAVGs.add(tPoint.getAvg_5());
            rssObj.signalAVGs.add(tPoint.getAvg_3());rssObj.signalAVGs.add(tPoint.getAvg_6());

            rssObj.signalVARs.add(tPoint.getSd_1());rssObj.signalVARs.add(tPoint.getSd_4());
            rssObj.signalVARs.add(tPoint.getSd_2());rssObj.signalVARs.add(tPoint.getSd_5());
            rssObj.signalVARs.add(tPoint.getSd_3());rssObj.signalVARs.add(tPoint.getSd_6());
            rList.add(rssObj);
        }
        return rList;
    }
}

