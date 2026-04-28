package com.example.loan.dao;

import com.example.loan.dao.entity.RepayOverdueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepayOverdueRecordRepository extends JpaRepository<RepayOverdueRecord,Integer> {
    Boolean existsByUserId(Integer userId);
    List<RepayOverdueRecord> getByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}
