package org.yingzuidou.platform.blog;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.yingzuidou.platform.auth.client.EnableAuthClient;

/**
 * 类功能描述
 * <p>1. 在这个{@link BlogApplication}类上配置了缓存、鉴权和熔断，这里熔断是给服务降级准备的，只要在需要降级的服务的方
 * 法上增加{@link HystrixCommand}注解就可以非常简单的实现服务降级。
 * <p>2. 关于{@link EntityScan}注解主要让Spring加载platform-common模块中的JPA实体。
 * <p>3. {@link EnableAuthClient}中的mode分两种，一种是SERVICE_AUTH，它的作用是为了让当前服务判断请求是否来自ZUUL网关一种
 * 是RESOURCE-AUTH,主要是给ZUUL网关做认证授权
 *
 * @author 鹰嘴豆
 * @date 2019/6/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@SpringBootApplication
@EnableCaching
@EntityScan("org.yingzuidou.platform")
@EnableAuthClient(mode = "SERVICE-AUTH")
@EnableCircuitBreaker
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
