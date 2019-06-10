package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.service.ConstService;
import org.yingzuidou.platform.common.cache.CmsCacheManager;
import org.yingzuidou.platform.common.dto.CmsMap;

import java.util.Map;

/**
 * 类功能描述
 * 系统相关接口在这个类中实现
 *
 * @author 鹰嘴豆
 * @date 2018/11/16
 *
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/16     鹰嘴豆        v1          增加系统常量的读取
 */

@RestController
@RequestMapping(value="/system")
public class SystemController {

   @Autowired
   private ConstService constService;

    /**
     * 获取系统标题常量
     *
     * @return 系统标志
     */
    @GetMapping(value="/sysParams.do")
    public CmsMap systemParams() {
        CmsMap<Map<String, String>> cMap = new CmsMap<>();
        // 获取所有的系统常量
        Map<String, String> systemParams = constService.systemConst();
        cMap.success().setResult(systemParams);

        return cMap;
    }

}
