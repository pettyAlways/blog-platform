package org.yingzuidou.platform.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.OperRecordEntity;

import java.util.List;

public interface OperRecordRepository extends PagingAndSortingRepository<OperRecordEntity, Integer>,
        QuerydslPredicateExecutor<OperRecordEntity> {


    List<OperRecordEntity> findFirst6ByRootTypeAndRootObjIn(String rootType, List<Integer> rootObj);

}
