package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;

import java.util.List;

/**
 * RoleRepository
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Integer>, QuerydslPredicateExecutor<RoleEntity> {

    List<RoleEntity> findAllByIdInAndIsDeleteIs(List<Integer> idsList, String isDelete);

    List<RoleEntity> findAllByInUseAndIsDeleteIs(String inUse, String isDelete);

}
