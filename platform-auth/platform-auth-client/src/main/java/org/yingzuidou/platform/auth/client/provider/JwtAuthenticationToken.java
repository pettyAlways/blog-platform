package org.yingzuidou.platform.auth.client.provider;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;

import java.util.Collection;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Object principal;

    private String token;

    public JwtAuthenticationToken(String token) {
       this(null, token, null);
    }

    public JwtAuthenticationToken(UserDetails userDetails, String token, Collection<? extends GrantedAuthority> getAuthoritie) {
        super(getAuthoritie);
        this.principal = userDetails;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getToken() {
        return token;
    }
}
