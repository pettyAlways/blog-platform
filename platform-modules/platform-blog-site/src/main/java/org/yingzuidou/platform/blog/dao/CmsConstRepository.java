package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CmsConstEntity;

import java.util.List;

public interface CmsConstRepository extends PagingAndSortingRepository<CmsConstEntity, Integer>, QuerydslPredicateExecutor<CmsConstEntity> {

    List<CmsConstEntity> findAllByTypeAndInUse(String type, String inUse);
}
