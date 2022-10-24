package com.lppnb.gatewayimpl;

import com.lppnb.convertor.UserConvertor;
import com.lppnb.domain.gateway.UserGateway;
import com.lppnb.domain.model.User;
import com.lppnb.gatewayimpl.database.UserDAO;
import com.lppnb.gatewayimpl.database.dataobject.UserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGatewayImpl implements UserGateway {

    @Autowired
    private UserDAO userDAO;

    public int add(User user) {
        return userDAO.add(UserConvertor.toDataObject(user));
    }

    public User findByUserName(String name) {
        UserDO userDO = userDAO.findByUserName(name);
        if (userDO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userDO, user);
        return user;
    }
}
