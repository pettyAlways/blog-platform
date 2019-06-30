package org.yingzuidou.platform.auth.client.core.interceptor;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 类功能描述
 * <p>当前拦截器是{@link org.springframework.security.web.access.intercept.FilterSecurityInterceptor}的延伸实现，
 * 在这个{@code FilterSecurityInterceptor}拦截器之前执行,通过获取请求的路径去数据库查找拥有的权限，并与登录时获取到的所有
 * 资源角色映射集合做比较
 * <p>资源的授权在这拦截器中执行，其中有个因为错误servlet容器执行/error请求导致拦截链执行两次问题，如果是继承
 * {@link org.springframework.web.filter.OncePerRequestFilter}类的拦截器则不存在这个问题，因为它的
 * {@link org.springframework.web.filter.OncePerRequestFilter#shouldNotFilterErrorDispatch}设置默认跳过异常类
 * <p>针对这个问题采用以下代码解决当前拦截器被多次执行问题
 * <pre><code>
 *     if (request.getAttribute(WebUtils.ERROR_REQUEST_URI_ATTRIBUTE) != null) {
 *          chain.doFilter(request, response);
 *     }
 * </code></pre>
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private SecurityMetadataSource securityMetadataSource;


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(SecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 如果是错误页面的请求则不执行该拦截器
        if (request.getAttribute(WebUtils.ERROR_REQUEST_URI_ATTRIBUTE) != null) {
            chain.doFilter(request, response);
            return;
        }
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }
        finally {
            super.afterInvocation(token, null);
        }
    }

}
