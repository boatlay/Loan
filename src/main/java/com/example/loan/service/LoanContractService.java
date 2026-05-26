package com.example.loan.service;

import com.deepoove.poi.XWPFTemplate;
import com.example.loan.dao.LoanApplyRepository;
import com.example.loan.dao.LoanContractRepository;
import com.example.loan.dao.LoanProductRepository;
import com.example.loan.dao.entity.LoanContract;
import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.LoanProduct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoanContractService {
    @Autowired
    private LoanContractRepository loanContractRepository;

    @Autowired
    private LoanApplyRepository loanApplyRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    // 模板文件路径
    private static final String CONTRACT_PATH = "contract.docx";

    /**
     * 根据只有userId和applyId的loanContract，完善loanContract的信息
     * @param loanContract
     * @return
     */
    public boolean autoComplete(LoanContract loanContract){
        if(loanContractRepository.getByApplyId(loanContract.getApplyId()) == null){
            loanContract.setContractContent("合同内容......");

            //根据生效时间startTime和申请期限applyPeriod，计算终止时间endTime
            LoanApply loanApply = loanApplyRepository.getReferenceById(loanContract.getApplyId());
            loanContract.setStartTime(LocalDateTime.now());
            LocalDateTime endTime = loanContract.getStartTime().plusMonths(loanApply.getApplyPeriod());
            loanContract.setEndTime(endTime);

            loanContract.setOverdueInterestMethod("罚息利率为产品利率的150%、按日罚息：除了缴纳逾期阶段内应还的本金和利息以外，还要缴纳罚息=剩余未还本金×罚息日利率×逾期天数");

            loanContractRepository.save(loanContract);
            return true;
        }else{
            return false;
        }
    }


    /**
     * 根据合同ID生成 Word 文件并写入响应流
     */
    public void downloadContract(Integer contractId, HttpServletResponse response) {
        try {
            // 1. 查询合同数据
            LoanContract contract = loanContractRepository.findById(contractId)
                    .orElseThrow(() -> new RuntimeException("合同不存在，ID: " + contractId));

            // 2. 查询关联的申请信息
            LoanApply loanApply = loanApplyRepository.findById(contract.getApplyId())
                    .orElseThrow(() -> new RuntimeException("申请信息不存在，ID: " + contract.getApplyId()));

            // 查询关联的贷款产品信息
            LoanProduct loanProduct = loanProductRepository.findById(loanApply.getProductId())
                    .orElseThrow(() -> new RuntimeException("商品信息不存在，ID: " + loanApply.getProductId()));

            // 3. 准备模板填充数据
            // 合同数据
            Map<String, Object> data = new HashMap<>();
            data.put("id", contract.getId());
            data.put("userId", contract.getUserId());
            data.put("startTime", formatDateTime(contract.getStartTime()));
            data.put("endTime", formatDateTime(contract.getEndTime()));
            data.put("contractContent", contract.getContractContent());
            data.put("overdueInterestMethod", contract.getOverdueInterestMethod());
            // 申请和产品数据
            data.put("applyQuota", loanApply.getApplyQuota());
            data.put("repayMethod", loanApply.getRepayMethod());
            data.put("rate", loanProduct.getRate());

            // 4. 生成 Word 字节数组
            byte[] wordBytes = generateWordFromTemplate(data);

            // 5. 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

            String fileName = "借款合同_" + contractId + "_" + System.currentTimeMillis() + ".docx";
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8")
                    .replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
            response.setContentLengthLong(wordBytes.length);

            // 6. 写入响应流
            try (OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(wordBytes);
                outputStream.flush();
            }

        } catch (Exception e) {
            try {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":500,\"message\":\"合同生成失败: " + e.getMessage() + "\"}");
            } catch (IOException ex) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用 poi-tl 模板生成 Word 文件
     */
    private byte[] generateWordFromTemplate(Map<String, Object> data) throws Exception {
        // 加载模板文件（你放在 resources 根目录的 contract.docx）
        InputStream templateStream = getClass().getClassLoader()
                .getResourceAsStream(CONTRACT_PATH);

        if (templateStream == null) {
            throw new RuntimeException("合同模板文件不存在，请检查 resources/contract.docx");
        }

        // 使用 poi-tl 渲染模板
        XWPFTemplate template = XWPFTemplate.compile(templateStream).render(data);

        // 输出为字节数组
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        template.write(out);
        template.close();

        return out.toByteArray();
    }

    /**
     * 格式化日期时间
     */
    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        return dateTime.format(formatter);
    }
}