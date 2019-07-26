package org.yingzuidou.platform.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.platform.common.vo.CmsMap;

import javax.servlet.http.HttpServletRequest;

/**
 * 类功能描述
 * 全局异常捕捉放到公共模块中供其他模块依赖，默认ControllerAdvice注解应用在所有的Controllers类中，根据这个注解的其他属性
 * 可以配置其他的目标类捕捉策略
 *
 * @author 鹰嘴豆
 * @date 2018/9/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CmsMap handleGlobalException(HttpServletRequest req, Exception e) {
        CmsMap cMap = null;
        if (e instanceof BusinessException) {
            cMap = CmsMap.error(((BusinessException) e).getCode(), e.getMessage());
        } else if (e instanceof RuntimeException) {
            logger.error("系统异常", e);
            cMap = CmsMap.error("500", "系统异常");
        }
        return cMap;
    }

}
