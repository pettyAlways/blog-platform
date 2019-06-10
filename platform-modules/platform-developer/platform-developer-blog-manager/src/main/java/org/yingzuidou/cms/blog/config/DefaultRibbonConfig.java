package org.yingzuidou.cms.blog.config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/5/19
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class DefaultRibbonConfig {
    /**
     * 全局配置路由规则
     */
    @Bean
    public IRule ribbonRule() {
        return new BestAvailableRule();
    }
}
