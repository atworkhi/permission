package com.hanxx.permission.exception;

/**
 * @Author hanxx
 * @Date 2018/4/19-17:53
 * 校验异常处理类
 */
public class ParamValidateException extends RuntimeException{
    public ParamValidateException() {
        super();
    }

    public ParamValidateException(String message) {
        super(message);
    }

    public ParamValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamValidateException(Throwable cause) {
        super(cause);
    }

    protected ParamValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
