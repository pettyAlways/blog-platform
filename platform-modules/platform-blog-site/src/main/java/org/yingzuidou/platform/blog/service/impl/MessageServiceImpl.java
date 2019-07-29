package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.blog.dao.MessageRepository;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.common.constant.IsReadEnum;
import org.yingzuidou.platform.common.constant.MessageTypeEnum;
import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.*;

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
@Transactional
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
     * 批量增加消息
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userIdList 通知用户列表
     */
    @Override
    public void addBatchMessage(String type, String message, List<Integer> userIdList) {
        List<MessageEntity> messageEntities = new ArrayList<>();
        Optional.ofNullable(userIdList).orElse(new ArrayList<>()).forEach(item -> {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setMessage(message).setMRead(IsReadEnum.UNREAD.getValue())
                    .setCreateTime(new Date()).setUserId(item).setMType(type);
            messageEntities.add(messageEntity);
        });
        if (!messageEntities.isEmpty()) {
            messageRepository.saveAll(messageEntities);
        }
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

    /**
     * 设置用户下所有的未读消息为可读
     *
     * @param userId 用户ID
     */
    @Override
    public void setMessageAllRead(Integer userId) {
        messageRepository.updateAllMessageReadByUserId(userId, IsReadEnum.UNREAD.getValue());
    }

    /**
     * 获取用户的所有未读消息数量
     *
     * @param userId 用户ID
     * @return 未读消息数量
     */
    @Override
    public Integer countOfMessage(int userId) {
        return messageRepository.countByUserIdAndMRead(userId, IsReadEnum.UNREAD.getValue());
    }

    /**
     * 获取用户下的所有已读或未读
     *
     * @param userId 用户ID
     * @param isRead 是否已读
     * @return 已读或未读消息
     */
    @Override
    public List<MessageEntity> userMessages(int userId, String isRead) {
        return messageRepository.findAllByUserIdAndMReadOrderByCreateTimeDesc(userId, isRead);
    }

    /**
     * 获取用户的所有审核的消息
     *
     * @param userId 用户ID
     * @param isRead 是否已读
     * @return 所有审核的消息
     */
    @Override
    public List<MessageEntity> retrieveAuditMessage(int userId, String isRead) {
        List<String> types = Arrays.asList(MessageTypeEnum.JOINKNOWLEDGE.getValue(), MessageTypeEnum.BEAUTHOR.getValue());
        return messageRepository.findAllByUserIdAndMTypeInAndMRead(userId, types, isRead);
    }

    /**
     * 删除用户下所有已读的消息
     *
     * @param userId 用户ID
     * @param isRead 已读
     */
    @Override
    public void deleteMessage(int userId, String isRead) {
        messageRepository.deleteAllByUserIdAndMRead(userId, isRead);
    }

}
