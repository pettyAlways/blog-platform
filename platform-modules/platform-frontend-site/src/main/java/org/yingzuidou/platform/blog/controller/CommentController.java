package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.CommentDTO;
import org.yingzuidou.platform.blog.service.CommentService;
import org.yingzuidou.platform.common.entity.CommentEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/28
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 新增一条评论
     *
     * @param commentEntity 评论内容
     * @return 评论新增状态
     */
    @PostMapping("/add")
    public CmsMap addComment(@RequestBody CommentEntity commentEntity) {
        commentService.addComment(commentEntity);
        return CmsMap.ok();
    }

    /**
     * 获取文章下的所有评论
     *
     * @param articleId 文章ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户
     * @return 评论列表
     */
    @GetMapping("/search/list")
    public CmsMap<List<CommentDTO>> listComment(Integer articleId, String token, Integer userId) {
        List<CommentDTO> commentDTOList = commentService.listArticleComment(articleId, token, userId);
        return CmsMap.<List<CommentDTO>>ok().setResult(commentDTOList);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 删除状态
     */
    @DeleteMapping("/delete")
    public CmsMap deleteComment(Integer commentId) {
        commentService.deleteComment(commentId);
        return CmsMap.ok();
    }

    /**
     * 更新评论
     *
     * @param commentEntity 评论内容
     * @return 更新状态
     */
    @PutMapping("/edit")
    public CmsMap editComment(@RequestBody CommentEntity commentEntity) {
        commentService.updateComment(commentEntity);
        return CmsMap.ok();
    }

}
