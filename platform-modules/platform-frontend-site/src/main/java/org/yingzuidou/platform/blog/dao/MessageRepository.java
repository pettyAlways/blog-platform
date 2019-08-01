package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Integer>, QuerydslPredicateExecutor<MessageEntity> {

    List<MessageEntity> findAllByUserIdOrderByCreateTimeDesc(Integer userId);

    Integer countByUserIdAndMRead(Integer userId, String isRead);

    /**
     * 设置用户下所有消息已读
     *
     * @param userId 用户ID
     * @param isRead 已读
     */
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE message m SET m_read = 'Y' WHERE m.user_id = :userId AND m.m_read = :isRead")
    void updateAllMessageReadByUserId(@Param("userId") Integer userId, @Param("isRead") String isRead);

    /**
     * 获取用户下所有未读的审核消息
     *
     * @param userId 用户ID
     * @param type 审核
     * @param isRead 未读
     * @return 未读的审核消息
     */
    List<MessageEntity> findAllByUserIdAndMTypeInAndMRead(Integer userId, List<String> type, String isRead);

    /**
     * 删除用户下所有已读的消息
     *
     * @param userId 用户ID
     * @param isRead 已读
     */
    void deleteAllByUserIdAndMRead(Integer userId, String isRead);

    /**
     * 获取用户下所有未读的消息
     *
     * @param userId 用户ID
     * @param isRead 是否已读
     * @return 所有未读消息
     */
    List<MessageEntity> findAllByUserIdAndMReadOrderByCreateTimeDesc(Integer userId, String isRead);
}
