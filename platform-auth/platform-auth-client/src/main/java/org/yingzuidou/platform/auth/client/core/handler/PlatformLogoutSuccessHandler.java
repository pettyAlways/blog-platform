package org.yingzuidou.platform.auth.client.core.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.yingzuidou.platform.auth.client.core.util.ResponseUtil;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.vo.CmsMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述
 * <p>登出处理器设置 在{@link org.yingzuidou.platform.auth.client.core.config.WebSecurityConfig#configure}中装配好
 * @code logout()的所需对象，其中包括这个登出成功处理类用来代替原先内置的处理类，这是因为原先的处理类不适用前后端分离，
 * 框架提供一个${@link org.springframework.security.config.annotation.web.configurers.LogoutConfigurer}来装配一个
 * {@link org.springframework.security.web.authentication.logout.LogoutFilter}过滤器，在@code doFilter() 方法中实现
 * 登出处理，包括线程上下文清除
 *
 * @author 鹰嘴豆
 * @date 2019/8/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CmsMap result = CmsMap.ok();
        ResponseUtil.sendBack(response, CmsBeanUtils.beanToJson(result));
    }
}
