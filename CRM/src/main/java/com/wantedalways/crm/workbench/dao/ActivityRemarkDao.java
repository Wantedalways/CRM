package com.wantedalways.crm.workbench.dao;

public interface ActivityRemarkDao {

    int selectCountByAids(String[] id);

    int deleteRemarkByAids(String[] id);
}
