package com.wantedalways.crm.workbench.service.impl;

import com.wantedalways.crm.workbench.dao.ActivityDao;
import com.wantedalways.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }
}
