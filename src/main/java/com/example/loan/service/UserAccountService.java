package com.example.loan.service;

import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void register(UserAccount userAccount){
        userAccountRepository.save(userAccount);
    }

    public boolean existsUserAccount(UserAccount userAccount){
        return userAccountRepository.existsUserAccountByName(userAccount.getName());
    }

    public int login(UserAccount userAccount){
        UserAccount userAccount1= userAccountRepository.login(userAccount.getName(),userAccount.getPassword());
        if(userAccount1!=null){
            return userAccount1.getId();
        }else {
            return 0;
        }
    }

    public UserAccount getUserAccountByName(String name){
        return userAccountRepository.getUserAccountByName(name);
    }
}
