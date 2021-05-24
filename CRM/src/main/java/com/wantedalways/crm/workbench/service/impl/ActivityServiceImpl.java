package com.wantedalways.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.wantedalways.crm.exception.ActivityException;
import com.wantedalways.crm.workbench.dao.ActivityDao;
import com.wantedalways.crm.workbench.dao.ActivityRemarkDao;
import com.wantedalways.crm.workbench.entity.Activity;
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
    public int addActivity(Activity activity) {

        return activityDao.insertActivity(activity);
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
    public boolean delActivity(String[] id) throws ActivityException {

        // 布尔标记
        boolean flag = false;

        // 先删除市场活动对应的备注
        // 查询备注总条数
        int idealCount = activityRemarkDao.selectCountByAids(id);
        // 删除备注
        int realityCount = activityRemarkDao.deleteRemarkByAids(id);

        if (idealCount != realityCount) {

            // 抛出异常，回滚事务
            throw new ActivityException("市场活动备注删除错误！");
        }
        // 无异常抛出则执行删除市场活动操作
        int resultCount = activityDao.deleteActivityByIds(id);

        if (resultCount != id.length) {

            throw new ActivityException("市场活动删除错误！");

        } else {

            // 无异常抛出则执行删除操作成功
            flag = true;

        }

        return flag;


    }
}

