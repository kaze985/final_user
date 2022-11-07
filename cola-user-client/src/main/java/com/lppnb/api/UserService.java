package com.lppnb.api;


import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.dto.data.UserDTO;

public interface UserService {

    /**
     * 注册用户
     */
    Response register(String userName, String pwd);

    /**
     * 执行登录逻辑
     */
    SingleResponse<UserDTO> login(String userName, String pwd);

}
