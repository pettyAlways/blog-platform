package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.service.LoginService;

import java.util.Date;

/**
 * LoginServiceImpl
 *
 * @author 鹰嘴豆
 * @date 2018/10/16
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserRepository userRepository;

    /**
     * 登录错误次数操作5次则锁定用户
     */
    @Override
    public void userLock(String userAccount) {
        CmsUserEntity currentUser = userBiz.findByUserAccount(userAccount);
        currentUser.setUserStatus("2");
        currentUser.setLockTime(new Date());
        userRepository.save(currentUser);
    }

    /**
     * 更新用户如登录时间等信息
     *
     * @param user 用户对象
     */
    @Override
    public void saveUser(CmsUserEntity user) {
        userRepository.save(user);
    }
}
