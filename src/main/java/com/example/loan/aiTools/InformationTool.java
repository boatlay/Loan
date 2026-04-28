package com.example.loan.aiTools;

import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.entity.UserInformation;
import com.example.loan.service.UserInformationService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InformationTool {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Tool("帮用户完善/修改个人信息")
    public void addInformation(
           @P("用户昵称") String username,
           @P("用户真实姓名") String name,
           @P("用户的民族") String nationality,
           @P("用户身份证号") String idCardNumber,
           @P("用户电话号码") String phoneNumber,
           @P("用户邮箱地址") String email,
           @P("用户住址") String address,
           @P("用户银行卡号") String bankCardId,
           @P("用户开户行名称") String bankName,
           @P("用户银行卡状态，只能从active和frozen里面选") String bankCardStatus
    ){
        UserAccount userAccount=userAccountRepository.getUserAccountByName(username);
        UserInformation userInformation=new UserInformation();
        userInformation.setId(userAccount.getId());
        userInformation.setName(name);
        userInformation.setNationality(nationality);
        userInformation.setIdCardNumber(idCardNumber);
        userInformation.setPhoneNumber(phoneNumber);
        userInformation.setEmail(email);
        userInformation.setAdress(address);
        userInformation.setBankCardId(bankCardId);
        userInformation.setBankName(bankName);
        userInformation.setBankCardStatus(bankCardStatus);
        userInformation.setCreditScore(100);
        userInformationRepository.save(userInformation);
    }
}
