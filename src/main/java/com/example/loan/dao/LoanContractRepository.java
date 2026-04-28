package com.example.loan.dao;

import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.LoanContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanContractRepository extends JpaRepository<LoanContract,Integer> {
    Boolean existsByUserId(Integer userId);
    List<LoanContract> getByUserId(Integer userId);
    void deleteByUserId(Integer userId);

    Boolean existsByApplyId(Integer applyId);
    LoanContract getByApplyId(Integer applyId);
    void deleteByApplyId(Integer applyId);
}
