package com.example.loan.service;

import com.example.loan.dao.*;
import com.example.loan.dao.entity.*;
import com.example.loan.utils.Calculate;
import com.example.loan.utils.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RepayPlanService {
    @Autowired
    private RepayPlanRepository repayPlanRepository;

    @Autowired
    private RepayRecordRepository repayRecordRepository;

    @Autowired
    private LoanApplyRepository loanApplyRepository;

    @Autowired
    private LoanContractRepository loanContractRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RepayOverdueRecordRepository repayOverdueRecordRepository;

    @Autowired
    private BusinessSettlementRepository businessSettlementRepository;

    /**
     * 根据只有contractId的repayPlan，完善repayPlan的信息
     * @param repayPlan
     * @return
     */
    public boolean autoComplete(RepayPlan repayPlan){
        if(repayPlan.getContractId() != null && repayPlanRepository.getByContractId(repayPlan.getContractId()) == null){

            Calculate.toFirstPlan(repayPlan);
            repayPlanRepository.save(repayPlan);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据用户昵称查询还款计划
     * @param name
     * @return
     */
    public List<RepayPlan> getPlanByName(String name){
        UserAccount userAccount = userAccountRepository.getUserAccountByName(name);
        if(userAccount != null){
            Integer userId = userAccount.getId();
            List<RepayPlan>  list = repayPlanRepository.getByUserId(userId);
            return list;
        }else{
            return null;
        }

    }

    /**
     * 根据planId查询plan，返回repayPlan
     * @param id
     * @return
     */
    public RepayPlan getPlanById(Integer id){
        return repayPlanRepository.getReferenceById(id);
    }

    /**
     * 根据传入的repayPlan的id去更新数据表repay_plan中id一致的列
     * @param repayPlan
     */
    public void updateById(RepayPlan repayPlan){

    }

    /**
     * 还款：
     * 若支付失败，则返回false;
     * 若支付成功，则生成还款记录repayRecord并保存、根据传入的repayPlan生成下一期还款计划nextPlan(判断是否是最后一期)并保存，返回true
     * 若属于逾期支付，则额外还要生成逾期记录表repayOverdueRecord并保存，同样返回true
     * @param repayPlan
     * @return
     */
    public boolean repay(RepayPlan repayPlan, String repayMethod){
        //根据repayPlan取得LoanApply，从而取得总期数
        Integer contractId = repayPlan.getContractId();
        LoanContract loanContract = loanContractRepository.getReferenceById(contractId);
        Integer applyId = loanContract.getApplyId();
        LoanApply loanApply = loanApplyRepository.getReferenceById(applyId);
        Integer applyPeriod = loanApply.getApplyPeriod();

        //取得是否逾期判断返回的map键值对集合
        Map<String, Float> map = Calculate.isOverdue(repayPlan);
        //要支付的总额
        float all;
        if(map != null){//有逾期
            all = map.get("overdueMoney") + map.get("overduePunish") + repayPlan.getPriIntAll();
        }else{//无逾期
            all = repayPlan.getPriIntAll();
        }

        if(Pay.pay(all, repayMethod)){//支付成功
            //生成还款记录
            RepayRecord repayRecord = new RepayRecord();
            repayRecord.setPlanId(repayPlan.getId());
            repayRecord.setUserId(repayPlan.getUserId());
            repayRecord.setCurrentPeriod(repayPlan.getCurrentPeriod());
            repayRecord.setRepayTime(LocalDateTime.now());
            repayRecord.setRepayMethod(repayMethod);
            repayRecord.setRepayPrincipal(repayPlan.getRepayPrincipal());
            repayRecord.setRepayInterest(repayPlan.getRepayInterest());
            if(map != null){//有逾期
                repayRecord.setOverdueMoney(map.get("overdueMoney"));
                repayRecord.setOverduePunish(map.get("overduePunish"));
            }else{//无逾期
                repayRecord.setOverdueMoney(0);
                repayRecord.setOverduePunish(0);
            }
            repayRecord.setRepayAll(all);
            //保存还款记录
            repayRecordRepository.save(repayRecord);

            //当前期数不是最后一期，则要更新成下一期还款计划并保存
            if(repayPlan.getCurrentPeriod() != applyPeriod){
                RepayPlan nextRepayPlan = Calculate.toNextPlan(repayPlan);
                repayPlanRepository.save(nextRepayPlan);
            }else{//若是最后一期，则生成业务结算并保存
                BusinessSettlement businessSettlement = new BusinessSettlement();
                businessSettlement.setUserId(repayPlan.getUserId());
                businessSettlement.setContractId(repayPlan.getContractId());
                businessSettlement.setStatus(true);
                businessSettlement.setRestLoan(0);
                businessSettlementRepository.save(businessSettlement);
            }

            //如果是支付包括了逾期应付和罚息，则还要生成还款逾期记录并保存
            if(map != null){
                RepayOverdueRecord repayOverdueRecord = Calculate.toRepayOverdueRecord(repayRecord);
                repayOverdueRecordRepository.save(repayOverdueRecord);
            }

            //将当期计划的业务状态设置成false
            repayPlan.setStatus(true);
            repayPlanRepository.save(repayPlan);

            return true;
        }else{//支付失败返回false
            return false;
        }
    }
}
