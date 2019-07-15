package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.ArticleParticipantEntity;

import java.util.List;

public interface ArticleParticipantRepository extends PagingAndSortingRepository<ArticleParticipantEntity, Integer>, QuerydslPredicateExecutor<ArticleParticipantEntity> {

    @EntityGraph(value = "ArticleParticipant.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<ArticleParticipantEntity> findAllByArticleId(Integer articleId);

    int removeAllByArticleId(Integer articleId);

    /**
     * 是否在指定文章下已经存在文章编辑者
     *
     * @param articleId 文章ID
     * @param userId 参与者ID
     * @return true：存在 false: 不存在
     */
    boolean existsByArticleIdAndUserId(Integer articleId, Integer userId);
}
