package org.yingzuidou.platform.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yingzuidou.platform.auth.client.core.base.ConfigData;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.auth.client.interceptor.ServiceAuthInterceptor;
import org.yingzuidou.platform.auth.client.interceptor.UserAuthInterceptor;
import org.yingzuidou.platform.common.utils.PasswordJwtUtil;

/**
 * 类功能描述
 * 博客模块的配置类，实现{@link WebMvcConfigurer#addInterceptors}接口方法配置服务拦截器和用户token拦截器
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Configuration
@ComponentScan("org.yingzuidou.platform.common.exception")
public class BlogConfig implements WebMvcConfigurer {

    @Autowired
    private RestTemplateBuilder builder;

    /**
     * 增加服务访问拦截器和用户信息获取拦截器
     *
     * @param registry 注册拦截器对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInterceptor());
        registry.addInterceptor(serviceAuthInterceptor());
    }

    @Bean
    public UserAuthInterceptor userAuthInterceptor() {
        return new UserAuthInterceptor();
    }

    @Bean
    public ServiceAuthInterceptor serviceAuthInterceptor() {
        return new ServiceAuthInterceptor();
    }

    @Bean
    public RestTemplate restTemplate(){
        return builder.build();
    }

    @Bean
    public ConfigData configData() {
        return new ConfigData();
    }

    @Autowired(required = false)
    public void initSecurity(ConfigData configData) {
        // 登录生成的token
        JwtTokenUtil.create(configData.getIssuer(), configData.getSubject(), configData.getSecret());
        // 知识库密码访问需要用的到token
        PasswordJwtUtil.create(configData.getIssuer(), configData.getSubject(), configData.getSecret());
        PlatformContext.create(configData.getIssuer(), configData.getSubject(), configData.getSecret(),
                configData.getTokenHeader(), configData.getTokenHeaderPrefix(), configData.getExpires(),
                configData.getRefreshToken(), configData.getZuulHeader(), configData.getZuulHeaderValue());
    }
}
