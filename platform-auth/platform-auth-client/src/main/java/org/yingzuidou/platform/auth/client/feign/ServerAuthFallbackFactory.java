package org.yingzuidou.platform.auth.client.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/29
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Component
public class ServerAuthFallbackFactory implements FallbackFactory<ServerAuthFeign>  {

    @Override
    public ServerAuthFeign create(Throwable cause) {

        return new ServerAuthFeign() {
            @Override
            public CmsMap loadUserByUserName(String userName) {

                return CmsMap.error("1000", "platform-blog-site服务不可用");
            }

            @Override
            public CmsMap loadAuthResource() {
                return CmsMap.error("1000", "platform-blog-site服务不可用");
            }
        };
    }

}
