package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.AuditDTO;
import org.yingzuidou.platform.common.paging.PageInfo;

import java.util.List;

public interface AuditService {

    /**
     * 获取登录用户下的所有作者申请
     *
     * @param handleResult 处理结果
     * @param pageInfo 分页信息
     * @return 作者申请列表
     */
    List<AuditDTO> getAuthorAudit(String handleResult, PageInfo pageInfo);

    /**
     * 审核通过
     *
     * @param auditId 审核ID
     */
    void authorPass(Integer auditId);

    /**
     * 审核不通过
     *
     * @param auditId 审核ID
     * @param reason 不通过理由
     */
    void authorNoPass(Integer auditId, String reason);

    /**
     * 加入知识库审核列表
     * 
     * @param handleResult 处理结果
     * @param pageInfo 分页信息
     * @return 加入知识库审核列表
     */
    List<AuditDTO> getJoinKnowledgeAudit(String handleResult, PageInfo pageInfo);

    /**
     * 加入知识库审核通过
     *
     * @param auditId 审核ID
     */
    void joinKnowledgePass(Integer auditId);

    /**
     * 加入知识库审核不通过
     *
     * @param auditId 审核ID
     * @param reason 不通过理由
     */
    void joinKnowledgeNoPass(Integer auditId, String reason);
}
