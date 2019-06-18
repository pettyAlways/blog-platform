package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.platform.blog.service.BlogResource;

/**
 * 类功能描述
 * 博客写作后台资源类,根据登录用户获取各自的资源
 *
 *
 * @author 鹰嘴豆
 * @date 2019/6/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private BlogResource blogResource;

    @GetMapping("/list")
    public CmsMap listResource() {
        blogResource.listResource();
        return null;
    }
}
