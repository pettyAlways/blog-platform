package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * CustomFormAuthenticationFilter
 *
 * @author 鹰嘴豆
 * @date 2018/10/22
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 当访问需要登录的资源时，如果session已经过期或者重新启动时就会执行下面的方法
     * shiro在用户登录时会缓存好用户，上面的两种操作都会清空用户导致shiro认为没有登录
     *
     * @param request 前端请求
     * @param response 请求返回
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (logger.isTraceEnabled()) {
                    logger.trace("发现登录请求，准备登录");
                }
                return executeLogin(request, response);
            } else {
                if (logger.isTraceEnabled()) {
                    logger.trace("回到登录页面");
                }
                return true;
            }
        } else {
            if (logger.isTraceEnabled()) {
                logger.trace("试图访问一个需要认证的地址，重定向到登录页面");
            }
            // 需要登录才能访问
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
