package com.example.loan.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "loan_apply")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class LoanApply {
    @Id
    @GenericGenerator(
            name = "intSnowflakeGenerator",
            strategy = "com.example.loan.utils.IntSnowflakeHibernateGenerator"
    )
    @GeneratedValue(generator = "intSnowflakeGenerator")
    private Integer id;

    private String name;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "apply_quota")
    private float applyQuota;

    @Column(name = "apply_period")
    private Integer applyPeriod;

    @Column(name = "apply_time", updatable = false)
    @CreatedDate
    private LocalDateTime applyTime;

    @Column(name = "apply_status")
    private String applyStatus;

    @Column(name = "refuse_reason")
    private String refuseReason;

    @Column(name = "repay_method")
    private String repayMethod;
}
