package org.yingzuidou.platform.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.OperRecordEntity;

import java.util.List;

public interface OperRecordRepository extends PagingAndSortingRepository<OperRecordEntity, Integer>,
        QuerydslPredicateExecutor<OperRecordEntity> {

    @Query(nativeQuery = true, value = "SELECT o.* FROM oper_record o " +
            "WHERE ((o.root_type = :knowledgeType AND o.root_obj in (:kRoot)) OR o.root_type = :categoryType) " +
            "ORDER BY o.oper_time DESC LIMIT 0, 6")
    List<OperRecordEntity> findRecentPost(
            @Param("knowledgeType") String knowledgeType,
            @Param("kRoot") List<Integer> kRoot,
            @Param("categoryType") String categoryType
    );


}
