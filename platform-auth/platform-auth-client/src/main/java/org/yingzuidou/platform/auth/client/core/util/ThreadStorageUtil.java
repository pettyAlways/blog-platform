package org.yingzuidou.platform.auth.client.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class ThreadStorageUtil {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    private static Map<String, Object> stores;

    /**
     * 存放键值对到本地缓存中
     *
     * @param key key值
     * @param value value值
     */
    public static void storeItem(String key, Object value) {
        Map<String, Object> stores = threadLocal.get();
        if (Objects.isNull(stores)) {
            stores = new HashMap<>();
        }
        stores.put(key, value);
        threadLocal.set(stores);
    }

    public static Object getItem(String key) {
        Map<String, Object> stores = threadLocal.get();
        if (Objects.isNull(stores)) {
            return "";
        }
        return stores.get(key);
    }
    /**
     * 移除本地线程缓存的key的值
     *
     * @param key 需要移除的key
     */
    public static void removeItemByKey(String key) {
        Map<String, Object> stores = threadLocal.get();
        if (Objects.isNull(stores)) {
            return;
        }
        stores.remove(key);
        threadLocal.set(stores);
    }
}
