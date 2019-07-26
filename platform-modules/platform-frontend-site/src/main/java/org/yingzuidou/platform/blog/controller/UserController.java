package org.yingzuidou.platform.blog.controller;

import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.dto.UserSkillDTO;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.UserSkillEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/search/info")
    public CmsMap<UserDTO> retrieveUser() {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        UserDTO user = userService.retrieveUserInfo(cmsUserEntity.getId());
        return CmsMap.<UserDTO>ok().setResult(user);
    }

    @GetMapping("/search/profile")
    public CmsMap<UserDTO> retrieveUserProfile(Integer userId) {
        UserDTO user = userService.retrieveUserProfile(userId);
        return CmsMap.<UserDTO>ok().setResult(user);
    }

    @PostMapping("/add/skill")
    public CmsMap<UserSkillDTO> addSkill(@RequestBody UserSkillEntity userSkillEntity) {
        UserSkillDTO userSkillDTO = userService.addUserSkill(userSkillEntity.getSkill());
        return CmsMap.<UserSkillDTO>ok().setResult(userSkillDTO);
    }

    @DeleteMapping("/del/skill")
    public CmsMap delSkill(Integer skillId) {
        userService.delUserSkill(skillId);
        return CmsMap.ok();
    }

    @GetMapping("/search/profile/extra")
    public CmsMap<UserDTO> retrieveProfileExtra() {
        UserDTO userDTO = userService.retrieveProfileExtra();
        return CmsMap.<UserDTO>ok().setResult(userDTO);
    }

    @PutMapping("/update/info")
    public CmsMap saveUserInfo(@RequestBody CmsUserEntity cmsUserEntity) {
        userService.saveUserInfo(cmsUserEntity);
        return CmsMap.ok();
    }

    @PutMapping("/bind/account")
    public CmsMap bindUserAccount(@RequestBody CmsUserEntity cmsUserEntity) {
        userService.bindUserAccount(cmsUserEntity);
        return CmsMap.ok();
    }

}
