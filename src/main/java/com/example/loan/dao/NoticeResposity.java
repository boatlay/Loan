package com.example.loan.dao;

import com.example.loan.dao.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeResposity extends JpaRepository<Notice,Integer> {
}