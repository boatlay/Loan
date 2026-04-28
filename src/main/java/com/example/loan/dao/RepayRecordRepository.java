package com.example.loan.dao;

import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.RepayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RepayRecordRepository extends JpaRepository<RepayRecord,Integer> {
    Boolean existsByUserId(Integer userId);
    List<RepayRecord> getByUserId(Integer userId);
    void deleteByUserId(Integer userId);

    Boolean existsByPlanId(Integer planId);
    RepayRecord getByPlanId(Integer planId);
    void deleteByPlanId(Integer planId);
}
