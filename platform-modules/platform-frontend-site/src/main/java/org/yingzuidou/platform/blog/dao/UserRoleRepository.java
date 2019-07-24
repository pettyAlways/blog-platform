package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRoleEntity, Integer>, QuerydslPredicateExecutor<UserRoleEntity> {


    /**
     * 查找指定用户的所有角色名字
     *
     * @param userId 用户ID
     * @return 角色名列表
     */
    @Query(nativeQuery = true, value = "SELECT role.role_name FROM user_role ur LEFT JOIN role role ON ur.role_id" +
            " = role.id WHERE ur.user_id = :userId")
    List<String> findRoleNameListByUserId(@Param("userId") Integer userId);

    /**
     * 从用户角色表中根据用户id查找所有启用角色ID
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    @Query(nativeQuery = true, value = "select r.id from user_role ur, role r " +
            "where ur.role_id = r.id and ur.user_id=:userId and r.in_use='1'")
    List<Object> findAllByUserIdAndRoleInUse(@Param("userId") int userId);
}
