package com.lppnb.api;


import com.alibaba.cola.dto.Response;

public interface UserService {

    /**
     * 注册用户
     */
    Response register(String userName, String pwd);

    /**
     * 执行登录逻辑
     */
    Response login(String userName, String pwd);

}
