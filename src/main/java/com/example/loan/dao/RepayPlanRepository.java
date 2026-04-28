package com.example.loan.dao;

import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.RepayPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepayPlanRepository extends JpaRepository<RepayPlan,Integer> {
    Boolean existsByUserId(Integer userId);
    List<RepayPlan> getByUserId(Integer userId);
    void deleteByUserId(Integer userId);

    Boolean existsByContractId(Integer contractId);
    RepayPlan getByContractId(Integer contractId);
    void deleteByContractId(Integer contractId);

    /**
     * 注解装配成找到表中所有repay_date字段早于now并且status字段为true的数据（即已逾期且依旧开启事务的数据），并填充到List中
     * @param now
     * @return
     */
    @Query("SELECT rp FROM RepayPlan rp WHERE rp.endTime < :now AND rp.status = true")
    List<RepayPlan> findOverdueAndOpen(@Param("now") LocalDateTime now);
}
