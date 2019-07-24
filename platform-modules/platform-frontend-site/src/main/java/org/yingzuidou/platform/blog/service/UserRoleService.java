package org.yingzuidou.platform.blog.service;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface UserRoleService {

    /**
     * 获取用户所拥有的角色列表
     *
     * @param id 用户Id
     * @return 用户所拥有的角色列表
     */
    List<String> retrieveRolesByUserId(int id);

    /**
     * 为用户增加角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     */
    void addRoleForUser(Integer userId, Integer roleId);
}
