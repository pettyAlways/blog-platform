package org.yingzuidou.cms.cmsweb.core.utils;

import org.springframework.context.ApplicationContext;

/**
 * SpringUtil
 *
 * @author shangguanls
 * @date 2018/10/23
 */
public class SpringUtil {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);

    }
}
