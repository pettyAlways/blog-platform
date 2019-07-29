package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.CommentEntity;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Integer>, QuerydslPredicateExecutor<CommentEntity> {


    /**
     * 获取文章下的所有按评论时间倒序排列的评论
     *
     * @param articleId 文章ID
     * @return 评论列表
     */
    @Query(nativeQuery = true, value = "SELECT c.comment_user, u.user_name, u.user_avatar, c.comment_time, c.comment_content, c.id AS commentId " +
            "FROM `comment` c LEFT JOIN cms_user u ON c.comment_user = u.id WHERE c.article_id = :articleId " +
            "ORDER BY c.comment_time DESC")
    List<Object[]> findAllCommentInArticle(@Param("articleId") Integer articleId);
}
