package com.example.loan.dao;

import com.example.loan.dao.entity.UserQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQualificationRepository extends JpaRepository<UserQualification,Integer> {

    UserQualification findUserQualificationById(Integer id);
}
