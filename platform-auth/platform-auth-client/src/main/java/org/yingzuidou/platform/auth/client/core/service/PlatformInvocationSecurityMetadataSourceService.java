package org.yingzuidou.platform.auth.client.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.yingzuidou.platform.auth.client.core.exception.PlatformAuthorizationException;
import org.yingzuidou.platform.auth.client.feign.ServerAuthFeign;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.HystrixUtil;
import org.yingzuidou.platform.common.vo.CmsMap;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private Map<String, Collection<ConfigAttribute>> map = new HashMap<>(50);

    @Autowired
    private ServerAuthFeign serverAuthFeign;

    /**
     * 获取资源表中的资源url与角色的对应关系的集合，只要在资源表中配置了资源那么就要权限校验，不在资源表中的Url请求不需要
     * 进行权限校验，资源表中未赋予角色的资源也需要权限校验
     * <note>如果资源表中的路径没有赋予角色，需要构建一个随意字符，如-.-的SecurityConfig集合，否则会绕过权限校验进行登录校验
     * 只要登录就能访问，这就能预想中的不一样了</note>
     *
     * @param object 包含请求路径的对象
     * @return 该请求路径的资源权限
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        CmsMap<Map<String, List<String>>> authResource =  serverAuthFeign.loadAuthResource();
        if (!HystrixUtil.checkHystrixException(authResource)) {
            throw new PlatformAuthorizationException(authResource.get("message").toString());
        }
        Map resourceMap = CmsBeanUtils.beanTransform(authResource.get("data"), Map.class);
        Set entries = resourceMap.entrySet();
        for (Object item : entries) {
            Map.Entry entry = (Map.Entry) item;
            List<String> roles = (List<String>) entry.getValue();
            map.put((String) entry.getKey(),
                    roles.isEmpty() ? Collections.singletonList(new SecurityConfig("-.-")) : roles.stream().map(SecurityConfig::new)
                            .collect(Collectors.toList()));

        }
        return map.get(((FilterInvocation) object).getRequestUrl());
}

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
