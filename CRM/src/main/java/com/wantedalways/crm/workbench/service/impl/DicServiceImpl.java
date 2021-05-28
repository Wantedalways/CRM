package com.wantedalways.crm.workbench.service.impl;

import com.wantedalways.crm.workbench.dao.DicTypeDao;
import com.wantedalways.crm.workbench.dao.DicValueDao;
import com.wantedalways.crm.workbench.entity.DicType;
import com.wantedalways.crm.workbench.entity.DicValue;
import com.wantedalways.crm.workbench.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {

    @Resource
    private DicTypeDao dicTypeDao;

    @Resource
    private DicValueDao dicValueDao;

    @Override
    public Map<String,List<DicValue>> getDic() {

        Map<String,List<DicValue>> dicMap = new HashMap<>();

        List<DicType> dicTypeList = dicTypeDao.getDicType();

        for (DicType type : dicTypeList) {

            dicMap.put(type.getCode(),dicValueDao.getDicValue(type.getCode()));

        }

        return dicMap;
    }
}
