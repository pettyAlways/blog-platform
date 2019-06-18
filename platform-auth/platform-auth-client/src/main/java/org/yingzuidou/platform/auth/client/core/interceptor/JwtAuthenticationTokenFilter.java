package org.yingzuidou.platform.auth.client.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yingzuidou.platform.auth.client.core.matcher.SkipPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher;

    @Value("${jwt.token-header}")
    private String tokenHeader;

    @Value("${jwt.token-header-prefix}")
    private String tokenHeaderPrefix;

    public JwtAuthenticationTokenFilter(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    /**
     * 从请求头中获取token值,请求头中的token格式在依赖该安全模块的配置文件中指定
     *
     * @param request 请求头对象
     * @return token值
     */
    private String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader(this.tokenHeader);
        if (!(StringUtils.hasText(authInfo) && authInfo.startsWith(tokenHeaderPrefix))) {
            throw new InsufficientAuthenticationException("token无效");
        }
        return authInfo.substring(tokenHeaderPrefix.length());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 不需要jwt拦截的请求校验
        if (Objects.nonNull(requestMatcher) &&  !requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
        }
        String token = getJwtToken(request);
        log.info("当前请求" + request.getRequestURI() + "携带的token的值：[" + token + "]");
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(JWT.decode(token));
        // 查看redis中的token信息是否过期
        boolean isExists = redisUtil.hexists(RedisKeys.USER_KEY,authToken);
        if (!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
        } else {
            Authentication authResult = null;
            AuthenticationException failed = null;
            try {
                // 从头中获取token并封装后提交给AuthenticationManager
                String token = getJwtToken(request);
                if (!StringUtils.isEmpty(token)) {
                    JwtAuthenticationToken authToken = new JwtAuthenticationToken(JWT.decode(token));
                    authResult = this.getAuthenticationManager().authenticate(authToken);
                } else {  //如果token长度为0
                    failed = new InsufficientAuthenticationException("JWT is Empty");
                }
            } catch(JWTDecodeException e) {
                logger.error("JWT format error", e);
                failed = new InsufficientAuthenticationException("JWT format error", failed);
            }catch (InternalAuthenticationServiceException e) {
                logger.error(
                        "An internal error occurred while trying to authenticate the user.",
                        failed);
                failed = e;
            }catch (AuthenticationException e) {
                // Authentication failed
                failed = e;
            }
            if(authResult != null) {   //token认证成功
                successfulAuthentication(request, response, filterChain, authResult);
            } else if(!permissiveRequest(request)){
                //token认证失败，并且这个request不在例外列表里，才会返回错误
                unsuccessfulAuthentication(request, response, failed);
                return;
            }
            filterChain.doFilter(request, response);
        }


    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    protected boolean permissiveRequest(HttpServletRequest request) {
        if (permissiveRequestMatchers == null) {
            return false;
        }
        for(RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if (permissiveMatcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
