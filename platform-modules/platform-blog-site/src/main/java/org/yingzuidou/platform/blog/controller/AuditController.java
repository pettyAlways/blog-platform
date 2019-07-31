package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.AuditDTO;
import org.yingzuidou.platform.blog.service.AuditService;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    /**
     * 获取登录用户下的所有作者申请
     *
     * @param handleResult 处理结果
     * @param pageInfo 分页信息
     * @return 作者申请列表
     */
    @GetMapping("/author/list")
    public CmsMap<List<AuditDTO>> getAuthorAudit(String handleResult, PageInfo pageInfo) {
        List<AuditDTO> auditDTOList = auditService.getAuthorAudit(handleResult, pageInfo);
        return CmsMap.<List<AuditDTO>>ok().setResult(auditDTOList);
    }

    @PutMapping("/author/pass")
    public CmsMap authorPass(Integer auditId ) {
        auditService.authorPass(auditId);
        return CmsMap.ok();
    }

    @PutMapping("/author/no-pass")
    public CmsMap authorNoPass(Integer auditId, String reason ) {
        auditService.authorNoPass(auditId, reason);
        return CmsMap.ok();
    }

    /**
     * 获取登录用户下的所有作者申请
     *
     * @param handleResult 处理结果
     * @param pageInfo 分页信息
     * @return 作者申请列表
     */
    @GetMapping("/join-knowledge/list")
    public CmsMap<List<AuditDTO>> getJoinKnowledgeAudit(String handleResult, PageInfo pageInfo) {
        List<AuditDTO> knowledgeDTOList = auditService.getJoinKnowledgeAudit(handleResult, pageInfo);
        return CmsMap.<List<AuditDTO>>ok().setResult(knowledgeDTOList);
    }

    @PutMapping("/join-knowledge/pass")
    public CmsMap joinKnowledgePass(Integer auditId ) {
        auditService.joinKnowledgePass(auditId);
        return CmsMap.ok();
    }

    @PutMapping("/join-knowledge/no-pass")
    public CmsMap joinKnowledgeNoPass(Integer auditId, String reason ) {
        auditService.joinKnowledgeNoPass(auditId, reason);
        return CmsMap.ok();
    }
}
