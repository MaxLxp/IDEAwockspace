package com.lxp.shiro.controller;

import com.lxp.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            subject.login(token);
        }catch (Exception e){
            return "登录失败";
        }
        return "登录成功";
    }

    @RequestMapping(value = "/testRole", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testRole() {
        return "testRole 访问成功";
    }

    @RequestMapping(value = "/testRole1", method = RequestMethod.GET,produces = "application/json;charset=utf-8" )
    @ResponseBody
    public String testRole1() {
        return "testRole1 访问成功";
    }

}
