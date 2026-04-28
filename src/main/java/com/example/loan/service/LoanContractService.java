package com.example.loan.service;

import com.example.loan.dao.LoanApplyRepository;
import com.example.loan.dao.LoanContractRepository;
import com.example.loan.dao.entity.LoanContract;
import com.example.loan.dao.entity.LoanApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanContractService {
    @Autowired
    private LoanContractRepository loanContractRepository;

    @Autowired
    private LoanApplyRepository loanApplyRepository;

    /**
     * 根据只有userId和applyId的loanContract，完善loanContract的信息
     * @param loanContract
     * @return
     */
    public boolean autoComplete(LoanContract loanContract){
        if(loanContractRepository.getByApplyId(loanContract.getApplyId()) == null){
            loanContract.setContractContent("合同内容......");

            //根据生效时间startTime和申请期限applyPeriod，计算终止时间endTime
            LoanApply loanApply = loanApplyRepository.getReferenceById(loanContract.getApplyId());
            loanContract.setStartTime(LocalDateTime.now());
            LocalDateTime endTime = loanContract.getStartTime().plusMonths(loanApply.getApplyPeriod());
            loanContract.setEndTime(endTime);

            loanContract.setOverdueInterestMethod("罚息利率为产品利率的150%、按日罚息：除了缴纳逾期阶段内应还的本金和利息以外，还要缴纳罚息=剩余未还本金×罚息日利率×逾期天数");

            loanContractRepository.save(loanContract);
            return true;
        }else{
            return false;
        }
    }
}
