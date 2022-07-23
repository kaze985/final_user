package com.geekgame.demo.dao;

import com.geekgame.demo.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    UserDO findById(String id);

    int add(UserDO userDO);

    int update(UserDO userDO);

    int delete(String id);

    UserDO findByUserName(String name);

}
