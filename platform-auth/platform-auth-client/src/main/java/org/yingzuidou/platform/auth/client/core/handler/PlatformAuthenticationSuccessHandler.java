package org.yingzuidou.platform.auth.client.core.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.auth.client.core.util.ResponseUtil;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.vo.CmsMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String tokenHeader;

    private String tokenHeaderPrefix;

    private int expires;

    public PlatformAuthenticationSuccessHandler(String tokenHeader, String tokenHeaderPrefix) {
        this.tokenHeader = tokenHeader;
        this.tokenHeaderPrefix = tokenHeaderPrefix;
        this.expires = PlatformContext.getExpires();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JWTUserDetails userDetails =  (JWTUserDetails) authentication.getPrincipal();
        String token = JwtTokenUtil.generateToken(userDetails.getId(), userDetails.getUserName(), userDetails.getUserAccount(),
                userDetails.getUserPassword(), expires, userDetails.getGrantedAuthorities());
        response.setHeader(tokenHeader, tokenHeaderPrefix + token);
        CmsMap result = CmsMap.ok().appendData("authorization", tokenHeaderPrefix + token);
        ResponseUtil.sendBack(response, CmsBeanUtils.beanToJson(result));
    }
}
