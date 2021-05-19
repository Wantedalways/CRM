package com.wantedalways.service.impl;

import com.wantedalways.dao.UserDao;
import com.wantedalways.entity.User;
import com.wantedalways.exception.LoginException;
import com.wantedalways.service.UserService;
import com.wantedalways.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException{

        User user = userDao.selectUserLogin(loginAct,loginPwd);

        if (user == null) {
            throw new LoginException("用户名或密码错误！");
        }

        String nowTime = DateUtil.getDate();
        String expireTime = user.getExpireTime();

        if (expireTime.compareTo(nowTime) < 0) {
            throw new LoginException("账号失效！");
        }

        if ("0".equals(user.getLockState())) {
            throw new LoginException("账号锁定！");
        }

        if (!user.getAllowIps().contains(ip)) {
            throw new LoginException("ip地址受限！");
        }
        return user;
    }
}
