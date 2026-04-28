package com.example.loan.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "business_settlement")
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSettlement {
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

    @Column(name = "status")
    private Boolean status;

    @Column(name = "rest_loan")
    private float restLoan;
}