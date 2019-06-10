package org.yingzuidou.platform.auth.client.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */

@Data
public class ServiceAuthVO {

    private byte[] pubKeyByte;

    @Value("${auth.client.id:null}")
    private String clientId;

    @Value("${auth.client.secret:123}")
    private String clientSecret;

    @Value("${auth.client.token-header:x-client-header}")
    private String tokenHeader;

    @Value("${spring.application.name:yingzuidou}")
    private String applicationName;
}
