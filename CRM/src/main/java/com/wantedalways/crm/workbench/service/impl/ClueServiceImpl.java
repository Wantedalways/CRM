package com.wantedalways.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.wantedalways.crm.exception.DMLException;
import com.wantedalways.crm.util.DateUtil;
import com.wantedalways.crm.util.UUIDUtil;
import com.wantedalways.crm.workbench.dao.*;
import com.wantedalways.crm.workbench.entity.*;
import com.wantedalways.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueDao clueDao;
    @Resource
    private ClueRemarkDao clueRemarkDao;

    @Resource
    private CustomerDao customerDao;
    @Resource
    private CustomerRemarkDao customerRemarkDao;

    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;

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

        PageHelper.startPage(pageNo, pageSize);

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

    @Override
    public boolean removeRelationClue(String id) throws DMLException {

        int result = clueDao.deleteRelation(id);

        if (result != 1) {

            throw new DMLException("解除关联失败！");

        }

        return true;
    }

    @Override
    public boolean addRelationClue(String[] activityId, String clueId) throws DMLException {

        int total = activityId.length;

        int count = 0;

        for (String aid : activityId) {

            int result = clueDao.addRelation(UUIDUtil.getUUID(), aid, clueId);

            if (result != 1) {

                throw new DMLException("关联失败,市场活动：" + aid);

            } else {

                count++;

            }
        }

        if (count != total) {

            throw new DMLException("关联失败！");

        }

        return true;
    }

    @Override
    public boolean convertClue(String clueId, Tran tran, String createBy) throws DMLException {

        // 通过线索id获取线索对象
        Clue clue = clueDao.selectClueById(clueId);

        // 通过线索对象提取客户信息(公司名)，客户不存在时，新建客户
        Customer customer = customerDao.selectCustomerByName(clue.getCompany());

        if (customer == null) {

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateUtil.getDate());
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            customer.setName(clue.getCompany());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());

            int count1 = customerDao.insertCustomer(customer);

            if (count1 != 1) {

                throw new DMLException("添加客户失败");

            }
        }

        // 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();

        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullName(clue.getFullName());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMobilePhone(clue.getMobilePhone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(DateUtil.getDate());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());

        int count2 = contactsDao.insertContacts(contacts);
        if (count2 != 1) {

            throw new DMLException("添加联系人失败！");

        }

        // 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.selectRemarkByClueId(clueId);

        if (clueRemarkList.size() > 0) {

            for (ClueRemark clueRemark : clueRemarkList) {

                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setNoteContent(clueRemark.getNoteContent());
                customerRemark.setCreateBy(createBy);
                customerRemark.setCreateTime(DateUtil.getDate());
                customerRemark.setEditFlag("0");
                customerRemark.setCustomerId(customer.getId());

                int count3 = customerRemarkDao.insertRemark(customerRemark);

                if (count3 != 1) {

                    throw new DMLException("转换客户备注失败！");

                }

                ContactsRemark contactsRemark = new ContactsRemark();
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setNoteContent(clueRemark.getNoteContent());
                contactsRemark.setCreateBy(createBy);
                contactsRemark.setCreateTime(DateUtil.getDate());
                contactsRemark.setEditFlag("0");
                contactsRemark.setContactsId(contacts.getId());

                int count4 = contactsRemarkDao.insertRemark(contactsRemark);

                if (count4 != 1) {

                    throw new DMLException("转换联系人备注失败！");

                }
            }
            // “线索和市场活动”的关系转换到“联系人和市场活动”的关系
            List<String> activityIds = clueDao.selectRelationByClueId(clueId);

            if (activityIds.size() > 0) {

                for (String activityId : activityIds) {

                    int count5 = contactsDao.insertRelation(UUIDUtil.getUUID(), contacts.getId(), activityId);

                    if (count5 != 1) {

                        throw new DMLException("转换联系人和市场活动关系失败！");

                    }
                }

                int count6 = clueDao.deleteRelationByClueId(clueId);

                if (count6 != activityIds.size()) {

                    throw new DMLException("转换关系时删除线索关系失败！");

                }
            }
        }

        // 如果有创建交易需求，创建一条交易
        if (tran.getId() != null) {

            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            tran.setSource(clue.getSource());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());

            int count7 = tranDao.insertTran(tran);

            if (count7 != 1) {

                throw new DMLException("线索转换时创建交易失败！");

            }
            // 创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();

            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(DateUtil.getDate());
            tranHistory.setTranId(tran.getId());

            int count8 = tranHistoryDao.insertHistory(tranHistory);

            if (count8 != 1) {

                throw new DMLException("线索转换时创建交易历史失败！");

            }
        }
        // 删除线索备注
        int count9 = clueRemarkDao.deleteByClueId(clueId);

        if (count9 != 1) {

            throw new DMLException("线索转换时删除备注失败！");

        }
        // 删除线索
        int count10 = clueDao.deleteClueById(clueId);

        if (count10 != 1) {

            throw new DMLException("线索转换时删除线索失败！");

        }

        return true;
    }
}
