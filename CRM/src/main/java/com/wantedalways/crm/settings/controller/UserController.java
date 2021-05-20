package com.wantedalways.crm.settings.controller;

import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.exception.LoginException;
import com.wantedalways.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wantedalways.crm.util.MD5Util;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        User user = userService.login(loginAct,loginPwd,ip);

        // Session令牌，防止恶意登录
        HttpSession session = request.getSession();
        session.setAttribute("user",user);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",true);
        resultMap.put("msg","登陆成功");

        return resultMap;
    }
}
