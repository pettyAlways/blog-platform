package org.yingzuidou.platform.common.utils;

import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class HystrixUtil {

    private static final String KEY = "flag";

    public static boolean checkHystrixException(CmsMap cmsMap) {
        return !Objects.equals(cmsMap.get(KEY), false);
    }
}
