package org.yingzuidou.platform.auth.client.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;
import org.yingzuidou.platform.auth.client.core.exception.PlatformAuthenticationException;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.auth.client.feign.ServerAuthFeign;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.HystrixUtil;
import org.yingzuidou.platform.common.vo.CmsMap;

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
public class PlatformUserDetailsService implements UserDetailsService {

    @Autowired
    private ServerAuthFeign serverAuthFeign;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        CmsMap<CmsUserEntity> cmsMap = serverAuthFeign.loadUserByUserName(userName);
        if (!HystrixUtil.checkHystrixException(cmsMap)) {
            throw new PlatformAuthenticationException(cmsMap.get("message").toString());
        }
        CmsUserEntity user = CmsBeanUtils.map2Bean((Map) cmsMap.get("data"), CmsUserEntity.class);
        if (Objects.isNull(user)) {
            throw new BadCredentialsException("用户名或密码不存在");
        }
        // MD5加密需要用到盐值
        ThreadStorageUtil.storeItem("salt-uuid", user.getUuid());
        List<String> roleNameList = CmsBeanUtils.object2List(cmsMap.get("roleList"), String.class);
        List<GrantedAuthority> grantedAuthorities = Optional.ofNullable(roleNameList).orElse(new ArrayList<>())
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new JWTUserDetails(user.getId(),user.getUserName(), user.getUserAccount(), user.getUserPassword(),
                grantedAuthorities, user.getUserStatus());
    }
}
