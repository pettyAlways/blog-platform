package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;

import java.util.List;

/**
 * 组织机构持久化类
 *
 * @author 鹰嘴豆
 * @date 2018/9/13     
 */
public interface PermissionRepository extends PagingAndSortingRepository<ResourceEntity, Integer>, QuerydslPredicateExecutor<ResourceEntity> {

    List<ResourceEntity> findAllByIsDeleteIs(String isDelete);

    /**
     * 资源列表是否存在子资源
     *
     * @param resourceIds 资源列表
     * @return 子资源
     */
    boolean existsByParentIdInAndIsDelete(List<Integer> resourceIds, String isDelete);

    List<ResourceEntity> findAllByIdInAndIsDeleteIs(List<Integer> ids, String isDelete);
}
