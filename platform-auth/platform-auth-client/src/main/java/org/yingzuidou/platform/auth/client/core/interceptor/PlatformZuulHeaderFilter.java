package org.yingzuidou.platform.auth.client.core.interceptor;

import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yingzuidou.platform.auth.client.core.util.PlatformContext;
import org.yingzuidou.platform.common.request.MutableHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 * <p>基于SpringCloud的服务需要判断访问该服务的上一个服务是否已经授予访问权限，这里上一个服务是zuul网关，任何不经过网关
 * 访问服务的请求都将被{@link org.yingzuidou.platform.auth.client.interceptor.ServiceAuthInterceptor}拦截器拦截，
 * 处理方式是通过在{@link HttpServletRequest}的请求头中增加一个头部标志，由于{@link HttpServletRequest}不提供增加头部参数
 * 的方法，所以这里通过继承实现{@link HttpServletRequestWrapper}一个子类来完成。
 * 参考{@link HeaderWriterFilter#doFilterInternal}的实现方式
 * <p>这个过滤器不能在{@code @Configuration}类中使用{@code @Bean}进行注册到容器，它会{@link ApplicationFilterChain}注册，
 * 由于已经有个SpringSecurity的{@link DelegatingFilterProxyRegistrationBean}拦截器在它里面注册了，我们新增拦自定义拦截器
 * 需要使用{@link HttpSecurity#addFilterBefore}来往上面的代理拦截器中新增，所以我们不能使用{@code @Bean}默认在
 * {@link ApplicationFilterChain}中再一次注册，否则会执行两遍拦截器
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Slf4j
public class PlatformZuulHeaderFilter extends OncePerRequestFilter {

    /**
     * <p>请求头拦截器的作用是给请求头增加一个访问服务请求来自zuul网关的标志，使用{@link MutableHttpServletRequest}重写
     * {@link HttpServletRequest}来增加请求头.
     * 在{@link org.yingzuidou.platform.auth.client.config.FeignConfiguration#apply(RequestTemplate)}中需要获取到
     * 这个自定义的请求，所以使用{@link RequestContextHolder#setRequestAttributes}来将这个自定义请求保存到当前线程的上下
     * 文中。
     *
     * @param request 请求对象
     * @param response 返回对象
     * @param filterChain 拦截链
     * @throws ServletException Servlet异常
     * @throws IOException Servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        MutableHttpServletRequest mRequest = new MutableHttpServletRequest(request);
        mRequest.putHeader(PlatformContext.getZuulHeader(), PlatformContext.getZuulHeaderValue());
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mRequest, response);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        filterChain.doFilter(mRequest, response);
    }
}
