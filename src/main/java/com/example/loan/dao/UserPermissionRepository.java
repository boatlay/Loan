package com.example.loan.dao;

import com.example.loan.dao.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission,Integer> {
    boolean existsUserPermissionByUsername(String username);

    UserPermission findUserPermissionByUsername(String username);
}
