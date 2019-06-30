package org.yingzuidou.platform.auth.client.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 类功能描述
 * 登录认证的异常处理类
 *
 * @author 鹰嘴豆
 * @date 2019/6/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformAuthenticationException extends AuthenticationException  {

    public PlatformAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public PlatformAuthenticationException(String msg) {
        super(msg);
    }
}
