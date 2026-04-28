package com.example.loan.service;

import com.example.loan.dao.*;
import com.example.loan.dao.entity.RiskModelExercise;
import com.example.loan.dao.entity.UserInformation;
import com.example.loan.dao.entity.UserPrivate;
import com.example.loan.dao.entity.UserQualification;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RiskModelExerciseService {

    @Autowired
    private RiskModelExerciseRepository riskModelExerciseRepository;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserQualificationRepository userQualificationRepository;

    @Autowired
    private UserPrivateRepository userPrivateRepository;

    public ResponseResult<Void> calculateScore(String name){
        RestTemplate restTemplate=new RestTemplate();
        String url="http://127.0.0.1:5000/model/calculate_score";
        int id=userAccountRepository.getUserAccountByName(name).getId();
        RiskModelExercise riskModelExercise=null;
        if(riskModelExerciseRepository.existsRiskModelExerciseById(id)){
            riskModelExercise=riskModelExerciseRepository.findRiskModelExerciseById(id);
        }else {
            riskModelExercise=new RiskModelExercise();
            riskModelExercise.setId(id);
        }

        UserInformation userInformation=userInformationRepository.getUserInformationById(id);
        riskModelExercise.setNationality(userInformation.getNationality());
        riskModelExercise.setBank_card_status(userInformation.getBankCardStatus());
        riskModelExercise.setAge(25);

        UserQualification userQualification=userQualificationRepository.findUserQualificationById(id);
        riskModelExercise.setMarriage_status(userQualification.getMarriageStatus());
        riskModelExercise.setEducation_background(userQualification.getEducationBackground());
        riskModelExercise.setProfession(userQualification.getProfession());
        riskModelExercise.setIncome(userQualification.getIncome());
        riskModelExercise.setSocial_insurance_status(userQualification.getSocialInsuranceStatus());
        riskModelExercise.setCredit_status(userQualification.getCreditStatus());

        if(userPrivateRepository.existsUserPrivateById(id)){
            UserPrivate userPrivate=userPrivateRepository.findUserPrivateById(id);
            riskModelExercise.setLocation_private(userPrivate.getLocationPrivate());
            riskModelExercise.setContact_private(userPrivate.getContactPrivate());
            riskModelExercise.setBug_private(userPrivate.getBugPrivate());
        }

        riskModelExerciseRepository.save(riskModelExercise);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RiskModelExercise> request=new HttpEntity<>(riskModelExercise,headers);

        ResponseEntity<Map> response=restTemplate.postForEntity(url,request,Map.class);

        if(response.getStatusCode().is2xxSuccessful()&&response.getBody()!=null){
            int score=(Integer)response.getBody().get("score");
            userInformation.setCreditScore(score);
            userInformationRepository.save(userInformation);
            return ResponseResult.success();
        }
        return ResponseResult.error();
    }
}
