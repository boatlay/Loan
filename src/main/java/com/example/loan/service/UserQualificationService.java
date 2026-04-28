package com.example.loan.service;

import com.example.loan.dao.UserQualificationRepository;
import com.example.loan.dao.entity.UserQualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQualificationService {

    @Autowired
    private UserQualificationRepository userQualificationRepository;

    public void addUserQualification(UserQualification userQualification,Integer id){
        userQualification.setId(id);
        userQualificationRepository.save(userQualification);
    }
}
