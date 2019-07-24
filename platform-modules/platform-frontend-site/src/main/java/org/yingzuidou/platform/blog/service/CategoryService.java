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
     * 查找所有的分类列表
     *
     * @return 有效的分类
     */
    List<CategoryDTO> allCategoryInUse();

}
