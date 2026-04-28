package com.example.loan.service;

import com.example.loan.dao.RepayOverdueRecordRepository;
import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.entity.RepayOverdueRecord;
import com.example.loan.dao.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepayOverdueRecordService {
    @Autowired
    private RepayOverdueRecordRepository repayOverdueRecordRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<RepayOverdueRecord> getOverdueRecordByName(String name){
        UserAccount userAccount = userAccountRepository.getUserAccountByName(name);
        Integer userId = userAccount.getId();

        if(userId != null){
            List<RepayOverdueRecord> list = repayOverdueRecordRepository.getByUserId(userId);
            return list;
        }else{
            return null;
        }

    }
}
