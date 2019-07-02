package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.common.vo.Node;

import java.util.List;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ResourceService {

    /**
     * 获取外部资源的资源路径与路径所有角色的映射集合
     *
     * @return 资源路径与角色集合的映射
     */
    Map<String,List<String>> findResourceRoleMap();

    Node acquireUserPermission(int id);
}
