package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Reference
    private UserService userService;

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {    //直接从注解中获得 Cookie 值
        userService.logout(ticket);
        return "redirect:/";
    }
}
