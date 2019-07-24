package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.UserRoleRepository;
import org.yingzuidou.platform.blog.service.UserRoleService;
import org.yingzuidou.platform.common.entity.UserRoleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 从用户角色关联表中查找指定用户所拥有的角色名列表
     *
     * @param id 用户Id
     * @return 指定用户拥有的角色名列表
     */
    @Override
    public List<String> retrieveRolesByUserId(int id) {
        if (Objects.isNull(id)) {
            return new ArrayList<>();
        }
        return userRoleRepository.findRoleNameListByUserId(id);
    }

    /**
     * 给用户新增角色
     *
     * @param userId 用户ID 用户ID
     * @param roleId 角色ID 角色ID
     */
    @Override
    public void addRoleForUser(Integer userId, Integer roleId) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userId).setRoleId(roleId).setCreateTime(new Date());
        userRoleRepository.save(userRoleEntity);
    }
}
