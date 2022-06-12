package com.zz.service;

import com.zz.pojo.User;
import com.zz.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public int save(User user) {
        return userMapper.save(user);
    }

    @Override
    public int delete(String account) {
        return userMapper.delete(account);
    }

    @Override
    public User get(String account) {
        return userMapper.get(account);
    }

    @Override
    public int updatePassword(String password, String account) {
        return userMapper.updatePassword(password,account);
    }

    @Override
    public int updateUsername(String username, String account) {
        return userMapper.updateUsername(username,account);
    }

    @Override
    public int updateUserStatus(int user_status, String account) {
        return userMapper.updateUserStatus(user_status,account);
    }

    @Override
    public int updateUserSex(int sex, String account) {
        return userMapper.updateUserSex(sex,account);
    }
}