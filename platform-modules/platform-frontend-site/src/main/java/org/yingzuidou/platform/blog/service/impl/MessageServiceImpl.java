package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.blog.dao.MessageRepository;
import org.yingzuidou.platform.blog.dto.MessageDTO;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.common.constant.IsReadEnum;
import org.yingzuidou.platform.common.constant.MessageTypeEnum;
import org.yingzuidou.platform.common.entity.MessageEntity;
import org.yingzuidou.platform.common.exception.BusinessException;

import java.util.*;
import java.util.stream.Collectors;

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
     * 获取用户下的未读消息列表
     *
     * @param userId 用户ID
     * @return 未读消息列表
     */
    @Override
    public List<MessageDTO> retrieveMessageList(int userId) {
        List<MessageEntity> unReadMessageList = messageRepository.findAllByUserIdAndMReadOrderByCreateTimeDesc(userId, IsReadEnum.UNREAD.getValue());
        if (unReadMessageList.isEmpty()) {
            return null;
        }
        return unReadMessageList.stream().map(item -> new MessageDTO().setUserId(item.getUserId())
                .setMessageId(item.getId()).setMessage(item.getMessage()).setCreateTime(item.getCreateTime()))
                .collect(Collectors.toList());
    }

    /**
     * 单条消息已读
     *
     * @param messageId 消息ID
     */
    @Override
    public void messageReaded(int messageId) {
        Optional<MessageEntity> messageEntityOp = messageRepository.findById(messageId);
        if (!messageEntityOp.isPresent()) {
            throw new BusinessException("消息不存在");
        }
        MessageEntity target = messageEntityOp.get();
        target.setMRead(IsReadEnum.READ.getValue());
        messageRepository.save(target);
    }

    /**
     * 全部消息已读
     *
     * @param userId 用户ID
     */
    @Override
    public void messageAllReaded(Integer userId) {
        messageRepository.updateAllMessageReadByUserId(userId, IsReadEnum.UNREAD.getValue());
    }
}
