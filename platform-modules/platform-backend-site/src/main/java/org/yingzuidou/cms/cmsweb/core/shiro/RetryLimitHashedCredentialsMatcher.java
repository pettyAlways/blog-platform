package org.yingzuidou.cms.cmsweb.core.shiro;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.yingzuidou.cms.cmsweb.core.cache.CmsCacheManager;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 类功能描述
 * shiro 加密密码匹配并认证错误的次数限制
 *
 * @author 鹰嘴豆
 * @date 2018/11/20
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    /**
     * 这里注入导致CmsCacheManager中的ConstService过早创建，让这个ConstService中的AOP失效
     * 加个@lazy可以解决
     */
    @Autowired
    @Lazy
    private CmsCacheManager cmsCacheManager;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CmsUserEntity user = (CmsUserEntity) info.getPrincipals().getPrimaryPrincipal();
        Cache cache = cmsCacheManager.getCacheByName("LoginRetry");
        if (Objects.isNull(cache)) {
            throw new IllegalArgumentException("找不到LoginRetry缓存");
        }
        Element usrCache = cache.get("user_" + user.getId());
        // 为每个用户创建一个计数器
        if (Objects.isNull(usrCache)) {
            usrCache = new Element("user_" + user.getId(), new AtomicInteger(1));
            cache.put(usrCache);
        }

        // 密码匹配则移除用户重登计数器缓存
        if (super.doCredentialsMatch(token, info)) {
            cache.remove("user_" + user.getId());
            return true;
        }

        // 如果超过5次则抛出异常在登录页面的subject.login的try catch中捕捉
        AtomicInteger retryCounts = (AtomicInteger) usrCache.getObjectValue();
        if ( retryCounts.incrementAndGet() > 5) {
            cache.remove("user_" + user.getId());
            throw new ExcessiveAttemptsException();
        }

        return false;
    }
}
