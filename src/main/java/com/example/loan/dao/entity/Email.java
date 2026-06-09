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
@Table(name = "email")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GenericGenerator(
            name = "intSnowflakeGenerator",
            strategy = "com.example.loan.utils.IntSnowflakeHibernateGenerator"
    )
    @GeneratedValue(generator = "intSnowflakeGenerator")
    private Integer id;

    private String name;
    private String subject;
    private String summary;
    private String content;

    @CreatedDate
    private LocalDateTime date;

    @Column(name = "avatar_color")
    private String avatarColor;
}