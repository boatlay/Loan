package com.example.loan.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "risk_model_exercise")
@AllArgsConstructor
@NoArgsConstructor
public class RiskModelExercise {

    @Id
    private Integer id;

    private Integer age;
    private String nationality;

    @Column(name = "bank_card_status")
    private String bank_card_status;

    @Column(name = "marriage_status")
    private Boolean marriage_status;

    @Column(name = "education_background")
    private String education_background;

    private String profession;
    private Integer income;

    @Column(name = "social_insurance_status")
    private Boolean social_insurance_status;

    @Column(name = "credit_status")
    private Boolean credit_status;

    @Column(name = "contact_private")
    private String contact_private;

    @Column(name = "location_private")
    private String location_private;

    @Column(name = "bug_private")
    private String bug_private;
}
