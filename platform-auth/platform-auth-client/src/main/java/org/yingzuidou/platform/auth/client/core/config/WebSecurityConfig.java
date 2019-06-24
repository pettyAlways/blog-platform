package org.yingzuidou.platform.auth.client.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.yingzuidou.platform.auth.client.core.configurer.JwtVerifyConfigurer;
import org.yingzuidou.platform.auth.client.core.configurer.PlatformLoginConfigurer;
import org.yingzuidou.platform.auth.client.core.encoder.HashedCredentialsEncoder;
import org.yingzuidou.platform.auth.client.core.interceptor.JwtAuthenticationTokenFilter;
import org.yingzuidou.platform.auth.client.core.interceptor.PlatformFilterSecurityInterceptor;
import org.yingzuidou.platform.auth.client.core.service.PlatformAccessDecisionManager;
import org.yingzuidou.platform.auth.client.core.service.PlatformInvocationSecurityMetadataSourceService;
import org.yingzuidou.platform.auth.client.core.service.PlatformUserDetailsService;
import org.yingzuidou.platform.auth.client.core.matcher.SkipPathRequestMatcher;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.auth.client.core.provider.JwtAuthenticationProvider;

import java.util.Arrays;
import java.util.List;

/**
 * 类功能描述
 * 通过SpringSecurity来做认证授权管理
 *
 * @author 鹰嘴豆
 * @date 2019/6/14
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           // 认证授权部分配置
           .authorizeRequests()
                // 不需要登录认证的资源
                .antMatchers("/login").permitAll()
                // 其他请求都需要登录
                .anyRequest().authenticated()
                .and()
           // CSRF禁用
            .csrf().disable()
           // Session 禁用
            .sessionManagement().disable()
           // 禁用原生的form页面登录
            .formLogin().disable()
            .cors().and()
            // 增加权限校验拦截器
            .addFilterBefore(platformFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
            // 增加Jwt验证配置器
            .apply(new JwtVerifyConfigurer<>()).setJwtAuthenticationProvider(jwtAuthenticationProvider())
                .setJwtFilter(authenticationTokenFilterBean()).and()
            // 增加登录认证配置器
            .apply(new PlatformLoginConfigurer<>());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(platformUserDetailsService()).passwordEncoder(hashedCredentialsEncoder());
    }

    @Bean
    public HashedCredentialsEncoder hashedCredentialsEncoder() {
        return new HashedCredentialsEncoder();
    }

    @Bean
    public UserDetailsService platformUserDetailsService() {
        return new PlatformUserDetailsService();
    }

    @Bean
    protected AuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider();
        authenticationProvider.setPlatformUserDetailsService(platformUserDetailsService());
        return authenticationProvider;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource platformInvocationSecurityMetadataSourceService() {
        return new PlatformInvocationSecurityMetadataSourceService();
    }

    @Bean
    public PlatformAccessDecisionManager platformAccessDecisionManager() {
        return new PlatformAccessDecisionManager();
    }

    @Bean
    public PlatformFilterSecurityInterceptor platformFilterSecurityInterceptor() {
        PlatformFilterSecurityInterceptor platformFilterSecurityInterceptor = new PlatformFilterSecurityInterceptor();
        platformFilterSecurityInterceptor.setAccessDecisionManager(platformAccessDecisionManager());
        platformFilterSecurityInterceptor.setSecurityMetadataSource(platformInvocationSecurityMetadataSourceService());
        return platformFilterSecurityInterceptor;
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        // 不需要token 验证的url
        List<String> pathsToSkip = Arrays.asList("/login","/auth/v1/api/login/entry","/auth/v1/api/login/enter");
        //　需要验证token　的url
        String processingPath = "/platform/backend/**";
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, processingPath);
        return new JwtAuthenticationTokenFilter(matcher);
    }

    @Autowired(required = false)
    public void initSecurity(@Value("${jwt.issuer}") String issuer, @Value("${jwt.subject}") String subject,
                             @Value("${jwt.secret}") String secret, @Value("${jwt.token-header}") String tokenHeader,
                             @Value("${jwt.token-header-prefix}") String tokenHeaderPrefix,
                             @Value("${jwt.expire_token}") int expires,
                             @Value("${jwt.refresh_token}") long refreshToken) {
        JwtTokenUtil.create(issuer, subject, secret);
        PlatformContext.create(issuer, subject, secret, tokenHeader, tokenHeaderPrefix, expires, refreshToken);
    }

}