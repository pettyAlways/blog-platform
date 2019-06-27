package org.yingzuidou.platform.auth.client;

import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        Map<String, Object> attrMap =  annotationMetadata.getAnnotationAttributes(EnableAuthClient.class.getName());
        List<String> configs = SpringFactoriesLoader.loadFactoryNames(EnableAuthClientImportSelector.class,
                EnableAuthClientImportSelector.class.getClassLoader());
        if (Objects.equals(attrMap.get("mode"), "RESOURCE-AUTH")) {
            configs = configs.stream().filter(item -> Objects.equals(item,
                    "org.yingzuidou.platform.auth.client.core.config.WebSecurityConfig")).collect(Collectors.toList());
        } else if (Objects.equals(attrMap.get("mode"), "SERVICE-AUTH")) {
            configs = configs.stream().filter(item -> Objects.equals(item,
                    "org.yingzuidou.platform.auth.client.config.AuthClientConfig")).collect(Collectors.toList());
        }

        return configs.toArray(new String[0]);
    }
}
