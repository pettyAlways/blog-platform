package org.yingzuidou.platform.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
@EnableZuulProxy
@EnableAuthClient
@EnableFeignClients(value = "org.yingzuidou.platform.auth.client")
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
