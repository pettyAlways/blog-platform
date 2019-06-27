package org.yingzuidou.platform.auth.client.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yingzuidou.platform.auth.client.vo.AuthConfig;

import java.util.Objects;

/**
 * 类功能描述
 * <p>每个注册在eureka上的服务会被互相访问，所以需要增加一个服务的校验，在事先指定某个服务能被那些服务访问，这些服务间
 * 的鉴权就需要用到{@link ServiceAuthInterceptor}来进行拦截配置</p>
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Slf4j
public class ServiceAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthConfig authConfig;

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        String whereFrom = request.getHeader(authConfig.getServiceHeader());
        if (Objects.equals(whereFrom, "zuul-client-token")) {
            return super.preHandle(request, response, handler);
        }
        log.warn("当前请求[" + request.getRequestURI() + "]非法访问模块");
        return false;
    }
}
