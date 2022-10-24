package com.lppnb.web;


import com.alibaba.cola.dto.Response;
import com.google.code.kaptcha.Constants;
import com.lppnb.api.UserService;
import com.lppnb.dto.data.UserLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @PostMapping("/api/user/reg")
    @ResponseBody
    public Response reg(String userName, String pwd) {
        if (StringUtils.isEmpty(userName)) {
            return Response.buildFailure("600", "用户名不能为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return Response.buildFailure("601", "密码不能为空");
        }
        return userService.register(userName, pwd);
    }

    /**
     * 登录
     */
    @PostMapping("/api/user/login")
    @ResponseBody
    public Response login(String userName, String pwd, String code, HttpServletRequest request) {
        if (StringUtils.isEmpty(userName)) {
            return Response.buildFailure("600", "用户名不能为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return Response.buildFailure("601", "密码不能为空");
        }
        if (code == null) {
            return Response.buildFailure("605", "验证码不能为空");
        } else {
            String kaptchaId = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (!code.equals(kaptchaId)) {
                return Response.buildFailure("606", "验证码不正确");
            }
        }

        Response result = userService.login(userName, pwd);

        if (result.isSuccess()) {
            UserLoginInfo loginInfo = new UserLoginInfo();
            loginInfo.setUserName(userName);
            loginInfo.setLoginTime(LocalDateTime.now());
            request.getSession().setAttribute("loginInfo",loginInfo);
        }

        return result;
    }

    /**
     * 登出
     */
    @GetMapping("/api/user/logout")
    @ResponseBody
    public Response logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginInfo");
        return Response.buildSuccess();
    }

}
