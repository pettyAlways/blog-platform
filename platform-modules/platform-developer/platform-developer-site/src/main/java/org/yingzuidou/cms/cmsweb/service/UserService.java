package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.platform.common.paging.PageInfo;

/**
 * 用户管理Service
 *
 * @author shangguanls
 * @date 2018/9/24
 */
public interface UserService {
    UserDTO list(UserDTO userDTO, PageInfo pageInfo);

    void save(CmsUserEntity userEntity);

    void update(CmsUserEntity userEntity);

    void delete(Integer[] delIds);

    UserDTO userInfo();

    void authUser(UserDTO userDTO);

    UserDTO acquireRoles(Integer id);
}
