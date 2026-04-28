package com.example.loan.dao.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "repay_overdue_record")
@NoArgsConstructor
@AllArgsConstructor
public class RepayOverdueRecord {
    @Id
    @GenericGenerator(
            name = "intSnowflakeGenerator",
            strategy = "com.example.loan.utils.IntSnowflakeHibernateGenerator"
    )
    @GeneratedValue(generator = "intSnowflakeGenerator")
    private Integer id;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "user_id")
    private Integer userId;

    //逾期开始时间
    @Column(name = "start_time")
    private LocalDateTime startTime;

    //逾期结束时间
    @Column(name = "end_time")
    private LocalDateTime endTime;

    //总逾期期数
    @Column(name = "overdue_period")
    private Integer overduePeriod;

    //逾期总额（包括逾期应还和逾期罚息）
    @Column(name = "overdue_all")
    private float overdueAll;

    //本次扣减信用分
    @Column(name = "credit_decrease")
    private float creditDecrease;
}
