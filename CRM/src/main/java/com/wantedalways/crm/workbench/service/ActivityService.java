package com.wantedalways.crm.workbench.service;

import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.entity.ActivityRemark;

import java.util.List;


public interface ActivityService {

    boolean addActivity(Activity activity) throws DMLException;

    List<Activity> pageList(Integer pageNo,Integer pageSize,Activity activity);

    Integer queryTotalByCondition(Activity activity);

    boolean delActivity(String[] id) throws DMLException;

    Activity getActivityById(String id);

    boolean editActivity(Activity activity) throws DMLException;

    Activity getDetailById(String id);

    boolean addRemark(ActivityRemark activityRemark) throws DMLException;

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id) throws DMLException;

    boolean updateRemark(ActivityRemark remark) throws DMLException;

    List<Activity> getActivityList(String clueId);

    List<Activity> getActivityListByName(String clueId, String name);
}
