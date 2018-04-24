package com.x.ms.mapper;

import com.x.ms.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User get_one_user(User user);

    void add_one_user(User user);
}
