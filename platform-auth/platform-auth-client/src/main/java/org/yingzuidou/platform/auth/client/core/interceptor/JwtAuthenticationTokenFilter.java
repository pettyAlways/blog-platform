package org.yingzuidou.platform.auth.client.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yingzuidou.platform.auth.client.core.handler.PlatformAuthenticationFailureHandler;
import org.yingzuidou.platform.auth.client.provider.JwtAuthenticationToken;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 类功能描述
 * <p>Jwt对token的校验，这里需要注意异常的处理，SpringSecurity默认的异常处理在{@link HttpSecurity#exceptionHandling()}
 * 这个方法中指定拦截器处理而这个拦截器优先级排在倒数的位置，而{@link JwtAuthenticationTokenFilter}拦截器在异常拦截器
 * 之前处理，又因为在jwt拦截器中有业务处理，会抛出异常，一旦抛出异常就会返回拦截器不会执行接下来的拦截器，这也就是说
 * 不会经过异常处理这个拦截器。这时候我们需要在这个拦截器中捕捉异常并处理,详细参考{@link #doFilterInternal}
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

    private AuthenticationManager authenticationManager;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    public JwtAuthenticationTokenFilter(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 不需要jwt拦截的请求校验
        if (Objects.nonNull(requestMatcher) &&  !requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getJwtToken(request);
        log.info("当前请求" + request.getRequestURI() + "携带的token的值：[" + token + "]");
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(token);
        try {
            Authentication authentication = this.getAuthenticationManager().authenticate(authToken);
            if (Objects.nonNull(authentication)) {
                authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                // 执行下一个拦截器
                filterChain.doFilter(request, response);
            }
        } catch (Exception exception) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, new InsufficientAuthenticationException(exception.getMessage()));
        }
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

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
