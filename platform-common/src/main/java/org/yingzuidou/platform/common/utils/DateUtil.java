package org.yingzuidou.platform.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/19
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class DateUtil {

    /**
     * 生成当前日期之后的某个时间
     *
     * @param unit 日期计量单位
     * @param value 具体间距值
     * @return 生成后的日期
     */
    public static Date generateAfterDate(int unit, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(unit,value);
        return calendar.getTime();
    }
}
