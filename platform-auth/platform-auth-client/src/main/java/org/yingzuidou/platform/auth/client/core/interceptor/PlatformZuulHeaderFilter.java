package org.yingzuidou.platform.auth.client.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yingzuidou.platform.common.request.MutableHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 类功能描述
 * <p>基于SpringCloud的服务需要判断访问该服务的上一个服务是否已经授予访问权限，这里上一个服务是zuul网关，任何不经过网关
 * 访问服务的请求都将被{@link org.yingzuidou.platform.auth.client.interceptor.ServiceAuthInterceptor}拦截器拦截，
 * 处理方式是通过在{@link HttpServletRequest}的请求头中增加一个头部标志，由于{@link HttpServletRequest}不提供增加头部参数
 * 的方法，所以这里通过继承实现{@link HttpServletRequestWrapper}一个子类来完成。
 * 参考{@link HeaderWriterFilter#doFilterInternal}的实现方式
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Slf4j
public class PlatformZuulHeaderFilter extends OncePerRequestFilter {

    @Value("${auth.token-header}")
    private String zuulHeader;

    @Value("${auth.token-value}")
    private String zuulHeaderPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        MutableHttpServletRequest mRequest = new MutableHttpServletRequest(request);
        mRequest.putHeader(zuulHeader, zuulHeaderPrefix);
        filterChain.doFilter(request, response);
    }
}
