package org.yingzuidou.platform.blog.controller;

import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

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
}
