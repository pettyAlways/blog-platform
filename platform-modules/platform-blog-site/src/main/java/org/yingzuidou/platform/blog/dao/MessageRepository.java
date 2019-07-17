package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Integer>, QuerydslPredicateExecutor<MessageEntity> {

    List<MessageEntity> findAllByUserIdOrderByCreateTimeDesc(Integer userId);

    List<MessageEntity> findAllByUserIdAndMReadOrderByCreateTimeDesc(Integer userId, String isRead);

    Integer countByUserIdAndMRead(Integer userId, String isRead);
}
