package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.*;
import org.yingzuidou.platform.blog.dto.AuditDTO;
import org.yingzuidou.platform.blog.service.AuditService;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.common.constant.AuditResultEnum;
import org.yingzuidou.platform.common.constant.MessageTypeEnum;
import org.yingzuidou.platform.common.entity.*;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    private static final Integer ADMIN_ROLE = 1;

    /**
     * 获取登录用户下的所有作者申请
     *
     * @param handleResult 处理结果
     * @param pageInfo 分页信息
     * @return 作者申请列表
     */
    @Override
    public List<AuditDTO> getAuthorAudit(String handleResult, PageInfo pageInfo) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<Object> roleList =userRoleRepository.findAllByUserIdAndRoleInUse(user.getId());
        if (!roleList.contains(ADMIN_ROLE)) {
            throw new BusinessException("没有权限查询作者申请列表");
        }
        List<Object[]> authorAuditList = auditRepository.findBeAuthorAudit(user.getId(), handleResult, pageInfo.toPageableNoOrder());
        return Optional.ofNullable(authorAuditList).orElse(new ArrayList<>()).stream().map(item -> AuditDTO
        .authorAuditList.apply(item)).collect(Collectors.toList());
    }

    /**
     * 作者审核通过
     *
     * @param auditId 审核ID
     */
    @Override
    public void authorPass(Integer auditId) {
        Optional<AuditEntity> auditEntityOp = auditRepository.findById(auditId);
        if (!auditEntityOp.isPresent()) {
            throw new BusinessException("审核记录不存在");
        }
        AuditEntity target = auditEntityOp.get();
        target.setHandleResult(AuditResultEnum.PASS.getValue()).setHandleTime(new Date());
        auditRepository.save(target);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        userRoleEntity.setRoleId(3).setUserId(target.getApplyUser()).setCreateTime(new Date()).setCreator(user.getId());
        userRoleRepository.save(userRoleEntity);
        Integer count = userRepository.findAuthorCount();
        String message = "恭喜您成为了本站第" + count + "个作者，管理员寄语：加油吧少年，一起构建完善的知识体系";
        messageService.addMessage(MessageTypeEnum.JOINKNOWLEDGERESULT.getValue(), message, target.getApplyUser());
    }

    /**
     * 作者审核不通过
     *
     * @param auditId 审核ID
     * @param reason 不通过理由
     */
    @Override
    public void authorNoPass(Integer auditId, String reason) {
        Optional<AuditEntity> auditEntityOp = auditRepository.findById(auditId);
        if (!auditEntityOp.isPresent()) {
            throw new BusinessException("审核记录不存在");
        }
        AuditEntity target = auditEntityOp.get();
        target.setHandleResult(AuditResultEnum.NOPASS.getValue()).setHandleTime(new Date())
        .setRejectReason(reason);
        auditRepository.save(target);
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        String message = "权衡再三，" + user.getUserName() + "拒绝了您提交的作者申请，拒绝理由:" + reason;
        messageService.addMessage(MessageTypeEnum.JOINKNOWLEDGERESULT.getValue(), message, target.getApplyUser());
    }

    /**
     * 获取知识库加入的申请列表
     *
     * @param handleResult 处理结果
     * @param pageInfo 分页信息
     * @return 知识库申请列表
     */
    @Override
    public List<AuditDTO> getJoinKnowledgeAudit(String handleResult, PageInfo pageInfo) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<Object[]> joinKnowledgeList = auditRepository.findKnowledgeJoinList(user.getId(), handleResult, pageInfo.toPageableNoOrder());
        return Optional.ofNullable(joinKnowledgeList).orElse(new ArrayList<>()).stream().map(item -> AuditDTO
                .joinKnowledgeList.apply(item)).collect(Collectors.toList());
    }

    /**
     * 加入知识库申请通过
     *
     * @param auditId 审核ID
     */
    @Override
    public void joinKnowledgePass(Integer auditId) {
        Optional<AuditEntity> auditEntityOp = auditRepository.findById(auditId);
        if (!auditEntityOp.isPresent()) {
            throw new BusinessException("审核记录不存在");
        }
        AuditEntity target = auditEntityOp.get();
        target.setHandleResult(AuditResultEnum.PASS.getValue()).setHandleTime(new Date());
        auditRepository.save(target);
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(target.getApplyObj());
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setKnowledgeId(target.getApplyObj()).setParticipantId(target.getApplyUser()).setCreateTime(new Date())
                .setCreator(user.getId());
        participantRepository.save(participantEntity);
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        String message = "恭喜！" + user.getUserName() + "通过了您加入知识库[" + knowledgeEntity.getKName() + "]的申请，" +
                user.getUserName() + "寄语：一起努力吧，少年";
        messageService.addMessage(MessageTypeEnum.JOINKNOWLEDGERESULT.getValue(), message, target.getApplyUser());
    }

    /**
     * 加入知识库申请不通过
     *
     * @param auditId 审核ID
     * @param reason 不通过理由
     */
    @Override
    public void joinKnowledgeNoPass(Integer auditId, String reason) {
        Optional<AuditEntity> auditEntityOp = auditRepository.findById(auditId);
        if (!auditEntityOp.isPresent()) {
            throw new BusinessException("审核记录不存在");
        }
        AuditEntity target = auditEntityOp.get();
        target.setHandleResult(AuditResultEnum.NOPASS.getValue()).setHandleTime(new Date())
                .setRejectReason(reason);
        auditRepository.save(target);
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(target.getApplyObj());
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        String message = user.getUserName() + "权衡再三拒绝了您加入知识库[" + knowledgeEntity.getKName() + "]的申请,拒绝理由：" + reason;
        messageService.addMessage(MessageTypeEnum.JOINKNOWLEDGERESULT.getValue(), message, target.getApplyUser());
    }
}
