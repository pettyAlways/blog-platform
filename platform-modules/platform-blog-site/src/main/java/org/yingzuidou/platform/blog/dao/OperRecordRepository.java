package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.OperRecordEntity;

import java.util.List;

public interface OperRecordRepository extends PagingAndSortingRepository<OperRecordEntity, Integer>,
        QuerydslPredicateExecutor<OperRecordEntity> {

    /**
     * 查找用户参与的知识库下的所有动态以及分类的动态
     *
     * @param knowledgeType 根类型为知识库
     * @param kRoot 知识库ID列表
     * @param categoryType 根类型为分类
     * @return 知识库和分类下的动态
     */
    @Query(nativeQuery = true, value = "SELECT u.user_name operUser, o.oper_time, o.oper_type, " +
            "uu.user_name handleUser, o.handle_result, o.obj_type, o.obj, o.root_type, o.root_obj, o.reserve " +
            "FROM oper_record o  LEFT JOIN cms_user u ON o.oper_user = u.id LEFT JOIN cms_user uu ON o.handle_user = uu.id " +
            "WHERE ((o.root_type = :knowledgeType AND o.root_obj in (:kRoot)) OR o.root_type = :categoryType) " +
            "ORDER BY o.oper_time DESC LIMIT 0, 6")
    List<Object[]> findRecentPost(@Param("knowledgeType") String knowledgeType, @Param("kRoot") List<Integer> kRoot,
                                          @Param("categoryType") String categoryType);


}
