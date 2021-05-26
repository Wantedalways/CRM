package com.wantedalways.crm.workbench.service;

import com.wantedalways.crm.exception.ActivityException;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.entity.ActivityRemark;

import java.util.List;


public interface ActivityService {

    boolean addActivity(Activity activity) throws ActivityException;

    List<Activity> pageList(Integer pageNo,Integer pageSize,Activity activity);

    Integer queryTotalByCondition(Activity activity);

    boolean delActivity(String[] id) throws ActivityException;

    Activity getActivityById(String id);

    boolean editActivity(Activity activity) throws ActivityException;

    Activity getDetailById(String id);

    boolean addRemark(ActivityRemark activityRemark) throws ActivityException;

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id) throws ActivityException;

    boolean updateRemark(ActivityRemark remark) throws ActivityException;
}
