package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int selectCountByAids(String[] id);

    int deleteRemarkByAids(String[] id);

    int insertRemark(ActivityRemark activityRemark);

    List<ActivityRemark> selectRemarkListByAid(String activityId);

    int deleteRemarkById(String id);

    int updateRemark(ActivityRemark activityRemark);
}
