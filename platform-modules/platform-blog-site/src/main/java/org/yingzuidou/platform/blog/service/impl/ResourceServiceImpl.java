package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.ResourceRepository;
import org.yingzuidou.platform.blog.service.ResourceService;

import java.util.*;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * 获取外部资源的资源路径与路径所有角色的映射集合
     *
     * @return 资源路径与角色集合的映射
     */
    @Override
    public Map<String, List<String>> findResourceRoleMap() {
        Map<String, List<String>> authMap = new HashMap<>(20);
        List<Object> roleResources = resourceRepository.acquireRoleResources();
        Optional.ofNullable(roleResources).orElse(new ArrayList<>()).forEach(roleResource -> {
            Object[] objects = (Object[])roleResource;
            String[] roleNames = ((String) objects[1]).split(",");
            List<String> roleList = Arrays.asList(roleNames);
            authMap.put((String) objects[0], roleList);
        });
        return authMap;
    }
}
