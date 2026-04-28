package com.example.loan.service;

import com.example.loan.dao.*;
import com.example.loan.dao.entity.*;
import com.example.loan.utils.Calculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OverdueMonitorService {
    @Autowired
    private RepayPlanRepository repayPlanRepository;

    @Autowired
    private LoanContractRepository loanContractRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    @Autowired
    private LoanApplyRepository loanApplyRepository;

    @Autowired
    private BusinessSettlementRepository businessSettlementRepository;

    /**
     * 每5秒执行一次，检查并处理逾期的还款计划
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void checkAndHandleOverduePlans() {
        //查询所有已逾期且依旧开启业务的还款计划，装到List overduePlans中
        LocalDateTime now = LocalDateTime.now();
        List<RepayPlan> overduePlans = repayPlanRepository.findOverdueAndOpen(now);

//        if (overduePlans.isEmpty()) {
//            System.out.println("没有发现逾期记录。");
//            return;
//        }

        for(RepayPlan plan : overduePlans){
            LoanContract loanContract = loanContractRepository.getReferenceById(plan.getContractId());
            LoanApply loanApply = loanApplyRepository.getReferenceById(loanContract.getApplyId());
            LoanProduct loanProduct = loanProductRepository.getReferenceById(loanApply.getProductId());
            Integer period = loanApply.getApplyPeriod();

            if(period == plan.getCurrentPeriod()){
                //该计划已是最后一期
                //生成业务结算
                BusinessSettlement businessSettlement = new BusinessSettlement();
                businessSettlement.setUserId(plan.getUserId());
                businessSettlement.setContractId(plan.getContractId());
                businessSettlement.setStatus(false);

                //取得是否逾期判断返回的map键值对集合
                Map<String, Float> map = Calculate.isOverdue(plan);
                //要支付的总额
                float all;
                if(map != null){//有逾期
                    all = map.get("overdueMoney") + map.get("overduePunish") + plan.getPriIntAll();
                }else{//无逾期
                    all = plan.getPriIntAll();
                }
                businessSettlement.setRestLoan(all);
                businessSettlementRepository.save(businessSettlement);
            }else{
                //如果该计划并非最后一期，根据已经逾期且依旧开启事务的计划生成下一期还款计划并保存
                //此时生成下一期还款计划的逻辑可以借用一下根据已支付计划生成下一期计划的逻辑，只需要再改一下上一次已支付计划id和生育未还本金就行
                RepayPlan newPlan = Calculate.toNextPlan(plan);
                newPlan.setOveredPlanId(plan.getOveredPlanId());
                newPlan.setRestPrincipal(plan.getRestPrincipal());
                repayPlanRepository.save(newPlan);


            }
            //关闭事务
            plan.setStatus(false);
            repayPlanRepository.save(plan);
        }
    }
}
