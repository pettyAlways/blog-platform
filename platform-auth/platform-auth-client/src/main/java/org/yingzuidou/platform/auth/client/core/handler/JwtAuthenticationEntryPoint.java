package org.yingzuidou.platform.auth.client.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求访问: ");
        msg.append(httpServletRequest.getRequestURI()).append(" 接口， 经jwt 认证失败，无法访问系统资源.");
        log.info(msg.toString());
        log.info(e.toString());
        // 用户登录时身份认证未通过
        if(e instanceof BadCredentialsException) {
            log.info("用户登录时身份认证失败.");
            // ResultUtil.writeJavaScript(httpServletResponse, ErrorCodeEnum.LOGIN_INCORRECT, msg.toString());
        }else if(e instanceof InsufficientAuthenticationException){
            log.info("缺少请求头参数,Authorization传递是token值所以参数是必须的.");
            // ResultUtil.writeJavaScript(httpServletResponse,ErrorCodeEnum.NO_TOKEN,msg.toString());
        }else {
            log.info("用户token无效.");
            // ResultUtil.writeJavaScript(httpServletResponse,ErrorCodeEnum.TOKEN_INVALID,msg.toString());
        }

    }
}
