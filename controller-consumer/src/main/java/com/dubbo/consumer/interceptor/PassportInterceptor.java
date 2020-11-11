package com.dubbo.consumer.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.consumer.entity.HostHolder;
import com.dubbo.entity.LoginTicket;
import com.dubbo.entity.User;
import com.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

//拦截器执行过程： preHandle -> Controller -> postHandle -> View -> afterCompletion
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Reference
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {    //如果检测到含有 ticket，赋值给上面的 ticket 变量
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        //验证 ticket 是否有效
        try {
            if (ticket != null) {
                User user = (User) userService.verifyTicket(ticket).getData().get("user");
                hostHolder.setUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());    //向前端添加变量(可以直接在页面上 $user 了)
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();    //结束时清除刚才保存的用户
    }

}
