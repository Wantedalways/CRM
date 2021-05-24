package com.wantedalways.crm.workbench.service;

import com.wantedalways.crm.exception.ActivityException;
import com.wantedalways.crm.workbench.entity.Activity;

import java.util.List;


public interface ActivityService {

    int addActivity(Activity activity);

    List<Activity> pageList(Integer pageNo,Integer pageSize,Activity activity);

    Integer queryTotalByCondition(Activity activity);

    boolean delActivity(String[] id) throws ActivityException;
}
