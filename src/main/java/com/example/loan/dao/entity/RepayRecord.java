package com.example.loan.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "repay_record")
@NoArgsConstructor
@AllArgsConstructor
public class RepayRecord {
    @Id
    @GenericGenerator(
            name = "intSnowflakeGenerator",
            strategy = "com.example.loan.utils.IntSnowflakeHibernateGenerator"
    )
    @GeneratedValue(generator = "intSnowflakeGenerator")
    private Integer id;

    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "current_period")
    private Integer currentPeriod;

    //支付时间
    @CreationTimestamp
    @Column(name = "repay_time")
    private LocalDateTime repayTime;

    //支付渠道
    @Column(name = "repay_method")
    private String repayMethod;

    //应还本金
    @Column(name = "repay_principal")
    private float repayPrincipal;

    //应还利息
    @Column(name = "repay_interest")
    private float repayInterest;

    //逾期应还
    @Column(name = "overdue_money")
    private float overdueMoney;

    //逾期罚息
    @Column(name = "overdue_punish")
    private float overduePunish;

    //总支付额
    @Column(name = "repay_all")
    private float repayAll;
}
