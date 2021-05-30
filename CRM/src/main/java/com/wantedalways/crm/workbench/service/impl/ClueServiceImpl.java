package com.wantedalways.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.workbench.dao.ClueDao;
import com.wantedalways.crm.workbench.entity.Clue;
import com.wantedalways.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueDao clueDao;

    @Override
    public boolean addClue(Clue clue) throws DMLException {

        int result = clueDao.insertClue(clue);

        if (result != 1) {

            throw new DMLException("添加线索失败！");
        }

        return true;
    }

    @Override
    public List<Clue> clueList(Integer pageNo, Integer pageSize, Clue clue) {

        PageHelper.startPage(pageNo,pageSize);

        return clueDao.selectAllClue(clue);
    }

    @Override
    public int queryTotalByCondition(Clue clue) {

        return clueDao.selectTotalByCondition(clue);
    }

    @Override
    public Clue getDetailById(String id) {

        return clueDao.selectDetailById(id);
    }
}
