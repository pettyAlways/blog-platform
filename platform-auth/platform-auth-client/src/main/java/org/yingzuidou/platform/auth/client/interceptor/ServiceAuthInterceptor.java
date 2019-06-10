package org.yingzuidou.platform.auth.client.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
public class ServiceAuthInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request,
                             javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        System.out.println("也是蛮");
        return super.preHandle(request, response, handler);
    }
}
