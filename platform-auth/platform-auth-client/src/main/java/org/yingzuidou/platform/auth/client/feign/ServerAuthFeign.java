package org.yingzuidou.platform.auth.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yingzuidou.platform.auth.client.config.FeignConfiguration;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;
import java.util.Map;

/**
 * 类功能描述
 * <p> 基于SpringSecurity的鉴权系统通过feign访问platform-blog-site服务来获取用户信息与鉴权的资源.
 * <p>通过{@link FeignConfiguration}配置当前Feign客户端的拦截器来增加获取
 * {@link org.yingzuidou.platform.auth.client.core.interceptor.PlatformZuulHeaderFilter}中增加的请求头
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@FeignClient(value = "${auth.serviceId}",configuration = FeignConfiguration.class)
public interface ServerAuthFeign {

    @RequestMapping("/blog/user/loadUser/{userName}")
    CmsMap<CmsUserEntity> loadUserByUserName(@PathVariable("userName") String userName);

    @RequestMapping("/blog/resource/auth")
    public CmsMap<Map<String, List<String>>> loadAuthResource();
}
