package com.wantedalways.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.workbench.dao.ActivityDao;
import com.wantedalways.crm.workbench.dao.ActivityRemarkDao;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.entity.ActivityRemark;
import com.wantedalways.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;

    @Resource
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public boolean addActivity(Activity activity) throws DMLException {

        boolean flag = false;

        int result = activityDao.insertActivity(activity);

        if (result != 1) {

            throw new DMLException("添加市场活动失败！");

        }

        flag = true;

        return flag;
    }

    @Override
    public List<Activity> pageList(Integer pageNo, Integer pageSize, Activity activity) {

        PageHelper.startPage(pageNo,pageSize);
        return activityDao.selectActivityList(activity);
    }

    @Override
    public Integer queryTotalByCondition(Activity activity) {

        return activityDao.selectTotalByCondition(activity);
    }

    @Override
    public boolean delActivity(String[] id) throws DMLException {

        // 布尔标记
        boolean flag = false;

        // 先删除市场活动对应的备注
        // 查询备注总条数
        int idealCount = activityRemarkDao.selectCountByAids(id);
        // 删除备注
        int realityCount = activityRemarkDao.deleteRemarkByAids(id);

        if (idealCount != realityCount) {

            // 抛出异常，回滚事务
            throw new DMLException("市场活动备注删除错误！");
        }
        // 无异常抛出则执行删除市场活动操作
        int resultCount = activityDao.deleteActivityByIds(id);

        if (resultCount != id.length) {

            throw new DMLException("市场活动删除错误！");

        } else {

            // 无异常抛出则执行删除操作成功
            flag = true;

        }

        return flag;
    }

    @Override
    public Activity getActivityById(String id) {

        return activityDao.selectActivityById(id);

    }

    @Override
    public boolean editActivity(Activity activity) throws DMLException {

        boolean flag = false;

        int result = activityDao.updateActivity(activity);

        if (result != 1) {

            throw new DMLException("修改市场活动失败！");

        }

        flag = true;

        return flag;
    }

    @Override
    public Activity getDetailById(String id) {

        return activityDao.selectDetailById(id);
    }

    @Override
    public boolean addRemark(ActivityRemark activityRemark) throws DMLException {

        boolean flag = false;

        int result = activityRemarkDao.insertRemark(activityRemark);

        if (result != 1) {

            throw new DMLException("添加市场活动备注失败！");

        }

        flag = true;

        return flag;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        return activityRemarkDao.selectRemarkListByAid(activityId);
    }

    @Override
    public boolean deleteRemark(String id) throws DMLException {

        int result = activityRemarkDao.deleteRemarkById(id);

        if (result != 1) {

            throw new DMLException("删除市场活动备注失败！");

        }

        return true;
    }

    @Override
    public boolean updateRemark(ActivityRemark remark) throws DMLException {

        int result = activityRemarkDao.updateRemark(remark);

        if (result != 1) {

            throw new DMLException("修改市场活动备注失败！");

        }

        return true;
    }

    @Override
    public List<Activity> getActivityList(String clueId) {

        return activityDao.selectActivityRelation(clueId);

    }

    @Override
    public List<Activity> getActivityListByName(String clueId, String name) {

        return activityDao.selectActivityListByName(clueId,name);

    }
}

