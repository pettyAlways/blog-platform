package org.yingzuidou.platform.auth.client.core.service;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformAccessDecisionManager implements AccessDecisionManager {

    /**
     * 将当前访问拥有的权限和该访问资源路径拥有的权限对比，如果用户拥有这个资源的方法权限就通过，这里采用资源-角色组
     * 的形式来鉴权
     *
     * @param authentication 当前访问用户上下文对象
     * @param o 访问资源上下文
     * @param collection 当前访问资源拥有的权限集合
     * @throws AccessDeniedException 没有权限访问异常
     * @throws InsufficientAuthenticationException 内部鉴权异常
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AccessDeniedException("无访问权限.");
        }
        List<String> userAuth = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        List<String> resourceAuth = collection.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        if (Collections.disjoint(userAuth, resourceAuth)) {
            throw  new AccessDeniedException("无访问权限.");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
