package org.yingzuidou.platform.auth.client.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/29
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
public class AuthConfig {

    /**
     * JWT的Header头存储token的key
     */
    @Value("${jwt.token-header}")
    String tokenHeader;

    /**
     * JWT的Header头存储token的Value值的前缀
     */
    @Value("${jwt.token-header-prefix}")
    private String tokenHeaderPrefix;

    /**
     * 经过网关的请求头部携带的key
     */
    @Value("${auth.token-header}")
    private String zuulHeader;

    /**
     * 经过网关的请求头部携带的value
     */
    @Value("${auth.token-value}")
    private String zuulHeaderValue;
}
