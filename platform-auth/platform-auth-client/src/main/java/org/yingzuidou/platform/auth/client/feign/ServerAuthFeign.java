package org.yingzuidou.platform.auth.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@FeignClient(value = "${auth.serviceId}",configuration = {})
public interface ServerAuthFeign {

    @RequestMapping("/blog/user/loadUser/{userName}")
    CmsMap<CmsUserEntity> loadUserByUserName(@PathVariable("userName") String userName);

    @RequestMapping("/blog/resource/auth")
    public CmsMap<Map<String, List<String>>> loadAuthResource();
}
