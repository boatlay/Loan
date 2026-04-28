package com.example.loan.service;

import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void addInformation(UserInformation userInformation,Integer id){
        userInformation.setId(id);
        userInformation.setCreditScore(100);
        userInformationRepository.save(userInformation);
    }

    public int updateInformation(UserInformation userInformation,Integer id){
        UserInformation userInformation1= userInformationRepository.getUserInformationById(id);
        if(userInformation1==null){
            return 0;
        }

        userInformation.setId(userInformation1.getId());
        userInformation.setCreditScore(userInformation1.getCreditScore());
        userInformationRepository.save(userInformation);
        return 1;
    }

    public int getScore(String name){
        UserAccount userAccount= userAccountRepository.getUserAccountByName(name);
        if(userInformationRepository.existsUserInformationById(userAccount.getId())){
            UserInformation userInformation= userInformationRepository.getUserInformationById(userAccount.getId());
            return userInformation.getCreditScore();
        }else {
            return 100;
        }
    }
}
