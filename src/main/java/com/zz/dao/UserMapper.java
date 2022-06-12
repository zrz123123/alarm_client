package com.zz.dao;

import com.zz.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    //查询全部
    @Select("select * from user")
    List<User> findAll();

    //新增数据
    @Insert("insert into user (account,password,username,sex,limits) values (#{account},#{password},#{username},#{sex},#{limits})")
    public int save(User user);

    //删除数据
    @Delete("delete from user where account=#{account}")
    public int delete(String account);

    //根据id查找
    @Select("select * from user where account=#{account}")
    public User get(String account);

    //更新密码数据
    @Update("update user set password=#{password} where account=#{account}")
    public int updatePassword(String password,String account);

    //更新用户名
    @Update("update user set username=#{username} where account=#{account}")
    public int updateUsername(String username,String account);

    //更新账号可用性
    @Update("update user set user_status=#{user_status} where account=#{account}")
    public int updateUserStatus(int  user_status,String account);

    //更新用户性别
    @Update("update user set sex=#{sex} where account=#{account}")
    public int updateUserSex(int sex,String account);
}