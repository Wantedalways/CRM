package com.wantedalways.crm.workbench.controller;

import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.settings.entity.User;
import com.wantedalways.crm.settings.service.UserService;
import com.wantedalways.crm.util.DateUtil;
import com.wantedalways.crm.util.UUIDUtil;
import com.wantedalways.crm.vo.PageListVo;
import com.wantedalways.crm.workbench.entity.Activity;
import com.wantedalways.crm.workbench.entity.Clue;
import com.wantedalways.crm.workbench.service.ActivityService;
import com.wantedalways.crm.workbench.service.ClueService;
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
@RequestMapping(value = "/clue")
public class ClueController {

    @Resource
    private UserService userService;

    @Resource
    private ClueService clueService;

    @Resource
    private ActivityService activityService;

    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        return userService.findAllUser();
    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String,Object> addClue(Clue clue, HttpSession session) throws DMLException {

        User user = (User) session.getAttribute("user");

        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(user.getName());
        clue.setCreateTime(DateUtil.getDate());

        boolean result = clueService.addClue(clue);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",result);

        return resultMap;

    }

    @RequestMapping(value = "/clueList.do")
    @ResponseBody
    public PageListVo<Clue> clueList(Integer pageNo, Integer pageSize, Clue clue) {

        List<Clue> clueList = clueService.clueList(pageNo,pageSize,clue);

        int total = clueService.queryTotalByCondition(clue);

        PageListVo<Clue> result = new PageListVo<>();
        result.setTotal(total);
        result.setDataList(clueList);

        return result;

    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView toDetail(String id) {

        ModelAndView mv = new ModelAndView();

        Clue clue = clueService.getDetailById(id);

        mv.addObject("clue",clue);
        mv.setViewName("/workbench/clue/detail.jsp");

        return mv;
    }

    @RequestMapping(value = "/getActivityList.do")
    @ResponseBody
    public List<Activity> getActivityList(String clueId) {

        return activityService.getActivityList(clueId);

    }

    @RequestMapping(value = "/removeRelation.do")
    @ResponseBody
    public Map<String,Object> removeRelation(String id) throws DMLException {

        boolean result = clueService.removeRelationClue(id);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",result);

        return resultMap;
    }

    @RequestMapping(value = "/getActivityListByName.do")
    @ResponseBody
    public List<Activity> getActivityListByName(String clueId,String name) {

        return activityService.getActivityListByName(clueId,name);

    }

    @RequestMapping(value = "/addRelation.do")
    @ResponseBody
    public Map<String,Object> addRelation(String[] activityId,String clueId) throws DMLException {

        boolean result = clueService.addRelationClue(activityId,clueId);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("success",result);

        return resultMap;
    }
}
