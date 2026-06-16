package com.example.loan.utils;

import com.example.loan.dao.*;
import com.example.loan.dao.entity.*;
import com.example.loan.exception.RepayMethodIllegalException;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.entity.UserInformation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 各种金融计算
 */
@Service
public class Calculate {
    //逾期罚息倍率
    private static final BigDecimal PUNISH_RATE = BigDecimal.valueOf(1.5);
    //逾期信用扣分：第一期基础扣分
    private static final float CREDIT_DECREASE_BASE_SCORE = 5f;
    //逾期信用扣分：零散天数每日扣分率
    private static final float CREDIT_DECREASE_DAILY_RATE = 0.1f;

    private static RepayPlanRepository repayPlanRepository;
    private static RepayRecordRepository repayRecordRepository;
    private static LoanApplyRepository loanApplyRepository;
    private static LoanContractRepository loanContractRepository;
    private static LoanProductRepository loanProductRepository;
    private static UserAccountRepository userAccountRepository;
    private static UserInformationRepository userInformationRepository;

    @Autowired
    private RepayPlanRepository autowiredRepayPlanRepo;
    @Autowired
    private RepayRecordRepository autowiredRepayRecordRepo;
    @Autowired
    private LoanApplyRepository autowiredLoanApplyRepo;
    @Autowired
    private LoanContractRepository autowiredLoanContractRepo;
    @Autowired
    private LoanProductRepository autowiredLoanProductRepo;
    @Autowired
    private UserAccountRepository autowiredUserAccountRepo;
    @Autowired
    private UserInformationRepository autowiredUserInfoRepo;

    @PostConstruct
    public void init() {
        repayPlanRepository = this.autowiredRepayPlanRepo;
        repayRecordRepository = this.autowiredRepayRecordRepo;
        loanApplyRepository = this.autowiredLoanApplyRepo;
        loanContractRepository = this.autowiredLoanContractRepo;
        loanProductRepository = this.autowiredLoanProductRepo;
        userAccountRepository = this.autowiredUserAccountRepo;
        userInformationRepository = this.autowiredUserInfoRepo;
    }

    /**
     * 将传入的还款计划填写为第一期还款计划
     *
     * @param repayPlan 传入的还款计划对象
     */
    public static void toFirstPlan(RepayPlan repayPlan) {
        //获取对应的贷款合同、贷款申请和贷款产品信息
        LoanContract loanContract = loanContractRepository.getReferenceById(repayPlan.getContractId());
        LoanApply loanApply = loanApplyRepository.getReferenceById(loanContract.getApplyId());
        LoanProduct loanProduct = loanProductRepository.getReferenceById(loanApply.getProductId());

        //填写用户id，合同id，当前期数
        repayPlan.setUserId(loanContract.getUserId());
        repayPlan.setContractId(loanContract.getId());
        repayPlan.setCurrentPeriod(1);

        //贷款总额
        BigDecimal total = BigDecimal.valueOf(loanApply.getApplyQuota());
        //贷款（月/期）利率
        BigDecimal periodRate = BigDecimal.valueOf(loanProduct.getRate() / 12);
        //贷款总期数
        int n = loanApply.getApplyPeriod();
        //当前期数
        int curN = 1;
        //还款方式->等额本息或者先息后本
        String repayMethod = loanProduct.getRepayMethod();

        //分情况计算本金和利息（由于是第一期，故罚金不参与考虑）
        BigDecimal principal;
        BigDecimal interest;
        if ("等额本息".equals(repayMethod)) {
            //利息计算
            interest = total.multiply(periodRate);

            BigDecimal one = new BigDecimal(1);//1
            BigDecimal pow = periodRate.add(one).pow(n);//(1+m)^N
            BigDecimal fenzi = pow.multiply(periodRate);
            BigDecimal fenmu = pow.subtract(one);

            //principle本金等于每月固定的还额减去interest利息
//            principal = total.multiply(fenzi).divide(fenmu).subtract(interest);
            principal=total.multiply(fenzi).divide(fenmu,2, RoundingMode.HALF_UP).subtract(interest);

            repayPlan.setRepayPrincipal(principal.floatValue());
            repayPlan.setRepayInterest(interest.floatValue());

        } else if ("先息后本".equals(repayMethod)) {
            interest = total.multiply(periodRate);

            repayPlan.setRepayPrincipal(0);
            repayPlan.setRepayInterest(interest.floatValue());
        }

        repayPlan.setPriIntAll(repayPlan.getRepayPrincipal() + repayPlan.getRepayInterest());
        repayPlan.setOveredPlanId(null);
        repayPlan.setRestPrincipal(total.floatValue());
        repayPlan.setStartTime(LocalDateTime.now());
        repayPlan.setEndTime(TimeCalculator.toOneMonthNextDateTime(repayPlan.getStartTime()));
        repayPlan.setStatus(true);
    }

    /**
     * 若当前时间支付不算逾期，则返回null
     * 若当前时间支付属于逾期，则返回逾期罚息和逾期应还的键值对map
     *
     * @param repayPlan
     */
    public static Map<String, Float> isOverdue(RepayPlan repayPlan) {
        Map<String, Float> map = new HashMap<>();
        //获取对应的贷款合同、贷款申请和贷款产品信息
        LoanContract loanContract = loanContractRepository.getReferenceById(repayPlan.getContractId());
        LoanApply loanApply = loanApplyRepository.getReferenceById(loanContract.getApplyId());
        LoanProduct loanProduct = loanProductRepository.getReferenceById(loanApply.getProductId());
        //获取上一次已支付的计划的对象
//        RepayPlan overedPlan = repayPlanRepository.getReferenceById(repayPlan.getOveredPlanId());

        // 🌟 核心修复：处理第 1 期没有上一期计划的情况
        LocalDateTime lastEndTime;
        int lastPeriod;

        if (repayPlan.getOveredPlanId() != null) {
            // 如果有上一期，正常去数据库查
            RepayPlan overedPlan = repayPlanRepository.getReferenceById(repayPlan.getOveredPlanId());
            lastEndTime = overedPlan.getEndTime();
            lastPeriod = overedPlan.getCurrentPeriod();
        } else {
            // 如果是第 1 期（没有上一期）
            // 那么“上一期的结束时间”就相当于“本期的开始时间”（放款时间）
            lastEndTime = repayPlan.getStartTime();
            lastPeriod = 0;
        }

        //贷款总额
        BigDecimal total = BigDecimal.valueOf(loanApply.getApplyQuota());
        //贷款（月/期）利率
        BigDecimal periodRate = BigDecimal.valueOf(loanProduct.getRate() / 12);
        //贷款总期数
        int n = loanApply.getApplyPeriod();
        //当前期数
        int curN = repayPlan.getCurrentPeriod();

        BigDecimal overdueMoney;
        BigDecimal overduePunish;
//        if (TimeCalculator.isOverdue(overedPlan.getEndTime())) {//逾期
        if (TimeCalculator.isOverdue(lastEndTime)) {//逾期

            //按照还款方式计算每期应付
            BigDecimal periodPay;
            if ("等额本息".equals(loanProduct.getRepayMethod())) {
                BigDecimal one = new BigDecimal(1);//1
                BigDecimal pow = periodRate.add(one).pow(n);//(1+m)^N
                BigDecimal fenzi = pow.multiply(periodRate);
                BigDecimal fenmu = pow.subtract(one);
//                periodPay = total.multiply(fenzi).divide(fenmu);
                periodPay = total.multiply(fenzi).divide(fenmu, 2, RoundingMode.HALF_UP);
            } else if ("先息后本".equals(loanProduct.getRepayMethod())) {
                periodPay = total.multiply(periodRate);
            } else {
                throw new RepayMethodIllegalException();
            }

            //逾期应还额为-->每期应还额乘以逾期期数
//            overdueMoney = periodPay.multiply(BigDecimal.valueOf(repayPlan.getCurrentPeriod() - overedPlan.getCurrentPeriod() - 1));

            overdueMoney = periodPay.multiply(BigDecimal.valueOf(repayPlan.getCurrentPeriod() - lastPeriod - 1));

            //逾期天数为-->上次已还计划的endTime到当前时间的天数
//            BigDecimal overdueDay = BigDecimal.valueOf(TimeCalculator.calNumToTargetDay(overedPlan.getEndTime()));

            BigDecimal overdueDay = BigDecimal.valueOf(TimeCalculator.calNumToTargetDay(lastEndTime));

            //计算剩余本金和日息
            BigDecimal restPrincipal = BigDecimal.valueOf(repayPlan.getRestPrincipal());
            BigDecimal dayRate = BigDecimal.valueOf(loanProduct.getRate() / 365);
            //逾期罚息额为-->剩余未还本金乘以逾期天数乘以日利率乘以罚息倍率
            overduePunish = restPrincipal.multiply(overdueDay).multiply(dayRate).multiply(PUNISH_RATE);

            map.put("overdueMoney", overdueMoney.floatValue());
            map.put("overduePunish", overduePunish.floatValue());

            return map;
        } else {//没有逾期
            return null;
        }
    }

    /**
     * 支付完成后，根据当前计划填写下一期还款计划并返回
     * @param repayPlan 当前还款计划
     * @return
     */
    public static RepayPlan toNextPlan(RepayPlan repayPlan) {
        //获取对应的贷款合同、贷款申请和贷款产品信息
        LoanContract loanContract = loanContractRepository.getReferenceById(repayPlan.getContractId());
        LoanApply loanApply = loanApplyRepository.getReferenceById(loanContract.getApplyId());
        LoanProduct loanProduct = loanProductRepository.getReferenceById(loanApply.getProductId());

        //总期数
        Integer applyPeriod = loanApply.getApplyPeriod();

        RepayPlan nextRepayPlan = new RepayPlan();

        nextRepayPlan.setUserId(repayPlan.getUserId());
        nextRepayPlan.setContractId(repayPlan.getContractId());
        nextRepayPlan.setCurrentPeriod(repayPlan.getCurrentPeriod() + 1);

        if ("先息后本".equals(loanProduct.getRepayMethod())) {
            if (repayPlan.getCurrentPeriod() + 1 < applyPeriod) {//下一期计划并非是最后一期
                //应还本金其实一直为零（也就是不变）
                nextRepayPlan.setRepayPrincipal(repayPlan.getRepayPrincipal());
                //应还利息一直不变
                nextRepayPlan.setRepayInterest(repayPlan.getRepayInterest());
            } else {//下一期计划已是最后一期
                //应还本金最后一期变为剩余本金（也就是总贷款额，因为之前每一期都没有还本金）
                nextRepayPlan.setRepayPrincipal(repayPlan.getRestPrincipal());
                //应还利息一直不变哪怕是最后一期
                nextRepayPlan.setRepayInterest(repayPlan.getRepayInterest());
            }
        } else if ("等额本息".equals(loanProduct.getRepayMethod())) {
            //等额本息模式下，下一期是否是最后一期与本金利息计算无关

            //贷款（月/期）利率
            BigDecimal periodRate = BigDecimal.valueOf(loanProduct.getRate() / 12);
            //当前剩余本金
            BigDecimal restPrincipal = BigDecimal.valueOf(repayPlan.getRestPrincipal());

            //应还利息等于剩余本金乘以月利率
            nextRepayPlan.setRepayInterest(restPrincipal.multiply(periodRate).floatValue());
            //应还本金等于每期固定还金额减去应还利息
            nextRepayPlan.setRepayPrincipal(repayPlan.getPriIntAll() - nextRepayPlan.getRepayInterest());
        } else {
            throw new RepayMethodIllegalException();
        }

        //下一期计划的应还本金利息加和应该与当前计划的一样
        nextRepayPlan.setPriIntAll(repayPlan.getPriIntAll());
        //下一期计划的上一期已还计划id就是当前计划id
        nextRepayPlan.setOveredPlanId(repayPlan.getId());
        //下一期计划的剩余本金等于已支付的当前计划的剩余本金减去当前计划的应还本金
        nextRepayPlan.setRestPrincipal(repayPlan.getRestPrincipal() - repayPlan.getRepayPrincipal());
        //下一期计划开始时间为当前计划的截止时间
        nextRepayPlan.setStartTime(repayPlan.getEndTime());
        //下一期计划截止时间为其本身开始时间一月期之后
        nextRepayPlan.setEndTime(TimeCalculator.toOneMonthNextDateTime(nextRepayPlan.getStartTime()));
        //下一期计划业务处理状态初始为是，代表业务还需要处理
        nextRepayPlan.setStatus(true);

        return nextRepayPlan;
    }

    /**
     * 若支付包括逾期金额，那么支付后，根据还款记录生成还款逾期记录
     *
     * @param repayRecord
     * @return
     */
    public static RepayOverdueRecord toRepayOverdueRecord(RepayRecord repayRecord) {
        //得到还款记录当期的还款计划
        RepayPlan repayPlan = repayPlanRepository.getReferenceById(repayRecord.getPlanId());

        LocalDateTime startTime;
        int lastPeriod;
        if (repayPlan.getOveredPlanId() != null) {
            //得到当期还款计划的上一期已支付计划
            RepayPlan overedPlan = repayPlanRepository.getReferenceById(repayPlan.getOveredPlanId());
            startTime = overedPlan.getEndTime();
            lastPeriod = overedPlan.getCurrentPeriod();
        } else {
            //第1期逾期，没有上一期已支付计划
            startTime = repayPlan.getStartTime();
            lastPeriod = 0;
        }

        RepayOverdueRecord repayOverdueRecord = new RepayOverdueRecord();
        repayOverdueRecord.setContractId(repayPlan.getContractId());
        repayOverdueRecord.setUserId(repayRecord.getUserId());
        repayOverdueRecord.setStartTime(startTime);
        repayOverdueRecord.setEndTime(repayRecord.getRepayTime());
        //填写逾期期数
        repayOverdueRecord.setOverduePeriod(repayPlan.getCurrentPeriod() - lastPeriod - 1);
        //填写逾期总额
        repayOverdueRecord.setOverdueAll(repayRecord.getOverdueMoney() + repayRecord.getOverduePunish());

        // 逾期信用分扣减
        // 公式: creditDecrease = 期数累进扣分 + 零散天数扣分
        //
        // 1 期数累进扣分: 连续逾期期数越多，后续每期扣分越重（等差数列）
        //    第1期扣 baseScore，第2期扣 2×baseScore，第3期扣 3×baseScore ...
        //    前 n 期合计 = baseScore × n × (n+1) / 2
        //
        // 2 零散天数扣分: 超出完整期数（30天/期）的零散天数，按日另行扣分
        //    extraDays = max(0, 总逾期天数 − 完整期数天数)

        int overduePeriod = repayPlan.getCurrentPeriod() - lastPeriod - 1;
        long totalOverdueDays = TimeCalculator.calNumToTargetDay(startTime);

        long fullPeriodDays = overduePeriod * 30L;
        long extraDays = Math.max(0, totalOverdueDays - fullPeriodDays);

        int periodPenalty = (int) (CREDIT_DECREASE_BASE_SCORE * overduePeriod * (overduePeriod + 1) / 2f);
        int daysPenalty = (int) (extraDays * CREDIT_DECREASE_DAILY_RATE);
        int creditDecrease = periodPenalty + daysPenalty;

        // 持久化：更新用户信用分，不低于0
        UserInformation userInfo = userInformationRepository.getUserInformationById(repayRecord.getUserId());
        if (userInfo != null) {
            int newScore = Math.max(0, userInfo.getCreditScore() - creditDecrease);
            userInfo.setCreditScore(newScore);
            userInformationRepository.save(userInfo);
        }

        repayOverdueRecord.setCreditDecrease(creditDecrease);

        return repayOverdueRecord;
    }
}