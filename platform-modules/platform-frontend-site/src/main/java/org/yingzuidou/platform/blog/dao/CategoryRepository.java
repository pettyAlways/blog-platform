package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Integer>, QuerydslPredicateExecutor<CategoryEntity> {

    /**
     * 根据参数是否删除查找所有分类不包括未启用分类
     *
     * @param isDelete 是否删除
     * @param inUse 是否启用
     * @return 分类集合
     */
    List<CategoryEntity> findAllByIsDeleteAndInUse(String isDelete, String inUse);

}
