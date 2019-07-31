package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dto.MessageDTO;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取用户的消息列表
     *
     * @return 消息列表
     */
    @GetMapping("/message-list")
    public CmsMap<List<MessageDTO>> getMessageList() {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<MessageDTO> messageDTOList = messageService.retrieveMessageList(cmsUserEntity.getId());
        return CmsMap.<List<MessageDTO>>ok().setResult(messageDTOList);
    }

    /**
     * 单条消息已读
     *
     * @param messageId 消息ID
     * @return 执行结果
     */
    @PutMapping("/readed")
    public CmsMap messageRead(Integer messageId) {
        messageService.messageReaded(messageId);
        return CmsMap.ok();
    }

    /**
     * 用户下所有消息已读
     *
     * @return 执行结果
     */
    @PutMapping("/all/readed")
    public CmsMap messageRead() {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        messageService.messageAllReaded(cmsUserEntity.getId());
        return CmsMap.ok();
    }
}
