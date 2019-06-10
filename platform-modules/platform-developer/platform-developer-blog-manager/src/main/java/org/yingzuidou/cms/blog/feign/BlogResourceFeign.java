package org.yingzuidou.cms.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.yingzuidou.cms.blog.config.HystrixClientFallbackFactory;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@FeignClient(value = "PRODUCE", fallbackFactory = HystrixClientFallbackFactory.class )
public interface BlogResourceFeign {

}
