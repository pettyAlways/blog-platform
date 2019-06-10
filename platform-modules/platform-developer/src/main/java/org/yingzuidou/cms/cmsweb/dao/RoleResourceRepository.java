package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.RoleResourceEntity;

import java.util.List;
import java.util.Optional;

/**
 * RoleResourceRepository
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
public interface RoleResourceRepository extends PagingAndSortingRepository<RoleResourceEntity, Integer>, QuerydslPredicateExecutor<RoleResourceEntity> {

    void deleteAllByRoleIdIs(Integer roleId);

    /**
     * 根据资源id列表删除数据
     *
     * @param resourceIds 要删除的资源列表
     */
    void deleteAllByResourceIdIn(List<Integer> resourceIds);

    /**
     * 根据资源ID查找所有的角色资源数据
     *
     * @return 改资源所关联的角色资源数据
     */
    Optional<List<RoleResourceEntity> > findAllByResourceId(Integer resourceId);

    List<RoleResourceEntity> findAllByRoleIdIs(Integer roleId);

    List<RoleResourceEntity> findAllByRoleIdIn(List<Integer> roleIds);

    /**
     * 将资源按照角色分组并将同一组的角色名字拼接
     * 已经软删除的资源和不可用的角色将排除在外
     *
     * @return 资源路径以及该资源被授权的所有角色
     */
    @Query(nativeQuery = true, value = "SELECT r.resource_path, GROUP_CONCAT( role.role_name ) " +
            "FROM resource r LEFT JOIN role_resource roleReource ON r.id = roleReource.resource_id " +
            "LEFT JOIN role role ON role.id = roleReource.role_id AND role.in_use='1' WHERE r.is_delete = 'N' AND r.resource_type= 'button' GROUP BY r.id")
    List<Object> acquireRoleResources();

}
