package com.lppnb.domain.model;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.cola.domain.Entity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户模型
 */
@Data
@Entity
public class User{

    private String id;

    private String userName;

    private String password;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public static User valueOf(String userName, String password) {
        User user = SpringUtil.getBean(User.class);
        user.setUserName(userName);
        user.setPassword(password);
        user.setGmtCreated(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        user.setId(IdUtil.getSnowflake(1, 1).nextIdStr());
        return user;
    }

}
