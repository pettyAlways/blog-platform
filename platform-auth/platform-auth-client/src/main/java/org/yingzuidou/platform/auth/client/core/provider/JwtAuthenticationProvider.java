package org.yingzuidou.platform.auth.client.core.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.common.exception.BusinessException;

import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/19
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService platformUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((JwtAuthenticationToken)authentication).getToken();
        DecodedJWT jwt = JwtTokenUtil.decode(token);
        if (JwtTokenUtil.isExpires(jwt)) {
            throw new BusinessException("Token过期");
        }
        String userAccount = jwt.getClaim("userAccount").asString();
        UserDetails user = platformUserDetailsService.loadUserByUsername(userAccount);
        if (Objects.isNull(user)) {
            throw new BusinessException("token无效");
        }
        String password = user.getPassword();

        JwtTokenUtil.verifyToken(token, userAccount, password);
        //成功后返回认证信息，filter会将认证信息放入SecurityContext,注意需要设置是否已经登录的标志，校验成功就表明登录
        return new JwtAuthenticationToken(user, token, true, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }

    public void setPlatformUserDetailsService(UserDetailsService platformUserDetailsService) {
        this.platformUserDetailsService = platformUserDetailsService;
    }
}
