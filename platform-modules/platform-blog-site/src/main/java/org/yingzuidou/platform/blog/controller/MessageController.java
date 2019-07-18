package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.blog.service.SysConstService;
import org.yingzuidou.platform.blog.service.UserRoleService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.constant.ConstEnum;
import org.yingzuidou.platform.common.constant.IsReadEnum;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.MessageEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 类功能描述
 * 用户消息通知功能模块
 *
 * @author 鹰嘴豆
 * @date 2019/7/18
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
     * 获取用户未读消息的数量
     *
     * @return 未读消息数量
     */
    @GetMapping("/unRead/count")
    public CmsMap message() {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Integer messageCount = messageService.countOfMessage(user.getId());
        return CmsMap.ok().setResult(messageCount);
    }

    /**
     * 获取用户已读或者未读的消息列表
     *
     * @param isRead 已读或未读
     * @return 已读或者未读消息列表
     */
    @GetMapping("/list")
    public CmsMap messages(@RequestParam("isRead") String isRead) {
        String backKey = Objects.equals(isRead, "Y") ? "readMessages" : "unReadMessages";
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<MessageEntity> messageEntities = messageService.userMessages(user.getId(), isRead);
        return CmsMap.ok().appendData(backKey, messageEntities);
    }

    /**
     * 设置消息为已读
     *
     * @param messgeId 消息ID
     * @return 用户未读消息数量
     */
    @PostMapping("/confirm/read")
    public CmsMap confirmRead(@RequestParam("id") Integer messgeId) {
        messageService.setMessageRead(messgeId);
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Integer messageCount = messageService.countOfMessage(user.getId());
        return CmsMap.ok().appendData("unReadNum", messageCount);
    }

    /**
     * 设置用户下所有未读消息为已读
     *
     * @return 用户未读消息数量
     */
    @PostMapping("/confirm/all")
    public CmsMap confirmAllRead() {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        messageService.setMessageAllRead(user.getId());
        Integer messageCount = messageService.countOfMessage(user.getId());
        return CmsMap.ok().appendData("unReadNum", messageCount);
    }

    @GetMapping("/audit")
    public CmsMap auditMessage(@RequestParam("isRead") String isRead) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<MessageEntity> messageEntities = messageService.retrieveAuditMessage(user.getId(), isRead);
        return CmsMap.ok().appendData("auditMessages", messageEntities);
    }

    @DeleteMapping("/deleteRead")
    public CmsMap deleteReadMessage() {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        messageService.deleteMessage(user.getId(), IsReadEnum.READ.getValue());
        return CmsMap.ok();
    }

}
