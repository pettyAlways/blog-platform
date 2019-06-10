package org.yingzuidou.cms.cmsweb.core.boot;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.yingzuidou.cms.cmsweb.service.ConstService;

/**
 * 类功能描述
 * 项目启动时执行加载常量缓存
 * 该方法可以获取容器中的Bean
 *
 * @author 鹰嘴豆
 * @date 2018/11/16
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/16     鹰嘴豆        v1.0        加载常量并缓存Ehcache中
 */
@Component
public class CmsBootStart implements ApplicationRunner {

    @Autowired
    private ConstService constService;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 获取系统常量管理中的系统常量和枚举常量
     *
     * @param args 命令行参数
     */
    @Override
    public void run(ApplicationArguments args) {
        // 获取系统常量列表
        constService.findAllConstByType("1");
        // 获取枚举常量
        constService.findAllConstByType("2");
    }
}
