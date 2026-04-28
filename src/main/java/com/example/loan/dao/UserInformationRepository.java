package com.example.loan.dao;

import com.example.loan.dao.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation,Integer> {

    UserInformation getUserInformationById(Integer id);

    Boolean existsUserInformationById(Integer id);

    @Query(value = "select u.name from user_information u where u.id = ?1", nativeQuery = true)
    String getNameById(Integer id);

    Boolean existsUserInformationByEmail(String email);

    UserInformation findUserInformationByEmail(String email);
}
