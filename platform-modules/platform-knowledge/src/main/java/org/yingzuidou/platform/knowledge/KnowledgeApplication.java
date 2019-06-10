package org.yingzuidou.platform.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.yingzuidou.platform.auth.client.EnableAuthClient;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/5
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAuthClient
public class KnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeApplication.class, args);
    }
}
