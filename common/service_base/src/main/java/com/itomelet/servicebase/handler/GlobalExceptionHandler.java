package com.itomelet.servicebase.handler;

import com.itomelet.commonutils.ExceptionUtil;
import com.itomelet.commonutils.Result;
import com.itomelet.servicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMsg());
    }


}
