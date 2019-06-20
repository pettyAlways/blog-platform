package org.yingzuidou.cms.cmsweb.service;


import org.yingzuidou.platform.common.entity.CmsUserEntity;

/**
 * LoginService
 *
 * @author 鹰嘴豆
 * @date 2018/10/16
 */
public interface LoginService {

    void userLock(String userAccount);

    /**
     * 保存用户登录时间等信息
     *
     * @param user 用户对象
     */
    void saveUser(CmsUserEntity user);
}
