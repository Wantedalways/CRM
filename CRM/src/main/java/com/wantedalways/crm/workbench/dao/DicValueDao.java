package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.DicType;
import com.wantedalways.crm.workbench.entity.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getDicValue(String typeCode);
}
