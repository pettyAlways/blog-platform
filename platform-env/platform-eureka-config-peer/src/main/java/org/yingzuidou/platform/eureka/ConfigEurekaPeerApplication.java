package org.yingzuidou.platform.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 类功能描述
 * <p>Eureka服务器用于注册配置中心服务</p>
 * <ul>
 *     <li>这个Eureka服务器为了实现高可用需要实现集群</li>
 *     <li>Eureka集群服务器端口为8080和8081</li>
 * </ul>
 *
 * @author 鹰嘴豆
 * @date 2019/5/31
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@SpringBootApplication
@EnableEurekaServer
public class ConfigEurekaPeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigEurekaPeerApplication.class, args);
    }
}
