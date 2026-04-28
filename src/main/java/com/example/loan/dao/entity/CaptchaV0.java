package com.example.loan.dao.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaptchaV0 {
    private String captchaId;
    private String captchaImage;
}