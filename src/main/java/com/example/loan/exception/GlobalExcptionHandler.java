package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@ResponseBody
public class GlobalExcptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseResult<Void> globalException(){
//        return ResponseResult.error("服务器错误，请稍后重试",500,null);
//    }

    @ExceptionHandler(UserAccountExistsException.class)
    public ResponseResult<Void> userAccountExists(UserAccountExistsException e){
        return e.error(null);
    }

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseResult<Void> userAccountNotFound(UserAccountNotFoundException e){
        return e.error(null);
    }

    @ExceptionHandler(ProductExistsException.class)
    public ResponseResult<Void> productExists(ProductExistsException e){
        return e.error(null);
    }

    @ExceptionHandler(ApplyIllegalException.class)
    public ResponseResult<Void> applyIllegal(ApplyIllegalException e){
        return e.error(null);
    }

    @ExceptionHandler(ContractIllegalException.class)
    public ResponseResult<Void> contractIllegal(ContractIllegalException e){
        return e.error(null);
    }

    @ExceptionHandler(PlanIllegalException.class)
    public ResponseResult<Void> planIllegal(PlanIllegalException e){
        return e.error(null);
    }

    @ExceptionHandler(LoanApplyNotFoundException.class)
    public ResponseResult<Void> loanApplyNotFoundException(LoanApplyNotFoundException e) { return e.error(null); }

    @ExceptionHandler(RepayPlanNotFoundException.class)
    public ResponseResult<Void> repayPlanNotFoundException(RepayPlanNotFoundException e) { return e.error(null); }

    @ExceptionHandler(RepayRecordNotFoundException.class)
    public ResponseResult<Void> repayRecordNotFoundException(RepayRecordNotFoundException e) { return e.error(null); }

    @ExceptionHandler(RepayOverdueRecordNotFoundException.class)
    public ResponseResult<Void> repayOverdueRecordNotFoundException(RepayOverdueRecordNotFoundException e) { return e.error(null); }

    @ExceptionHandler(RepayException.class)
    public ResponseResult<Void> repayException(RepayException e) { return e.error(null); }
}
