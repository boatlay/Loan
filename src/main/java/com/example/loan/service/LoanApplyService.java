package com.example.loan.service;

import com.example.loan.dao.LoanApplyRepository;
import com.example.loan.dao.LoanProductRepository;
import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.LoanProduct;
import com.example.loan.dao.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApplyService {
    @Autowired
    private LoanApplyRepository loanApplyRepository;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    /**
     * 贷款申请loanApply已有id，name、productId、applyQuota、applyPeriod、userId、repayMethod，填写贷款申请剩下的属性
     * @param loanApply
     * @return
     */
    public boolean complete(LoanApply loanApply){
        //根据贷款申请loanApply得到贷款产品loanProduct
        Integer productId = loanApply.getProductId();
        LoanProduct loanProduct = loanProductRepository.getReferenceById(productId);

        if(loanApply.getName().equals(userInformationRepository.getNameById(loanApply.getUserId()))
        && loanApply.getApplyQuota() >= loanProduct.getMinQuota()
        && loanApply.getApplyQuota() <= loanProduct.getMaxQuota()
        && loanApply.getApplyPeriod() >= loanProduct.getMinPeriod()
        && loanApply.getApplyPeriod() <= loanProduct.getMaxQuota()){
            loanApply.setApplyStatus("审核中");
            loanApply.setRefuseReason("未拒绝");

            loanApplyRepository.save(loanApply);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据用户昵称查询贷款申请
     * @param name
     * @return
     */
    public List<LoanApply> getApplyByName(String name){
        UserAccount userAccount = userAccountRepository.getUserAccountByName(name);
        if(userAccount != null){
            Integer userId = userAccount.getId();
            List<LoanApply> list= loanApplyRepository.getByUserId(userId);
            return list;
        }else{
            return null;
        }
    }
}
