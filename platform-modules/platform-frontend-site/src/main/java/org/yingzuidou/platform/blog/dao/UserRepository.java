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


    /**
     * 根据第三方ID查找第三方用户
     *
     * @param thirdPartyId 第三方ID
     * @param isDelete 是否删除
     * @param status 用户状态
     * @return 用户实体
     */
    CmsUserEntity findByThirdPartyIdAndIsDeleteAndUserStatus(Integer thirdPartyId, String isDelete, String status);
}
