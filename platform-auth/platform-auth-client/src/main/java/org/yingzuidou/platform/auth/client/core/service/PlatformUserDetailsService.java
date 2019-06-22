package org.yingzuidou.platform.auth.client.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.auth.client.feign.ServerAuthFeign;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        CmsUserEntity user = CmsBeanUtils.map2Bean((Map) cmsMap.get("data"), CmsUserEntity.class);
        // MD5加密需要用到盐值
        ThreadStorageUtil.storeItem("salt-uuid", user.getUuid());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
        grantedAuthorities.add(grantedAuthority);
        return new JWTUserDetails(user.getId(), user.getUserAccount(), user.getUserPassword(),
                grantedAuthorities, user.getUserStatus());
    }
}
