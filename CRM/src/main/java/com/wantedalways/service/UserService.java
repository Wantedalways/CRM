package com.wantedalways.service;

import com.wantedalways.entity.User;
import com.wantedalways.exception.LoginException;

public interface UserService {
    User login(String loginAct,String loginPwd,String ip) throws LoginException;
}
