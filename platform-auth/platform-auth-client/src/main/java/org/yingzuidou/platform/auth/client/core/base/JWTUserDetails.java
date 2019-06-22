package org.yingzuidou.platform.auth.client.core.base;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

import java.util.Collection;
import java.util.Objects;

/**
 * 类功能描述
 * 基于SpringSecurity的鉴权用户类必须继承它提供的UserDetails接口的一些方法，例如isAccountNonLocked等，它会在SpringSecurity
 * 的内部根据这些接口实现的内容来做校验判断，如用户是否锁定、无效、过期，如果不实现这些接口，那么登录校验就通不过
 *
 * @author 鹰嘴豆
 * @date 2019/6/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class JWTUserDetails extends CmsUserEntity implements UserDetails {

    private Collection<? extends GrantedAuthority> grantedAuthorities;


    public JWTUserDetails() {
    }

    public JWTUserDetails(int id, String userName, String userPassword, Collection<? extends GrantedAuthority> grantedAuthorities, String userLock) {
        this.setId(id);
        this.setUserName(userName);
        this.setUserPassword(userPassword);
        this.grantedAuthorities = grantedAuthorities;
        this.setUserStatus(userLock);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.equals(this.getUserStatus(), "1");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }
}
