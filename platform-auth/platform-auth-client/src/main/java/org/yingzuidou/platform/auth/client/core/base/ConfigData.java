package org.yingzuidou.platform.auth.client.core.base;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 类功能描述
 * 配置中心的配置属性加载到这个POJO对象中
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
public class ConfigData {

    /**
     * JWT发布人
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * JWT主题
     */
    @Value("${jwt.subject}")
    private String subject;

    /**
     * JWT鉴权密钥
     */
    @Value("${jwt.secret}")
    String secret;

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
     * JWT的token过期的时间
     */
    @Value("${jwt.expire_token}")
    private int expires;

    /**
     * JWT的token需要重新生成的时间
     */
    @Value("${jwt.refresh_token}")
    long refreshToken;

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
