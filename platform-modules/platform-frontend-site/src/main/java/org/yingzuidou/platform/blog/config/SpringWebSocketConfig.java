package org.yingzuidou.platform.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.yingzuidou.platform.blog.websocket.BlogSocket;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Configuration
public class SpringWebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 因 SpringBoot WebSocket 对每个客户端连接都会创建一个 WebSocketServer（@ServerEndpoint 注解对应的） 对象，Bean
     * 注入操作会被直接略过，因而手动注入一个全局变量
     *
     * @param objectMapper jackson对象
     */
    @Autowired
    public void setMessageService(ObjectMapper objectMapper) {
        BlogSocket.objectMapper = objectMapper;
    }
}
