package org.yingzuidou.platform.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.yingzuidou.platform.auth.client.EnableAuthClient;

/**
 * 类功能描述
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
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
