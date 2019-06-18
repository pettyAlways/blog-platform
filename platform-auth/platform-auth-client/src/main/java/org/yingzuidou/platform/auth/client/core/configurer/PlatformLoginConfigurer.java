package org.yingzuidou.platform.auth.client.core.configurer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.yingzuidou.platform.auth.client.core.handler.PlatformAuthenticationFailureHandler;
import org.yingzuidou.platform.auth.client.core.handler.PlatformAuthenticationSuccessHandler;
import org.yingzuidou.platform.auth.client.core.interceptor.PlatformUsernamePasswordAuthenticationFilter;
import org.yingzuidou.platform.auth.client.core.service.PlatformUserDetailsService;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformLoginConfigurer<T extends PlatformLoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>>
        extends AbstractHttpConfigurer<T, B> {

    private PlatformUsernamePasswordAuthenticationFilter authFilter;

    public PlatformLoginConfigurer() {
        this.authFilter = new PlatformUsernamePasswordAuthenticationFilter();
    }

    @Override
    public void configure(B builder) throws Exception {
        authFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        // 设置登录认证失败的处理类
        authFilter.setAuthenticationFailureHandler(new PlatformAuthenticationFailureHandler());
        // 设置登录认证成功的处理类
        authFilter.setAuthenticationSuccessHandler(new PlatformAuthenticationSuccessHandler());
        // 不将认证后的context放入session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        authFilter = postProcess(authFilter);

        builder.addFilter(authFilter);
    }
}
