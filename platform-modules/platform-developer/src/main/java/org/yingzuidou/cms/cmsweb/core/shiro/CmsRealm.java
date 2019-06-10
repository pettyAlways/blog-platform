package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.constant.LockStatusEnum;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import java.util.Objects;

/**
 * Shiro相关的授权、校验
 *
 * @author 鹰嘴豆
 * @date 2018/10/14     
 */
public class CmsRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserBiz userBiz;

    @Autowired
    private UserRepository userRepository;

    private Integer ahour = 3600000;
    /**
     * 做登录认证，提供一个用户对象给AuthenticationInfo，subject.login自动做密码加密认证
     * 加密请参考这篇文章：https://www.cnblogs.com/wq3435/p/6260692.html
     *
     * @param authenticationToken 存放原始用户名和密码
     * @return 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        // 从数据库获取对应用户名密码的用户
        CmsUserEntity user = userBiz.findByUserAccount(userName);
        // 保存当前用户
        if (Objects.isNull(user)) {
            throw new AuthenticationException("账号不存在");
        }
        // 用户禁用则抛出异常提示用户
        if (LockStatusEnum.INVAILD.getValue().equals(user.getUserStatus())) {
            throw new AuthenticationException("用户已经被禁用，请联系管理员");
            // 用户被锁定则判断是否已经过了一个小时，是则可以重新登录
        } else if (LockStatusEnum.LOCK.getValue().equals(user.getUserStatus())) {
            if (System.currentTimeMillis() - user.getLockTime().getTime() < ahour) {
                throw new AuthenticationException("用户已经被锁定一个小时");
            }
            // 恢复用户锁定的状态
            user.setUserStatus(LockStatusEnum.NORMAL.getValue());
            userRepository.save(user);
        }
        /*
           返回AuthenticationInfo会在subject.login中做密码校验,
           这里使用HashedCredentialsMatcher做密码加密认证
           详细请看AuthenticatingRealm.assertCredentialsMatch方法
         */
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUuid());
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), credentialsSalt , getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("需要调用subject.hasRoles()才能调用该方法");
        return null;
    }

}
