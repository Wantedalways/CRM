package com.wantedalways.crm.workbench.controller;

import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/clue")
public class ClueController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        return userService.findAllUser();
    }
}
