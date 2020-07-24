package com.itomelet.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {

    private Integer code; //状态码

    private String msg; //异常信息

}

