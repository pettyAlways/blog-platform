package org.yingzuidou.cms.blog.config;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.yingzuidou.cms.blog.feign.BlogResourceFeign;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/5/20
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<BlogResourceFeign> {

    @Override
    public BlogResourceFeign create(Throwable cause) {
        return null;
    }
}
