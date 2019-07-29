package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.ReplyEntity;

import java.util.List;

public interface ReplyRepository extends PagingAndSortingRepository<ReplyEntity, Integer>, QuerydslPredicateExecutor<ReplyEntity>  {

    /**
     * 获取评论下的所有回复
     *
     * @param commentId 评论ID
     * @return 回复列表
     */
   @Query(nativeQuery = true, value = "SELECT r.reply_user, ru.user_name AS replyName, r.reply_time, r.reply_content, r.reply_obj, " +
           "ou.user_name AS objName, r.id AS replyId FROM reply r LEFT JOIN cms_user ru ON r.reply_user = ru.id " +
           "LEFT JOIN cms_user ou ON r.reply_obj = ou.id WHERE r.comment_id = :commentId")
    List<Object[]> getAllReplyInComment(@Param("commentId") Integer commentId);

    /**
     * 删除评论下的所有回复
     *
     * @param commentId 评论ID
     */
   void deleteAllByCommentId(Integer commentId);
}
