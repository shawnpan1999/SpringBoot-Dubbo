package com.dubbo.user.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.entity.LoginTicket;
import com.dubbo.entity.User;
import com.dubbo.service.UserService;
import com.dubbo.user.dao.LoginTicketDAO;
import com.dubbo.user.dao.UserDAO;
import com.dubbo.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service //注意这个Service是dubbo的Service
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @return Result 实体，data 部分包含新创建的用户携带的 ticket
     */
    public ResultEntity register(String username, String password) {
        ResultEntity result = new ResultEntity();
        if (StringUtils.isBlank(username)) {
            result.setMsg("用户名不能为空");
            return result;
        }
        if (StringUtils.isBlank(password)) {
            result.setMsg("密码不能为空");
            return result;
        }
        //判断用户名是否已被注册
        User user = userDAO.selectByUsername(username);
        if (user != null) {
            result.setMsg("用户名已被注册");
            return result;
        }
        user = new User(username, password);
        /*可选-用 md5 做密码加强
        user.setPassword(MD5Util.MD5(password+user.getSalt()));
        */
        userDAO.addUser(user);
        //注册成功给他发一个机票 自动登录
        result.getData().put("ticket", addLoginTicket(user.getId()));    //由于 useGeneratedKeys 的存在，可以直接获得自增 id
        result.setCode(0);
        result.setMsg("注册成功");
        return result;
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return Result 实体，data 部分包含登录的 ticket 串
     */
    @Override
    public ResultEntity login(String username, String password) {
        ResultEntity result = new ResultEntity();
        if (StringUtils.isBlank(username)) {
            result.setMsg("用户名不能为空");
            return result;
        }
        if (StringUtils.isBlank(password)) {
            result.setMsg("密码不能为空");
            return result;
        }
        User user = userDAO.selectByUsername(username);
        if (user == null) {    //判断用户存在
            result.setMsg("用户名不存在");
            return result;
        }
        if (!password.equals(user.getPassword())) {
            result.setMsg("用户名密码不匹配");
            return result;
        }
        result.getData().put("ticket", addLoginTicket(user.getId()));
        result.setCode(0);
        result.setMsg("登录成功");
        return result;
    }

    /**
     * 登出
     * @param ticket 用户ticket串
     * @return Result 实体，包含成功、失败信息
     */
    @Override
    public ResultEntity logout(String ticket) {
        ResultEntity result = new ResultEntity();
        if (StringUtils.isBlank(ticket)) {
            result.setMsg("ticket为空！");
            return result;
        }
        loginTicketDAO.updateStatus(ticket, 1);    //登出只要前端传回来一个票，这里把 ticket 设置为无效即可
        result.setCode(0);
        result.setMsg("退出成功");
        return result;
    }

    public ResultEntity verifyTicket(String ticket) {
        ResultEntity result = new ResultEntity();
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);    //通过 DAO 在数据库中取出 ticket 实体
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return result;    //无效机票
            }

            result.setCode(0);
            int userId = loginTicket.getUserId();
            User user = userDAO.selectById(userId);
            result.getData().put("user", user);
        }
        return result;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));    //把随机的 UUID 横杠去掉
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
}
