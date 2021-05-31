package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ActivityDao {

    int insertActivity(Activity activity);

    List<Activity> selectActivityList(Activity activity);

    int selectTotalByCondition(Activity activity);

    int deleteActivityByIds(String[] id);

    Activity selectActivityById(String id);

    int updateActivity(Activity activity);

    Activity selectDetailById(String id);

    List<Activity> selectActivityRelation(String clueId);

    List<Activity> selectActivityListByName(@Param("clueId") String clueId, @Param("name") String name);
}
