package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.AuditRepository;
import org.yingzuidou.platform.blog.dao.UserRoleRepository;
import org.yingzuidou.platform.blog.service.AuditService;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.common.constant.AuditEnum;
import org.yingzuidou.platform.common.constant.InUseEnum;
import org.yingzuidou.platform.common.constant.MessageTypeEnum;
import org.yingzuidou.platform.common.entity.AuditEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.UserRoleEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/27
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class AuditServiceImpl implements AuditService {

    /**
     * 拥有审核用户成为作者的角色
     */
    private static final Integer SHARE_ROLE = 1;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private MessageService messageService;


    @Override
    public void becomeAuthorAudit(AuditEntity auditEntity) {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<UserRoleEntity> userRoleEntityList = userRoleRepository.findUserListByRoleId(SHARE_ROLE, InUseEnum.USE.getValue());
        auditEntity.setApplyType(AuditEnum.AUTHOR.getValue()).setApplyTime(new Date());
        Optional.ofNullable(userRoleEntityList).orElse(new ArrayList<>()).stream().map(UserRoleEntity::getUserId)
                .forEach(userId -> {
                    AuditEntity temp = new AuditEntity();
                    CmsBeanUtils.copyMorNULLProperties(auditEntity, temp);
                    temp.setHandleUser(userId);
                    auditRepository.save(auditEntity);
                    messageService.addMessage(MessageTypeEnum.BEAUTHOR.getValue(), cmsUserEntity.getUserName() + "申请成为作者", userId);
                });
    }
}
