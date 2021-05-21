package com.wantedalways.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.workbench.dao.ActivityDao;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Override
    public int addActivity(Activity activity) {

        return activityDao.insertActivity(activity);
    }

    @Override
    public List<Activity> pageList(Integer pageNo, Integer pageSize, Activity activity) {

        PageHelper.startPage(pageNo,pageSize);
        return activityDao.selectActivityList(activity);
    }
}
