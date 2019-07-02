package org.yingzuidou.platform.auth.client.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ServerAuthFallbackFactory implements FallbackFactory<ServerAuthFeign>  {

    @Override
    public ServerAuthFeign create(Throwable cause) {

        return new ServerAuthFeign() {
            @Override
            public CmsMap loadUserByUserName(String userName) {
                log.error("执行feign请求[/blog/user/loadUser]超时，错误[" + cause.getMessage()+ "]");
                return CmsMap.error("1000", "请求超时，重新再试");
            }

            @Override
            public CmsMap loadAuthResource() {
                log.error("执行feign请求[/blog/resource/auth]超时，错误[" + cause.getMessage()+ "]");
                return CmsMap.error("1000", "请求超时，重新再试");
            }
        };
    }

}
