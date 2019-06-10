package org.yingzuidou.cms.cmsweb.core;

import java.util.HashMap;

/**
 * 返回结果的Map数据结构
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
public class CmsMap<T> extends HashMap<String, Object>{

    public CmsMap<T> success() {
        this.put("code", 200);
        this.put("flag", true);
        return this;
    }


    public CmsMap<T> setResult(T data) {
        this.put("data", data);
        return this;
    }

    public <D> CmsMap<T> appendData(String code, D data) {
        this.put(code, data);
        return this;
    }

    public static CmsMap error(String code, String message) {
        CmsMap<Object> cmsMap = new CmsMap<>();
        cmsMap.put("code", code);
        cmsMap.put("message", message);
        cmsMap.put("flag", false);
        return cmsMap;
    }

    public static <T> CmsMap<T> ok() {
        CmsMap<T> cmsMap = new CmsMap<>();
        cmsMap.success();
        return cmsMap;
    }
}
