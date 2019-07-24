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


    @GetMapping("/search/all")
    public CmsMap<List<CategoryDTO>> allCategory() {
        List<CategoryDTO> categoryDTOS = categoryService.allCategoryInUse();
        return CmsMap.<List<CategoryDTO>>ok().setResult(categoryDTOS);
    }


}
