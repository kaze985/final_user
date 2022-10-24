package com.lppnb.domain.gateway;

import com.lppnb.domain.model.User;

public interface UserGateway {
    int add(User user);
    User findByUserName(String name);
}
