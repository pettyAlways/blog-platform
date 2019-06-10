package org.yingzuidou.cms.cmsweb.core.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yingzuidou.cms.cmsweb.constant.WebSocketTypeEnum;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类功能描述
 * WebSocket处理类
 * 将用户和WebSocket的通道绑定在一起,一个用户只能拥有一个通道
 * 每次前端不同页面创建WebSocket都会创建下面新的Bean所以下面的Bean不是单例
 *
 * @author 鹰嘴豆
 * @date 2018/11/25
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@ServerEndpoint(value = "/websocket.ws/{userId}")
@Component
public class CmsWebSocket {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 静态常量保存每次新的实例创建的WebSocket会话
     */
    public static Map<Integer, Session> connectSessions = new ConcurrentHashMap<>();

    /**
     * 解决WebSocket的Bean无法注入
     */
    public static ObjectMapper objectMapper;

    /**
     * 连接建立成功调用的方法
     * 连接时传参使用@PathParam
     */
    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId, Session session) {
        //每次Open都会创建一个新的Bean所以可以用成员变量保存不同的session
        this.session = session;
        connectSessions.put(userId, session);
        logger.info("socket创建成功");
    }

    /**
     * 连接关闭调用的方法
     * 移除当前的session会话
     */
    @OnClose
    public void onClose() {
        Optional.of(connectSessions.entrySet()).orElse(new HashSet<>())
                .stream().filter(item -> item.getValue().equals(this.session))
                .forEach(item -> connectSessions.remove(item.getKey()));

        System.out.println("连接关闭");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        Map<String, Object> msg = new HashMap<>(2);
        msg.put("type", WebSocketTypeEnum.HEART.getValue());
        msg.put("msg", message);
        CmsWebSocket.sendMessage(message, session);
        System.out.println("来自客户端的消息:" + message);
    }


     @OnError
     public void onError(Session session, Throwable error) {
         System.out.println("发生错误");
         error.printStackTrace();
     }

    /**
     * 发送消息
     *
     * @param message 消息文本
     * @param session 会话
     */
    public static void sendMessage(Object message, Session session) {
        try {
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给指定用户发送消息
     *
     * @param userId 用户Id
     */
    public static void sendSpecifyUserMsg(Integer userId, Map<String, Object> msg) {
        Optional.of(CmsWebSocket.connectSessions.entrySet()).orElse(new HashSet<>())
                .stream().filter(item -> item.getKey().equals(userId))
                .forEach(item -> CmsWebSocket.sendMessage(msg, item.getValue()));
    }
}
