package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.UserDTO;
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
public interface UserService {

    /**
     * 根据用户名获取用户对象
     *
     * @param userName 用户名
     * @return 用户对象
     */
    CmsUserEntity loadUserByUserName(String userName);

    UserDTO userInfo();

}
