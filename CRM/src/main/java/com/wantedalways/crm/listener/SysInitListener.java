package com.wantedalways.crm.listener;

import com.wantedalways.crm.workbench.entity.DicValue;
import com.wantedalways.crm.workbench.service.DicService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        /*
            添加数据字典至服务器缓存
        */

        // 获取全局作用域对象
        ServletContext application = servletContextEvent.getServletContext();
        // 通过spring容器对象获取DicService
        DicService dicService = WebApplicationContextUtils.getRequiredWebApplicationContext(application).getBean(DicService.class);
        
        Map<String,List<DicValue>> dicMap = dicService.getDic();

        // 遍历添加
        Set<Map.Entry<String, List<DicValue>>> dicSet = dicMap.entrySet();
        for (Map.Entry<String, List<DicValue>> node : dicSet) {

            application.setAttribute(node.getKey(),node.getValue());

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
