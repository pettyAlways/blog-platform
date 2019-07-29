package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.platform.blog.service.AuditService;
import org.yingzuidou.platform.common.entity.AuditEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/27
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/be-author")
    public CmsMap postAudit(@RequestBody AuditEntity auditEntity) {
        auditService.becomeAuthorAudit(auditEntity);
        return CmsMap.ok();
    }
}
