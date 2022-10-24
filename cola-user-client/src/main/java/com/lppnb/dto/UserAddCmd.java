package com.lppnb.dto;

import com.lppnb.dto.data.UserDTO;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;


@Data
public class UserAddCmd {
    @NotNull
    private UserDTO userDTO;

}
