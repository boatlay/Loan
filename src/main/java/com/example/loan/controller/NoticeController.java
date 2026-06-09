package com.example.loan.controller;

import com.example.loan.dao.entity.Email;
import com.example.loan.dao.entity.Notice;
import com.example.loan.service.NoticeService;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/adamin/notice")
    public ResponseResult<Void> addNotice(@RequestBody Notice notice){
        noticeService.addNotice(notice);
        return ResponseResult.success();
    }

    @GetMapping("/adamin/notice")
    public ResponseResult<PagedModel<Notice>> getPages(@PageableDefault(size = 10,sort = "id") Pageable pageable){
        Page<Notice> page;
        page=noticeService.getPage(pageable);

        PagedModel<Notice> pagedModel=new PagedModel<>(page);
        if(page==null){
            return ResponseResult.error("空空如也",400,null);
        }else {
            return ResponseResult.success(200,"success",pagedModel);
        }
    }

    @DeleteMapping("/adamin/notice")
    public ResponseResult<Void> deleteEmail(@RequestBody Notice notice){
        noticeService.deleteNotice(notice);
        return ResponseResult.success();
    }
}