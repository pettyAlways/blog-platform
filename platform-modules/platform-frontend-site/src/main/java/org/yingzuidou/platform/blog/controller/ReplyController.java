package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.service.ReplyService;
import org.yingzuidou.platform.common.entity.CommentEntity;
import org.yingzuidou.platform.common.entity.ReplyEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

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
@RequestMapping("reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @DeleteMapping("/delete")
    public CmsMap deleteReply(Integer replyId) {
        replyService.deleteReplyById(replyId);
        return CmsMap.ok();
    }

    @PutMapping("/edit")
    public CmsMap editReply(@RequestBody ReplyEntity replyEntity) {
        replyService.editReply(replyEntity);
        return CmsMap.ok();
    }

    /**
     * 新增一条回复
     *
     * @param replyEntity 回复内容
     * @return 回复新增状态
     */
    @PostMapping("/add")
    public CmsMap addComment(@RequestBody ReplyEntity replyEntity) {
        replyService.addReply(replyEntity);
        return CmsMap.ok();
    }
}
