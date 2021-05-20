package com.wantedalways.crm.settings.dao;

import com.wantedalways.crm.settings.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    User selectUserLogin(@Param("loginAct") String loginAct,@Param("loginPwd") String loginPwd);

    List<User> selectAllUser();
}
