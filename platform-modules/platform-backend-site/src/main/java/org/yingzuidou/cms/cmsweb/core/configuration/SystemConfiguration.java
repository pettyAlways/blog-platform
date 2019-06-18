package org.yingzuidou.cms.cmsweb.core.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.yingzuidou.cms.cmsweb.core.websocket.CmsWebSocket;

/**
 * 类功能描述
 * cms系统需要注册的一些Bean,这里配置了当前系统能扫描的包，为什么要配置它呢？默认SpringBoot的启动类会扫描启动类所在包下
 * 的依赖注入的注解（包括子包），但是这个系统还依赖了其他包目录，比如platform-common模块的包就不在启动类默认扫描的包下面，
 * 这样造成platform-common被maven加进来时，这个系统会找不到platform-common里面通过@Service加入容器的Bean。这里通过指定包
 * 名来防止这种问题出现
 *
 * @author 鹰嘴豆
 * @date 2018/11/25
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/25     鹰嘴豆        v1.0        引入WebSocket
 */
@Configuration
@ComponentScan("org.yingzuidou.cms, org.yingzuidou.platform")
public class SystemConfiguration {

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
        CmsWebSocket.objectMapper = objectMapper;
    }
}
