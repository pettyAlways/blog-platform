package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.AuditEntity;

public interface AuditRepository extends PagingAndSortingRepository<AuditEntity, Integer>, QuerydslPredicateExecutor<AuditEntity> {


}
