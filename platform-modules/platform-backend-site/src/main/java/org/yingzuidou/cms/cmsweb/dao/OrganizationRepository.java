package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.OrganizationEntity;

import java.util.List;

/**
 * 组织机构持久化类
 *
 * @author 鹰嘴豆
 * @date 2018/9/13     
 */
public interface OrganizationRepository extends PagingAndSortingRepository<OrganizationEntity, Integer>, QuerydslPredicateExecutor<OrganizationEntity> {

    Page<OrganizationEntity> findByParentIdAndIsDeleteIs(Integer parentId, String isDelete, Pageable pageable);

    List<OrganizationEntity> findAllByIsDeleteIs(String isDelete);
}
