package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.CategoryRepository;
import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.blog.service.CategoryService;
import org.yingzuidou.platform.common.constant.InUseEnum;
import org.yingzuidou.platform.common.constant.IsDeleteEnum;
import org.yingzuidou.platform.common.entity.CategoryEntity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 * 分类服务实现类，做分类常见的增删改查功能操作，这部分功能是提供用户操作的，所以需要考虑数据的安全性与可恢复性
 *
 * @author 鹰嘴豆
 * @date 2019/7/3
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 所有有效的分类列表
     *
     * @return 分类列表
     */
    @Override
    public List<CategoryDTO> allCategoryInUse() {
        List<CategoryEntity> categoryEntities = categoryRepository
                .findAllByIsDeleteAndInUse(IsDeleteEnum.NOTDELETE.getValue(), InUseEnum.USE.getValue());
        return Optional.ofNullable(categoryEntities).orElse(new ArrayList<>()).stream()
                .map(item -> new CategoryDTO().setCategoryId(item.getId()).setCategoryName(item.getCategoryName()))
                .collect(Collectors.toList());
    }
}
