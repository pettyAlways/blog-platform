package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.UserRepository;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;
    /**
     * 根据用户名获取未删除的用户
     *
     * @param userName 用户名
     * @return 用户对象
     */
    @Override
    public CmsUserEntity loadUserByUserName(String userName) {
        return userRepository.findByUserAccountAndIsDelete(userName, "N");
    }
}
