package com.example.loan.service;

import com.example.loan.dao.AuditAccountRepository;
import com.example.loan.dao.entity.AuditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditAccountService {

    @Autowired
    private AuditAccountRepository auditAccountRepository;

    public int login(AuditAccount auditAccount){
        AuditAccount auditAccount1= auditAccountRepository.login(auditAccount.getName(),auditAccount.getPassword());
        if(auditAccount1==null){
            return 0;
        }else {
            return 1;
        }
    }

    public AuditAccount modify(AuditAccount auditAccount,String name){
        AuditAccount auditAccount1= auditAccountRepository.getAuditAccountByName(name);
        auditAccount1.setName(auditAccount.getName());
        auditAccount1.setPassword(auditAccount.getPassword());
        auditAccountRepository.save(auditAccount1);
        return auditAccount1;
    }

    public void register(AuditAccount auditAccount){
        auditAccount.setPersonality("audit_worker");
        auditAccountRepository.save(auditAccount);
    }
}
