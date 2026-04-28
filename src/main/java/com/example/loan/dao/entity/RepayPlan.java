package com.example.loan.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "repay_plan")
@NoArgsConstructor
@AllArgsConstructor
public class RepayPlan {
    @Id
    @GenericGenerator(
            name = "intSnowflakeGenerator",
            strategy = "com.example.loan.utils.IntSnowflakeHibernateGenerator"
    )
    @GeneratedValue(generator = "intSnowflakeGenerator")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "current_period")
    private Integer currentPeriod;

    //本期应还本金
    @Column(name = "repay_principal")
    private float repayPrincipal;

    //本期应还利息
    @Column(name = "repay_interest")
    private float repayInterest;

    //本期应还本金加利息
    @Column(name = "pri_int_all")
    private float priIntAll;

    //上一次已支付计划的id
    @Column(name = "overed_plan_id")
    private Integer overedPlanId;

    //目前剩余未还本金（包括当期应还本金）
    @Column(name = "rest_principal")
    private float restPrincipal;

    //本期还款开始时间
    @Column(name = "start_time")
    private LocalDateTime startTime;

    //本期还款结束时间
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private Boolean status;
}
