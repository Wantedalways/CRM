package com.wantedalways.controller;

import com.wantedalways.entity.User;
import com.wantedalways.exception.LoginException;
import com.wantedalways.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wantedalways.util.MD5Util;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login.do")
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {

        // 获取MD5加密后的密码
        loginPwd = MD5Util.getMD5(loginPwd);
        // 获取请求的ip地址
        String ip = request.getRemoteAddr();

        userService.login(loginAct,loginPwd,ip);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("msg","登陆成功");

        return resultMap;
    }
}
