package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.CommentRepository;
import org.yingzuidou.platform.blog.dto.CommentDTO;
import org.yingzuidou.platform.blog.service.CommentService;
import org.yingzuidou.platform.blog.service.ReplyService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.CommentEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/28
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReplyService replyService;

    /**
     * 增加一条评论
     * @param commentEntity 评论内容
     */
    @Override
    public void addComment(CommentEntity commentEntity) {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        commentEntity.setCommentUser(cmsUserEntity.getId()).setCommentTime(new Date());
        commentRepository.save(commentEntity);
    }

    /**
     * 按照时间倒序查询文章下的所有评论
     *
     * @param articleId 文章ID
     * @return 评论列表
     */
    @Override
    public List<CommentDTO> listArticleComment(Integer articleId) {
        List<Object[]> commentEntityList = commentRepository.findAllCommentInArticle(articleId);
        return Optional.ofNullable(commentEntityList).orElse(new ArrayList<>()).stream()
                .map(item -> {
                    CommentDTO commentDTO = CommentDTO.articleCommentList.apply(item);
                    commentDTO.setReplyList(replyService.retrieveReplyListInComment(commentDTO.getCommentId()));
                    return commentDTO;
                }).collect(Collectors.toList());
    }

    /**
     * 删除评论移除所有回复
     *
     * @param commentId 评论ID
     */
    @Override
    public void deleteComment(Integer commentId) {
        Optional<CommentEntity> commentEntityOp = commentRepository.findById(commentId);
        if (!commentEntityOp.isPresent()) {
            throw new BusinessException("评论不存在");
        }
        commentRepository.deleteById(commentId);
        replyService.deleteAllReplyByComment(commentId);
    }

    /**
     * 修改评论内容
     * @param commentEntity 评论内容
     */
    @Override
    public void updateComment(CommentEntity commentEntity) {
        Optional<CommentEntity> commentEntityOp = commentRepository.findById(commentEntity.getId());
        if (!commentEntityOp.isPresent()) {
            throw new BusinessException("评论不存在");
        }
        CommentEntity target = commentEntityOp.get();
        CmsBeanUtils.copyMorNULLProperties(commentEntity, target);
        commentRepository.save(target);
    }
}
