package org.yingzuidou.platform.auth.client.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.yingzuidou.platform.auth.client.core.interceptor.JwtAuthenticationTokenFilter;
import org.yingzuidou.platform.auth.client.core.interceptor.PlatformFilterSecurityInterceptor;
import org.yingzuidou.platform.auth.client.core.service.PlatformInvocationSecurityMetadataSourceService;
import org.yingzuidou.platform.auth.client.core.service.PlatformAccessDecisionManager;
import org.yingzuidou.platform.auth.client.core.service.PlatformUserDetailsService;
import org.yingzuidou.platform.auth.client.core.matcher.SkipPathRequestMatcher;

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
            .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(platformUserDetailsService()).passwordEncoder(bCryptPasswordEncoder()).and()
                .authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService platformUserDetailsService() {
        return new PlatformUserDetailsService();
    }

    @Bean
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource platformInvocationSecurityMetadataSourceService() {
        return new PlatformInvocationSecurityMetadataSourceService();
    }

    @Bean
    public AccessDecisionManager platformAccessDecisionManager() {
        return new PlatformAccessDecisionManager();
    }

    @Bean
    @DependsOn("platformAccessDecisionManager")
    public PlatformFilterSecurityInterceptor platformFilterSecurityInterceptor() {
        return new PlatformFilterSecurityInterceptor();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        // 不需要token 验证的url
        List<String> pathsToSkip = Arrays.asList("/auth/v1/api/login/retrieve/pwd","/auth/v1/api/login/entry","/auth/v1/api/login/enter");
        //　需要验证token　的url
        String processingPath = "/auth/v1/api/**";
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, processingPath);
        return new JwtAuthenticationTokenFilter(matcher);
    }

}
