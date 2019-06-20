package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.UserRoleEntity;

import java.util.List;

/**
 * RoleRepository
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
public interface UserRoleRepository extends PagingAndSortingRepository<UserRoleEntity, Integer>, QuerydslPredicateExecutor<UserRoleEntity> {

    void deleteAllByUserId(Integer userId);

    /**
     * 从用户角色表中根据用户id查找所有启用角色ID
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Query(nativeQuery = true, value = "select r.id from user_role ur, role r " +
            "where ur.role_id = r.id and ur.user_id=:userId and r.in_use='1'")
    List<Object> findAllByUserIdAndRoleInUse(@Param("userId") Integer userId);

    /**
     * 查找所有的关联角色的用户
     *
     * @param roleId 角色Id
     * @return 所有关联角色的用户列表
     */
    List<UserRoleEntity> findAllByRoleId(Integer roleId);

    @Query(nativeQuery = true, value = "select ur.user_id from user_role ur, role r " +
            "where ur.role_id = r.id and ur.role_id in (:roleId) and r.in_use='1'")
    List<Object> findAllByRoleIdAndRoleInUse(@Param("roleId") List roleId);

}
