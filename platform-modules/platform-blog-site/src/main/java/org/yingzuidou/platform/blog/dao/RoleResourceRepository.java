package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.RoleResourceEntity;

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

}
