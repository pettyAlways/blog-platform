package org.yingzuidou.cms.cmsweb.core.validate;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 类功能描述
 * 电话号码校验实现类，包括400开头的号码、手机号、固定电话
 *
 * @author 鹰嘴豆
 * @date 2018/11/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class TelValidation implements ConstraintValidator<TelNum, String> {
    @Override
    public void initialize(TelNum constraintAnnotation) {
    }

    /**
     * 如果非空则校验电话号码格式，如果为空则不校验
     *
     * @param value 待校验的内容
     * @param context 校验上下文
     * @return 是否有效
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.hasLength(value)) {
            // 电话号码校验
            String regex = "(^[0-9]{3,4}-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\\([0-9]{3,4}\\)[0-9]{3,8}$)" +
                    "|(^(400)-(\\d{3})-(\\d{4})(.)(\\d{1,4})$)|(^(400)-(\\d{3})-(\\d{4})$)" +
                    "|(^(0|86|17951)?(13[0-9]|16[0-9]|15[012356789]|17[678]|18[0-9]|14[357])[0-9]{8}$)";
            return Pattern.matches(regex, value);
        }
        return true;
    }
}
