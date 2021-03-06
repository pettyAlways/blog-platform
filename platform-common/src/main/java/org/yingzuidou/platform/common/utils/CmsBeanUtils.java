package org.yingzuidou.platform.common.utils;
/**
 * CmsBeanUtils description goes here
 *
 * @author dell
 * @date 2018/9/15
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author dell
 * @date 2018/9/15     
 */
public class CmsBeanUtils extends BeanUtils {


    public static void copyMorNULLProperties(Object source, Object target) {
        copyMorNULLProperties(source, target, null, (String[])null);
    }

    public static void copyMorNULLProperties(Object source, Object target, @Nullable Class<?> editable,
                                      @Nullable String... ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 增加空判断
                            if (value == null) {
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 将map转成指ID指定的Bean对象
     *
     * @param map 需要转换的map
     * @param bean bean的class类型
     * @param <T> Bean的类型
     * @return 转换后的bean
     */
    public static <T> T map2Bean(Map map, Class<T> bean) {
        String json = JSONObject.toJSONString(map);
        return JSONObject.parseObject(json, bean);
    }

    public static <T> T beanTransform(Object obj, Class<T> bean) {
        String json = JSONObject.toJSONString(obj);
        return JSONObject.parseObject(json, bean);
    }

    public static <T> List<T> object2List(Object obj, Class<T> list) {
        String json = JSONObject.toJSONString(obj);
        return JSONArray.parseArray(json, list);
    }

    public static String beanToJson(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public static <T> T jsonToBean(String json, Class<T> bean) {
        return JSONObject.parseObject(json, bean);
    }

    public static Integer objectToInt(Object obj) {
        if (Objects.nonNull(obj)) {
            return Integer.valueOf(String.valueOf(obj));
        }
        return null;
    }

    public static String objectToString(Object obj) {
        if (Objects.nonNull(obj)) {
            return String.valueOf(obj);
        }
        return null;
    }

    public static String limitContent(String content, int num) {
        if (Objects.nonNull(content)) {
            String placeholder = content.length() > num ? "..." : "";
            int minLen = content.length() > num ? num : content.length();
            return content.substring(0, minLen) + placeholder;
        }
        return null;
    }

}
