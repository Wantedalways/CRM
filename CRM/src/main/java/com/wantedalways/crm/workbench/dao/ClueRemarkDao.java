package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> selectRemarkByClueId(String clueId);

    int deleteByClueId(String clueId);
}
