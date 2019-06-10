package org.yingzuidou.platform.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 类功能描述
 * <p>SpringCloud配置中心管理其他服务的配置文件</p>
 * <ul>
 *     <li>platform-config和platform-config-peer构成一个集群端口号分别是8082、8083</li>
 *     <li>需要注册到管理配置中心的eureka集群上，这里分别是platform-eureka-config和platform-eureka-config-peer</li>
 * </ul>
 *
 * @author 鹰嘴豆
 * @date 2019/6/2
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class PlatformConfigPeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformConfigPeerApplication.class, args);
    }
}
