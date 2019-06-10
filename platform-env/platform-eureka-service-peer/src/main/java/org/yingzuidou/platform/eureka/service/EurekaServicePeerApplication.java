package org.yingzuidou.platform.eureka.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 类功能描述
 * <p>Eureka的服务注册中心</p>
 * <ul>
 *     <li>提供博客相关功能的注册</li>
 *     <li>它是一个注册中心的副本和platform-eureka-service构成一个集群</li>
 *     <li>端口8085</li>
 * </ul>
 * @author 鹰嘴豆
 * @date 2019/6/2
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@SpringBootApplication
@EnableEurekaServer
@EnableEurekaClient
public class EurekaServicePeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServicePeerApplication.class, args);
    }
}
