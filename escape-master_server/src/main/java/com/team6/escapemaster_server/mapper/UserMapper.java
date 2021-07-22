package com.team6.escapemaster_server.mapper;

import com.team6.escapemaster_server.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserMapper {
    User findUserById(int id);
    Integer insertIntoUser(String mobile,String password);
    Integer deleteUserById(int id);
    Integer updateUserById(int id,String mobile,String password);
    List<User> findUserByPassword(String password);

}
