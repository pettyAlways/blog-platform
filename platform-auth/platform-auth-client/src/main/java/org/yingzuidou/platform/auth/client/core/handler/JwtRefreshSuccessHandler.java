package org.yingzuidou.platform.auth.client.core.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.auth.client.provider.JwtAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/19
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {

    private String tokenHeader;

    private String tokenHeaderPrefix;

    private long refreshToken;

    private int expires;


    public JwtRefreshSuccessHandler(String tokenHeader, String tokenHeaderPrefix) {
        this.tokenHeader = tokenHeader;
        this.tokenHeaderPrefix = tokenHeaderPrefix;
        this.refreshToken = PlatformContext.getRefreshToken();
        this.expires = PlatformContext.getExpires();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String oToken = ((JwtAuthenticationToken) authentication).getToken();
        DecodedJWT jwt = JwtTokenUtil.decode(oToken);

        if (shouldTokenRefresh(jwt.getIssuedAt())) {
            JWTUserDetails userDetails = (JWTUserDetails) authentication.getPrincipal();
            String token = JwtTokenUtil
                    .generateToken(userDetails.getUserName(), userDetails.getUserPassword(), expires, userDetails.getGrantedAuthorities());
            response.setHeader(tokenHeader, tokenHeaderPrefix + token);
        }

    }

    private boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusMinutes(refreshToken).isAfter(issueTime);
    }
}
