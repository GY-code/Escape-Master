package com.team6.escapemaster_server.mapper;

import com.team6.escapemaster_server.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User Sel(int id);
}
