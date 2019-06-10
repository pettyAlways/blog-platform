package org.yingzuidou.cms.cmsweb.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.service.ConstService;

import java.util.*;

/**
 * 类功能描述
 * 该类专门用来管理缓存，通过它来调用constBiz去获取缓存中的常量，如果不存在就
 * 从数据获取
 *
 * @author 鹰嘴豆
 * @date 2018/11/16
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/16     鹰嘴逗        v1.0        获取系统常量列表
 */
@Service
public class CmsCacheManager {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ConstService constService;

    /**
     *  查询缓存中的系统常量，如果没在缓存没命中则从数据库查询
     *
     * @return 系统常量列表
     */
    public  Map<String, String> systemConst() {
        Map<String, String> systemParams = new HashMap<>(100);
        List<CmsConstEntity> constEntities = constService.findAllConstByType("1");
        Optional.ofNullable(constEntities).orElse(new ArrayList <>())
                .forEach(item -> systemParams.put(item.getConstKey(), item.getConstValue()));
        return systemParams;
    }

    /**
     * 清除指定缓存中的键内容
     *
     * @param cacheName 缓存名字
     * @param cacheKey 缓存键
     */
    public void clearCacheByKeys(String cacheName, String cacheKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache.isKeyInCache(cacheKey)) {
            cache.remove(cacheKey);
        }
    }

    public Cache getCacheByName(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

}
