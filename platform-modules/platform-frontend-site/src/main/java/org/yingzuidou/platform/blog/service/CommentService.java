package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.CommentDTO;
import org.yingzuidou.platform.common.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    /**
     * 增加一条评论
     *
     * @param commentEntity 评论内容
     */
    void addComment(CommentEntity commentEntity);

    /**
     * 按照时间倒序查询文章下的所有评论
     *
     * @param articleId 文章ID
     * @return 评论列表
     */
    List<CommentDTO> listArticleComment(Integer articleId);

    /**
     * 删除评论
     * @param commentId 评论ID
     */
    void deleteComment(Integer commentId);

    /**
     * 修改回复内容
     *
     * @param commentEntity 回复内容
     */
    void updateComment(CommentEntity commentEntity);
}
