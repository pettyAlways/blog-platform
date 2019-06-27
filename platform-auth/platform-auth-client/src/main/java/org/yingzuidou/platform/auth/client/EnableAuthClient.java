package org.yingzuidou.platform.auth.client;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 类功能描述
 * <p>启用客户端配置注解</p>
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EnableAuthClientImportSelector.class)
public @interface EnableAuthClient {

    String mode() default "RESOURCE-AUTH";
}
