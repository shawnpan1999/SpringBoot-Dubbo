package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.service.UserService;
import com.dubbo.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        HttpServletResponse response) {
        try {
            ResultEntity result = userService.login(username, password);
            if (result.getCode() == 0) {
                Cookie cookie = new Cookie("ticket", result.getData().get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "登录异常").toJSONString();
        }
    }

    @RequestMapping(value = "/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           HttpServletResponse response) {
        try {
            ResultEntity result = userService.register(username, password);
            if (result.getCode() == 0) {
                //注册成功则配置 cookie
                Cookie cookie = new Cookie("ticket", result.getData().get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "注册异常").toJSONString();
        }
    }
}
