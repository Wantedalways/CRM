package com.wantedalways.crm.workbench.service;

import com.wantedalways.crm.exception.ActivityException;
import com.wantedalways.crm.workbench.entity.Activity;

import java.util.List;


public interface ActivityService {

    boolean addActivity(Activity activity) throws ActivityException;

    List<Activity> pageList(Integer pageNo,Integer pageSize,Activity activity);

    Integer queryTotalByCondition(Activity activity);

    boolean delActivity(String[] id) throws ActivityException;

    Activity getActivityById(String id);

    boolean editActivity(Activity activity) throws ActivityException;
}
