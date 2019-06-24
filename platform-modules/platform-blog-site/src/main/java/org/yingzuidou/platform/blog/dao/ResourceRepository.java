package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.ResourceEntity;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ResourceRepository extends PagingAndSortingRepository<ResourceEntity, Integer>, QuerydslPredicateExecutor<ResourceEntity> {

    /**
     * 将<b>外部</b>资源按照角色分组并将同一组的角色名字拼接，已经软删除的资源和不可用的角色将排除在外
     *
     * @return 资源路径以及该资源被授权的所有角色
     */
    @Query(nativeQuery = true, value = "SELECT r.resource_path, GROUP_CONCAT( role.role_name ) " +
            "FROM resource r LEFT JOIN role_resource roleReource ON r.id = roleReource.resource_id " +
            "LEFT JOIN role role ON role.id = roleReource.role_id AND role.in_use='1' WHERE r.is_delete = 'N' " +
            "AND r.resource_type= 'button' AND r.belongs = 'external' GROUP BY r.id")
    List<Object> acquireRoleResources();
}
