package org.yingzuidou.platform.auth.client.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.yingzuidou.platform.auth.client.core.base.ConfigData;
import org.yingzuidou.platform.auth.client.core.configurer.JwtVerifyConfigurer;
import org.yingzuidou.platform.auth.client.core.configurer.PlatformLoginConfigurer;
import org.yingzuidou.platform.auth.client.core.encoder.HashedCredentialsEncoder;
import org.yingzuidou.platform.auth.client.core.interceptor.JwtAuthenticationTokenFilter;
import org.yingzuidou.platform.auth.client.core.interceptor.PlatformFilterSecurityInterceptor;
import org.yingzuidou.platform.auth.client.core.interceptor.PlatformZuulHeaderFilter;
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
@ComponentScan("org.yingzuidou.platform.auth.client.feign")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           // 认证授权部分配置
           .authorizeRequests()
                // 不需要登录认证的资源
                .antMatchers("/platform/login", "/error").permitAll()
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
            .addFilterBefore(platformZuulHeaderFilter(), HeaderWriterFilter.class)
            // 增加Jwt验证配置器
            .apply(new JwtVerifyConfigurer<>()).setJwtAuthenticationProvider(jwtAuthenticationProvider())
                .setJwtFilter(authenticationTokenFilter()).and()
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

    private PlatformFilterSecurityInterceptor platformFilterSecurityInterceptor() {
        PlatformFilterSecurityInterceptor platformFilterSecurityInterceptor = new PlatformFilterSecurityInterceptor();
        platformFilterSecurityInterceptor.setAccessDecisionManager(platformAccessDecisionManager());
        platformFilterSecurityInterceptor.setSecurityMetadataSource(platformInvocationSecurityMetadataSourceService());
        return platformFilterSecurityInterceptor;
    }

    private JwtAuthenticationTokenFilter authenticationTokenFilter() {
        // 不需要token 验证的url
        List<String> pathsToSkip = Arrays.asList("/login","/auth/v1/api/login/entry","/auth/v1/api/login/enter");
        //　需要验证token　的url
        String processingPath = "/platform/blog/**";
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, processingPath);
        return new JwtAuthenticationTokenFilter(matcher);
    }

    private PlatformZuulHeaderFilter platformZuulHeaderFilter() {
        return new PlatformZuulHeaderFilter();
    }

    @Bean
    public ConfigData configData() {
        return new ConfigData();
    }

    @Autowired(required = false)
    public void initSecurity(ConfigData configData) {
        JwtTokenUtil.create(configData.getIssuer(), configData.getSubject(), configData.getSecret());
        PlatformContext.create(configData.getIssuer(), configData.getSubject(), configData.getSecret(),
                configData.getTokenHeader(), configData.getTokenHeaderPrefix(), configData.getExpires(),
                configData.getRefreshToken(), configData.getZuulHeader(), configData.getZuulHeaderValue());
    }



}
