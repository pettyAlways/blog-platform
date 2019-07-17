package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.MessageRepository;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.common.constant.IsReadEnum;
import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 增加一条消息通知
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userId 通知用户ID
     * @param reserve 额外的信息
     */
    @Override
    public void addMessage(String type, String message, Integer userId, String reserve) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message).setMRead(IsReadEnum.UNREAD.getValue()).setReserve(reserve)
                .setCreateTime(new Date()).setUserId(userId).setMType(type);
        messageRepository.save(messageEntity);
    }

    /**
     * 增加一条消息通知
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userId 通知用户ID
     */
    @Override
    public void addMessage(String type, String message, Integer userId) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message).setMRead(IsReadEnum.UNREAD.getValue())
                .setCreateTime(new Date()).setUserId(userId).setMType(type);
        messageRepository.save(messageEntity);
    }

    /**
     * 查询指定用户的所有消息
     *
     * @param userId 指定用户ID
     * @return 指定用户的消息
     */
    @Override
    public List<MessageEntity> listMessage(Integer userId) {
        return messageRepository.findAllByUserIdOrderByCreateTimeDesc(userId);
    }

    /**
     * 获取用户的所有已读或者未读的消息
     *
     * @param userId 指定用户ID
     * @param isRead 是否已读
     * @return 已读或未读消息
     */
    @Override
    public List<MessageEntity> listMessage(Integer userId, String isRead) {
        return messageRepository.findAllByUserIdAndMReadOrderByCreateTimeDesc(userId, isRead);
    }

    /**
     * 设置指定消息已读
     *
     * @param messageId 消息ID
     */
    @Override
    public void setMessageRead(Integer messageId) {
        Optional<MessageEntity> messageEntityOp = messageRepository.findById(messageId);
        if (!messageEntityOp.isPresent()) {
            return;
        }
        MessageEntity messageEntity = messageEntityOp.get();
        messageEntity.setMRead(IsReadEnum.READ.getValue());
        messageRepository.save(messageEntity);
    }

    @Override
    public Integer countOfMessage(int userId) {
        return messageRepository.countByUserIdAndMRead(userId, IsReadEnum.UNREAD.getValue());
    }
}
