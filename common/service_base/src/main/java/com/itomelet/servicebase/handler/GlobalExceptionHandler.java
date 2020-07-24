package com.itomelet.servicebase.handler;

import com.atguigu.commonutils.Result;
import com.itomelet.servicebase.exception.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return Result.error().code(e.getCode()).message(e.getMsg());
    }


}
