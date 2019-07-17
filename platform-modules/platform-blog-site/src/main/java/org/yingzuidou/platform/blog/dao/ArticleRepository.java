package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.common.entity.ArticleEntity;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Integer>, QuerydslPredicateExecutor<ArticleEntity> {

    @EntityGraph(value = "Article.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<ArticleEntity> findAllByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(Integer knowledgeId, String isDelete);

    @EntityGraph(value = "Article.Graph", type = EntityGraph.EntityGraphType.FETCH)
    ArticleEntity  findByIdAndIsDelete(Integer articleId, String isDelete);

    List<ArticleEntity> findByIdIn(List<Integer> articleId);

    @EntityGraph(value = "Article.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<ArticleEntity> findFirst6ByKnowledgeIdAndIsDeleteOrderByUpdateTimeDesc(Integer knowledgeId, String isDelete);

    @EntityGraph(value = "Article.Graph", type = EntityGraph.EntityGraphType.FETCH)
    ArticleEntity findFirstByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(Integer knowledgeId, String isDelete);

    boolean existsByKnowledgeIdAndIsDelete(Integer knowledgeId, String isDelete);
}
