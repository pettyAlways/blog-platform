package org.yingzuidou.cms.cmsweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.yingzuidou.cms.cmsweb.core.utils.SpringUtil;

/**
 * cms web 入口函数
 * @author yingzuidou
 */
@SpringBootApplication
@EnableCaching
public class CmsWebApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(CmsWebApplication.class, args);
		SpringUtil.setApplicationContext(applicationContext);
	}
}
