package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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


    @Query(nativeQuery = true, value = "SELECT a.id, a.article_title, a.post_time, a.creator, u.user_name " +
            "FROM article a LEFT JOIN cms_user u  ON a.creator = u.id " +
            "WHERE a.knowledge_id = :knowledgeId AND a.is_delete = :isDelete ORDER BY a.post_time ASC")
    List<Object[]> findArticleListInKnowledge(@Param("knowledgeId") Integer knowledgeId, @Param("isDelete") String isDelete);

    /**
     * 获取文章显示的信息
     *
     * @param articleId 文章ID
     * @param isDelete 是否删除
     * @return 文章显示信息
     */
    @Query(nativeQuery = true, value = "SELECT a.id as articleId, a.article_title, a.post_time, a.content, a.creator, " +
            "u.user_name, k.id AS knowledgeId, k.k_name, c.id AS categoryId, c.category_name, a.cover_url " +
            "FROM article a LEFT JOIN cms_user u  ON a.creator = u.id " +
            "LEFT JOIN knowledge k ON k.id = a.knowledge_id " +
            "LEFT JOIN category c ON c.id = k.k_type WHERE a.id = :articleId AND a.is_delete = :isDelete")
    List<Object[]>  findArticleShowInfo(@Param("articleId") Integer articleId, @Param("isDelete") String isDelete);

    List<ArticleEntity> findByIdIn(List<Integer> articleId);

    /**
     * 查找知识库下修改的6篇文章
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 最近修改的6篇文章
     */
    @Query(nativeQuery = true, value = "SELECT a.id, a.article_title, a.post_time FROM article a " +
            "WHERE a.knowledge_id = :knowledgeId AND a.is_delete = :isDelete ORDER BY update_time DESC Limit 0, 6")
    List<Object[]> findLast6ArticleInKnowledge(@Param("knowledgeId") Integer knowledgeId, @Param("isDelete") String isDelete);

    /**
     * 查找知识库下第一篇发布的文章
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 知识库下第一篇文章
     */
    ArticleEntity findFirstByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(Integer knowledgeId, String isDelete);

    boolean existsByKnowledgeIdAndIsDelete(Integer knowledgeId, String isDelete);
}
