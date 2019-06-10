package org.yingzuidou.platform.auth.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 类功能描述
 * <p>开发者平台的权限校验，使用spring security来对访问开发者平台的请求进行鉴权</p>
 *
 * @author 鹰嘴豆
 * @date 2019/6/8
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Configuration
@EnableWebSecurity
public class AuthDeveloperPlatformConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                // 允许访问的路径
                .antMatchers("/platform/cms/login").permitAll()
                // 剩下的是需要登陆才能访问的路径
                .antMatchers("/platform/cms/**").authenticated()
                .anyRequest().permitAll()
                .and().formLogin()
                .and().headers().cacheControl();
        // httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // httpSecurity.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
}
