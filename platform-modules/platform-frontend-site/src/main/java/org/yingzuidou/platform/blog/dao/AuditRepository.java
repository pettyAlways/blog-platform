package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.AuditEntity;

public interface AuditRepository extends PagingAndSortingRepository<AuditEntity, Integer>, QuerydslPredicateExecutor<AuditEntity> {

    /**
     * 用户申请成为作者的审批是否已经存在
     *
     * @param applyUser 申请用户
     * @param applyType 成为作者
     * @param HandleResult 处理只能怪
     * @return true 存在 false 不存在
     */
    boolean existsByApplyUserAndApplyTypeAndHandleResult(Integer applyUser, String applyType, String HandleResult);
}
