package org.yingzuidou.platform.auth.client.core.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");
        grantedAuthorities.add(grantedAuthority);
        return new JWTUserDetails(1, "yingzuidou", "$2a$10$/zL2TEu4yWA2GRio7dTRhu1Lh2XRXExCYGaTAyY1myUeQJqunGu92", grantedAuthorities, false);
    }
}
