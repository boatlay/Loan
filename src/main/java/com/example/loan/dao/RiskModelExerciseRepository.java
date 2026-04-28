package com.example.loan.dao;

import com.example.loan.dao.entity.RiskModelExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskModelExerciseRepository extends JpaRepository<RiskModelExercise,Integer> {

    RiskModelExercise findRiskModelExerciseById(Integer id);

    Boolean existsRiskModelExerciseById(Integer is);
}
