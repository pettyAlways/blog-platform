package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.constant.LockStatusEnum;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;
import org.yingzuidou.cms.cmsweb.dao.OrganizationRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.OnlineDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.service.OnlineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2018/11/26
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class OnlineServerImpl implements OnlineService {

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserRepository userRepository;


    /**
     * 查询当前在线用户的角色、部门信息
     *
     * @param onlineDTO 查询条件
     * @param pageInfo 分页信息
     * @return 登录的用户
     */
    @Override
    public List<OnlineDTO> list(OnlineDTO onlineDTO, PageInfo pageInfo) {
        List<Integer> userIds = shiroService.currentOnlineUser();
        List<CmsUserEntity> users = userBiz.findUsersByIds(userIds);
        List<OnlineDTO> onlineDTOList = new ArrayList<>();

        // 获取在线用户的所属角色、部门等信息
        for (CmsUserEntity item : Optional.ofNullable(users).orElse(new ArrayList<>())) {
            OnlineDTO online = new OnlineDTO();
            online.setUserId(item.getId());
            CmsBeanUtils.copyMorNULLProperties(item, online);
            // 获取用户的角色信息
            List<Object> roleIds = userRoleRepository.findAllByUserIdAndRoleInUse(item.getId());
            if (Objects.nonNull(roleIds)) {
                List<Integer> againTransform = roleIds.stream()
                        .map(rItem -> Integer.parseInt(String.valueOf(rItem))).collect(Collectors.toList());
                List<RoleEntity> roles = roleRepository.findAllByIdInAndIsDeleteIs(againTransform, "N");
                StringBuilder sBuild = new StringBuilder();
                if (Objects.nonNull(roles)) {
                    roles.forEach(rnItem -> sBuild.append(rnItem.getRoleName()).append(","));
                    String roleNames = sBuild.toString().substring(0, sBuild.length() - 1);
                    online.setRoleName(roleNames);
                }
                // 获取部门信息
                Optional<OrganizationEntity> organizationEntityOp = organizationRepository.findById(item.getUserDepart());
                organizationEntityOp.ifPresent(organizationEntity -> online.setDepartName(organizationEntity.getOrgName()));
                onlineDTOList.add(online);
            }
        }

        // 按用户名/账号过滤
        if (StringUtils.hasLength(onlineDTO.getUserName())) {
            onlineDTOList = onlineDTOList.stream().filter(online -> online.getUserName().contains(onlineDTO.getUserName()))
                    .collect(Collectors.toList());
        }
        if (StringUtils.hasLength(onlineDTO.getUserAccount())) {
            onlineDTOList = onlineDTOList.stream().filter(online -> online.getUserAccount().contains(onlineDTO.getUserAccount()))
                    .collect(Collectors.toList());
        }

        // 分页查询为了防止前端通过Http模拟分页页数及每页条数，采取查询结果与起始索引的最小值
        if (Objects.nonNull(onlineDTOList) && onlineDTOList.size() != 0) {
            int fromIndex = Math.min(onlineDTOList.size() - 1, (pageInfo.getPage() - 1) * pageInfo.getSize());
            onlineDTOList.subList(fromIndex, Math.min(onlineDTOList.size(), fromIndex + pageInfo.getSize())  );
        }
        return onlineDTOList;
    }

    @Override
    public void inValidUser(Integer userId) {
        Optional<CmsUserEntity> userOp = userBiz.findById(userId);
        if (!userOp.isPresent()) {
            throw new BusinessException("用户不存在");
        }
        CmsUserEntity user = userOp.get();
        user.setUserStatus(LockStatusEnum.INVAILD.getValue());
        userRepository.save(user);
    }
}
