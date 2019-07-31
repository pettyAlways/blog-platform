package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

import java.util.List;

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


    /**
     * 查找作者的人数
     *
     * @return 作者人数
     */
    @Query(nativeQuery = true, value = "SELECT count(c.id) " +
            "FROM cms_user c LEFT JOIN user_role ur ON c.id = ur.user_id " +
            "WHERE ur.role_id = 3 AND c.is_delete = 'N'")
    Integer findAuthorCount();
}
