package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.platform.common.utils.SpringUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * CustomRolesAuthorizationFilter
 *
 * @author 鹰嘴豆
 * @date 2018/10/21
 */
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject.getPrincipals() == null) {
            return false;
        }
        CmsUserEntity user = (CmsUserEntity) subject.getPrincipals().getPrimaryPrincipal();

        if (null == user) {
            return true;
        }
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        List<String> rolesList = Arrays.asList(rolesArray);
        UserBiz userBiz = SpringUtil.getBean(UserBiz.class);
        List<String> roleNames = userBiz.findRoleNameByUserId(user.getId());
        Set<String> roleSet = new HashSet<>(roleNames);

        boolean disjoint = Collections.disjoint(roleSet, rolesList);
        return !disjoint;
    }

    /**
     * 当isAccessAllowed方法返回的是false就会进入下面的方法中
     *
     * @param request 前端请求
     * @param response 请求返回
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // 访问拒绝有一种情况就是用户已经退出登录，这时候需要返回一个错误码为401
            if (subject.getPrincipal() == null) {
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            // 访问拒绝还有一种情况就是没有权限访问，这时候需要返回一个错误码为403
        } else {
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        return false;
    }
}
