package org.yingzuidou.platform.auth.client.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 类功能描述
 * 配置中心对Request头的配置信息自动装配到{@link AuthConfig}对象中
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
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
     * zuul网关访问服务增加的头Key值
     */
    @Value("${auth.token-header}")
    private String serviceHeader;
}
