package com.wantedalways.crm.workbench.controller;

import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.settings.service.UserService;
import com.wantedalways.crm.util.DateUtil;
import com.wantedalways.crm.util.UUIDUtil;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        return userService.findAllUser();
    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity, HttpSession session) {

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.getDate());
        User user = (User) session.getAttribute("user");
        activity.setCreateBy(user.getName());

        int result = activityService.addActivity(activity);

        Map<String,Object> resultMap = new HashMap<>();
        if (result > 0) {

            resultMap.put("success",true);

        } else {

            resultMap.put("success",false);
        }

        return resultMap;
    }
}
