package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.UserRoleRepository;
import org.yingzuidou.platform.blog.service.UserRoleService;

import java.util.ArrayList;
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
        return userRoleRepository.findUserNameListByUserId(id);
    }
}
