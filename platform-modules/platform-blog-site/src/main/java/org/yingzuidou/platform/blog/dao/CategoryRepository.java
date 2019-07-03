package org.yingzuidou.platform.blog.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.CategoryEntity;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Integer>, QuerydslPredicateExecutor<CategoryEntity> {

    List<CategoryEntity> findAllByCategoryName(String categoryName);
}
