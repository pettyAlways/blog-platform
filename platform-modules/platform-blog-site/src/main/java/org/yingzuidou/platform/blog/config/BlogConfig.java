package org.yingzuidou.platform.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yingzuidou.platform.auth.client.interceptor.ServiceAuthInterceptor;
import org.yingzuidou.platform.auth.client.interceptor.UserAuthInterceptor;

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
}
