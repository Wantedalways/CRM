package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.Contacts;
import org.apache.ibatis.annotations.Param;

public interface ContactsDao {
    int insertContacts(Contacts contacts);

    int insertRelation(@Param("id") String id, @Param("contactsId")String contactsId, @Param("activityId")String activityId);
}
