package com.lppnb.dto.data;

import cn.hutool.core.util.IdUtil;
import com.alibaba.cola.dto.DTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO extends DTO {
    private String id;

    private String userName;

    @JsonIgnore
    private String password;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    public static UserDTO valueOf(String userName, String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        userDTO.setPassword(password);
        userDTO.setGmtCreated(LocalDateTime.now());
        userDTO.setGmtModified(LocalDateTime.now());
        userDTO.setId(IdUtil.getSnowflake(1, 1).nextIdStr());
        return userDTO;
    }
}
