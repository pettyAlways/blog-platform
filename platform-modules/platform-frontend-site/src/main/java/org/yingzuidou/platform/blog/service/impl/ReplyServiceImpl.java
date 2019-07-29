package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.ReplyRepository;
import org.yingzuidou.platform.blog.dto.ReplyDTO;
import org.yingzuidou.platform.blog.service.ReplyService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.ReplyEntity;
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
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    /**
     * 获取某个评论下的所有回复
     *
     * @param commentId 评论ID
     * @return 评论下的所有回复
     */
    @Override
    public List<ReplyDTO> retrieveReplyListInComment(Integer commentId) {
        List<Object[]> replyList = replyRepository.getAllReplyInComment(commentId);
        return Optional.ofNullable(replyList).orElse(new ArrayList<>()).stream()
                .map(item -> ReplyDTO.articleReplyList.apply(item)).collect(Collectors.toList());
    }

    /**
     * 删除评论下的所有回复
     *
     * @param commentId 评论ID
     */
    @Override
    public void deleteAllReplyByComment(Integer commentId) {
        replyRepository.deleteAllByCommentId(commentId);
    }

    /**
     * 根据回复ID删除回复
     *
     * @param replyId 回复ID
     */
    @Override
    public void deleteReplyById(Integer replyId) {
        replyRepository.deleteById(replyId);
    }

    /**
     * 修改回复内容
     *
     * @param replyEntity 回复实体
     */
    @Override
    public void editReply(ReplyEntity replyEntity) {
        Optional<ReplyEntity> replyEntityOp = replyRepository.findById(replyEntity.getId());
        if (!replyEntityOp.isPresent()) {
            throw new BusinessException("回复不存在");
        }
        ReplyEntity target = replyEntityOp.get();
        CmsBeanUtils.copyMorNULLProperties(replyEntity, target);
        replyRepository.save(target);
    }

    /**
     * 新增回复
     *
     * @param replyEntity 回复实体
     */
    @Override
    public void addReply(ReplyEntity replyEntity) {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        replyEntity.setReplyUser(cmsUserEntity.getId()).setReplyTime(new Date());
        replyRepository.save(replyEntity);
    }
}
