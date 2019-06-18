package org.yingzuidou.platform.auth.client;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * 类功能描述
 * <p>权限校验客户端加载META-INFO下的spring.factories配置文件</p>
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class EnableAuthClientImportSelector implements ImportSelector {

    /**
     * <p>SpringBoot会通过加载器对象预先加载classpath下的META-INF里面的spring.factories文件的配置,
     * 这里通过{@link SpringFactoriesLoader}类的{@code loadFactoryNames}方法获取EnableAuthClient注解启动所需的配置类
     *
     * @param annotationMetadata 使用@EnableAuthClient所在的类上的所有注解元数据信息
     * @return 配置在META-INF下的所有配置类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        List<String> configs = SpringFactoriesLoader.loadFactoryNames(EnableAuthClientImportSelector.class,
                EnableAuthClientImportSelector.class.getClassLoader());
        annotationMetadata.getAnnotationTypes().forEach(System.out::println);
        return configs.toArray(new String[0]);
    }
}
