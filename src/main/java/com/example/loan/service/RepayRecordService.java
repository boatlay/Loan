package com.example.loan.service;

import com.example.loan.dao.RepayRecordRepository;
import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.entity.RepayRecord;
import com.example.loan.dao.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepayRecordService {
    @Autowired
    private RepayRecordRepository repayRecordRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * 根据用户昵称查询还款记录
     * @param name
     * @return
     */
    public List<RepayRecord> getRecordByName(String name){
        UserAccount userAccount = userAccountRepository.getUserAccountByName(name);
        if(userAccount != null){
            Integer userId = userAccount.getId();
            List<RepayRecord> list= repayRecordRepository.getByUserId(userId);
            return list;
        }else{
            return null;
        }

    }
}
