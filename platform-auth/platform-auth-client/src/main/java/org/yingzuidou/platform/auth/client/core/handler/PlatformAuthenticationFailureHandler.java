package org.yingzuidou.platform.auth.client.core.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 * <p>登录认证失败处理方法在{@link AbstractAuthenticationProcessingFilter#doFilter}的异常块中调用
 * 通过{@link HttpServletResponse#sendError(int)}返回错误码和信息
 * <p>需要注意如果使用{@link HttpServletResponse#sendError(int)}则会调用
 * {@link org.apache.catalina.connector.Response#sendError(int)}增加一次{@code errorState}的错误状态，如果这个状态存在，
 * 那么在{@link org.apache.catalina.core.StandardHostValve#invoke}中通过如下代码判断
 * <pre><code>
 *     if (response.isErrorReportRequired()) {
 *          // If an error has occurred that prevents further I/O, don't waste time
 *          // producing an error report that will never be read
 *          AtomicBoolean result = new AtomicBoolean(false);
 *          response.getCoyoteResponse().action(ActionCode.IS_IO_ALLOWED, result);
 *              if (result.get()) {
 *                  if (t != null) {
 *                      throwable(request, response, t);
 *                   } else {
 *                      status(request, response);
 *                   }
 *              }
 *    }
 * </code></pre>
 * <p>上述代码会发送/error的请求再次触发拦截链，这就是出现拦截器链执行两次的原因
 *
 * @author 鹰嘴豆
 * @date 2019/6/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }
}
