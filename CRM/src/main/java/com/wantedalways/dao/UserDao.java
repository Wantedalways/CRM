package com.wantedalways.dao;

import com.wantedalways.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User selectUserLogin(@Param("loginAct") String loginAct,@Param("loginPwd") String loginPwd);
}
