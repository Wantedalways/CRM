package com.wantedalways.crm.workbench.service.impl;

import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.workbench.dao.ActivityDao;
import com.wantedalways.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

}
