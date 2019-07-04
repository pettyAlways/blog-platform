package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.blog.service.CategoryService;
import org.yingzuidou.platform.common.entity.CategoryEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/28
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * <p>查找所有的分类而不管是否是启用，由于是协同的博客网站，分类对拥有权限的用户共享
     * <p><note>在前端需要比较未启用的资源</note>
     *
     * @return 分类集合
     */
    @GetMapping("/search")
    public CmsMap<List<CategoryDTO>> search() {
       List<CategoryDTO> categoryEntities = categoryService.searchCategory();
       return CmsMap.<List<CategoryDTO>>ok().setResult(categoryEntities);
    }

    @DeleteMapping("/delete")
    public CmsMap delete(Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return CmsMap.ok();
    }

    @PutMapping("/update")
    public CmsMap update(@RequestBody CategoryEntity categoryEntity) {
        categoryService.updateCategory(categoryEntity);
        return CmsMap.ok();
    }

    @PostMapping("/add")
    public CmsMap add(@RequestBody CategoryEntity categoryEntity) {
        categoryService.insertCategory(categoryEntity);
        return CmsMap.ok();
    }
}
