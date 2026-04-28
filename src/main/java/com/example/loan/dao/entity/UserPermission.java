package com.example.loan.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermission {

    @Id
    private Integer id;

    private String username;
    private Integer contactPermission;
    private Integer locationPermission;
    private Integer cameraPermission;
    private String weiboUrl;
}
