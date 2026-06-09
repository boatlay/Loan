package com.example.loan.service;

import com.example.loan.dao.NoticeResposity;
import com.example.loan.dao.entity.Email;
import com.example.loan.dao.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    @Autowired
    private NoticeResposity noticeResposity;

    public void addNotice(Notice notice){
        noticeResposity.save(notice);
    }

    public Page<Notice> getPage(Pageable pageable){
        return noticeResposity.findAll(pageable);
    }

    public void deleteNotice(Notice notice){
        noticeResposity.deleteById(notice.getId());
    }
}