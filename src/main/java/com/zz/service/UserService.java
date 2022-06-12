package com.zz.service;

import com.zz.pojo.User;

import java.util.List;

public interface UserService {
    //查询全部
    List<User> findAll();
    //新增数据
    int save(User user);
    //删除数据
    int delete(String account);
    //根据id查找
    User get(String id);
    /**
     * //更新密码数据
     *     @Update("update user set password=#{password} where account=#{account}")
     *     public int updatePassword(String password,int account);
     *     //更新用户名
     *     @Update("update user set username=#{username} where account=#{account}")
     *     public int updateUsername(String username,int account);
     *     //更新账号可用性
     *     @Update("update user set user_status=#{user_status} where account=#{account}")
     *     public int updateUserStatus(int  user_status,int account);
     *     //更新用户性别
     *     @Update("update user set sex=#{sex} where account=#{account}")
     *     public int updateUserSex(int sex,int account);
     * */
    //更新数据
    int updatePassword(String password,String account);
    int updateUsername(String username,String account);
    int updateUserStatus(int  user_status,String account);
    int updateUserSex(int sex,String account);
}