package org.yingzuidou.platform.eureka.service.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 类功能描述
 * <p>使用spring-security给Eureka做认证</p>
 * <ul>
 *     <li>
 *         涉及到跨域而eureka客户端都不会做跨域请求的处理所以指定eureka客户端
 *         请求需要忽略跨域的拦截
 *     </li>
 * </ul>
 *
 * @author 鹰嘴豆
 * @date 2019/5/31
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
