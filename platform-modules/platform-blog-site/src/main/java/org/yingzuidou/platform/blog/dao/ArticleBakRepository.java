package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.ArticleBakEntity;

import java.util.List;

public interface ArticleBakRepository extends PagingAndSortingRepository<ArticleBakEntity, Integer>,
        QuerydslPredicateExecutor<ArticleBakEntity> {

    List<ArticleBakEntity> findAllByKnowledgeIdAndStatusInAndIsDelete(Integer knowledgeId, List<String> status, String isDelete);

    ArticleBakEntity findByArticleIdAndIsDeleteAndStatus(Integer articleId, String isDelete, String status);

}
