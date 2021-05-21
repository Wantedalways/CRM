package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.Activity;

import java.util.List;


public interface ActivityDao {

    int insertActivity(Activity activity);

    List<Activity> selectActivityList(Activity activity);
}
