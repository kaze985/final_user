package com.geekgame.demo.api;


import com.geekgame.demo.model.Result;
import com.geekgame.demo.model.User;
import com.geekgame.demo.model.UserLoginInfo;
import com.geekgame.demo.service.UserService;
import com.google.code.kaptcha.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Controller
@Slf4j
public class UserAPI {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param userName
     * @param pwd
     * @return
     */
    @PostMapping("/api/user/reg")
    @ResponseBody
    public Result<User> reg(String userName, String pwd) {
        return userService.register(userName, pwd);
    }

    /**
     * 登录
     * @param userName
     * @param pwd
     * @param code
     * @param request
     * @return
     */
    @PostMapping("/api/user/login")
    @ResponseBody
    public Result<User> login(String userName, String pwd, String code, HttpServletRequest request) {
        Result<User> result = new Result<>();
        if (code == null) {
            result.setCode("604");
            result.setMessage("验证码不能为空");
            return result;
        } else {
            String kaptchaId = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (!code.equals(kaptchaId)) {
                result.setCode("605");
                result.setMessage("验证码不正确");
                return result;
            }
        }

        result = userService.login(userName, pwd);

        if (result.isSuccess()) {
            UserLoginInfo loginInfo = new UserLoginInfo();
            loginInfo.setId(result.getData().getId());
            loginInfo.setUserName(result.getData().getUserName());
            loginInfo.setLoginTime(LocalDateTime.now());
            request.getSession().setAttribute("loginInfo",loginInfo);
        }

        return result;
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/api/user/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        Result result = new Result();
        request.getSession().removeAttribute("loginInfo");
        result.setSuccess(true);
        return result;
    }

}
