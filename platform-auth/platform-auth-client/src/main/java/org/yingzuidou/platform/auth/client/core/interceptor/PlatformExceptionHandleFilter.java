package org.yingzuidou.platform.auth.client.core.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 * 基于SpringSecurity的全局异常处理
 *
 * @author 鹰嘴豆
 * @date 2019/6/20
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformExceptionHandleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception  exception) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        }
    }
}
