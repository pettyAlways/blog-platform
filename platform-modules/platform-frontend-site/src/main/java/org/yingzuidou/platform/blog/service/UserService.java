package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.MessageDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.dto.UserSkillDTO;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

import java.util.List;

public interface UserService {

    /**
     * 用户第三方登录生成一个本博客平台的一个用户并赋予普通角色
     *
     * @param userDTO 用户信息
     * @return 返回用户实体
     */
    CmsUserEntity generateUser(UserDTO userDTO);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserDTO retrieveUserInfo(int userId);

    /**
     * 获取用户个人资料
     *
     * @param userId 用户ID
     * @return 个人资料
     */
    UserDTO retrieveUserProfile(Integer userId);

    /**
     * 新增用户技能
     *
     * @param skill 技能
     */
    UserSkillDTO addUserSkill(String skill);

    /**
     * 获取用户可编辑的资料信息
     *
     * @return 用户信息
     */
    UserDTO retrieveProfileExtra();

    /**
     * 删除用户技能
     *
     * @param skillId 用户技能ID
     */
    void delUserSkill(Integer skillId);

    /**
     * 更新用户信息
     *
     * @param userSex 更新用户信息
     */
    void saveUserInfo(CmsUserEntity userSex);

    /**
     * 绑定用户账号
     *
     * @param cmsUserEntity 用户实体
     */
    void bindUserAccount(CmsUserEntity cmsUserEntity);

    /**
     * 获取推荐用户列表
     *
     * @return 推荐用户列表
     */
    List<UserDTO> retrieveRecommendUser();

}
