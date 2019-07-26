package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.blog.dto.UserSkillDTO;
import org.yingzuidou.platform.common.entity.UserSkillEntity;

import java.util.List;

public interface UserSkillRepository extends PagingAndSortingRepository<UserSkillEntity, Integer>, QuerydslPredicateExecutor<UserSkillEntity> {


    /**
     * 获取用户的技能
     *
     * @param userId 用户ID
     * @return 技能列表
     */
    List<UserSkillEntity> findAllByUserId(Integer userId);

}
