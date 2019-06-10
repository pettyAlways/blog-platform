package org.yingzuidou.platform.auth.client.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 类功能描述
 * <p>每一个服务上一层都会通过Zuul网关，想要做到这一点只需要将服务内网化，例如阿里云同一个区域的主机可以通过内网ip进行访问{@link UserAuthInterceptor}来进行拦截配置</p>
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class UserAuthInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        System.out.println("也是用户");
        return super.preHandle(request, response, handler);
    }
}
