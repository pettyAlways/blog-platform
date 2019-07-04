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
    List<CategoryEntity> findAllByCategoryName(String categoryName);

    /**
     * 查找所有未删除的分类包括未启用分类，并获取创建人名字
     *
     * @return 分类集合
     */
    @Query(nativeQuery = true, value= "SELECT c.*, u.user_name FROM category c LEFT JOIN cms_user u " +
            "on c.creator = u.id WHERE c.is_delete = 'N' ORDER BY c.sort")
    List<CategoryEntity> findAllWithCreatorName();

}
