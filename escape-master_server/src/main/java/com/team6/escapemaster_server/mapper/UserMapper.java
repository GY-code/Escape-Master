package com.team6.escapemaster_server.mapper;

import com.team6.escapemaster_server.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserMapper {
    User findUserByNumber(String phone_number);
    Integer insertUser(String phone_number, String password);

    User findUserById(int id);
    Integer deleteUserById(int id);
    Integer updateUserById(int id,String phone_number,String password,
                           String nickname,int gender,
                           String signature);

    List<User> findUserByPassword(String password);
}
