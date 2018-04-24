package com.x.ms.service;

import com.x.ms.domain.User;
import com.x.ms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public String login(User user){
        User dbUser = userMapper.get_one_user(user);
        if (dbUser == null){
            return "用户不存在";
        }
        else if (!dbUser.getPassword().equals(user.getPassword())){
            return "密码错误";
        }
        else {
            return "登陆成功";
        }
    }

    public String register(User user) {

        if (userMapper.get_one_user(user) == null){
            userMapper.add_one_user(user);
            return "注册成功";

        }else {
            return  "该用户名已被使用";
        }
    }
}
