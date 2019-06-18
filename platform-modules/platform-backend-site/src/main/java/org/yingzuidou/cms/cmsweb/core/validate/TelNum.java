package org.yingzuidou.cms.cmsweb.core.validate;

/**
 * 类功能描述
 * 自定义校验电话号码校验注解
 * 参考文章：https://www.ibm.com/developerworks/cn/java/j-lo-jsr303/
 *
 * @author 鹰嘴豆
 * @date 2018/11/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {TelValidation.class})
@Documented
@Target( { ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TelNum {
    String message() default "号码格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
