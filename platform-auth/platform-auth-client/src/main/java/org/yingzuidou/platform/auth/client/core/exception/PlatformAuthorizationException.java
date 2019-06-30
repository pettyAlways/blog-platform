package org.yingzuidou.platform.auth.client.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 类功能描述
 * 资源认证异常类，不会被SpringSecurity记录到日志里面，通常使一些业务类的提示
 *
 * @author 鹰嘴豆
 * @date 2019/6/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformAuthorizationException extends RuntimeException  {

    public PlatformAuthorizationException(String msg, Throwable t) {
        super(msg, t);
    }

    public PlatformAuthorizationException(String msg) {
        super(msg);
    }
}
