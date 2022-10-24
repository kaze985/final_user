package com.lppnb.executor.query;

import com.alibaba.cola.dto.SingleResponse;
import com.lppnb.domain.gateway.UserGateway;
import com.lppnb.domain.model.User;
import com.lppnb.dto.UserGetQry;
import com.lppnb.dto.data.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGetQryExe {
    @Autowired
    private UserGateway userGateway;

    public SingleResponse<UserDTO> execute(UserGetQry qry) {
        User user = userGateway.findByUserName(qry.getUserName());
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return SingleResponse.of(userDTO);
    }
}
