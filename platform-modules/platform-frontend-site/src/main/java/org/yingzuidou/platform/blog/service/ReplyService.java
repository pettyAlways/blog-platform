package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.ReplyDTO;
import org.yingzuidou.platform.common.entity.ReplyEntity;

import java.util.List;

public interface ReplyService {

    /**
     * 获取某个评论下的所有回复
     *
     * @param commentId 评论ID
     * @return 评论下的所有回复
     */
    List<ReplyDTO> retrieveReplyListInComment(Integer commentId);

    /**
     * 删除评论下的所有回复
     *
     * @param commentId 评论ID
     */
    void deleteAllReplyByComment(Integer commentId);

    /**
     * 根据回复ID删除回复
     *
     * @param replyId 回复ID
     */
    void deleteReplyById(Integer replyId);

    /**
     * 编辑回复
     *
     * @param replyEntity 回复实体
     */
    void editReply(ReplyEntity replyEntity);

    /**
     * 新增一条回复
     *
     * @param replyEntity 回复实体
     */
    void addReply(ReplyEntity replyEntity);
}
