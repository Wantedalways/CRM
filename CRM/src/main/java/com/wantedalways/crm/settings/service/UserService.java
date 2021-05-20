package com.wantedalways.crm.settings.service;

import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.exception.LoginException;

import java.util.List;

public interface UserService {

    User login(String loginAct,String loginPwd,String ip) throws LoginException;

    List<User> findAllUser();
}
