package com.wantedalways.crm.workbench.dao;

import com.wantedalways.crm.workbench.entity.Clue;

import java.util.List;

public interface ClueDao {

    int insertClue(Clue clue);

    List<Clue> selectAllClue(Clue clue);

    int selectTotalByCondition(Clue clue);

    Clue selectDetailById(String id);
}
