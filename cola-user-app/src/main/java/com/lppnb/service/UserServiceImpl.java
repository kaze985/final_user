package com.lppnb.service;


import cn.hutool.crypto.SecureUtil;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.api.UserService;
import com.lppnb.dto.UserAddCmd;
import com.lppnb.dto.UserGetQry;
import com.lppnb.dto.data.UserDTO;
import com.lppnb.executor.UserAddCmdExe;
import com.lppnb.executor.query.UserGetQryExe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAddCmdExe userAddCmdExe;

    @Autowired
    private UserGetQryExe userGetQryExe;

    @Override
    public Response register(String userName, String pwd) {

        UserGetQry userGetQry = new UserGetQry();
        userGetQry.setUserName(userName);
        SingleResponse<UserDTO> result = userGetQryExe.execute(userGetQry);

        if (result != null) {
            return Response.buildFailure("602", "用户名已经存在");
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "lppnb";
        // 生成md5值，并转大写字母
        String md5Pwd = SecureUtil.md5(saltPwd).toUpperCase();

        UserAddCmd userAddCmd = new UserAddCmd();
        userAddCmd.setUserDTO(UserDTO.valueOf(userName,md5Pwd));
        userAddCmdExe.execute(userAddCmd);

        return Response.buildSuccess();
    }

    @Override
    public SingleResponse<UserDTO> login(String userName, String pwd) {

        UserGetQry userGetQry = new UserGetQry();
        userGetQry.setUserName(userName);
        SingleResponse<UserDTO> result = userGetQryExe.execute(userGetQry);

        if (result == null) {
            return SingleResponse.buildFailure("603", "用户名不存在");
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "lppnb";
        // 生成md5值，并转大写字母
        String md5Pwd = SecureUtil.md5(saltPwd).toUpperCase();

        if (!StringUtils.equals(md5Pwd, result.getData().getPassword())) {
            return SingleResponse.buildFailure("604", "密码不对");
        }

        return result;
    }

}
