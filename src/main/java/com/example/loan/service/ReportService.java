package com.example.loan.service;

import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.UserPrivateRepository;
import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.entity.UserInformation;
import com.example.loan.dao.entity.UserPrivate;
import com.example.loan.service.aiService.AIReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ReportService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserPrivateRepository userPrivateRepository;

    @Autowired
    private AIReportService reportService;

    @Autowired
    private RedisService redisService;

    public String generateReport(String userName){
        UserAccount userAccount=userAccountRepository.getUserAccountByName(userName);
        UserInformation userInformation=userInformationRepository.getUserInformationById(userAccount.getId());
        Integer score=userInformation.getCreditScore();
        UserPrivate userPrivate=userPrivateRepository.findUserPrivateById(userAccount.getId());
        String contactPrivate=userPrivate.getContactPrivate();
        String locationPrivate=userPrivate.getLocationPrivate();
        String bugPrivate=userPrivate.getBugPrivate();
        String message="用户信用分是"+score+"，用户昵称是"+userName;
        String result=reportService.chat(message);
        redisService.set(userName+"report",result,24, TimeUnit.HOURS);
        return result;
    }

    public String getReport(String userName){
        if(redisService.get(userName+"report")!=null){
            return (String) redisService.get(userName+"report");
        }else {
            return generateReport(userName);
        }
    }
}
