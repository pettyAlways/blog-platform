package org.yingzuidou.platform.auth.client.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.auth.client.core.util.ResponseUtil;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.auth.client.vo.AuthConfig;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.vo.CmsMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 类功能描述
 * <p> 如果请求携带token,那么将token解析成{@link CmsUserEntity}对象并存放到当前线程的缓存中
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Slf4j
public class UserAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthConfig authConfig;

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String token;
        try {
            token = getJwtToken(request);
            if (Objects.nonNull(token)) {
                DecodedJWT jwt = JwtTokenUtil.decode(token);
                String userName = jwt.getClaim("userName").asString();
                String password = jwt.getClaim("password").asString();
                Integer userId =  jwt.getClaim("userId").asInt();
                CmsUserEntity cmsUserEntity = new CmsUserEntity();
                cmsUserEntity.setUserAccount(userName);
                cmsUserEntity.setUserPassword(password);
                cmsUserEntity.setId(userId);
                ThreadStorageUtil.storeItem("user", cmsUserEntity);
            }
        } catch (Exception e) {
            log.error(request.getRequestURI() + "所携带的token无效");
            CmsMap result = CmsMap.error(HttpStatus.FORBIDDEN.value() + "",
                    request.getRequestURI() + "所携带的token无效");
            ResponseUtil.sendError(response, HttpStatus.FORBIDDEN.value(), CmsBeanUtils.beanToJson(result));
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    private String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader(authConfig.getTokenHeader());
        String tokenHeaderPrefix = authConfig.getTokenHeaderPrefix();
        if (!StringUtils.hasText(authInfo)) {
            return null;
        }
        if (!authInfo.startsWith(tokenHeaderPrefix)) {
            throw new BusinessException("token无效");
        }
        return authInfo.substring(tokenHeaderPrefix.length());
    }
}
