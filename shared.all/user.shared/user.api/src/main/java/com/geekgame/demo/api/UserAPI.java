package com.geekgame.demo.api;


import com.geekgame.demo.model.Result;
import com.geekgame.demo.model.User;
import com.geekgame.demo.model.UserLoginInfo;
import com.geekgame.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Controller
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user/reg")
    @ResponseBody
    public Result<User> reg(String userName, String pwd) {
        return userService.register(userName, pwd);
    }

    @PostMapping("/api/user/login")
    @ResponseBody
    public Result<User> login(String userName, String pwd, HttpServletRequest request) {
        Result<User> result = userService.login(userName, pwd);

        if (result.isSuccess()) {
            UserLoginInfo loginInfo = new UserLoginInfo();
            loginInfo.setId(result.getData().getId());
            loginInfo.setUserName(result.getData().getUserName());
            loginInfo.setLoginTime(LocalDateTime.now());
            request.getSession().setAttribute("loginInfo",loginInfo);
        }

        return result;
    }

    @GetMapping("/api/user/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        Result result = new Result();
        request.getSession().removeAttribute("loginInfo");
        result.setSuccess(true);
        return result;
    }


}
