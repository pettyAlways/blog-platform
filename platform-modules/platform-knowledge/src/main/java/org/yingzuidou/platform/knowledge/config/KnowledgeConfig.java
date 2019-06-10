package org.yingzuidou.platform.knowledge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yingzuidou.platform.auth.client.interceptor.ServiceAuthInterceptor;

import java.util.Arrays;
import java.util.List;

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
public class KnowledgeConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getServiceAuthInterceptor()).addPathPatterns(getIncludePathPatterns());
    }

    @Bean
    public ServiceAuthInterceptor getServiceAuthInterceptor() {
        return new ServiceAuthInterceptor();
    }

    private List<String> getIncludePathPatterns() {
        String[] pathPatterns = {
                "/knowledge/**",
                "/test/**"
        };
        return Arrays.asList(pathPatterns);
    }
}
