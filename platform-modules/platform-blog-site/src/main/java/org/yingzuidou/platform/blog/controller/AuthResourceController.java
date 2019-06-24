package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.platform.blog.service.ResourceService;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("resource")
public class AuthResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("auth")
    public CmsMap<Map<String, List<String>>> loadAuthResource() {
        Map<String, List<String>> authResource = resourceService.findResourceRoleMap();
        return CmsMap.<Map<String, List<String>>>ok().setResult(authResource);
    }

}
