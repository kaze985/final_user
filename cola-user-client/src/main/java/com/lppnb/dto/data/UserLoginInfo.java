package com.lppnb.dto.data;

import com.alibaba.cola.dto.DTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户登录信息
 */
@Data
public class UserLoginInfo extends DTO {
    private String id;
    private String userName;

    private LocalDateTime loginTime;
}
