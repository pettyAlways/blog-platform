package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.common.entity.RecentEditEntity;

import java.util.List;

public interface RecentEditRepository extends PagingAndSortingRepository<RecentEditEntity, Integer>,
        QuerydslPredicateExecutor<RecentEditEntity> {

    boolean removeAllByArticleId(Integer articleId);

    /**
     * 通过EntityGraph能够将多个表通过一条sql出来，根据用户ID查找最近的编辑列表
     *
     * @param userId 用户ID
     * @return 最近编辑的列表
     */
    @EntityGraph(value = "RecentEdit.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<RecentEditEntity> findFirst6ByUserIdOrderByEditTimeDesc(Integer userId);

    /**
     * 使用JPA的Class-based Projections方式映射原生sql的结果集，获取最近的3个知识库
     *
     * @param userId 用户ID
     * @param num 知识库个数
     * @return 最近知识库结果集
     */
    @Query(nativeQuery = true, value = "SELECT k.id AS knowledgeId, k.k_name AS knowledgeName, c.id AS categoryId, " +
            "c.category_name AS categoryName FROM knowledge k " +
            "INNER JOIN (SELECT recent.knowledge_id, MAX(recent.edit_time) edit_time " +
            "FROM `recent-edit` recent WHERE recent.user_id = :userId  GROUP BY recent.knowledge_id " +
            "ORDER BY edit_time DESC LIMIT 0, :num) rr " +
            "ON k.id = rr.knowledge_id " +
            "LEFT JOIN category c ON k.k_type = c.id")
    List<RecentKnowledgeDTO> findRecentKnowledge(@Param("userId") Integer userId, @Param("num") Integer num);


}
