package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Integer>, QuerydslPredicateExecutor<CategoryEntity> {

    /**
     * 是否存在同一个分类名字的列表
     *
     * @param categoryName 分类名字
     * @return 同一个分类名字的列表
     */
    List<CategoryEntity> findAllByCategoryNameAndIsDelete(String categoryName, String isDelete);

    /**
     * 根据参数是否删除查找所有分类包括未启用分类
     *
     * @param isDelete 是否删除
     * @return 分类集合
     */
    List<CategoryEntity> findAllByIsDelete(String isDelete);

}
