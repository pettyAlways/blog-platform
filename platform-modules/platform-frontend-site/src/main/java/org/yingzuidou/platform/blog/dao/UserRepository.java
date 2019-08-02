package org.yingzuidou.platform.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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


    /**
     * 根据第三方ID查找第三方用户
     *
     * @param thirdPartyId 第三方ID
     * @param isDelete 是否删除
     * @param status 用户状态
     * @return 用户实体
     */
    CmsUserEntity findByThirdPartyIdAndIsDeleteAndUserStatus(Integer thirdPartyId, String isDelete, String status);

    /**
     * 是否已经存在账号
     *
     * @param userAccount 账号
     * @param isDelete 是否删除
     * @return 是否存在
     */
    boolean existsByUserAccountAndIsDelete(String userAccount, String isDelete);

    /**
     * 推荐用户列表
     *
     * @param pageable 分页信息
     * @return 推荐用户列表
     */
    @Query(nativeQuery = true, value = "SELECT aa.author_id FROM (SELECT count(1) num, a.author_id FROM article a WHERE a.author_id in " +
            "(SELECT DISTINCT ur.user_id FROM user_role ur LEFT JOIN role r ON ur.role_id = r.id AND r.in_use = 1 WHERE ur.role_id = 3) " +
            "AND a.is_delete = :isDelete GROUP BY a.author_id ORDER BY num DESC) aa WHERE 1=1 LIMIT 0, 2")
    List<Integer> retrieveRecommendUser(@Param("isDelete") String isDelete);

    /**
     * 推荐用户列表
     *
     * @param isDelete 是否删除
     * @return 推荐用户列表
     */
    @Query(nativeQuery = true, value = "SELECT aa.author_id FROM (SELECT count(1) num, a.author_id FROM article a WHERE a.author_id in " +
            "(SELECT DISTINCT ur.user_id FROM user_role ur LEFT JOIN role r ON ur.role_id = r.id AND r.in_use = 1 WHERE ur.role_id = 3) " +
            "AND a.is_delete = :isDelete GROUP BY a.author_id ORDER BY num DESC) aa WHERE 1=1 LIMIT 0, 3")
    List<Integer> retrieveRecommendUserList(@Param("isDelete") String isDelete);
}
