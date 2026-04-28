package com.example.loan.dao;

import com.example.loan.dao.entity.UserPrivate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivateRepository extends JpaRepository<UserPrivate,Integer> {

    Boolean existsUserPrivateById(Integer id);

    UserPrivate findUserPrivateById(Integer id);
}
