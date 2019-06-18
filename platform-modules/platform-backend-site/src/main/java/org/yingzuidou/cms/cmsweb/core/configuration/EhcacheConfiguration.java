package org.yingzuidou.cms.cmsweb.core.configuration;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

/**
 * 类功能描述
 * EhcacheConfiguration提供了Ehcache缓存的相关配置
 *
 * @author 鹰嘴豆
 * @date 2018/11/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/17     鹰嘴豆        v1.0        提供Ehcache的缓存配置
 */
@Configuration
public class EhcacheConfiguration {

    @Bean(name = "cacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager (Objects.requireNonNull(bean.getObject()));
    }


    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        return cacheManagerFactoryBean;
    }
}
