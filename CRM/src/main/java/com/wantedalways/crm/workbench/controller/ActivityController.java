package com.wantedalways.crm.workbench.controller;

import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.settings.service.UserService;
import com.wantedalways.crm.util.DateUtil;
import com.wantedalways.crm.util.UUIDUtil;
import com.wantedalways.crm.vo.PageListVo;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.entity.ActivityRemark;
import com.wantedalways.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private UserService userService;

    /**
     * 查询所有用户，用于添加市场活动的模态窗口
     * @return 返回用户的List集合
     */
    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        return userService.findAllUser();
    }

    /**
     * 保存市场活动，用于添加市场活动的模态窗口
     * @param activity  使用Activity对象接收前端参数
     * @param session   用于获取会话作用域中的登录用户，得到市场活动的创建人
     * @return  返回添加是否成功的布尔标记
     */
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity, HttpSession session) throws DMLException {

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.getDate());
        User user = (User) session.getAttribute("user");
        activity.setCreateBy(user.getName());

        boolean flag = activityService.addActivity(activity);

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("success",flag);

        return resultMap;
    }

    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public PageListVo<Activity> pageList(Integer pageNo, Integer pageSize, Activity activity) {

        List<Activity> dataList = activityService.pageList(pageNo,pageSize,activity);
        Integer total = activityService.queryTotalByCondition(activity);

        PageListVo<Activity> result = new PageListVo<>();
        result.setTotal(total);
        result.setDataList(dataList);

        return result;
    }

    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public Map<String,Object> delActivity(String[] id) throws DMLException {

        boolean success = activityService.delActivity(id);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",success);

        return resultMap;
    }

    @RequestMapping(value = "/getActivityById.do")
    @ResponseBody
    public Activity getActivityById(String id) {

        return activityService.getActivityById(id);
    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map<String,Object> editActivity(Activity activity,HttpSession session) throws DMLException {

        User user = (User) session.getAttribute("user");
        activity.setEditBy(user.getName());
        activity.setEditTime(DateUtil.getDate());

        boolean flag = activityService.editActivity(activity);

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("success",flag);

        return resultMap;
    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView toDetail(String id) {

        Activity activity = activityService.getDetailById(id);

        ModelAndView mv = new ModelAndView();
        mv.addObject("activity",activity);
        mv.setViewName("/workbench/activity/detail.jsp");

        return mv;
    }

    @RequestMapping(value = "/addRemark.do")
    @ResponseBody
    public Map<String,Object> addRemark(ActivityRemark activityRemark,HttpSession session) throws DMLException {

        User user = (User) session.getAttribute("user");

        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateUtil.getDate());
        activityRemark.setCreateBy(user.getName());

        boolean flag = activityService.addRemark(activityRemark);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",flag);
        resultMap.put("remark",activityRemark);

        return resultMap;
    }

    @RequestMapping(value = "/getRemarkListByAid.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        return activityService.getRemarkListByAid(activityId);
    }

    @RequestMapping(value = "/deleteRemark.do")
    @ResponseBody
    public Map<String,Object> deleteRemarkById(String id) throws DMLException {

        boolean flag = activityService.deleteRemark(id);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",flag);

        return resultMap;
    }

    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark remark,HttpSession session) throws DMLException {

        User user = (User) session.getAttribute("user");

        remark.setEditBy(user.getName());
        remark.setEditTime(DateUtil.getDate());

        boolean flag = activityService.updateRemark(remark);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",flag);
        resultMap.put("remark",remark);

        return resultMap;
    }
}
