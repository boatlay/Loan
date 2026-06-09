package com.example.loan.controller;

import com.example.loan.dao.entity.Email;
import com.example.loan.service.EmailService;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/adamin/email")
    public ResponseResult<Void> addEmail(@RequestBody Email email){
        emailService.addEmail(email);
        return ResponseResult.success();
    }

    @GetMapping("/adamin/email")
    public ResponseResult<PagedModel<Email>> getPages(@PageableDefault(size = 10,sort = "id") Pageable pageable){
        Page<Email> page;
        page=emailService.getPage(pageable);

        PagedModel<Email> pagedModel=new PagedModel<>(page);
        if(page==null){
            return ResponseResult.error("空空如也",400,null);
        }else {
            return ResponseResult.success(200,"success",pagedModel);
        }
    }

    @DeleteMapping("/adamin/email")
    public ResponseResult<Void> deleteEmail(@RequestBody Email email){
        emailService.deleteEmail(email);
        return ResponseResult.success();
    }
}