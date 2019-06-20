package org.yingzuidou.platform.auth.client.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 类功能描述
 * SpringSecurity默认自带一个form表单的登陆验证配置器及拦截器，但是这不适用于基于json的登陆认证，所以在这里需要重写这个
 * 登录认证的拦截器
 *
 * @author 鹰嘴豆
 * @date 2019/6/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public PlatformUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        // 从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String username = null, password = null;
        if(StringUtils.hasText(body)) {
            JSONObject jsonObj = JSON.parseObject(body);
            username = jsonObj.getString("username");
            password = jsonObj.getString("password");
        }

        username = Objects.isNull(username) ? "" : username.trim();
        password = Objects.isNull(password) ? "" : password.trim();
        // 封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
