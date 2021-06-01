package com.wantedalways.crm.workbench.service;

import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.workbench.entity.Clue;
import com.wantedalways.crm.workbench.entity.Tran;

import java.util.List;

public interface ClueService {


    boolean addClue(Clue clue) throws DMLException;

    List<Clue> clueList(Integer pageNo, Integer pageSize, Clue clue);

    int queryTotalByCondition(Clue clue);

    Clue getDetailById(String id);

    boolean removeRelationClue(String id) throws DMLException;

    boolean addRelationClue(String[] activityId, String clueId) throws DMLException;

    boolean convertClue(String clueId, Tran tran, String createBy) throws DMLException;
}
