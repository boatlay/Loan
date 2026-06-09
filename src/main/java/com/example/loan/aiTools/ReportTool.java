package com.example.loan.aiTools;

import com.example.loan.dao.RiskModelExerciseRepository;
import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.entity.RiskModelExercise;
import com.example.loan.dao.entity.UserAccount;
import com.example.loan.service.RedisService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ReportTool {

    @Autowired
    private RiskModelExerciseRepository riskModelExerciseRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Tool("获得用户用户评信用分的数据")
    public Map<String,Object> getUserData(
            @P("用户昵称") String username
    ){
        UserAccount userAccount=userAccountRepository.getUserAccountByName(username);
        RiskModelExercise exercise=riskModelExerciseRepository.findRiskModelExerciseById(userAccount.getId());
        Map<String,Object> map=new HashMap();
        map.put("用户年龄",exercise.getAge());
        map.put("用户银行卡状态",exercise.getBank_card_status());
        map.put("用户婚姻状况",exercise.getMarriage_status());
        map.put("用户学历",exercise.getEducation_background());
        map.put("用户职业",exercise.getProfession());
        map.put("用户收入",exercise.getIncome());
        map.put("用户社保状态",exercise.getSocial_insurance_status());
        map.put("用户信用状态",exercise.getCredit_status());
        map.put("用户通讯录是否含有其他贷款平台的联系方式",exercise.getContact_private());
        map.put("用户地理位置是否处于高危诈骗地区",exercise.getLocation_private());
        map.put("用户是否为失信人",exercise.getBug_private());
        return map;
    }
}