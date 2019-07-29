package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.common.entity.AuditEntity;

public interface AuditService {

    /**
     * 成为作者申请
     *
     * @param auditEntity 申请实体
     */
    void becomeAuthorAudit(AuditEntity auditEntity);
}
