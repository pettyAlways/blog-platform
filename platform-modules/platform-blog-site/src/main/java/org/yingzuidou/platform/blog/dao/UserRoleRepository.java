package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRoleEntity, Integer>, QuerydslPredicateExecutor<UserRoleEntity> {


    @Query(nativeQuery = true, value = "SELECT role.role_name FROM user_role ur LEFT JOIN role role ON ur.role_id" +
            " = role.id WHERE ur.user_id = :userId")
    List<String> findUserNameListByUserId(@Param("userId") Integer userId);
}
