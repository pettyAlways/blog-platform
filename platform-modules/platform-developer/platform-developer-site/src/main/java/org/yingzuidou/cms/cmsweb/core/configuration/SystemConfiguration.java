package org.yingzuidou.cms.cmsweb.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.yingzuidou.cms.cmsweb.core.websocket.CmsWebSocket;

/**
 * 类功能描述
 * cms系统需要注册的一些Bean
 *
 * @author 鹰嘴豆
 * @date 2018/11/25
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/25     鹰嘴豆        v1.0        引入WebSocket
 */
@Configuration
public class SystemConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 因 SpringBoot WebSocket 对每个客户端连接都会创建一个 WebSocketServer（@ServerEndpoint 注解对应的） 对象，Bean 注入操作会被直接略过，因而手动注入一个全局变量
     *
     * @param objectMapper jackson对象
     */
    @Autowired
    public void setMessageService(ObjectMapper objectMapper) {
        CmsWebSocket.objectMapper = objectMapper;
    }
}
