package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.UserRepository;
import org.yingzuidou.platform.blog.dao.UserSkillRepository;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.dto.UserSkillDTO;
import org.yingzuidou.platform.blog.service.UserRoleService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.constant.IsDeleteEnum;
import org.yingzuidou.platform.common.constant.LockStatusEnum;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.UserSkillEntity;
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

    @Autowired
    UserSkillRepository userSkillRepository;

    /**
     * 普通用户授予的角色ID
     */
    private static final Integer ORDINARY = 2;

    /**
     * 为第三方登录生成一个用户并赋予一个普通权限
     *
     * @param userDTO 第三方信息
     * @return 返回用户实体
     */
    @Override
    public CmsUserEntity generateUser(UserDTO userDTO) {
        CmsUserEntity user = userRepository.findByThirdPartyIdAndIsDeleteAndUserStatus(userDTO.getThirdPartyId(),
                IsDeleteEnum.NOTDELETE.getValue(), LockStatusEnum.NORMAL.getValue());
        if (Objects.isNull(user)) {
            CmsUserEntity cmsUserEntity = new CmsUserEntity();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String password = CmsCommonUtil.getMd5PasswordText(uuid, userDTO.getUserPassword());
            cmsUserEntity.setUserName(userDTO.getUserName()).setUserAvatar(userDTO.getAvatarUrl())
                    .setThirdPartyId(userDTO.getThirdPartyId()).setLoginTime(new Date())
                    .setUserAccount(userDTO.getUserAccount())
                    .setUserPassword(password)
                    .setUuid(uuid);
            user = userRepository.save(cmsUserEntity);
            userRoleService.addRoleForUser(user.getId(), ORDINARY);
        }
        return user;
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

    /**
     * 新增用户技能
     *
     * @param skill 技能
     * @return 技能信息
     */
    @Override
    public UserSkillDTO addUserSkill(String skill) {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        UserSkillEntity userSkillEntity = new UserSkillEntity();
        userSkillEntity.setSkill(skill).setUserId(cmsUserEntity.getId());
        userSkillEntity = userSkillRepository.save(userSkillEntity);
        return new UserSkillDTO().setSkillId(userSkillEntity.getId()).setSkillName(userSkillEntity.getSkill());
    }

    /**
     * 获取用户额外的资料信息
     *
     * @return 用户信息
     */
    @Override
    public UserDTO retrieveProfileExtra() {
        UserDTO userDTO = new UserDTO();
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Optional<CmsUserEntity> cmsUserEntityOp = userRepository.findById(cmsUserEntity.getId());
        if (cmsUserEntityOp.isPresent()) {
            CmsUserEntity user = cmsUserEntityOp.get();
            userDTO.setSignature(user.getSignature()).setIntroduce(user.getIntroduce()).setWork(user.getWork())
                    .setPlace(user.getPlace()).setHobby(user.getHobby()).setUserMail(user.getUserMail())
                    .setUserSex(user.getUserSex()).setThirdPartyId(user.getThirdPartyId())
                    .setIsBind(user.getIsBind()).setUserAccount(user.getUserAccount())
                    .setUserName(user.getUserName());

            List<UserSkillEntity> userSkillEntityList = userSkillRepository.findAllByUserId(cmsUserEntity.getId());
            List<UserSkillDTO> userSkillDTOList = Optional.ofNullable(userSkillEntityList).orElse(new ArrayList<>()).stream().map(item -> new UserSkillDTO()
                    .setSkillId(item.getId()).setSkillName(item.getSkill())).collect(Collectors.toList());
            userDTO.setSkillList(userSkillDTOList);
        }
        return userDTO;
    }

    /**
     * 删除用户技能
     * @param skillId 用户技能ID
     */
    @Override
    public void delUserSkill(Integer skillId) {
        Optional<UserSkillEntity> userSkillEntityOp = userSkillRepository.findById(skillId);
        if (!userSkillEntityOp.isPresent()) {
            throw new BusinessException("技能不存在");
        }
        userSkillRepository.deleteById(skillId);
    }

    /**
     * 更新用户信息
     *
     * @param user 更新用户信息
     */
    @Override
    public void saveUserInfo(CmsUserEntity user) {
        Optional<CmsUserEntity> userOp = userRepository.findById(user.getId());
        if (!userOp.isPresent()) {
            throw new BusinessException("用户不存在");
        }
        CmsUserEntity target = userOp.get();
        CmsBeanUtils.copyMorNULLProperties(user, target);
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        target.setUpdator(cmsUserEntity.getId());
        target.setUpdateTime(new Date());
        userRepository.save(target);
    }

    /**
     * 第三方登录的用户需要绑定账号方便后续登录
     *
     * @param cmsUserEntity 用户实体
     */
    @Override
    public void bindUserAccount(CmsUserEntity cmsUserEntity) {
        Optional<CmsUserEntity> userOp = userRepository.findById(cmsUserEntity.getId());
        if (!userOp.isPresent()) {
            throw new BusinessException("用户不存在");
        }
        if (userRepository.existsByUserAccountAndIsDelete(cmsUserEntity.getUserAccount(),
                IsDeleteEnum.NOTDELETE.getValue())) {
            throw new BusinessException("账号已经被使用，请更换");
        }
        CmsUserEntity target = userOp.get();
        String password = CmsCommonUtil.getMd5PasswordText(target.getUuid(),
                CmsBeanUtils.objectToString(cmsUserEntity.getUserPassword()));
        cmsUserEntity.setUserPassword(password);
        CmsBeanUtils.copyMorNULLProperties(cmsUserEntity, target);
        userRepository.save(target);
    }
}
