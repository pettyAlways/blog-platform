package org.yingzuidou.platform.auth.client.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.yingzuidou.platform.auth.client.feign.ServerAuthFeign;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
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

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        CmsMap<Map<String, List<String>>> authResource =  serverAuthFeign.loadAuthResource();
        Map resourceMap = CmsBeanUtils.beanTransform(authResource.get("data"), Map.class);
        Set entries = resourceMap.entrySet();
        for (Object item : entries) {
            Map.Entry entry = (Map.Entry) item;
            List<String> roles = (List<String>) entry.getValue();
            map.put((String) entry.getKey(),
                    Optional.ofNullable(roles).orElse(new ArrayList<>()).stream().map(SecurityConfig::new)
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
