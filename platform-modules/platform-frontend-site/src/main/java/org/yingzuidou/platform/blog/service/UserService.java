package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.UserDTO;

public interface UserService {

    /**
     * 用户第三方登录用户信息生成jwt
     *
     * @param userDTO 用户信息
     * @return jwt
     */
    String generateJwt(UserDTO userDTO);

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
}
