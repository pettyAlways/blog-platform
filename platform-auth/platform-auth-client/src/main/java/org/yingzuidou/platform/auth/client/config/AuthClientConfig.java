package org.yingzuidou.platform.auth.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yingzuidou.platform.auth.client.vo.AuthConfig;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Configuration
public class AuthClientConfig {

    @Bean
    public AuthConfig authConfig() {
        return new AuthConfig();
    }
}
