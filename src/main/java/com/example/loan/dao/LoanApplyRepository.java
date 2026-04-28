package com.example.loan.dao;

import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.LoanProduct;
import com.example.loan.dao.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplyRepository extends JpaRepository<LoanApply,Integer> {
    Boolean existsByUserId(Integer userId);
    List<LoanApply> getByUserId(Integer userId);
    void deleteByUserId(Integer userId);

}
