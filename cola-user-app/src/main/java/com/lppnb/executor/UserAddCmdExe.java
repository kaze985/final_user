package com.lppnb.executor;

import com.alibaba.cola.dto.Response;
import com.lppnb.convertor.UserConvertor;
import com.lppnb.domain.gateway.UserGateway;
import com.lppnb.domain.model.User;
import com.lppnb.dto.UserAddCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAddCmdExe {
    @Autowired
    private UserGateway userGateway;

    public Response execute(UserAddCmd cmd) {
        User user = UserConvertor.toEntity(cmd.getUserDTO());
        userGateway.add(user);
        return Response.buildSuccess();
    }
}
