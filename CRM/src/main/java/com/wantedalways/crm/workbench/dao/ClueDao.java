package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {

    int insertClue(Clue clue);

    List<Clue> selectAllClue(Clue clue);

    int selectTotalByCondition(Clue clue);

    Clue selectDetailById(String id);

    int deleteRelation(String id);

    int addRelation(@Param("id") String id, @Param("activityId")String aid, @Param("clueId")String clueId);

    Clue selectClueById(String clueId);

    List<String> selectRelationByClueId(String clueId);

    int deleteRelationByClueId(String clueId);

    int deleteClueById(String clueId);
}
