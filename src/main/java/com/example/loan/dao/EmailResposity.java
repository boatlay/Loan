package com.example.loan.dao;

import com.example.loan.dao.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailResposity extends JpaRepository<Email,Integer> {
}