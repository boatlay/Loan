package com.example.loan.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_private")
@AllArgsConstructor
@NoArgsConstructor
public class UserPrivate {

    @Id
    private Integer id;

    private String username;
    private String contactPrivate;
    private String locationPrivate;
    private String bugPrivate;
}
