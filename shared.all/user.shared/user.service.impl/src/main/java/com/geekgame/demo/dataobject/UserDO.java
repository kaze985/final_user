package com.geekgame.demo.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geekgame.demo.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDO implements Serializable {

    private String id;

    private String userName;

    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    public UserDO(){}

    /**
     * Model 转换为 DO
     * @param user 用户模型
     */
    public UserDO(User user){
        BeanUtils.copyProperties(user,this);
    }

    /**
     * DO 转换为 Model
     * @return User
     */
    public User toModel() {
        User user = new User();
        BeanUtils.copyProperties(this,user);
        return user;
    }
}