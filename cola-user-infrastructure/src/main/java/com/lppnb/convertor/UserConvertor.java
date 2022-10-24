package com.lppnb.convertor;

import com.lppnb.domain.model.User;
import com.lppnb.dto.data.UserDTO;
import com.lppnb.gatewayimpl.database.dataobject.UserDO;
import org.springframework.beans.BeanUtils;

public class UserConvertor {

    public static User toEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    public static UserDO toDataObject(User user){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        return userDO;
    }


}
