package org.yingzuidou.cms.cmsweb.core.exception;

/**
 * 业务异常
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
public class BusinessException extends RuntimeException{
    private String code = "10086";

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
}
