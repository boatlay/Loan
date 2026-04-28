package com.example.loan.service;

import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.UserPermissionRepository;
import com.example.loan.dao.UserPrivateRepository;
import com.example.loan.dao.entity.*;
import com.example.loan.service.aiService.CrawlerService;
import com.example.loan.utils.ContactDTO;
import com.example.loan.utils.ContactItem;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserPrivateRepository userPrivateRepository;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private CrawlerService crawlerService;

    public ResponseResult<Void> createUserPermission(String username){
        if(userPermissionRepository.existsUserPermissionByUsername(username)){
            return ResponseResult.success();
        }

        UserAccount userAccount=userAccountRepository.getUserAccountByName(username);
        UserPermission userPermission=new UserPermission();
        userPermission.setId(userAccount.getId());
        userPermission.setUsername(username);
        userPermission.setCameraPermission(0);
        userPermission.setContactPermission(0);
        userPermission.setLocationPermission(0);
        userPermissionRepository.save(userPermission);
        return ResponseResult.success();
    }

    public ResponseResult<UserPermission> getUserPermission(String username){
        UserPermission userPermission=userPermissionRepository.findUserPermissionByUsername(username);
        return ResponseResult.success(200,"success",userPermission);
    }

    public ResponseResult<Void> updateUserPermission(UserPermission userPermission){
        userPermissionRepository.save(userPermission);
        return ResponseResult.success();
    }

    public ResponseResult<Void> uploadContacts(String name, ContactDTO contactDTO){
        UserAccount account=userAccountRepository.getUserAccountByName(name);
        if(!userPrivateRepository.existsUserPrivateById(account.getId())) {
            UserPrivate userPrivate = new UserPrivate();
            userPrivate.setUsername(name);
            userPrivate.setId(account.getId());
            String[] riskKeywords = {"银行", "贷款", "催收", "套现", "黑户"};
            userPrivate.setContactPrivate("safe");
            for (ContactItem contactItem : contactDTO.getContacts()) {
                for (String keyword : riskKeywords) {
                    if (contactItem.getName().contains(keyword)) {
                        userPrivate.setContactPrivate("risk");
                    }
                }
            }
            userPrivateRepository.save(userPrivate);
        }else {
            UserPrivate userPrivate=userPrivateRepository.findUserPrivateById(account.getId());
            String[] riskKeywords = {"银行", "贷款", "催收", "套现", "黑户"};
            userPrivate.setContactPrivate("safe");
            for (ContactItem contactItem : contactDTO.getContacts()) {
                for (String keyword : riskKeywords) {
                    if (contactItem.getName().contains(keyword)) {
                        userPrivate.setContactPrivate("risk");
                    }
                }
            }
            userPrivateRepository.save(userPrivate);
        }
            return ResponseResult.success();
    }

    public ResponseResult<Void> checkAndSaveLocation(LocationDTO locationDTO,String name){
        if(locationDTO.getLongitude()==null||locationDTO.getLatitude()==null){
            return ResponseResult.error("error",400,null);
        }
        UserAccount account=userAccountRepository.getUserAccountByName(name);
        if(!userPrivateRepository.existsUserPrivateById(account.getId())) {
            UserPrivate userPrivate = new UserPrivate();
            userPrivate.setUsername(name);
            userPrivate.setId(account.getId());
            userPrivate.setLocationPrivate("safe");
            Double lon = locationDTO.getLongitude();
            Double lat = locationDTO.getLatitude();
            boolean inMyanmar = (lon >= 97.0 && lon <= 101.5) && (lat >= 20.0 && lat <= 28.5);
            boolean inCambodia = (lon >= 102.0 && lon <= 105.0) && (lat >= 10.0 && lat <= 14.5);
            boolean inPhilippines = (lon >= 120.0 && lon <= 122.0) && (lat >= 13.5 && lat <= 15.5);
            if (inMyanmar || inCambodia || inPhilippines) {
                userPrivate.setLocationPrivate("risk");
            }
            userPrivateRepository.save(userPrivate);
        }else {
            UserPrivate userPrivate=userPrivateRepository.findUserPrivateById(account.getId());
            userPrivate.setLocationPrivate("safe");
            Double lon = locationDTO.getLongitude();
            Double lat = locationDTO.getLatitude();
            boolean inMyanmar = (lon >= 97.0 && lon <= 101.5) && (lat >= 20.0 && lat <= 28.5);
            boolean inCambodia = (lon >= 102.0 && lon <= 105.0) && (lat >= 10.0 && lat <= 14.5);
            boolean inPhilippines = (lon >= 120.0 && lon <= 122.0) && (lat >= 13.5 && lat <= 15.5);
            if (inMyanmar || inCambodia || inPhilippines) {
                userPrivate.setLocationPrivate("risk");
            }
            userPrivateRepository.save(userPrivate);
        }
        return ResponseResult.success(200,"success",null);
    }

    @Async
    public void crawl(String name,String message) {
        UserAccount userAccount = userAccountRepository.getUserAccountByName(name);
        if (!userInformationRepository.existsUserInformationById(userAccount.getId())) {
            return;
        } else {
            UserInformation userInformation = userInformationRepository.getUserInformationById(userAccount.getId());
            message = message + "姓名:" + userInformation.getName() + ",身份证号:" + userInformation.getIdCardNumber();
            String result = crawlerService.chat(message);
            System.out.println(result);
            if (!userPrivateRepository.existsUserPrivateById(userAccount.getId())) {
                UserPrivate userPrivate = new UserPrivate();
                userPrivate.setId(userAccount.getId());
                userPrivate.setUsername(name);
                userPrivate.setBugPrivate(result);
                userPrivateRepository.save(userPrivate);
            } else {
                UserPrivate userPrivate = userPrivateRepository.findUserPrivateById(userAccount.getId());
                userPrivate.setBugPrivate(result);
                userPrivateRepository.save(userPrivate);
            }
            System.out.println("爬虫任务完成，结果已存入数据库！结果为："+result);
        }
    }
}
