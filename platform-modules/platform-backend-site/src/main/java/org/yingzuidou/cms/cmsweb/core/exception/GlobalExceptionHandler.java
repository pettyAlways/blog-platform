package org.yingzuidou.cms.cmsweb.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yingzuidou.cms.cmsweb.core.CmsMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 鹰嘴豆
 * @date 2018/9/13     
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
