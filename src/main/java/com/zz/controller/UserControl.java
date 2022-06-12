package com.zz.controller;
import com.zz.pojo.User;
import com.zz.service.UserService;
import org.apache.ibatis.logging.Log;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

//控制层，导入Service层
@RestController
public class UserControl {
    @Autowired
    private UserService userService;

    @GetMapping("/index.html")
    public String userList(Map<String,List> result) {
        List<User> Users=userService.findAll();
        result.put("users",Users);
        System.out.println(">>>>>>>>>>>"+result);
        return  Users.toString();
    }

    //新增数据
    @PostMapping("/add")
    public String save(User user) {
        userService.save(user);
        //表示重置index.html界面并且跳转到index.html界面
        return "redirect:/index.html";
    }

    //删除数据,本来需要使用DeleteMapping，但是由于没有界面可以隐藏，所以这里就直接写了RequestMapping
    @PostMapping("/delete")
    public String delete(@RequestParam String account, HttpServletResponse servletResponse) throws IOException {
        userService.delete(account);
        System.out.println("delete方法执行");
        return "redirect:/index.html";
    }

    //根据account进行修改
    @PostMapping("/updateUser/{account}")
    public String updatePage(Model model,@PathVariable String account){
        User users = userService.get(account);
        model.addAttribute("users",users);
        //表示跳转到modifie,html界面
        return "modifie";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("account") String account,
                                 @RequestParam("changeType") String changeType,
                                 @RequestParam("changeValue") String changeValue) throws Exception {
        //String account = request.getParameter("account");
        User userByAccount = userService.get(account);
        int res =0;
        switch (changeType){
            case "updatePassword":
                res = userService.updatePassword(changeValue,account);
                break;
            case "updateUsername":
                res = userService.updateUsername(changeValue,account);
                break;
            case "updateUserStatus":
                res = userService.updateUserStatus(Integer.parseInt(changeValue),account);
                break;
            case "updateUserSex":
                res = userService.updateUserSex(Integer.parseInt(changeValue),account);
                break;
            default:
                throw new Exception("ERROR: unexpected request >>"+changeType);
        };
        if (0 == res){
            return "Error: "+changeType+"修改失败!";
        };
        return "OK: "+changeType+"修改成功!";
    }
}