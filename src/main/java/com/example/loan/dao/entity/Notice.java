package com.example.loan.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notice")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    @Id
    @GenericGenerator(
            name = "intSnowflakeGenerator",
            strategy = "com.example.loan.utils.IntSnowflakeHibernateGenerator"
    )
    @GeneratedValue(generator = "intSnowflakeGenerator")
    private Integer id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime time;
}