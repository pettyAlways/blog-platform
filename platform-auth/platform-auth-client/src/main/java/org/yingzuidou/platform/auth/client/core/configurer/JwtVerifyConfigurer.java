package org.yingzuidou.platform.auth.client.core.configurer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.yingzuidou.platform.auth.client.core.handler.JwtRefreshSuccessHandler;
import org.yingzuidou.platform.auth.client.core.handler.PlatformAuthenticationFailureHandler;
import org.yingzuidou.platform.auth.client.core.interceptor.JwtAuthenticationTokenFilter;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.auth.client.provider.JwtAuthenticationProvider;

import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/19
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class JwtVerifyConfigurer <T extends JwtVerifyConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private JwtAuthenticationTokenFilter jwtFilter;

    private AuthenticationProvider jwtAuthenticationProvider;

    private RequestMatcher requestMatcher;

    private String tokenHeader;

    private String tokenHeaderPrefix;

    public JwtVerifyConfigurer() {
        this.tokenHeader = PlatformContext.getTokenHeader();
        this.tokenHeaderPrefix = PlatformContext.getTokenHeaderPrefix();
    }

    @Override
    public void init(B builder) {
        if (Objects.isNull(jwtAuthenticationProvider)) {
            jwtAuthenticationProvider = new JwtAuthenticationProvider();
        }
        if (Objects.isNull(jwtFilter)) {
            jwtFilter = new JwtAuthenticationTokenFilter(requestMatcher);
        }
        jwtAuthenticationProvider = postProcess(jwtAuthenticationProvider);
        builder.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(B http) {
        jwtFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        jwtFilter.setAuthenticationFailureHandler(new PlatformAuthenticationFailureHandler());
        jwtFilter.setAuthenticationSuccessHandler(new JwtRefreshSuccessHandler(tokenHeader, tokenHeaderPrefix));
        // 将filter放到logoutFilter之前
        JwtAuthenticationTokenFilter filter = postProcess(jwtFilter);
        http.addFilterBefore(filter, LogoutFilter.class);
    }

    public JwtVerifyConfigurer<T, B> setJwtFilter(JwtAuthenticationTokenFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
        return this;
    }

    public JwtVerifyConfigurer<T, B> setJwtAuthenticationProvider(AuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        return this;
    }
}
