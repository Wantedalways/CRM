package com.wantedalways.crm.workbench.service;

import com.wantedalways.crm.workbench.entity.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    Map<String,List<DicValue>> getDic();
}
