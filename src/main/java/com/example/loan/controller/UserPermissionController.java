package com.example.loan.controller;

import com.example.loan.dao.entity.LocationDTO;
import com.example.loan.dao.entity.UserPermission;
import com.example.loan.service.UserPermissionService;
import com.example.loan.utils.ContactDTO;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/permission")
public class UserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;

    @PostMapping("/create_permission")
    public ResponseResult<Void> createUserPermission(@RequestBody Map map){
        String username=(String) map.get("username");
        return userPermissionService.createUserPermission(username);
    }

    @GetMapping("/get_permission")
    public ResponseResult<UserPermission> getUserPermission(@RequestParam String username){
        return userPermissionService.getUserPermission(username);
    }

    @PutMapping("/update_permission")
    public ResponseResult<Void> updateUserPermission(@RequestBody UserPermission userPermission){
        return userPermissionService.updateUserPermission(userPermission);
    }

    @PostMapping("/contacts/upload")
    public ResponseResult<Void> uploadContacts(@RequestBody ContactDTO contactDTO,@RequestHeader String token){
        String name= JwtUtils.getNameFromJwt(token);
        return userPermissionService.uploadContacts(name,contactDTO);
    }

    @PostMapping("/location/upload")
    public ResponseResult<Void> uploadLocation(@RequestBody LocationDTO locationDTO,@RequestHeader String token){
        String name=JwtUtils.getNameFromJwt(token);
        return userPermissionService.checkAndSaveLocation(locationDTO,name);
    }
}
