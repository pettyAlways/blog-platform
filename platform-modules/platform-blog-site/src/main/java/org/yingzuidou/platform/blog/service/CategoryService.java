package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.common.entity.CategoryEntity;

import java.util.List;

/**
 * 类功能描述
 * 分类管理模块涉及到的功能接口
 *
 * @author 鹰嘴豆
 * @date 2019/7/3
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface CategoryService {

    /**
     * 获取所有的分类包括不可用资源
     *
     * @return 所有分类集合
     */
    public List<CategoryDTO> searchCategory();

    /**
     * 根据分类id删除分类
     *
     * @param categoryId 分类ID
     */
    void deleteCategory(Integer categoryId);

    /**
     * 更新分类信息
     *
     * @param categoryEntity 分类信息实体对象
     */
    void updateCategory(CategoryEntity categoryEntity);

    /**
     * 新增一条分类
     *
     * @param categoryEntity 需要新增的分类对象
     */
    void insertCategory(CategoryEntity categoryEntity);
}
