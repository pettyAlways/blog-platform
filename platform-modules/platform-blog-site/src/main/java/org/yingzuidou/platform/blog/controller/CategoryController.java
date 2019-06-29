package org.yingzuidou.platform.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("cateory")
public class CategoryController {

    @RequestMapping("search")
    public CmsMap<List<String>> loadAuthResource() {
       List<String> users = new ArrayList<>();
       users.add("yingzuidou");
        return CmsMap.<List<String>>ok().setResult(users);
    }
}
