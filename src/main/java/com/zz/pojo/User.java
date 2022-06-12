package com.zz.pojo;

/**
 * `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
 * `account` char(11) DEFAULT NULL COMMENT '账号，默认为手机号',
 * `passwd` varchar(50) DEFAULT NULL COMMENT '密码',
 * `name` varchar(50) DEFAULT NULL COMMENT '用户名称',
 * `sex` tinyint  NOT NULL DEFAULT 1 COMMENT '性别',
 * `start_time` datetime DEFAULT NOW() COMMENT '注册时间',
 * `limits` int(11)  NOT NULL DEFAULT 1 COMMENT '使用年限',
 */
public class User {
    String account;
    String password;
    String username;
    Integer sex;
    String start_time;
    Integer limits;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Integer getLimits() {
        return limits;
    }

    public void setLimits(Integer limits) {
        this.limits = limits;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", sex=" + sex +
                ", start_time='" + start_time + '\'' +
                ", limits=" + limits +
                '}';
    }
}
