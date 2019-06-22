package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface UserRepository extends PagingAndSortingRepository<CmsUserEntity, Integer>, QuerydslPredicateExecutor<CmsUserEntity> {

    CmsUserEntity findByUserAccountAndIsDelete(String userAccount, String isDelete);
}
