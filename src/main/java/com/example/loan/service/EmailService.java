package com.example.loan.service;

import com.example.loan.dao.EmailResposity;
import com.example.loan.dao.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailResposity emailResposity;

    public void addEmail(Email email){
        email.setAvatarColor("#f5a623");
        emailResposity.save(email);
    }

    public Page<Email> getPage(Pageable pageable){
        return emailResposity.findAll(pageable);
    }

    public void deleteEmail(Email email){
        emailResposity.deleteById(email.getId());
    }
}
