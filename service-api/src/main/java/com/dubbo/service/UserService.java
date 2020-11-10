package com.dubbo.service;

import com.dubbo.util.ResultEntity;

public interface UserService {

    public ResultEntity login(String username, String password);

    public ResultEntity logout(String ticket);

    public ResultEntity verifyTicket(String ticket);

    public ResultEntity register(String username, String password);

}
