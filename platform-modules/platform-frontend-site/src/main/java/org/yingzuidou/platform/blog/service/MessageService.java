package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.List;

public interface MessageService {

    /**
     * 增加一条消息通知
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userId 通知用户ID
     */
    void addMessage(String type, String message, Integer userId);

    /**
     * 批量增加消息
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userIdList 通知用户列表
     */
    void addBatchMessage(String type, String message, List<Integer> userIdList);

}
