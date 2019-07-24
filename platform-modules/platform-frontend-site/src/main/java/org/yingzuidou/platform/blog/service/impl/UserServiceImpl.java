package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.blog.dao.UserRepository;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.UserRoleService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.constant.IsDeleteEnum;
import org.yingzuidou.platform.common.constant.LockStatusEnum;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.CmsCommonUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 普通用户授予的角色ID
     */
    private static final Integer ORDINARY = 2;

    @Override
    public String generateJwt(UserDTO userDTO) {
        CmsUserEntity user = userRepository.findByThirdPartyIdAndIsDeleteAndUserStatus(userDTO.getThirdPartyId(),
                IsDeleteEnum.NOTDELETE.getValue(), LockStatusEnum.NORMAL.getValue());
        if (Objects.isNull(user)) {
            CmsUserEntity cmsUserEntity = new CmsUserEntity();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String password = CmsCommonUtil.getMd5PasswordText(uuid, CmsBeanUtils.objectToString(userDTO.getThirdPartyId()));
            cmsUserEntity.setUserName(userDTO.getUserName()).setUserAvatar(userDTO.getAvatarUrl())
                    .setThirdPartyId(userDTO.getThirdPartyId()).setLoginTime(new Date())
                    .setUserAccount(CmsBeanUtils.objectToString(userDTO.getThirdPartyId()))
                    .setUserPassword(password)
                    .setUuid(uuid);
            user = userRepository.save(cmsUserEntity);
            userRoleService.addRoleForUser(user.getId(), ORDINARY);
        }
        List<String>  roleNameList =  userRoleService.retrieveRolesByUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = roleNameList.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return JwtTokenUtil.generateToken(user.getId(), user.getUserName(), user.getUserAccount(),
                    user.getUserPassword(), 600, grantedAuthorities);
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserDTO retrieveUserInfo(int userId) {
        Optional<CmsUserEntity> cmsUserEntityOp = userRepository.findById(userId);
        if (!cmsUserEntityOp.isPresent()) {
            throw new BusinessException("用户不存在");
        }
        CmsUserEntity cmsUserEntity = cmsUserEntityOp.get();
        return new UserDTO().setUserId(cmsUserEntity.getId()).setUserName(cmsUserEntity.getUserName())
                .setAvatarUrl(cmsUserEntity.getUserAvatar());
    }

    /**
     * 获取用户个人资料
     *
     * @param userId 用户ID
     * @return 个人资料
     */
    @Override
    public UserDTO retrieveUserProfile(Integer userId) {
        UserDTO userDTO = new UserDTO();
        Optional<CmsUserEntity> userOp = userRepository.findById(userId);
        if (userOp.isPresent()) {
            CmsUserEntity userEntity = userOp.get();
            userDTO.setUserId(userEntity.getId()).setUserName(userEntity.getUserName())
                    .setAvatarUrl(userEntity.getUserAvatar()).setCoverUrl(userEntity.getCoverUrl())
                    .setSignature(userEntity.getSignature());
        }
        return userDTO;
    }
}
